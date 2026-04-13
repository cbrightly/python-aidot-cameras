package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.Set;

public interface Config {

    public interface OptionMatcher {
        boolean onOptionMatched(@NonNull Option<?> option);
    }

    public enum OptionPriority {
        ALWAYS_OVERRIDE,
        REQUIRED,
        OPTIONAL
    }

    boolean containsOption(@NonNull Option<?> option);

    void findOptions(@NonNull String str, @NonNull OptionMatcher optionMatcher);

    @NonNull
    OptionPriority getOptionPriority(@NonNull Option<?> option);

    @NonNull
    Set<OptionPriority> getPriorities(@NonNull Option<?> option);

    @NonNull
    Set<Option<?>> listOptions();

    @Nullable
    <ValueT> ValueT retrieveOption(@NonNull Option<ValueT> option);

    @Nullable
    <ValueT> ValueT retrieveOption(@NonNull Option<ValueT> option, @Nullable ValueT valuet);

    @Nullable
    <ValueT> ValueT retrieveOptionWithPriority(@NonNull Option<ValueT> option, @NonNull OptionPriority optionPriority);

    @AutoValue
    public static abstract class Option<T> {
        @NonNull
        public abstract String getId();

        @Nullable
        public abstract Object getToken();

        @NonNull
        public abstract Class<T> getValueClass();

        Option() {
        }

        @NonNull
        public static <T> Option<T> create(@NonNull String id, @NonNull Class<?> valueClass) {
            return create(id, valueClass, (Object) null);
        }

        @NonNull
        public static <T> Option<T> create(@NonNull String id, @NonNull Class<?> valueClass, @Nullable Object token) {
            return new AutoValue_Config_Option(id, valueClass, token);
        }
    }
}
