package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class TlsBlockCipher implements TlsCipher {
    protected TlsContext a;
    protected byte[] b = new byte[256];
    protected boolean c;
    protected boolean d;
    protected BlockCipher e;
    protected BlockCipher f;
    protected TlsMac g;
    protected TlsMac h;

    public TlsBlockCipher(TlsContext context, BlockCipher clientWriteCipher, BlockCipher serverWriteCipher, Digest clientWriteDigest, Digest serverWriteDigest, int cipherKeySize) {
        int key_block_size;
        byte[] server_write_IV;
        byte[] client_write_IV;
        CipherParameters decryptParams;
        CipherParameters encryptParams;
        TlsContext tlsContext = context;
        BlockCipher blockCipher = clientWriteCipher;
        BlockCipher blockCipher2 = serverWriteCipher;
        int i = cipherKeySize;
        this.a = tlsContext;
        context.e().a(this.b);
        this.c = TlsUtils.S(context);
        this.d = context.f().n;
        int key_block_size2 = (i * 2) + clientWriteDigest.e() + serverWriteDigest.e();
        if (!this.c) {
            key_block_size = key_block_size2 + clientWriteCipher.c() + serverWriteCipher.c();
        } else {
            key_block_size = key_block_size2;
        }
        byte[] key_block = TlsUtils.c(tlsContext, key_block_size);
        TlsContext tlsContext2 = context;
        byte[] bArr = key_block;
        TlsMac serverWriteMac = new TlsMac(tlsContext2, clientWriteDigest, bArr, 0, clientWriteDigest.e());
        int offset = 0 + clientWriteDigest.e();
        TlsMac serverWriteMac2 = new TlsMac(tlsContext2, serverWriteDigest, bArr, offset, serverWriteDigest.e());
        int offset2 = offset + serverWriteDigest.e();
        KeyParameter client_write_key = new KeyParameter(key_block, offset2, i);
        int offset3 = offset2 + i;
        KeyParameter server_write_key = new KeyParameter(key_block, offset3, i);
        int offset4 = offset3 + i;
        if (this.c) {
            client_write_IV = new byte[clientWriteCipher.c()];
            server_write_IV = new byte[serverWriteCipher.c()];
        } else {
            client_write_IV = Arrays.B(key_block, offset4, clientWriteCipher.c() + offset4);
            int offset5 = offset4 + clientWriteCipher.c();
            server_write_IV = Arrays.B(key_block, offset5, serverWriteCipher.c() + offset5);
            offset4 = offset5 + serverWriteCipher.c();
        }
        if (offset4 == key_block_size) {
            if (context.g()) {
                this.g = serverWriteMac2;
                this.h = serverWriteMac;
                this.e = blockCipher2;
                this.f = blockCipher;
                encryptParams = new ParametersWithIV(server_write_key, server_write_IV);
                decryptParams = new ParametersWithIV(client_write_key, client_write_IV);
            } else {
                this.g = serverWriteMac;
                this.h = serverWriteMac2;
                this.e = blockCipher;
                this.f = blockCipher2;
                encryptParams = new ParametersWithIV(client_write_key, client_write_IV);
                decryptParams = new ParametersWithIV(server_write_key, server_write_IV);
            }
            TlsMac tlsMac = serverWriteMac2;
            KeyParameter keyParameter = client_write_key;
            this.e.a(true, encryptParams);
            this.f.a(false, decryptParams);
            return;
        }
        KeyParameter keyParameter2 = client_write_key;
        throw new TlsFatalAlert(80);
    }

    public byte[] b(long seqNo, short type, byte[] plaintext, int offset, int len) {
        int enc_input_length;
        int padding_length;
        int totalSize;
        int macSize;
        int i = len;
        int blockSize = this.e.c();
        int macSize2 = this.g.d();
        ProtocolVersion version = this.a.b();
        int enc_input_length2 = len;
        boolean z = this.d;
        if (!z) {
            enc_input_length = enc_input_length2 + macSize2;
        } else {
            enc_input_length = enc_input_length2;
        }
        int padding_length2 = (blockSize - 1) - (enc_input_length % blockSize);
        if ((z || !this.a.f().m) && !version.g() && !version.j()) {
            padding_length = padding_length2 + (d(this.a.d(), (255 - padding_length2) / blockSize) * blockSize);
        } else {
            padding_length = padding_length2;
        }
        int totalSize2 = i + macSize2 + padding_length + 1;
        boolean z2 = this.c;
        if (z2) {
            totalSize = totalSize2 + blockSize;
        } else {
            totalSize = totalSize2;
        }
        byte[] outBuf = new byte[totalSize];
        int outOff = 0;
        if (z2) {
            byte[] explicitIV = new byte[blockSize];
            this.a.e().a(explicitIV);
            this.e.a(true, new ParametersWithIV((CipherParameters) null, explicitIV));
            System.arraycopy(explicitIV, 0, outBuf, 0, blockSize);
            outOff = 0 + blockSize;
        }
        int blocks_start = outOff;
        System.arraycopy(plaintext, offset, outBuf, outOff, i);
        int outOff2 = outOff + i;
        if (this.d == 0) {
            int outOff3 = outOff2;
            int i2 = macSize2;
            macSize = 0;
            byte[] mac = this.g.a(seqNo, type, plaintext, offset, len);
            System.arraycopy(mac, 0, outBuf, outOff3, mac.length);
            outOff2 = outOff3 + mac.length;
        } else {
            int i3 = outOff2;
            int i4 = macSize2;
            macSize = 0;
        }
        int i5 = 0;
        while (i5 <= padding_length) {
            outBuf[outOff2] = (byte) padding_length;
            i5++;
            outOff2++;
        }
        for (int i6 = blocks_start; i6 < outOff2; i6 += blockSize) {
            this.e.f(outBuf, i6, outBuf, i6);
        }
        if (this.d == 0) {
            return outBuf;
        }
        byte[] outBuf2 = outBuf;
        byte[] mac2 = this.g.a(seqNo, type, outBuf2, 0, outOff2);
        System.arraycopy(mac2, macSize, outBuf2, outOff2, mac2.length);
        int outOff4 = outOff2 + mac2.length;
        return outBuf2;
    }

    public byte[] a(long seqNo, short type, byte[] ciphertext, int offset, int len) {
        int minLen;
        int minLen2;
        char c2;
        int offset2;
        int blocks_length;
        int blocks_length2;
        byte[] bArr;
        int totalPad;
        byte[] bArr2 = ciphertext;
        int offset3 = offset;
        int i = len;
        int blockSize = this.f.c();
        int macSize = this.h.d();
        int minLen3 = blockSize;
        if (this.d) {
            minLen = minLen3 + macSize;
        } else {
            minLen = Math.max(minLen3, macSize + 1);
        }
        if (this.c) {
            minLen2 = minLen + blockSize;
        } else {
            minLen2 = minLen;
        }
        if (i >= minLen2) {
            int blocks_length3 = len;
            boolean z = this.d;
            if (z) {
                blocks_length3 -= macSize;
            }
            if (blocks_length3 % blockSize == 0) {
                if (z) {
                    int end = offset3 + i;
                    if (!(!Arrays.u(this.h.a(seqNo, type, ciphertext, offset, i - macSize), Arrays.B(bArr2, end - macSize, end)))) {
                        c2 = 20;
                    } else {
                        throw new TlsFatalAlert(20);
                    }
                } else {
                    c2 = 20;
                }
                boolean z2 = false;
                if (this.c) {
                    this.f.a(false, new ParametersWithIV((CipherParameters) null, bArr2, offset3, blockSize));
                    offset2 = offset3 + blockSize;
                    blocks_length = blocks_length3 - blockSize;
                } else {
                    offset2 = offset3;
                    blocks_length = blocks_length3;
                }
                for (int i2 = 0; i2 < blocks_length; i2 += blockSize) {
                    this.f.f(bArr2, offset2 + i2, bArr2, offset2 + i2);
                }
                int i3 = c2;
                int i4 = minLen2;
                int i5 = blockSize;
                int totalPad2 = c(ciphertext, offset2, blocks_length, blockSize, this.d != 0 ? 0 : macSize);
                if (totalPad2 == 0) {
                    z2 = true;
                }
                boolean badMac = z2;
                int dec_output_length = blocks_length - totalPad2;
                if (!this.d) {
                    int dec_output_length2 = dec_output_length - macSize;
                    int macInputLen = dec_output_length2;
                    int macOff = offset2 + macInputLen;
                    int i6 = totalPad2;
                    int totalPad3 = i3;
                    int i7 = blocks_length;
                    totalPad = offset2;
                    int dec_output_length3 = dec_output_length2;
                    bArr = bArr2;
                    badMac |= !Arrays.u(this.h.b(seqNo, type, ciphertext, offset2, macInputLen, blocks_length - macSize, this.b), Arrays.B(bArr2, macOff, macOff + macSize));
                    blocks_length2 = dec_output_length3;
                } else {
                    int i8 = blocks_length;
                    totalPad = offset2;
                    blocks_length2 = dec_output_length;
                    bArr = bArr2;
                }
                if (!badMac) {
                    return Arrays.B(bArr, totalPad, totalPad + blocks_length2);
                }
                throw new TlsFatalAlert(20);
            }
            throw new TlsFatalAlert(21);
        }
        throw new TlsFatalAlert(50);
    }

    /* access modifiers changed from: protected */
    public int c(byte[] buf, int off, int len, int blockSize, int macSize) {
        int end = off + len;
        byte lastByte = buf[end - 1];
        int totalPad = (lastByte & 255) + 1;
        int dummyIndex = 0;
        byte padDiff = 0;
        if ((!TlsUtils.P(this.a) || totalPad <= blockSize) && macSize + totalPad <= len) {
            int padPos = end - totalPad;
            while (true) {
                int padPos2 = padPos + 1;
                padDiff = (byte) ((buf[padPos] ^ lastByte) | padDiff);
                if (padPos2 >= end) {
                    break;
                }
                padPos = padPos2;
            }
            dummyIndex = totalPad;
            if (padDiff != 0) {
                totalPad = 0;
            }
        } else {
            totalPad = 0;
        }
        byte[] dummyPad = this.b;
        while (dummyIndex < 256) {
            padDiff = (byte) ((dummyPad[dummyIndex] ^ lastByte) | padDiff);
            dummyIndex++;
        }
        dummyPad[0] = (byte) (dummyPad[0] ^ padDiff);
        return totalPad;
    }

    /* access modifiers changed from: protected */
    public int d(SecureRandom r, int max) {
        return Math.min(e(r.nextInt()), max);
    }

    /* access modifiers changed from: protected */
    public int e(int x) {
        if (x == 0) {
            return 32;
        }
        int n = 0;
        while ((x & 1) == 0) {
            n++;
            x >>= 1;
        }
        return n;
    }
}
