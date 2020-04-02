package com.compscieddy.plantr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.compscieddy.plantr.R;
import com.vanniktech.emoji.EmojiTextView;

import static com.compscieddy.plantr.ui.FontCache.AVENIR_NEXT_REGULAR;

/**
 * Created by elee on 1/6/16.
 */
public class FontTextView extends EmojiTextView {

  public FontTextView(Context context) {
    super(context);
    init(context, null);
  }

  public FontTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) return;

    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

      int typefaceId = ta.getInt(R.styleable.FontTextView_fontface, AVENIR_NEXT_REGULAR);
      setTypeface(FontCache.get(context, typefaceId));

      if (getLetterSpacing() == 0) {
        // Assume then that no letter spacing was set, safe to do our own setting.
        initDefaults();
      }

      ta.recycle();
    }
  }

  private void initDefaults() {
    setLetterSpacing(-0.05f);
  }

}
