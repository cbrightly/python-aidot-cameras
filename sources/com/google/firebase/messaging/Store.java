package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public class Store {
    static final String NO_BACKUP_FILE = "com.google.android.gms.appid-no-backup";
    static final String PREFERENCES = "com.google.android.gms.appid";
    private static final String SCOPE_ALL = "*";
    private static final String STORE_KEY_TOKEN = "|T|";
    final SharedPreferences store;

    public Store(Context context) {
        this.store = context.getSharedPreferences(PREFERENCES, 0);
        checkForRestore(context, NO_BACKUP_FILE);
    }

    private void checkForRestore(Context context, String fileName) {
        File file = new File(ContextCompat.getNoBackupFilesDir(context), fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i(Constants.TAG, "App restored, clearing state");
                    deleteAll();
                }
            } catch (IOException e) {
                if (Log.isLoggable(Constants.TAG, 3)) {
                    Log.d(Constants.TAG, "Error creating file in no backup dir: " + e.getMessage());
                }
            }
        }
    }

    public synchronized boolean isEmpty() {
        return this.store.getAll().isEmpty();
    }

    private String createTokenKey(String subtype, String audience) {
        return subtype + STORE_KEY_TOKEN + audience + "|" + "*";
    }

    public synchronized void deleteAll() {
        this.store.edit().clear().commit();
    }

    public synchronized Token getToken(String subtype, String audience) {
        return Token.parse(this.store.getString(createTokenKey(subtype, audience), (String) null));
    }

    public synchronized void saveToken(String subtype, String audience, String token, String appVersion) {
        String encodedToken = Token.encode(token, appVersion, System.currentTimeMillis());
        if (encodedToken != null) {
            SharedPreferences.Editor edit = this.store.edit();
            edit.putString(createTokenKey(subtype, audience), encodedToken);
            edit.commit();
        }
    }

    public synchronized void deleteToken(String subtype, String audience) {
        String key = createTokenKey(subtype, audience);
        SharedPreferences.Editor edit = this.store.edit();
        edit.remove(key);
        edit.commit();
    }

    public static class Token {
        private static final String KEY_APP_VERSION = "appVersion";
        private static final String KEY_TIMESTAMP = "timestamp";
        private static final String KEY_TOKEN = "token";
        private static final long REFRESH_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(7);
        final String appVersion;
        final long timestamp;
        final String token;

        private Token(String token2, String appVersion2, long timestamp2) {
            this.token = token2;
            this.appVersion = appVersion2;
            this.timestamp = timestamp2;
        }

        static Token parse(String s) {
            if (TextUtils.isEmpty(s)) {
                return null;
            }
            if (!s.startsWith("{")) {
                return new Token(s, (String) null, 0);
            }
            try {
                JSONObject json = new JSONObject(s);
                return new Token(json.getString(KEY_TOKEN), json.getString(KEY_APP_VERSION), json.getLong(KEY_TIMESTAMP));
            } catch (JSONException e) {
                Log.w(Constants.TAG, "Failed to parse token: " + e);
                return null;
            }
        }

        static String encode(String token2, String appVersion2, long timestamp2) {
            try {
                JSONObject json = new JSONObject();
                json.put(KEY_TOKEN, (Object) token2);
                json.put(KEY_APP_VERSION, (Object) appVersion2);
                json.put(KEY_TIMESTAMP, timestamp2);
                return json.toString();
            } catch (JSONException e) {
                Log.w(Constants.TAG, "Failed to encode token: " + e);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean needsRefresh(String appVersion2) {
            return System.currentTimeMillis() > this.timestamp + REFRESH_PERIOD_MILLIS || !appVersion2.equals(this.appVersion);
        }
    }
}
