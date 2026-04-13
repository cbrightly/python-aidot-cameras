package com.sensorsdata.analytics.android.sdk.encrypt;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

public class SAECEncrypt implements SAEncryptListener {
    byte[] aesKey;
    String mEncryptKey;

    SAECEncrypt() {
    }

    static {
        try {
            Security.addProvider((Provider) Class.forName("org.spongycastle.jce.provider.BouncyCastleProvider").newInstance());
        } catch (Exception e) {
            SALog.i("SA.SAECEncrypt", e.toString());
        }
    }

    public String symmetricEncryptType() {
        return "AES";
    }

    public String encryptEvent(byte[] event) {
        return EncryptUtils.symmetricEncrypt(this.aesKey, event, SymmetricEncryptMode.AES);
    }

    public String asymmetricEncryptType() {
        return "EC";
    }

    public String encryptSymmetricKeyWithPublicKey(String publicKey) {
        if (this.mEncryptKey == null) {
            try {
                byte[] generateSymmetricKey = EncryptUtils.generateSymmetricKey(SymmetricEncryptMode.AES);
                this.aesKey = generateSymmetricKey;
                this.mEncryptKey = EncryptUtils.encryptAESKey(publicKey, generateSymmetricKey, "EC");
            } catch (NoSuchAlgorithmException e) {
                SALog.printStackTrace(e);
                return null;
            }
        }
        return this.mEncryptKey;
    }
}
