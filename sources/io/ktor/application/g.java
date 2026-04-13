package io.ktor.application;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.util.d;
import java.io.Closeable;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ApplicationFeature.kt */
public final class g {
    private static final io.ktor.util.a<io.ktor.util.b> a = new io.ktor.util.a<>("ApplicationFeatureRegistry");

    @Nullable
    public static final <A extends io.ktor.util.pipeline.c<?, b>, B, F> F a(@NotNull A $this$featureOrNull, @NotNull f<? super A, ? extends B, F> feature) {
        k.f($this$featureOrNull, "$this$featureOrNull");
        k.f(feature, "feature");
        io.ktor.util.b bVar = (io.ktor.util.b) $this$featureOrNull.i().e(a);
        if (bVar != null) {
            return bVar.e(feature.getKey());
        }
        return null;
    }

    /* compiled from: ApplicationFeature.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<B, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public final void invoke(@NotNull B $receiver) {
            k.f($receiver, "$receiver");
        }
    }

    public static /* synthetic */ Object c(io.ktor.util.pipeline.c cVar, f fVar, kotlin.jvm.functions.l lVar, int i, Object obj) {
        if ((i & 2) != 0) {
            lVar = a.INSTANCE;
        }
        return b(cVar, fVar, lVar);
    }

    /* compiled from: ApplicationFeature.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<io.ktor.util.b> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        @NotNull
        public final io.ktor.util.b invoke() {
            return d.a(true);
        }
    }

    @NotNull
    public static final <P extends io.ktor.util.pipeline.c<?, b>, B, F> F b(@NotNull P $this$install, @NotNull f<? super P, ? extends B, F> feature, @NotNull kotlin.jvm.functions.l<? super B, x> configure) {
        k.f($this$install, "$this$install");
        k.f(feature, "feature");
        k.f(configure, "configure");
        io.ktor.util.b registry = (io.ktor.util.b) $this$install.i().f(a, b.INSTANCE);
        Object installedFeature = registry.e(feature.getKey());
        if (installedFeature == null) {
            Object installed = feature.a($this$install, configure);
            registry.b(feature.getKey(), installed);
            return installed;
        } else if (k.a(installedFeature, feature)) {
            return installedFeature;
        } else {
            throw new DuplicateApplicationFeatureException("Conflicting application feature is already installed with the same key as `" + feature.getKey().a() + '`');
        }
    }

    /* compiled from: ApplicationFeature.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<io.ktor.util.b> {
        public static final c INSTANCE = new c();

        c() {
            super(0);
        }

        @NotNull
        public final io.ktor.util.b invoke() {
            return d.a(true);
        }
    }

    public static final <A extends io.ktor.util.pipeline.c<?, b>> void d(@NotNull A $this$uninstallAllFeatures) {
        k.f($this$uninstallAllFeatures, "$this$uninstallAllFeatures");
        for (io.ktor.util.a it : ((io.ktor.util.b) $this$uninstallAllFeatures.i().f(a, c.INSTANCE)).c()) {
            if (it != null) {
                e($this$uninstallAllFeatures, it);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.AttributeKey<kotlin.Any>");
            }
        }
    }

    public static final <A extends io.ktor.util.pipeline.c<?, b>, F> void e(@NotNull A $this$uninstallFeature, @NotNull io.ktor.util.a<F> key) {
        Object instance;
        k.f($this$uninstallFeature, "$this$uninstallFeature");
        k.f(key, CacheEntity.KEY);
        io.ktor.util.b registry = (io.ktor.util.b) $this$uninstallFeature.i().e(a);
        if (registry != null && (instance = registry.e(key)) != null) {
            if (instance instanceof Closeable) {
                ((Closeable) instance).close();
            }
            registry.d(key);
        }
    }
}
