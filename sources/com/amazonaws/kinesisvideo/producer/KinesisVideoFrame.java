package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import java.nio.ByteBuffer;
import java.util.Objects;

public class KinesisVideoFrame {
    private final ByteBuffer mData;
    private final long mDecodingTs;
    private final long mDuration;
    private final int mFlags;
    private final int mIndex;
    private final long mPresentationTs;

    public KinesisVideoFrame(int index, int flags, long decodingTs, long presentationTs, long duration, @NonNull ByteBuffer data) {
        this.mIndex = index;
        this.mFlags = flags;
        this.mDecodingTs = decodingTs;
        this.mPresentationTs = presentationTs;
        this.mDuration = duration;
        Objects.requireNonNull(data);
        this.mData = data;
        removeTrailingZeros();
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public long getDecodingTs() {
        return this.mDecodingTs;
    }

    public long getPresentationTs() {
        return this.mPresentationTs;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public int getSize() {
        return this.mData.remaining();
    }

    @NonNull
    public ByteBuffer getData() {
        ByteBuffer byteBuffer = this.mData;
        try {
            if (!this.mData.hasArray()) {
                return byteBuffer;
            }
            ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(this.mData.remaining());
            byteBuffer2.put(this.mData);
            return byteBuffer2;
        } catch (Exception e) {
            return byteBuffer;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + "{" + "mIndex=" + this.mIndex + ", mFlags=" + this.mFlags + ", mDecodingTs=" + this.mDecodingTs + ", mPresentationTs=" + this.mPresentationTs + ", mDuration=" + this.mDuration + ", mData=" + this.mData + "}";
    }

    private void removeTrailingZeros() {
        int index = this.mData.limit();
        while (true) {
            index--;
            if (index <= this.mData.position()) {
                return;
            }
            if (this.mData.get(index) != 0) {
                this.mData.limit(index + 1);
                return;
            }
        }
    }
}
