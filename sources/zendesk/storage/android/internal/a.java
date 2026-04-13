package zendesk.storage.android.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: BasicStorage.kt */
public final class a implements zendesk.storage.android.c {
    @NotNull
    public static final C0554a a = new C0554a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final SharedPreferences c;

    public a(@NotNull String namespace, @NotNull Context context) {
        k.e(namespace, "namespace");
        k.e(context, "context");
        this.b = namespace;
        SharedPreferences sharedPreferences = context.getSharedPreferences(c(), 0);
        k.d(sharedPreferences, "context.getSharedPrefere…ce, Context.MODE_PRIVATE)");
        this.c = sharedPreferences;
    }

    @NotNull
    public String c() {
        return this.b;
    }

    @Nullable
    public <T> T b(@NotNull String key, @NotNull Class<T> type) {
        k.e(key, CacheEntity.KEY);
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (!this.c.contains(key)) {
            zendesk.logger.a.h("SimpleStorage", "There is no stored data for the given key", new Object[0]);
            return null;
        }
        try {
            if (k.a(type, String.class)) {
                return this.c.getString(key, (String) null);
            }
            if (k.a(type, Integer.TYPE)) {
                return Integer.valueOf(this.c.getInt(key, 0));
            }
            if (k.a(type, Boolean.TYPE)) {
                return Boolean.valueOf(this.c.getBoolean(key, false));
            }
            if (k.a(type, Float.TYPE)) {
                return Float.valueOf(this.c.getFloat(key, 0.0f));
            }
            if (k.a(type, Long.TYPE)) {
                return Long.valueOf(this.c.getLong(key, 0));
            }
            return null;
        } catch (ClassCastException ex) {
            zendesk.logger.a.c("SimpleStorage", "The stored data did not match the requested type", ex, new Object[0]);
            return null;
        }
    }

    /* compiled from: BasicStorage.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<SharedPreferences.Editor, x> {
        final /* synthetic */ String $key;
        final /* synthetic */ T $value;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(T t, String str) {
            super(1);
            this.$value = t;
            this.$key = str;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((SharedPreferences.Editor) p1);
            return x.a;
        }

        public final void invoke(@NotNull SharedPreferences.Editor $this$edit) {
            k.e($this$edit, "$this$edit");
            T t = this.$value;
            if (t == null) {
                $this$edit.remove(this.$key);
            } else if (t instanceof String) {
                $this$edit.putString(this.$key, (String) t);
            } else if (t instanceof Integer) {
                $this$edit.putInt(this.$key, ((Number) t).intValue());
            } else if (t instanceof Boolean) {
                $this$edit.putBoolean(this.$key, ((Boolean) t).booleanValue());
            } else if (t instanceof Float) {
                $this$edit.putFloat(this.$key, ((Number) t).floatValue());
            } else if (t instanceof Long) {
                $this$edit.putLong(this.$key, ((Number) t).longValue());
            } else {
                zendesk.logger.a.d("SimpleStorage", "Unable to store the value provided as it is not a supported type", new Object[0]);
            }
        }
    }

    public <T> void a(@NotNull String key, @Nullable T value, @NotNull Class<T> type) {
        k.e(key, CacheEntity.KEY);
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        b.b(this.c, new c(value, key));
    }

    /* compiled from: BasicStorage.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<SharedPreferences.Editor, x> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((SharedPreferences.Editor) p1);
            return x.a;
        }

        public final void invoke(@NotNull SharedPreferences.Editor $this$edit) {
            k.e($this$edit, "$this$edit");
            $this$edit.clear();
        }
    }

    public void clear() {
        b.b(this.c, b.INSTANCE);
    }

    /* renamed from: zendesk.storage.android.internal.a$a  reason: collision with other inner class name */
    /* compiled from: BasicStorage.kt */
    public static final class C0554a {
        public /* synthetic */ C0554a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0554a() {
        }
    }
}
