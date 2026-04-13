package coil.size;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Px;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Size.kt */
public final class PixelSize extends Size {
    @NotNull
    public static final Parcelable.Creator<PixelSize> CREATOR = new a();
    private final int c;
    private final int d;

    /* compiled from: Size.kt */
    public static final class a implements Parcelable.Creator<PixelSize> {
        @NotNull
        /* renamed from: a */
        public final PixelSize createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            return new PixelSize(parcel.readInt(), parcel.readInt());
        }

        @NotNull
        /* renamed from: b */
        public final PixelSize[] newArray(int i) {
            return new PixelSize[i];
        }
    }

    public final int a() {
        return this.c;
    }

    public final int b() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PixelSize)) {
            return false;
        }
        PixelSize pixelSize = (PixelSize) obj;
        return this.c == pixelSize.c && this.d == pixelSize.d;
    }

    public int hashCode() {
        return (this.c * 31) + this.d;
    }

    @NotNull
    public String toString() {
        return "PixelSize(width=" + this.c + ", height=" + this.d + ')';
    }

    public void writeToParcel(@NotNull Parcel parcel, int i) {
        k.e(parcel, "out");
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
    }

    public final int d() {
        return this.c;
    }

    public final int c() {
        return this.d;
    }

    public PixelSize(@Px int width, @Px int height) {
        super((DefaultConstructorMarker) null);
        this.c = width;
        this.d = height;
        if (!(width > 0 && height > 0)) {
            throw new IllegalArgumentException("width and height must be > 0.".toString());
        }
    }
}
