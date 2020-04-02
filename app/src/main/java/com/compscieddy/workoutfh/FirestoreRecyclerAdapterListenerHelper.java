package com.compscieddy.workoutfh;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import java.util.ArrayList;

/**
 * Keeps track of the recycler adapters that you need to call .startListening() and .stopListening() on.
 *
 * Because we're creating recycler views within ViewHolders where there is no activity/fragment onStart() and onStop() methods,
 * we need to keep track of a list which we cycle through to call .startListening() and .stopListening().
 */
public class FirestoreRecyclerAdapterListenerHelper {

  ArrayList<FirestoreRecyclerAdapter> mFirestoreRecyclerAdapters = new ArrayList<>();

  public void addFirestoreRecyclerAdapterForListeningCallbacks(FirestoreRecyclerAdapter firestoreRecyclerAdapter) {
    mFirestoreRecyclerAdapters.add(firestoreRecyclerAdapter);
  }

  public void removeFirestoreRecyclerAdapterForListeningCallbacks(FirestoreRecyclerAdapter firestoreRecyclerAdapter) {
    mFirestoreRecyclerAdapters.remove(firestoreRecyclerAdapter);
  }

  public void startListeningOnAllAdapters() {
    for (FirestoreRecyclerAdapter adapter : mFirestoreRecyclerAdapters) {
      adapter.startListening();
    }
  }

  public void stopListeningOnAllAdapters() {
    for (FirestoreRecyclerAdapter adapter : mFirestoreRecyclerAdapters) {
      adapter.stopListening();
    }
  }
}
