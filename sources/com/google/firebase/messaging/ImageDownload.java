package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ImageDownload implements Closeable {
    private static final int MAX_IMAGE_SIZE_BYTES = 1048576;
    @Nullable
    private volatile Future<?> future;
    @Nullable
    private Task<Bitmap> task;
    private final URL url;

    @Nullable
    public static ImageDownload create(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }
        try {
            return new ImageDownload(new URL(imageUrl));
        } catch (MalformedURLException e) {
            Log.w(Constants.TAG, "Not downloading image, bad URL: " + imageUrl);
            return null;
        }
    }

    private ImageDownload(URL url2) {
        this.url = url2;
    }

    public void start(ExecutorService executor) {
        TaskCompletionSource<Bitmap> taskCompletionSource = new TaskCompletionSource<>();
        this.future = executor.submit(new w(this, taskCompletionSource));
        this.task = taskCompletionSource.getTask();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$start$0 */
    public /* synthetic */ void a(TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(blockingDownload());
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    public Task<Bitmap> getTask() {
        return (Task) Preconditions.checkNotNull(this.task);
    }

    public Bitmap blockingDownload() {
        if (Log.isLoggable(Constants.TAG, 4)) {
            Log.i(Constants.TAG, "Starting download of: " + this.url);
        }
        byte[] imageBytes = blockingDownloadBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        if (bitmap != null) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Successfully downloaded image: " + this.url);
            }
            return bitmap;
        }
        throw new IOException("Failed to decode image: " + this.url);
    }

    private byte[] blockingDownloadBytes() {
        URLConnection connection = this.url.openConnection();
        if (connection.getContentLength() <= 1048576) {
            InputStream connectionInputStream = connection.getInputStream();
            try {
                byte[] bytes = ByteStreams.toByteArray(ByteStreams.limit(connectionInputStream, 1048577));
                if (connectionInputStream != null) {
                    connectionInputStream.close();
                }
                if (Log.isLoggable(Constants.TAG, 2)) {
                    Log.v(Constants.TAG, "Downloaded " + bytes.length + " bytes from " + this.url);
                }
                if (bytes.length <= 1048576) {
                    return bytes;
                }
                throw new IOException("Image exceeds max size of 1048576");
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        } else {
            throw new IOException("Content-Length exceeds max size of 1048576");
        }
        throw th;
    }

    public void close() {
        this.future.cancel(true);
    }
}
