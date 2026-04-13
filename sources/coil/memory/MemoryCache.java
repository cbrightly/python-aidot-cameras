package coil.memory;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import coil.size.Size;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemoryCache.kt */
public interface MemoryCache {
    @Nullable
    Bitmap b(@NotNull Key key);

    /* compiled from: MemoryCache.kt */
    public static abstract class Key implements Parcelable {
        @NotNull
        public static final a c = new a((DefaultConstructorMarker) null);

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
        }

        /* compiled from: MemoryCache.kt */
        public static final class a {
            public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private a() {
            }

            @NotNull
            public final Key a(@NotNull String value) {
                k.e(value, "value");
                return new Simple(value);
            }
        }

        /* compiled from: MemoryCache.kt */
        public static final class Simple extends Key {
            @NotNull
            public static final Parcelable.Creator<Simple> CREATOR = new a();
            @NotNull
            private final String d;

            /* compiled from: MemoryCache.kt */
            public static final class a implements Parcelable.Creator<Simple> {
                @NotNull
                /* renamed from: a */
                public final Simple createFromParcel(@NotNull Parcel parcel) {
                    k.e(parcel, "parcel");
                    return new Simple(parcel.readString());
                }

                @NotNull
                /* renamed from: b */
                public final Simple[] newArray(int i) {
                    return new Simple[i];
                }
            }

            public int describeContents() {
                return 0;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Simple) && k.a(this.d, ((Simple) obj).d);
            }

            public int hashCode() {
                return this.d.hashCode();
            }

            @NotNull
            public String toString() {
                return "Simple(value=" + this.d + ')';
            }

            public void writeToParcel(@NotNull Parcel parcel, int i) {
                k.e(parcel, "out");
                parcel.writeString(this.d);
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public Simple(@NotNull String value) {
                super((DefaultConstructorMarker) null);
                k.e(value, "value");
                this.d = value;
            }
        }

        /* compiled from: MemoryCache.kt */
        public static final class Complex extends Key {
            @NotNull
            public static final Parcelable.Creator<Complex> CREATOR = new a();
            @NotNull
            private final String d;
            @NotNull
            private final List<String> f;
            @Nullable
            private final Size q;
            @NotNull
            private final Map<String, String> x;

            /* compiled from: MemoryCache.kt */
            public static final class a implements Parcelable.Creator<Complex> {
                @NotNull
                /* renamed from: a */
                public final Complex createFromParcel(@NotNull Parcel parcel) {
                    k.e(parcel, "parcel");
                    String readString = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    Size size = (Size) parcel.readParcelable(Complex.class.getClassLoader());
                    int readInt = parcel.readInt();
                    LinkedHashMap linkedHashMap = new LinkedHashMap(readInt);
                    for (int i = 0; i != readInt; i++) {
                        linkedHashMap.put(parcel.readString(), parcel.readString());
                    }
                    return new Complex(readString, createStringArrayList, size, linkedHashMap);
                }

                @NotNull
                /* renamed from: b */
                public final Complex[] newArray(int i) {
                    return new Complex[i];
                }
            }

            public int describeContents() {
                return 0;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Complex)) {
                    return false;
                }
                Complex complex = (Complex) obj;
                return k.a(this.d, complex.d) && k.a(this.f, complex.f) && k.a(this.q, complex.q) && k.a(this.x, complex.x);
            }

            public int hashCode() {
                int hashCode = ((this.d.hashCode() * 31) + this.f.hashCode()) * 31;
                Size size = this.q;
                return ((hashCode + (size == null ? 0 : size.hashCode())) * 31) + this.x.hashCode();
            }

            @NotNull
            public String toString() {
                return "Complex(base=" + this.d + ", transformations=" + this.f + ", size=" + this.q + ", parameters=" + this.x + ')';
            }

            public void writeToParcel(@NotNull Parcel parcel, int i) {
                k.e(parcel, "out");
                parcel.writeString(this.d);
                parcel.writeStringList(this.f);
                parcel.writeParcelable(this.q, i);
                Map<String, String> map = this.x;
                parcel.writeInt(map.size());
                for (Map.Entry next : map.entrySet()) {
                    parcel.writeString((String) next.getKey());
                    parcel.writeString((String) next.getValue());
                }
            }

            @Nullable
            public final Size a() {
                return this.q;
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public Complex(@NotNull String base, @NotNull List<String> transformations, @Nullable Size size, @NotNull Map<String, String> parameters) {
                super((DefaultConstructorMarker) null);
                k.e(base, "base");
                k.e(transformations, "transformations");
                k.e(parameters, "parameters");
                this.d = base;
                this.f = transformations;
                this.q = size;
                this.x = parameters;
            }
        }
    }
}
