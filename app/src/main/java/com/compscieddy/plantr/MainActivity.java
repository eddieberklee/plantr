package com.compscieddy.plantr;

import android.os.Bundle;
import android.view.View;

import com.compscieddy.plantr.god.GodFragmentButtonHelper;
import com.compscieddy.plantr.god.GodPagerAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  @BindView(R.id.god_view_pager) ViewPager mGodViewPager;
  @BindView(R.id.settings_god_fragment_button) View mSettingsGodFragmentButton;
  @BindView(R.id.todo_god_fragment_button) View mTodoGodFragmentButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(MainActivity.this);
    init();
  }

  @Override
  protected void onResume() {
    super.onResume();
    attachListeners();
  }

  @Override
  protected void onPause() {
    super.onPause();
    detachListeners();
  }

  private void init() {
    mGodViewPager.setAdapter(new GodPagerAdapter(MainActivity.this, getSupportFragmentManager()));
    mGodViewPager.setCurrentItem(1);

    setGodFragmentButtonsUnselected();
  }

  private void attachListeners() {
    mSettingsGodFragmentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(0);
      }
    });
    mTodoGodFragmentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(2);
      }
    });
    mGodViewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

      @Override
      public void onPageSelected(int position) {
        setGodFragmentButtonsUnselected();

        // Highlight current god fragment, do nothing if on main screen
        if (position == GodPagerAdapter.SETTINGS_POSITION) {
          GodFragmentButtonHelper.setGodFragmentButtonSelectedState(true, mSettingsGodFragmentButton);
        } else if (position == GodPagerAdapter.TODO_POSITION) {
          GodFragmentButtonHelper.setGodFragmentButtonSelectedState(true, mTodoGodFragmentButton);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {}
    });
  }

  private void setGodFragmentButtonsUnselected() {
    GodFragmentButtonHelper.setGodFragmentButtonSelectedState(false, mSettingsGodFragmentButton);
    GodFragmentButtonHelper.setGodFragmentButtonSelectedState(false, mTodoGodFragmentButton);
  }

  private void detachListeners() {
  }
}
