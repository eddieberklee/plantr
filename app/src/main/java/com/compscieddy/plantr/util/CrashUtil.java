package com.compscieddy.plantr.util;

import com.compscieddy.eddie_utils.etil.Etil;
import com.compscieddy.plantr.PlantrApplication;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import timber.log.Timber;

public class CrashUtil {

  public static void log(String errorMessage) {
    Timber.e(errorMessage);
    FirebaseCrashlytics.getInstance().log(errorMessage);
  }

  public static void logAndShowToast(String errorMessage) {
    log(errorMessage);
    Etil.showToast(PlantrApplication.sApplicationContext, errorMessage);
  }
}
