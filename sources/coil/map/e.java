package coil.map;

import android.net.Uri;
import coil.map.b;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StringMapper.kt */
public final class e implements b<String, Uri> {
    /* renamed from: b */
    public boolean a(@NotNull String data) {
        return b.a.a(this, data);
    }

    @NotNull
    /* renamed from: c */
    public Uri map(@NotNull String data) {
        k.e(data, "data");
        Uri parse = Uri.parse(data);
        k.d(parse, "parse(this)");
        return parse;
    }
}
