package zendesk.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskResult.kt */
public abstract class f<T, E> {
    public /* synthetic */ f(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private f() {
    }

    /* compiled from: ZendeskResult.kt */
    public static final class b<T> extends f {
        private final T a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && k.a(this.a, ((b) obj).a);
        }

        public int hashCode() {
            T t = this.a;
            if (t == null) {
                return 0;
            }
            return t.hashCode();
        }

        @NotNull
        public String toString() {
            return "Success(value=" + this.a + ')';
        }

        public b(T value) {
            super((DefaultConstructorMarker) null);
            this.a = value;
        }

        public final T a() {
            return this.a;
        }
    }

    /* compiled from: ZendeskResult.kt */
    public static final class a<E> extends f {
        private final E a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && k.a(this.a, ((a) obj).a);
        }

        public int hashCode() {
            E e = this.a;
            if (e == null) {
                return 0;
            }
            return e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Failure(error=" + this.a + ')';
        }

        public a(E error) {
            super((DefaultConstructorMarker) null);
            this.a = error;
        }

        public final E a() {
            return this.a;
        }
    }
}
