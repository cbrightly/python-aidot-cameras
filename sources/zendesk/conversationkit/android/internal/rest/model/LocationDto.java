package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class LocationDto {
    @Nullable
    private final String a;
    @Nullable
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationDto)) {
            return false;
        }
        LocationDto locationDto = (LocationDto) obj;
        return k.a(this.a, locationDto.a) && k.a(this.b, locationDto.b);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "LocationDto(name=" + this.a + ", address=" + this.b + ')';
    }

    public LocationDto(@Nullable String name, @Nullable String address) {
        this.a = name;
        this.b = address;
    }

    @Nullable
    public final String b() {
        return this.a;
    }

    @Nullable
    public final String a() {
        return this.b;
    }
}
