//
//  ViewController.swift
//  ScreenTranctionExam
//
//  Created by Brian on 2022/05/25.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        print("ViewController 뷰가 로드되었다")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        print("ViewController 뷰가 나타날 것이다")
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        print("ViewController 뷰가 나타났다")
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        print("ViewController 뷰가 사라질 것이다")
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        print("ViewController 뷰가 사라졌다")
    }
    

    

    @IBAction func tapCodePushButton(_ sender: Any) {
        guard let viewController = self.storyboard?.instantiateViewController(withIdentifier: "CodePushViewController") else { return }
        
        self.navigationController?.pushViewController(viewController, animated: true)
    }
    
    @IBAction func tapCodePresentButton(_ sender: Any) {
        guard let viewController1 = self.storyboard?.instantiateViewController(withIdentifier: "CodePresentViewController") else { return }
        
        viewController1.modalPresentationStyle = .fullScreen
        self.navigationController?.present(viewController1, animated: true)
    }
    
    
}

