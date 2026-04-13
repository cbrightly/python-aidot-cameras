package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class CoordinatesDto {
    private final double a;
    private final double b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoordinatesDto)) {
            return false;
        }
        CoordinatesDto coordinatesDto = (CoordinatesDto) obj;
        return k.a(Double.valueOf(this.a), Double.valueOf(coordinatesDto.a)) && k.a(Double.valueOf(this.b), Double.valueOf(coordinatesDto.b));
    }

    public int hashCode() {
        return (Double.doubleToLongBits(this.a) * 31) + Double.doubleToLongBits(this.b);
    }

    @NotNull
    public String toString() {
        return "CoordinatesDto(lat=" + this.a + ", long=" + this.b + ')';
    }

    public CoordinatesDto(double lat, double d) {
        this.a = lat;
        this.b = d;
    }

    public final double a() {
        return this.a;
    }

    public final double b() {
        return this.b;
    }
}
