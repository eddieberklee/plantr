package com.compscieddy.plantr.authentication;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.compscieddy.eddie_utils.etil.Etil;
import com.compscieddy.plantr.Analytics;
import com.compscieddy.plantr.MainActivity;
import com.compscieddy.plantr.R;
import com.compscieddy.plantr.PlantrApplication;
import com.compscieddy.plantr.model.User;
import com.compscieddy.plantr.util.CrashUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.VISIBLE;
import static com.compscieddy.plantr.Analytics.AUTHENTICATION_BUTTON;
import static com.compscieddy.plantr.PreferenceConstants.FIRST_LOGIN_MILLIS;

public class AuthenticationActivity extends AppCompatActivity {

  public static final int REQUEST_CODE_SIGN_IN = 101;
  private static final int REQUEST_CODE_RESOLVE_CONNECTION = 102;

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthStateListener;

  private GoogleApiClient mGoogleClient;

  @BindView(R.id.sign_in_button) SignInButton mGoogleLoginButton;
  @BindView(R.id.loading_screen) View mLoadingScreen;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_authentication);
    ButterKnife.bind(AuthenticationActivity.this);
    Analytics.track(Analytics.AUTHENTICATION_SCREEN);
    init();
    setListeners();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mGoogleClient.connect();
    mAuth.addAuthStateListener(mAuthStateListener);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mGoogleClient.disconnect();
    if (mAuthStateListener != null) {
      mAuth.removeAuthStateListener(mAuthStateListener);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Timber.d("onActivityResult()");

    switch (requestCode) {
      case REQUEST_CODE_RESOLVE_CONNECTION:
        CrashUtil.log("Request Code REQUEST_CODE_RESOLVE_CONNECTION");
        if (resultCode == RESULT_OK) {
          mGoogleClient.connect();
        }
        break;
      case REQUEST_CODE_SIGN_IN:
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Timber.d("REQUEST_CODE_SIGN_IN status: " + result.getStatus() + " status code: " + result.getStatus().getStatusCode());
        if (result.isSuccess()) {
          Timber.d("Google sign-in was successful");
          // Google Sign In was successful, authenticate with Firebase
          GoogleSignInAccount account = result.getSignInAccount();
          firebaseAuthWithGoogle(account);
        } else if (result.getStatus().getStatusCode() == 7) { // found out this magic constant through debugging
          Etil.showToast(this, "Not detecting a network connection.");
        } else {
          CrashUtil.log("Found a non-successful google api sign-in case. Status: " + result.getStatus() + " status code: " + result.getStatus().getStatusCode());
        }
        showLoadingScreen();
        break;
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Timber.d("firebaseAuthWithGoogle:" + acct.getId() + " /// " + acct.getIdToken());

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

    FirebaseAuth auth = FirebaseAuth.getInstance();
    auth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            Timber.d("signInWithCredential:onComplete:" + task.isSuccessful());

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
              Timber.d("signInWithCredential " + task.getException());
              CrashUtil.logAndShowToast("Authentication Failed - Please Retry");

              Handler handler = new Handler(Looper.getMainLooper());
              handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  AuthenticationActivity.this.finish();
                }
              }, 3000);
            }
          }
        });
  }

  private void init() {
    mAuth = FirebaseAuth.getInstance();
    mAuthStateListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        handleSignInOrSignOut(firebaseAuth);
      }
    };

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.firebase_web_client_id))
        .requestEmail()
        .build();

    mGoogleClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, getOnConnectionFailedListener())
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @NotNull
  private GoogleApiClient.OnConnectionFailedListener getOnConnectionFailedListener() {
    return new GoogleApiClient.OnConnectionFailedListener() {
      @Override
      public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        CrashUtil.log("onConnectionFailed() " + connectionResult);
        if (connectionResult.hasResolution()) {
          try {
            connectionResult.startResolutionForResult(AuthenticationActivity.this, REQUEST_CODE_RESOLVE_CONNECTION);
          } catch (IntentSender.SendIntentException e) {
            CrashUtil.log("Google connection could not be established for Google API Client");
          }
        }
      }
    };
  }

  private void setListeners() {
    mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launchLogin();
        Analytics.track(AUTHENTICATION_BUTTON);
      }
    });
  }

  private void handleSignInOrSignOut(@NonNull FirebaseAuth firebaseAuth) {
    FirebaseUser user = firebaseAuth.getCurrentUser();
    if (user != null) {
      // User is signed in
      Timber.d("onAuthStateChanged:signed_in:" + user.getUid());
      loginSuccess();
    } else {
      // User is signed out
      Timber.d("onAuthStateChanged:signed_out");
    }
  }

  private void launchLogin() {
    Timber.d("auth launchLogin");
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleClient);
    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
  }

  private void loginSuccess() {
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String name = firebaseUser.getDisplayName();

    Timber.d("Login has been done successfully! For: " + firebaseUser.getEmail());
    Etil.showToast(AuthenticationActivity.this, "Hi, " + name + ".");

    saveUserToFirestoreThenForwardToMainActivity();
    saveFirstTimeLoginMillis();
  }

  private void saveUserToFirestoreThenForwardToMainActivity() {
    @Nullable FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser == null) {
      Etil.showToast(this, "Couldn't login. Close the app and try again.");
    }
    User user = new User(currentUser);
    user.saveUserToFirestore(new Runnable() {
      @Override
      public void run() {
        boolean isAlreadySignedIn = FirebaseAuth.getInstance().getCurrentUser() != null;
        if (isAlreadySignedIn) {
          Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
          startActivity(intent);
          overridePendingTransition(0, 0);
          finish();
        }
      }
    });
  }

  private void saveFirstTimeLoginMillis() {
    long firstLoginMillis = PlantrApplication.getSharedPreferencesLong(FIRST_LOGIN_MILLIS);
    if (firstLoginMillis == -1L) {
      PlantrApplication.setSharedPreferencesLong(FIRST_LOGIN_MILLIS, System.currentTimeMillis());
    }
  }

  private void showLoadingScreen() {
    mLoadingScreen.setVisibility(VISIBLE);
  }

}
