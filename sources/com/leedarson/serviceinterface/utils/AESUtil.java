package com.leedarson.serviceinterface.utils;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Base64;
import androidx.annotation.RequiresApi;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.security.Key;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AESUtil.kt */
public final class AESUtil {
    @NotNull
    public static final AESUtil INSTANCE = new AESUtil();
    private static final int IV_BLOCK_SIZE = 16;
    @Nullable
    private static SecretKey _innerSecretKey;
    public static ChangeQuickRedirect changeQuickRedirect;

    private AESUtil() {
    }

    @RequiresApi(23)
    @Nullable
    public final SecretKey getKeyFromKeyStore(@NotNull String alias) {
        SecretKey key;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{alias}, this, changeQuickRedirect, false, 9229, new Class[]{String.class}, SecretKey.class);
        if (proxy.isSupported) {
            return (SecretKey) proxy.result;
        }
        k.e(alias, "alias");
        SecretKey key2 = _innerSecretKey;
        if (key2 == null) {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load((KeyStore.LoadStoreParameter) null);
            if (keyStore.containsAlias(alias)) {
                Key key3 = keyStore.getKey(alias, (char[]) null);
                if (key3 != null) {
                    key = (SecretKey) key3;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type javax.crypto.SecretKey");
                }
            } else {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                KeyGenParameterSpec parameterSpec = new KeyGenParameterSpec.Builder(alias, 3).setBlockModes(new String[]{"CBC"}).setUserAuthenticationRequired(false).setEncryptionPaddings(new String[]{"PKCS7Padding"}).build();
                k.d(parameterSpec, "Builder(alias,\n                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT  //用于加密和解密\n            ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)  // AEC_CBC\n                    .setUserAuthenticationRequired(false)   // 是否需要用户认证\n                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)  //AES算法的PADDING, 和前面的AESUtil里保持统一\n                    .build()");
                keyGenerator.init(parameterSpec);
                SecretKey generateKey = keyGenerator.generateKey();
                k.d(generateKey, "keyGenerator.generateKey()");
                key = generateKey;
            }
            _innerSecretKey = key;
            return key;
        }
        SecretKey secretKey = key2;
        return key2;
    }

    @Nullable
    public final byte[] encryptAES(@NotNull byte[] encryptBytes, @NotNull SecretKey encryptKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{encryptBytes, encryptKey}, this, changeQuickRedirect, false, 9230, new Class[]{byte[].class, SecretKey.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        k.e(encryptBytes, "encryptBytes");
        k.e(encryptKey, "encryptKey");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(1, encryptKey);
            byte[] doFinal = cipher.doFinal(encryptBytes);
            byte[] iv = cipher.getIV();
            k.d(iv, "cipher.iv");
            k.d(doFinal, "final");
            return kotlin.collections.k.m(iv, doFinal);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public final byte[] decryptAES(@NotNull byte[] decryptBytes, @NotNull SecretKey decryptKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{decryptBytes, decryptKey}, this, changeQuickRedirect, false, 9231, new Class[]{byte[].class, SecretKey.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        k.e(decryptBytes, "decryptBytes");
        k.e(decryptKey, "decryptKey");
        try {
            byte[] iv = kotlin.collections.k.h(decryptBytes, 0, 16);
            byte[] decryptData = kotlin.collections.k.h(decryptBytes, 16, decryptBytes.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(2, decryptKey, new IvParameterSpec(iv));
            return cipher.doFinal(decryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public final String encode(@NotNull String data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 9232, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        k.e(data, "data");
        SecretKey key = getKeyFromKeyStore("myKey");
        if (key == null) {
            return "";
        }
        SecretKey secretKey = key;
        AESUtil aESUtil = INSTANCE;
        byte[] bytes = data.getBytes(c.a);
        k.d(bytes, "(this as java.lang.String).getBytes(charset)");
        byte[] encryptData = aESUtil.encryptAES(bytes, key);
        if (encryptData == null) {
            return "";
        }
        String encodeToString = Base64.encodeToString(encryptData, 2);
        k.d(encodeToString, "encodeToString(encryptData, Base64.NO_WRAP)");
        return encodeToString;
    }

    @NotNull
    public final String decode(@NotNull String encryptData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{encryptData}, this, changeQuickRedirect, false, 9233, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        k.e(encryptData, "encryptData");
        SecretKey decryptKey = getKeyFromKeyStore("myKey");
        if (decryptKey == null) {
            return "";
        }
        SecretKey secretKey = decryptKey;
        byte[] value = Base64.decode(encryptData, 2);
        AESUtil aESUtil = INSTANCE;
        k.d(value, "value");
        byte[] decryptAES = aESUtil.decryptAES(value, decryptKey);
        if (decryptAES != null) {
            return new String(decryptAES, c.a);
        }
        return "";
    }
}
