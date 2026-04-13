package com.didichuxing.doraemonkit.widget.verticalviewpager.transforms;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class StackTransformer implements ViewPager.PageTransformer {
    public void transformPage(View page, float position) {
        page.setTranslationX(((float) page.getWidth()) * (-position));
        float f = 0.0f;
        if (position < 0.0f) {
            f = ((float) page.getHeight()) * position;
        }
        page.setTranslationY(f);
    }
}
