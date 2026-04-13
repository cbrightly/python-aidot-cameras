package zendesk.messaging.android.internal;

import android.content.Intent;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.properties.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: IntentDelegate.kt */
public abstract class f<T> implements c<Intent, T> {
    @NotNull
    private final String a;

    public /* synthetic */ f(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private f(String key) {
        this.a = key;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final String c() {
        return this.a;
    }

    /* compiled from: IntentDelegate.kt */
    public static final class a extends f<String> {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull String key) {
            super(key, (DefaultConstructorMarker) null);
            k.e(key, CacheEntity.KEY);
        }

        @NotNull
        /* renamed from: d */
        public String b(@NotNull Intent thisRef, @NotNull kotlin.reflect.k<?> property) {
            k.e(thisRef, "thisRef");
            k.e(property, "property");
            String stringExtra = thisRef.getStringExtra(c());
            return stringExtra != null ? stringExtra : "";
        }

        /* renamed from: e */
        public void a(@NotNull Intent thisRef, @NotNull kotlin.reflect.k<?> property, @NotNull String value) {
            k.e(thisRef, "thisRef");
            k.e(property, "property");
            k.e(value, "value");
            thisRef.putExtra(c(), value);
        }
    }
}
