package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleLayoutContainer;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;

public class FadingCircle extends CircleLayoutContainer {
    public Sprite[] onCreateChild() {
        Dot[] dots = new Dot[12];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new Dot();
            if (Build.VERSION.SDK_INT >= 24) {
                dots[i].setAnimationDelay(i * 100);
            } else {
                dots[i].setAnimationDelay((i * 100) - 1200);
            }
        }
        return dots;
    }

    public class Dot extends CircleSprite {
        Dot() {
            setAlpha(0);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.39f, 0.4f, 1.0f};
            return new SpriteAnimatorBuilder(this).alpha(fractions, 0, 0, 255, 0).duration(1200).easeInOut(fractions).build();
        }
    }
}
