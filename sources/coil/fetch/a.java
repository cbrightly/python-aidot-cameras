package coil.fetch;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import coil.bitmap.c;
import coil.decode.b;
import coil.decode.m;
import coil.size.Size;
import coil.util.f;
import java.io.InputStream;
import java.util.List;
import kotlin.collections.y;
import kotlin.coroutines.d;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AssetUriFetcher.kt */
public final class a implements g<Uri> {
    @NotNull
    public static final C0005a a = new C0005a((DefaultConstructorMarker) null);
    @NotNull
    private final Context b;

    public a(@NotNull Context context) {
        k.e(context, "context");
        this.b = context;
    }

    /* renamed from: e */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getScheme(), "file") && k.a(f.c(data), "android_asset");
    }

    @NotNull
    /* renamed from: f */
    public String c(@NotNull Uri data) {
        k.e(data, "data");
        String uri = data.toString();
        k.d(uri, "data.toString()");
        return uri;
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull c pool, @NotNull Uri data, @NotNull Size size, @NotNull m options, @NotNull d<? super f> $completion) {
        List<String> pathSegments = data.getPathSegments();
        k.d(pathSegments, "data.pathSegments");
        String path = y.b0(y.O(pathSegments, 1), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
        InputStream open = this.b.getAssets().open(path);
        k.d(open, "context.assets.open(path)");
        e d = p.d(p.l(open));
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        k.d(singleton, "getSingleton()");
        return new m(d, f.e(singleton, path), b.DISK);
    }

    /* renamed from: coil.fetch.a$a  reason: collision with other inner class name */
    /* compiled from: AssetUriFetcher.kt */
    public static final class C0005a {
        public /* synthetic */ C0005a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0005a() {
        }
    }
}
