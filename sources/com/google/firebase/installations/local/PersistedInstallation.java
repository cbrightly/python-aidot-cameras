package com.google.firebase.installations.local;

import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class PersistedInstallation {
    private static final String AUTH_TOKEN_KEY = "AuthToken";
    private static final String EXPIRES_IN_SECONDS_KEY = "ExpiresInSecs";
    private static final String FIREBASE_INSTALLATION_ID_KEY = "Fid";
    private static final String FIS_ERROR_KEY = "FisError";
    private static final String PERSISTED_STATUS_KEY = "Status";
    private static final String REFRESH_TOKEN_KEY = "RefreshToken";
    private static final String SETTINGS_FILE_NAME_PREFIX = "PersistedInstallation";
    private static final String TOKEN_CREATION_TIME_IN_SECONDS_KEY = "TokenCreationEpochInSecs";
    private File dataFile;
    @NonNull
    private final FirebaseApp firebaseApp;

    public enum RegistrationStatus {
        ATTEMPT_MIGRATION,
        NOT_GENERATED,
        UNREGISTERED,
        REGISTERED,
        REGISTER_ERROR
    }

    public PersistedInstallation(@NonNull FirebaseApp firebaseApp2) {
        this.firebaseApp = firebaseApp2;
    }

    private File getDataFile() {
        if (this.dataFile == null) {
            synchronized (this) {
                if (this.dataFile == null) {
                    File filesDir = this.firebaseApp.getApplicationContext().getFilesDir();
                    this.dataFile = new File(filesDir, "PersistedInstallation." + this.firebaseApp.getPersistenceKey() + ".json");
                }
            }
        }
        return this.dataFile;
    }

    @NonNull
    public PersistedInstallationEntry readPersistedInstallationEntryValue() {
        JSONObject json = readJSONFromFile();
        String fid = json.optString(FIREBASE_INSTALLATION_ID_KEY, (String) null);
        int status = json.optInt(PERSISTED_STATUS_KEY, RegistrationStatus.ATTEMPT_MIGRATION.ordinal());
        String authToken = json.optString(AUTH_TOKEN_KEY, (String) null);
        String refreshToken = json.optString(REFRESH_TOKEN_KEY, (String) null);
        long tokenCreationTime = json.optLong(TOKEN_CREATION_TIME_IN_SECONDS_KEY, 0);
        long expiresIn = json.optLong(EXPIRES_IN_SECONDS_KEY, 0);
        return PersistedInstallationEntry.builder().setFirebaseInstallationId(fid).setRegistrationStatus(RegistrationStatus.values()[status]).setAuthToken(authToken).setRefreshToken(refreshToken).setTokenCreationEpochInSecs(tokenCreationTime).setExpiresInSecs(expiresIn).setFisError(json.optString(FIS_ERROR_KEY, (String) null)).build();
    }

    private JSONObject readJSONFromFile() {
        FileInputStream fis;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] tmpBuf = new byte[16384];
        try {
            fis = new FileInputStream(getDataFile());
            while (true) {
                int numRead = fis.read(tmpBuf, 0, tmpBuf.length);
                if (numRead < 0) {
                    JSONObject jSONObject = new JSONObject(baos.toString());
                    fis.close();
                    return jSONObject;
                }
                baos.write(tmpBuf, 0, numRead);
            }
        } catch (IOException | JSONException e) {
            return new JSONObject();
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    @NonNull
    public PersistedInstallationEntry insertOrUpdatePersistedInstallationEntry(@NonNull PersistedInstallationEntry prefs) {
        try {
            JSONObject json = new JSONObject();
            json.put(FIREBASE_INSTALLATION_ID_KEY, (Object) prefs.getFirebaseInstallationId());
            json.put(PERSISTED_STATUS_KEY, prefs.getRegistrationStatus().ordinal());
            json.put(AUTH_TOKEN_KEY, (Object) prefs.getAuthToken());
            json.put(REFRESH_TOKEN_KEY, (Object) prefs.getRefreshToken());
            json.put(TOKEN_CREATION_TIME_IN_SECONDS_KEY, prefs.getTokenCreationEpochInSecs());
            json.put(EXPIRES_IN_SECONDS_KEY, prefs.getExpiresInSecs());
            json.put(FIS_ERROR_KEY, (Object) prefs.getFisError());
            File tmpFile = File.createTempFile(SETTINGS_FILE_NAME_PREFIX, "tmp", this.firebaseApp.getApplicationContext().getFilesDir());
            FileOutputStream fos = new FileOutputStream(tmpFile);
            fos.write(json.toString().getBytes("UTF-8"));
            fos.close();
            if (tmpFile.renameTo(getDataFile())) {
                return prefs;
            }
            throw new IOException("unable to rename the tmpfile to PersistedInstallation");
        } catch (IOException | JSONException e) {
        }
    }

    public void clearForTesting() {
        getDataFile().delete();
    }
}
