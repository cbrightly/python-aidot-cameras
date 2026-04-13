package com.didichuxing.doraemonkit.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;
import com.didichuxing.doraemonkit.picasso.RequestHandler;

public class AssetRequestHandler extends RequestHandler {
    protected static final String ANDROID_ASSET = "android_asset";
    private static final int ASSET_PREFIX_LENGTH = "file:///android_asset/".length();
    private final AssetManager assetManager;

    public AssetRequestHandler(Context context) {
        this.assetManager = context.getAssets();
    }

    public boolean canHandleRequest(Request data) {
        Uri uri = data.uri;
        if (!"file".equals(uri.getScheme()) || uri.getPathSegments().isEmpty() || !ANDROID_ASSET.equals(uri.getPathSegments().get(0))) {
            return false;
        }
        return true;
    }

    public RequestHandler.Result load(Request request, int networkPolicy) {
        return new RequestHandler.Result(this.assetManager.open(getFilePath(request)), DokitPicasso.LoadedFrom.DISK);
    }

    static String getFilePath(Request request) {
        return request.uri.toString().substring(ASSET_PREFIX_LENGTH);
    }
}
