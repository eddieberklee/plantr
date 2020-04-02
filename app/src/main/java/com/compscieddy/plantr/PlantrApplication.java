package com.compscieddy.plantr;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import timber.log.Timber;

public class PlantrApplication extends Application {

  public static PlantrApplication sApplicationContext;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree() {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
          super.log(priority, "Timber " + tag, message, t);
        }
      });
    }

    sApplicationContext = PlantrApplication.this;
    initEmojis();
  }

  private void initEmojis() {
    EmojiManager.install(new IosEmojiProvider());
  }

  public static SharedPreferences getSharedPreferences() {
    SharedPreferences sharedPreferences = sApplicationContext.getSharedPreferences("plantr", Context.MODE_PRIVATE);
    return sharedPreferences;
  }

  public static boolean getSharedPreferencesBoolean(String key) {
    return getSharedPreferences().getBoolean(key, false);
  }

  public static void setSharedPreferencesBoolean(String key, boolean booleanValue) {
    SharedPreferences.Editor editor = getSharedPreferences().edit();
    editor.putBoolean(key, booleanValue);
    editor.apply();
  }

  public static int getSharedPreferencesInt(String key) {
    return getSharedPreferences().getInt(key, -1);
  }

  public static void setSharedPreferencesInt(String key, int intValue) {
    SharedPreferences.Editor editor = getSharedPreferences().edit();
    editor.putInt(key, intValue);
    editor.apply();
  }

  public static long getSharedPreferencesLong(String key) {
    return getSharedPreferences().getLong(key, -1L);
  }

  public static void setSharedPreferencesLong(String key, long longValue) {
    SharedPreferences.Editor editor = getSharedPreferences().edit();
    editor.putLong(key, longValue);
    editor.apply();
  }
}
