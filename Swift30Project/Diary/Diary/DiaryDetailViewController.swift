//
//  DiaryDetailViewController.swift
//  Diary
//
//  Created by Brian on 2022/06/02.
//

import UIKit

//protocol DiaryDetailViewDelegate: AnyObject {
//    func didSelectDelete(indexPath: IndexPath)
//     func didSelectStar(indexPath: IndexPath, isStar: Bool)
//}

class DiaryDetailViewController: UIViewController {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var contentsTextView: UITextView!
    @IBOutlet weak var dateLabel: UILabel!
    var starButton: UIBarButtonItem?

    // weak var delegate: DiaryDetailViewDelegate?
    var diary: Diary?
    var indexPath: IndexPath?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.configureView()
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(starDiaryNotification(_:)),
            name: NSNotification.Name("starDiary"),
            object: nil
        )
    }
    
    private func configureView() {
        guard let diary = self.diary else {return}
        self.titleLabel.text = diary.title
        self.contentsTextView.text = diary.contents
        self.dateLabel.text = dataToString(date: diary.date)
        
        self.starButton = UIBarButtonItem(image: nil, style: .plain, target: self, action: #selector(tapStarButton))
        self.starButton?.image = diary.isStar ? UIImage(systemName: "star.fill") : UIImage(systemName: "star")
        self.starButton?.tintColor = .orange
        self.navigationItem.rightBarButtonItem = self.starButton
    }

    @IBAction func tapEditButton(_ sender: UIButton) {
        guard let viewController = self.storyboard?.instantiateViewController(withIdentifier: "WriteDiaryViewController") as? WriteDiaryViewController else { return }
        guard let indexPath = self.indexPath else {return}
        guard let diary = self.diary else {return}
        viewController.diaryEditorMode = .edit(indexPath, diary)
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(editDiaryNotification(_:)),
            name: NSNotification.Name("editDiary"),
            object: nil
        )
        self.navigationController?.pushViewController(viewController, animated: true)
    }
    
    @objc func editDiaryNotification(_ notification: Notification) {
        guard let diary = notification.object as? Diary else { return }
        
        self.diary = diary
        self.configureView()
    }
    
    @IBAction func tapDeleteButton(_ sender: UIButton) {
        guard let uuidString = self.diary?.uuidString else { return }
        NotificationCenter.default.post(
            name: NSNotification.Name("deleteDiary"),
            object: uuidString,
            userInfo: nil
        )
        
        self.navigationController?.popViewController(animated: true)
    }
    
    private func dataToString(date: Date) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yy년 MM월 dd일 (EEEEEE)"
        formatter.locale = Locale(identifier: "ko_KR")
        return formatter.string(from: date)
    }
    
    @objc func tapStarButton() {
        guard let isStar = self.diary?.isStar else { return }
        if isStar {
            self.starButton?.image = UIImage(systemName: "star")
        } else {
            self.starButton?.image = UIImage(systemName: "star.fill")
        }
        self.diary?.isStar = !isStar
        //self.delegate?.didSelectStar(indexPath: indexPath, isStar: self.diary?.isStar ?? false)
        
        NotificationCenter.default.post(
            name: NSNotification.Name("starDiary"),
            object: [
                "isStar": self.diary?.isStar ?? false,
                "uuidString": self.diary?.uuidString ?? false
            ],
            userInfo: nil
        )
    }

    @objc func starDiaryNotification(_ notification: Notification) {
        guard let startDiary = notification.object as? [String: Any] else { return }
        guard let isStar = startDiary["isStar"] as? Bool else { return }
        guard let uuidString = startDiary["uudiString"] as? String else { return }
        guard let diary = self.diary else { return }

        if diary.uuidString == uuidString {
            self.diary?.isStar = isStar
            self.configureView()
        }
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
