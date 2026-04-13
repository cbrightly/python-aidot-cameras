package zendesk.ui.android.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import coil.b;
import coil.d;
import coil.decode.i;
import coil.decode.j;
import coil.decode.n;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.x;
import okhttp3.d0;
import okhttp3.w;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageLoaderFactory.kt */
public final class f {
    @NotNull
    public static final f a = new f();
    @Nullable
    private static d b;
    /* access modifiers changed from: private */
    @NotNull
    public static final w c = a.b;

    private f() {
    }

    /* access modifiers changed from: private */
    public static final d0 b(w.a chain) {
        d0 originalResponse = chain.a(chain.g());
        String cacheControl = originalResponse.n(HttpHeaders.HEAD_KEY_CACHE_CONTROL);
        if (cacheControl == null || x.S(cacheControl, "no-cache", false, 2, (Object) null) || x.S(cacheControl, "max-age=0", false, 2, (Object) null)) {
            return originalResponse.v().j(HttpHeaders.HEAD_KEY_CACHE_CONTROL, "public, max-age=604800").c();
        }
        return originalResponse;
    }

    @NotNull
    public final d c(@NotNull Context context) {
        k.e(context, "context");
        d dVar = b;
        if (dVar != null) {
            return dVar;
        }
        d.a this_$iv = new d.a(context).g(new a(context));
        b.a aVar = new b.a();
        b.a $this$getImageLoader_u24lambda_u2d1 = aVar;
        if (Build.VERSION.SDK_INT >= 28) {
            $this$getImageLoader_u24lambda_u2d1.a(new j(context));
        } else {
            $this$getImageLoader_u24lambda_u2d1.a(new i(false, 1, (DefaultConstructorMarker) null));
        }
        $this$getImageLoader_u24lambda_u2d1.a(new n(context, false, 2, (DefaultConstructorMarker) null));
        $this$getImageLoader_u24lambda_u2d1.b(new m(context), Uri.class);
        kotlin.x xVar = kotlin.x.a;
        d it = this_$iv.f(aVar.d()).b();
        b = it;
        return it;
    }

    /* compiled from: ImageLoaderFactory.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<z> {
        final /* synthetic */ Context $context;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context) {
            super(0);
            this.$context = context;
        }

        @NotNull
        public final z invoke() {
            z c = new z.a().d(coil.util.j.a(this.$context)).b(f.c).c();
            k.d(c, "Builder()\n              …\n                .build()");
            return c;
        }
    }
}
