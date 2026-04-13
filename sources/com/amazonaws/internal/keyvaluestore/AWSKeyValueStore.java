package com.amazonaws.internal.keyvaluestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.Base64;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

public class AWSKeyValueStore {
    private static final int ANDROID_API_LEVEL_10 = 10;
    private static final int ANDROID_API_LEVEL_18 = 18;
    private static final int ANDROID_API_LEVEL_23 = 23;
    private static final int AWS_KEY_VALUE_STORE_VERSION = 1;
    private static final String CHARSET_NAME = "UTF-8";
    private static final String CIPHER_AES_GCM_NOPADDING = "AES/GCM/NoPadding";
    private static final int CIPHER_AES_GCM_NOPADDING_IV_LENGTH_IN_BYTES = 12;
    private static final int CIPHER_AES_GCM_NOPADDING_TAG_LENGTH_LENGTH_IN_BITS = 128;
    static final String SHARED_PREFERENCES_DATA_IDENTIFIER_SUFFIX = ".encrypted";
    static final String SHARED_PREFERENCES_ENCRYPTION_KEY_NAMESPACE_SUFFIX = ".encryptionkey";
    static final String SHARED_PREFERENCES_IV_SUFFIX = ".iv";
    static final String SHARED_PREFERENCES_STORE_VERSION_SUFFIX = ".keyvaluestoreversion";
    static Map<String, HashMap<String, String>> cacheFactory = new HashMap();
    private static final Log logger = LogFactory.getLog(AWSKeyValueStore.class);
    private int apiLevel;
    private Map<String, String> cache;
    Context context;
    private boolean isPersistenceEnabled;
    KeyProvider keyProvider;
    private SecureRandom secureRandom = new SecureRandom();
    SharedPreferences sharedPreferencesForData;
    SharedPreferences sharedPreferencesForEncryptionMaterials;
    private final String sharedPreferencesName;

    private static Map<String, String> getCacheForKey(String key) {
        if (cacheFactory.containsKey(key)) {
            return cacheFactory.get(key);
        }
        HashMap<String, String> cache2 = new HashMap<>();
        cacheFactory.put(key, cache2);
        return cache2;
    }

    public AWSKeyValueStore(Context context2, String sharedPreferencesName2, boolean isPersistenceEnabled2) {
        this.cache = getCacheForKey(sharedPreferencesName2);
        this.sharedPreferencesName = sharedPreferencesName2;
        this.apiLevel = Build.VERSION.SDK_INT;
        this.context = context2;
        setPersistenceEnabled(isPersistenceEnabled2);
    }

    public synchronized void setPersistenceEnabled(boolean isPersistenceEnabled2) {
        try {
            boolean previousIsPersistenceEnabled = this.isPersistenceEnabled;
            this.isPersistenceEnabled = isPersistenceEnabled2;
            if (isPersistenceEnabled2 && !previousIsPersistenceEnabled) {
                this.sharedPreferencesForData = this.context.getSharedPreferences(this.sharedPreferencesName, 0);
                Context context2 = this.context;
                this.sharedPreferencesForEncryptionMaterials = context2.getSharedPreferences(this.sharedPreferencesName + SHARED_PREFERENCES_ENCRYPTION_KEY_NAMESPACE_SUFFIX, 0);
                initKeyProviderBasedOnAPILevel();
                Log log = logger;
                log.info("Detected Android API Level = " + this.apiLevel);
                log.info("Creating the AWSKeyValueStore with key for sharedPreferencesForData = " + this.sharedPreferencesName);
                onMigrateFromNoEncryption();
            } else if (!isPersistenceEnabled2) {
                logger.info("Persistence is disabled. Data will be accessed from memory.");
            }
            if (!isPersistenceEnabled2 && previousIsPersistenceEnabled) {
                this.sharedPreferencesForData.edit().clear().apply();
            }
        } catch (Exception ex) {
            Log log2 = logger;
            log2.error("Error in enabling persistence for " + this.sharedPreferencesName, ex);
        }
        return;
    }

    public synchronized boolean contains(String dataKey) {
        if (this.isPersistenceEnabled) {
            return this.sharedPreferencesForData.contains(getDataKeyUsedInPersistentStore(dataKey));
        }
        return this.cache.containsKey(dataKey);
    }

    public synchronized String get(String dataKey) {
        if (dataKey == null) {
            return null;
        }
        if (!this.isPersistenceEnabled) {
            return this.cache.get(dataKey);
        }
        String dataKeyInPersistentStore = getDataKeyUsedInPersistentStore(dataKey);
        Key decryptionKey = retrieveEncryptionKey(getEncryptionKeyAlias());
        if (decryptionKey == null) {
            Log log = logger;
            log.error("Error in retrieving the decryption key used to decrypt the data from the persistent store. Returning null for the requested dataKey = " + dataKey);
            return null;
        } else if (!this.sharedPreferencesForData.contains(dataKeyInPersistentStore)) {
            return null;
        } else {
            try {
                SharedPreferences sharedPreferences = this.sharedPreferencesForData;
                if (Integer.parseInt(sharedPreferences.getString(dataKeyInPersistentStore + SHARED_PREFERENCES_STORE_VERSION_SUFFIX, (String) null)) != 1) {
                    Log log2 = logger;
                    log2.error("The version of the data read from SharedPreferences for " + dataKey + " does not match the version of the store.");
                    return null;
                }
                String decryptedDataInString = decrypt(decryptionKey, getInitializationVector(dataKeyInPersistentStore), this.sharedPreferencesForData.getString(dataKeyInPersistentStore, (String) null));
                this.cache.put(dataKey, decryptedDataInString);
                return decryptedDataInString;
            } catch (Exception ex) {
                Log log3 = logger;
                log3.error("Error in retrieving value for dataKey = " + dataKey, ex);
                remove(dataKey);
                return null;
            }
        }
    }

    public synchronized void put(String dataKey, String value) {
        if (dataKey == null) {
            logger.error("dataKey is null.");
            return;
        }
        this.cache.put(dataKey, value);
        if (this.isPersistenceEnabled) {
            if (value == null) {
                logger.debug("Value is null. Removing the data, IV and version from SharedPreferences");
                this.cache.remove(dataKey);
                remove(dataKey);
                return;
            }
            String dataKeyInPersistentStore = getDataKeyUsedInPersistentStore(dataKey);
            String encryptionKeyAlias = getEncryptionKeyAlias();
            Key encryptionKey = retrieveEncryptionKey(encryptionKeyAlias);
            if (encryptionKey == null) {
                Log log = logger;
                log.warn("No encryption key found for encryptionKeyAlias: " + encryptionKeyAlias);
                encryptionKey = generateEncryptionKey(encryptionKeyAlias);
                if (encryptionKey == null) {
                    log.error("Error in generating the encryption key for encryptionKeyAlias: " + encryptionKeyAlias + " used to encrypt the data before storing. Skipping persisting the data in the persistent store.");
                    return;
                }
            }
            try {
                byte[] iv = generateInitializationVector();
                if (iv != null) {
                    String base64EncodedEncryptedString = encrypt(encryptionKey, getAlgorithmParameterSpecForIV(iv), value);
                    String base64EncodedIV = Base64.encodeAsString(iv);
                    if (base64EncodedIV != null) {
                        SharedPreferences.Editor putString = this.sharedPreferencesForData.edit().putString(dataKeyInPersistentStore, base64EncodedEncryptedString);
                        SharedPreferences.Editor putString2 = putString.putString(dataKeyInPersistentStore + SHARED_PREFERENCES_IV_SUFFIX, base64EncodedIV);
                        putString2.putString(dataKeyInPersistentStore + SHARED_PREFERENCES_STORE_VERSION_SUFFIX, String.valueOf(1)).apply();
                    } else {
                        throw new Exception("Error in Base64 encoding the IV for dataKey = " + dataKey);
                    }
                } else {
                    throw new Exception("The generated IV for dataKey = " + dataKey + " is null.");
                }
            } catch (Exception ex) {
                Log log2 = logger;
                log2.error("Error in storing value for dataKey = " + dataKey + ". This data has not been stored in the persistent store.", ex);
            }
        } else {
            return;
        }
        return;
    }

    public synchronized void remove(String dataKey) {
        this.cache.remove(dataKey);
        if (this.isPersistenceEnabled) {
            String keyUsedInPersistentStore = getDataKeyUsedInPersistentStore(dataKey);
            SharedPreferences.Editor remove = this.sharedPreferencesForData.edit().remove(keyUsedInPersistentStore);
            SharedPreferences.Editor remove2 = remove.remove(keyUsedInPersistentStore + SHARED_PREFERENCES_IV_SUFFIX);
            remove2.remove(keyUsedInPersistentStore + SHARED_PREFERENCES_STORE_VERSION_SUFFIX).apply();
        }
    }

    public synchronized void clear() {
        this.cache.clear();
        if (this.isPersistenceEnabled) {
            this.sharedPreferencesForData.edit().clear().apply();
        }
    }

    private String encrypt(Key encryptionKey, AlgorithmParameterSpec ivSpec, String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_AES_GCM_NOPADDING);
            cipher.init(1, encryptionKey, ivSpec);
            return Base64.encodeAsString(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (Exception ex) {
            logger.error("Error in encrypting data. ", ex);
            return null;
        }
    }

    private String decrypt(Key decryptionKey, AlgorithmParameterSpec ivSpec, String encryptedData) {
        try {
            byte[] encryptedDecodedData = Base64.decode(encryptedData);
            Cipher cipher = Cipher.getInstance(CIPHER_AES_GCM_NOPADDING);
            cipher.init(2, decryptionKey, ivSpec);
            return new String(cipher.doFinal(encryptedDecodedData), "UTF-8");
        } catch (Exception ex) {
            logger.error("Error in decrypting data. ", ex);
            return null;
        }
    }

    private AlgorithmParameterSpec getInitializationVector(String keyOfDataInSharedPreferences) {
        String keyOfIV = keyOfDataInSharedPreferences + SHARED_PREFERENCES_IV_SUFFIX;
        if (this.sharedPreferencesForData.contains(keyOfIV)) {
            String initializationVectorInString = this.sharedPreferencesForData.getString(keyOfIV, (String) null);
            if (initializationVectorInString != null) {
                byte[] base64DecodedIV = Base64.decode(initializationVectorInString);
                if (base64DecodedIV != null && base64DecodedIV.length != 0) {
                    return getAlgorithmParameterSpecForIV(base64DecodedIV);
                }
                throw new Exception("Cannot base64 decode the initialization vector for " + keyOfDataInSharedPreferences + " read from SharedPreferences.");
            }
            throw new Exception("Cannot read the initialization vector for " + keyOfDataInSharedPreferences + " from SharedPreferences.");
        }
        throw new Exception("Initialization vector for " + keyOfDataInSharedPreferences + " is missing from the SharedPreferences.");
    }

    private byte[] generateInitializationVector() {
        byte[] initializationVector = new byte[12];
        this.secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    private AlgorithmParameterSpec getAlgorithmParameterSpecForIV(byte[] iv) {
        return this.apiLevel >= 23 ? new GCMParameterSpec(128, iv) : new IvParameterSpec(iv);
    }

    private synchronized Key retrieveEncryptionKey(String encryptionKeyAlias) {
        try {
        } catch (KeyNotFoundException keyNotFoundException) {
            Log log = logger;
            log.error(keyNotFoundException);
            log.info("Deleting the encryption key identified by the keyAlias: " + encryptionKeyAlias);
            this.keyProvider.deleteKey(encryptionKeyAlias);
            return null;
        }
        return this.keyProvider.retrieveKey(encryptionKeyAlias);
    }

    /* access modifiers changed from: package-private */
    public synchronized Key generateEncryptionKey(String encryptionKeyAlias) {
        try {
        } catch (KeyNotGeneratedException keyNotGeneratedException) {
            logger.error("Encryption Key cannot be generated successfully.", keyNotGeneratedException);
            return null;
        }
        return this.keyProvider.generateKey(encryptionKeyAlias);
    }

    private String getDataKeyUsedInPersistentStore(String key) {
        if (key == null) {
            return null;
        }
        return key + SHARED_PREFERENCES_DATA_IDENTIFIER_SUFFIX;
    }

    private String getEncryptionKeyAlias() {
        int i = this.apiLevel;
        if (i >= 23) {
            return this.sharedPreferencesName + ".aesKeyStoreAlias";
        } else if (i >= 18) {
            return this.sharedPreferencesName + ".rsaKeyStoreAlias";
        } else if (i >= 10) {
            return "AesGcmNoPaddingEncryption10-encryption-key";
        } else {
            Log log = logger;
            log.error("API Level " + String.valueOf(Build.VERSION.SDK_INT) + " not supported by the SDK. Setting persistence to false.");
            this.isPersistenceEnabled = false;
            return null;
        }
    }

    private void initKeyProviderBasedOnAPILevel() {
        int i = this.apiLevel;
        if (i >= 23) {
            this.keyProvider = new KeyProvider23();
        } else if (i >= 18) {
            this.keyProvider = new KeyProvider18(this.context, this.sharedPreferencesForEncryptionMaterials);
        } else if (i >= 10) {
            this.keyProvider = new KeyProvider10(this.sharedPreferencesForEncryptionMaterials);
        } else {
            Log log = logger;
            log.error("API Level " + String.valueOf(Build.VERSION.SDK_INT) + " not supported by the SDK. Setting persistence to false.");
            this.isPersistenceEnabled = false;
        }
    }

    private void onMigrateFromNoEncryption() {
        Map<String, ?> map = this.sharedPreferencesForData.getAll();
        for (String keyOfUnencryptedData : map.keySet()) {
            if (!keyOfUnencryptedData.endsWith(SHARED_PREFERENCES_DATA_IDENTIFIER_SUFFIX) && !keyOfUnencryptedData.endsWith(SHARED_PREFERENCES_IV_SUFFIX) && !keyOfUnencryptedData.endsWith(SHARED_PREFERENCES_STORE_VERSION_SUFFIX)) {
                if (map.get(keyOfUnencryptedData) instanceof Long) {
                    put(keyOfUnencryptedData, String.valueOf(Long.valueOf(this.sharedPreferencesForData.getLong(keyOfUnencryptedData, 0))));
                } else if (map.get(keyOfUnencryptedData) instanceof String) {
                    put(keyOfUnencryptedData, this.sharedPreferencesForData.getString(keyOfUnencryptedData, (String) null));
                } else if (map.get(keyOfUnencryptedData) instanceof Float) {
                    put(keyOfUnencryptedData, String.valueOf(Float.valueOf(this.sharedPreferencesForData.getFloat(keyOfUnencryptedData, 0.0f))));
                } else if (map.get(keyOfUnencryptedData) instanceof Boolean) {
                    put(keyOfUnencryptedData, String.valueOf(Boolean.valueOf(this.sharedPreferencesForData.getBoolean(keyOfUnencryptedData, false))));
                } else if (map.get(keyOfUnencryptedData) instanceof Integer) {
                    put(keyOfUnencryptedData, String.valueOf(Integer.valueOf(this.sharedPreferencesForData.getInt(keyOfUnencryptedData, 0))));
                } else if (map.get(keyOfUnencryptedData) instanceof Set) {
                    StringBuilder stringBuilder = new StringBuilder();
                    Iterator<String> setIterator = ((Set) map.get(keyOfUnencryptedData)).iterator();
                    while (setIterator.hasNext()) {
                        stringBuilder.append(setIterator.next());
                        if (setIterator.hasNext()) {
                            stringBuilder.append(",");
                        }
                    }
                    put(keyOfUnencryptedData, stringBuilder.toString());
                }
                this.sharedPreferencesForData.edit().remove(keyOfUnencryptedData).apply();
            }
        }
    }
}
