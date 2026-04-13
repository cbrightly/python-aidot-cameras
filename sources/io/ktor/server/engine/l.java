package io.ktor.server.engine;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EngineConnectorConfig.kt */
public final class l {
    /* access modifiers changed from: private */
    @NotNull
    public static final l a = new l("HTTP");
    /* access modifiers changed from: private */
    @NotNull
    public static final l b = new l("HTTPS");
    public static final a c = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof l) && k.a(this.d, ((l) obj).d);
        }
        return true;
    }

    public int hashCode() {
        String str = this.d;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "ConnectorType(name=" + this.d + ")";
    }

    /* compiled from: EngineConnectorConfig.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final l a() {
            return l.a;
        }

        @NotNull
        public final l b() {
            return l.b;
        }
    }

    public l(@NotNull String name) {
        k.f(name, "name");
        this.d = name;
    }

    @NotNull
    public final String c() {
        return this.d;
    }
}
