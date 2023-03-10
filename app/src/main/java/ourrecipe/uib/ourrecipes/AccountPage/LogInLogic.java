package ourrecipe.uib.ourrecipes.AccountPage;

import UIKit
import Firebase

public class LogInLogic: UIViewController {

        override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        // Check if user is logged in
        if let user = Auth.auth().currentUser {
        // User is logged in, check if this is their first time
        let firstTime = UserDefaults.standard.bool(forKey: "firstTime")
        if firstTime {
        // User is first-time user, show sign up and preferences screens
        showSignUpScreen()
        showPreferencesScreen()
        } else {
        // User is returning user, show main screen
        showMainScreen()
        }
        } else {
        // User is not logged in, show login screen
        showLoginScreen()
        }
        }

private func showLoginScreen() {
        // Show login screen
        }

private func showSignUpScreen() {
        // Show sign up screen
        }

private func showPreferencesScreen() {
        // Show preferences screen
        UserDefaults.standard.set(false, forKey: "firstTime")
        }

private func showMainScreen() {
        // Show main screen (bottom navigation bar)
        }
}
