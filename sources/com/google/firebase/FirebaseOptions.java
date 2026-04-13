package com.google.firebase;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.util.Strings;

public final class FirebaseOptions {
    private static final String API_KEY_RESOURCE_NAME = "google_api_key";
    private static final String APP_ID_RESOURCE_NAME = "google_app_id";
    private static final String DATABASE_URL_RESOURCE_NAME = "firebase_database_url";
    private static final String GA_TRACKING_ID_RESOURCE_NAME = "ga_trackingId";
    private static final String GCM_SENDER_ID_RESOURCE_NAME = "gcm_defaultSenderId";
    private static final String PROJECT_ID_RESOURCE_NAME = "project_id";
    private static final String STORAGE_BUCKET_RESOURCE_NAME = "google_storage_bucket";
    /* access modifiers changed from: private */
    public final String apiKey;
    /* access modifiers changed from: private */
    public final String applicationId;
    /* access modifiers changed from: private */
    public final String databaseUrl;
    /* access modifiers changed from: private */
    public final String gaTrackingId;
    /* access modifiers changed from: private */
    public final String gcmSenderId;
    /* access modifiers changed from: private */
    public final String projectId;
    /* access modifiers changed from: private */
    public final String storageBucket;

    public static final class Builder {
        private String apiKey;
        private String applicationId;
        private String databaseUrl;
        private String gaTrackingId;
        private String gcmSenderId;
        private String projectId;
        private String storageBucket;

        public Builder() {
        }

        public Builder(@NonNull FirebaseOptions options) {
            this.applicationId = options.applicationId;
            this.apiKey = options.apiKey;
            this.databaseUrl = options.databaseUrl;
            this.gaTrackingId = options.gaTrackingId;
            this.gcmSenderId = options.gcmSenderId;
            this.storageBucket = options.storageBucket;
            this.projectId = options.projectId;
        }

        @NonNull
        public Builder setApiKey(@NonNull String apiKey2) {
            this.apiKey = Preconditions.checkNotEmpty(apiKey2, "ApiKey must be set.");
            return this;
        }

        @NonNull
        public Builder setApplicationId(@NonNull String applicationId2) {
            this.applicationId = Preconditions.checkNotEmpty(applicationId2, "ApplicationId must be set.");
            return this;
        }

        @NonNull
        public Builder setDatabaseUrl(@Nullable String databaseUrl2) {
            this.databaseUrl = databaseUrl2;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder setGaTrackingId(@Nullable String gaTrackingId2) {
            this.gaTrackingId = gaTrackingId2;
            return this;
        }

        @NonNull
        public Builder setGcmSenderId(@Nullable String gcmSenderId2) {
            this.gcmSenderId = gcmSenderId2;
            return this;
        }

        @NonNull
        public Builder setStorageBucket(@Nullable String storageBucket2) {
            this.storageBucket = storageBucket2;
            return this;
        }

        @NonNull
        public Builder setProjectId(@Nullable String projectId2) {
            this.projectId = projectId2;
            return this;
        }

        @NonNull
        public FirebaseOptions build() {
            return new FirebaseOptions(this.applicationId, this.apiKey, this.databaseUrl, this.gaTrackingId, this.gcmSenderId, this.storageBucket, this.projectId);
        }
    }

    private FirebaseOptions(@NonNull String applicationId2, @NonNull String apiKey2, @Nullable String databaseUrl2, @Nullable String gaTrackingId2, @Nullable String gcmSenderId2, @Nullable String storageBucket2, @Nullable String projectId2) {
        Preconditions.checkState(!Strings.isEmptyOrWhitespace(applicationId2), "ApplicationId must be set.");
        this.applicationId = applicationId2;
        this.apiKey = apiKey2;
        this.databaseUrl = databaseUrl2;
        this.gaTrackingId = gaTrackingId2;
        this.gcmSenderId = gcmSenderId2;
        this.storageBucket = storageBucket2;
        this.projectId = projectId2;
    }

    @Nullable
    public static FirebaseOptions fromResource(@NonNull Context context) {
        StringResourceValueReader reader = new StringResourceValueReader(context);
        String applicationId2 = reader.getString(APP_ID_RESOURCE_NAME);
        if (TextUtils.isEmpty(applicationId2)) {
            return null;
        }
        return new FirebaseOptions(applicationId2, reader.getString(API_KEY_RESOURCE_NAME), reader.getString(DATABASE_URL_RESOURCE_NAME), reader.getString(GA_TRACKING_ID_RESOURCE_NAME), reader.getString(GCM_SENDER_ID_RESOURCE_NAME), reader.getString(STORAGE_BUCKET_RESOURCE_NAME), reader.getString(PROJECT_ID_RESOURCE_NAME));
    }

    @NonNull
    public String getApiKey() {
        return this.apiKey;
    }

    @NonNull
    public String getApplicationId() {
        return this.applicationId;
    }

    @Nullable
    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    @KeepForSdk
    @Nullable
    public String getGaTrackingId() {
        return this.gaTrackingId;
    }

    @Nullable
    public String getGcmSenderId() {
        return this.gcmSenderId;
    }

    @Nullable
    public String getStorageBucket() {
        return this.storageBucket;
    }

    @Nullable
    public String getProjectId() {
        return this.projectId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof FirebaseOptions)) {
            return false;
        }
        FirebaseOptions other = (FirebaseOptions) o;
        if (!Objects.equal(this.applicationId, other.applicationId) || !Objects.equal(this.apiKey, other.apiKey) || !Objects.equal(this.databaseUrl, other.databaseUrl) || !Objects.equal(this.gaTrackingId, other.gaTrackingId) || !Objects.equal(this.gcmSenderId, other.gcmSenderId) || !Objects.equal(this.storageBucket, other.storageBucket) || !Objects.equal(this.projectId, other.projectId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.applicationId, this.apiKey, this.databaseUrl, this.gaTrackingId, this.gcmSenderId, this.storageBucket, this.projectId);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("applicationId", this.applicationId).add("apiKey", this.apiKey).add("databaseUrl", this.databaseUrl).add("gcmSenderId", this.gcmSenderId).add("storageBucket", this.storageBucket).add("projectId", this.projectId).toString();
    }
}
