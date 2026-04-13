package io.ktor.server.engine;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import io.ktor.server.engine.a;
import java.util.List;
import kotlin.collections.q;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.s1;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.c;

/* compiled from: EmbeddedServer.kt */
public final class p {
    public static /* synthetic */ a d(f fVar, int i, String str, List list, l lVar, l lVar2, int i2, Object obj) {
        List list2;
        a aVar;
        int i3 = (i2 & 2) != 0 ? 80 : i;
        String str2 = (i2 & 4) != 0 ? NetworkListener.DEFAULT_NETWORK_HOST : str;
        if ((i2 & 8) != 0) {
            list2 = q.g();
        } else {
            list2 = list;
        }
        if ((i2 & 16) != 0) {
            aVar = a.INSTANCE;
        } else {
            aVar = lVar;
        }
        return a(fVar, i3, str2, list2, aVar, lVar2);
    }

    /* compiled from: EmbeddedServer.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<TConfiguration, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((a.C0270a) obj);
            return x.a;
        }

        public final void invoke(@NotNull TConfiguration $receiver) {
            k.f($receiver, "$receiver");
        }
    }

    @NotNull
    public static final <TEngine extends a, TConfiguration extends a.C0270a> TEngine a(@NotNull f<? extends TEngine, TConfiguration> factory, int port, @NotNull String host, @NotNull List<String> watchPaths, @NotNull l<? super TConfiguration, x> configure, @NotNull l<? super io.ktor.application.a, x> module) {
        k.f(factory, "factory");
        k.f(host, SerializableCookie.HOST);
        k.f(watchPaths, "watchPaths");
        k.f(configure, "configure");
        k.f(module, "module");
        return c(s1.c, factory, port, host, watchPaths, h.INSTANCE, configure, module);
    }

    /* compiled from: EmbeddedServer.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<c, x> {
        final /* synthetic */ String $host;
        final /* synthetic */ l $module;
        final /* synthetic */ g $parentCoroutineContext;
        final /* synthetic */ int $port;
        final /* synthetic */ o0 $this_embeddedServer;
        final /* synthetic */ List $watchPaths;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(o0 o0Var, g gVar, List list, l lVar, int i, String str) {
            super(1);
            this.$this_embeddedServer = o0Var;
            this.$parentCoroutineContext = gVar;
            this.$watchPaths = list;
            this.$module = lVar;
            this.$port = i;
            this.$host = str;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((c) obj);
            return x.a;
        }

        public final void invoke(@NotNull c $this$applicationEngineEnvironment) {
            k.f($this$applicationEngineEnvironment, "$receiver");
            $this$applicationEngineEnvironment.e(this.$this_embeddedServer.getCoroutineContext().plus(this.$parentCoroutineContext));
            org.slf4j.b j = c.j("ktor.application");
            k.b(j, "LoggerFactory.getLogger(\"ktor.application\")");
            $this$applicationEngineEnvironment.d(j);
            $this$applicationEngineEnvironment.f(this.$watchPaths);
            $this$applicationEngineEnvironment.c(this.$module);
            List<r> b = $this$applicationEngineEnvironment.b();
            q qVar = new q((l) null, 1, (DefaultConstructorMarker) null);
            q $this$connector = qVar;
            $this$connector.d(this.$port);
            $this$connector.c(this.$host);
            b.add(qVar);
        }
    }

    @NotNull
    public static final <TEngine extends a, TConfiguration extends a.C0270a> TEngine c(@NotNull o0 $this$embeddedServer, @NotNull f<? extends TEngine, TConfiguration> factory, int port, @NotNull String host, @NotNull List<String> watchPaths, @NotNull g parentCoroutineContext, @NotNull l<? super TConfiguration, x> configure, @NotNull l<? super io.ktor.application.a, x> module) {
        k.f($this$embeddedServer, "$this$embeddedServer");
        k.f(factory, "factory");
        k.f(host, SerializableCookie.HOST);
        k.f(watchPaths, "watchPaths");
        k.f(parentCoroutineContext, "parentCoroutineContext");
        k.f(configure, "configure");
        k.f(module, "module");
        return b(factory, d.a(new b($this$embeddedServer, parentCoroutineContext, watchPaths, module, port, host)), configure);
    }

    @NotNull
    public static final <TEngine extends a, TConfiguration extends a.C0270a> TEngine b(@NotNull f<? extends TEngine, TConfiguration> factory, @NotNull b environment, @NotNull l<? super TConfiguration, x> configure) {
        k.f(factory, "factory");
        k.f(environment, "environment");
        k.f(configure, "configure");
        return factory.a(environment, configure);
    }
}
