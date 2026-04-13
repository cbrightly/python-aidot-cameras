package com.didichuxing.doraemonkit.aop.bigimg.fresco;

import android.net.Uri;
import com.facebook.imagepipeline.request.Postprocessor;

public class FrescoHook {
    public static Postprocessor proxy(Uri uri, Postprocessor postprocessor) {
        return new DokitFrescoPostprocessor(uri, postprocessor);
    }
}
