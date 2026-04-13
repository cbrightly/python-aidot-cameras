package com.leedarson.serviceimpl.zendesk;

import android.app.Activity;
import android.webkit.WebChromeClient;
import java.lang.ref.WeakReference;

/* compiled from: FileSupportWebChromeClient */
public abstract class a extends WebChromeClient {
    private String a = a.class.getSimpleName();
    private WeakReference<Activity> b;

    public a(Activity activity) {
        this.b = new WeakReference<>(activity);
    }
}
