package com.amazonaws.internal.keyvaluestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.KeyPairGeneratorSpec;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.Base64;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Calendar;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

public class KeyProvider18 implements KeyProvider {
    static final String ANDROID_KEY_STORE_NAME = "AndroidKeyStore";
    static final String AWS_KEY_VALUE_STORE_VERSION_1_KEY_STORE_ALIAS_FOR_RSA_SUFFIX = ".rsaKeyStoreAlias";
    static final int CIPHER_AES_GCM_NOPADDING_KEY_LENGTH_IN_BITS = 256;
    static final String CIPHER_PROVIDER_NAME_FOR_RSA = "AndroidOpenSSL";
    static final String CIPHER_RSA_MODE = "RSA/ECB/PKCS1Padding";
    static final String ENCRYPTED_AES_KEY = "AesGcmNoPadding18-encrypted-encryption-key";
    static final String KEY_ALGORITHM_AES = "AES";
    static final String KEY_ALGORITHM_RSA = "RSA";
    private static final Log logger = LogFactory.getLog(KeyProvider18.class);
    private Context context;
    private SecureRandom secureRandom;
    private SharedPreferences sharedPreferences;

    KeyProvider18(Context context2, SharedPreferences sharedPreferences2) {
        this.context = context2;
        this.sharedPreferences = sharedPreferences2;
    }

    private byte[] rsaEncrypt(String keyAlias, byte[] rawData) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            Cipher encryptCipher = Cipher.getInstance(CIPHER_RSA_MODE, CIPHER_PROVIDER_NAME_FOR_RSA);
            encryptCipher.init(1, ((KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, (KeyStore.ProtectionParameter) null)).getCertificate().getPublicKey());
            return encryptCipher.doFinal(rawData);
        } catch (Exception ex) {
            Log log = logger;
            log.error("Exception occurred while encrypting data. " + ex.getMessage());
            return null;
        }
    }

    private byte[] rsaDecrypt(String keyAlias, byte[] encryptedData) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            Cipher decryptCipher = Cipher.getInstance(CIPHER_RSA_MODE, CIPHER_PROVIDER_NAME_FOR_RSA);
            decryptCipher.init(2, ((KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, (KeyStore.ProtectionParameter) null)).getPrivateKey());
            return decryptCipher.doFinal(encryptedData);
        } catch (Exception ex) {
            logger.error("Exception occurred while decrypting the encrypted AES key. ", ex);
            return null;
        }
    }

    public synchronized Key generateKey(String rsaKeyAlias) {
        SecretKey aesEncryptionKey;
        try {
            KeyStore.getInstance(ANDROID_KEY_STORE_NAME).load((KeyStore.LoadStoreParameter) null);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(1, 30);
            KeyPairGeneratorSpec.Builder alias = new KeyPairGeneratorSpec.Builder(this.context).setAlias(rsaKeyAlias);
            KeyPairGeneratorSpec spec = alias.setSubject(new X500Principal("CN=" + rsaKeyAlias)).setSerialNumber(BigInteger.TEN).setStartDate(start.getTime()).setEndDate(end.getTime()).build();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, ANDROID_KEY_STORE_NAME);
            kpg.initialize(spec);
            kpg.generateKeyPair();
            this.secureRandom = new SecureRandom();
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
            generator.init(256, this.secureRandom);
            aesEncryptionKey = generator.generateKey();
            if (aesEncryptionKey != null) {
                byte[] aesEncryptionKeyInBytes = aesEncryptionKey.getEncoded();
                if (aesEncryptionKeyInBytes == null || aesEncryptionKeyInBytes.length == 0) {
                    throw new KeyNotGeneratedException("Error in generating the AES encryption key for the alias: AesGcmNoPadding18-encrypted-encryption-key");
                }
                byte[] rsaEncryptedAESKeyInBytes = rsaEncrypt(rsaKeyAlias, aesEncryptionKeyInBytes);
                if (rsaEncryptedAESKeyInBytes == null || rsaEncryptedAESKeyInBytes.length == 0) {
                    throw new KeyNotGeneratedException("Error in RSA encrypting the AES encryption key for the AES keyAlias: AesGcmNoPadding18-encrypted-encryption-key using the rsaKeyAlias: " + rsaKeyAlias);
                }
                String base64EncodedStringOfEncryptedAESKey = Base64.encodeAsString(rsaEncryptedAESKeyInBytes);
                if (base64EncodedStringOfEncryptedAESKey != null) {
                    this.sharedPreferences.edit().putString(ENCRYPTED_AES_KEY, base64EncodedStringOfEncryptedAESKey).apply();
                    logger.info("Generated and saved the Encrypted AES encryption key for the AES keyAlias: AesGcmNoPadding18-encrypted-encryption-key to SharedPreferences.");
                } else {
                    throw new KeyNotGeneratedException("Error in Base64 encoding of the Encrypted AES key for the AES keyAlias: AesGcmNoPadding18-encrypted-encryption-key using the rsaKeyAlias: " + rsaKeyAlias);
                }
            } else {
                throw new KeyNotGeneratedException("Error in generating the AES encryption key for the alias: AesGcmNoPadding18-encrypted-encryption-key");
            }
        } catch (Exception ex) {
            throw new KeyNotGeneratedException("Error in generating the RSA Encryption key for the rsaKeyAlias: " + rsaKeyAlias + " in " + ANDROID_KEY_STORE_NAME, ex);
        } catch (Exception ex2) {
            throw new KeyNotGeneratedException("Error in generating the AES key and RSA encrypting the AES key using the rsaKeyAlias: " + rsaKeyAlias + " in " + ANDROID_KEY_STORE_NAME, ex2);
        } catch (Throwable th) {
            throw th;
        }
        return aesEncryptionKey;
    }

    public synchronized Key retrieveKey(String keyAlias) {
        byte[] aesKey;
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            if (!keyStore.containsAlias(keyAlias)) {
                throw new KeyNotFoundException("The RSA Key identified by the alias: " + keyAlias + " cannot be found in " + ANDROID_KEY_STORE_NAME);
            } else if (this.sharedPreferences.contains(ENCRYPTED_AES_KEY)) {
                logger.debug("Loading the encryption key from SharedPreferences");
                String encryptedAesEncryptionKey = this.sharedPreferences.getString(ENCRYPTED_AES_KEY, (String) null);
                if (encryptedAesEncryptionKey != null) {
                    byte[] base64DecodedKey = Base64.decode(encryptedAesEncryptionKey);
                    if (base64DecodedKey == null || base64DecodedKey.length == 0) {
                        throw new KeyNotFoundException("Unable to Base64 decode the encrypted AES key identified by: AesGcmNoPadding18-encrypted-encryption-key");
                    }
                    aesKey = rsaDecrypt(keyAlias, base64DecodedKey);
                    if (aesKey == null || aesKey.length == 0) {
                        throw new KeyNotFoundException("Unable to RSA decrypt the encrypted AES key identified by: AesGcmNoPadding18-encrypted-encryption-key using the RSA key identified by keyAlias: " + keyAlias);
                    }
                } else {
                    throw new KeyNotFoundException("Unable to retrieve the encrypted AES Key identified by AesGcmNoPadding18-encrypted-encryption-key from the SharedPreferences.");
                }
            } else {
                throw new KeyNotFoundException("SharedPreferences does not have the key for keyAlias: AesGcmNoPadding18-encrypted-encryption-key");
            }
        } catch (Exception ex) {
            throw new KeyNotFoundException("Error occurred while accessing AndroidKeyStore to retrieve the key for keyAlias: " + keyAlias, ex);
        }
        return new SecretKeySpec(aesKey, KEY_ALGORITHM_AES);
    }

    public synchronized void deleteKey(String keyAlias) {
        try {
            this.sharedPreferences.edit().remove(ENCRYPTED_AES_KEY).apply();
        } catch (Exception ex) {
            logger.error("Error in deleting the encrypted AES key identified by AesGcmNoPadding18-encrypted-encryption-key from SharedPreferences.", ex);
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE_NAME);
            keyStore.load((KeyStore.LoadStoreParameter) null);
            keyStore.deleteEntry(keyAlias);
        } catch (Exception ex2) {
            Log log = logger;
            log.error("Error in deleting the RSA Key identified by the keyAlias: " + keyAlias + " from " + ANDROID_KEY_STORE_NAME, ex2);
        }
        return;
    }
}
