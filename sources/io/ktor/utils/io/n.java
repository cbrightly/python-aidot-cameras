package io.ktor.utils.io;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.sequences.h;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.t;
import kotlinx.coroutines.v;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Coroutines.kt */
public final class n implements v, y, z1 {
    private final z1 c;
    @NotNull
    private final f d;

    public boolean I() {
        return this.c.I();
    }

    @Nullable
    public Object J(@NotNull d<? super x> dVar) {
        return this.c.J(dVar);
    }

    @NotNull
    public t T(@NotNull v vVar) {
        k.f(vVar, "child");
        return this.c.T(vVar);
    }

    public void c(@Nullable CancellationException cancellationException) {
        this.c.c(cancellationException);
    }

    public <R> R fold(R r, @NotNull p<? super R, ? super g.b, ? extends R> pVar) {
        k.f(pVar, "operation");
        return this.c.fold(r, pVar);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> cVar) {
        k.f(cVar, CacheEntity.KEY);
        return this.c.get(cVar);
    }

    @NotNull
    public h<z1> getChildren() {
        return this.c.getChildren();
    }

    @NotNull
    public g.c<?> getKey() {
        return this.c.getKey();
    }

    public boolean isActive() {
        return this.c.isActive();
    }

    public boolean isCancelled() {
        return this.c.isCancelled();
    }

    @NotNull
    public f1 m(boolean z, boolean z2, @NotNull l<? super Throwable, x> lVar) {
        k.f(lVar, "handler");
        return this.c.m(z, z2, lVar);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> cVar) {
        k.f(cVar, CacheEntity.KEY);
        return this.c.minusKey(cVar);
    }

    @NotNull
    public CancellationException n() {
        return this.c.n();
    }

    @NotNull
    public g plus(@NotNull g gVar) {
        k.f(gVar, "context");
        return this.c.plus(gVar);
    }

    public boolean start() {
        return this.c.start();
    }

    @NotNull
    public f1 t(@NotNull l<? super Throwable, x> lVar) {
        k.f(lVar, "handler");
        return this.c.t(lVar);
    }

    public n(@NotNull z1 delegate, @NotNull f channel) {
        k.f(delegate, "delegate");
        k.f(channel, "channel");
        this.c = delegate;
        this.d = channel;
    }

    @NotNull
    /* renamed from: b */
    public f getChannel() {
        return this.d;
    }

    @NotNull
    public String toString() {
        return "ChannelJob[" + this.c + ']';
    }
}
