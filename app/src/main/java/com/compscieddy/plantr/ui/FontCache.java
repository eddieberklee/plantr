package com.compscieddy.plantr.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

import timber.log.Timber;

/**
 * Created by elee on 1/7/16.
 */
public class FontCache {
  public static final int MONTSERRAT_HAIRLINE   = 0;
  public static final int MONTSERRAT_ULTRALIGHT = 1;
  public static final int MONTSERRAT_LIGHT      = 2;
  public static final int MONTSERRAT_REGULAR    = 3;
  public static final int MONTSERRAT_SEMIBOLD   = 4;
  public static final int MONTSERRAT_BOLD       = 5;
  public static final int MONTSERRAT_EXTRABOLD  = 6;
  public static final int MONTSERRAT_BLACK      = 7;

  public static final int AVENIR_NEXT_ULTRALIGHT = 8;
  public static final int AVENIR_NEXT_REGULAR = 9;
  public static final int AVENIR_NEXT_MEDIUM = 10;
  public static final int AVENIR_NEXT_DEMIBOLD = 11;
  public static final int AVENIR_NEXT_BOLD = 12;
  public static final int AVENIR_NEXT_HEAVY = 13;

  private static SparseArray<Typeface> fontCache = new SparseArray<>();

  public static Typeface get(Context context, int id) {
    Typeface tf = fontCache.get(id);
    if (tf == null) {
      String path = "";
      switch (id) {
        case MONTSERRAT_HAIRLINE:
          path = "Montserrat-Hairline.otf"; break;
        case MONTSERRAT_ULTRALIGHT:
          path = "Montserrat-UltraLight.otf"; break;
        case MONTSERRAT_LIGHT:
          path = "Montserrat-Light.otf"; break;
        case MONTSERRAT_REGULAR:
          path = "Montserrat-Regular.otf"; break;
        case MONTSERRAT_SEMIBOLD:
          path = "Montserrat-SemiBold.otf"; break;
        case MONTSERRAT_BOLD:
          path = "Montserrat-Bold.otf"; break;
        case MONTSERRAT_EXTRABOLD:
          path = "Montserrat-ExtraBold.otf"; break;
        case MONTSERRAT_BLACK:
          path = "Montserrat-Black.otf"; break;
        case AVENIR_NEXT_ULTRALIGHT:
          path = "AvenirNext-UltraLight.otf"; break;
        case AVENIR_NEXT_REGULAR:
          path = "AvenirNext-Regular.otf"; break;
        case AVENIR_NEXT_MEDIUM:
          path = "AvenirNext-Medium.otf"; break;
        case AVENIR_NEXT_DEMIBOLD:
          path = "AvenirNext-DemiBold.otf"; break;
        case AVENIR_NEXT_BOLD:
          path = "AvenirNext-Bold.otf"; break;
        case AVENIR_NEXT_HEAVY:
          path = "AvenirNext-Heavy.otf"; break;

        case -1:
          // default font would have to be
          path = "Montserrat-Regular.otf";
          break;
      }
      try {
        tf = Typeface.createFromAsset(context.getAssets(), path);
        fontCache.put(id, tf);
      } catch (RuntimeException e) {
        String errorMessage = "Runtime Exception for path: " + path + " id: " + id + " e: " + e;
        Timber.e(errorMessage);
        tf = Typeface.createFromAsset(context.getAssets(), "AvenirNext-Regular.otf");
      }
    }
    return tf;
  }
}
