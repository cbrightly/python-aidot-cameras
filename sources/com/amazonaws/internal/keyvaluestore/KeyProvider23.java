package com.amazonaws.internal.keyvaluestore;

import android.security.keystore.KeyGenParameterSpec;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.google.android.gms.stats.CodePackage;
import java.security.Key;
import java.security.KeyStore;
import javax.crypto.KeyGenerator;

public class KeyProvider23 implements KeyProvider {
    private static final String AES_KEY_ALGORITHM = "AES";
    private static final String ANDROID_KEY_STORE_NAME = "AndroidKeyStore";
    static final String AWS_KEY_VALUE_STORE_VERSION_1_KEY_STORE_ALIAS_FOR_AES_SUFFIX = ".aesKeyStoreAlias";
    private static final int CIPHER_AES_GCM_NOPADDING_KEY_LENGTH_IN_BITS = 256;
    private static final Log logger = LogFactory.getLog(KeyProvider23.class);

    KeyProvider23() {
    }

    public synchronized Key retrieveKey(String keyAlias) {
        Key key;
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            if (keyStore.containsAlias(keyAlias)) {
                Log log = logger;
                log.debug("AndroidKeyStore contains keyAlias " + keyAlias);
                log.debug("Loading the encryption key from Android KeyStore.");
                key = keyStore.getKey(keyAlias, (char[]) null);
                if (key == null) {
                    throw new KeyNotFoundException("Key is null even though the keyAlias: " + keyAlias + " is present in " + ANDROID_KEY_STORE_NAME);
                }
            } else {
                throw new KeyNotFoundException("AndroidKeyStore does not contain the keyAlias: " + keyAlias);
            }
        } catch (Exception ex) {
            throw new KeyNotFoundException("Error occurred while accessing AndroidKeyStore to retrieve the key for keyAlias: " + keyAlias, ex);
        }
        return key;
    }

    public synchronized Key generateKey(String keyAlias) {
        Key key;
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            if (!keyStore.containsAlias(keyAlias)) {
                KeyGenerator generator = KeyGenerator.getInstance(AES_KEY_ALGORITHM, ANDROID_KEY_STORE_NAME);
                generator.init(new KeyGenParameterSpec.Builder(keyAlias, 3).setBlockModes(new String[]{CodePackage.GCM}).setEncryptionPaddings(new String[]{"NoPadding"}).setKeySize(256).setRandomizedEncryptionRequired(false).build());
                key = generator.generateKey();
                Log log = logger;
                log.info("Generated the encryption key identified by the keyAlias: " + keyAlias + " using " + ANDROID_KEY_STORE_NAME);
            } else {
                throw new KeyNotGeneratedException("Key already exists for the keyAlias: " + keyAlias + " in " + ANDROID_KEY_STORE_NAME);
            }
        } catch (Exception ex) {
            throw new KeyNotGeneratedException("Cannot generate a key for alias: " + keyAlias + " in " + ANDROID_KEY_STORE_NAME, ex);
        }
        return key;
    }

    public synchronized void deleteKey(String keyAlias) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            keyStore.deleteEntry(keyAlias);
        } catch (Exception ex) {
            Log log = logger;
            log.error("Error in deleting the key for keyAlias: " + keyAlias + " from Android KeyStore.", ex);
        }
        return;
    }
}
