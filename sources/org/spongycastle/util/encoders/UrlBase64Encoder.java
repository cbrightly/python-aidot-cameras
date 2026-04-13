package org.spongycastle.util.encoders;

public class UrlBase64Encoder extends Base64Encoder {
    public UrlBase64Encoder() {
        byte[] bArr = this.a;
        bArr[bArr.length - 2] = 45;
        bArr[bArr.length - 1] = 95;
        this.b = 46;
        e();
    }
}
