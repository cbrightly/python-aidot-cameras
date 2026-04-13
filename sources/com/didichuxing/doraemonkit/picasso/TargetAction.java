package com.didichuxing.doraemonkit.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;

public final class TargetAction extends Action<Target> {
    TargetAction(DokitPicasso picasso, Target target, Request data, int memoryPolicy, int networkPolicy, Drawable errorDrawable, String key, Object tag, int errorResId) {
        super(picasso, target, data, memoryPolicy, networkPolicy, errorResId, errorDrawable, key, tag, false);
    }

    /* access modifiers changed from: package-private */
    public void complete(Bitmap result, DokitPicasso.LoadedFrom from) {
        if (result != null) {
            Target target = (Target) getTarget();
            if (target != null) {
                target.onBitmapLoaded(result, from);
                if (result.isRecycled()) {
                    throw new IllegalStateException("Target callback must not recycle bitmap!");
                }
                return;
            }
            return;
        }
        throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
    }

    /* access modifiers changed from: package-private */
    public void error() {
        Target target = (Target) getTarget();
        if (target == null) {
            return;
        }
        if (this.errorResId != 0) {
            target.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
        } else {
            target.onBitmapFailed(this.errorDrawable);
        }
    }
}
