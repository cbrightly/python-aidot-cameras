package com.google.firebase.installations.local;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.stats.CodePackage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.e;

public class IidStore {
    private static final String[] ALLOWABLE_SCOPES = {e.ANY_MARKER, FirebaseMessaging.INSTANCE_ID_SCOPE, CodePackage.GCM, ""};
    private static final String IID_SHARED_PREFS_NAME = "com.google.android.gms.appid";
    private static final String JSON_ENCODED_PREFIX = "{";
    private static final String JSON_TOKEN_KEY = "token";
    private static final String STORE_KEY_ID = "|S|id";
    private static final String STORE_KEY_PUB = "|S||P|";
    private static final String STORE_KEY_SEPARATOR = "|";
    private static final String STORE_KEY_TOKEN = "|T|";
    private final String defaultSenderId;
    @GuardedBy("iidPrefs")
    private final SharedPreferences iidPrefs;

    public IidStore(@NonNull FirebaseApp firebaseApp) {
        this.iidPrefs = firebaseApp.getApplicationContext().getSharedPreferences(IID_SHARED_PREFS_NAME, 0);
        this.defaultSenderId = getDefaultSenderId(firebaseApp);
    }

    @VisibleForTesting
    public IidStore(@NonNull SharedPreferences iidPrefs2, @Nullable String defaultSenderId2) {
        this.iidPrefs = iidPrefs2;
        this.defaultSenderId = defaultSenderId2;
    }

    private static String getDefaultSenderId(FirebaseApp app) {
        String senderId = app.getOptions().getGcmSenderId();
        if (senderId != null) {
            return senderId;
        }
        String appId = app.getOptions().getApplicationId();
        if (!appId.startsWith("1:") && !appId.startsWith("2:")) {
            return appId;
        }
        String[] parts = appId.split(":");
        if (parts.length != 4) {
            return null;
        }
        String projectNumber = parts[1];
        if (projectNumber.isEmpty()) {
            return null;
        }
        return projectNumber;
    }

    private String createTokenKey(@NonNull String senderId, @NonNull String scope) {
        return STORE_KEY_TOKEN + senderId + STORE_KEY_SEPARATOR + scope;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        return r1;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readToken() {
        /*
            r8 = this;
            android.content.SharedPreferences r0 = r8.iidPrefs
            monitor-enter(r0)
            java.lang.String[] r1 = ALLOWABLE_SCOPES     // Catch:{ all -> 0x0036 }
            int r2 = r1.length     // Catch:{ all -> 0x0036 }
            r3 = 0
        L_0x0007:
            r4 = 0
            if (r3 >= r2) goto L_0x0034
            r5 = r1[r3]     // Catch:{ all -> 0x0036 }
            java.lang.String r6 = r8.defaultSenderId     // Catch:{ all -> 0x0036 }
            java.lang.String r6 = r8.createTokenKey(r6, r5)     // Catch:{ all -> 0x0036 }
            android.content.SharedPreferences r7 = r8.iidPrefs     // Catch:{ all -> 0x0036 }
            java.lang.String r4 = r7.getString(r6, r4)     // Catch:{ all -> 0x0036 }
            if (r4 == 0) goto L_0x0031
            boolean r7 = r4.isEmpty()     // Catch:{ all -> 0x0036 }
            if (r7 != 0) goto L_0x0031
            java.lang.String r1 = "{"
            boolean r1 = r4.startsWith(r1)     // Catch:{ all -> 0x0036 }
            if (r1 == 0) goto L_0x002e
            java.lang.String r1 = r8.parseIidTokenFromJson(r4)     // Catch:{ all -> 0x0036 }
            goto L_0x002f
        L_0x002e:
            r1 = r4
        L_0x002f:
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            return r1
        L_0x0031:
            int r3 = r3 + 1
            goto L_0x0007
        L_0x0034:
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            return r4
        L_0x0036:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.installations.local.IidStore.readToken():java.lang.String");
    }

    private String parseIidTokenFromJson(String token) {
        try {
            return new JSONObject(token).getString(JSON_TOKEN_KEY);
        } catch (JSONException e) {
            return null;
        }
    }

    @Nullable
    public String readIid() {
        synchronized (this.iidPrefs) {
            String id = readInstanceIdFromLocalStorage();
            if (id != null) {
                return id;
            }
            String readPublicKeyFromLocalStorageAndCalculateInstanceId = readPublicKeyFromLocalStorageAndCalculateInstanceId();
            return readPublicKeyFromLocalStorageAndCalculateInstanceId;
        }
    }

    @Nullable
    private String readInstanceIdFromLocalStorage() {
        String string;
        synchronized (this.iidPrefs) {
            string = this.iidPrefs.getString(STORE_KEY_ID, (String) null);
        }
        return string;
    }

    @Nullable
    private String readPublicKeyFromLocalStorageAndCalculateInstanceId() {
        synchronized (this.iidPrefs) {
            String base64PublicKey = this.iidPrefs.getString(STORE_KEY_PUB, (String) null);
            if (base64PublicKey == null) {
                return null;
            }
            PublicKey publicKey = parseKey(base64PublicKey);
            if (publicKey == null) {
                return null;
            }
            String idFromPublicKey = getIdFromPublicKey(publicKey);
            return idFromPublicKey;
        }
    }

    @Nullable
    private static String getIdFromPublicKey(@NonNull PublicKey publicKey) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(publicKey.getEncoded());
            digest[0] = (byte) (((digest[0] & 15) + 112) & 255);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("ContentValues", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    @Nullable
    private PublicKey parseKey(String base64PublicKey) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(base64PublicKey, 8)));
        } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.w("ContentValues", "Invalid key stored " + e);
            return null;
        }
    }
}
