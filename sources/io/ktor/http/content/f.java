package io.ktor.http.content;

import io.ktor.http.c;
import io.ktor.http.content.j;
import io.ktor.http.i;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;
import java.util.List;
import kotlin.collections.y;
import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LocalFileContent.kt */
public final class f extends j.d {
    @NotNull
    private final File b;
    @NotNull
    private final c c;

    public f(@NotNull File file, @NotNull c contentType) {
        k.f(file, "file");
        k.f(contentType, "contentType");
        this.b = file;
        this.c = contentType;
        List<?> a = n.a(this);
        FileTime lastModifiedTime = Files.getLastModifiedTime(file.toPath(), new LinkOption[0]);
        k.b(lastModifiedTime, "Files.getLastModifiedTime(file.toPath())");
        n.b(this, y.o0(a, d.a(lastModifiedTime)));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ f(File file, c cVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? i.a(c.e, file) : cVar);
    }

    @NotNull
    public c b() {
        return this.c;
    }

    @NotNull
    public Long a() {
        return Long.valueOf(this.b.length());
    }

    @NotNull
    public io.ktor.utils.io.i g() {
        return io.ktor.util.cio.c.b(this.b, 0, 0, (g) null, 7, (Object) null);
    }
}
