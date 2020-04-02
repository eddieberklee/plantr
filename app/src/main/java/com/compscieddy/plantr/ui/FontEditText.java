package com.compscieddy.plantr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.compscieddy.plantr.R;
import com.vanniktech.emoji.EmojiEditText;

import static com.compscieddy.plantr.ui.FontCache.MONTSERRAT_REGULAR;

/**
 * Created by elee on 1/7/16.
 */
public class FontEditText extends EmojiEditText {

  public FontEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) return;

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontEditText);
    int typefaceId = ta.getInt(R.styleable.FontEditText_fontface, MONTSERRAT_REGULAR);
    setTypeface(FontCache.get(context, typefaceId));
    ta.recycle();
  }

}
