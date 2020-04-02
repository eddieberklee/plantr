package com.compscieddy.plantr.util;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil {

  public static void launchActivityAndFinish(Activity fromActivity, Class toActivity) {
    Intent intent = new Intent(fromActivity, toActivity);
    fromActivity.startActivity(intent);
    fromActivity.finish();
  }

}
