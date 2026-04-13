package coil.memory;

import android.graphics.Bitmap;
import android.view.View;
import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.collection.SimpleArrayMap;
import coil.request.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ViewTargetRequestManager.kt */
public final class t implements View.OnAttachStateChangeListener {
    @Nullable
    private ViewTargetRequestDelegate c;
    @Nullable
    private volatile UUID d;
    @Nullable
    private volatile z1 f;
    @NotNull
    private final SimpleArrayMap<Object, Bitmap> p0 = new SimpleArrayMap<>();
    @Nullable
    private volatile j.a q;
    @Nullable
    private volatile z1 x;
    private boolean y;
    private boolean z = true;

    @Nullable
    public final UUID b() {
        return this.d;
    }

    public final void g(@Nullable j.a aVar) {
        this.q = aVar;
    }

    @Nullable
    @MainThread
    public final Bitmap d(@NotNull Object tag, @Nullable Bitmap bitmap) {
        k.e(tag, Progress.TAG);
        if (bitmap != null) {
            return this.p0.put(tag, bitmap);
        }
        return this.p0.remove(tag);
    }

    @MainThread
    public final void e(@Nullable ViewTargetRequestDelegate request) {
        if (this.y) {
            this.y = false;
        } else {
            z1 z1Var = this.x;
            if (z1Var != null) {
                z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
            }
            this.x = null;
        }
        ViewTargetRequestDelegate viewTargetRequestDelegate = this.c;
        if (viewTargetRequestDelegate != null) {
            viewTargetRequestDelegate.b();
        }
        this.c = request;
        this.z = true;
    }

    @NotNull
    @AnyThread
    public final UUID f(@NotNull z1 job) {
        k.e(job, "job");
        UUID requestId = c();
        this.d = requestId;
        this.f = job;
        return requestId;
    }

    @AnyThread
    public final void a() {
        this.d = null;
        this.f = null;
        z1 z1Var = this.x;
        if (z1Var != null) {
            z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
        }
        d1 d1Var = d1.a;
        this.x = kotlinx.coroutines.j.d(p0.a(d1.c().W()), (g) null, (q0) null, new a(this, (d<? super a>) null), 3, (Object) null);
    }

    @f(c = "coil.memory.ViewTargetRequestManager$clearCurrentRequest$1", f = "ViewTargetRequestManager.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ViewTargetRequestManager.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        int label;
        final /* synthetic */ t this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(t tVar, d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = tVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.e((ViewTargetRequestDelegate) null);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @MainThread
    public void onViewAttachedToWindow(@NotNull View v) {
        k.e(v, "v");
        if (this.z) {
            this.z = false;
            return;
        }
        ViewTargetRequestDelegate request = this.c;
        if (request != null) {
            this.y = true;
            request.c();
        }
    }

    @MainThread
    public void onViewDetachedFromWindow(@NotNull View v) {
        k.e(v, "v");
        this.z = false;
        ViewTargetRequestDelegate viewTargetRequestDelegate = this.c;
        if (viewTargetRequestDelegate != null) {
            viewTargetRequestDelegate.b();
        }
    }

    @AnyThread
    private final UUID c() {
        UUID requestId = this.d;
        if (requestId != null && this.y && coil.util.f.j()) {
            return requestId;
        }
        UUID randomUUID = UUID.randomUUID();
        k.d(randomUUID, "randomUUID()");
        return randomUUID;
    }
}
