package com.compscieddy.workoutfh.util;

import com.compscieddy.eddie_utils.etil.Etil;
import com.compscieddy.workoutfh.WorkoutFHApplication;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import timber.log.Timber;

public class CrashUtil {

  public static void log(String errorMessage) {
    Timber.e(errorMessage);
    FirebaseCrashlytics.getInstance().log(errorMessage);
  }

  public static void logAndShowToast(String errorMessage) {
    log(errorMessage);
    Etil.showToast(WorkoutFHApplication.sApplicationContext, errorMessage);
  }
}
