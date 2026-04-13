package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.alibaba.fastjson.asm.Opcodes;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.github.ybq.android.spinkit.sprite.RingSprite;

public class PulseRing extends RingSprite {
    public PulseRing() {
        setScale(0.0f);
    }

    public ValueAnimator onCreateAnimation() {
        float[] fractions = {0.0f, 0.7f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        Float valueOf = Float.valueOf(1.0f);
        return spriteAnimatorBuilder.scale(fractions, Float.valueOf(0.0f), valueOf, valueOf).alpha(fractions, 255, Integer.valueOf(Opcodes.GETSTATIC), 0).duration(1000).interpolator(KeyFrameInterpolator.pathInterpolator(0.21f, 0.53f, 0.56f, 0.8f, fractions)).build();
    }
}
