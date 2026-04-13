package io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.nio.charset.Charset;
import java.security.PrivateKey;

public final class PemPrivateKey extends AbstractReferenceCounted implements PrivateKey, PemEncoded {
    private static final byte[] BEGIN_PRIVATE_KEY;
    private static final byte[] END_PRIVATE_KEY;
    private static final String PKCS8_FORMAT = "PKCS#8";
    private static final long serialVersionUID = 7978017465645018936L;
    private final ByteBuf content;

    static {
        Charset charset = CharsetUtil.US_ASCII;
        BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(charset);
        END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(charset);
    }

    static PemEncoded toPEM(ByteBufAllocator allocator, boolean useDirect, PrivateKey key) {
        if (key instanceof PemEncoded) {
            return ((PemEncoded) key).retain();
        }
        ByteBuf pem = Unpooled.wrappedBuffer(key.getEncoded());
        try {
            ByteBuf base64 = SslUtils.toBase64(allocator, pem);
            try {
                byte[] bArr = BEGIN_PRIVATE_KEY;
                int length = bArr.length + base64.readableBytes();
                byte[] bArr2 = END_PRIVATE_KEY;
                int size = length + bArr2.length;
                pem = useDirect ? allocator.directBuffer(size) : allocator.buffer(size);
                pem.writeBytes(bArr);
                pem.writeBytes(base64);
                pem.writeBytes(bArr2);
                PemValue value = new PemValue(pem, true);
                if (1 == 0) {
                }
                SslUtils.zerooutAndRelease(base64);
                SslUtils.zerooutAndRelease(pem);
                return value;
            } catch (Throwable th) {
                SslUtils.zerooutAndRelease(base64);
                throw th;
            }
        } finally {
            SslUtils.zerooutAndRelease(pem);
        }
    }

    public static PemPrivateKey valueOf(byte[] key) {
        return valueOf(Unpooled.wrappedBuffer(key));
    }

    public static PemPrivateKey valueOf(ByteBuf key) {
        return new PemPrivateKey(key);
    }

    private PemPrivateKey(ByteBuf content2) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content2, FirebaseAnalytics.Param.CONTENT);
    }

    public boolean isSensitive() {
        return true;
    }

    public ByteBuf content() {
        int count = refCnt();
        if (count > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(count);
    }

    public PemPrivateKey copy() {
        return new PemPrivateKey(this.content.copy());
    }

    public PemPrivateKey duplicate() {
        return new PemPrivateKey(this.content.duplicate());
    }

    public PemPrivateKey retain() {
        return (PemPrivateKey) super.retain();
    }

    public PemPrivateKey retain(int increment) {
        return (PemPrivateKey) super.retain(increment);
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        SslUtils.zerooutAndRelease(this.content);
    }

    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    public String getFormat() {
        return PKCS8_FORMAT;
    }

    public void destroy() {
        release(refCnt());
    }

    public boolean isDestroyed() {
        return refCnt() == 0;
    }
}
