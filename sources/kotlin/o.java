package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Result.kt */
public final class o<T> implements Serializable {
    @NotNull
    public static final a Companion = new a((DefaultConstructorMarker) null);
    @Nullable
    private final Object value;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ o m16boximpl(Object obj) {
        return new o(obj);
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m18equalsimpl(Object obj, Object obj2) {
        return (obj2 instanceof o) && k.a(obj, ((o) obj2).m25unboximpl());
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m19equalsimpl0(Object obj, Object obj2) {
        return k.a(obj, obj2);
    }

    public static /* synthetic */ void getValue$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m21hashCodeimpl(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public boolean equals(Object obj) {
        return m18equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m21hashCodeimpl(this.value);
    }

    @NotNull
    public String toString() {
        return m24toStringimpl(this.value);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ Object m25unboximpl() {
        return this.value;
    }

    private /* synthetic */ o(Object value2) {
        this.value = value2;
    }

    @NotNull
    /* renamed from: constructor-impl  reason: not valid java name */
    public static Object m17constructorimpl(@Nullable Object value2) {
        return value2;
    }

    /* renamed from: isSuccess-impl  reason: not valid java name */
    public static final boolean m23isSuccessimpl(Object $this) {
        return !($this instanceof b);
    }

    /* renamed from: isFailure-impl  reason: not valid java name */
    public static final boolean m22isFailureimpl(Object $this) {
        return $this instanceof b;
    }

    @Nullable
    /* renamed from: exceptionOrNull-impl  reason: not valid java name */
    public static final Throwable m20exceptionOrNullimpl(Object $this) {
        if ($this instanceof b) {
            return ((b) $this).exception;
        }
        return null;
    }

    @NotNull
    /* renamed from: toString-impl  reason: not valid java name */
    public static String m24toStringimpl(Object $this) {
        if ($this instanceof b) {
            return $this.toString();
        }
        return "Success(" + $this + ')';
    }

    /* compiled from: Result.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* compiled from: Result.kt */
    public static final class b implements Serializable {
        @NotNull
        public final Throwable exception;

        public b(@NotNull Throwable exception2) {
            k.e(exception2, "exception");
            this.exception = exception2;
        }

        public boolean equals(@Nullable Object other) {
            return (other instanceof b) && k.a(this.exception, ((b) other).exception);
        }

        public int hashCode() {
            return this.exception.hashCode();
        }

        @NotNull
        public String toString() {
            return "Failure(" + this.exception + ')';
        }
    }
}
