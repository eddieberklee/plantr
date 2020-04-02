package com.compscieddy.workoutfh.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.compscieddy.eddie_utils.etil.ColorEtil;
import com.compscieddy.workoutfh.R;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by elee on 1/25/16.
 */
public class ColorImageView extends AppCompatImageView {

  /** I love StackOverflow
   *  http://stackoverflow.com/questions/35003312/am-i-applying-the-colorfilter-in-the-right-place-in-my-custom-imageview
   *  except the answer didn't work so hopefully someone adds another answer soon
   */

  private int mColor = -1;

  public ColorImageView(Context context) {
    super(context);
  }
  public ColorImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorImageView);
    mColor = ta.getColor(R.styleable.ColorImageView_customColor, -1);

    colorDrawable(getDrawable());

    ta.recycle();
  }

  @Override
  public void setImageDrawable(Drawable drawable) {
    super.setImageDrawable(drawable);
    if (mColor != -1) {
      colorDrawable(getDrawable());
    }
  }

  @Override
  public void setImageResource(int resId) {
    super.setImageResource(resId);
    colorDrawable(getDrawable());
  }

  private void colorDrawable(Drawable drawable) {
    if (drawable != null) {
      ColorEtil.applyColorFilter(drawable, mColor, true);
    }
  }

  public void setCustomColor(int color) {
    Drawable d = getDrawable();
    if (d != null) {
      ColorEtil.applyColorFilter(d, color, true);
    }
  }

}
