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
        // Do any additional setup after loading the view.
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

