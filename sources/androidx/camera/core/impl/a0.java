package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;
import java.util.Set;

/* compiled from: ReadableConfig */
public final /* synthetic */ class a0 {
    public static boolean a(@NonNull ReadableConfig _this, Config.Option id) {
        return _this.getConfig().containsOption(id);
    }

    @Nullable
    public static <ValueT> Object f(@NonNull ReadableConfig _this, Config.Option id) {
        return _this.getConfig().retrieveOption(id);
    }

    @Nullable
    public static <ValueT> Object g(@NonNull ReadableConfig _this, @Nullable Config.Option id, Object valueIfMissing) {
        return _this.getConfig().retrieveOption(id, valueIfMissing);
    }

    public static void b(@NonNull ReadableConfig _this, @NonNull String idSearchString, Config.OptionMatcher matcher) {
        _this.getConfig().findOptions(idSearchString, matcher);
    }

    @NonNull
    public static Set e(ReadableConfig _this) {
        return _this.getConfig().listOptions();
    }

    @Nullable
    public static <ValueT> Object h(@NonNull ReadableConfig _this, @NonNull Config.Option id, Config.OptionPriority priority) {
        return _this.getConfig().retrieveOptionWithPriority(id, priority);
    }

    @NonNull
    public static Config.OptionPriority c(@NonNull ReadableConfig _this, Config.Option opt) {
        return _this.getConfig().getOptionPriority(opt);
    }

    @NonNull
    public static Set d(@NonNull ReadableConfig _this, Config.Option option) {
        return _this.getConfig().getPriorities(option);
    }
}
