package coil.size;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Size.kt */
public final class OriginalSize extends Size {
    @NotNull
    public static final Parcelable.Creator<OriginalSize> CREATOR = new a();
    @NotNull
    public static final OriginalSize c = new OriginalSize();

    /* compiled from: Size.kt */
    public static final class a implements Parcelable.Creator<OriginalSize> {
        @NotNull
        /* renamed from: a */
        public final OriginalSize createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            parcel.readInt();
            return OriginalSize.c;
        }

        @NotNull
        /* renamed from: b */
        public final OriginalSize[] newArray(int i) {
            return new OriginalSize[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NotNull Parcel parcel, int i) {
        k.e(parcel, "out");
        parcel.writeInt(1);
    }

    private OriginalSize() {
        super((DefaultConstructorMarker) null);
    }

    @NotNull
    public String toString() {
        return "coil.size.OriginalSize";
    }
}
