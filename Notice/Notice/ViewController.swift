//
//  ViewController.swift
//  Notice
//
//  Created by Brian on 2022/06/22.
//

import UIKit
import FirebaseRemoteConfig
import FirebaseAnalytics

class ViewController: UIViewController {

    var remoteConfig: RemoteConfig?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        remoteConfig = RemoteConfig.remoteConfig()
        
        let setting = RemoteConfigSettings()
        setting.minimumFetchInterval = 0
        
        remoteConfig?.configSettings = setting
        remoteConfig?.setDefaults(fromPlist: "RemoteConfigDefaults")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        getNotice()
    }


}

// RemoteConfig
extension ViewController {
    func getNotice() {
        guard let remoteConfig = remoteConfig else { return }
        
        remoteConfig.fetch { [weak self] status, _ in
            
            if status == .success {
                remoteConfig.activate(completion: nil)
            } else {
                print("ERROR: config not fetched")
            }
            
            guard let self = self else { return }
            
            if !self.isNoticeHidden(remoteConfig) {
                let noticeVC = NoticeViewController(nibName: "NoticeViewController", bundle: nil)
                
                noticeVC.modalPresentationStyle = .custom
                noticeVC.modalTransitionStyle = .crossDissolve
                
                let title = (remoteConfig["title"].stringValue ?? "" ).replacingOccurrences(of: "\\n", with: "\n")
                let detail = (remoteConfig["detail"].stringValue ?? "" ).replacingOccurrences(of: "\\n", with: "\n")
                let date = (remoteConfig["date"].stringValue ?? "" ).replacingOccurrences(of: "\\n", with: "\n")
                
                noticeVC.noticeContents = (title: title, detail: detail, date: date)
                self.present(noticeVC, animated: true, completion: nil)
            } else {
                
                // A/B Test
                let mmm = remoteConfig["message"].stringValue ?? ""
            
                let confirmAction = UIAlertAction(title: "확인하기", style: .default) { _ in
                    
                    Analytics.logEvent("promotion_alert", parameters: nil)
                    
                }
                
                let cancelAction = UIAlertAction(title: "취소", style: .default, handler: nil)
                let alertController = UIAlertController(title: "깜짝이벤트", message: mmm, preferredStyle: .alert)
                alertController.addAction(cancelAction)
                alertController.addAction(confirmAction)
                
                self.present(alertController, animated: true, completion: nil)
            }
        }
    }
    
    func isNoticeHidden(_ remoteConfig: RemoteConfig) -> Bool {
        return remoteConfig["isHidden"].boolValue
    }
}
