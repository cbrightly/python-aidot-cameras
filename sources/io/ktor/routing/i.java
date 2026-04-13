package io.ktor.routing;

import io.ktor.application.b;
import io.ktor.application.c;
import io.ktor.util.pipeline.d;
import io.ktor.util.pipeline.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Route.kt */
public class i extends c {
    @Nullable
    private final i A4;
    @NotNull
    private final j B4;
    private volatile c cachedPipeline;
    private final List<i> p4 = new ArrayList();
    @NotNull
    private final ArrayList<q<d<x, b>, x, kotlin.coroutines.d<? super x>, Object>> z4 = new ArrayList<>();

    public i(@Nullable i parent, @NotNull j selector) {
        k.f(selector, "selector");
        this.A4 = parent;
        this.B4 = selector;
    }

    @NotNull
    public final j I() {
        return this.B4;
    }

    @NotNull
    public final List<i> G() {
        return this.p4;
    }

    @NotNull
    public final ArrayList<q<d<x, b>, x, kotlin.coroutines.d<? super x>, Object>> H() {
        return this.z4;
    }

    @NotNull
    public final i F(@NotNull j selector) {
        T $this$firstOrNull$iv;
        k.f(selector, "selector");
        Iterator<T> it = this.p4.iterator();
        while (true) {
            if (!it.hasNext()) {
                $this$firstOrNull$iv = null;
                break;
            }
            $this$firstOrNull$iv = it.next();
            if (k.a(((i) $this$firstOrNull$iv).B4, selector)) {
                break;
            }
        }
        i existingEntry = (i) $this$firstOrNull$iv;
        if (existingEntry != null) {
            return existingEntry;
        }
        i entry = new i(this, selector);
        this.p4.add(entry);
        return entry;
    }

    public final void J(@NotNull q<? super d<x, b>, ? super x, ? super kotlin.coroutines.d<? super x>, ? extends Object> handler) {
        k.f(handler, "handler");
        this.z4.add(handler);
        this.cachedPipeline = null;
    }

    public void b() {
        K();
    }

    private final void K() {
        this.cachedPipeline = null;
        for (i it : this.p4) {
            it.K();
        }
    }

    @NotNull
    public final c D() {
        c cVar = this.cachedPipeline;
        if (cVar != null) {
            return cVar;
        }
        c pipeline = new c();
        List routePipelines = new ArrayList();
        for (i current = this; current != null; current = current.A4) {
            routePipelines.add(current);
        }
        for (int index = kotlin.collections.q.i(routePipelines); index >= 0; index--) {
            c routePipeline = (c) routePipelines.get(index);
            pipeline.r(routePipeline);
            pipeline.B().r(routePipeline.B());
            pipeline.C().r(routePipeline.C());
        }
        ArrayList handlers = this.z4;
        int index2 = 0;
        int i = kotlin.collections.q.i(handlers);
        if (i >= 0) {
            while (true) {
                g a = c.a2.a();
                q<d<x, b>, x, kotlin.coroutines.d<? super x>, Object> qVar = handlers.get(index2);
                k.b(qVar, "handlers[index]");
                pipeline.p(a, qVar);
                if (index2 == i) {
                    break;
                }
                index2++;
            }
        }
        this.cachedPipeline = pipeline;
        return pipeline;
    }

    @NotNull
    public String toString() {
        String parentText;
        i iVar = this.A4;
        if (iVar == null) {
            StringBuilder sb = new StringBuilder();
            sb.append('/');
            sb.append(this.B4);
            return sb.toString();
        } else if (iVar.A4 == null) {
            String parentText2 = iVar.toString();
            if (kotlin.text.x.V(parentText2, '/', false, 2, (Object) null)) {
                parentText = parentText2 + this.B4;
            } else {
                parentText = parentText2 + '/' + this.B4;
            }
            return parentText;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.A4);
            sb2.append('/');
            sb2.append(this.B4);
            return sb2.toString();
        }
    }
}
