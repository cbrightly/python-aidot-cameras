package io.ktor.utils.io;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Coroutines.kt */
public final class q {
    @NotNull
    public static final v b(@NotNull o0 $this$reader, @NotNull g coroutineContext, @NotNull f channel, @NotNull p<? super w, ? super d<? super x>, ? extends Object> block) {
        k.f($this$reader, "$this$reader");
        k.f(coroutineContext, "coroutineContext");
        k.f(channel, "channel");
        k.f(block, "block");
        return a($this$reader, coroutineContext, channel, false, block);
    }

    @NotNull
    public static final v c(@NotNull o0 $this$reader, @NotNull g coroutineContext, boolean autoFlush, @NotNull p<? super w, ? super d<? super x>, ? extends Object> block) {
        k.f($this$reader, "$this$reader");
        k.f(coroutineContext, "coroutineContext");
        k.f(block, "block");
        return a($this$reader, coroutineContext, g.a(autoFlush), true, block);
    }

    @NotNull
    public static final y d(@NotNull o0 $this$writer, @NotNull g coroutineContext, @NotNull f channel, @NotNull p<? super z, ? super d<? super x>, ? extends Object> block) {
        k.f($this$writer, "$this$writer");
        k.f(coroutineContext, "coroutineContext");
        k.f(channel, "channel");
        k.f(block, "block");
        return a($this$writer, coroutineContext, channel, false, block);
    }

    @NotNull
    public static final y e(@NotNull o0 $this$writer, @NotNull g coroutineContext, boolean autoFlush, @NotNull p<? super z, ? super d<? super x>, ? extends Object> block) {
        k.f($this$writer, "$this$writer");
        k.f(coroutineContext, "coroutineContext");
        k.f(block, "block");
        return a($this$writer, coroutineContext, g.a(autoFlush), true, block);
    }

    @f(c = "io.ktor.utils.io.CoroutinesKt$launchChannel$job$1", f = "Coroutines.kt", l = {124}, m = "invokeSuspend")
    /* compiled from: Coroutines.kt */
    public static final class b extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ boolean $attachJob;
        final /* synthetic */ p $block;
        final /* synthetic */ f $channel;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(boolean z, f fVar, p pVar, d dVar) {
            super(2, dVar);
            this.$attachJob = z;
            this.$channel = fVar;
            this.$block = pVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            k.f(dVar, "completion");
            b bVar = new b(this.$attachJob, this.$channel, this.$block, dVar);
            o0 o0Var = (o0) obj;
            bVar.p$ = (o0) obj;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((b) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$launch = this.p$;
                    if (this.$attachJob) {
                        f fVar = this.$channel;
                        g.b bVar = $this$launch.getCoroutineContext().get(z1.g);
                        if (bVar == null) {
                            k.n();
                        }
                        fVar.s((z1) bVar);
                    }
                    p pVar = this.$block;
                    o oVar = new o($this$launch, this.$channel);
                    this.L$0 = $this$launch;
                    this.label = 1;
                    if (pVar.invoke(oVar, this) != d) {
                        o0 o0Var = $this$launch;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$launch2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    private static final <S extends o0> n a(@NotNull o0 $this$launchChannel, g context, f channel, boolean attachJob, p<? super S, ? super d<? super x>, ? extends Object> block) {
        z1 job = j.d($this$launchChannel, context, (q0) null, new b(attachJob, channel, block, (d) null), 2, (Object) null);
        job.t(new a(channel));
        return new n(job, channel);
    }

    /* compiled from: Coroutines.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ f $channel;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(f fVar) {
            super(1);
            this.$channel = fVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable cause) {
            this.$channel.d(cause);
        }
    }
}
