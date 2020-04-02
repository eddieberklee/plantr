package com.compscieddy.workoutfh.habit_record;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.model.HabitRecord;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class HabitRecordRecyclerAdapter extends FirestoreRecyclerAdapter<HabitRecord, HabitRecordViewHolder> {

  private FragmentManager mChildFragmentManager;

  public HabitRecordRecyclerAdapter(FragmentManager childFragmentManager, String habitId) {
    super(
        new FirestoreRecyclerOptions.Builder<HabitRecord>()
            .setQuery(HabitRecord.getHabitRecordQuery(habitId), HabitRecord.class)
            .build());
    mChildFragmentManager = childFragmentManager;
  }

  @NonNull
  @Override
  public HabitRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new HabitRecordViewHolder(
        mChildFragmentManager,
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit_record, parent, false));
  }

  @Override
  protected void onBindViewHolder(@NonNull HabitRecordViewHolder holder, int position, @NonNull HabitRecord model) {
    holder.setHabitRecordModel(model);
  }
}
