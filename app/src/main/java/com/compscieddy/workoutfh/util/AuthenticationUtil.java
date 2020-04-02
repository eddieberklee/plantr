package com.compscieddy.workoutfh.util;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationUtil {

  public static String getUserEmail() {
    return FirebaseAuth.getInstance().getCurrentUser().getEmail();
  }

}
