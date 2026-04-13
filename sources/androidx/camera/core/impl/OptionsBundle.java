package androidx.camera.core.impl;

import android.util.ArrayMap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OptionsBundle implements Config {
    private static final OptionsBundle EMPTY_BUNDLE;
    protected static final Comparator<Config.Option<?>> ID_COMPARE;
    protected final TreeMap<Config.Option<?>, Map<Config.OptionPriority, Object>> mOptions;

    static {
        q qVar = q.c;
        ID_COMPARE = qVar;
        EMPTY_BUNDLE = new OptionsBundle(new TreeMap(qVar));
    }

    OptionsBundle(TreeMap<Config.Option<?>, Map<Config.OptionPriority, Object>> options) {
        this.mOptions = options;
    }

    @NonNull
    public static OptionsBundle from(@NonNull Config otherConfig) {
        if (OptionsBundle.class.equals(otherConfig.getClass())) {
            return (OptionsBundle) otherConfig;
        }
        TreeMap<Config.Option<?>, Map<Config.OptionPriority, Object>> persistentOptions = new TreeMap<>(ID_COMPARE);
        for (Config.Option<?> opt : otherConfig.listOptions()) {
            Set<Config.OptionPriority> priorities = otherConfig.getPriorities(opt);
            Map<Config.OptionPriority, Object> valuesMap = new ArrayMap<>();
            for (Config.OptionPriority priority : priorities) {
                valuesMap.put(priority, otherConfig.retrieveOptionWithPriority(opt, priority));
            }
            persistentOptions.put(opt, valuesMap);
        }
        return new OptionsBundle(persistentOptions);
    }

    @NonNull
    public static OptionsBundle emptyBundle() {
        return EMPTY_BUNDLE;
    }

    @NonNull
    public Set<Config.Option<?>> listOptions() {
        return Collections.unmodifiableSet(this.mOptions.keySet());
    }

    public boolean containsOption(@NonNull Config.Option<?> id) {
        return this.mOptions.containsKey(id);
    }

    @Nullable
    public <ValueT> ValueT retrieveOption(@NonNull Config.Option<ValueT> id) {
        Map<Config.OptionPriority, Object> values = this.mOptions.get(id);
        if (values != null) {
            return values.get((Config.OptionPriority) Collections.min(values.keySet()));
        }
        throw new IllegalArgumentException("Option does not exist: " + id);
    }

    @Nullable
    public <ValueT> ValueT retrieveOption(@NonNull Config.Option<ValueT> id, @Nullable ValueT valueIfMissing) {
        try {
            return retrieveOption(id);
        } catch (IllegalArgumentException e) {
            return valueIfMissing;
        }
    }

    @Nullable
    public <ValueT> ValueT retrieveOptionWithPriority(@NonNull Config.Option<ValueT> id, @NonNull Config.OptionPriority priority) {
        Map<Config.OptionPriority, Object> values = this.mOptions.get(id);
        if (values == null) {
            throw new IllegalArgumentException("Option does not exist: " + id);
        } else if (values.containsKey(priority)) {
            return values.get(priority);
        } else {
            throw new IllegalArgumentException("Option does not exist: " + id + " with priority=" + priority);
        }
    }

    @NonNull
    public Config.OptionPriority getOptionPriority(@NonNull Config.Option<?> opt) {
        Map<Config.OptionPriority, Object> values = this.mOptions.get(opt);
        if (values != null) {
            return (Config.OptionPriority) Collections.min(values.keySet());
        }
        throw new IllegalArgumentException("Option does not exist: " + opt);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void findOptions(@androidx.annotation.NonNull java.lang.String r6, @androidx.annotation.NonNull androidx.camera.core.impl.Config.OptionMatcher r7) {
        /*
            r5 = this;
            java.lang.Class<java.lang.Void> r0 = java.lang.Void.class
            androidx.camera.core.impl.Config$Option r0 = androidx.camera.core.impl.Config.Option.create(r6, r0)
            java.util.TreeMap<androidx.camera.core.impl.Config$Option<?>, java.util.Map<androidx.camera.core.impl.Config$OptionPriority, java.lang.Object>> r1 = r5.mOptions
            java.util.SortedMap r1 = r1.tailMap(r0)
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0014:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x003f
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            androidx.camera.core.impl.Config$Option r3 = (androidx.camera.core.impl.Config.Option) r3
            java.lang.String r3 = r3.getId()
            boolean r3 = r3.startsWith(r6)
            if (r3 != 0) goto L_0x0031
            goto L_0x003f
        L_0x0031:
            java.lang.Object r3 = r2.getKey()
            androidx.camera.core.impl.Config$Option r3 = (androidx.camera.core.impl.Config.Option) r3
            boolean r4 = r7.onOptionMatched(r3)
            if (r4 != 0) goto L_0x003e
            goto L_0x003f
        L_0x003e:
            goto L_0x0014
        L_0x003f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.OptionsBundle.findOptions(java.lang.String, androidx.camera.core.impl.Config$OptionMatcher):void");
    }

    @NonNull
    public Set<Config.OptionPriority> getPriorities(@NonNull Config.Option<?> opt) {
        Map<Config.OptionPriority, Object> values = this.mOptions.get(opt);
        if (values == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(values.keySet());
    }
}
