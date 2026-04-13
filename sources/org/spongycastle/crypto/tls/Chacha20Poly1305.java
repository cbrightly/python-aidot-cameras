package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.engines.ChaCha7539Engine;
import org.spongycastle.crypto.macs.Poly1305;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class Chacha20Poly1305 implements TlsCipher {
    private static final byte[] a = new byte[15];
    protected TlsContext b;
    protected ChaCha7539Engine c;
    protected ChaCha7539Engine d;
    protected byte[] e;
    protected byte[] f;

    public Chacha20Poly1305(TlsContext context) {
        KeyParameter decryptKey;
        KeyParameter encryptKey;
        TlsContext tlsContext = context;
        if (TlsUtils.U(context)) {
            this.b = tlsContext;
            int key_block_size = (32 * 2) + (12 * 2);
            byte[] key_block = TlsUtils.c(tlsContext, key_block_size);
            KeyParameter client_write_key = new KeyParameter(key_block, 0, 32);
            int offset = 0 + 32;
            KeyParameter server_write_key = new KeyParameter(key_block, offset, 32);
            int offset2 = offset + 32;
            byte[] client_write_IV = Arrays.B(key_block, offset2, offset2 + 12);
            int offset3 = offset2 + 12;
            byte[] server_write_IV = Arrays.B(key_block, offset3, offset3 + 12);
            if (offset3 + 12 == key_block_size) {
                this.c = new ChaCha7539Engine();
                this.d = new ChaCha7539Engine();
                if (context.g()) {
                    encryptKey = server_write_key;
                    decryptKey = client_write_key;
                    this.e = server_write_IV;
                    this.f = client_write_IV;
                } else {
                    encryptKey = client_write_key;
                    decryptKey = server_write_key;
                    this.e = client_write_IV;
                    this.f = server_write_IV;
                }
                this.c.a(true, new ParametersWithIV(encryptKey, this.e));
                this.d.a(false, new ParametersWithIV(decryptKey, this.f));
                return;
            }
            throw new TlsFatalAlert(80);
        }
        throw new TlsFatalAlert(80);
    }

    public int g(int ciphertextLimit) {
        return ciphertextLimit - 16;
    }

    public byte[] b(long seqNo, short type, byte[] plaintext, int offset, int len) {
        int i = len;
        KeyParameter macKey = h(this.c, true, seqNo, this.e);
        byte[] output = new byte[(i + 16)];
        this.c.d(plaintext, offset, len, output, 0);
        byte[] mac = d(macKey, f(seqNo, type, i), output, 0, len);
        System.arraycopy(mac, 0, output, i, mac.length);
        return output;
    }

    public byte[] a(long seqNo, short type, byte[] ciphertext, int offset, int len) {
        int i = len;
        if (g(i) >= 0) {
            int plaintextLength = i - 16;
            byte[] calculatedMAC = d(h(this.d, false, seqNo, this.f), f(seqNo, type, plaintextLength), ciphertext, offset, plaintextLength);
            byte[] receivedMAC = Arrays.B(ciphertext, offset + plaintextLength, offset + i);
            if (Arrays.u(calculatedMAC, receivedMAC)) {
                byte[] output = new byte[plaintextLength];
                byte[] output2 = output;
                byte[] bArr = receivedMAC;
                this.d.d(ciphertext, offset, plaintextLength, output, 0);
                return output2;
            }
            throw new TlsFatalAlert(20);
        }
        long j = seqNo;
        short s = type;
        byte[] bArr2 = ciphertext;
        throw new TlsFatalAlert(50);
    }

    /* access modifiers changed from: protected */
    public KeyParameter h(StreamCipher cipher, boolean forEncryption, long seqNo, byte[] iv) {
        cipher.a(forEncryption, new ParametersWithIV((CipherParameters) null, c(seqNo, iv)));
        return e(cipher);
    }

    /* access modifiers changed from: protected */
    public byte[] c(long seqNo, byte[] iv) {
        byte[] nonce = new byte[12];
        TlsUtils.I0(seqNo, nonce, 4);
        for (int i = 0; i < 12; i++) {
            nonce[i] = (byte) (nonce[i] ^ iv[i]);
        }
        return nonce;
    }

    /* access modifiers changed from: protected */
    public KeyParameter e(StreamCipher cipher) {
        byte[] firstBlock = new byte[64];
        cipher.d(firstBlock, 0, firstBlock.length, firstBlock, 0);
        KeyParameter macKey = new KeyParameter(firstBlock, 0, 32);
        Arrays.F(firstBlock, (byte) 0);
        return macKey;
    }

    /* access modifiers changed from: protected */
    public byte[] d(KeyParameter macKey, byte[] additionalData, byte[] buf, int off, int len) {
        Mac mac = new Poly1305();
        mac.a(macKey);
        j(mac, additionalData, 0, additionalData.length);
        j(mac, buf, off, len);
        i(mac, additionalData.length);
        i(mac, len);
        byte[] output = new byte[mac.e()];
        mac.c(output, 0);
        return output;
    }

    /* access modifiers changed from: protected */
    public void i(Mac mac, int len) {
        byte[] longLen = Pack.u(((long) len) & 4294967295L);
        mac.update(longLen, 0, longLen.length);
    }

    /* access modifiers changed from: protected */
    public void j(Mac mac, byte[] buf, int off, int len) {
        mac.update(buf, off, len);
        int partial = len % 16;
        if (partial != 0) {
            mac.update(a, 0, 16 - partial);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] f(long seqNo, short type, int len) {
        byte[] additional_data = new byte[13];
        TlsUtils.I0(seqNo, additional_data, 0);
        TlsUtils.M0(type, additional_data, 8);
        TlsUtils.S0(this.b.b(), additional_data, 9);
        TlsUtils.E0(len, additional_data, 11);
        return additional_data;
    }
}
