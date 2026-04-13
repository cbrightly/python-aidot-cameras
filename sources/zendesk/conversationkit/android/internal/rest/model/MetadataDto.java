package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UploadFileDto.kt */
public final class MetadataDto {
    @NotNull
    private final Map<String, Object> a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MetadataDto) && k.a(this.a, ((MetadataDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "MetadataDto(metadata=" + this.a + ')';
    }

    public MetadataDto(@NotNull Map<String, ? extends Object> metadata) {
        k.e(metadata, "metadata");
        this.a = metadata;
    }

    @NotNull
    public final Map<String, Object> a() {
        return this.a;
    }
}
