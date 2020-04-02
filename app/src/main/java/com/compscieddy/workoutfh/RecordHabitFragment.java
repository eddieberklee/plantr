package com.compscieddy.workoutfh;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.compscieddy.workoutfh.model.Habit;
import com.compscieddy.workoutfh.model.HabitRecord;
import com.compscieddy.workoutfh.ui.FloatingBaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecordHabitFragment extends FloatingBaseFragment {

  public static final String TAG = RecordHabitFragment.class.getSimpleName();

  private View mRootView;
  private EditText mHabitCountEditText;
  private View mHabitRecordButton;

  private Habit mHabit;

  public static RecordHabitFragment newInstance() {
    return new RecordHabitFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_record_habit, container, false);
    initViews();
    init();
    return mRootView;
  }

  /**
   * Habit needs to be passed in.
   */
  public void setHabit(Habit habit) {
    mHabit = habit;
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

  @Override
  public View getBlackBackground() {
    return mRootView.findViewById(R.id.black_background);
  }

  @Override
  public View getMainDialogContainer() {
    return mRootView.findViewById(R.id.main_dialog_container);
  }

  @Override
  public View getKeyboardFocusView() {
    return mHabitCountEditText;
  }

  private void initViews() {
    mHabitCountEditText = mRootView.findViewById(R.id.habit_count_edit_text);
    mHabitRecordButton = mRootView.findViewById(R.id.save_habit_record_button);
  }

  private void init() {
    mHabitCountEditText.post(new Runnable() {
      @Override
      public void run() {
        mHabitCountEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mHabitCountEditText, InputMethodManager.SHOW_IMPLICIT);
      }
    });
  }

  private void attachListeners() {
    mHabitRecordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveHabitRecordAndDismissWithAnimation();
      }
    });
    mHabitCountEditText.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
            && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
          saveHabitRecordAndDismissWithAnimation();
          return true;
        }
        return false;
      }
    });
  }

  private void saveHabitRecordAndDismissWithAnimation() {
    HabitRecord.createNewHabitRecordOnFirestore(mHabit, Float.valueOf(mHabitCountEditText.getText().toString()));
    dismissWithAnimation();
  }

  private void detachListeners() {
    mHabitRecordButton.setOnClickListener(null);
    mHabitCountEditText.setOnKeyListener(null);
  }
}
