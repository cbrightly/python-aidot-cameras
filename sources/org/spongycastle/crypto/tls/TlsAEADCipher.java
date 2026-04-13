package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class TlsAEADCipher implements TlsCipher {
    protected TlsContext a;
    protected int b;
    protected int c;
    protected AEADBlockCipher d;
    protected AEADBlockCipher e;
    protected byte[] f;
    protected byte[] g;
    protected int h;

    public TlsAEADCipher(TlsContext context, AEADBlockCipher clientWriteCipher, AEADBlockCipher serverWriteCipher, int cipherKeySize, int macSize) {
        this(context, clientWriteCipher, serverWriteCipher, cipherKeySize, macSize, 1);
    }

    TlsAEADCipher(TlsContext context, AEADBlockCipher clientWriteCipher, AEADBlockCipher serverWriteCipher, int cipherKeySize, int macSize, int nonceMode) {
        int fixed_iv_length;
        KeyParameter encryptKey;
        KeyParameter decryptKey;
        TlsContext tlsContext = context;
        AEADBlockCipher aEADBlockCipher = clientWriteCipher;
        AEADBlockCipher aEADBlockCipher2 = serverWriteCipher;
        int i = cipherKeySize;
        int i2 = macSize;
        int i3 = nonceMode;
        if (TlsUtils.U(context)) {
            this.h = i3;
            switch (i3) {
                case 1:
                    fixed_iv_length = 4;
                    this.c = 8;
                    break;
                case 2:
                    fixed_iv_length = 12;
                    this.c = 0;
                    break;
                default:
                    throw new TlsFatalAlert(80);
            }
            this.a = tlsContext;
            this.b = i2;
            int key_block_size = (i * 2) + (fixed_iv_length * 2);
            byte[] key_block = TlsUtils.c(tlsContext, key_block_size);
            KeyParameter client_write_key = new KeyParameter(key_block, 0, i);
            int offset = 0 + i;
            KeyParameter server_write_key = new KeyParameter(key_block, offset, i);
            int offset2 = offset + i;
            byte[] client_write_IV = Arrays.B(key_block, offset2, offset2 + fixed_iv_length);
            int offset3 = offset2 + fixed_iv_length;
            byte[] server_write_IV = Arrays.B(key_block, offset3, offset3 + fixed_iv_length);
            if (offset3 + fixed_iv_length == key_block_size) {
                if (context.g()) {
                    this.d = aEADBlockCipher2;
                    this.e = aEADBlockCipher;
                    this.f = server_write_IV;
                    this.g = client_write_IV;
                    encryptKey = server_write_key;
                    decryptKey = client_write_key;
                } else {
                    this.d = aEADBlockCipher;
                    this.e = aEADBlockCipher2;
                    this.f = client_write_IV;
                    this.g = server_write_IV;
                    encryptKey = client_write_key;
                    decryptKey = server_write_key;
                }
                byte[] dummyNonce = new byte[(this.c + fixed_iv_length)];
                this.d.a(true, new AEADParameters(encryptKey, i2 * 8, dummyNonce));
                this.e.a(false, new AEADParameters(decryptKey, i2 * 8, dummyNonce));
                return;
            }
            throw new TlsFatalAlert(80);
        }
        throw new TlsFatalAlert(80);
    }

    public int d(int ciphertextLimit) {
        return (ciphertextLimit - this.b) - this.c;
    }

    public byte[] b(long seqNo, short type, byte[] plaintext, int offset, int len) {
        long j = seqNo;
        byte[] bArr = this.f;
        byte[] nonce = new byte[(bArr.length + this.c)];
        switch (this.h) {
            case 1:
                System.arraycopy(bArr, 0, nonce, 0, bArr.length);
                TlsUtils.I0(j, nonce, this.f.length);
                break;
            case 2:
                TlsUtils.I0(j, nonce, nonce.length - 8);
                int i = 0;
                while (true) {
                    byte[] bArr2 = this.f;
                    if (i >= bArr2.length) {
                        break;
                    } else {
                        nonce[i] = (byte) (bArr2[i] ^ nonce[i]);
                        i++;
                    }
                }
            default:
                throw new TlsFatalAlert(80);
        }
        int plaintextOffset = offset;
        int plaintextLength = len;
        int ciphertextLength = this.d.f(plaintextLength);
        int i2 = this.c;
        byte[] output = new byte[(i2 + ciphertextLength)];
        if (i2 != 0) {
            System.arraycopy(nonce, nonce.length - i2, output, 0, i2);
        }
        int outputPos = this.c;
        byte[] additionalData = c(j, type, plaintextLength);
        AEADParameters parameters = new AEADParameters((KeyParameter) null, this.b * 8, nonce, additionalData);
        try {
            this.d.a(true, parameters);
            AEADParameters aEADParameters = parameters;
            byte[] bArr3 = additionalData;
            try {
                int outputPos2 = outputPos + this.d.d(plaintext, plaintextOffset, plaintextLength, output, outputPos);
                if (outputPos2 + this.d.c(output, outputPos2) == output.length) {
                    return output;
                }
                throw new TlsFatalAlert(80);
            } catch (Exception e2) {
                e = e2;
                throw new TlsFatalAlert(80, e);
            }
        } catch (Exception e3) {
            e = e3;
            AEADParameters aEADParameters2 = parameters;
            byte[] bArr4 = additionalData;
            throw new TlsFatalAlert(80, e);
        }
    }

    public byte[] a(long seqNo, short type, byte[] ciphertext, int offset, int len) {
        int outputPos;
        long j = seqNo;
        int i = offset;
        int i2 = len;
        if (d(i2) >= 0) {
            byte[] bArr = this.g;
            byte[] nonce = new byte[(bArr.length + this.c)];
            switch (this.h) {
                case 1:
                    System.arraycopy(bArr, 0, nonce, 0, bArr.length);
                    int length = nonce.length;
                    int i3 = this.c;
                    System.arraycopy(ciphertext, i, nonce, length - i3, i3);
                    break;
                case 2:
                    TlsUtils.I0(j, nonce, nonce.length - 8);
                    int i4 = 0;
                    while (true) {
                        byte[] bArr2 = this.g;
                        if (i4 >= bArr2.length) {
                            byte[] bArr3 = ciphertext;
                            break;
                        } else {
                            nonce[i4] = (byte) (bArr2[i4] ^ nonce[i4]);
                            i4++;
                        }
                    }
                default:
                    throw new TlsFatalAlert(80);
            }
            int i5 = this.c;
            int ciphertextOffset = i + i5;
            int ciphertextLength = i2 - i5;
            int plaintextLength = this.e.f(ciphertextLength);
            byte[] output = new byte[plaintextLength];
            byte[] additionalData = c(j, type, plaintextLength);
            try {
                this.e.a(false, new AEADParameters((KeyParameter) null, this.b * 8, nonce, additionalData));
                byte[] bArr4 = additionalData;
                byte[] output2 = output;
                int i6 = plaintextLength;
                int i7 = ciphertextLength;
                try {
                    outputPos = 0 + this.e.d(ciphertext, ciphertextOffset, ciphertextLength, output2, 0);
                } catch (Exception e2) {
                    e = e2;
                    byte[] bArr5 = output2;
                    throw new TlsFatalAlert(20, e);
                }
                try {
                    byte[] output3 = output2;
                    try {
                        if (outputPos + this.e.c(output3, outputPos) == output3.length) {
                            return output3;
                        }
                        throw new TlsFatalAlert(80);
                    } catch (Exception e3) {
                        e = e3;
                        int i8 = outputPos;
                        throw new TlsFatalAlert(20, e);
                    }
                } catch (Exception e4) {
                    e = e4;
                    byte[] bArr6 = output2;
                    int i9 = outputPos;
                    throw new TlsFatalAlert(20, e);
                }
            } catch (Exception e5) {
                e = e5;
                byte[] bArr7 = additionalData;
                byte[] bArr8 = output;
                int i10 = plaintextLength;
                int i11 = ciphertextLength;
                throw new TlsFatalAlert(20, e);
            }
        } else {
            throw new TlsFatalAlert(50);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] c(long seqNo, short type, int len) {
        byte[] additional_data = new byte[13];
        TlsUtils.I0(seqNo, additional_data, 0);
        TlsUtils.M0(type, additional_data, 8);
        TlsUtils.S0(this.a.b(), additional_data, 9);
        TlsUtils.E0(len, additional_data, 11);
        return additional_data;
    }
}
