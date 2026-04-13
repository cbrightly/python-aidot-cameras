package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: AppDto.kt */
public final class AppDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;

    @NotNull
    public final AppDto copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3) {
        k.e(str, "id");
        k.e(str2, "status");
        k.e(str3, "name");
        return new AppDto(str, str2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppDto)) {
            return false;
        }
        AppDto appDto = (AppDto) obj;
        return k.a(this.a, appDto.a) && k.a(this.b, appDto.b) && k.a(this.c, appDto.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "AppDto(id=" + this.a + ", status=" + this.b + ", name=" + this.c + ')';
    }

    public AppDto(@e(name = "_id") @NotNull String id, @NotNull String status, @NotNull String name) {
        k.e(id, "id");
        k.e(status, "status");
        k.e(name, "name");
        this.a = id;
        this.b = status;
        this.c = name;
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @NotNull
    public final String b() {
        return this.c;
    }
}
