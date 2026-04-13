package zendesk.messaging.android.internal.conversationscreen.attachments;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Attachment.kt */
public final class Attachment implements Parcelable {
    @NotNull
    public static final a CREATOR = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final String f;
    private final long q;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Attachment)) {
            return false;
        }
        Attachment attachment = (Attachment) obj;
        return k.a(this.c, attachment.c) && k.a(this.d, attachment.d) && k.a(this.f, attachment.f) && this.q == attachment.q;
    }

    public int hashCode() {
        return (((((this.c.hashCode() * 31) + this.d.hashCode()) * 31) + this.f.hashCode()) * 31) + com.google.chip.chiptool.setuppayloadscanner.a.a(this.q);
    }

    @NotNull
    public String toString() {
        return "Attachment(name=" + this.c + ", uri=" + this.d + ", mimeType=" + this.f + ", size=" + this.q + ')';
    }

    public Attachment(@NotNull String name, @NotNull String uri, @NotNull String mimeType, long size) {
        k.e(name, "name");
        k.e(uri, "uri");
        k.e(mimeType, "mimeType");
        this.c = name;
        this.d = uri;
        this.f = mimeType;
        this.q = size;
    }

    @NotNull
    public final String b() {
        return this.c;
    }

    @NotNull
    public final String d() {
        return this.d;
    }

    @NotNull
    public final String a() {
        return this.f;
    }

    public final long c() {
        return this.q;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Attachment(@org.jetbrains.annotations.NotNull android.os.Parcel r8) {
        /*
            r7 = this;
            java.lang.String r0 = "parcel"
            kotlin.jvm.internal.k.e(r8, r0)
            java.lang.String r2 = r8.readString()
            kotlin.jvm.internal.k.c(r2)
            java.lang.String r0 = "parcel.readString()!!"
            kotlin.jvm.internal.k.d(r2, r0)
            java.lang.String r3 = r8.readString()
            kotlin.jvm.internal.k.c(r3)
            kotlin.jvm.internal.k.d(r3, r0)
            java.lang.String r4 = r8.readString()
            kotlin.jvm.internal.k.c(r4)
            kotlin.jvm.internal.k.d(r4, r0)
            long r5 = r8.readLong()
            r1 = r7
            r1.<init>(r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.attachments.Attachment.<init>(android.os.Parcel):void");
    }

    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        k.e(parcel, "parcel");
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.f);
        parcel.writeLong(this.q);
    }

    public int describeContents() {
        return 0;
    }

    /* compiled from: Attachment.kt */
    public static final class a implements Parcelable.Creator<Attachment> {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        /* renamed from: a */
        public Attachment createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            return new Attachment(parcel);
        }

        @NotNull
        /* renamed from: b */
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    }
}
