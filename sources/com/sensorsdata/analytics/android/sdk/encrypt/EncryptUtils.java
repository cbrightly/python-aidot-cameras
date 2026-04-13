package com.sensorsdata.analytics.android.sdk.encrypt;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class EncryptUtils {
    private static final String TAG = "SensorsDataEncrypt";

    EncryptUtils() {
    }

    static byte[] generateSymmetricKey(SymmetricEncryptMode mode) {
        KeyGenerator keyGen = KeyGenerator.getInstance(mode.algorithm);
        keyGen.init(128);
        return keyGen.generateKey().getEncoded();
    }

    static String encryptAESKey(String publicKey, byte[] aesKey, String type) {
        return publicKeyEncrypt(publicKey, type, aesKey);
    }

    private static String publicKeyEncrypt(String publicKey, String type, byte[] content) {
        Cipher cipher;
        byte[] cache;
        if (TextUtils.isEmpty(publicKey)) {
            SALog.i(TAG, "PublicKey is null.");
            return null;
        }
        try {
            KeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64Coder.decode(publicKey));
            if ("EC".equals(type)) {
                cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
                cipher.init(1, (ECPublicKey) KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME).generatePublic(x509EncodedKeySpec));
            } else {
                Key rsaPublicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
                Cipher cipher2 = Cipher.getInstance("RSA/None/PKCS1Padding");
                cipher2.init(1, rsaPublicKey);
                cipher = cipher2;
            }
            int contentLen = content.length;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (int offSet = 0; contentLen - offSet > 0; offSet += 245) {
                if (contentLen - offSet > 245) {
                    cache = cipher.doFinal(content, offSet, 245);
                } else {
                    cache = cipher.doFinal(content, offSet, contentLen - offSet);
                }
                outputStream.write(cache, 0, cache.length);
            }
            byte[] cache2 = outputStream.toByteArray();
            outputStream.close();
            return new String(Base64Coder.encode(cache2));
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return null;
        }
    }

    static String symmetricEncrypt(byte[] key, byte[] contentBytes, SymmetricEncryptMode mode) {
        if (key == null || contentBytes == null) {
            return null;
        }
        try {
            byte[] ivBytes = new byte[16];
            new SecureRandom().nextBytes(ivBytes);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, mode.algorithm);
            Cipher cipher = Cipher.getInstance(mode.transformation);
            cipher.init(1, secretKeySpec, new IvParameterSpec(ivBytes));
            byte[] encryptedBytes = cipher.doFinal(contentBytes);
            ByteBuffer byteBuffer = ByteBuffer.allocate(ivBytes.length + encryptedBytes.length);
            byteBuffer.put(ivBytes);
            byteBuffer.put(encryptedBytes);
            return new String(Base64Coder.encode(byteBuffer.array()));
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return null;
        }
    }
}
