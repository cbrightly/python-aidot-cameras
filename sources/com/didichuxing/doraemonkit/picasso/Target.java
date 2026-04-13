package com.didichuxing.doraemonkit.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;

public interface Target {
    void onBitmapFailed(Drawable drawable);

    void onBitmapLoaded(Bitmap bitmap, DokitPicasso.LoadedFrom loadedFrom);

    void onPrepareLoad(Drawable drawable);
}
