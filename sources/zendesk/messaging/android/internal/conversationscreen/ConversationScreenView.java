package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.leedarson.bean.Constants;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.conversationscreen.messagelog.MessageLogView;
import zendesk.messaging.android.internal.conversationscreen.messagelog.g;
import zendesk.messaging.android.internal.conversationscreen.messagelog.h;
import zendesk.ui.android.conversation.composer.MessageComposerView;
import zendesk.ui.android.conversation.connectionbanner.ConnectionBannerView;
import zendesk.ui.android.conversation.connectionbanner.e;
import zendesk.ui.android.conversation.header.ConversationHeaderView;

/* compiled from: ConversationScreenView.kt */
public final class ConversationScreenView extends RelativeLayout implements zendesk.ui.android.a<g> {
    @NotNull
    private static final b c = new b((DefaultConstructorMarker) null);
    @NotNull
    private final MessageComposerView a1;
    /* access modifiers changed from: private */
    @NotNull
    public g d;
    @NotNull
    private final ConversationHeaderView f;
    @NotNull
    private final l<g, g> p0;
    @NotNull
    private final l<zendesk.ui.android.conversation.composer.g, zendesk.ui.android.conversation.composer.g> p1;
    @NotNull
    private final l<zendesk.ui.android.conversation.header.b, zendesk.ui.android.conversation.header.b> q;
    @NotNull
    private final ConnectionBannerView x;
    @NotNull
    private final l<zendesk.ui.android.conversation.connectionbanner.d, zendesk.ui.android.conversation.connectionbanner.d> y;
    @NotNull
    private final MessageLogView z;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConversationScreenView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConversationScreenView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ConversationScreenView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConversationScreenView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        k.e(context, "context");
        this.d = new g();
        this.q = new d(this);
        this.y = new c(this);
        this.p0 = new f(this, context);
        this.p1 = new e(this);
        RelativeLayout.inflate(context, R$layout.zma_view_conversation, this);
        View findViewById = findViewById(R$id.zma_conversation_header_view);
        k.d(findViewById, "findViewById(R.id.zma_conversation_header_view)");
        this.f = (ConversationHeaderView) findViewById;
        View findViewById2 = findViewById(R$id.zma_message_list);
        k.d(findViewById2, "findViewById(R.id.zma_message_list)");
        this.z = (MessageLogView) findViewById2;
        View findViewById3 = findViewById(R$id.zma_message_composer_view);
        k.d(findViewById3, "findViewById(R.id.zma_message_composer_view)");
        this.a1 = (MessageComposerView) findViewById3;
        View findViewById4 = findViewById(R$id.zma_connection_banner_view);
        k.d(findViewById4, "findViewById(R.id.zma_connection_banner_view)");
        ConnectionBannerView connectionBannerView = (ConnectionBannerView) findViewById4;
        this.x = connectionBannerView;
        connectionBannerView.bringToFront();
        a(a.INSTANCE);
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.header.b, zendesk.ui.android.conversation.header.b> {
        final /* synthetic */ ConversationScreenView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(ConversationScreenView conversationScreenView) {
            super(1);
            this.this$0 = conversationScreenView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.header.b invoke(@NotNull zendesk.ui.android.conversation.header.b conversationHeaderRendering) {
            k.e(conversationHeaderRendering, "conversationHeaderRendering");
            return conversationHeaderRendering.c().g(new a(this.this$0)).d(this.this$0.d.b()).a();
        }

        /* compiled from: ConversationScreenView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.header.c, zendesk.ui.android.conversation.header.c> {
            final /* synthetic */ ConversationScreenView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ConversationScreenView conversationScreenView) {
                super(1);
                this.this$0 = conversationScreenView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.header.c invoke(@NotNull zendesk.ui.android.conversation.header.c state) {
                Integer num;
                Integer num2;
                k.e(state, Constants.ACTION_STATE);
                String o = this.this$0.d.m().o();
                String i = this.this$0.d.m().i();
                Uri parse = Uri.parse(this.this$0.d.m().k());
                zendesk.android.messaging.model.a $this$invoke_u24lambda_u2d0 = this.this$0.d.m().e();
                if ($this$invoke_u24lambda_u2d0 == null) {
                    num = null;
                } else {
                    num = $this$invoke_u24lambda_u2d0.e($this$invoke_u24lambda_u2d0.d());
                }
                zendesk.android.messaging.model.a $this$invoke_u24lambda_u2d1 = this.this$0.d.m().e();
                if ($this$invoke_u24lambda_u2d1 == null) {
                    num2 = null;
                } else {
                    num2 = $this$invoke_u24lambda_u2d1.e($this$invoke_u24lambda_u2d1.d());
                }
                return state.a(o, i, parse, num, num2);
            }
        }
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.connectionbanner.d, zendesk.ui.android.conversation.connectionbanner.d> {
        final /* synthetic */ ConversationScreenView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(ConversationScreenView conversationScreenView) {
            super(1);
            this.this$0 = conversationScreenView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.connectionbanner.d invoke(@NotNull zendesk.ui.android.conversation.connectionbanner.d connectionBannerRendering) {
            k.e(connectionBannerRendering, "connectionBannerRendering");
            return connectionBannerRendering.c().d(this.this$0.d.i()).g(new a(this.this$0)).a();
        }

        /* compiled from: ConversationScreenView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.connectionbanner.e, zendesk.ui.android.conversation.connectionbanner.e> {
            final /* synthetic */ ConversationScreenView this$0;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.ConversationScreenView$c$a$a  reason: collision with other inner class name */
            /* compiled from: ConversationScreenView.kt */
            public final /* synthetic */ class C0520a {
                public static final /* synthetic */ int[] a;

                static {
                    int[] iArr = new int[zendesk.conversationkit.android.a.values().length];
                    iArr[zendesk.conversationkit.android.a.DISCONNECTED.ordinal()] = 1;
                    iArr[zendesk.conversationkit.android.a.CONNECTING_REALTIME.ordinal()] = 2;
                    iArr[zendesk.conversationkit.android.a.CONNECTED_REALTIME.ordinal()] = 3;
                    a = iArr;
                }
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ConversationScreenView conversationScreenView) {
                super(1);
                this.this$0 = conversationScreenView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.connectionbanner.e invoke(@NotNull zendesk.ui.android.conversation.connectionbanner.e state) {
                e.a aVar;
                k.e(state, Constants.ACTION_STATE);
                zendesk.conversationkit.android.a g = this.this$0.d.m().g();
                switch (g == null ? -1 : C0520a.a[g.ordinal()]) {
                    case 1:
                        aVar = e.a.b.b;
                        break;
                    case 2:
                        aVar = e.a.d.b;
                        break;
                    case 3:
                        aVar = e.a.c.b;
                        break;
                    default:
                        aVar = e.a.C0559a.b;
                        break;
                }
                return state.a(aVar);
            }
        }
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<g, g> {
        final /* synthetic */ Context $context;
        final /* synthetic */ ConversationScreenView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(ConversationScreenView conversationScreenView, Context context) {
            super(1);
            this.this$0 = conversationScreenView;
            this.$context = context;
        }

        @NotNull
        public final g invoke(@NotNull g messageLogRendering) {
            k.e(messageLogRendering, "messageLogRendering");
            return messageLogRendering.h().v(new a(this.this$0, this.$context)).m(this.this$0.d.h()).i(this.this$0.d.c()).n(this.this$0.d.l()).j(this.this$0.d.d()).l(this.this$0.d.f()).k(this.this$0.d.e()).a();
        }

        /* compiled from: ConversationScreenView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<h, h> {
            final /* synthetic */ Context $context;
            final /* synthetic */ ConversationScreenView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ConversationScreenView conversationScreenView, Context context) {
                super(1);
                this.this$0 = conversationScreenView;
                this.$context = context;
            }

            /* JADX WARNING: Removed duplicated region for block: B:23:0x0057  */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x0059  */
            /* JADX WARNING: Removed duplicated region for block: B:28:0x0064  */
            /* JADX WARNING: Removed duplicated region for block: B:29:0x0066  */
            /* JADX WARNING: Removed duplicated region for block: B:40:0x0099  */
            /* JADX WARNING: Removed duplicated region for block: B:41:0x009b  */
            /* JADX WARNING: Removed duplicated region for block: B:43:0x00a7  */
            /* JADX WARNING: Removed duplicated region for block: B:44:0x00a9  */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00b6  */
            /* JADX WARNING: Removed duplicated region for block: B:47:0x00b8  */
            @org.jetbrains.annotations.NotNull
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final zendesk.messaging.android.internal.conversationscreen.messagelog.h invoke(@org.jetbrains.annotations.NotNull zendesk.messaging.android.internal.conversationscreen.messagelog.h r13) {
                /*
                    r12 = this;
                    java.lang.String r0 = "state"
                    kotlin.jvm.internal.k.e(r13, r0)
                    zendesk.messaging.android.internal.conversationscreen.ConversationScreenView r0 = r12.this$0
                    zendesk.messaging.android.internal.conversationscreen.g r0 = r0.d
                    zendesk.messaging.android.internal.conversationscreen.h r0 = r0.m()
                    java.util.List r0 = r0.n()
                    zendesk.messaging.android.internal.conversationscreen.ConversationScreenView r1 = r12.this$0
                    zendesk.messaging.android.internal.conversationscreen.g r1 = r1.d
                    zendesk.messaging.android.internal.conversationscreen.h r1 = r1.m()
                    zendesk.android.messaging.model.a r8 = r1.e()
                    r1 = 0
                    r2 = 1
                    if (r0 == 0) goto L_0x002e
                    boolean r3 = r0.isEmpty()
                    if (r3 == 0) goto L_0x002c
                    goto L_0x002e
                L_0x002c:
                    r3 = r1
                    goto L_0x002f
                L_0x002e:
                    r3 = r2
                L_0x002f:
                    r4 = 0
                    if (r3 != 0) goto L_0x004c
                    java.lang.Object r3 = kotlin.collections.y.d0(r0)
                    boolean r5 = r3 instanceof zendesk.messaging.android.internal.model.b.a
                    if (r5 == 0) goto L_0x003d
                    zendesk.messaging.android.internal.model.b$a r3 = (zendesk.messaging.android.internal.model.b.a) r3
                    goto L_0x003e
                L_0x003d:
                    r3 = r4
                L_0x003e:
                    if (r3 != 0) goto L_0x0042
                    r3 = r4
                    goto L_0x0046
                L_0x0042:
                    zendesk.messaging.android.internal.model.a r3 = r3.b()
                L_0x0046:
                    zendesk.messaging.android.internal.model.a r5 = zendesk.messaging.android.internal.model.a.OUTBOUND
                    if (r3 != r5) goto L_0x004c
                    r3 = r2
                    goto L_0x004d
                L_0x004c:
                    r3 = r1
                L_0x004d:
                    r9 = r3
                    if (r0 == 0) goto L_0x0059
                    boolean r3 = r0.isEmpty()
                    if (r3 == 0) goto L_0x0057
                    goto L_0x0059
                L_0x0057:
                    r3 = r1
                    goto L_0x005a
                L_0x0059:
                    r3 = r2
                L_0x005a:
                    if (r3 != 0) goto L_0x0066
                    java.lang.Object r3 = kotlin.collections.y.d0(r0)
                    boolean r3 = r3 instanceof zendesk.messaging.android.internal.model.b.C0546b
                    if (r3 == 0) goto L_0x0066
                    r3 = r2
                    goto L_0x0067
                L_0x0066:
                    r3 = r1
                L_0x0067:
                    r10 = r3
                    java.util.List r3 = r13.d()
                    boolean r3 = r3.isEmpty()
                    if (r3 != 0) goto L_0x0087
                    java.util.List r3 = r13.d()
                    int r3 = r3.size()
                    int r5 = r0.size()
                    if (r3 == r5) goto L_0x0085
                    if (r9 != 0) goto L_0x0087
                    if (r10 == 0) goto L_0x0085
                    goto L_0x0087
                L_0x0085:
                    r3 = r1
                    goto L_0x0088
                L_0x0087:
                    r3 = r2
                L_0x0088:
                    zendesk.messaging.android.internal.conversationscreen.ConversationScreenView r1 = r12.this$0
                    zendesk.messaging.android.internal.conversationscreen.g r1 = r1.d
                    zendesk.messaging.android.internal.conversationscreen.h r1 = r1.m()
                    zendesk.android.messaging.model.a r1 = r1.e()
                    if (r1 != 0) goto L_0x009b
                    r5 = r4
                    goto L_0x00a5
                L_0x009b:
                    r2 = 0
                    java.lang.String r5 = r1.b()
                    java.lang.Integer r1 = r1.e(r5)
                    r5 = r1
                L_0x00a5:
                    if (r8 != 0) goto L_0x00a9
                    r6 = r4
                    goto L_0x00b4
                L_0x00a9:
                    r1 = r8
                    r2 = 0
                    java.lang.String r6 = r1.a()
                    java.lang.Integer r1 = r1.e(r6)
                    r6 = r1
                L_0x00b4:
                    if (r8 != 0) goto L_0x00b8
                    r7 = r4
                    goto L_0x00dc
                L_0x00b8:
                    android.content.Context r1 = r12.$context
                    r2 = r8
                    r7 = 0
                    java.lang.String r11 = r2.c()
                    if (r11 != 0) goto L_0x00c3
                    goto L_0x00c9
                L_0x00c3:
                    r4 = r11
                    r11 = 0
                    java.lang.Integer r4 = r2.e(r4)
                L_0x00c9:
                    if (r4 != 0) goto L_0x00d3
                    int r4 = zendesk.ui.android.R$color.colorError
                    int r1 = androidx.core.content.ContextCompat.getColor(r1, r4)
                    goto L_0x00d7
                L_0x00d3:
                    int r1 = r4.intValue()
                L_0x00d7:
                    java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                    r7 = r1
                L_0x00dc:
                    zendesk.messaging.android.internal.conversationscreen.ConversationScreenView r1 = r12.this$0
                    zendesk.messaging.android.internal.conversationscreen.g r1 = r1.d
                    zendesk.messaging.android.internal.conversationscreen.h r1 = r1.m()
                    java.util.Map r4 = r1.l()
                    r1 = r13
                    r2 = r0
                    zendesk.messaging.android.internal.conversationscreen.messagelog.h r1 = r1.a(r2, r3, r4, r5, r6, r7)
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.ConversationScreenView.f.a.invoke(zendesk.messaging.android.internal.conversationscreen.messagelog.h):zendesk.messaging.android.internal.conversationscreen.messagelog.h");
            }
        }
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.composer.g, zendesk.ui.android.conversation.composer.g> {
        final /* synthetic */ ConversationScreenView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(ConversationScreenView conversationScreenView) {
            super(1);
            this.this$0 = conversationScreenView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.composer.g invoke(@NotNull zendesk.ui.android.conversation.composer.g messageComposerRendering) {
            k.e(messageComposerRendering, "messageComposerRendering");
            return messageComposerRendering.f().h(this.this$0.d.j()).g(this.this$0.d.a()).j(this.this$0.d.k()).i(this.this$0.d.g()).p(new a(this.this$0)).a();
        }

        /* compiled from: ConversationScreenView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.composer.h, zendesk.ui.android.conversation.composer.h> {
            final /* synthetic */ ConversationScreenView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ConversationScreenView conversationScreenView) {
                super(1);
                this.this$0 = conversationScreenView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.composer.h invoke(@NotNull zendesk.ui.android.conversation.composer.h state) {
                Integer num;
                k.e(state, Constants.ACTION_STATE);
                zendesk.android.messaging.model.a $this$invoke_u24lambda_u2d0 = this.this$0.d.m().e();
                if ($this$invoke_u24lambda_u2d0 == null) {
                    num = null;
                } else {
                    num = $this$invoke_u24lambda_u2d0.e($this$invoke_u24lambda_u2d0.d());
                }
                return state.a(!this.this$0.d.m().c(), this.this$0.d.m().d(), this.this$0.d.m().j(), true, this.this$0.d.m().m(), 4096, num, this.this$0.d.m().f());
            }
        }
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<g, g> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final g invoke(@NotNull g it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull l<? super g, g> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        g invoke = renderingUpdate.invoke(this.d);
        this.d = invoke;
        zendesk.logger.a.e("ConversationScreenView", k.l("Updating the Conversation Screen with ", invoke.m()), new Object[0]);
        this.f.a(this.q);
        this.x.a(this.y);
        this.z.a(this.p0);
        this.a1.a(this.p1);
    }

    /* compiled from: ConversationScreenView.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
