package com.compscieddy.workoutfh.habit;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.compscieddy.workoutfh.FirestoreRecyclerAdapterListenerHelper;
import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.RecordHabitFragment;
import com.compscieddy.workoutfh.habit_record.HabitRecordRecyclerAdapter;
import com.compscieddy.workoutfh.model.Habit;
import com.compscieddy.workoutfh.util.StringUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.FrameLayout;

public class HabitViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.habit_name) TextView mHabitName;
  @BindView(R.id.habit_count) TextView mHabitCount;
  @BindView(R.id.habit_record_button) FrameLayout mHabitRecordButton;
  @BindView(R.id.habit_record_recycler_view) RecyclerView mHabitRecordRecyclerView;

  private final Context mContext;
  private FragmentManager mChildFragmentManager;

  private Habit mHabit;
  private FirestoreRecyclerAdapterListenerHelper mFirestoreRecyclerAdapterListenerHelper;

  public HabitViewHolder(
      FragmentManager childFragmentManager,
      FirestoreRecyclerAdapterListenerHelper firestoreRecyclerAdapterListenerHelper,
      @NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(HabitViewHolder.this, itemView);
    mContext = itemView.getContext();
    mChildFragmentManager = childFragmentManager;
    mFirestoreRecyclerAdapterListenerHelper = firestoreRecyclerAdapterListenerHelper;
    init();
  }

  void setHabitModel(Habit habit) {
    mHabit = habit;
    initHabit();
    initHabitRecordRecyclerView();
  }

  private void init() {
    mHabitRecordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RecordHabitFragment recordHabitFragment = RecordHabitFragment.newInstance();
        recordHabitFragment.setHabit(mHabit);
        recordHabitFragment.show(mChildFragmentManager, RecordHabitFragment.TAG);
      }
    });
  }

  private void initHabit() {
    mHabitName.setText(mHabit.getHabitName());
    mHabitCount.setText(StringUtil.getCountString(mHabit.getTotalHabitCount()));
  }

  private void initHabitRecordRecyclerView() {
    FirestoreRecyclerAdapter habitRecordRecyclerAdapter = new HabitRecordRecyclerAdapter(mChildFragmentManager, mHabit.getId());
    habitRecordRecyclerAdapter.startListening();
    mFirestoreRecyclerAdapterListenerHelper.addFirestoreRecyclerAdapterForListeningCallbacks(habitRecordRecyclerAdapter);
    mHabitRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
    mHabitRecordRecyclerView.setAdapter(habitRecordRecyclerAdapter);
  }

}
