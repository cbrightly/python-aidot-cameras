package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MutableStateObservable<T> extends StateObservable<T> {
    private MutableStateObservable(@Nullable Object initialState, boolean isError) {
        super(initialState, isError);
    }

    @NonNull
    public static <T> MutableStateObservable<T> withInitialState(@Nullable T initialState) {
        return new MutableStateObservable<>(initialState, false);
    }

    @NonNull
    public static <T> MutableStateObservable<T> withInitialError(@NonNull Throwable initialError) {
        return new MutableStateObservable<>(initialError, true);
    }

    public void setState(@Nullable T state) {
        updateState(state);
    }

    public void setError(@NonNull Throwable error) {
        updateStateAsError(error);
    }
}
