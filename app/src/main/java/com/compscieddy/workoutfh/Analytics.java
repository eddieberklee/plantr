package com.compscieddy.workoutfh;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

  public static void track(String analyticsString) {
    FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(WorkoutFHApplication.sApplicationContext);
    firebaseAnalytics.logEvent(analyticsString, null);
  }

  public static final String AUTHENTICATION_SCREEN = "authentication_screen";
  public static final String AUTHENTICATION_BUTTON = "authentication_button";

}
