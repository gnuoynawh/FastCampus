//
//  RoundButton.swift
//  Calculator
//
//  Created by Brian on 2022/05/31.
//

import UIKit

@IBDesignable
class RoundButton: UIButton {

    @IBInspectable var isRound: Bool = false {
        didSet {
            if isRound {
                self.layer.cornerRadius = self.frame.height/2
            }
        }
    }

}
