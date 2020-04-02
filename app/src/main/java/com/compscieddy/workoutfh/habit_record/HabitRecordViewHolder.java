package com.compscieddy.workoutfh.habit_record;

import android.view.View;
import android.widget.TextView;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.model.HabitRecord;
import com.compscieddy.workoutfh.util.StringUtil;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HabitRecordViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.habit_record_count) TextView mHabitRecordCount;

  private FragmentManager mChildFragmentManager;
  private HabitRecord mHabitRecord;

  public HabitRecordViewHolder(FragmentManager childFragmentManager, @NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(HabitRecordViewHolder.this, itemView);
    mChildFragmentManager = childFragmentManager;
    init();
  }

  void setHabitRecordModel(HabitRecord habitRecord) {
    mHabitRecord = habitRecord;
    initHabitRecord();
  }

  private void init() {

  }

  private void initHabitRecord() {
    mHabitRecordCount.setText("+" + StringUtil.getCountString(mHabitRecord.getHabitCount()));
  }
}
