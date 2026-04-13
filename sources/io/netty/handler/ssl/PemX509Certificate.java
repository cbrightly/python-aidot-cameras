package io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

public final class PemX509Certificate extends X509Certificate implements PemEncoded {
    private static final byte[] BEGIN_CERT;
    private static final byte[] END_CERT;
    private final ByteBuf content;

    static {
        Charset charset = CharsetUtil.US_ASCII;
        BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n".getBytes(charset);
        END_CERT = "\n-----END CERTIFICATE-----\n".getBytes(charset);
    }

    static PemEncoded toPEM(ByteBufAllocator allocator, boolean useDirect, X509Certificate... chain) {
        if (chain == null || chain.length == 0) {
            throw new IllegalArgumentException("X.509 certificate chain can't be null or empty");
        }
        if (chain.length == 1) {
            X509Certificate first = chain[0];
            if (first instanceof PemEncoded) {
                return ((PemEncoded) first).retain();
            }
        }
        X509Certificate success = null;
        ByteBuf pem = null;
        try {
            int length = chain.length;
            int i = 0;
            while (i < length) {
                X509Certificate cert = chain[i];
                if (cert != null) {
                    if (cert instanceof PemEncoded) {
                        pem = append(allocator, useDirect, (PemEncoded) cert, chain.length, pem);
                    } else {
                        pem = append(allocator, useDirect, cert, chain.length, pem);
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Null element in chain: " + Arrays.toString(chain));
                }
            }
            success = true;
            return new PemValue(pem, false);
        } finally {
            if (success == null && pem != null) {
                pem.release();
            }
        }
    }

    private static ByteBuf append(ByteBufAllocator allocator, boolean useDirect, PemEncoded encoded, int count, ByteBuf pem) {
        ByteBuf content2 = encoded.content();
        if (pem == null) {
            pem = newBuffer(allocator, useDirect, content2.readableBytes() * count);
        }
        pem.writeBytes(content2.slice());
        return pem;
    }

    private static ByteBuf append(ByteBufAllocator allocator, boolean useDirect, X509Certificate cert, int count, ByteBuf pem) {
        ByteBuf base64 = Unpooled.wrappedBuffer(cert.getEncoded());
        try {
            base64 = SslUtils.toBase64(allocator, base64);
            if (pem == null) {
                pem = newBuffer(allocator, useDirect, (BEGIN_CERT.length + base64.readableBytes() + END_CERT.length) * count);
            }
            pem.writeBytes(BEGIN_CERT);
            pem.writeBytes(base64);
            pem.writeBytes(END_CERT);
            base64.release();
            return pem;
        } catch (Throwable th) {
            throw th;
        } finally {
            base64.release();
        }
    }

    private static ByteBuf newBuffer(ByteBufAllocator allocator, boolean useDirect, int initialCapacity) {
        return useDirect ? allocator.directBuffer(initialCapacity) : allocator.buffer(initialCapacity);
    }

    public static PemX509Certificate valueOf(byte[] key) {
        return valueOf(Unpooled.wrappedBuffer(key));
    }

    public static PemX509Certificate valueOf(ByteBuf key) {
        return new PemX509Certificate(key);
    }

    private PemX509Certificate(ByteBuf content2) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content2, FirebaseAnalytics.Param.CONTENT);
    }

    public boolean isSensitive() {
        return false;
    }

    public int refCnt() {
        return this.content.refCnt();
    }

    public ByteBuf content() {
        int count = refCnt();
        if (count > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(count);
    }

    public PemX509Certificate copy() {
        return new PemX509Certificate(this.content.copy());
    }

    public PemX509Certificate duplicate() {
        return new PemX509Certificate(this.content.duplicate());
    }

    public PemX509Certificate retain() {
        this.content.retain();
        return this;
    }

    public PemX509Certificate retain(int increment) {
        this.content.retain(increment);
        return this;
    }

    public boolean release() {
        return this.content.release();
    }

    public boolean release(int decrement) {
        return this.content.release(decrement);
    }

    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    public boolean hasUnsupportedCriticalExtension() {
        throw new UnsupportedOperationException();
    }

    public Set<String> getCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    public Set<String> getNonCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    public byte[] getExtensionValue(String oid) {
        throw new UnsupportedOperationException();
    }

    public void checkValidity() {
        throw new UnsupportedOperationException();
    }

    public void checkValidity(Date date) {
        throw new UnsupportedOperationException();
    }

    public int getVersion() {
        throw new UnsupportedOperationException();
    }

    public BigInteger getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    public Principal getIssuerDN() {
        throw new UnsupportedOperationException();
    }

    public Principal getSubjectDN() {
        throw new UnsupportedOperationException();
    }

    public Date getNotBefore() {
        throw new UnsupportedOperationException();
    }

    public Date getNotAfter() {
        throw new UnsupportedOperationException();
    }

    public byte[] getTBSCertificate() {
        throw new UnsupportedOperationException();
    }

    public byte[] getSignature() {
        throw new UnsupportedOperationException();
    }

    public String getSigAlgName() {
        throw new UnsupportedOperationException();
    }

    public String getSigAlgOID() {
        throw new UnsupportedOperationException();
    }

    public byte[] getSigAlgParams() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getIssuerUniqueID() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getSubjectUniqueID() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getKeyUsage() {
        throw new UnsupportedOperationException();
    }

    public int getBasicConstraints() {
        throw new UnsupportedOperationException();
    }

    public void verify(PublicKey key) {
        throw new UnsupportedOperationException();
    }

    public void verify(PublicKey key, String sigProvider) {
        throw new UnsupportedOperationException();
    }

    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PemX509Certificate)) {
            return false;
        }
        return this.content.equals(((PemX509Certificate) o).content);
    }

    public int hashCode() {
        return this.content.hashCode();
    }

    public String toString() {
        return this.content.toString(CharsetUtil.UTF_8);
    }
}
