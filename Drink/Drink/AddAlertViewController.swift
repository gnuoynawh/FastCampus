//
//  AddAlertViewController.swift
//  Drink
//
//  Created by Brian on 2022/06/23.
//

import UIKit

class AddAlertViewController: UIViewController {
    var pickedDate: ((_ date: Date) -> Void)?
    
    @IBOutlet weak var datepicker: UIDatePicker!
    
    @IBAction func tapCancelButton(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func tapSaveButton(_ sender: UIBarButtonItem) {
        pickedDate?(datepicker.date)
        self.dismiss(animated: true, completion: nil)
    }
    
}
