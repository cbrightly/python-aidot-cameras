package org.spongycastle.crypto.engines;

public class VMPCKSA3Engine extends VMPCEngine {
    public String b() {
        return "VMPC-KSA3";
    }

    /* access modifiers changed from: protected */
    public void c(byte[] keyBytes, byte[] ivBytes) {
        this.c = 0;
        this.b = new byte[256];
        for (int i = 0; i < 256; i++) {
            this.b[i] = (byte) i;
        }
        for (int m = 0; m < 768; m++) {
            byte[] bArr = this.b;
            byte b = bArr[(this.c + bArr[m & 255] + keyBytes[m % keyBytes.length]) & 255];
            this.c = b;
            byte temp = bArr[m & 255];
            bArr[m & 255] = bArr[b & 255];
            bArr[b & 255] = temp;
        }
        for (int m2 = 0; m2 < 768; m2++) {
            byte[] bArr2 = this.b;
            byte b2 = bArr2[(this.c + bArr2[m2 & 255] + ivBytes[m2 % ivBytes.length]) & 255];
            this.c = b2;
            byte temp2 = bArr2[m2 & 255];
            bArr2[m2 & 255] = bArr2[b2 & 255];
            bArr2[b2 & 255] = temp2;
        }
        for (int m3 = 0; m3 < 768; m3++) {
            byte[] bArr3 = this.b;
            byte b3 = bArr3[(this.c + bArr3[m3 & 255] + keyBytes[m3 % keyBytes.length]) & 255];
            this.c = b3;
            byte temp3 = bArr3[m3 & 255];
            bArr3[m3 & 255] = bArr3[b3 & 255];
            bArr3[b3 & 255] = temp3;
        }
        this.a = 0;
    }
}
