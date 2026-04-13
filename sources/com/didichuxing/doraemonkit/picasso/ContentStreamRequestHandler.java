package com.didichuxing.doraemonkit.picasso;

import android.content.Context;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;
import com.didichuxing.doraemonkit.picasso.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;

public class ContentStreamRequestHandler extends RequestHandler {
    final Context context;

    ContentStreamRequestHandler(Context context2) {
        this.context = context2;
    }

    public boolean canHandleRequest(Request data) {
        return FirebaseAnalytics.Param.CONTENT.equals(data.uri.getScheme());
    }

    public RequestHandler.Result load(Request request, int networkPolicy) {
        return new RequestHandler.Result(getInputStream(request), DokitPicasso.LoadedFrom.DISK);
    }

    /* access modifiers changed from: package-private */
    public InputStream getInputStream(Request request) {
        return this.context.getContentResolver().openInputStream(request.uri);
    }
}
