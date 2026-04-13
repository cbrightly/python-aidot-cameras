package com.sensorsdata.analytics.android.sdk.encrypt;

import android.content.Context;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import meshsdk.ConfigUtil;
import org.json.JSONObject;

public class SensorsDataEncrypt {
    private static final int KEY_VERSION_DEFAULT = 0;
    private static final String SP_SECRET_KEY = "secret_key";
    private static final String TAG = "SA.SensorsDataEncrypt";
    private Context mContext;
    private SAEncryptListener mEncryptListener;
    private List<SAEncryptListener> mListeners;
    private IPersistentSecretKey mPersistentSecretKey;
    private SecreteKey mSecreteKey;

    public SensorsDataEncrypt(Context context, IPersistentSecretKey persistentSecretKey, List<SAEncryptListener> listeners) {
        this.mPersistentSecretKey = persistentSecretKey;
        this.mContext = context;
        this.mListeners = listeners;
        listeners.add(new SARSAEncrypt());
        if (isECEncrypt()) {
            this.mListeners.add(new SAECEncrypt());
        }
    }

    public static boolean isECEncrypt() {
        try {
            Class.forName("org.spongycastle.jce.provider.BouncyCastleProvider");
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public JSONObject encryptTrackData(JSONObject jsonObject) {
        try {
            if (isSecretKeyNull(this.mSecreteKey)) {
                SecreteKey loadSecretKey = loadSecretKey();
                this.mSecreteKey = loadSecretKey;
                if (isSecretKeyNull(loadSecretKey)) {
                    return jsonObject;
                }
            }
            if (!isMatchEncryptType(this.mEncryptListener, this.mSecreteKey)) {
                this.mEncryptListener = getEncryptListener(this.mSecreteKey);
            }
            if (this.mEncryptListener == null) {
                return jsonObject;
            }
            String publicKey = this.mSecreteKey.key;
            if (publicKey.startsWith("EC:")) {
                publicKey = publicKey.substring(publicKey.indexOf(":") + 1);
            }
            String encryptedKey = this.mEncryptListener.encryptSymmetricKeyWithPublicKey(publicKey);
            if (TextUtils.isEmpty(encryptedKey)) {
                return jsonObject;
            }
            String encryptData = this.mEncryptListener.encryptEvent(gzipEventData(jsonObject.toString()));
            if (TextUtils.isEmpty(encryptData)) {
                return jsonObject;
            }
            JSONObject dataJson = new JSONObject();
            dataJson.put("ekey", (Object) encryptedKey);
            dataJson.put("pkv", this.mSecreteKey.version);
            dataJson.put("payloads", (Object) encryptData);
            return dataJson;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return jsonObject;
        }
    }

    public void saveSecretKey(SecreteKey secreteKey) {
        try {
            SALog.i(TAG, "[saveSecretKey] publicKey = " + secreteKey.toString());
            if (getEncryptListener(secreteKey) != null) {
                IPersistentSecretKey iPersistentSecretKey = this.mPersistentSecretKey;
                if (iPersistentSecretKey != null) {
                    iPersistentSecretKey.saveSecretKey(secreteKey);
                    SAStoreManager.getInstance().setString(SP_SECRET_KEY, "");
                    return;
                }
                SAStoreManager.getInstance().setString(SP_SECRET_KEY, secreteKey.toString());
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isPublicSecretKeyNull() {
        try {
            return TextUtils.isEmpty(loadSecretKey().key);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isMatchEncryptType(SAEncryptListener listener, SecreteKey secreteKey) {
        return listener != null && !isSecretKeyNull(secreteKey) && !isEncryptorTypeNull(listener) && listener.asymmetricEncryptType().equals(secreteKey.asymmetricEncryptType) && listener.symmetricEncryptType().equals(secreteKey.symmetricEncryptType);
    }

    public String checkPublicSecretKey(String version, String key, String symmetricEncryptType, String asymmetricEncryptType) {
        try {
            SecreteKey secreteKey = loadSecretKey();
            if (secreteKey == null) {
                return "密钥验证不通过，App 端密钥为空";
            }
            if (TextUtils.isEmpty(secreteKey.key)) {
                return "密钥验证不通过，App 端密钥为空";
            }
            if (!version.equals(secreteKey.version + "") || !disposeECPublicKey(key).equals(disposeECPublicKey(secreteKey.key))) {
                return "密钥验证不通过，所选密钥与 App 端密钥不相同。所选密钥版本:" + version + "，App 端密钥版本:" + secreteKey.version;
            } else if (symmetricEncryptType == null || asymmetricEncryptType == null) {
                return "密钥验证通过，所选密钥与 App 端密钥相同";
            } else {
                if (symmetricEncryptType.equals(secreteKey.symmetricEncryptType) && asymmetricEncryptType.equals(secreteKey.asymmetricEncryptType)) {
                    return "密钥验证通过，所选密钥与 App 端密钥相同";
                }
                return "密钥验证不通过，所选密钥类型与 App 端密钥类型不相同。所选密钥对称算法类型:" + symmetricEncryptType + "，非对称算法类型:" + asymmetricEncryptType + "，App 端密钥对称算法类型:" + secreteKey.symmetricEncryptType + "，非对称算法类型:" + secreteKey.asymmetricEncryptType;
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }

    public String disposeECPublicKey(String key) {
        if (TextUtils.isEmpty(key) || !key.startsWith("EC:")) {
            return key;
        }
        return key.substring(key.indexOf(":") + 1);
    }

    private byte[] gzipEventData(String record) {
        GZIPOutputStream gzipOutputStream = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream2 = new GZIPOutputStream(buffer);
            gzipOutputStream2.write(record.getBytes());
            gzipOutputStream2.finish();
            byte[] byteArray = buffer.toByteArray();
            try {
                gzipOutputStream2.close();
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
            return byteArray;
        } catch (Exception ex2) {
            SALog.printStackTrace(ex2);
            if (gzipOutputStream != null) {
                try {
                    gzipOutputStream.close();
                } catch (Exception ex3) {
                    SALog.printStackTrace(ex3);
                }
            }
            return null;
        } catch (Throwable th) {
            if (gzipOutputStream != null) {
                try {
                    gzipOutputStream.close();
                } catch (Exception ex4) {
                    SALog.printStackTrace(ex4);
                }
            }
            throw th;
        }
    }

    private SecreteKey loadSecretKey() {
        if (this.mPersistentSecretKey != null) {
            return readAppKey();
        }
        return readLocalKey();
    }

    private SecreteKey readAppKey() {
        String publicKey = null;
        int keyVersion = 0;
        String symmetricEncryptType = null;
        String asymmetricEncryptType = null;
        SecreteKey rsaPublicKeyVersion = this.mPersistentSecretKey.loadSecretKey();
        if (rsaPublicKeyVersion != null) {
            publicKey = rsaPublicKeyVersion.key;
            keyVersion = rsaPublicKeyVersion.version;
            symmetricEncryptType = rsaPublicKeyVersion.symmetricEncryptType;
            asymmetricEncryptType = rsaPublicKeyVersion.asymmetricEncryptType;
        }
        SALog.i(TAG, "readAppKey [key = " + publicKey + " ,v = " + keyVersion + " ,symmetricEncryptType = " + symmetricEncryptType + " ,asymmetricEncryptType = " + asymmetricEncryptType + "]");
        return new SecreteKey(publicKey, keyVersion, symmetricEncryptType, asymmetricEncryptType);
    }

    private SecreteKey readLocalKey() {
        String publicKey = null;
        int keyVersion = 0;
        String symmetricEncryptType = null;
        String asymmetricEncryptType = null;
        String secretKey = SAStoreManager.getInstance().getString(SP_SECRET_KEY, "");
        if (!TextUtils.isEmpty(secretKey)) {
            JSONObject jsonObject = new JSONObject(secretKey);
            publicKey = jsonObject.optString(CacheEntity.KEY, "");
            keyVersion = jsonObject.optInt(ConfigUtil.VERSION_FILE, 0);
            symmetricEncryptType = jsonObject.optString("symmetricEncryptType", "");
            asymmetricEncryptType = jsonObject.optString("asymmetricEncryptType", "");
        }
        SALog.i(TAG, "readLocalKey [key = " + publicKey + " ,v = " + keyVersion + " ,symmetricEncryptType = " + symmetricEncryptType + " ,asymmetricEncryptType = " + asymmetricEncryptType + "]");
        return new SecreteKey(publicKey, keyVersion, symmetricEncryptType, asymmetricEncryptType);
    }

    private boolean isSecretKeyNull(SecreteKey secreteKey) {
        return secreteKey == null || TextUtils.isEmpty(secreteKey.key) || secreteKey.version == 0;
    }

    private boolean isEncryptorTypeNull(SAEncryptListener saEncryptListener) {
        return TextUtils.isEmpty(saEncryptListener.asymmetricEncryptType()) || TextUtils.isEmpty(saEncryptListener.symmetricEncryptType());
    }

    /* access modifiers changed from: package-private */
    public SAEncryptListener getEncryptListener(SecreteKey secreteKey) {
        if (isSecretKeyNull(secreteKey)) {
            return null;
        }
        for (SAEncryptListener listener : this.mListeners) {
            if (listener != null && isMatchEncryptType(listener, secreteKey)) {
                return listener;
            }
        }
        return null;
    }
}
