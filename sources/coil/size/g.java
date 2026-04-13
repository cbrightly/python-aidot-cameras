package coil.size;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ViewSizeResolver.kt */
public interface g<T extends View> extends f {
    @NotNull
    public static final a b = a.a;

    boolean a();

    @NotNull
    T getView();

    /* compiled from: ViewSizeResolver.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        public static /* synthetic */ g b(a aVar, View view, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return aVar.a(view, z);
        }

        @NotNull
        public final <T extends View> g<T> a(@NotNull T view, boolean subtractPadding) {
            k.e(view, "view");
            return new d(view, subtractPadding);
        }
    }

    /* compiled from: ViewSizeResolver.kt */
    public static final class b {
        @Nullable
        public static <T extends View> Object h(@NotNull g<T> gVar, @NotNull d<? super Size> $completion) {
            PixelSize it = e(gVar);
            if (it != null) {
                return it;
            }
            o cancellable$iv = new o(kotlin.coroutines.intrinsics.b.c($completion), 1);
            cancellable$iv.w();
            n continuation = cancellable$iv;
            ViewTreeObserver viewTreeObserver = gVar.getView().getViewTreeObserver();
            C0008b preDrawListener = new C0008b(gVar, viewTreeObserver, continuation);
            viewTreeObserver.addOnPreDrawListener(preDrawListener);
            continuation.f(new a(gVar, viewTreeObserver, preDrawListener));
            Object t = cancellable$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            return t;
        }

        /* renamed from: coil.size.g$b$b  reason: collision with other inner class name */
        /* compiled from: ViewSizeResolver.kt */
        public static final class C0008b implements ViewTreeObserver.OnPreDrawListener {
            private boolean c;
            final /* synthetic */ g<T> d;
            final /* synthetic */ ViewTreeObserver f;
            final /* synthetic */ n<Size> q;

            C0008b(g<T> $receiver, ViewTreeObserver $viewTreeObserver, n<? super Size> $continuation) {
                this.d = $receiver;
                this.f = $viewTreeObserver;
                this.q = $continuation;
            }

            public boolean onPreDraw() {
                PixelSize size = b.e(this.d);
                if (size != null) {
                    g<T> gVar = this.d;
                    ViewTreeObserver viewTreeObserver = this.f;
                    k.d(viewTreeObserver, "viewTreeObserver");
                    b.g(gVar, viewTreeObserver, this);
                    if (!this.c) {
                        this.c = true;
                        n<Size> nVar = this.q;
                        o.a aVar = kotlin.o.Companion;
                        nVar.resumeWith(kotlin.o.m17constructorimpl(size));
                    }
                }
                return true;
            }
        }

        /* compiled from: ViewSizeResolver.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ C0008b $preDrawListener;
            final /* synthetic */ ViewTreeObserver $viewTreeObserver;
            final /* synthetic */ g<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(g<T> gVar, ViewTreeObserver viewTreeObserver, C0008b bVar) {
                super(1);
                this.this$0 = gVar;
                this.$viewTreeObserver = viewTreeObserver;
                this.$preDrawListener = bVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((Throwable) p1);
                return x.a;
            }

            public final void invoke(@Nullable Throwable it) {
                g<T> gVar = this.this$0;
                ViewTreeObserver viewTreeObserver = this.$viewTreeObserver;
                k.d(viewTreeObserver, "viewTreeObserver");
                b.g(gVar, viewTreeObserver, this.$preDrawListener);
            }
        }

        /* access modifiers changed from: private */
        public static <T extends View> PixelSize e(g<T> gVar) {
            int width = f(gVar);
            if (width <= 0) {
                return null;
            }
            int height = d(gVar);
            if (height <= 0) {
                return null;
            }
            return new PixelSize(width, height);
        }

        private static <T extends View> int f(g<T> gVar) {
            ViewGroup.LayoutParams layoutParams = gVar.getView().getLayoutParams();
            return c(gVar, layoutParams == null ? -1 : layoutParams.width, gVar.getView().getWidth(), gVar.a() ? gVar.getView().getPaddingLeft() + gVar.getView().getPaddingRight() : 0, true);
        }

        private static <T extends View> int d(g<T> gVar) {
            ViewGroup.LayoutParams layoutParams = gVar.getView().getLayoutParams();
            return c(gVar, layoutParams == null ? -1 : layoutParams.height, gVar.getView().getHeight(), gVar.a() ? gVar.getView().getPaddingTop() + gVar.getView().getPaddingBottom() : 0, false);
        }

        private static <T extends View> int c(g<T> gVar, int paramSize, int viewSize, int paddingSize, boolean isWidth) {
            int insetParamSize = paramSize - paddingSize;
            if (insetParamSize > 0) {
                return insetParamSize;
            }
            int insetViewSize = viewSize - paddingSize;
            if (insetViewSize > 0) {
                return insetViewSize;
            }
            if (paramSize != -2) {
                return -1;
            }
            DisplayMetrics receiver = gVar.getView().getContext().getResources().getDisplayMetrics();
            return isWidth ? receiver.widthPixels : receiver.heightPixels;
        }

        /* access modifiers changed from: private */
        public static <T extends View> void g(g<T> gVar, ViewTreeObserver receiver, ViewTreeObserver.OnPreDrawListener victim) {
            if (receiver.isAlive()) {
                receiver.removeOnPreDrawListener(victim);
            } else {
                gVar.getView().getViewTreeObserver().removeOnPreDrawListener(victim);
            }
        }
    }
}
