package com.compscieddy.workoutfh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.compscieddy.workoutfh.ui.FontCache;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import static com.compscieddy.workoutfh.ui.FontCache.AVENIR_NEXT_REGULAR;
import static com.compscieddy.workoutfh.FontConstants.DEFAULT_FONT;
import static com.compscieddy.workoutfh.FontConstants.PREF_SELECTED_FONT;

/**
 * Created by elee on 1/7/16.
 */
public class FontAutoCompleteTextView extends AppCompatAutoCompleteTextView {

  private Context mContext;

  public FontAutoCompleteTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    mContext = context;
    if (isInEditMode()) return;

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontAutoCompleteTextView);
    int typefaceId = ta.getInt(R.styleable.FontEditText_fontface, AVENIR_NEXT_REGULAR);
    if (typefaceId == -1) {
      // If there isn't an explicit fontface set, then let's go with the settings option
      SharedPreferences sharedPreferences = WorkoutFHApplication.getSharedPreferences();
      typefaceId = sharedPreferences.getInt(PREF_SELECTED_FONT, DEFAULT_FONT);
    }
    setCustomFont(typefaceId);
    ta.recycle();
  }

  // Note: any modifications should be reflected for both FontTextView and FontEditText
  public void setCustomFont(int typefaceId) {
    setTypeface(FontCache.get(mContext, typefaceId));
  }

}
