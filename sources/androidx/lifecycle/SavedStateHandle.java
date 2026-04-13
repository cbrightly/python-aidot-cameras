package androidx.lifecycle;

import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.core.os.BundleKt;
import androidx.savedstate.SavedStateRegistry;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.p0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.t;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.flow.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\t\u0018\u0000 *2\u00020\u0001:\u0002*+B\u001d\b\u0016\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0003¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J\u0011\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u001e\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0019\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J)\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0019\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u0002H\u0016H\u0007¢\u0006\u0002\u0010\u001bJ1\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0019\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u0002H\u0016H\u0002¢\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00160 \"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u0002H\u0016H\u0007¢\u0006\u0002\u0010!J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007J\u001d\u0010$\u001a\u0004\u0018\u0001H\u0016\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\u0017J\b\u0010\r\u001a\u00020\u000eH\u0007J&\u0010%\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0012\u001a\u00020\u00042\b\u0010&\u001a\u0004\u0018\u0001H\u0016H\u0002¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u000eH\u0007R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\t0\bX\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\bX\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\bX\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Landroidx/lifecycle/SavedStateHandle;", "", "initialState", "", "", "(Ljava/util/Map;)V", "()V", "flows", "", "Lkotlinx/coroutines/flow/MutableStateFlow;", "liveDatas", "Landroidx/lifecycle/SavedStateHandle$SavingStateLiveData;", "regular", "savedStateProvider", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "savedStateProviders", "clearSavedStateProvider", "", "key", "contains", "", "get", "T", "(Ljava/lang/String;)Ljava/lang/Object;", "getLiveData", "Landroidx/lifecycle/MutableLiveData;", "initialValue", "(Ljava/lang/String;Ljava/lang/Object;)Landroidx/lifecycle/MutableLiveData;", "getLiveDataInternal", "hasInitialValue", "(Ljava/lang/String;ZLjava/lang/Object;)Landroidx/lifecycle/MutableLiveData;", "getStateFlow", "Lkotlinx/coroutines/flow/StateFlow;", "(Ljava/lang/String;Ljava/lang/Object;)Lkotlinx/coroutines/flow/StateFlow;", "keys", "", "remove", "set", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "setSavedStateProvider", "provider", "Companion", "SavingStateLiveData", "lifecycle-viewmodel-savedstate_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: SavedStateHandle.kt */
public final class SavedStateHandle {
    /* access modifiers changed from: private */
    @NotNull
    public static final Class<? extends Object>[] ACCEPTABLE_CLASSES;
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    @NotNull
    private static final String KEYS = "keys";
    @NotNull
    private static final String VALUES = "values";
    /* access modifiers changed from: private */
    @NotNull
    public final Map<String, q<Object>> flows;
    @NotNull
    private final Map<String, SavingStateLiveData<?>> liveDatas;
    /* access modifiers changed from: private */
    @NotNull
    public final Map<String, Object> regular;
    @NotNull
    private final SavedStateRegistry.SavedStateProvider savedStateProvider;
    @NotNull
    private final Map<String, SavedStateRegistry.SavedStateProvider> savedStateProviders;

    @NotNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final SavedStateHandle createHandle(@Nullable Bundle bundle, @Nullable Bundle bundle2) {
        return Companion.createHandle(bundle, bundle2);
    }

    /* access modifiers changed from: private */
    /* renamed from: savedStateProvider$lambda-0  reason: not valid java name */
    public static final Bundle m2savedStateProvider$lambda0(SavedStateHandle this$0) {
        k.e(this$0, "this$0");
        for (Map.Entry next : l0.q(this$0.savedStateProviders).entrySet()) {
            this$0.set((String) next.getKey(), ((SavedStateRegistry.SavedStateProvider) next.getValue()).saveState());
        }
        Set keySet = this$0.regular.keySet();
        ArrayList keys = new ArrayList(keySet.size());
        ArrayList value = new ArrayList(keys.size());
        for (String key : keySet) {
            keys.add(key);
            value.add(this$0.regular.get(key));
        }
        return BundleKt.bundleOf(t.a(KEYS, keys), t.a(VALUES, value));
    }

    public SavedStateHandle(@NotNull Map<String, ? extends Object> initialState) {
        k.e(initialState, "initialState");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.regular = linkedHashMap;
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new c(this);
        linkedHashMap.putAll(initialState);
    }

    public SavedStateHandle() {
        this.regular = new LinkedHashMap();
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new c(this);
    }

    @NotNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final SavedStateRegistry.SavedStateProvider savedStateProvider() {
        return this.savedStateProvider;
    }

    @MainThread
    public final boolean contains(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        return this.regular.containsKey(key);
    }

    @NotNull
    @MainThread
    public final <T> MutableLiveData<T> getLiveData(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        return getLiveDataInternal(key, false, (Object) null);
    }

    @NotNull
    @MainThread
    public final <T> MutableLiveData<T> getLiveData(@NotNull String key, T initialValue) {
        k.e(key, CacheEntity.KEY);
        return getLiveDataInternal(key, true, initialValue);
    }

    private final <T> MutableLiveData<T> getLiveDataInternal(String key, boolean hasInitialValue, T initialValue) {
        SavingStateLiveData mutableLd;
        MutableLiveData mutableLiveData = this.liveDatas.get(key);
        MutableLiveData liveData = mutableLiveData instanceof MutableLiveData ? mutableLiveData : null;
        if (liveData != null) {
            return liveData;
        }
        if (this.regular.containsKey(key)) {
            mutableLd = new SavingStateLiveData(this, key, this.regular.get(key));
        } else if (hasInitialValue) {
            this.regular.put(key, initialValue);
            mutableLd = new SavingStateLiveData(this, key, initialValue);
        } else {
            mutableLd = new SavingStateLiveData(this, key);
        }
        this.liveDatas.put(key, mutableLd);
        return mutableLd;
    }

    @NotNull
    @MainThread
    public final <T> x<T> getStateFlow(@NotNull String key, T initialValue) {
        Object answer$iv;
        k.e(key, CacheEntity.KEY);
        Map $this$getOrPut$iv = this.flows;
        Object value$iv = $this$getOrPut$iv.get(key);
        if (value$iv == null) {
            if (!this.regular.containsKey(key)) {
                this.regular.put(key, initialValue);
            }
            Object $this$getStateFlow_u24lambda_u2d2_u24lambda_u2d1 = z.a(this.regular.get(key));
            this.flows.put(key, $this$getStateFlow_u24lambda_u2d2_u24lambda_u2d1);
            answer$iv = $this$getStateFlow_u24lambda_u2d2_u24lambda_u2d1;
            $this$getOrPut$iv.put(key, answer$iv);
        } else {
            answer$iv = value$iv;
        }
        return e.b((q) answer$iv);
    }

    @NotNull
    @MainThread
    public final Set<String> keys() {
        return p0.i(p0.i(this.regular.keySet(), this.savedStateProviders.keySet()), this.liveDatas.keySet());
    }

    @Nullable
    @MainThread
    public final <T> T get(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        return this.regular.get(key);
    }

    @MainThread
    public final <T> void set(@NotNull String key, @Nullable T value) {
        k.e(key, CacheEntity.KEY);
        if (Companion.validateValue(value)) {
            SavingStateLiveData<?> savingStateLiveData = this.liveDatas.get(key);
            MutableLiveData mutableLiveData = savingStateLiveData instanceof MutableLiveData ? savingStateLiveData : null;
            if (mutableLiveData != null) {
                mutableLiveData.setValue(value);
            } else {
                this.regular.put(key, value);
            }
            q qVar = this.flows.get(key);
            if (qVar != null) {
                qVar.setValue(value);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't put value with type ");
        k.c(value);
        sb.append(value.getClass());
        sb.append(" into saved state");
        throw new IllegalArgumentException(sb.toString());
    }

    @Nullable
    @MainThread
    public final <T> T remove(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        Object latestValue = this.regular.remove(key);
        SavingStateLiveData liveData = this.liveDatas.remove(key);
        if (liveData != null) {
            liveData.detach();
        }
        this.flows.remove(key);
        return latestValue;
    }

    @MainThread
    public final void setSavedStateProvider(@NotNull String key, @NotNull SavedStateRegistry.SavedStateProvider provider) {
        k.e(key, CacheEntity.KEY);
        k.e(provider, "provider");
        this.savedStateProviders.put(key, provider);
    }

    @MainThread
    public final void clearSavedStateProvider(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        this.savedStateProviders.remove(key);
    }

    @l(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B!\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\tJ\u0006\u0010\n\u001a\u00020\u000bJ\u0015\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Landroidx/lifecycle/SavedStateHandle$SavingStateLiveData;", "T", "Landroidx/lifecycle/MutableLiveData;", "handle", "Landroidx/lifecycle/SavedStateHandle;", "key", "", "value", "(Landroidx/lifecycle/SavedStateHandle;Ljava/lang/String;Ljava/lang/Object;)V", "(Landroidx/lifecycle/SavedStateHandle;Ljava/lang/String;)V", "detach", "", "setValue", "(Ljava/lang/Object;)V", "lifecycle-viewmodel-savedstate_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SavedStateHandle.kt */
    public static final class SavingStateLiveData<T> extends MutableLiveData<T> {
        @Nullable
        private SavedStateHandle handle;
        @NotNull
        private String key;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SavingStateLiveData(@Nullable SavedStateHandle handle2, @NotNull String key2, T value) {
            super(value);
            k.e(key2, CacheEntity.KEY);
            this.key = key2;
            this.handle = handle2;
        }

        public SavingStateLiveData(@Nullable SavedStateHandle handle2, @NotNull String key2) {
            k.e(key2, CacheEntity.KEY);
            this.key = key2;
            this.handle = handle2;
        }

        public void setValue(T value) {
            SavedStateHandle it = this.handle;
            if (it != null) {
                it.regular.put(this.key, value);
                q qVar = (q) it.flows.get(this.key);
                if (qVar != null) {
                    qVar.setValue(value);
                }
            }
            super.setValue(value);
        }

        public final void detach() {
            this.handle = null;
        }
    }

    @l(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0007J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0007R \u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\b\u0001\u0012\u00020\u0001\u0018\u00010\u00050\u0004X\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/lifecycle/SavedStateHandle$Companion;", "", "()V", "ACCEPTABLE_CLASSES", "", "Ljava/lang/Class;", "[Ljava/lang/Class;", "KEYS", "", "VALUES", "createHandle", "Landroidx/lifecycle/SavedStateHandle;", "restoredState", "Landroid/os/Bundle;", "defaultState", "validateValue", "", "value", "lifecycle-viewmodel-savedstate_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SavedStateHandle.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final SavedStateHandle createHandle(@Nullable Bundle restoredState, @Nullable Bundle defaultState) {
            if (restoredState != null) {
                ArrayList keys = restoredState.getParcelableArrayList(SavedStateHandle.KEYS);
                ArrayList values = restoredState.getParcelableArrayList(SavedStateHandle.VALUES);
                if ((keys == null || values == null || keys.size() != values.size()) ? false : true) {
                    Map state = new LinkedHashMap();
                    int i = 0;
                    int size = keys.size();
                    while (i < size) {
                        Object obj = keys.get(i);
                        if (obj != null) {
                            state.put((String) obj, values.get(i));
                            i++;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                        }
                    }
                    return new SavedStateHandle(state);
                }
                throw new IllegalStateException("Invalid bundle passed as restored state".toString());
            } else if (defaultState == null) {
                return new SavedStateHandle();
            } else {
                Map state2 = new HashMap();
                for (String key : defaultState.keySet()) {
                    k.d(key, CacheEntity.KEY);
                    state2.put(key, defaultState.get(key));
                }
                return new SavedStateHandle(state2);
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final boolean validateValue(@Nullable Object value) {
            if (value == null) {
                return true;
            }
            for (Class cl : SavedStateHandle.ACCEPTABLE_CLASSES) {
                k.c(cl);
                if (cl.isInstance(value)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Class<? extends java.lang.Object>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            androidx.lifecycle.SavedStateHandle$Companion r0 = new androidx.lifecycle.SavedStateHandle$Companion
            r1 = 0
            r0.<init>(r1)
            Companion = r0
            r0 = 29
            java.lang.Class[] r0 = new java.lang.Class[r0]
            java.lang.Class r1 = java.lang.Boolean.TYPE
            r2 = 0
            r0[r2] = r1
            r1 = 1
            java.lang.Class<boolean[]> r2 = boolean[].class
            r0[r1] = r2
            java.lang.Class r1 = java.lang.Double.TYPE
            r2 = 2
            r0[r2] = r1
            r1 = 3
            java.lang.Class<double[]> r2 = double[].class
            r0[r1] = r2
            java.lang.Class r1 = java.lang.Integer.TYPE
            r2 = 4
            r0[r2] = r1
            r2 = 5
            java.lang.Class<int[]> r3 = int[].class
            r0[r2] = r3
            java.lang.Class r2 = java.lang.Long.TYPE
            r3 = 6
            r0[r3] = r2
            r2 = 7
            java.lang.Class<long[]> r3 = long[].class
            r0[r2] = r3
            r2 = 8
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            r0[r2] = r3
            r2 = 9
            java.lang.Class<java.lang.String[]> r3 = java.lang.String[].class
            r0[r2] = r3
            r2 = 10
            java.lang.Class<android.os.Binder> r3 = android.os.Binder.class
            r0[r2] = r3
            r2 = 11
            java.lang.Class<android.os.Bundle> r3 = android.os.Bundle.class
            r0[r2] = r3
            java.lang.Class r2 = java.lang.Byte.TYPE
            r3 = 12
            r0[r3] = r2
            r2 = 13
            java.lang.Class<byte[]> r3 = byte[].class
            r0[r2] = r3
            java.lang.Class r2 = java.lang.Character.TYPE
            r3 = 14
            r0[r3] = r2
            r2 = 15
            java.lang.Class<char[]> r3 = char[].class
            r0[r2] = r3
            r2 = 16
            java.lang.Class<java.lang.CharSequence> r3 = java.lang.CharSequence.class
            r0[r2] = r3
            r2 = 17
            java.lang.Class<java.lang.CharSequence[]> r3 = java.lang.CharSequence[].class
            r0[r2] = r3
            r2 = 18
            java.lang.Class<java.util.ArrayList> r3 = java.util.ArrayList.class
            r0[r2] = r3
            java.lang.Class r2 = java.lang.Float.TYPE
            r3 = 19
            r0[r3] = r2
            r2 = 20
            java.lang.Class<float[]> r3 = float[].class
            r0[r2] = r3
            java.lang.Class<android.os.Parcelable> r2 = android.os.Parcelable.class
            r3 = 21
            r0[r3] = r2
            r2 = 22
            java.lang.Class<android.os.Parcelable[]> r4 = android.os.Parcelable[].class
            r0[r2] = r4
            r2 = 23
            java.lang.Class<java.io.Serializable> r4 = java.io.Serializable.class
            r0[r2] = r4
            java.lang.Class r2 = java.lang.Short.TYPE
            r4 = 24
            r0[r4] = r2
            r2 = 25
            java.lang.Class<short[]> r4 = short[].class
            r0[r2] = r4
            r2 = 26
            java.lang.Class<android.util.SparseArray> r4 = android.util.SparseArray.class
            r0[r2] = r4
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 < r3) goto L_0x00ae
            java.lang.Class<android.util.Size> r4 = android.util.Size.class
            goto L_0x00af
        L_0x00ae:
            r4 = r1
        L_0x00af:
            r5 = 27
            r0[r5] = r4
            r4 = 28
            if (r2 < r3) goto L_0x00b9
            java.lang.Class<android.util.SizeF> r1 = android.util.SizeF.class
        L_0x00b9:
            r0[r4] = r1
            ACCEPTABLE_CLASSES = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.SavedStateHandle.<clinit>():void");
    }
}
