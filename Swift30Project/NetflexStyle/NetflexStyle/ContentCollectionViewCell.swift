//
//  ContentCollectionViewCell.swift
//  NetflexStyle
//
//  Created by Brian on 2022/07/07.
//

import UIKit
import SnapKit

class ContentCollectionViewCell: UICollectionViewCell {
    
    let imageView = UIImageView()
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        contentView.backgroundColor = .white
        contentView.layer.cornerRadius = 5
        contentView.clipsToBounds = true
        
        imageView.contentMode = .scaleToFill
        
        contentView.addSubview(imageView)
        
        imageView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
}
