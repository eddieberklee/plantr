package com.compscieddy.plantr.model;

import com.compscieddy.plantr.util.AuthenticationUtil;
import com.compscieddy.plantr.util.CrashUtil;
import com.compscieddy.plantr.util.DateUtil;
import com.compscieddy.plantr.util.FirestoreUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HabitRecord {

  public static final String HABIT_DAY_COLLECTION = "habitDay";

  public static final String FIELD_ID = "id";
  public static final String FIELD_USER_EMAIL = "userEmail";
  public static final String FIELD_YEAR_MONTH_DAY = "yearMonthDay";
  public static final String FIELD_HABIT_ID = "habitId";
  public static final String FIELD_HABIT_COUNT = "habitCount";
  public static final String FIELD_CREATED_AT_MILLIS = "createdAtMillis";

  private String mId;
  private String mUserEmail;
  private String mYearMonthDay;
  private String mHabitId;
  private float mHabitCount;
  private long mCreatedAtMillis;

  public HabitRecord() {}

  public HabitRecord(Calendar calendar, String habitId, float habitCount) {
    mId = FirestoreUtil.generateId(getHabitRecordCollection());
    mUserEmail = AuthenticationUtil.getUserEmail();
    mHabitId = habitId;
    mYearMonthDay = DateUtil.getYearMonthDayString(calendar);
    mHabitCount = habitCount;
    mCreatedAtMillis = System.currentTimeMillis();
  }

  public static void createNewHabitRecordOnFirestore(Habit habit, float habitCount) {
    HabitRecord habitRecord = new HabitRecord(Calendar.getInstance(), habit.getId(), habitCount);
    habitRecord.saveHabitRecordToFirestore(null);
  }

  /**
   * Habit Record has a count that needs to be added to the total habit count on Habit.
   * This is called an aggregation. Doing the client-side implementation here.
   * https://firebase.google.com/docs/firestore/solutions/aggregation
   */
  private void saveHabitRecordToFirestore(@Nullable final Runnable onSuccessRunnable) {
    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Void>() {
      @Nullable
      @Override
      public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
        // Update the Habit
        DocumentReference habitReference = Habit.getHabitCollection().document(getHabitId());
        Habit habit = transaction.get(habitReference).toObject(Habit.class);
        habit.updateTotalHabitCount(HabitRecord.this);
        transaction.set(habitReference, habit);

        // Update the HabitRecord
        DocumentReference habitRecordReference = getHabitRecordCollection().document(getId());
        transaction.set(habitRecordReference, HabitRecord.this);
        return null;
      }
    })
    .addOnSuccessListener(aVoid -> {
      if (onSuccessRunnable != null) {
        onSuccessRunnable.run();
      }
    })
    .addOnFailureListener(e -> {
      CrashUtil.log("Failed to save habit record with id: " + getId());
    });
  }

  @Exclude
  public static CollectionReference getHabitRecordCollection() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db.collection(HABIT_DAY_COLLECTION);
  }

  @Exclude
  public static Query getHabitRecordQuery(String habitId) {
    return getHabitRecordCollection()
        .whereEqualTo(FIELD_USER_EMAIL, AuthenticationUtil.getUserEmail())
        .whereEqualTo(FIELD_HABIT_ID, habitId)
        .orderBy(FIELD_CREATED_AT_MILLIS, Query.Direction.DESCENDING);
  }

  /** Getters and Setters */
  public String getId() {
    return mId;
  }

  public void setId(String id) {
    mId = id;
  }

  public String getUserEmail() {
    return mUserEmail;
  }

  public void setUserEmail(String userEmail) {
    mUserEmail = userEmail;
  }

  public String getYearMonthDay() {
    return mYearMonthDay;
  }

  public void setYearMonthDay(String yearMonthDay) {
    mYearMonthDay = yearMonthDay;
  }

  public String getHabitId() {
    return mHabitId;
  }

  public void setHabitId(String habitId) {
    mHabitId = habitId;
  }

  public float getHabitCount() {
    return mHabitCount;
  }

  public void setHabitCount(float habitCount) {
    mHabitCount = habitCount;
  }

  public long getCreatedAtMillis() {
    return mCreatedAtMillis;
  }

  public void setCreatedAtMillis(long createdAtMillis) {
    mCreatedAtMillis = createdAtMillis;
  }
}
