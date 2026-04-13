package com.didichuxing.doraemonkit.okgo.request.base;

import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;
import java.io.IOException;
import okhttp3.c0;
import okhttp3.x;
import okio.b0;
import okio.c;
import okio.d;
import okio.j;
import okio.p;

public class ProgressRequestBody<T> extends c0 {
    /* access modifiers changed from: private */
    public Callback<T> callback;
    /* access modifiers changed from: private */
    public UploadInterceptor interceptor;
    private c0 requestBody;

    public interface UploadInterceptor {
        void uploadProgress(Progress progress);
    }

    ProgressRequestBody(c0 requestBody2, Callback<T> callback2) {
        this.requestBody = requestBody2;
        this.callback = callback2;
    }

    public x contentType() {
        return this.requestBody.contentType();
    }

    public long contentLength() {
        try {
            return this.requestBody.contentLength();
        } catch (IOException e) {
            OkLogger.printStackTrace(e);
            return -1;
        }
    }

    public void writeTo(d sink) {
        d bufferedSink = p.c(new CountingSink(sink));
        this.requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    public final class CountingSink extends j {
        private Progress progress;

        CountingSink(b0 delegate) {
            super(delegate);
            Progress progress2 = new Progress();
            this.progress = progress2;
            progress2.totalSize = ProgressRequestBody.this.contentLength();
        }

        public void write(c source, long byteCount) {
            super.write(source, byteCount);
            Progress.changeProgress(this.progress, byteCount, new Progress.Action() {
                public void call(Progress progress) {
                    if (ProgressRequestBody.this.interceptor != null) {
                        ProgressRequestBody.this.interceptor.uploadProgress(progress);
                    } else {
                        ProgressRequestBody.this.onProgress(progress);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void onProgress(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            public void run() {
                if (ProgressRequestBody.this.callback != null) {
                    ProgressRequestBody.this.callback.uploadProgress(progress);
                }
            }
        });
    }

    public void setInterceptor(UploadInterceptor interceptor2) {
        this.interceptor = interceptor2;
    }
}
