package com.leedarson.base.views;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.BuildConfig;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: ThreeCircle */
public class h extends SpriteContainer {
    public static ChangeQuickRedirect changeQuickRedirect;

    public Sprite[] onCreateChild() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 810, new Class[0], Sprite[].class);
        if (proxy.isSupported) {
            return (Sprite[]) proxy.result;
        }
        return new Sprite[]{new a(), new a(), new a()};
    }

    public void onChildCreated(Sprite... sprites) {
        if (!PatchProxy.proxy(new Object[]{sprites}, this, changeQuickRedirect, false, 811, new Class[]{Sprite[].class}, Void.TYPE).isSupported) {
            super.onChildCreated(sprites);
            sprites[1].setAnimationDelay(Opcodes.IF_ICMPNE);
            sprites[2].setAnimationDelay(BuildConfig.VERSION_CODE);
        }
    }

    public void onBoundsChange(Rect bounds) {
        if (!PatchProxy.proxy(new Object[]{bounds}, this, changeQuickRedirect, false, 812, new Class[]{Rect.class}, Void.TYPE).isSupported) {
            super.onBoundsChange(bounds);
            Rect bounds2 = clipSquare(bounds);
            int radius = (int) (((double) bounds2.width()) / 15.0d);
            int top = bounds2.centerY() - radius;
            int bottom = bounds2.centerY() + radius;
            Log.e("ThreeCircle", "onBoundsChange: " + bounds2.width() + "==" + bounds2.left);
            for (int i = 0; i < getChildCount(); i++) {
                int left = (int) ((((double) (bounds2.width() * i)) / 3.0d) + ((double) bounds2.left));
                getChildAt(i).setDrawBounds(left, top, (radius * 2) + left, bottom);
            }
        }
    }

    /* compiled from: ThreeCircle */
    public class a extends CircleSprite {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
            setAlpha(0);
        }

        public ValueAnimator onCreateAnimation() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CHECKSUPPORTMESSAGETYPE_RESP, new Class[0], ValueAnimator.class);
            if (proxy.isSupported) {
                return (ValueAnimator) proxy.result;
            }
            float[] fractions = {0.0f, 0.5f, 0.8f, 1.0f};
            return new SpriteAnimatorBuilder(this).alpha(fractions, 255, 0, 0, 0).duration(1400).easeInOut(fractions).build();
        }
    }
}
