package com.didichuxing.doraemonkit.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;
import com.didichuxing.doraemonkit.picasso.RequestHandler;

public class ResourceRequestHandler extends RequestHandler {
    private final Context context;

    ResourceRequestHandler(Context context2) {
        this.context = context2;
    }

    public boolean canHandleRequest(Request data) {
        if (data.resourceId != 0) {
            return true;
        }
        return "android.resource".equals(data.uri.getScheme());
    }

    public RequestHandler.Result load(Request request, int networkPolicy) {
        Resources res = Utils.getResources(this.context, request);
        return new RequestHandler.Result(decodeResource(res, Utils.getResourceId(res, request), request), DokitPicasso.LoadedFrom.DISK);
    }

    private static Bitmap decodeResource(Resources resources, int id, Request data) {
        BitmapFactory.Options options = RequestHandler.createBitmapOptions(data);
        if (RequestHandler.requiresInSampleSize(options)) {
            BitmapFactory.decodeResource(resources, id, options);
            RequestHandler.calculateInSampleSize(data.targetWidth, data.targetHeight, options, data);
        }
        return BitmapFactory.decodeResource(resources, id, options);
    }
}
