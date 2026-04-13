package zendesk.conversationkit.android;

import android.content.Context;
import kotlin.coroutines.jvm.internal.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.h;

/* compiled from: ConversationKitFactory.kt */
public final class f {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context b;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.ConversationKitFactory", f = "ConversationKitFactory.kt", l = {47}, m = "create")
    /* compiled from: ConversationKitFactory.kt */
    public static final class b extends d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(f fVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = fVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((i) null, (h) null, this);
        }
    }

    public /* synthetic */ f(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    private f(Context context) {
        this.b = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.i r6, @org.jetbrains.annotations.Nullable zendesk.conversationkit.android.model.h r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.g<? extends zendesk.conversationkit.android.b>> r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof zendesk.conversationkit.android.f.b
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.conversationkit.android.f$b r0 = (zendesk.conversationkit.android.f.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.f$b r0 = new zendesk.conversationkit.android.f$b
            r0.<init>(r5, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            java.lang.Object r6 = r8.L$0
            zendesk.conversationkit.android.j r6 = (zendesk.conversationkit.android.j) r6
            kotlin.p.b(r0)
            r2 = r6
            r6 = r0
            goto L_0x004e
        L_0x0036:
            kotlin.p.b(r0)
            r2 = r5
            zendesk.conversationkit.android.j r3 = new zendesk.conversationkit.android.j
            android.content.Context r4 = r2.b
            r3.<init>(r4)
            r2 = r3
            r8.L$0 = r2
            r3 = 1
            r8.label = r3
            java.lang.Object r6 = r2.n(r6, r7, r8)
            if (r6 != r1) goto L_0x004e
            return r1
        L_0x004e:
            zendesk.conversationkit.android.g r6 = (zendesk.conversationkit.android.g) r6
            boolean r7 = r6 instanceof zendesk.conversationkit.android.g.b
            if (r7 == 0) goto L_0x005a
            zendesk.conversationkit.android.g$b r6 = new zendesk.conversationkit.android.g$b
            r6.<init>(r2)
            goto L_0x0061
        L_0x005a:
            zendesk.conversationkit.android.g$a r6 = new zendesk.conversationkit.android.g$a
            zendesk.conversationkit.android.c$a r7 = zendesk.conversationkit.android.c.a.INSTANCE
            r6.<init>(r7)
        L_0x0061:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.f.a(zendesk.conversationkit.android.i, zendesk.conversationkit.android.model.h, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: ConversationKitFactory.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final f a(@NotNull Context context) {
            k.e(context, "context");
            return new f(context, (DefaultConstructorMarker) null);
        }
    }
}
