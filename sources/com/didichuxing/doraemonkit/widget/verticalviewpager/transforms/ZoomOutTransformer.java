package com.didichuxing.doraemonkit.widget.verticalviewpager.transforms;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class ZoomOutTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float alpha = 0.0f;
        if (0.0f <= position && position <= 1.0f) {
            alpha = 1.0f - position;
        } else if (-1.0f < position && position < 0.0f) {
            float scaleFactor = Math.max(MIN_SCALE, 1.0f - Math.abs(position));
            float verticalMargin = (((float) pageHeight) * (1.0f - scaleFactor)) / 2.0f;
            float horizontalMargin = (((float) pageWidth) * (1.0f - scaleFactor)) / 2.0f;
            if (position < 0.0f) {
                view.setTranslationX(horizontalMargin - (verticalMargin / 2.0f));
            } else {
                view.setTranslationX((-horizontalMargin) + (verticalMargin / 2.0f));
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            alpha = position + 1.0f;
        }
        view.setAlpha(alpha);
        view.setTranslationX(((float) view.getWidth()) * (-position));
        view.setTranslationY(((float) view.getHeight()) * position);
    }
}
