package com.compscieddy.plantr.util;

import java.util.Calendar;

public class DateUtil {

  /**
   * YYYYMMDD Format is zero padded on the months and days
   *
   * Ex: 20200328 for March 28, 2020
   */
  public static String getYearMonthDayString(Calendar calendar) {
    StringBuilder builder = new StringBuilder();
    builder.append(calendar.get(Calendar.YEAR));

    String month = String.valueOf(calendar.get(Calendar.MONTH));
    builder.append(month.length() == 1 ? "0" + month : month);

    String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    builder.append(day.length() == 1 ? "0" + day : day);

    return builder.toString();
  }

  /**
   * YYYYMMDD Format is zero padded on the months and days
   *
   * Ex: 20200328 for March 28, 2020
   */
  public static Calendar getYearMonthDayCalendar(String yearMonthDay) {
    Calendar calendar = Calendar.getInstance();

    int year = Integer.valueOf(yearMonthDay.substring(0, 3));
    int month = Integer.valueOf(yearMonthDay.substring(3, 5));
    int day = Integer.valueOf(yearMonthDay.substring(5, 7));

    calendar.set(year, month, day);
    return calendar;
  }

}
