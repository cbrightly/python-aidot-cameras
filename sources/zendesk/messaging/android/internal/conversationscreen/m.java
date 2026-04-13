package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.ColorInt;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageViewerScreenCoordinator.kt */
public final class m {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context b;
    @NotNull
    private final kotlin.jvm.functions.a<zendesk.android.d> c;
    /* access modifiers changed from: private */
    @NotNull
    public final String d;
    /* access modifiers changed from: private */
    @Nullable
    public final Integer e;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.a<x> f;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.ui.android.a<zendesk.ui.android.conversation.imagerviewer.a> g;
    @NotNull
    private final o0 h;
    @Nullable
    private i i;

    @f(c = "zendesk.messaging.android.internal.conversationscreen.ImageViewerScreenCoordinator", f = "ImageViewerScreenCoordinator.kt", l = {53, 59, 67}, m = "init")
    /* compiled from: ImageViewerScreenCoordinator.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(m mVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.e((kotlin.jvm.functions.a<x>) null, this);
        }
    }

    public m(@NotNull Context context, @NotNull kotlin.jvm.functions.a<zendesk.android.d> zendeskCredentialsProvider, @NotNull String imageUri, @Nullable @ColorInt Integer toolbarColor, @NotNull kotlin.jvm.functions.a<x> onBackButtonClicked, @NotNull zendesk.ui.android.a<zendesk.ui.android.conversation.imagerviewer.a> imageViewerRenderer, @NotNull o0 coroutineScope) {
        k.e(context, "context");
        k.e(zendeskCredentialsProvider, "zendeskCredentialsProvider");
        k.e(imageUri, "imageUri");
        k.e(onBackButtonClicked, "onBackButtonClicked");
        k.e(imageViewerRenderer, "imageViewerRenderer");
        k.e(coroutineScope, "coroutineScope");
        this.b = context;
        this.c = zendeskCredentialsProvider;
        this.d = imageUri;
        this.e = toolbarColor;
        this.f = onBackButtonClicked;
        this.g = imageViewerRenderer;
        this.h = coroutineScope;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005c, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        r0 = (zendesk.messaging.android.c) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0092, code lost:
        if ((r0 instanceof zendesk.messaging.android.c.b) == false) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0094, code lost:
        r1 = (zendesk.android.messaging.b) ((zendesk.messaging.android.c.b) r0).a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        if ((r1 instanceof zendesk.messaging.android.internal.d) != false) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a1, code lost:
        r12.invoke();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a6, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a7, code lost:
        r2 = ((zendesk.messaging.android.internal.d) r1).j(r10.b);
        r10.i = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b2, code lost:
        if (r2 != null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b5, code lost:
        r13.L$0 = null;
        r13.L$1 = null;
        r13.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c2, code lost:
        if (r10.f(r2, r13) != r8) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c4, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ca, code lost:
        if ((r0 instanceof zendesk.messaging.android.c.a) == false) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cc, code lost:
        r12.invoke();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d1, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlin.jvm.functions.a<kotlin.x>, code=kotlin.jvm.functions.a, for r12v0, types: [kotlin.jvm.functions.a<kotlin.x>, kotlin.jvm.functions.a, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object e(@org.jetbrains.annotations.NotNull kotlin.jvm.functions.a r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof zendesk.messaging.android.internal.conversationscreen.m.b
            if (r0 == 0) goto L_0x0013
            r0 = r13
            zendesk.messaging.android.internal.conversationscreen.m$b r0 = (zendesk.messaging.android.internal.conversationscreen.m.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.m$b r0 = new zendesk.messaging.android.internal.conversationscreen.m$b
            r0.<init>(r11, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r7 = r13.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r13.label
            r9 = 0
            switch(r0) {
                case 0: goto L_0x0047;
                case 1: goto L_0x0042;
                case 2: goto L_0x0034;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002d:
            r0 = r9
            r1 = 0
            kotlin.p.b(r7)
            goto L_0x00c6
        L_0x0034:
            java.lang.Object r12 = r13.L$1
            kotlin.jvm.functions.a r12 = (kotlin.jvm.functions.a) r12
            java.lang.Object r0 = r13.L$0
            zendesk.messaging.android.internal.conversationscreen.m r0 = (zendesk.messaging.android.internal.conversationscreen.m) r0
            kotlin.p.b(r7)
            r10 = r0
            r0 = r7
            goto L_0x008e
        L_0x0042:
            r0 = r11
            kotlin.p.b(r7)
            goto L_0x005a
        L_0x0047:
            kotlin.p.b(r7)
            r10 = r11
            zendesk.messaging.android.internal.conversationscreen.i r0 = r10.i
            if (r0 == 0) goto L_0x005d
            r1 = 1
            r13.label = r1
            java.lang.Object r0 = r10.f(r0, r13)
            if (r0 != r8) goto L_0x0059
            return r8
        L_0x0059:
            r0 = r10
        L_0x005a:
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x005d:
            r0 = 0
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r1 = "ImageViewerScreenCoordinator"
            java.lang.String r2 = "Initializing the Image Viewer Screen."
            zendesk.logger.a.e(r1, r2, r0)
            kotlin.jvm.functions.a<zendesk.android.d> r0 = r10.c
            java.lang.Object r0 = r0.invoke()
            r2 = r0
            zendesk.android.d r2 = (zendesk.android.d) r2
            if (r2 != 0) goto L_0x0078
            r12.invoke()
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x0078:
            zendesk.android.c$a r0 = zendesk.android.c.a
            android.content.Context r1 = r10.b
            r3 = 0
            r5 = 4
            r6 = 0
            r13.L$0 = r10
            r13.L$1 = r12
            r4 = 2
            r13.label = r4
            r4 = r13
            java.lang.Object r0 = zendesk.messaging.android.internal.extension.b.b(r0, r1, r2, r3, r4, r5, r6)
            if (r0 != r8) goto L_0x008e
            return r8
        L_0x008e:
            zendesk.messaging.android.c r0 = (zendesk.messaging.android.c) r0
            boolean r1 = r0 instanceof zendesk.messaging.android.c.b
            if (r1 == 0) goto L_0x00c8
            r1 = r0
            zendesk.messaging.android.c$b r1 = (zendesk.messaging.android.c.b) r1
            java.lang.Object r1 = r1.a()
            zendesk.android.messaging.b r1 = (zendesk.android.messaging.b) r1
            boolean r2 = r1 instanceof zendesk.messaging.android.internal.d
            if (r2 != 0) goto L_0x00a7
            r12.invoke()
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00a7:
            r2 = r1
            zendesk.messaging.android.internal.d r2 = (zendesk.messaging.android.internal.d) r2
            android.content.Context r3 = r10.b
            zendesk.messaging.android.internal.conversationscreen.i r2 = r2.j(r3)
            r10.i = r2
            if (r2 != 0) goto L_0x00b5
            goto L_0x00cf
        L_0x00b5:
            r1 = r2
            r2 = 0
            r13.L$0 = r9
            r13.L$1 = r9
            r3 = 3
            r13.label = r3
            java.lang.Object r1 = r10.f(r1, r13)
            if (r1 != r8) goto L_0x00c5
            return r8
        L_0x00c5:
            r1 = r2
        L_0x00c6:
            goto L_0x00cf
        L_0x00c8:
            boolean r1 = r0 instanceof zendesk.messaging.android.c.a
            if (r1 == 0) goto L_0x00cf
            r12.invoke()
        L_0x00cf:
            kotlin.x r12 = kotlin.x.a
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.m.e(kotlin.jvm.functions.a, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: Collect.kt */
    public static final class c implements kotlinx.coroutines.flow.d<h> {
        final /* synthetic */ m c;

        public c(m mVar) {
            this.c = mVar;
        }

        @Nullable
        public Object emit(h value, @NotNull kotlin.coroutines.d<? super x> $completion) {
            kotlin.coroutines.d<? super x> dVar = $completion;
            this.c.g.a(new d(this.c, value));
            return x.a;
        }
    }

    private final Object f(i conversationScreenStore, kotlin.coroutines.d<? super x> $completion) {
        zendesk.logger.a.e("ImageViewerScreenCoordinator", "Listening to Image Viewer Screen updates.", new Object[0]);
        Object a2 = conversationScreenStore.o().a(new c(this), $completion);
        if (a2 == kotlin.coroutines.intrinsics.c.d()) {
            return a2;
        }
        return x.a;
    }

    /* compiled from: ImageViewerScreenCoordinator.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagerviewer.a, zendesk.ui.android.conversation.imagerviewer.a> {
        final /* synthetic */ h $newState;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(m mVar, h hVar) {
            super(1);
            this.this$0 = mVar;
            this.$newState = hVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.imagerviewer.a invoke(@NotNull zendesk.ui.android.conversation.imagerviewer.a currentRendering) {
            k.e(currentRendering, "currentRendering");
            return currentRendering.c().g(new a(this.this$0, this.$newState)).d(new b(this.this$0)).a();
        }

        /* compiled from: ImageViewerScreenCoordinator.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagerviewer.b, zendesk.ui.android.conversation.imagerviewer.b> {
            final /* synthetic */ h $newState;
            final /* synthetic */ m this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(m mVar, h hVar) {
                super(1);
                this.this$0 = mVar;
                this.$newState = hVar;
            }

            @NotNull
            public final zendesk.ui.android.conversation.imagerviewer.b invoke(@NotNull zendesk.ui.android.conversation.imagerviewer.b imageViewerState) {
                Integer num;
                k.e(imageViewerState, "imageViewerState");
                String a = this.this$0.d;
                Integer d = this.this$0.e;
                zendesk.android.messaging.model.a $this$invoke_u24lambda_u2d0 = this.$newState.e();
                if ($this$invoke_u24lambda_u2d0 == null) {
                    num = null;
                } else {
                    num = $this$invoke_u24lambda_u2d0.e($this$invoke_u24lambda_u2d0.d());
                }
                return zendesk.ui.android.conversation.imagerviewer.b.b(imageViewerState, a, (String) null, (String) null, (Uri) null, d, num, 14, (Object) null);
            }
        }

        /* compiled from: ImageViewerScreenCoordinator.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ m this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(m mVar) {
                super(0);
                this.this$0 = mVar;
            }

            public final void invoke() {
                this.this$0.f.invoke();
            }
        }
    }

    /* compiled from: ImageViewerScreenCoordinator.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
