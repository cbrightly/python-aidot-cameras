package org.spongycastle.crypto.engines;

import com.tutk.IOTC.AVIOCTRLDEFs;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class SkipjackEngine implements BlockCipher {
    static short[] a = {163, 215, 9, 131, 248, 72, 246, 244, 179, 33, 21, 120, 153, 177, 175, 249, 231, 45, 77, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711A, 206, 76, 202, 46, 82, 149, 217, 30, 78, 56, 68, 40, 10, 223, 2, 160, 23, 241, 96, 104, 18, 183, 122, 195, 233, 250, 61, 83, 150, 132, 107, 186, 242, 99, 154, 25, 124, 174, 229, 245, 247, 22, 106, 162, 57, 182, 123, 15, 193, 147, 129, 27, 238, 180, 26, 234, 208, 145, 47, 184, 85, 185, 218, 133, 63, 65, 191, 224, 90, 88, MqttException.REASON_CODE_SUBSCRIBE_FAILED, 95, 102, 11, 216, 144, 53, 213, 192, 167, 51, 6, 101, 105, 69, 0, 148, 86, 109, 152, 155, 118, 151, 252, 178, 194, 176, 254, 219, 32, 225, 235, 214, 228, 221, 71, 74, 29, 66, 237, 158, 110, 73, 60, 205, 67, 39, 210, 7, 212, 222, 199, 103, 24, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711U, 203, 48, 31, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_SPEEX, 198, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G726, 170, 200, 116, 220, 201, 93, 92, 49, 164, 112, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_AAC, 97, 44, 159, 13, 43, 135, 80, 130, 84, 100, 38, 125, 3, 64, 52, 75, 28, 115, 209, 196, 253, 59, 204, 251, 127, 171, 230, 62, 91, 165, 173, 4, 35, 156, 20, 81, 34, 240, 41, 121, 113, 126, 255, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_PCM, 14, 226, 12, 239, 188, 114, 117, 111, 55, 161, 236, 211, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_MP3, 98, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_ADPCM, 134, 16, 232, 8, 119, 17, 190, 146, 79, 36, 197, 50, 54, 157, 207, 243, 166, 187, 172, 94, 108, 169, 19, 87, 37, 181, 227, 189, 168, 58, 1, 5, 89, 42, 70};
    private int[] b;
    private int[] c;
    private int[] d;
    private int[] e;
    private boolean f;

    public void a(boolean encrypting, CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] keyBytes = ((KeyParameter) params).a();
            this.f = encrypting;
            this.b = new int[32];
            this.c = new int[32];
            this.d = new int[32];
            this.e = new int[32];
            for (int i = 0; i < 32; i++) {
                this.b[i] = keyBytes[(i * 4) % 10] & 255;
                this.c[i] = keyBytes[((i * 4) + 1) % 10] & 255;
                this.d[i] = keyBytes[((i * 4) + 2) % 10] & 255;
                this.e[i] = keyBytes[((i * 4) + 3) % 10] & 255;
            }
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to SKIPJACK init - " + params.getClass().getName());
    }

    public String b() {
        return "SKIPJACK";
    }

    public int c() {
        return 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.c == null) {
            throw new IllegalStateException("SKIPJACK engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.f) {
            e(in, inOff, out, outOff);
            return 8;
        } else {
            d(in, inOff, out, outOff);
            return 8;
        }
    }

    public void reset() {
    }

    private int g(int k, int w) {
        int g2 = w & 255;
        short[] sArr = a;
        int g3 = sArr[this.b[k] ^ g2] ^ ((w >> 8) & 255);
        int g4 = sArr[this.c[k] ^ g3] ^ g2;
        int g5 = sArr[this.d[k] ^ g4] ^ g3;
        return (g5 << 8) + (sArr[this.e[k] ^ g5] ^ g4);
    }

    public int e(byte[] in, int inOff, byte[] out, int outOff) {
        int w1 = (in[inOff + 0] << 8) + (in[inOff + 1] & 255);
        int w2 = (in[inOff + 2] << 8) + (in[inOff + 3] & 255);
        int w3 = (in[inOff + 4] << 8) + (in[inOff + 5] & 255);
        int w4 = (in[inOff + 6] << 8) + (in[inOff + 7] & 255);
        int k = 0;
        for (int t = 0; t < 2; t++) {
            for (int i = 0; i < 8; i++) {
                int tmp = w4;
                w4 = w3;
                w3 = w2;
                w2 = g(k, w1);
                w1 = (w2 ^ tmp) ^ (k + 1);
                k++;
            }
            for (int i2 = 0; i2 < 8; i2++) {
                int tmp2 = w4;
                w4 = w3;
                w3 = (w1 ^ w2) ^ (k + 1);
                w2 = g(k, w1);
                w1 = tmp2;
                k++;
            }
        }
        out[outOff + 0] = (byte) (w1 >> 8);
        out[outOff + 1] = (byte) w1;
        out[outOff + 2] = (byte) (w2 >> 8);
        out[outOff + 3] = (byte) w2;
        out[outOff + 4] = (byte) (w3 >> 8);
        out[outOff + 5] = (byte) w3;
        out[outOff + 6] = (byte) (w4 >> 8);
        out[outOff + 7] = (byte) w4;
        return 8;
    }

    private int h(int k, int w) {
        int h2 = (w >> 8) & 255;
        short[] sArr = a;
        int h3 = sArr[this.e[k] ^ h2] ^ (w & 255);
        int h4 = sArr[this.d[k] ^ h3] ^ h2;
        int h5 = sArr[this.c[k] ^ h4] ^ h3;
        return ((sArr[this.b[k] ^ h5] ^ h4) << 8) + h5;
    }

    public int d(byte[] in, int inOff, byte[] out, int outOff) {
        int w2 = (in[inOff + 0] << 8) + (in[inOff + 1] & 255);
        int w1 = (in[inOff + 2] << 8) + (in[inOff + 3] & 255);
        int w4 = (in[inOff + 4] << 8) + (in[inOff + 5] & 255);
        int w3 = (in[inOff + 6] << 8) + (in[inOff + 7] & 255);
        int k = 31;
        for (int t = 0; t < 2; t++) {
            for (int i = 0; i < 8; i++) {
                int tmp = w4;
                w4 = w3;
                w3 = w2;
                w2 = h(k, w1);
                w1 = (w2 ^ tmp) ^ (k + 1);
                k--;
            }
            for (int i2 = 0; i2 < 8; i2++) {
                int tmp2 = w4;
                w4 = w3;
                w3 = (w1 ^ w2) ^ (k + 1);
                w2 = h(k, w1);
                w1 = tmp2;
                k--;
            }
        }
        out[outOff + 0] = (byte) (w2 >> 8);
        out[outOff + 1] = (byte) w2;
        out[outOff + 2] = (byte) (w1 >> 8);
        out[outOff + 3] = (byte) w1;
        out[outOff + 4] = (byte) (w4 >> 8);
        out[outOff + 5] = (byte) w4;
        out[outOff + 6] = (byte) (w3 >> 8);
        out[outOff + 7] = (byte) w3;
        return 8;
    }
}
