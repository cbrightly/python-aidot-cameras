package coil.fetch;

import android.net.Uri;
import kotlin.jvm.internal.k;
import okhttp3.e;
import okhttp3.v;
import org.apache.http.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpFetcher.kt */
public final class j extends i<Uri> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull e.a callFactory) {
        super(callFactory);
        k.e(callFactory, "callFactory");
    }

    /* renamed from: g */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getScheme(), l.DEFAULT_SCHEME_NAME) || k.a(data.getScheme(), "https");
    }

    @NotNull
    /* renamed from: h */
    public String c(@NotNull Uri data) {
        k.e(data, "data");
        String uri = data.toString();
        k.d(uri, "data.toString()");
        return uri;
    }

    @NotNull
    /* renamed from: i */
    public v f(@NotNull Uri $this$toHttpUrl) {
        k.e($this$toHttpUrl, "<this>");
        v i = v.i($this$toHttpUrl.toString());
        k.d(i, "get(toString())");
        return i;
    }
}
