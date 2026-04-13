package com.didichuxing.doraemonkit.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;

public class GetAction extends Action<Void> {
    GetAction(DokitPicasso picasso, Request data, int memoryPolicy, int networkPolicy, Object tag, String key) {
        super(picasso, null, data, memoryPolicy, networkPolicy, 0, (Drawable) null, key, tag, false);
    }

    /* access modifiers changed from: package-private */
    public void complete(Bitmap result, DokitPicasso.LoadedFrom from) {
    }

    public void error() {
    }
}
