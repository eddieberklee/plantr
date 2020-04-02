package com.compscieddy.plantr.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.compscieddy.eddie_utils.etil.Etil;
import com.compscieddy.eddie_utils.etil.KeyboardEtil;
import com.compscieddy.plantr.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public abstract class FloatingBaseFragment extends DialogFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.FloatingFullscreenDialogTheme);
  }

  @Override
  public void onStart() {
    super.onStart();
    getDialog().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);

    runEnteringAnimation();
  }

  @Override
  public void onResume() {
    super.onResume();
    attachListeners();
  }

  @Override
  public void onPause() {
    super.onPause();
    detachListeners();
  }

  /** Used for enter and exit animation of black background and dialog container. */
  public abstract View getBlackBackground();

  /** Used for enter and exit animation of black background and dialog container. */
  public abstract View getMainDialogContainer();

  /** Used for showing/hiding keyboard. */
  public abstract View getKeyboardFocusView();

  private void attachListeners() {
    getBlackBackground().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissWithAnimation();
      }
    });
  }

  private void detachListeners() {
    getBlackBackground().setOnClickListener(null);
  }

  protected void dismissWithAnimation() {
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        KeyboardEtil.hideKeyboard(getKeyboardFocusView());
      }
    }, 350);

    getBlackBackground().animate()
        .alpha(0)
        .setDuration(700)
        .withEndAction(new Runnable() {
          @Override
          public void run() {
            FloatingBaseFragment.this.dismiss();
          }
        });
    getMainDialogContainer().animate()
        .alpha(0)
        .setDuration(600)
        .setInterpolator(new FastOutSlowInInterpolator())
        .translationY(Etil.dpToPx(-30));
  }

  private void runEnteringAnimation() {
    getBlackBackground().setAlpha(0);
    getMainDialogContainer().setAlpha(0);

    getBlackBackground().animate()
        .alpha(1)
        .setDuration(500);
    getMainDialogContainer().animate()
        .alpha(1)
        .translationY(0)
        .setDuration(400);
  }
}
