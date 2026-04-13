package zendesk.ui.android.internal;

import android.content.Context;
import android.net.Uri;
import coil.bitmap.c;
import coil.decode.b;
import coil.fetch.f;
import coil.fetch.g;
import coil.size.Size;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskContentUriFetcher.kt */
public final class m implements g<Uri> {
    @NotNull
    private final Context a;

    public m(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    /* renamed from: e */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getScheme(), FirebaseAnalytics.Param.CONTENT);
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
    public Object b(@NotNull c pool, @NotNull Uri data, @NotNull Size size, @NotNull coil.decode.m options, @NotNull d<? super f> $completion) {
        Object obj;
        try {
            o.a aVar = o.Companion;
            obj = o.m17constructorimpl(this.a.getContentResolver().openInputStream(data));
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            obj = o.m17constructorimpl(p.a(th));
        }
        if (o.m22isFailureimpl(obj)) {
            obj = null;
        }
        InputStream inputStream = (InputStream) obj;
        if (inputStream != null) {
            return new coil.fetch.m(okio.p.d(okio.p.l(inputStream)), this.a.getContentResolver().getType(data), b.DISK);
        }
        throw new IllegalStateException(("Unable to open '" + data + "'.").toString());
    }
}
