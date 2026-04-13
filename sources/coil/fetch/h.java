package coil.fetch;

import android.webkit.MimeTypeMap;
import coil.bitmap.c;
import coil.decode.b;
import coil.decode.m;
import coil.fetch.g;
import coil.size.Size;
import java.io.File;
import kotlin.coroutines.d;
import kotlin.io.j;
import kotlin.jvm.internal.k;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileFetcher.kt */
public final class h implements g<File> {
    private final boolean a;

    public h(boolean addLastModifiedToFileCacheKey) {
        this.a = addLastModifiedToFileCacheKey;
    }

    /* renamed from: e */
    public boolean a(@NotNull File data) {
        return g.a.a(this, data);
    }

    @NotNull
    /* renamed from: f */
    public String c(@NotNull File data) {
        k.e(data, "data");
        if (this.a) {
            return data.getPath() + ':' + data.lastModified();
        }
        String path = data.getPath();
        k.d(path, "data.path");
        return path;
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull c pool, @NotNull File data, @NotNull Size size, @NotNull m options, @NotNull d<? super f> $completion) {
        return new m(p.d(p.k(data)), MimeTypeMap.getSingleton().getMimeTypeFromExtension(j.d(data)), b.DISK);
    }
}
