package zendesk.android.internal.network;

import android.content.Context;
import com.squareup.moshi.r;
import java.io.File;
import java.util.Date;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import retrofit2.t;
import zendesk.android.internal.g;
import zendesk.okhttp.b;

/* compiled from: NetworkModule.kt */
public final class c {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);

    @NotNull
    public final b e(@NotNull g componentConfig, @NotNull Context context) {
        k.e(componentConfig, "componentConfig");
        k.e(context, "context");
        return new b(context, componentConfig);
    }

    @NotNull
    public final a b(@NotNull g componentConfig, @NotNull b networkData) {
        k.e(componentConfig, "componentConfig");
        k.e(networkData, "networkData");
        return new a(componentConfig.d(), networkData);
    }

    @NotNull
    public final File a(@NotNull Context context) {
        k.e(context, "context");
        return new File(context.getCacheDir(), "zendesk.android");
    }

    @NotNull
    public final z f(@NotNull a headerFactory, @NotNull File cacheDir) {
        k.e(headerFactory, "headerFactory");
        k.e(cacheDir, "cacheDir");
        return b.a(new z.a(), headerFactory.c(), headerFactory.d()).d(new okhttp3.c(cacheDir, 20971520)).c();
    }

    @NotNull
    public final t g(@NotNull g componentConfig, @NotNull z okHttpClient, @NotNull retrofit2.converter.moshi.a moshiConverterFactory) {
        k.e(componentConfig, "componentConfig");
        k.e(okHttpClient, "okHttpClient");
        k.e(moshiConverterFactory, "moshiConverterFactory");
        t e = new t.b().c(componentConfig.a()).g(okHttpClient).b(moshiConverterFactory).e();
        k.d(e, "Builder()\n            .b…ory)\n            .build()");
        return e;
    }

    @NotNull
    public final retrofit2.converter.moshi.a d(@NotNull r moshi) {
        k.e(moshi, "moshi");
        retrofit2.converter.moshi.a a2 = retrofit2.converter.moshi.a.a(moshi);
        k.d(a2, "create(moshi)");
        return a2;
    }

    @NotNull
    public final r c() {
        r c = new r.b().b(Date.class, new com.squareup.moshi.adapters.c()).c();
        k.d(c, "Builder()\n        .add(D…apter())\n        .build()");
        return c;
    }

    /* compiled from: NetworkModule.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
