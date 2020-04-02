package com.compscieddy.plantr.authentication;

import android.app.Activity;

import com.compscieddy.plantr.util.ActivityUtil;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationHelper {

  public static boolean isLoggedOut() {
    return FirebaseAuth.getInstance().getCurrentUser() == null;
  }

  public static void handleLoggedOutUser(Activity activity) {
    ActivityUtil.launchActivityAndFinish(activity, AuthenticationActivity.class);
  }

}
