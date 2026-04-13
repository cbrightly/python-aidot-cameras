package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.os.SystemClock;
import okhttp3.e0;
import okhttp3.x;
import okio.c;
import okio.d0;
import okio.e;
import okio.g;
import okio.k;
import okio.p;
import org.jetbrains.annotations.Nullable;

public class SpeedLimitResponseBody extends e0 {
    private static String TAG = "SpeedLimitResponseBody";
    private e mBufferedSource;
    private e0 mResponseBody;
    /* access modifiers changed from: private */
    public long mSpeedByte;

    SpeedLimitResponseBody(long speed, e0 source) {
        this.mResponseBody = source;
        this.mSpeedByte = 1024 * speed;
    }

    public x contentType() {
        return this.mResponseBody.contentType();
    }

    public long contentLength() {
        return this.mResponseBody.contentLength();
    }

    public e source() {
        if (this.mBufferedSource == null) {
            this.mBufferedSource = p.d(source(this.mResponseBody.source()));
        }
        return this.mBufferedSource;
    }

    private okio.e0 source(okio.e0 source) {
        return new k(source) {
            private long cacheStartTime;
            private long cacheTotalBytesRead;

            @Nullable
            public /* bridge */ /* synthetic */ g cursor() {
                return d0.a(this);
            }

            public long read(c sink, long byteCount) {
                if (this.cacheStartTime == 0) {
                    this.cacheStartTime = SystemClock.uptimeMillis();
                }
                long bytesRead = super.read(sink.buffer(), 1024);
                if (bytesRead == -1) {
                    return bytesRead;
                }
                this.cacheTotalBytesRead += bytesRead;
                long costTime = SystemClock.uptimeMillis() - this.cacheStartTime;
                if (costTime <= 1000 && this.cacheTotalBytesRead >= SpeedLimitResponseBody.this.mSpeedByte) {
                    SystemClock.sleep(1000 - costTime);
                    this.cacheStartTime = 0;
                    this.cacheTotalBytesRead = 0;
                }
                return bytesRead;
            }
        };
    }
}
