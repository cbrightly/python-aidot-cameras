package io.ktor.utils.io.errors;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.internal.e;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Errors.kt */
public final class a {

    /* renamed from: io.ktor.utils.io.errors.a$a  reason: collision with other inner class name */
    /* compiled from: Require.kt */
    public static final class C0285a extends e {
        final /* synthetic */ int a;

        public C0285a(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("offset shouldn't be negative: " + this.a + '.');
        }
    }

    /* compiled from: Require.kt */
    public static final class b extends e {
        final /* synthetic */ int a;

        public b(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("min shouldn't be negative: " + this.a + '.');
        }
    }

    /* compiled from: Require.kt */
    public static final class c extends e {
        final /* synthetic */ int a;
        final /* synthetic */ int b;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("max should't be less than min: max = " + this.a + ", min = " + this.b + '.');
        }
    }

    /* compiled from: Require.kt */
    public static final class d extends e {
        final /* synthetic */ int a;
        final /* synthetic */ io.ktor.utils.io.core.c b;

        public d(int i, io.ktor.utils.io.core.c cVar) {
            this.a = i;
            this.b = cVar;
        }

        @NotNull
        public Void a() {
            StringBuilder sb = new StringBuilder();
            sb.append("Not enough free space in the destination buffer ");
            sb.append("to write the specified minimum number of bytes: min = ");
            sb.append(this.a);
            sb.append(", free = ");
            io.ktor.utils.io.core.c this_$iv = this.b;
            sb.append(this_$iv.m() - this_$iv.s());
            sb.append('.');
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static final void a(@NotNull io.ktor.utils.io.core.c destination, int offset, int min, int max) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        boolean condition$iv = true;
        if (offset >= 0) {
            if (min >= 0) {
                if (max >= min) {
                    io.ktor.utils.io.core.c this_$iv = destination;
                    if (min > this_$iv.m() - this_$iv.s()) {
                        condition$iv = false;
                    }
                    if (!condition$iv) {
                        new d(min, destination).a();
                        throw null;
                    }
                    return;
                }
                new c(max, min).a();
                throw null;
            }
            new b(min).a();
            throw null;
        }
        new C0285a(offset).a();
        throw null;
    }
}
