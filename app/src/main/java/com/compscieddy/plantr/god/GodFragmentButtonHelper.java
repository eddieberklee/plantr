package com.compscieddy.plantr.god;

import android.view.View;

public class GodFragmentButtonHelper {

  public static void setGodFragmentButtonSelectedState(boolean isSelected, View godFragmentButton) {
    final float SELECTED_ALPHA = 1.0f;
    final float UNSELECTED_ALPHA = 0.5f;
    final float SELECTED_SCALE = 1.2f;
    final float UNSELECTED_SCALE = 1.0f;

    godFragmentButton.setAlpha(isSelected ? SELECTED_ALPHA : UNSELECTED_ALPHA);
    godFragmentButton.animate()
        .scaleX(isSelected ? SELECTED_SCALE : UNSELECTED_SCALE)
        .scaleY(isSelected ? SELECTED_SCALE : UNSELECTED_SCALE);
  }

}
