//
//  SeguePushViewController.swift
//  ScreenTranctionExam
//
//  Created by Brian on 2022/05/25.
//

import UIKit

class SeguePushViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        print("SeguePushViewController 뷰가 로드되었다")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        print("SeguePushViewController 뷰가 나타날 것이다")
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        print("SeguePushViewController 뷰가 나타났다")
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        print("SeguePushViewController 뷰가 사라질 것이다")
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        print("SeguePushViewController 뷰가 사라졌다")
    }
    
    @IBAction func tapBackButton(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
}
