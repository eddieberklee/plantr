package com.compscieddy.workoutfh.god;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GodPagerAdapter extends FragmentPagerAdapter {

  public static final int SETTINGS_POSITION = 0;
  public static final int TODO_POSITION = 2;

  private final Context mContext;

  public GodPagerAdapter(Context context, FragmentManager fragmentManager) {
    super(fragmentManager);
    mContext = context;
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case SETTINGS_POSITION:
        return new SettingsGodFragment();
      case 1:
        return new MainGodFragment();
      case TODO_POSITION:
      default:
        return new TodoGodFragment();
    }
  }

  @Override
  public int getCount() {
    return 3;
  }
}
