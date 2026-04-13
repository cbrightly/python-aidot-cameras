package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContinuationImpl.kt */
public abstract class d extends a {
    private final g _context;
    private transient kotlin.coroutines.d<Object> intercepted;

    public d(@Nullable kotlin.coroutines.d<Object> completion, @Nullable g _context2) {
        super(completion);
        this._context = _context2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public d(@Nullable kotlin.coroutines.d<Object> completion) {
        this(completion, completion != null ? completion.getContext() : null);
    }

    @NotNull
    public g getContext() {
        g gVar = this._context;
        k.c(gVar);
        return gVar;
    }

    @NotNull
    public final kotlin.coroutines.d<Object> intercepted() {
        kotlin.coroutines.d it = this.intercepted;
        if (it == null) {
            e eVar = (e) getContext().get(e.a);
            if (eVar == null || (it = eVar.interceptContinuation(this)) == null) {
                it = this;
            }
            this.intercepted = it;
        }
        return it;
    }

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
        kotlin.coroutines.d intercepted2 = this.intercepted;
        if (!(intercepted2 == null || intercepted2 == this)) {
            g.b bVar = getContext().get(e.a);
            k.c(bVar);
            ((e) bVar).releaseInterceptedContinuation(intercepted2);
        }
        this.intercepted = c.c;
    }
}
