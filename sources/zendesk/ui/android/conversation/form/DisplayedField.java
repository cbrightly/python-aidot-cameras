package zendesk.ui.android.conversation.form;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormView.kt */
public final class DisplayedField implements Parcelable {
    @NotNull
    public static final Parcelable.Creator<DisplayedField> CREATOR = new a();
    private final int c;
    @Nullable
    private final String d;

    /* compiled from: FormView.kt */
    public static final class a implements Parcelable.Creator<DisplayedField> {
        @NotNull
        /* renamed from: a */
        public final DisplayedField createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            return new DisplayedField(parcel.readInt(), parcel.readString());
        }

        @NotNull
        /* renamed from: b */
        public final DisplayedField[] newArray(int i) {
            return new DisplayedField[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayedField)) {
            return false;
        }
        DisplayedField displayedField = (DisplayedField) obj;
        return this.c == displayedField.c && k.a(this.d, displayedField.d);
    }

    public int hashCode() {
        int i = this.c * 31;
        String str = this.d;
        return i + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "DisplayedField(index=" + this.c + ", value=" + this.d + ')';
    }

    public void writeToParcel(@NotNull Parcel parcel, int i) {
        k.e(parcel, "out");
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
    }

    public DisplayedField(int index, @Nullable String value) {
        this.c = index;
        this.d = value;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DisplayedField(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str);
    }

    public final int a() {
        return this.c;
    }

    @Nullable
    public final String b() {
        return this.d;
    }
}
