package com.google.firebase.messaging;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.AnyThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.cloudmessaging.Rpc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class GmsRpc {
    static final String CMD_RST = "RST";
    static final String CMD_RST_FULL = "RST_FULL";
    static final String CMD_SYNC = "SYNC";
    static final String ERROR_INSTANCE_ID_RESET = "INSTANCE_ID_RESET";
    static final String ERROR_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    static final String ERROR_INTERNAL_SERVER_ERROR_ALT = "InternalServerError";
    static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    private static final String EXTRA_DELETE = "delete";
    private static final String EXTRA_ERROR = "error";
    private static final String EXTRA_IID_OPERATION = "iid-operation";
    private static final String EXTRA_REGISTRATION_ID = "registration_id";
    private static final String EXTRA_SCOPE = "scope";
    private static final String EXTRA_SENDER = "sender";
    private static final String EXTRA_SUBTYPE = "subtype";
    private static final String EXTRA_TOPIC = "gcm.topic";
    private static final String EXTRA_UNREGISTERED = "unregistered";
    static final String FIREBASE_IID_HEARTBEAT_TAG = "fire-iid";
    private static final String PARAM_APP_VER_CODE = "app_ver";
    private static final String PARAM_APP_VER_NAME = "app_ver_name";
    private static final String PARAM_CLIENT_VER = "cliv";
    private static final String PARAM_FIREBASE_APP_NAME_HASH = "firebase-app-name-hash";
    private static final String PARAM_FIS_AUTH_TOKEN = "Goog-Firebase-Installations-Auth";
    private static final String PARAM_GMP_APP_ID = "gmp_app_id";
    private static final String PARAM_GMS_VER = "gmsv";
    private static final String PARAM_HEARTBEAT_CODE = "Firebase-Client-Log-Type";
    private static final String PARAM_INSTANCE_ID = "appid";
    private static final String PARAM_OS_VER = "osv";
    private static final String PARAM_USER_AGENT = "Firebase-Client";
    private static final String SCOPE_ALL = "*";
    static final String TAG = "FirebaseMessaging";
    private static final String TOPIC_PREFIX = "/topics/";
    private final FirebaseApp app;
    private final FirebaseInstallationsApi firebaseInstallations;
    private final Provider<HeartBeatInfo> heartbeatInfo;
    private final Metadata metadata;
    private final Rpc rpc;
    private final Provider<UserAgentPublisher> userAgentPublisher;

    GmsRpc(FirebaseApp app2, Metadata metadata2, Provider<UserAgentPublisher> userAgentPublisher2, Provider<HeartBeatInfo> heartbeatInfo2, FirebaseInstallationsApi firebaseInstallations2) {
        this(app2, metadata2, new Rpc(app2.getApplicationContext()), userAgentPublisher2, heartbeatInfo2, firebaseInstallations2);
    }

    @VisibleForTesting
    GmsRpc(FirebaseApp app2, Metadata metadata2, Rpc rpc2, Provider<UserAgentPublisher> userAgentPublisher2, Provider<HeartBeatInfo> heartbeatInfo2, FirebaseInstallationsApi firebaseInstallations2) {
        this.app = app2;
        this.metadata = metadata2;
        this.rpc = rpc2;
        this.userAgentPublisher = userAgentPublisher2;
        this.heartbeatInfo = heartbeatInfo2;
        this.firebaseInstallations = firebaseInstallations2;
    }

    /* access modifiers changed from: package-private */
    public Task<String> getToken() {
        return extractResponseWhenComplete(startRpc(Metadata.getDefaultSenderId(this.app), "*", new Bundle()));
    }

    /* access modifiers changed from: package-private */
    public Task<?> deleteToken() {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_DELETE, "1");
        return extractResponseWhenComplete(startRpc(Metadata.getDefaultSenderId(this.app), "*", extras));
    }

    /* access modifiers changed from: package-private */
    public Task<?> subscribeToTopic(String cachedToken, String topic) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_TOPIC, TOPIC_PREFIX + topic);
        return extractResponseWhenComplete(startRpc(cachedToken, TOPIC_PREFIX + topic, extras));
    }

    /* access modifiers changed from: package-private */
    public Task<?> unsubscribeFromTopic(String cachedToken, String topic) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_TOPIC, TOPIC_PREFIX + topic);
        extras.putString(EXTRA_DELETE, "1");
        return extractResponseWhenComplete(startRpc(cachedToken, TOPIC_PREFIX + topic, extras));
    }

    private Task<Bundle> startRpc(String to, String scope, Bundle extras) {
        try {
            setDefaultAttributesToBundle(to, scope, extras);
            return this.rpc.send(extras);
        } catch (InterruptedException | ExecutionException e) {
            return Tasks.forException(e);
        }
    }

    private static String base64UrlSafe(byte[] data) {
        return Base64.encodeToString(data, 11);
    }

    private String getHashedFirebaseAppName() {
        try {
            return base64UrlSafe(MessageDigest.getInstance("SHA-1").digest(this.app.getName().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "[HASH-ERROR]";
        }
    }

    private void setDefaultAttributesToBundle(String to, String scope, Bundle extras) {
        HeartBeatInfo.HeartBeat heartbeat;
        extras.putString(EXTRA_SCOPE, scope);
        extras.putString(EXTRA_SENDER, to);
        extras.putString(EXTRA_SUBTYPE, to);
        extras.putString(PARAM_GMP_APP_ID, this.app.getOptions().getApplicationId());
        extras.putString(PARAM_GMS_VER, Integer.toString(this.metadata.getGmsVersionCode()));
        extras.putString(PARAM_OS_VER, Integer.toString(Build.VERSION.SDK_INT));
        extras.putString(PARAM_APP_VER_CODE, this.metadata.getAppVersionCode());
        extras.putString(PARAM_APP_VER_NAME, this.metadata.getAppVersionName());
        extras.putString(PARAM_FIREBASE_APP_NAME_HASH, getHashedFirebaseAppName());
        try {
            String fisAuthToken = ((InstallationTokenResult) Tasks.await(this.firebaseInstallations.getToken(false))).getToken();
            if (!TextUtils.isEmpty(fisAuthToken)) {
                extras.putString(PARAM_FIS_AUTH_TOKEN, fisAuthToken);
            } else {
                Log.w("FirebaseMessaging", "FIS auth token is empty");
            }
        } catch (InterruptedException | ExecutionException e) {
            Log.e("FirebaseMessaging", "Failed to get FIS auth token", e);
        }
        extras.putString(PARAM_INSTANCE_ID, (String) Tasks.await(this.firebaseInstallations.getId()));
        extras.putString(PARAM_CLIENT_VER, "fcm-" + BuildConfig.VERSION_NAME);
        HeartBeatInfo heartbeatInfoObject = this.heartbeatInfo.get();
        UserAgentPublisher userAgentPublisherObject = this.userAgentPublisher.get();
        if (heartbeatInfoObject != null && userAgentPublisherObject != null && (heartbeat = heartbeatInfoObject.getHeartBeatCode(FIREBASE_IID_HEARTBEAT_TAG)) != HeartBeatInfo.HeartBeat.NONE) {
            extras.putString(PARAM_HEARTBEAT_CODE, Integer.toString(heartbeat.getCode()));
            extras.putString(PARAM_USER_AGENT, userAgentPublisherObject.getUserAgent());
        }
    }

    @AnyThread
    private String handleResponse(Bundle response) {
        if (response != null) {
            String token = response.getString(EXTRA_REGISTRATION_ID);
            if (token != null) {
                return token;
            }
            String unregisteredPackage = response.getString(EXTRA_UNREGISTERED);
            if (unregisteredPackage != null) {
                return unregisteredPackage;
            }
            String error = response.getString("error");
            if (CMD_RST.equals(error)) {
                throw new IOException(ERROR_INSTANCE_ID_RESET);
            } else if (error != null) {
                throw new IOException(error);
            } else {
                Log.w("FirebaseMessaging", "Unexpected response: " + response, new Throwable());
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
        } else {
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        }
    }

    private Task<String> extractResponseWhenComplete(Task<Bundle> rpcTask) {
        return rpcTask.continueWith(x.c, new v(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$extractResponseWhenComplete$0 */
    public /* synthetic */ String a(Task task) {
        return handleResponse((Bundle) task.getResult(IOException.class));
    }

    static boolean isErrorMessageForRetryableError(String errorMessage) {
        return ERROR_SERVICE_NOT_AVAILABLE.equals(errorMessage) || ERROR_INTERNAL_SERVER_ERROR.equals(errorMessage) || ERROR_INTERNAL_SERVER_ERROR_ALT.equals(errorMessage);
    }
}
