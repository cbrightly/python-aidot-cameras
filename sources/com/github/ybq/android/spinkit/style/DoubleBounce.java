package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build;
import com.alibaba.fastjson.asm.Opcodes;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import meshsdk.cache.CacheHandler;

public class DoubleBounce extends SpriteContainer {
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Bounce(), new Bounce()};
    }

    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT >= 24) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    public class Bounce extends CircleSprite {
        Bounce() {
            setAlpha(Opcodes.IFEQ);
            setScale(0.0f);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fractions, valueOf, Float.valueOf(1.0f), valueOf).duration(CacheHandler.delayTime).easeInOut(fractions).build();
        }
    }
}
