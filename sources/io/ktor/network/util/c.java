package io.ktor.network.util;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.net.SocketTimeoutException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Utils.kt */
public final class c {
    @Nullable
    public static final Object a(@NotNull o0 $this$withSocketTimeout, long socketTimeout, @NotNull p<? super o0, ? super d<? super x>, ? extends Object> block, @NotNull d<? super x> $completion) {
        if (socketTimeout == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            Object invoke = block.invoke($this$withSocketTimeout, $completion);
            if (invoke == kotlin.coroutines.intrinsics.c.d()) {
                return invoke;
            }
        } else {
            Object r = j.b($this$withSocketTimeout, (g) null, (q0) null, new a(socketTimeout, block, (d) null), 3, (Object) null).r($completion);
            if (r == kotlin.coroutines.intrinsics.c.d()) {
                return r;
            }
        }
        return x.a;
    }

    @f(c = "io.ktor.network.util.UtilsKt$withSocketTimeout$2", f = "Utils.kt", l = {23}, m = "invokeSuspend")
    /* compiled from: Utils.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ p $block;
        final /* synthetic */ long $socketTimeout;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(long j, p pVar, d dVar) {
            super(2, dVar);
            this.$socketTimeout = j;
            this.$block = pVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            k.f(dVar, "completion");
            a aVar = new a(this.$socketTimeout, this.$block, dVar);
            o0 o0Var = (o0) obj;
            aVar.p$ = (o0) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object obj;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$async = this.p$;
                    long j = this.$socketTimeout;
                    p pVar = this.$block;
                    this.L$0 = $this$async;
                    this.label = 1;
                    obj = z2.c(j, pVar, this);
                    if (obj != d) {
                        o0 o0Var = $this$async;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$async2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    obj = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (((x) obj) != null) {
                return x.a;
            }
            throw new SocketTimeoutException();
        }
    }
}
