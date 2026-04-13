package com.utils;

import android.media.MediaScannerConnection;
import android.net.Uri;

/* compiled from: lambda */
public final /* synthetic */ class a implements MediaScannerConnection.OnScanCompletedListener {
    public static final /* synthetic */ a a = new a();

    private /* synthetic */ a() {
    }

    public final void onScanCompleted(String str, Uri uri) {
        b.d(str, uri);
    }
}
