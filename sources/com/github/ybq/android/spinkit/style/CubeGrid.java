package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import meshsdk.BaseResp;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class CubeGrid extends SpriteContainer {
    public Sprite[] onCreateChild() {
        int[] delays = {200, IjkMediaCodecInfo.RANK_SECURE, BaseResp.ERR_MSG_SEND_FAIL, 100, 200, IjkMediaCodecInfo.RANK_SECURE, 0, 100, 200};
        GridItem[] gridItems = new GridItem[9];
        for (int i = 0; i < gridItems.length; i++) {
            gridItems[i] = new GridItem();
            gridItems[i].setAnimationDelay(delays[i]);
        }
        return gridItems;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect bounds2 = clipSquare(bounds);
        int width = (int) (((float) bounds2.width()) * 0.33f);
        int height = (int) (((float) bounds2.height()) * 0.33f);
        for (int i = 0; i < getChildCount(); i++) {
            int l = bounds2.left + ((i % 3) * width);
            int t = bounds2.top + ((i / 3) * height);
            getChildAt(i).setDrawBounds(l, t, l + width, t + height);
        }
    }

    public class GridItem extends RectSprite {
        private GridItem() {
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.35f, 0.7f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(1.0f);
            return spriteAnimatorBuilder.scale(fractions, valueOf, Float.valueOf(0.0f), valueOf, valueOf).duration(1300).easeInOut(fractions).build();
        }
    }
}
