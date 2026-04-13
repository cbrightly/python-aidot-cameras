package com.amazonaws.internal.keyvaluestore;

import android.content.SharedPreferences;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.Base64;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyProvider10 implements KeyProvider {
    private static final String AES_KEY_ALGORITHM = "AES";
    private static final int CIPHER_AES_GCM_NOPADDING_KEY_LENGTH_IN_BITS = 256;
    static final String KEY_ALIAS = "AesGcmNoPaddingEncryption10-encryption-key";
    private static final Log logger = LogFactory.getLog(KeyProvider10.class.getSimpleName());
    private SharedPreferences sharedPreferences;

    KeyProvider10(SharedPreferences sharedPreferences2) {
        this.sharedPreferences = sharedPreferences2;
    }

    public synchronized Key generateKey(String aesEncryptionKeyAlias) {
        SecretKey secretKey;
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator generator = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
            generator.init(256, secureRandom);
            secretKey = generator.generateKey();
            SecretKey aesEncryptionKey = generator.generateKey();
            if (aesEncryptionKey != null) {
                byte[] aesEncryptionKeyInBytes = aesEncryptionKey.getEncoded();
                if (aesEncryptionKeyInBytes == null || aesEncryptionKeyInBytes.length == 0) {
                    throw new KeyNotGeneratedException("Error in getting the encoded bytes for the AES encryption key identified by the aesEncryptionKeyAlias: " + aesEncryptionKeyAlias);
                }
                String base64EncodedStringOfEncryptedAESKey = Base64.encodeAsString(aesEncryptionKeyInBytes);
                if (base64EncodedStringOfEncryptedAESKey != null) {
                    this.sharedPreferences.edit().putString(aesEncryptionKeyAlias, base64EncodedStringOfEncryptedAESKey).apply();
                    Log log = logger;
                    log.info("Generated and saved the AES encryption key identified by the aesEncryptionKeyAlias: " + aesEncryptionKeyAlias + " to SharedPreferences.");
                } else {
                    throw new KeyNotGeneratedException("Error in Base64 encoding of the AES encryption key for the aesEncryptionKeyAlias: " + aesEncryptionKeyAlias);
                }
            } else {
                throw new KeyNotGeneratedException("Error in generating the AES encryption key identified by the aesEncryptionKeyAlias: " + aesEncryptionKeyAlias);
            }
        } catch (Exception ex) {
            throw new KeyNotGeneratedException("Error in generating the AES Encryption key for the aesEncryptionKeyAlias", ex);
        }
        return secretKey;
    }

    public synchronized Key retrieveKey(String keyAlias) {
        byte[] base64DecodedAESEncryptionKey;
        try {
            if (this.sharedPreferences.contains(keyAlias)) {
                logger.debug("Loading the encryption key from SharedPreferences");
                String keyInStringFormat = this.sharedPreferences.getString(keyAlias, (String) null);
                if (keyInStringFormat != null) {
                    base64DecodedAESEncryptionKey = Base64.decode(keyInStringFormat);
                    if (base64DecodedAESEncryptionKey == null || base64DecodedAESEncryptionKey.length == 0) {
                        throw new KeyNotFoundException("Error in Base64 decoding the AES encryption key identified by the keyAlias: " + keyAlias);
                    }
                } else {
                    throw new KeyNotFoundException("SharedPreferences does not have the key for keyAlias: " + keyAlias);
                }
            } else {
                throw new KeyNotFoundException("SharedPreferences does not have the key for keyAlias: " + keyAlias);
            }
        } catch (Exception ex) {
            throw new KeyNotFoundException("Error occurred while retrieving key for keyAlias: " + keyAlias, ex);
        }
        return new SecretKeySpec(base64DecodedAESEncryptionKey, AES_KEY_ALGORITHM);
    }

    public synchronized void deleteKey(String keyAlias) {
        try {
            this.sharedPreferences.edit().remove(keyAlias).apply();
        } catch (Exception ex) {
            Log log = logger;
            log.error("Error in deleting the AES key identified by " + keyAlias + " from SharedPreferences.", ex);
        }
        return;
    }
}
