package com.didichuxing.doraemonkit.widget.verticalviewpager.transforms;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class DefaultTransformer implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {
        float alpha = 0.0f;
        if (0.0f <= position && position <= 1.0f) {
            alpha = 1.0f - position;
        } else if (-1.0f < position && position < 0.0f) {
            alpha = position + 1.0f;
        }
        view.setAlpha(alpha);
        view.setTranslationX(((float) view.getWidth()) * (-position));
        view.setTranslationY(((float) view.getHeight()) * position);
    }
}
