//
//  LoginViewController.swift
//  SpotifyLoginSampleApp
//
//  Created by Brian on 2022/06/14.
//

import UIKit
import GoogleSignIn

class LoginViewController: UIViewController {
    @IBOutlet weak var emailLoginButton: UIButton!
    @IBOutlet weak var googleLoginButton: GIDSignInButton!
    @IBOutlet weak var appleLoginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        [emailLoginButton, googleLoginButton, appleLoginButton].forEach {
            $0?.layer.borderWidth = 1
            $0?.layer.borderColor = UIColor.white.cgColor
            $0?.layer.cornerRadius = 30
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // 네비게이션 바 숨기기
        navigationController?.navigationBar.isHidden = true
        
    
        //GoogleSignIn
        //GIDSignIn.sharedInstance()?.presentingViewController = self
        //GIDSignIn.sharedInstance().delegate = self
    }
    
    @IBAction func tapGoogleLogin(_ sender: UIButton) {
        // firebase 인증
        GIDSignIn.sharedInstance().signIn()
    }
    
    @IBAction func tapAppleLogin(_ sender: UIButton) {
        // firebase 인증
    }
    
}
