package com.compscieddy.plantr.util;

public class StringUtil {

  /**
   * If @param count has a decimal point then we return a string with decimal point.
   * Else return a string of an int.
   */
  public static String getCountString(float count) {
    String habitCountText;
    if (count % 1 > 0) { // has a decimal point
      habitCountText = Float.toString(count);
    } else {
      habitCountText = Integer.toString((int) count);
    }
    return habitCountText;
  }

}
