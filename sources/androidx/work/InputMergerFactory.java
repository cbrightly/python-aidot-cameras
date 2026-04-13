package androidx.work;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public abstract class InputMergerFactory {
    @Nullable
    public abstract InputMerger createInputMerger(@NonNull String str);

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final InputMerger createInputMergerWithDefaultFallback(@NonNull String className) {
        InputMerger inputMerger = createInputMerger(className);
        if (inputMerger == null) {
            return InputMerger.fromClassName(className);
        }
        return inputMerger;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static InputMergerFactory getDefaultInputMergerFactory() {
        return new InputMergerFactory() {
            @Nullable
            public InputMerger createInputMerger(@NonNull String className) {
                return null;
            }
        };
    }
}
