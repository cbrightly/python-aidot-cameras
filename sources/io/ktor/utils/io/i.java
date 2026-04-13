package io.ktor.utils.io;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.core.q;
import java.nio.ByteBuffer;
import kotlin.coroutines.d;
import kotlin.g;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteReadChannelJVM.kt */
public interface i {
    public static final a a = a.b;

    boolean b(@Nullable Throwable th);

    @Nullable
    Object c(@NotNull p<? super x, ? super d<? super x>, ? extends Object> pVar, @NotNull d<? super x> dVar);

    int e();

    boolean f();

    @Nullable
    Object g(long j, @NotNull d<? super Long> dVar);

    @Nullable
    <A extends Appendable> Object i(@NotNull A a2, int i, @NotNull d<? super Boolean> dVar);

    @Nullable
    Object j(@NotNull byte[] bArr, int i, int i2, @NotNull d<? super Integer> dVar);

    @Nullable
    <R> Object l(@NotNull p<? super t, ? super d<? super R>, ? extends Object> pVar, @NotNull d<? super R> dVar);

    @Nullable
    Object o(int i, int i2, @NotNull d<? super q> dVar);

    @Nullable
    Object p(long j, int i, @NotNull d<? super q> dVar);

    @Nullable
    Object r(@NotNull a0 a0Var, @NotNull d<? super Integer> dVar);

    long t();

    <R> R u(@NotNull l<? super s, ? extends R> lVar);

    @Nullable
    Object v(@NotNull ByteBuffer byteBuffer, @NotNull d<? super Integer> dVar);

    boolean y();

    /* compiled from: ByteReadChannelJVM.kt */
    public static final class a {
        @NotNull
        private static final g a = kotlin.i.b(C0287a.INSTANCE);
        static final /* synthetic */ a b = new a();

        @NotNull
        public final i a() {
            return (i) a.getValue();
        }

        /* renamed from: io.ktor.utils.io.i$a$a  reason: collision with other inner class name */
        /* compiled from: ByteReadChannelJVM.kt */
        public static final class C0287a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<f> {
            public static final C0287a INSTANCE = new C0287a();

            C0287a() {
                super(0);
            }

            @NotNull
            public final f invoke() {
                f $this$apply = g.b(false, 1, (Object) null);
                m.a($this$apply);
                return $this$apply;
            }
        }

        private a() {
        }
    }
}
