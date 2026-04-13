package io.ktor.util.date;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Date.kt */
public final class c implements Comparable<c> {
    @NotNull
    private static final c c = a.b(0L);
    public static final a d = new a((DefaultConstructorMarker) null);
    @NotNull
    private final d a1;
    private final long a2;
    private final int f;
    private final int p0;
    private final int p1;
    private final int q;
    private final int x;
    @NotNull
    private final e y;
    private final int z;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return this.f == cVar.f && this.q == cVar.q && this.x == cVar.x && k.a(this.y, cVar.y) && this.z == cVar.z && this.p0 == cVar.p0 && k.a(this.a1, cVar.a1) && this.p1 == cVar.p1 && this.a2 == cVar.a2;
    }

    public int hashCode() {
        int i = ((((this.f * 31) + this.q) * 31) + this.x) * 31;
        e eVar = this.y;
        int i2 = 0;
        int hashCode = (((((i + (eVar != null ? eVar.hashCode() : 0)) * 31) + this.z) * 31) + this.p0) * 31;
        d dVar = this.a1;
        if (dVar != null) {
            i2 = dVar.hashCode();
        }
        long j = this.a2;
        return ((((hashCode + i2) * 31) + this.p1) * 31) + ((int) (j ^ (j >>> 32)));
    }

    @NotNull
    public String toString() {
        return "GMTDate(seconds=" + this.f + ", minutes=" + this.q + ", hours=" + this.x + ", dayOfWeek=" + this.y + ", dayOfMonth=" + this.z + ", dayOfYear=" + this.p0 + ", month=" + this.a1 + ", year=" + this.p1 + ", timestamp=" + this.a2 + ")";
    }

    public c(int seconds, int minutes, int hours, @NotNull e dayOfWeek, int dayOfMonth, int dayOfYear, @NotNull d month, int year, long timestamp) {
        k.f(dayOfWeek, "dayOfWeek");
        k.f(month, "month");
        this.f = seconds;
        this.q = minutes;
        this.x = hours;
        this.y = dayOfWeek;
        this.z = dayOfMonth;
        this.p0 = dayOfYear;
        this.a1 = month;
        this.p1 = year;
        this.a2 = timestamp;
    }

    public final int h() {
        return this.f;
    }

    public final int e() {
        return this.q;
    }

    public final int d() {
        return this.x;
    }

    @NotNull
    public final e c() {
        return this.y;
    }

    public final int b() {
        return this.z;
    }

    @NotNull
    public final d f() {
        return this.a1;
    }

    public final int j() {
        return this.p1;
    }

    /* renamed from: a */
    public int compareTo(@NotNull c other) {
        k.f(other, "other");
        return (this.a2 > other.a2 ? 1 : (this.a2 == other.a2 ? 0 : -1));
    }

    /* compiled from: Date.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
