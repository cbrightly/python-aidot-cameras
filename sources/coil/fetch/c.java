package coil.fetch;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import androidx.annotation.VisibleForTesting;
import coil.decode.b;
import coil.decode.m;
import coil.size.Size;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentUriFetcher.kt */
public final class c implements g<Uri> {
    @NotNull
    private final Context a;

    public c(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    /* renamed from: e */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getScheme(), FirebaseAnalytics.Param.CONTENT);
    }

    @NotNull
    /* renamed from: g */
    public String c(@NotNull Uri data) {
        k.e(data, "data");
        String uri = data.toString();
        k.d(uri, "data.toString()");
        return uri;
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull coil.bitmap.c pool, @NotNull Uri data, @NotNull Size size, @NotNull m options, @NotNull d<? super f> $completion) {
        InputStream stream;
        if (f(data)) {
            AssetFileDescriptor openAssetFileDescriptor = this.a.getContentResolver().openAssetFileDescriptor(data, "r");
            stream = openAssetFileDescriptor == null ? null : openAssetFileDescriptor.createInputStream();
            if (stream == null) {
                throw new IllegalStateException(("Unable to find a contact photo associated with '" + data + "'.").toString());
            }
        } else {
            stream = this.a.getContentResolver().openInputStream(data);
            if (stream == null) {
                throw new IllegalStateException(("Unable to open '" + data + "'.").toString());
            }
        }
        return new m(p.d(p.l(stream)), this.a.getContentResolver().getType(data), b.DISK);
    }

    @VisibleForTesting
    public final boolean f(@NotNull Uri data) {
        k.e(data, "data");
        return k.a(data.getAuthority(), "com.android.contacts") && k.a(data.getLastPathSegment(), "display_photo");
    }
}
