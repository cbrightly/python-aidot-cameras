package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.ImageUtil;
import java.io.File;
import java.lang.ref.WeakReference;

public class ImageDetailFragment extends BaseFragment {
    private static final String TAG = "ImageDetailFragment";
    private File mFile;
    /* access modifiers changed from: private */
    public ImageView mImageView;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mImageView = (ImageView) findViewById(R.id.image);
        Bundle data = getArguments();
        if (data != null) {
            this.mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
        readImage(this.mFile);
    }

    private void readImage(File file) {
        if (file != null) {
            new ImageReadTask(this).execute(new File[]{file});
        }
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_image_detail;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mImageView.setImageBitmap((Bitmap) null);
    }

    public static class ImageReadTask extends AsyncTask<File, Void, Bitmap> {
        private WeakReference<ImageDetailFragment> mReference;

        public ImageReadTask(ImageDetailFragment fragment) {
            this.mReference = new WeakReference<>(fragment);
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(File... files) {
            return ImageUtil.decodeSampledBitmapFromFilePath(files[0].getPath(), 1080, 1920);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (this.mReference.get() != null) {
                ((ImageDetailFragment) this.mReference.get()).mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
