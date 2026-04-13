package coil.fetch;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;
import coil.bitmap.c;
import coil.decode.f;
import coil.decode.m;
import coil.size.Size;
import coil.util.e;
import com.yanzhenjie.andserver.util.h;
import java.io.InputStream;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.collections.y;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.v;
import kotlin.text.w;
import kotlin.text.x;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ResourceUriFetcher.kt */
public final class l implements g<Uri> {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context b;
    @NotNull
    private final f c;

    public l(@NotNull Context context, @NotNull f drawableDecoder) {
        k.e(context, "context");
        k.e(drawableDecoder, "drawableDecoder");
        this.b = context;
        this.c = drawableDecoder;
    }

    /* renamed from: e */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getScheme(), "android.resource");
    }

    @NotNull
    /* renamed from: f */
    public String c(@NotNull Uri data) {
        k.e(data, "data");
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        sb.append('-');
        Configuration configuration = this.b.getResources().getConfiguration();
        k.d(configuration, "context.resources.configuration");
        sb.append(coil.util.f.f(configuration));
        return sb.toString();
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull c pool, @NotNull Uri data, @NotNull Size size, @NotNull m options, @NotNull d<? super f> $completion) {
        Drawable drawable;
        e eVar;
        Drawable drawable2;
        e eVar2;
        Uri uri = data;
        String packageName = data.getAuthority();
        Integer num = null;
        if (packageName == null || !b.a(!w.A(packageName)).booleanValue()) {
            packageName = null;
        }
        if (packageName != null) {
            List<String> pathSegments = data.getPathSegments();
            k.d(pathSegments, "data.pathSegments");
            String str = (String) y.f0(pathSegments);
            if (str != null) {
                num = v.o(str);
            }
            if (num != null) {
                int resId = num.intValue();
                Context context = options.e();
                Resources resources = context.getPackageManager().getResourcesForApplication(packageName);
                k.d(resources, "context.packageManager.getResourcesForApplication(packageName)");
                TypedValue $this$fetch_u24lambda_u2d1 = new TypedValue();
                resources.getValue(resId, $this$fetch_u24lambda_u2d1, true);
                CharSequence path = $this$fetch_u24lambda_u2d1.string;
                k.d(path, "path");
                String entryName = path.subSequence(x.j0(path, '/', 0, false, 6, (Object) null), path.length()).toString();
                MimeTypeMap singleton = MimeTypeMap.getSingleton();
                k.d(singleton, "getSingleton()");
                String mimeType = coil.util.f.e(singleton, entryName);
                if (k.a(mimeType, h.TEXT_XML_VALUE)) {
                    if (k.a(packageName, context.getPackageName())) {
                        drawable = e.a(context, resId);
                    } else {
                        drawable = e.d(context, resources, resId);
                    }
                    boolean isVector = coil.util.f.k(drawable);
                    if (isVector) {
                        String str2 = packageName;
                        eVar2 = eVar;
                        Bitmap $this$toDrawable$iv = this.c.a(drawable, options.d(), size, options.k(), options.a());
                        Resources resources$iv$iv = context.getResources();
                        k.d(resources$iv$iv, "context.resources");
                        CharSequence charSequence = path;
                        drawable2 = new BitmapDrawable(resources$iv$iv, $this$toDrawable$iv);
                    } else {
                        CharSequence charSequence2 = path;
                        eVar2 = eVar;
                        drawable2 = drawable;
                    }
                    return new e(drawable2, isVector, coil.decode.b.DISK);
                }
                CharSequence charSequence3 = path;
                InputStream openRawResource = resources.openRawResource(resId);
                k.d(openRawResource, "resources.openRawResource(resId)");
                return new m(p.d(p.l(openRawResource)), mimeType, coil.decode.b.DISK);
            }
            g(uri);
            throw new KotlinNothingValueException();
        }
        g(uri);
        throw new KotlinNothingValueException();
    }

    private final Void g(Uri data) {
        throw new IllegalStateException(k.l("Invalid android.resource URI: ", data));
    }

    /* compiled from: ResourceUriFetcher.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
