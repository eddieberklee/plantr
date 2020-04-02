package com.compscieddy.workoutfh.util;

import com.google.firebase.firestore.CollectionReference;

public class FirestoreUtil {

  public static String generateId(CollectionReference collectionReference) {
    return collectionReference.document().getId();
  }

}
