package zendesk.conversationkit.android.internal.rest.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UploadFileDto.kt */
public final class d {
    @NotNull
    private final AuthorDto a;
    @NotNull
    private final MetadataDto b;
    @NotNull
    private final c c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return k.a(this.a, dVar.a) && k.a(this.b, dVar.b) && k.a(this.c, dVar.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "UploadFileDto(author=" + this.a + ", metadata=" + this.b + ", upload=" + this.c + ')';
    }

    public d(@NotNull AuthorDto author, @NotNull MetadataDto metadata, @NotNull c upload) {
        k.e(author, "author");
        k.e(metadata, "metadata");
        k.e(upload, "upload");
        this.a = author;
        this.b = metadata;
        this.c = upload;
    }

    @NotNull
    public final AuthorDto a() {
        return this.a;
    }

    @NotNull
    public final MetadataDto b() {
        return this.b;
    }

    @NotNull
    public final c c() {
        return this.c;
    }
}
