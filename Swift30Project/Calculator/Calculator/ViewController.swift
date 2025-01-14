//
//  ViewController.swift
//  Calculator
//
//  Created by Brian on 2022/05/30.
//

import UIKit

enum Operation {
    case Add
    case Subtract
    case Divide
    case Multiply
    case Unknown
}

class ViewController: UIViewController {

    @IBOutlet weak var numberOutputLabel: UILabel!

    //
    var displayNumber = ""
    var firstOperand = ""
    var secondOperand = ""
    var result = ""
    var currentOperation: Operation = .Unknown
        
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func tapNumberButton(_ sender: UIButton) {
        guard let numberValue = sender.titleLabel?.text else { return }
        
        if self.displayNumber.count < 9 {
            self.displayNumber += numberValue
            self.numberOutputLabel.text = self.displayNumber
        }
    }
    
    @IBAction func tapClearButton(_ sender: UIButton) {
        self.displayNumber = ""
        self.firstOperand = ""
        self.secondOperand = ""
        self.result = ""
        self.currentOperation = .Unknown
        self.numberOutputLabel.text = "0"
    }
    
    @IBAction func tapDotButton(_ sender: UIButton) {
        if self.displayNumber.count < 8, !self.displayNumber.contains(".") {
            
            if self.displayNumber.isEmpty {
                self.displayNumber += "0."
            } else {
                self.displayNumber += "."
            }
            
            self.numberOutputLabel.text = self.displayNumber
        }
    }
    
    @IBAction func tapDivideButton(_ sender: UIButton) {
        self.operation(.Divide)
    }
    
    @IBAction func tapMultiplyButton(_ sender: UIButton) {
        self.operation(.Multiply)
    }
    
    @IBAction func tapSubtrackButton(_ sender: UIButton) {
        self.operation(.Subtract)
    }
    
    @IBAction func tapAddButton(_ sender: UIButton) {
        self.operation(.Add)
    }
    
    @IBAction func tapEqualButton(_ sender: UIButton) {
        self.operation(self.currentOperation)
    }
    
    private func operation(_ operation: Operation) {
        if self.currentOperation != .Unknown {
            if !self.displayNumber.isEmpty {
                self.secondOperand = self.displayNumber
                self.displayNumber = ""
                
                guard let firstOperand = Double(self.firstOperand) else { return }
                guard let seocndOperand = Double(self.secondOperand) else { return }
                
                switch self.currentOperation {
                    case.Add:
                        self.result = "\(firstOperand + seocndOperand)"
                        break
                    case.Subtract:
                        self.result = "\(firstOperand - seocndOperand)"
                        break
                    case.Divide:
                        self.result = "\(firstOperand / seocndOperand)"
                        break
                    case.Multiply:
                        self.result = "\(firstOperand * seocndOperand)"
                        break
                    default:
                        break
                }
                
                if let result = Double(self.result), result.truncatingRemainder(dividingBy: 1) == 0 {
                    self.result = "\(Int(result))"
                }
                
                self.firstOperand = self.result
                self.numberOutputLabel.text = self.result
                
                self.currentOperation = operation
            }
        } else {
            self.firstOperand = self.displayNumber
            self.currentOperation = operation
            self.displayNumber = ""
        }
    }
}

