//
//  SeguePresentViewController.swift
//  ScreenTranctionExam
//
//  Created by Brian on 2022/05/25.
//

import UIKit

class SeguePresentViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    @IBAction func tapBackButton(_ sender: Any) {
        self.presentingViewController?.dismiss(animated: true)
    }
}
