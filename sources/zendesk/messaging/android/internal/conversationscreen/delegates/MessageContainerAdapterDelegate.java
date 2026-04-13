package zendesk.messaging.android.internal.conversationscreen.delegates;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.u;
import zendesk.messaging.R$color;
import zendesk.messaging.R$dimen;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.adapterdelegate.b;
import zendesk.messaging.android.internal.conversationscreen.s;
import zendesk.messaging.android.internal.m;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.avatar.AvatarImageView;
import zendesk.ui.android.conversation.form.DisplayedField;
import zendesk.ui.android.conversation.receipt.MessageReceiptView;
import zendesk.ui.android.conversation.receipt.c;

/* compiled from: MessageContainerAdapterDelegate.kt */
public final class MessageContainerAdapterDelegate extends b<b.a, zendesk.messaging.android.internal.model.b, ViewHolder> {
    @NotNull
    private l<? super b.a, x> a;
    @NotNull
    private m b;
    @NotNull
    private p<? super List<? extends Field>, ? super b.a, x> c;
    @NotNull
    private l<? super Boolean, x> d;
    @NotNull
    private l<? super DisplayedField, x> e;
    @NotNull
    private Map<Integer, DisplayedField> f;
    @Nullable
    @ColorInt
    private Integer g;
    @Nullable
    @ColorInt
    private Integer h;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ MessageContainerAdapterDelegate(kotlin.jvm.functions.l<zendesk.messaging.android.internal.model.b.a, kotlin.x> r5, zendesk.messaging.android.internal.m r6, kotlin.jvm.functions.p r7, kotlin.jvm.functions.l r8, kotlin.jvm.functions.l r9, java.util.Map r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r4 = this;
            r12 = r11 & 1
            if (r12 == 0) goto L_0x0008
            kotlin.jvm.functions.l r5 = zendesk.messaging.android.internal.conversationscreen.messagelog.f.d()
        L_0x0008:
            r12 = r11 & 2
            if (r12 == 0) goto L_0x0010
            zendesk.messaging.android.internal.k r6 = zendesk.messaging.android.internal.k.a
            r12 = r6
            goto L_0x0011
        L_0x0010:
            r12 = r6
        L_0x0011:
            r6 = r11 & 4
            if (r6 == 0) goto L_0x001b
            kotlin.jvm.functions.p r7 = zendesk.messaging.android.internal.conversationscreen.messagelog.f.a()
            r0 = r7
            goto L_0x001c
        L_0x001b:
            r0 = r7
        L_0x001c:
            r6 = r11 & 8
            if (r6 == 0) goto L_0x0026
            kotlin.jvm.functions.l r8 = zendesk.messaging.android.internal.conversationscreen.messagelog.f.c()
            r1 = r8
            goto L_0x0027
        L_0x0026:
            r1 = r8
        L_0x0027:
            r6 = r11 & 16
            if (r6 == 0) goto L_0x0031
            kotlin.jvm.functions.l r9 = zendesk.messaging.android.internal.conversationscreen.messagelog.f.b()
            r2 = r9
            goto L_0x0032
        L_0x0031:
            r2 = r9
        L_0x0032:
            r6 = r11 & 32
            if (r6 == 0) goto L_0x003d
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            r3 = r10
            goto L_0x003e
        L_0x003d:
            r3 = r10
        L_0x003e:
            r6 = r4
            r7 = r5
            r8 = r12
            r9 = r0
            r10 = r1
            r11 = r2
            r12 = r3
            r6.<init>(r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.delegates.MessageContainerAdapterDelegate.<init>(kotlin.jvm.functions.l, zendesk.messaging.android.internal.m, kotlin.jvm.functions.p, kotlin.jvm.functions.l, kotlin.jvm.functions.l, java.util.Map, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final void o(@NotNull l<? super b.a, x> lVar) {
        k.e(lVar, "<set-?>");
        this.a = lVar;
    }

    public final void s(@NotNull m mVar) {
        k.e(mVar, "<set-?>");
        this.b = mVar;
    }

    public final void p(@NotNull p<? super List<? extends Field>, ? super b.a, x> pVar) {
        k.e(pVar, "<set-?>");
        this.c = pVar;
    }

    public final void r(@NotNull l<? super Boolean, x> lVar) {
        k.e(lVar, "<set-?>");
        this.d = lVar;
    }

    public final void q(@NotNull l<? super DisplayedField, x> lVar) {
        k.e(lVar, "<set-?>");
        this.e = lVar;
    }

    public final void n(@NotNull Map<Integer, DisplayedField> map) {
        k.e(map, "<set-?>");
        this.f = map;
    }

    public MessageContainerAdapterDelegate(@NotNull l<? super b.a, x> onFailedMessageClicked, @NotNull m onUriClicked, @NotNull p<? super List<? extends Field>, ? super b.a, x> onFormCompleted, @NotNull l<? super Boolean, x> onFormFocusChangedListener, @NotNull l<? super DisplayedField, x> onFormDisplayedFieldsChanged, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields) {
        k.e(onFailedMessageClicked, "onFailedMessageClicked");
        k.e(onUriClicked, "onUriClicked");
        k.e(onFormCompleted, "onFormCompleted");
        k.e(onFormFocusChangedListener, "onFormFocusChangedListener");
        k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
        k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
        this.a = onFailedMessageClicked;
        this.b = onUriClicked;
        this.c = onFormCompleted;
        this.d = onFormFocusChangedListener;
        this.e = onFormDisplayedFieldsChanged;
        this.f = mapOfDisplayedFields;
    }

    @Nullable
    public final Integer i() {
        return this.g;
    }

    public final void t(@Nullable Integer num) {
        this.g = num;
    }

    @Nullable
    public final Integer h() {
        return this.h;
    }

    public final void m(@Nullable Integer num) {
        this.h = num;
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public boolean d(@NotNull zendesk.messaging.android.internal.model.b item, @NotNull List<? extends zendesk.messaging.android.internal.model.b> items, int position) {
        k.e(item, "item");
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        return item instanceof b.a;
    }

    @NotNull
    /* renamed from: l */
    public ViewHolder c(@NotNull ViewGroup parent) {
        k.e(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R$layout.zma_view_message_log_entry_message_container, parent, false);
        k.d(view, "from(parent.context)\n   …container, parent, false)");
        return new ViewHolder(view, this.g, this.h);
    }

    /* access modifiers changed from: protected */
    /* renamed from: k */
    public void f(@NotNull b.a item, @NotNull ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        holder.g(item, this.a, this.b, this.c, this.d, this.e, this.f);
    }

    /* compiled from: MessageContainerAdapterDelegate.kt */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        @Nullable
        public final Integer a;
        @Nullable
        private final Integer b;
        @NotNull
        private final TextView c;
        /* access modifiers changed from: private */
        @NotNull
        public final AvatarImageView d;
        @NotNull
        private final LinearLayout e;
        /* access modifiers changed from: private */
        @NotNull
        public final MessageReceiptView f;

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public final /* synthetic */ class a {
            public static final /* synthetic */ int[] a;
            public static final /* synthetic */ int[] b;

            static {
                int[] iArr = new int[u.values().length];
                iArr[u.PENDING.ordinal()] = 1;
                iArr[u.FAILED.ordinal()] = 2;
                iArr[u.SENT.ordinal()] = 3;
                a = iArr;
                int[] iArr2 = new int[zendesk.messaging.android.internal.model.c.values().length];
                iArr2[zendesk.messaging.android.internal.model.c.STANDALONE.ordinal()] = 1;
                iArr2[zendesk.messaging.android.internal.model.c.GROUP_TOP.ordinal()] = 2;
                iArr2[zendesk.messaging.android.internal.model.c.GROUP_MIDDLE.ordinal()] = 3;
                iArr2[zendesk.messaging.android.internal.model.c.GROUP_BOTTOM.ordinal()] = 4;
                b = iArr2;
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView, @Nullable @ColorInt Integer outboundMessageColor, @Nullable @ColorInt Integer actionColor) {
            super(itemView);
            kotlin.jvm.internal.k.e(itemView, "itemView");
            this.a = outboundMessageColor;
            this.b = actionColor;
            View findViewById = itemView.findViewById(R$id.zma_message_label);
            kotlin.jvm.internal.k.d(findViewById, "itemView.findViewById(R.id.zma_message_label)");
            this.c = (TextView) findViewById;
            View findViewById2 = itemView.findViewById(R$id.zma_avatar_view);
            kotlin.jvm.internal.k.d(findViewById2, "itemView.findViewById(R.id.zma_avatar_view)");
            this.d = (AvatarImageView) findViewById2;
            View findViewById3 = itemView.findViewById(R$id.zma_message_content);
            kotlin.jvm.internal.k.d(findViewById3, "itemView.findViewById(R.id.zma_message_content)");
            this.e = (LinearLayout) findViewById3;
            View findViewById4 = itemView.findViewById(R$id.zma_message_receipt);
            kotlin.jvm.internal.k.d(findViewById4, "itemView.findViewById(R.id.zma_message_receipt)");
            this.f = (MessageReceiptView) findViewById4;
        }

        public final void g(@NotNull b.a item, @NotNull kotlin.jvm.functions.l<? super b.a, x> onFailedMessageClicked, @NotNull zendesk.messaging.android.internal.m onUriClicked, @NotNull p<? super List<? extends Field>, ? super b.a, x> onFormCompleted, @NotNull kotlin.jvm.functions.l<? super Boolean, x> onFormFocusChangedListener, @NotNull kotlin.jvm.functions.l<? super DisplayedField, x> onFormDisplayedFieldsChanged, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields) {
            kotlin.jvm.internal.k.e(item, "item");
            kotlin.jvm.internal.k.e(onFailedMessageClicked, "onFailedMessageClicked");
            kotlin.jvm.internal.k.e(onUriClicked, "onUriClicked");
            kotlin.jvm.internal.k.e(onFormCompleted, "onFormCompleted");
            kotlin.jvm.internal.k.e(onFormFocusChangedListener, "onFormFocusChangedListener");
            kotlin.jvm.internal.k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
            kotlin.jvm.internal.k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
            l(item.d());
            j(item.a(), item.b());
            k(item, onFailedMessageClicked, onUriClicked, onFormCompleted, onFormFocusChangedListener, onFormDisplayedFieldsChanged, mapOfDisplayedFields);
            m(item.g(), item.b(), item.i(), (item.e().d() instanceof MessageContent.Text) || (item.e().d() instanceof MessageContent.File) || (item.e().d() instanceof MessageContent.Image) || (item.e().d() instanceof MessageContent.FileUpload) || (item.e().d() instanceof MessageContent.Unsupported) || item.e().m() == u.FAILED, item.e().d() instanceof MessageContent.Unsupported);
            f(item.f());
        }

        private final void l(String labelText) {
            int i2;
            this.c.setText(labelText);
            TextView textView = this.c;
            if (labelText == null) {
                i2 = 8;
            } else {
                String str = labelText;
                i2 = 0;
            }
            textView.setVisibility(i2);
        }

        private final void j(String avatarUrl, zendesk.messaging.android.internal.model.a messageDirection) {
            x xVar;
            if (avatarUrl == null) {
                xVar = null;
            } else {
                this.d.a(new f(avatarUrl, this));
                this.d.setVisibility(0);
                xVar = x.a;
            }
            if (xVar == null) {
                this.d.setVisibility(messageDirection == zendesk.messaging.android.internal.model.a.INBOUND ? 4 : 8);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.avatar.a, zendesk.ui.android.conversation.avatar.a> {
            final /* synthetic */ String $it;
            final /* synthetic */ ViewHolder this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            f(String str, ViewHolder viewHolder) {
                super(1);
                this.$it = str;
                this.this$0 = viewHolder;
            }

            @NotNull
            public final zendesk.ui.android.conversation.avatar.a invoke(@NotNull zendesk.ui.android.conversation.avatar.a rendering) {
                kotlin.jvm.internal.k.e(rendering, "rendering");
                return rendering.b().d(new a(this.$it, this.this$0)).a();
            }

            /* compiled from: MessageContainerAdapterDelegate.kt */
            public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.avatar.b, zendesk.ui.android.conversation.avatar.b> {
                final /* synthetic */ String $it;
                final /* synthetic */ ViewHolder this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                a(String str, ViewHolder viewHolder) {
                    super(1);
                    this.$it = str;
                    this.this$0 = viewHolder;
                }

                @NotNull
                public final zendesk.ui.android.conversation.avatar.b invoke(@NotNull zendesk.ui.android.conversation.avatar.b state) {
                    kotlin.jvm.internal.k.e(state, Constants.ACTION_STATE);
                    return zendesk.ui.android.conversation.avatar.b.b(state, Uri.parse(this.$it), false, 0, Integer.valueOf(ContextCompat.getColor(this.this$0.d.getContext(), R$color.zma_color_message_inbound_background)), zendesk.ui.android.conversation.avatar.c.CIRCLE, 6, (Object) null);
                }
            }
        }

        private final void k(b.a aVar, kotlin.jvm.functions.l<? super b.a, x> lVar, zendesk.messaging.android.internal.m mVar, p<? super List<? extends Field>, ? super b.a, x> pVar, kotlin.jvm.functions.l<? super Boolean, x> lVar2, kotlin.jvm.functions.l<? super DisplayedField, x> lVar3, Map<Integer, DisplayedField> map) {
            View view;
            b.a aVar2 = aVar;
            zendesk.messaging.android.internal.m mVar2 = mVar;
            p<? super List<? extends Field>, ? super b.a, x> pVar2 = pVar;
            kotlin.jvm.functions.l<? super Boolean, x> lVar4 = lVar2;
            this.e.removeAllViews();
            MessageContent d2 = aVar.e().d();
            if (d2 instanceof MessageContent.Unsupported) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.o(aVar2, this.e);
            } else if (d2 instanceof MessageContent.Carousel) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.l((MessageContent.Carousel) d2, this.e, this.b, mVar2);
            } else if (d2 instanceof MessageContent.Image) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.j(zendesk.messaging.android.internal.conversationscreen.messagelog.e.a, (MessageContent.Image) d2, aVar, this.e, mVar, (Integer) null, (kotlin.jvm.functions.l) null, 48, (Object) null);
            } else if (d2 instanceof MessageContent.File) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.e((MessageContent.File) d2, aVar, this.e, this.a, new g(mVar2));
            } else if (d2 instanceof MessageContent.FileUpload) {
                MessageContent.FileUpload fileUpload = (MessageContent.FileUpload) d2;
                if (fileUpload.f()) {
                    view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.k(fileUpload, aVar, this.e, this.a, lVar, mVar);
                } else {
                    view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.f(fileUpload, aVar, this.e, this.a, lVar);
                }
            } else if (d2 instanceof MessageContent.Form) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.h(this.e, s.a.a(((MessageContent.Form) d2).c(), new h(pVar2, aVar2), new i(lVar4), this.b, false, lVar3, map));
            } else if (d2 instanceof MessageContent.FormResponse) {
                switch (a.a[aVar.e().m().ordinal()]) {
                    case 1:
                        view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.h(this.e, s.a.a(((MessageContent.FormResponse) d2).d(), new j(pVar2, aVar2), new k(lVar4), this.b, true, lVar3, map));
                        break;
                    case 2:
                        view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.h(this.e, s.a.a(((MessageContent.FormResponse) d2).d(), new l(pVar2, aVar2), new m(lVar4), this.b, false, lVar3, map));
                        break;
                    case 3:
                        view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.g(this.e, s.a.b(((MessageContent.FormResponse) d2).d()));
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            } else if (d2 instanceof MessageContent.Text) {
                view = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a.m(aVar, this.e, this.a, h(aVar, lVar), new n(mVar2));
            } else {
                throw new NoWhenBranchMatchedException();
            }
            d(view, d2, aVar.b());
            this.e.addView(view);
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ zendesk.messaging.android.internal.m $onUriClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            g(zendesk.messaging.android.internal.m mVar) {
                super(1);
                this.$onUriClicked = mVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String uri) {
                kotlin.jvm.internal.k.e(uri, "uri");
                this.$onUriClicked.a(uri, zendesk.android.messaging.e.FILE);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<List<? extends Field>, x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ p<List<? extends Field>, b.a, x> $onFormCompleted;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            h(p<? super List<? extends Field>, ? super b.a, x> pVar, b.a aVar) {
                super(1);
                this.$onFormCompleted = pVar;
                this.$item = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((List<? extends Field>) (List) p1);
                return x.a;
            }

            public final void invoke(@NotNull List<? extends Field> field) {
                kotlin.jvm.internal.k.e(field, "field");
                this.$onFormCompleted.invoke(field, this.$item);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Boolean, x> {
            final /* synthetic */ kotlin.jvm.functions.l<Boolean, x> $onFormFocusChanged;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            i(kotlin.jvm.functions.l<? super Boolean, x> lVar) {
                super(1);
                this.$onFormFocusChanged = lVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean hasFocus) {
                this.$onFormFocusChanged.invoke(Boolean.valueOf(hasFocus));
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<List<? extends Field>, x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ p<List<? extends Field>, b.a, x> $onFormCompleted;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            j(p<? super List<? extends Field>, ? super b.a, x> pVar, b.a aVar) {
                super(1);
                this.$onFormCompleted = pVar;
                this.$item = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((List<? extends Field>) (List) p1);
                return x.a;
            }

            public final void invoke(@NotNull List<? extends Field> field) {
                kotlin.jvm.internal.k.e(field, "field");
                this.$onFormCompleted.invoke(field, this.$item);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class k extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Boolean, x> {
            final /* synthetic */ kotlin.jvm.functions.l<Boolean, x> $onFormFocusChanged;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            k(kotlin.jvm.functions.l<? super Boolean, x> lVar) {
                super(1);
                this.$onFormFocusChanged = lVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean hasFocus) {
                this.$onFormFocusChanged.invoke(Boolean.valueOf(hasFocus));
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class l extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<List<? extends Field>, x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ p<List<? extends Field>, b.a, x> $onFormCompleted;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            l(p<? super List<? extends Field>, ? super b.a, x> pVar, b.a aVar) {
                super(1);
                this.$onFormCompleted = pVar;
                this.$item = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((List<? extends Field>) (List) p1);
                return x.a;
            }

            public final void invoke(@NotNull List<? extends Field> field) {
                kotlin.jvm.internal.k.e(field, "field");
                this.$onFormCompleted.invoke(field, this.$item);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class m extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Boolean, x> {
            final /* synthetic */ kotlin.jvm.functions.l<Boolean, x> $onFormFocusChanged;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            m(kotlin.jvm.functions.l<? super Boolean, x> lVar) {
                super(1);
                this.$onFormFocusChanged = lVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean hasFocus) {
                this.$onFormFocusChanged.invoke(Boolean.valueOf(hasFocus));
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class n extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ zendesk.messaging.android.internal.m $onUriClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            n(zendesk.messaging.android.internal.m mVar) {
                super(1);
                this.$onUriClicked = mVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String uri) {
                kotlin.jvm.internal.k.e(uri, "uri");
                this.$onUriClicked.a(uri, zendesk.android.messaging.e.TEXT);
            }
        }

        private final void m(zendesk.messaging.android.internal.model.d receipt, zendesk.messaging.android.internal.model.a direction, u status, boolean showIcon, boolean isUnsupported) {
            if (receipt == null) {
                this.f.setVisibility(8);
                return;
            }
            this.f.a(new o(this, receipt, showIcon, direction, status, isUnsupported));
            this.f.setVisibility(0);
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class o extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.receipt.b, zendesk.ui.android.conversation.receipt.b> {
            final /* synthetic */ zendesk.messaging.android.internal.model.a $direction;
            final /* synthetic */ boolean $isUnsupported;
            final /* synthetic */ zendesk.messaging.android.internal.model.d $receipt;
            final /* synthetic */ boolean $showIcon;
            final /* synthetic */ u $status;
            final /* synthetic */ ViewHolder this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            o(ViewHolder viewHolder, zendesk.messaging.android.internal.model.d dVar, boolean z, zendesk.messaging.android.internal.model.a aVar, u uVar, boolean z2) {
                super(1);
                this.this$0 = viewHolder;
                this.$receipt = dVar;
                this.$showIcon = z;
                this.$direction = aVar;
                this.$status = uVar;
                this.$isUnsupported = z2;
            }

            @NotNull
            public final zendesk.ui.android.conversation.receipt.b invoke(@NotNull zendesk.ui.android.conversation.receipt.b receiptViewRendering) {
                kotlin.jvm.internal.k.e(receiptViewRendering, "receiptViewRendering");
                return receiptViewRendering.b().d(new a(this.this$0, this.$receipt, this.$showIcon, this.$direction, this.$status, this.$isUnsupported)).a();
            }

            /* compiled from: MessageContainerAdapterDelegate.kt */
            public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.receipt.c, zendesk.ui.android.conversation.receipt.c> {
                final /* synthetic */ zendesk.messaging.android.internal.model.a $direction;
                final /* synthetic */ boolean $isUnsupported;
                final /* synthetic */ zendesk.messaging.android.internal.model.d $receipt;
                final /* synthetic */ boolean $showIcon;
                final /* synthetic */ u $status;
                final /* synthetic */ ViewHolder this$0;

                /* renamed from: zendesk.messaging.android.internal.conversationscreen.delegates.MessageContainerAdapterDelegate$ViewHolder$o$a$a  reason: collision with other inner class name */
                /* compiled from: MessageContainerAdapterDelegate.kt */
                public final /* synthetic */ class C0522a {
                    public static final /* synthetic */ int[] a;

                    static {
                        int[] iArr = new int[u.values().length];
                        iArr[u.PENDING.ordinal()] = 1;
                        iArr[u.SENT.ordinal()] = 2;
                        iArr[u.FAILED.ordinal()] = 3;
                        a = iArr;
                    }
                }

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                a(ViewHolder viewHolder, zendesk.messaging.android.internal.model.d dVar, boolean z, zendesk.messaging.android.internal.model.a aVar, u uVar, boolean z2) {
                    super(1);
                    this.this$0 = viewHolder;
                    this.$receipt = dVar;
                    this.$showIcon = z;
                    this.$direction = aVar;
                    this.$status = uVar;
                    this.$isUnsupported = z2;
                }

                @NotNull
                public final zendesk.ui.android.conversation.receipt.c invoke(@NotNull zendesk.ui.android.conversation.receipt.c state) {
                    kotlin.jvm.internal.k.e(state, Constants.ACTION_STATE);
                    int labelColor = ContextCompat.getColor(this.this$0.f.getContext(), R$color.zma_color_label);
                    int failedColor = ContextCompat.getColor(this.this$0.f.getContext(), R$color.zma_color_alert);
                    c.a f = state.h().c(this.$receipt.a()).f(this.$showIcon);
                    zendesk.messaging.android.internal.model.a aVar = this.$direction;
                    u uVar = this.$status;
                    boolean z = this.$isUnsupported;
                    ViewHolder viewHolder = this.this$0;
                    c.a $this$invoke_u24lambda_u2d0 = f;
                    zendesk.messaging.android.internal.model.a aVar2 = zendesk.messaging.android.internal.model.a.INBOUND;
                    if (aVar == aVar2 && uVar == u.FAILED) {
                        $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.INBOUND_FAILED);
                        $this$invoke_u24lambda_u2d0.d(failedColor);
                        $this$invoke_u24lambda_u2d0.b(failedColor);
                    } else if (aVar != aVar2 || !z) {
                        if (aVar != aVar2) {
                            Integer b = viewHolder.a;
                            int outboundColor = b == null ? ContextCompat.getColor(viewHolder.f.getContext(), R$color.zma_color_message) : b.intValue();
                            switch (C0522a.a[uVar.ordinal()]) {
                                case 1:
                                    $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.OUTBOUND_SENDING);
                                    zendesk.messaging.android.internal.conversationscreen.messagelog.e eVar = zendesk.messaging.android.internal.conversationscreen.messagelog.e.a;
                                    $this$invoke_u24lambda_u2d0.d(zendesk.messaging.android.internal.conversationscreen.messagelog.e.d(eVar, labelColor, 0.0f, 1, (Object) null));
                                    $this$invoke_u24lambda_u2d0.b(zendesk.messaging.android.internal.conversationscreen.messagelog.e.d(eVar, outboundColor, 0.0f, 1, (Object) null));
                                    break;
                                case 2:
                                    $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.OUTBOUND_SENT);
                                    $this$invoke_u24lambda_u2d0.d(labelColor);
                                    $this$invoke_u24lambda_u2d0.b(outboundColor);
                                    break;
                                case 3:
                                    $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.OUTBOUND_FAILED);
                                    $this$invoke_u24lambda_u2d0.d(failedColor);
                                    $this$invoke_u24lambda_u2d0.b(failedColor);
                                    break;
                            }
                        } else {
                            int iconColor = ContextCompat.getColor(viewHolder.f.getContext(), R$color.zma_color_message_inbound_background);
                            $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.INBOUND);
                            $this$invoke_u24lambda_u2d0.d(labelColor);
                            $this$invoke_u24lambda_u2d0.b(iconColor);
                        }
                    } else {
                        $this$invoke_u24lambda_u2d0.e(zendesk.ui.android.conversation.receipt.a.INBOUND_FAILED);
                        $this$invoke_u24lambda_u2d0.d(failedColor);
                        $this$invoke_u24lambda_u2d0.b(failedColor);
                    }
                    return f.a();
                }
            }
        }

        private final void d(View view, MessageContent messageContent, zendesk.messaging.android.internal.model.a aVar) {
            int dimensionPixelSize = view.getResources().getDimensionPixelSize(R$dimen.zuia_horizontal_spacing_small);
            int dimensionPixelSize2 = view.getResources().getDimensionPixelSize(R$dimen.zma_cell_inbound_margin_end);
            int dimensionPixelSize3 = view.getResources().getDimensionPixelSize(R$dimen.zma_cell_outbound_margin_end);
            e eVar = new e(aVar);
            n(this.c, eVar);
            n(this.f, eVar);
            view.setMinimumWidth(view.getResources().getDimensionPixelSize(R$dimen.zma_message_cell_min_width));
            if ((messageContent instanceof MessageContent.Form) || (messageContent instanceof MessageContent.FormResponse) || (messageContent instanceof MessageContent.Carousel)) {
                i(view, new b(dimensionPixelSize));
            } else if ((messageContent instanceof MessageContent.Image) || e(messageContent)) {
                i(view, new c(aVar, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize));
            } else if ((messageContent instanceof MessageContent.File) || (messageContent instanceof MessageContent.FileUpload) || (messageContent instanceof MessageContent.Text) || (messageContent instanceof MessageContent.Unsupported)) {
                o(view, new d(aVar, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize));
                this.e.setGravity(aVar == zendesk.messaging.android.internal.model.a.OUTBOUND ? GravityCompat.END : GravityCompat.START);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<RelativeLayout.LayoutParams, x> {
            final /* synthetic */ zendesk.messaging.android.internal.model.a $direction;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            e(zendesk.messaging.android.internal.model.a aVar) {
                super(1);
                this.$direction = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((RelativeLayout.LayoutParams) p1);
                return x.a;
            }

            public final void invoke(@NotNull RelativeLayout.LayoutParams $this$null) {
                kotlin.jvm.internal.k.e($this$null, "$this$null");
                if (this.$direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
                    $this$null.removeRule(17);
                    $this$null.addRule(21);
                    return;
                }
                $this$null.removeRule(21);
                $this$null.addRule(17, R$id.zma_avatar_view);
            }
        }

        private static final boolean e(MessageContent content) {
            return (content instanceof MessageContent.FileUpload) && ((MessageContent.FileUpload) content).f();
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<LinearLayout.LayoutParams, x> {
            final /* synthetic */ int $spacingSmall;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(int i) {
                super(1);
                this.$spacingSmall = i;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((LinearLayout.LayoutParams) p1);
                return x.a;
            }

            public final void invoke(@NotNull LinearLayout.LayoutParams $this$edgeToEdge) {
                kotlin.jvm.internal.k.e($this$edgeToEdge, "$this$edgeToEdge");
                $this$edgeToEdge.setMarginEnd(this.$spacingSmall);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<LinearLayout.LayoutParams, x> {
            final /* synthetic */ zendesk.messaging.android.internal.model.a $direction;
            final /* synthetic */ int $inboundMarginEnd;
            final /* synthetic */ int $outboundMarginEnd;
            final /* synthetic */ int $spacingSmall;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(zendesk.messaging.android.internal.model.a aVar, int i, int i2, int i3) {
                super(1);
                this.$direction = aVar;
                this.$inboundMarginEnd = i;
                this.$outboundMarginEnd = i2;
                this.$spacingSmall = i3;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((LinearLayout.LayoutParams) p1);
                return x.a;
            }

            public final void invoke(@NotNull LinearLayout.LayoutParams $this$edgeToEdge) {
                kotlin.jvm.internal.k.e($this$edgeToEdge, "$this$edgeToEdge");
                if (this.$direction == zendesk.messaging.android.internal.model.a.INBOUND) {
                    $this$edgeToEdge.setMarginEnd(this.$inboundMarginEnd);
                    return;
                }
                $this$edgeToEdge.setMarginStart(this.$outboundMarginEnd);
                $this$edgeToEdge.setMarginEnd(this.$spacingSmall);
            }
        }

        /* compiled from: MessageContainerAdapterDelegate.kt */
        public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<LinearLayout.LayoutParams, x> {
            final /* synthetic */ zendesk.messaging.android.internal.model.a $direction;
            final /* synthetic */ int $inboundMarginEnd;
            final /* synthetic */ int $outboundMarginEnd;
            final /* synthetic */ int $spacingSmall;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(zendesk.messaging.android.internal.model.a aVar, int i, int i2, int i3) {
                super(1);
                this.$direction = aVar;
                this.$inboundMarginEnd = i;
                this.$outboundMarginEnd = i2;
                this.$spacingSmall = i3;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((LinearLayout.LayoutParams) p1);
                return x.a;
            }

            public final void invoke(@NotNull LinearLayout.LayoutParams $this$wrap) {
                kotlin.jvm.internal.k.e($this$wrap, "$this$wrap");
                if (this.$direction == zendesk.messaging.android.internal.model.a.INBOUND) {
                    $this$wrap.setMarginEnd(this.$inboundMarginEnd);
                    return;
                }
                $this$wrap.setMarginStart(this.$outboundMarginEnd);
                $this$wrap.setMarginEnd(this.$spacingSmall);
            }
        }

        private final void n(View $this$updateRelativeRules, kotlin.jvm.functions.l<? super RelativeLayout.LayoutParams, x> block) {
            ViewGroup.LayoutParams layoutParams = $this$updateRelativeRules.getLayoutParams();
            RelativeLayout.LayoutParams currentParams = layoutParams instanceof RelativeLayout.LayoutParams ? (RelativeLayout.LayoutParams) layoutParams : null;
            if (currentParams != null) {
                block.invoke(currentParams);
                $this$updateRelativeRules.setLayoutParams(currentParams);
            }
        }

        private final void i(View $this$edgeToEdge, kotlin.jvm.functions.l<? super LinearLayout.LayoutParams, x> block) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            block.invoke(layoutParams);
            $this$edgeToEdge.setLayoutParams(layoutParams);
        }

        private final void o(View $this$wrap, kotlin.jvm.functions.l<? super LinearLayout.LayoutParams, x> block) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            block.invoke(layoutParams);
            $this$wrap.setLayoutParams(layoutParams);
        }

        private final void f(zendesk.messaging.android.internal.model.c position) {
            int paddingBottom;
            int spacingBetweenMessages = this.itemView.getResources().getDimensionPixelSize(R$dimen.zuia_vertical_spacing_small);
            int spacingAroundGroups = this.itemView.getResources().getDimensionPixelSize(R$dimen.zuia_vertical_spacing_large);
            switch (a.b[position.ordinal()]) {
                case 1:
                case 4:
                    paddingBottom = spacingAroundGroups;
                    break;
                case 2:
                case 3:
                    paddingBottom = spacingBetweenMessages;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            this.itemView.setPaddingRelative(0, 0, 0, paddingBottom);
        }

        private final kotlin.jvm.functions.l<b.a, x> h(b.a $this$clickListener, kotlin.jvm.functions.l<? super b.a, x> onFailedMessageClicked) {
            if ($this$clickListener.e().m() == u.FAILED) {
                return onFailedMessageClicked;
            }
            return zendesk.messaging.android.internal.conversationscreen.messagelog.f.d();
        }
    }
}
