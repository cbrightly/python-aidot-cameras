package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;

public class TlsNullCipher implements TlsCipher {
    protected TlsContext a;
    protected TlsMac b;
    protected TlsMac c;

    public TlsNullCipher(TlsContext context, Digest clientWriteDigest, Digest serverWriteDigest) {
        TlsContext tlsContext = context;
        if ((clientWriteDigest == null) == (serverWriteDigest != null ? false : true)) {
            this.a = tlsContext;
            TlsMac clientWriteMac = null;
            TlsMac serverWriteMac = null;
            if (clientWriteDigest != null) {
                int key_block_size = clientWriteDigest.e() + serverWriteDigest.e();
                TlsContext tlsContext2 = context;
                byte[] c2 = TlsUtils.c(tlsContext, key_block_size);
                clientWriteMac = new TlsMac(tlsContext2, clientWriteDigest, c2, 0, clientWriteDigest.e());
                int offset = 0 + clientWriteDigest.e();
                serverWriteMac = new TlsMac(tlsContext2, serverWriteDigest, c2, offset, serverWriteDigest.e());
                if (offset + serverWriteDigest.e() != key_block_size) {
                    throw new TlsFatalAlert(80);
                }
            }
            if (context.g()) {
                this.b = serverWriteMac;
                this.c = clientWriteMac;
                return;
            }
            this.b = clientWriteMac;
            this.c = serverWriteMac;
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] b(long seqNo, short type, byte[] plaintext, int offset, int len) {
        TlsMac tlsMac = this.b;
        if (tlsMac == null) {
            return Arrays.B(plaintext, offset, offset + len);
        }
        byte[] mac = tlsMac.a(seqNo, type, plaintext, offset, len);
        byte[] ciphertext = new byte[(mac.length + len)];
        System.arraycopy(plaintext, offset, ciphertext, 0, len);
        System.arraycopy(mac, 0, ciphertext, len, mac.length);
        return ciphertext;
    }

    public byte[] a(long seqNo, short type, byte[] ciphertext, int offset, int len) {
        byte[] bArr = ciphertext;
        int i = offset;
        int i2 = len;
        TlsMac tlsMac = this.c;
        if (tlsMac == null) {
            return Arrays.B(bArr, i, i + i2);
        }
        int macSize = tlsMac.d();
        if (i2 >= macSize) {
            int macInputLen = i2 - macSize;
            if (Arrays.u(Arrays.B(bArr, i + macInputLen, i + i2), this.c.a(seqNo, type, ciphertext, offset, macInputLen))) {
                return Arrays.B(bArr, i, i + macInputLen);
            }
            throw new TlsFatalAlert(20);
        }
        throw new TlsFatalAlert(50);
    }
}
