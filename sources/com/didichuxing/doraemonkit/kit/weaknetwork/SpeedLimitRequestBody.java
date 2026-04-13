package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.os.SystemClock;
import okhttp3.c0;
import okhttp3.x;
import okio.b0;
import okio.c;
import okio.d;
import okio.j;

public class SpeedLimitRequestBody extends c0 {
    private d mBufferedSink;
    private c0 mRequestBody;
    /* access modifiers changed from: private */
    public long mSpeedByte;

    public SpeedLimitRequestBody(long speed, c0 source) {
        this.mRequestBody = source;
        this.mSpeedByte = 1024 * speed;
    }

    public x contentType() {
        return this.mRequestBody.contentType();
    }

    public long contentLength() {
        return this.mRequestBody.contentLength();
    }

    public void writeTo(d sink) {
        if (this.mBufferedSink == null) {
            this.mBufferedSink = new ByteCountBufferedSink(sink(sink), 1024);
        }
        this.mRequestBody.writeTo(this.mBufferedSink);
        this.mBufferedSink.close();
    }

    private b0 sink(d sink) {
        return new j(sink) {
            private long cacheStartTime;
            private long cacheTotalBytesWritten;

            public void write(c source, long byteCount) {
                if (this.cacheStartTime == 0) {
                    this.cacheStartTime = SystemClock.uptimeMillis();
                }
                super.write(source, byteCount);
                this.cacheTotalBytesWritten += byteCount;
                long endTime = SystemClock.uptimeMillis() - this.cacheStartTime;
                if (endTime <= 1000 && this.cacheTotalBytesWritten >= SpeedLimitRequestBody.this.mSpeedByte) {
                    SystemClock.sleep(1000 - endTime);
                    this.cacheStartTime = 0;
                    this.cacheTotalBytesWritten = 0;
                }
            }
        };
    }
}
