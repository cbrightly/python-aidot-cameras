package com.sensorsdata.analytics.android.sdk.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESSecretManager {
    private static final String ALGORITHM = "AES";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String SECRET_KEY_FILE = "com.sensorsdata.analytics.android.sdk.other";
    private static final String TAG = "SA.AESSecretManager";
    private static final byte[] ZERO_IV = new byte[16];
    private String mAESSecret;

    private AESSecretManager() {
    }

    public static class SingletonHolder {
        /* access modifiers changed from: private */
        public static final AESSecretManager INSTANCE = new AESSecretManager();

        private SingletonHolder() {
        }
    }

    public static AESSecretManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initSecretKey(Context context) {
        SharedPreferences secretSp = context.getSharedPreferences(SECRET_KEY_FILE, 0);
        String string = secretSp.getString(Base64Coder.encodeString(SAStoreManager.SECRET_KEY), "");
        this.mAESSecret = string;
        if (TextUtils.isEmpty(string)) {
            this.mAESSecret = generateAESKey();
            secretSp.edit().putString(Base64Coder.encodeString(SAStoreManager.SECRET_KEY), this.mAESSecret).apply();
        }
    }

    private String generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128);
            return new String(Base64Coder.encode(keyGen.generateKey().getEncoded()));
        } catch (Exception e) {
            SALog.i(TAG, "generateAESKey fail, msg: " + e);
            return "";
        }
    }

    private SecretKey strKey2SecretKey(String strKey) {
        return new SecretKeySpec(Base64Coder.decode(strKey), ALGORITHM);
    }

    public String encryptAES(String content) {
        if (content != null) {
            try {
                if (!TextUtils.isEmpty(this.mAESSecret)) {
                    Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
                    cipher.init(1, strKey2SecretKey(this.mAESSecret), new IvParameterSpec(ZERO_IV));
                    return new String(Base64Coder.encode(cipher.doFinal(content.getBytes("UTF-8"))));
                }
            } catch (Exception e) {
                SALog.i(TAG, "encryptAES fail, msg: " + e);
                return "";
            }
        }
        return content;
    }

    public String decryptAES(String content) {
        if (content != null) {
            try {
                if (!TextUtils.isEmpty(this.mAESSecret)) {
                    Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
                    cipher.init(2, strKey2SecretKey(this.mAESSecret), new IvParameterSpec(ZERO_IV));
                    return new String(cipher.doFinal(Base64Coder.decode(content)), "UTF-8");
                }
            } catch (Exception e) {
                SALog.i(TAG, "decryptAES fail, msg: " + e);
                return "";
            }
        }
        return content;
    }
}
