package com.compscieddy.workoutfh.god;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.authentication.AuthenticationActivity;
import com.compscieddy.workoutfh.util.ActivityUtil;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsGodFragment extends Fragment {

  @BindView(R.id.logout_button) View mLogoutButton;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_settings_god, container, false);
    ButterKnife.bind(SettingsGodFragment.this, rootView);
    return rootView;
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

  private void init() {

  }

  private void attachListeners() {
    mLogoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();

        ActivityUtil.launchActivityAndFinish(getActivity(), AuthenticationActivity.class);
      }
    });
  }

  private void detachListeners() {
    mLogoutButton.setOnClickListener(null);
  }
}
