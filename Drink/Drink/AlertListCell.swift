//
//  AlertListCell.swift
//  Drink
//
//  Created by Brian on 2022/06/23.
//

import UIKit
import UserNotifications

class AlertListCell: UITableViewCell {

    let userNotificationCenter = UNUserNotificationCenter.current()

    @IBOutlet weak var meridiemLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var alertSwitch: UISwitch!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func alertSwitchValueChange(_ sender: UISwitch) {
        guard let data = UserDefaults.standard.value(forKey: "alerts") as? Data,
              var alerts = try? PropertyListDecoder().decode([Alert].self, from: data)  else { return }
        
        alerts[sender.tag].isOn = sender.isOn
        
        UserDefaults.standard.set(try? PropertyListEncoder().encode(alerts), forKey: "alerts")
        
        if sender.isOn {
            self.userNotificationCenter.addNotificationRequest(by: alerts[sender.tag])
        } else {
            self.userNotificationCenter.removePendingNotificationRequests(withIdentifiers: [alerts[sender.tag].id])
        }

    }
}
