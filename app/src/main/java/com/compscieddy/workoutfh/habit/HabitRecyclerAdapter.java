package com.compscieddy.workoutfh.habit;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.compscieddy.workoutfh.FirestoreRecyclerAdapterListenerHelper;
import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.model.Habit;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class HabitRecyclerAdapter extends FirestoreRecyclerAdapter<Habit, HabitViewHolder> {

  private FragmentManager mChildFragmentManager;
  private FirestoreRecyclerAdapterListenerHelper mFirestoreRecyclerAdapterListenerHelper;

  public HabitRecyclerAdapter(
      FragmentManager childFragmentManager,
      FirestoreRecyclerAdapterListenerHelper firestoreRecyclerAdapterListenerHelper) {
    super(
        new FirestoreRecyclerOptions.Builder<Habit>()
            .setQuery(Habit.getHabitQuery(), Habit.class)
            .build());
    mChildFragmentManager = childFragmentManager;
    mFirestoreRecyclerAdapterListenerHelper = firestoreRecyclerAdapterListenerHelper;
  }

  @NonNull
  @Override
  public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new HabitViewHolder(
        mChildFragmentManager,
        mFirestoreRecyclerAdapterListenerHelper,
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, null, false));
  }

  @Override
  protected void onBindViewHolder(@NonNull HabitViewHolder holder, int position, @NonNull Habit model) {
    holder.setHabitModel(model);
  }
}
