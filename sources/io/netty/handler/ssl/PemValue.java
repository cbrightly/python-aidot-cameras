package io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

public class PemValue extends AbstractReferenceCounted implements PemEncoded {
    private final ByteBuf content;
    private final boolean sensitive;

    public PemValue(ByteBuf content2, boolean sensitive2) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content2, FirebaseAnalytics.Param.CONTENT);
        this.sensitive = sensitive2;
    }

    public boolean isSensitive() {
        return this.sensitive;
    }

    public ByteBuf content() {
        int count = refCnt();
        if (count > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(count);
    }

    public PemValue copy() {
        return new PemValue(this.content.copy(), this.sensitive);
    }

    public PemValue duplicate() {
        return new PemValue(this.content.duplicate(), this.sensitive);
    }

    public PemValue retain() {
        return (PemValue) super.retain();
    }

    public PemValue retain(int increment) {
        return (PemValue) super.retain(increment);
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        if (this.sensitive) {
            SslUtils.zeroout(this.content);
        }
        this.content.release();
    }
}
