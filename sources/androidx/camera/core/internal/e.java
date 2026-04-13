package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.UseCase;

/* compiled from: UseCaseEventConfig */
public final /* synthetic */ class e {
    @Nullable
    public static UseCase.EventCallback b(@Nullable UseCaseEventConfig _this, UseCase.EventCallback valueIfMissing) {
        return (UseCase.EventCallback) _this.retrieveOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, valueIfMissing);
    }

    @NonNull
    public static UseCase.EventCallback a(UseCaseEventConfig _this) {
        return (UseCase.EventCallback) _this.retrieveOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK);
    }
}
