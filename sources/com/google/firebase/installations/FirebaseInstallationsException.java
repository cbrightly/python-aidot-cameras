package com.google.firebase.installations;

import androidx.annotation.NonNull;
import com.google.firebase.FirebaseException;

public class FirebaseInstallationsException extends FirebaseException {
    @NonNull
    private final Status status;

    public enum Status {
        BAD_CONFIG,
        UNAVAILABLE,
        TOO_MANY_REQUESTS
    }

    public FirebaseInstallationsException(@NonNull Status status2) {
        this.status = status2;
    }

    public FirebaseInstallationsException(@NonNull String message, @NonNull Status status2) {
        super(message);
        this.status = status2;
    }

    public FirebaseInstallationsException(@NonNull String message, @NonNull Status status2, @NonNull Throwable cause) {
        super(message, cause);
        this.status = status2;
    }

    @NonNull
    public Status getStatus() {
        return this.status;
    }
}
