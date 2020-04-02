package com.compscieddy.plantr.god;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compscieddy.plantr.FirestoreRecyclerAdapterListenerHelper;
import com.compscieddy.plantr.NewHabitFragment;
import com.compscieddy.plantr.R;
import com.compscieddy.plantr.habit.HabitRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainGodFragment extends Fragment {

  @BindView(R.id.new_habit_button) View mNewHabitButton;
  @BindView(R.id.habit_recycler_view) RecyclerView mHabitRecyclerView;

  private HabitRecyclerAdapter mHabitRecyclerAdapter;
  private FirestoreRecyclerAdapterListenerHelper mFirestoreRecyclerAdapterListenerHelper;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main_god, container, false);
    ButterKnife.bind(MainGodFragment.this, rootView);
    initRecyclerViews();
    return rootView;
  }

  @Override
  public void onStart() {
    super.onStart();
    mHabitRecyclerAdapter.startListening();

    /** This is most likely not doing anything because HabitRecordRecyclerAdapters haven't even been created yet. */
    mFirestoreRecyclerAdapterListenerHelper.startListeningOnAllAdapters();
  }

  @Override
  public void onStop() {
    super.onStop();
    mHabitRecyclerAdapter.stopListening();
    mFirestoreRecyclerAdapterListenerHelper.stopListeningOnAllAdapters();
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

  /**
   * Init both RecyclerView for Habit and HabitRecord.
   *
   * HabitRecordRecyclerAdapter needs to be setup here so that we can properly call the .startListening() and .stopListening() methods.
   */
  private void initRecyclerViews() {
    mFirestoreRecyclerAdapterListenerHelper = new FirestoreRecyclerAdapterListenerHelper();
    mHabitRecyclerAdapter = new HabitRecyclerAdapter(getChildFragmentManager(), mFirestoreRecyclerAdapterListenerHelper);
    mHabitRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    mHabitRecyclerView.setAdapter(mHabitRecyclerAdapter);
  }

  private void attachListeners() {
    mNewHabitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        launchNewHabitFragment();
      }
    });
  }

  private void detachListeners() {
    mNewHabitButton.setOnClickListener(null);
  }

  private void launchNewHabitFragment() {
    DialogFragment newHabitFragment = NewHabitFragment.newInstance();
    newHabitFragment.show(getChildFragmentManager(), NewHabitFragment.TAG);
  }
}
