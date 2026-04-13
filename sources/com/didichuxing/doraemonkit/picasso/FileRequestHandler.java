package com.didichuxing.doraemonkit.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;
import com.didichuxing.doraemonkit.picasso.RequestHandler;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

public class FileRequestHandler extends ContentStreamRequestHandler {
    FileRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request data) {
        return "file".equals(data.uri.getScheme());
    }

    public RequestHandler.Result load(Request request, int networkPolicy) {
        return new RequestHandler.Result((Bitmap) null, getInputStream(request), DokitPicasso.LoadedFrom.DISK, getFileExifRotation(request.uri));
    }

    static int getFileExifRotation(Uri uri) {
        switch (new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1)) {
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                return 0;
        }
    }
}
