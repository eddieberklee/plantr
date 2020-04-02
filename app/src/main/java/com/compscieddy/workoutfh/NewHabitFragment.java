package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;

import com.compscieddy.workoutfh.model.Habit;
import com.compscieddy.workoutfh.ui.FloatingBaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewHabitFragment extends FloatingBaseFragment {

  public static final String TAG = NewHabitFragment.class.getSimpleName();
  private View mRootView;
  private View mSubmitButton;
  private EditText mHabitNameEditText;
  private View mBlackBackground;
  private View mMainDialogContainer;

  public static NewHabitFragment newInstance() {
    return new NewHabitFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_new_habit, container, false);
    /** ButterKnife does not appear to work, debugging was proving to be a waste of time. */
    initViews();
    return mRootView;
  }

  @Override
  public View getBlackBackground() {
    return mBlackBackground;
  }

  @Override
  public View getMainDialogContainer() {
    return mMainDialogContainer;
  }

  @Override
  public View getKeyboardFocusView() {
    return mHabitNameEditText;
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

  private void initViews() {
    mSubmitButton = mRootView.findViewById(R.id.new_habit_submit_button);
    mHabitNameEditText = mRootView.findViewById(R.id.new_habit_name_input);
    mBlackBackground = mRootView.findViewById(R.id.black_background);
    mMainDialogContainer = mRootView.findViewById(R.id.main_dialog_container);
  }

  private void attachListeners() {
    mSubmitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String habitName = mHabitNameEditText.getText().toString();

        if (TextUtils.isEmpty(habitName)) {
          mHabitNameEditText.animate()
              .setDuration(300)
              .scaleX(1.2f)
              .scaleY(1.2f)
              .setInterpolator(new BounceInterpolator())
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  mHabitNameEditText.animate()
                      .setDuration(300)
                      .scaleX(1.0f)
                      .scaleY(1.0f)
                      .setInterpolator(new BounceInterpolator());
                }
              });
          return;
        }

        Habit.createNewHabitOnFirestore(habitName);
        dismissWithAnimation();
      }
    });
  }

  private void detachListeners() {
    mSubmitButton.setOnClickListener(null);
    mBlackBackground.setOnClickListener(null);
  }
}
