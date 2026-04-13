package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class TlsStreamCipher implements TlsCipher {
    protected TlsContext a;
    protected StreamCipher b;
    protected StreamCipher c;
    protected TlsMac d;
    protected TlsMac e;
    protected boolean f;

    public TlsStreamCipher(TlsContext context, StreamCipher clientWriteCipher, StreamCipher serverWriteCipher, Digest clientWriteDigest, Digest serverWriteDigest, int cipherKeySize, boolean usesNonce) {
        CipherParameters decryptParams;
        CipherParameters encryptParams;
        TlsContext tlsContext = context;
        StreamCipher streamCipher = clientWriteCipher;
        StreamCipher streamCipher2 = serverWriteCipher;
        int i = cipherKeySize;
        boolean z = usesNonce;
        boolean isServer = context.g();
        this.a = tlsContext;
        this.f = z;
        this.b = streamCipher;
        this.c = streamCipher2;
        int key_block_size = (i * 2) + clientWriteDigest.e() + serverWriteDigest.e();
        byte[] key_block = TlsUtils.c(tlsContext, key_block_size);
        TlsContext tlsContext2 = context;
        byte[] bArr = key_block;
        TlsMac clientWriteMac = new TlsMac(tlsContext2, clientWriteDigest, bArr, 0, clientWriteDigest.e());
        int offset = 0 + clientWriteDigest.e();
        TlsMac clientWriteMac2 = clientWriteMac;
        TlsMac serverWriteMac = new TlsMac(tlsContext2, serverWriteDigest, bArr, offset, serverWriteDigest.e());
        int offset2 = offset + serverWriteDigest.e();
        CipherParameters clientWriteKey = new KeyParameter(key_block, offset2, i);
        int offset3 = offset2 + i;
        CipherParameters serverWriteKey = new KeyParameter(key_block, offset3, i);
        if (offset3 + i == key_block_size) {
            if (isServer) {
                this.d = serverWriteMac;
                this.e = clientWriteMac2;
                this.b = streamCipher2;
                this.c = streamCipher;
                encryptParams = serverWriteKey;
                decryptParams = clientWriteKey;
            } else {
                this.d = clientWriteMac2;
                this.e = serverWriteMac;
                this.b = streamCipher;
                this.c = streamCipher2;
                encryptParams = clientWriteKey;
                decryptParams = serverWriteKey;
            }
            if (z) {
                byte[] dummyNonce = new byte[8];
                TlsMac tlsMac = serverWriteMac;
                encryptParams = new ParametersWithIV(encryptParams, dummyNonce);
                decryptParams = new ParametersWithIV(decryptParams, dummyNonce);
            }
            this.b.a(true, encryptParams);
            this.c.a(false, decryptParams);
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] b(long seqNo, short type, byte[] plaintext, int offset, int len) {
        if (this.f) {
            long j = seqNo;
            d(this.b, true, seqNo);
        } else {
            long j2 = seqNo;
        }
        byte[] outBuf = new byte[(len + this.d.d())];
        this.b.d(plaintext, offset, len, outBuf, 0);
        byte[] mac = this.d.a(seqNo, type, plaintext, offset, len);
        this.b.d(mac, 0, mac.length, outBuf, len);
        return outBuf;
    }

    public byte[] a(long seqNo, short type, byte[] ciphertext, int offset, int len) {
        int i = len;
        if (this.f) {
            d(this.c, false, seqNo);
        } else {
            long j = seqNo;
        }
        int macSize = this.e.d();
        if (i >= macSize) {
            int plaintextLength = i - macSize;
            byte[] deciphered = new byte[i];
            byte[] bArr = deciphered;
            this.c.d(ciphertext, offset, len, bArr, 0);
            c(seqNo, type, bArr, plaintextLength, len, deciphered, 0, plaintextLength);
            return Arrays.B(deciphered, 0, plaintextLength);
        }
        throw new TlsFatalAlert(50);
    }

    /* access modifiers changed from: protected */
    public void c(long seqNo, short type, byte[] recBuf, int recStart, int recEnd, byte[] calcBuf, int calcOff, int calcLen) {
        if (!Arrays.u(Arrays.B(recBuf, recStart, recEnd), this.e.a(seqNo, type, calcBuf, calcOff, calcLen))) {
            throw new TlsFatalAlert(20);
        }
    }

    /* access modifiers changed from: protected */
    public void d(StreamCipher cipher, boolean forEncryption, long seqNo) {
        byte[] nonce = new byte[8];
        TlsUtils.I0(seqNo, nonce, 0);
        cipher.a(forEncryption, new ParametersWithIV((CipherParameters) null, nonce));
    }
}
