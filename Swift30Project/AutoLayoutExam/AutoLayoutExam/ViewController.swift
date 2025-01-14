//
//  ViewController.swift
//  AutoLayoutExam
//
//  Created by Brian on 2022/05/23.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var colorView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func tapChangeColorButton(_ sender: UIButton) {
        self.colorView.backgroundColor = UIColor.blue
        print("색상변경 버튼이 클릭되었음")
    }
    
}

