package com.sensorsdata.analytics.android.sdk.encrypt;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.security.NoSuchAlgorithmException;

public class SARSAEncrypt implements SAEncryptListener {
    byte[] aesKey;
    String mEncryptKey;

    SARSAEncrypt() {
    }

    public String symmetricEncryptType() {
        return "AES";
    }

    public String encryptEvent(byte[] event) {
        return EncryptUtils.symmetricEncrypt(this.aesKey, event, SymmetricEncryptMode.AES);
    }

    public String asymmetricEncryptType() {
        return "RSA";
    }

    public String encryptSymmetricKeyWithPublicKey(String publicKey) {
        if (this.mEncryptKey == null) {
            try {
                byte[] generateSymmetricKey = EncryptUtils.generateSymmetricKey(SymmetricEncryptMode.AES);
                this.aesKey = generateSymmetricKey;
                this.mEncryptKey = EncryptUtils.encryptAESKey(publicKey, generateSymmetricKey, "RSA");
            } catch (NoSuchAlgorithmException e) {
                SALog.printStackTrace(e);
                return null;
            }
        }
        return this.mEncryptKey;
    }
}
