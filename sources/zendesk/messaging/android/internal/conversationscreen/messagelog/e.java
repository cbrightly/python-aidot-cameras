package zendesk.messaging.android.internal.conversationscreen.messagelog;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.MessageItem;
import zendesk.conversationkit.android.model.u;
import zendesk.messaging.R$color;
import zendesk.messaging.R$drawable;
import zendesk.messaging.R$string;
import zendesk.messaging.android.internal.m;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.file.FileView;
import zendesk.ui.android.conversation.form.FormResponseView;
import zendesk.ui.android.conversation.form.FormView;
import zendesk.ui.android.conversation.form.p;
import zendesk.ui.android.conversation.form.q;
import zendesk.ui.android.conversation.imagecell.ImageCellView;
import zendesk.ui.android.conversation.item.ItemGroupView;
import zendesk.ui.android.conversation.textcell.TextCellView;
import zendesk.ui.android.conversation.typingindicatorcell.TypingIndicatorCellView;

/* compiled from: MessageLogCellFactory.kt */
public final class e {
    @NotNull
    public static final e a = new e();

    private e() {
    }

    @NotNull
    public final View o(@NotNull b.a item, @NotNull ViewGroup parentView) {
        k.e(item, "item");
        k.e(parentView, "parentView");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        TextCellView $this$createUnsupportedCell_u24lambda_u2d0 = new TextCellView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createUnsupportedCell_u24lambda_u2d0.a(new j($this$createUnsupportedCell_u24lambda_u2d0, item));
        return $this$createUnsupportedCell_u24lambda_u2d0;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class j extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.a, zendesk.ui.android.conversation.textcell.a> {
        final /* synthetic */ b.a $item;
        final /* synthetic */ TextCellView $this_apply;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(TextCellView textCellView, b.a aVar) {
            super(1);
            this.$this_apply = textCellView;
            this.$item = aVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.textcell.a invoke(@NotNull zendesk.ui.android.conversation.textcell.a textCellRendering) {
            k.e(textCellRendering, "textCellRendering");
            return textCellRendering.d().j(new a(this.$this_apply, this.$item)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.b, zendesk.ui.android.conversation.textcell.b> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ TextCellView $this_apply;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(TextCellView textCellView, b.a aVar) {
                super(1);
                this.$this_apply = textCellView;
                this.$item = aVar;
            }

            @NotNull
            public final zendesk.ui.android.conversation.textcell.b invoke(@NotNull zendesk.ui.android.conversation.textcell.b state) {
                k.e(state, Constants.ACTION_STATE);
                String string = this.$this_apply.getContext().getString(R$string.zma_conversation_message_label_cant_be_displayed);
                k.d(string, "context.getString(R.stri…_label_cant_be_displayed)");
                Integer valueOf = Integer.valueOf(ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text));
                e eVar = e.a;
                return state.a(string, valueOf, Integer.valueOf(e.d(eVar, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null)), Integer.valueOf(eVar.p(this.$item.h(), this.$item.b())));
            }
        }
    }

    @NotNull
    public final View m(@NotNull b.a item, @NotNull ViewGroup parentView, @Nullable @ColorInt Integer outboundMessageColor, @NotNull kotlin.jvm.functions.l<? super b.a, x> onMessageContainerClicked, @NotNull kotlin.jvm.functions.l<? super String, x> onMessageTextClicked) {
        k.e(item, "item");
        k.e(parentView, "parentView");
        k.e(onMessageContainerClicked, "onMessageContainerClicked");
        k.e(onMessageTextClicked, "onMessageTextClicked");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        TextCellView $this$createTextCell_u24lambda_u2d1 = new TextCellView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createTextCell_u24lambda_u2d1.a(new h(item, $this$createTextCell_u24lambda_u2d1, outboundMessageColor == null ? ContextCompat.getColor($this$createTextCell_u24lambda_u2d1.getContext(), R$color.zma_color_message) : outboundMessageColor.intValue(), onMessageContainerClicked, onMessageTextClicked));
        return $this$createTextCell_u24lambda_u2d1;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class h extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.a, zendesk.ui.android.conversation.textcell.a> {
        final /* synthetic */ b.a $item;
        final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onMessageContainerClicked;
        final /* synthetic */ kotlin.jvm.functions.l<String, x> $onMessageTextClicked;
        final /* synthetic */ int $resolvedOutboundMessageColor;
        final /* synthetic */ TextCellView $this_apply;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(b.a aVar, TextCellView textCellView, int i, kotlin.jvm.functions.l<? super b.a, x> lVar, kotlin.jvm.functions.l<? super String, x> lVar2) {
            super(1);
            this.$item = aVar;
            this.$this_apply = textCellView;
            this.$resolvedOutboundMessageColor = i;
            this.$onMessageContainerClicked = lVar;
            this.$onMessageTextClicked = lVar2;
        }

        @NotNull
        public final zendesk.ui.android.conversation.textcell.a invoke(@NotNull zendesk.ui.android.conversation.textcell.a textCellRendering) {
            k.e(textCellRendering, "textCellRendering");
            return textCellRendering.d().j(new a(this.$item, this.$this_apply, this.$resolvedOutboundMessageColor)).e(new b(this.$onMessageContainerClicked, this.$item)).f(new c(this.$onMessageTextClicked)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.b, zendesk.ui.android.conversation.textcell.b> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ int $resolvedOutboundMessageColor;
            final /* synthetic */ TextCellView $this_apply;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$h$a$a  reason: collision with other inner class name */
            /* compiled from: MessageLogCellFactory.kt */
            public final /* synthetic */ class C0539a {
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
            a(b.a aVar, TextCellView textCellView, int i) {
                super(1);
                this.$item = aVar;
                this.$this_apply = textCellView;
                this.$resolvedOutboundMessageColor = i;
            }

            @NotNull
            public final zendesk.ui.android.conversation.textcell.b invoke(@NotNull zendesk.ui.android.conversation.textcell.b state) {
                int i;
                int i2;
                k.e(state, Constants.ACTION_STATE);
                MessageContent d = this.$item.e().d();
                MessageContent.Text text = d instanceof MessageContent.Text ? (MessageContent.Text) d : null;
                String c = text == null ? null : text.c();
                if (c == null) {
                    c = "";
                }
                Context context = this.$this_apply.getContext();
                zendesk.messaging.android.internal.model.a b = this.$item.b();
                zendesk.messaging.android.internal.model.a aVar = zendesk.messaging.android.internal.model.a.INBOUND;
                if (b == aVar) {
                    i = R$color.zma_color_message_inbound_text;
                } else {
                    i = R$color.zma_color_message_outbound_text;
                }
                Integer valueOf = Integer.valueOf(ContextCompat.getColor(context, i));
                if (this.$item.b() == aVar) {
                    i2 = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_background);
                } else {
                    switch (C0539a.a[this.$item.i().ordinal()]) {
                        case 1:
                            i2 = e.d(e.a, this.$resolvedOutboundMessageColor, 0.0f, 1, (Object) null);
                            break;
                        case 2:
                            i2 = this.$resolvedOutboundMessageColor;
                            break;
                        case 3:
                            i2 = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                return state.a(c, valueOf, Integer.valueOf(i2), Integer.valueOf(e.a.p(this.$item.h(), this.$item.b())));
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onMessageContainerClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(kotlin.jvm.functions.l<? super b.a, x> lVar, b.a aVar) {
                super(1);
                this.$onMessageContainerClicked = lVar;
                this.$item = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                this.$onMessageContainerClicked.invoke(this.$item);
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class c extends l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ kotlin.jvm.functions.l<String, x> $onMessageTextClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(kotlin.jvm.functions.l<? super String, x> lVar) {
                super(1);
                this.$onMessageTextClicked = lVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                this.$onMessageTextClicked.invoke(it);
            }
        }
    }

    @NotNull
    public final View n(@NotNull ViewGroup parentView) {
        k.e(parentView, "parentView");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        TypingIndicatorCellView $this$createTypingIndicatorCell_u24lambda_u2d2 = new TypingIndicatorCellView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createTypingIndicatorCell_u24lambda_u2d2.a(new i($this$createTypingIndicatorCell_u24lambda_u2d2));
        return $this$createTypingIndicatorCell_u24lambda_u2d2;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class i extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.typingindicatorcell.a, zendesk.ui.android.conversation.typingindicatorcell.a> {
        final /* synthetic */ TypingIndicatorCellView $this_apply;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(TypingIndicatorCellView typingIndicatorCellView) {
            super(1);
            this.$this_apply = typingIndicatorCellView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.typingindicatorcell.a invoke(@NotNull zendesk.ui.android.conversation.typingindicatorcell.a typingIndicatorCellRendering) {
            k.e(typingIndicatorCellRendering, "typingIndicatorCellRendering");
            return typingIndicatorCellRendering.b().d(new a(this.$this_apply)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.typingindicatorcell.b, zendesk.ui.android.conversation.typingindicatorcell.b> {
            final /* synthetic */ TypingIndicatorCellView $this_apply;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(TypingIndicatorCellView typingIndicatorCellView) {
                super(1);
                this.$this_apply = typingIndicatorCellView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.typingindicatorcell.b invoke(@NotNull zendesk.ui.android.conversation.typingindicatorcell.b state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(Integer.valueOf(ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_background)));
            }
        }
    }

    @NotNull
    public final View e(@NotNull MessageContent.File fileContent, @NotNull b.a item, @NotNull ViewGroup parentView, @Nullable @ColorInt Integer outboundMessageColor, @NotNull kotlin.jvm.functions.l<? super String, x> onFileClicked) {
        k.e(fileContent, "fileContent");
        k.e(item, "item");
        k.e(parentView, "parentView");
        k.e(onFileClicked, "onFileClicked");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        FileView $this$createFileCell_u24lambda_u2d3 = new FileView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createFileCell_u24lambda_u2d3.a(new a(outboundMessageColor, $this$createFileCell_u24lambda_u2d3, item, fileContent, onFileClicked));
        return $this$createFileCell_u24lambda_u2d3;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.a, zendesk.ui.android.conversation.file.a> {
        final /* synthetic */ MessageContent.File $fileContent;
        final /* synthetic */ b.a $item;
        final /* synthetic */ kotlin.jvm.functions.l<String, x> $onFileClicked;
        final /* synthetic */ Integer $outboundMessageColor;
        final /* synthetic */ FileView $this_apply;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Integer num, FileView fileView, b.a aVar, MessageContent.File file, kotlin.jvm.functions.l<? super String, x> lVar) {
            super(1);
            this.$outboundMessageColor = num;
            this.$this_apply = fileView;
            this.$item = aVar;
            this.$fileContent = file;
            this.$onFileClicked = lVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.file.a invoke(@NotNull zendesk.ui.android.conversation.file.a fileRendering) {
            int textAndIconColor;
            k.e(fileRendering, "fileRendering");
            Integer num = this.$outboundMessageColor;
            int resolvedOutboundMessageColor = num == null ? ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message) : num.intValue();
            if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                textAndIconColor = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_text);
            } else if (this.$item.b() == zendesk.messaging.android.internal.model.a.OUTBOUND && this.$item.i() == u.SENT) {
                textAndIconColor = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text);
            } else {
                textAndIconColor = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text), 0.0f, 1, (Object) null);
            }
            return fileRendering.c().g(new C0532a(this.$fileContent, textAndIconColor, this.$item, this.$this_apply, resolvedOutboundMessageColor)).d(new b(this.$onFileClicked, this.$fileContent)).a();
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$a$a  reason: collision with other inner class name */
        /* compiled from: MessageLogCellFactory.kt */
        public static final class C0532a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.b, zendesk.ui.android.conversation.file.b> {
            final /* synthetic */ MessageContent.File $fileContent;
            final /* synthetic */ b.a $item;
            final /* synthetic */ int $resolvedOutboundMessageColor;
            final /* synthetic */ int $textAndIconColor;
            final /* synthetic */ FileView $this_apply;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$a$a$a  reason: collision with other inner class name */
            /* compiled from: MessageLogCellFactory.kt */
            public final /* synthetic */ class C0533a {
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
            C0532a(MessageContent.File file, int i, b.a aVar, FileView fileView, int i2) {
                super(1);
                this.$fileContent = file;
                this.$textAndIconColor = i;
                this.$item = aVar;
                this.$this_apply = fileView;
                this.$resolvedOutboundMessageColor = i2;
            }

            @NotNull
            public final zendesk.ui.android.conversation.file.b invoke(@NotNull zendesk.ui.android.conversation.file.b state) {
                String fileName;
                int i;
                k.e(state, Constants.ACTION_STATE);
                String fallbackName = kotlin.text.x.V0(this.$fileContent.e(), "/", (String) null, 2, (Object) null);
                try {
                    String queryParameter = Uri.parse(this.$fileContent.e()).getQueryParameter("name");
                    if (queryParameter == null) {
                        queryParameter = fallbackName;
                    }
                    fileName = queryParameter;
                } catch (NullPointerException e) {
                    fileName = fallbackName;
                }
                k.d(fileName, "try {\n                  …ame\n                    }");
                long c = this.$fileContent.c();
                Integer valueOf = Integer.valueOf(this.$textAndIconColor);
                Integer valueOf2 = Integer.valueOf(this.$textAndIconColor);
                if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                    i = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_background);
                } else {
                    switch (C0533a.a[this.$item.i().ordinal()]) {
                        case 1:
                            i = e.d(e.a, this.$resolvedOutboundMessageColor, 0.0f, 1, (Object) null);
                            break;
                        case 2:
                            i = this.$resolvedOutboundMessageColor;
                            break;
                        case 3:
                            i = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                return state.a(fileName, c, valueOf, valueOf2, Integer.valueOf(i), Integer.valueOf(e.a.p(this.$item.h(), this.$item.b())));
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ MessageContent.File $fileContent;
            final /* synthetic */ kotlin.jvm.functions.l<String, x> $onFileClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(kotlin.jvm.functions.l<? super String, x> lVar, MessageContent.File file) {
                super(0);
                this.$onFileClicked = lVar;
                this.$fileContent = file;
            }

            public final void invoke() {
                this.$onFileClicked.invoke(this.$fileContent.e());
            }
        }
    }

    @NotNull
    public final View f(@NotNull MessageContent.FileUpload uploadContent, @NotNull b.a item, @NotNull ViewGroup parentView, @Nullable @ColorInt Integer outboundMessageColor, @NotNull kotlin.jvm.functions.l<? super b.a, x> onFailedMessageClicked) {
        k.e(uploadContent, "uploadContent");
        k.e(item, "item");
        k.e(parentView, "parentView");
        k.e(onFailedMessageClicked, "onFailedMessageClicked");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        FileView $this$createFileUploadCell_u24lambda_u2d4 = new FileView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createFileUploadCell_u24lambda_u2d4.a(new b(outboundMessageColor, $this$createFileUploadCell_u24lambda_u2d4, item, uploadContent, onFailedMessageClicked));
        return $this$createFileUploadCell_u24lambda_u2d4;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.a, zendesk.ui.android.conversation.file.a> {
        final /* synthetic */ b.a $item;
        final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onFailedMessageClicked;
        final /* synthetic */ Integer $outboundMessageColor;
        final /* synthetic */ FileView $this_apply;
        final /* synthetic */ MessageContent.FileUpload $uploadContent;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Integer num, FileView fileView, b.a aVar, MessageContent.FileUpload fileUpload, kotlin.jvm.functions.l<? super b.a, x> lVar) {
            super(1);
            this.$outboundMessageColor = num;
            this.$this_apply = fileView;
            this.$item = aVar;
            this.$uploadContent = fileUpload;
            this.$onFailedMessageClicked = lVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.file.a invoke(@NotNull zendesk.ui.android.conversation.file.a fileRendering) {
            int textAndIconColor;
            k.e(fileRendering, "fileRendering");
            Integer num = this.$outboundMessageColor;
            int resolvedOutboundMessageColor = num == null ? ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message) : num.intValue();
            if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                textAndIconColor = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_text);
            } else if (this.$item.b() == zendesk.messaging.android.internal.model.a.OUTBOUND && this.$item.i() == u.SENT) {
                textAndIconColor = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text);
            } else {
                textAndIconColor = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text), 0.0f, 1, (Object) null);
            }
            return fileRendering.c().g(new a(this.$uploadContent, textAndIconColor, this.$item, this.$this_apply, resolvedOutboundMessageColor)).d(new C0535b(this.$item, this.$onFailedMessageClicked)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.b, zendesk.ui.android.conversation.file.b> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ int $resolvedOutboundMessageColor;
            final /* synthetic */ int $textAndIconColor;
            final /* synthetic */ FileView $this_apply;
            final /* synthetic */ MessageContent.FileUpload $uploadContent;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$b$a$a  reason: collision with other inner class name */
            /* compiled from: MessageLogCellFactory.kt */
            public final /* synthetic */ class C0534a {
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
            a(MessageContent.FileUpload fileUpload, int i, b.a aVar, FileView fileView, int i2) {
                super(1);
                this.$uploadContent = fileUpload;
                this.$textAndIconColor = i;
                this.$item = aVar;
                this.$this_apply = fileView;
                this.$resolvedOutboundMessageColor = i2;
            }

            @NotNull
            public final zendesk.ui.android.conversation.file.b invoke(@NotNull zendesk.ui.android.conversation.file.b state) {
                int i;
                k.e(state, Constants.ACTION_STATE);
                String c = this.$uploadContent.c();
                long d = this.$uploadContent.d();
                Integer valueOf = Integer.valueOf(this.$textAndIconColor);
                Integer valueOf2 = Integer.valueOf(this.$textAndIconColor);
                if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                    i = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_background);
                } else {
                    switch (C0534a.a[this.$item.i().ordinal()]) {
                        case 1:
                            i = e.d(e.a, this.$resolvedOutboundMessageColor, 0.0f, 1, (Object) null);
                            break;
                        case 2:
                            i = this.$resolvedOutboundMessageColor;
                            break;
                        case 3:
                            i = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                return state.a(c, d, valueOf, valueOf2, Integer.valueOf(i), Integer.valueOf(e.a.p(this.$item.h(), this.$item.b())));
            }
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$b$b  reason: collision with other inner class name */
        /* compiled from: MessageLogCellFactory.kt */
        public static final class C0535b extends l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onFailedMessageClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0535b(b.a aVar, kotlin.jvm.functions.l<? super b.a, x> lVar) {
                super(0);
                this.$item = aVar;
                this.$onFailedMessageClicked = lVar;
            }

            public final void invoke() {
                if (this.$item.i() == u.FAILED) {
                    this.$onFailedMessageClicked.invoke(this.$item);
                }
            }
        }
    }

    @NotNull
    public final FormView<Field> h(@NotNull ViewGroup parentView, @NotNull kotlin.jvm.functions.l<? super p<Field>, p<Field>> renderingUpdate) {
        k.e(parentView, "parentView");
        k.e(renderingUpdate, "renderingUpdate");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        FormView $this$createFormView_u24lambda_u2d5 = new FormView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createFormView_u24lambda_u2d5.a(renderingUpdate);
        return $this$createFormView_u24lambda_u2d5;
    }

    @NotNull
    public final FormResponseView g(@NotNull ViewGroup parentView, @NotNull kotlin.jvm.functions.l<? super q, q> renderingUpdate) {
        k.e(parentView, "parentView");
        k.e(renderingUpdate, "renderingUpdate");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        FormResponseView $this$createFormResponseView_u24lambda_u2d6 = new FormResponseView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createFormResponseView_u24lambda_u2d6.a(renderingUpdate);
        return $this$createFormResponseView_u24lambda_u2d6;
    }

    @NotNull
    public final View l(@NotNull MessageContent.Carousel content, @NotNull ViewGroup parentView, @Nullable @ColorInt Integer pressedColor, @NotNull m uriHandler) {
        k.e(content, FirebaseAnalytics.Param.CONTENT);
        k.e(parentView, "parentView");
        k.e(uriHandler, "uriHandler");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        ItemGroupView $this$createListCell_u24lambda_u2d7 = new ItemGroupView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createListCell_u24lambda_u2d7.a(new g(content, pressedColor, $this$createListCell_u24lambda_u2d7, uriHandler));
        return $this$createListCell_u24lambda_u2d7;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class g extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.item.b, zendesk.ui.android.conversation.item.b> {
        final /* synthetic */ MessageContent.Carousel $content;
        final /* synthetic */ Integer $pressedColor;
        final /* synthetic */ ItemGroupView $this_apply;
        final /* synthetic */ m $uriHandler;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(MessageContent.Carousel carousel, Integer num, ItemGroupView itemGroupView, m mVar) {
            super(1);
            this.$content = carousel;
            this.$pressedColor = num;
            this.$this_apply = itemGroupView;
            this.$uriHandler = mVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.item.b invoke(@NotNull zendesk.ui.android.conversation.item.b itemGroupRendering) {
            k.e(itemGroupRendering, "itemGroupRendering");
            return itemGroupRendering.c().g(new a(this.$content, this.$pressedColor, this.$this_apply)).d(new b(this.$uriHandler)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.item.c, zendesk.ui.android.conversation.item.c> {
            final /* synthetic */ MessageContent.Carousel $content;
            final /* synthetic */ Integer $pressedColor;
            final /* synthetic */ ItemGroupView $this_apply;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(MessageContent.Carousel carousel, Integer num, ItemGroupView itemGroupView) {
                super(1);
                this.$content = carousel;
                this.$pressedColor = num;
                this.$this_apply = itemGroupView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.item.c invoke(@NotNull zendesk.ui.android.conversation.item.c state) {
                zendesk.ui.android.conversation.item.a aVar;
                k.e(state, Constants.ACTION_STATE);
                Iterable<MessageItem> $this$map$iv = this.$content.b();
                ItemGroupView itemGroupView = this.$this_apply;
                int $i$f$map = false;
                ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
                for (MessageItem item : $this$map$iv) {
                    Iterable $this$filterIsInstanceTo$iv$iv = item.a();
                    ArrayList arrayList2 = new ArrayList();
                    for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                        Iterable $this$map$iv2 = $this$map$iv;
                        int $i$f$map2 = $i$f$map;
                        if ((element$iv$iv instanceof MessageAction.Link) != 0) {
                            arrayList2.add(element$iv$iv);
                        }
                        $this$map$iv = $this$map$iv2;
                        $i$f$map = $i$f$map2;
                    }
                    Iterable $this$map$iv3 = $this$map$iv;
                    int $i$f$map3 = $i$f$map;
                    MessageAction.Link link = (MessageAction.Link) y.U(arrayList2);
                    if (link == null) {
                        aVar = null;
                    } else {
                        aVar = new zendesk.ui.android.conversation.item.a((String) null, item.g(), (Integer) null, item.b(), link.e(), 5, (DefaultConstructorMarker) null);
                    }
                    if (aVar == null) {
                        String string = itemGroupView.getResources().getString(R$string.zuia_conversation_message_label_unsupported_item);
                        k.d(string, "resources.getString(R.st…e_label_unsupported_item)");
                        aVar = new zendesk.ui.android.conversation.item.a((String) null, string, Integer.valueOf(ContextCompat.getColor(itemGroupView.getContext(), R$color.zma_color_alert)), (String) null, (Object) null, 25, (DefaultConstructorMarker) null);
                    }
                    arrayList.add(aVar);
                    $this$map$iv = $this$map$iv3;
                    $i$f$map = $i$f$map3;
                }
                int i = $i$f$map;
                return zendesk.ui.android.conversation.item.c.b(state, arrayList, (Integer) null, this.$pressedColor, 2, (Object) null);
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.item.a<String>, x> {
            final /* synthetic */ m $uriHandler;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(m mVar) {
                super(1);
                this.$uriHandler = mVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((zendesk.ui.android.conversation.item.a<String>) (zendesk.ui.android.conversation.item.a) p1);
                return x.a;
            }

            public final void invoke(@NotNull zendesk.ui.android.conversation.item.a<String> it) {
                k.e(it, "it");
                String uri = it.d();
                if (uri != null) {
                    this.$uriHandler.a(uri, zendesk.android.messaging.e.CAROUSEL);
                }
            }
        }
    }

    public static /* synthetic */ View j(e eVar, MessageContent.Image image, b.a aVar, ViewGroup viewGroup, m mVar, Integer num, kotlin.jvm.functions.l lVar, int i2, Object obj) {
        zendesk.messaging.android.internal.k kVar;
        c cVar;
        if ((i2 & 8) != 0) {
            kVar = zendesk.messaging.android.internal.k.a;
        } else {
            kVar = mVar;
        }
        Integer num2 = (i2 & 16) != 0 ? null : num;
        if ((i2 & 32) != 0) {
            cVar = c.INSTANCE;
        } else {
            cVar = lVar;
        }
        return eVar.i(image, aVar, viewGroup, kVar, num2, cVar);
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<String, x> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((String) p1);
            return x.a;
        }

        public final void invoke(@NotNull String it) {
            k.e(it, "it");
        }
    }

    @NotNull
    public final View i(@NotNull MessageContent.Image content, @NotNull b.a item, @NotNull ViewGroup parentView, @NotNull m uriHandler, @Nullable @ColorInt Integer outboundMessageColor, @NotNull kotlin.jvm.functions.l<? super String, x> onFileClicked) {
        k.e(content, FirebaseAnalytics.Param.CONTENT);
        k.e(item, "item");
        k.e(parentView, "parentView");
        k.e(uriHandler, "uriHandler");
        k.e(onFileClicked, "onFileClicked");
        if (zendesk.ui.android.conversation.imagecell.e.Companion.a(content.f())) {
            Context context = parentView.getContext();
            k.d(context, "parentView.context");
            ImageCellView imageCellView = new ImageCellView(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
            imageCellView.a(new d(content, parentView, item, uriHandler));
            return imageCellView;
        }
        Context context2 = parentView.getContext();
        k.d(context2, "parentView.context");
        FileView fileView = new FileView(context2, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        fileView.a(new C0536e(outboundMessageColor, parentView, item, content, onFileClicked));
        return fileView;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagecell.b, zendesk.ui.android.conversation.imagecell.b> {
        final /* synthetic */ MessageContent.Image $content;
        final /* synthetic */ b.a $item;
        final /* synthetic */ ViewGroup $parentView;
        final /* synthetic */ m $uriHandler;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(MessageContent.Image image, ViewGroup viewGroup, b.a aVar, m mVar) {
            super(1);
            this.$content = image;
            this.$parentView = viewGroup;
            this.$item = aVar;
            this.$uriHandler = mVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.imagecell.b invoke(@NotNull zendesk.ui.android.conversation.imagecell.b imageCellRendering) {
            k.e(imageCellRendering, "imageCellRendering");
            return imageCellRendering.c().g(new a(this.$content, this.$parentView, this.$item)).d(new b(this.$item, this.$uriHandler)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagecell.c, zendesk.ui.android.conversation.imagecell.c> {
            final /* synthetic */ MessageContent.Image $content;
            final /* synthetic */ b.a $item;
            final /* synthetic */ ViewGroup $parentView;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(MessageContent.Image image, ViewGroup viewGroup, b.a aVar) {
                super(1);
                this.$content = image;
                this.$parentView = viewGroup;
                this.$item = aVar;
            }

            @NotNull
            public final zendesk.ui.android.conversation.imagecell.c invoke(@NotNull zendesk.ui.android.conversation.imagecell.c state) {
                Uri uri;
                int i;
                k.e(state, Constants.ACTION_STATE);
                Uri parse = Uri.parse(this.$content.g());
                String it = this.$content.d();
                if (it == null) {
                    uri = null;
                } else {
                    uri = Uri.parse(it);
                }
                String f = this.$content.f();
                String h = this.$content.h();
                String string = this.$parentView.getContext().getString(R$string.zma_image_loading_error);
                Context context = this.$parentView.getContext();
                if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                    i = R$color.zma_color_message_inbound_text;
                } else {
                    i = R$color.zma_color_message_outbound_text;
                }
                return zendesk.ui.android.conversation.imagecell.c.b(state, parse, uri, f, h, false, false, Integer.valueOf(ContextCompat.getColor(context, i)), (Integer) null, string, e.a.q(this.$item.h(), this.$item.b()), Opcodes.ARETURN, (Object) null);
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ b.a $item;
            final /* synthetic */ m $uriHandler;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(b.a aVar, m mVar) {
                super(1);
                this.$item = aVar;
                this.$uriHandler = mVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String uri) {
                k.e(uri, "uri");
                if (this.$item.i() == u.SENT) {
                    this.$uriHandler.a(uri, zendesk.android.messaging.e.IMAGE);
                }
            }
        }
    }

    /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$e  reason: collision with other inner class name */
    /* compiled from: MessageLogCellFactory.kt */
    public static final class C0536e extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.a, zendesk.ui.android.conversation.file.a> {
        final /* synthetic */ MessageContent.Image $content;
        final /* synthetic */ b.a $item;
        final /* synthetic */ kotlin.jvm.functions.l<String, x> $onFileClicked;
        final /* synthetic */ Integer $outboundMessageColor;
        final /* synthetic */ ViewGroup $parentView;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0536e(Integer num, ViewGroup viewGroup, b.a aVar, MessageContent.Image image, kotlin.jvm.functions.l<? super String, x> lVar) {
            super(1);
            this.$outboundMessageColor = num;
            this.$parentView = viewGroup;
            this.$item = aVar;
            this.$content = image;
            this.$onFileClicked = lVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.file.a invoke(@NotNull zendesk.ui.android.conversation.file.a fileRendering) {
            int textAndIconColor;
            k.e(fileRendering, "fileRendering");
            Integer num = this.$outboundMessageColor;
            int resolvedOutboundMessageColor = num == null ? ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_message) : num.intValue();
            if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                textAndIconColor = ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_message_inbound_text);
            } else if (this.$item.b() == zendesk.messaging.android.internal.model.a.OUTBOUND && this.$item.i() == u.SENT) {
                textAndIconColor = ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_message_outbound_text);
            } else {
                textAndIconColor = e.d(e.a, ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_message_outbound_text), 0.0f, 1, (Object) null);
            }
            return fileRendering.c().g(new a(this.$content, textAndIconColor, this.$item, this.$parentView, resolvedOutboundMessageColor)).d(new b(this.$onFileClicked, this.$content)).a();
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$e$a */
        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.file.b, zendesk.ui.android.conversation.file.b> {
            final /* synthetic */ MessageContent.Image $content;
            final /* synthetic */ b.a $item;
            final /* synthetic */ ViewGroup $parentView;
            final /* synthetic */ int $resolvedOutboundMessageColor;
            final /* synthetic */ int $textAndIconColor;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$e$a$a  reason: collision with other inner class name */
            /* compiled from: MessageLogCellFactory.kt */
            public final /* synthetic */ class C0537a {
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
            a(MessageContent.Image image, int i, b.a aVar, ViewGroup viewGroup, int i2) {
                super(1);
                this.$content = image;
                this.$textAndIconColor = i;
                this.$item = aVar;
                this.$parentView = viewGroup;
                this.$resolvedOutboundMessageColor = i2;
            }

            @NotNull
            public final zendesk.ui.android.conversation.file.b invoke(@NotNull zendesk.ui.android.conversation.file.b state) {
                String fileName;
                int i;
                k.e(state, Constants.ACTION_STATE);
                String fallbackName = kotlin.text.x.V0(this.$content.g(), "/", (String) null, 2, (Object) null);
                try {
                    String queryParameter = Uri.parse(this.$content.g()).getQueryParameter("name");
                    if (queryParameter == null) {
                        queryParameter = fallbackName;
                    }
                    fileName = queryParameter;
                } catch (NullPointerException e) {
                    fileName = fallbackName;
                }
                k.d(fileName, "try {\n                  …                        }");
                long e2 = this.$content.e();
                Integer valueOf = Integer.valueOf(this.$textAndIconColor);
                Integer valueOf2 = Integer.valueOf(this.$textAndIconColor);
                if (this.$item.b() == zendesk.messaging.android.internal.model.a.INBOUND) {
                    i = ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_message_inbound_background);
                } else {
                    switch (C0537a.a[this.$item.i().ordinal()]) {
                        case 1:
                            i = e.d(e.a, this.$resolvedOutboundMessageColor, 0.0f, 1, (Object) null);
                            break;
                        case 2:
                            i = this.$resolvedOutboundMessageColor;
                            break;
                        case 3:
                            i = e.d(e.a, ContextCompat.getColor(this.$parentView.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                return state.a(fileName, e2, valueOf, valueOf2, Integer.valueOf(i), Integer.valueOf(e.a.p(this.$item.h(), this.$item.b())));
            }
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$e$b */
        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ MessageContent.Image $content;
            final /* synthetic */ kotlin.jvm.functions.l<String, x> $onFileClicked;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(kotlin.jvm.functions.l<? super String, x> lVar, MessageContent.Image image) {
                super(0);
                this.$onFileClicked = lVar;
                this.$content = image;
            }

            public final void invoke() {
                this.$onFileClicked.invoke(this.$content.g());
            }
        }
    }

    @NotNull
    public final View k(@NotNull MessageContent.FileUpload content, @NotNull b.a item, @NotNull ViewGroup parentView, @Nullable @ColorInt Integer outboundMessageColor, @NotNull kotlin.jvm.functions.l<? super b.a, x> onFailedMessageClicked, @NotNull m uriHandler) {
        k.e(content, FirebaseAnalytics.Param.CONTENT);
        k.e(item, "item");
        k.e(parentView, "parentView");
        k.e(onFailedMessageClicked, "onFailedMessageClicked");
        k.e(uriHandler, "uriHandler");
        Context context = parentView.getContext();
        k.d(context, "parentView.context");
        ImageCellView imageCellView = new ImageCellView(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        ImageCellView $this$createImageUploadCell_u24lambda_u2d8 = imageCellView;
        $this$createImageUploadCell_u24lambda_u2d8.a(new f(outboundMessageColor, $this$createImageUploadCell_u24lambda_u2d8, item, content, onFailedMessageClicked, uriHandler));
        return imageCellView;
    }

    /* compiled from: MessageLogCellFactory.kt */
    public static final class f extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagecell.b, zendesk.ui.android.conversation.imagecell.b> {
        final /* synthetic */ MessageContent.FileUpload $content;
        final /* synthetic */ b.a $item;
        final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onFailedMessageClicked;
        final /* synthetic */ Integer $outboundMessageColor;
        final /* synthetic */ ImageCellView $this_apply;
        final /* synthetic */ m $uriHandler;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(Integer num, ImageCellView imageCellView, b.a aVar, MessageContent.FileUpload fileUpload, kotlin.jvm.functions.l<? super b.a, x> lVar, m mVar) {
            super(1);
            this.$outboundMessageColor = num;
            this.$this_apply = imageCellView;
            this.$item = aVar;
            this.$content = fileUpload;
            this.$onFailedMessageClicked = lVar;
            this.$uriHandler = mVar;
        }

        @NotNull
        public final zendesk.ui.android.conversation.imagecell.b invoke(@NotNull zendesk.ui.android.conversation.imagecell.b imageCellRendering) {
            k.e(imageCellRendering, "imageCellRendering");
            return imageCellRendering.c().g(new a(this.$outboundMessageColor, this.$this_apply, this.$item, this.$content)).d(new b(this.$item, this.$onFailedMessageClicked, this.$uriHandler, this.$content)).a();
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.imagecell.c, zendesk.ui.android.conversation.imagecell.c> {
            final /* synthetic */ MessageContent.FileUpload $content;
            final /* synthetic */ b.a $item;
            final /* synthetic */ Integer $outboundMessageColor;
            final /* synthetic */ ImageCellView $this_apply;

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.e$f$a$a  reason: collision with other inner class name */
            /* compiled from: MessageLogCellFactory.kt */
            public final /* synthetic */ class C0538a {
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
            a(Integer num, ImageCellView imageCellView, b.a aVar, MessageContent.FileUpload fileUpload) {
                super(1);
                this.$outboundMessageColor = num;
                this.$this_apply = imageCellView;
                this.$item = aVar;
                this.$content = fileUpload;
            }

            @NotNull
            public final zendesk.ui.android.conversation.imagecell.c invoke(@NotNull zendesk.ui.android.conversation.imagecell.c state) {
                int i;
                int i2;
                k.e(state, Constants.ACTION_STATE);
                Integer num = this.$outboundMessageColor;
                int resolvedOutboundMessageColor = num == null ? ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message) : num.intValue();
                zendesk.messaging.android.internal.model.a b = this.$item.b();
                zendesk.messaging.android.internal.model.a aVar = zendesk.messaging.android.internal.model.a.INBOUND;
                if (b == aVar) {
                    i = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_text);
                } else if (this.$item.b() == zendesk.messaging.android.internal.model.a.OUTBOUND && this.$item.i() == u.SENT) {
                    i = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text);
                } else {
                    i = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_outbound_text), 0.0f, 1, (Object) null);
                }
                int textAndIconColor = i;
                if (this.$item.b() == aVar) {
                    i2 = ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_message_inbound_background);
                } else {
                    switch (C0538a.a[this.$item.i().ordinal()]) {
                        case 1:
                            i2 = e.d(e.a, resolvedOutboundMessageColor, 0.0f, 1, (Object) null);
                            break;
                        case 2:
                            i2 = resolvedOutboundMessageColor;
                            break;
                        case 3:
                            i2 = e.d(e.a, ContextCompat.getColor(this.$this_apply.getContext(), R$color.zma_color_alert), 0.0f, 1, (Object) null);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                int backgroundColor = i2;
                Uri parse = Uri.parse(this.$content.e());
                Uri parse2 = Uri.parse(this.$content.e());
                String b2 = this.$content.b();
                boolean z = false;
                boolean z2 = this.$item.i() == u.FAILED;
                if (this.$item.i() == u.PENDING) {
                    z = true;
                }
                return zendesk.ui.android.conversation.imagecell.c.b(state, parse, parse2, b2, (String) null, z2, z, Integer.valueOf(textAndIconColor), Integer.valueOf(backgroundColor), (String) null, e.a.q(this.$item.h(), this.$item.b()), 264, (Object) null);
            }
        }

        /* compiled from: MessageLogCellFactory.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ MessageContent.FileUpload $content;
            final /* synthetic */ b.a $item;
            final /* synthetic */ kotlin.jvm.functions.l<b.a, x> $onFailedMessageClicked;
            final /* synthetic */ m $uriHandler;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(b.a aVar, kotlin.jvm.functions.l<? super b.a, x> lVar, m mVar, MessageContent.FileUpload fileUpload) {
                super(1);
                this.$item = aVar;
                this.$onFailedMessageClicked = lVar;
                this.$uriHandler = mVar;
                this.$content = fileUpload;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                if (this.$item.i() == u.FAILED) {
                    this.$onFailedMessageClicked.invoke(this.$item);
                } else if (this.$item.i() == u.SENT) {
                    this.$uriHandler.a(this.$content.e(), zendesk.android.messaging.e.IMAGE);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @DrawableRes
    public final int p(zendesk.messaging.android.internal.model.e shape, zendesk.messaging.android.internal.model.a direction) {
        zendesk.messaging.android.internal.model.e eVar = zendesk.messaging.android.internal.model.e.STANDALONE;
        if (shape == eVar && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return R$drawable.zuia_message_cell_inbound_shape_single;
        }
        zendesk.messaging.android.internal.model.e eVar2 = zendesk.messaging.android.internal.model.e.GROUP_TOP;
        if (shape == eVar2 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return R$drawable.zuia_message_cell_inbound_shape_top;
        }
        zendesk.messaging.android.internal.model.e eVar3 = zendesk.messaging.android.internal.model.e.GROUP_MIDDLE;
        if (shape == eVar3 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return R$drawable.zuia_message_cell_inbound_shape_middle;
        }
        zendesk.messaging.android.internal.model.e eVar4 = zendesk.messaging.android.internal.model.e.GROUP_BOTTOM;
        if (shape == eVar4 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return R$drawable.zuia_message_cell_inbound_shape_bottom;
        }
        if (shape == eVar && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return R$drawable.zuia_message_cell_outbound_shape_single;
        }
        if (shape == eVar2 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return R$drawable.zuia_message_cell_outbound_shape_top;
        }
        if (shape == eVar3 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return R$drawable.zuia_message_cell_outbound_shape_middle;
        }
        if (shape == eVar4 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return R$drawable.zuia_message_cell_outbound_shape_bottom;
        }
        return R$drawable.zuia_message_cell_inbound_shape_single;
    }

    /* access modifiers changed from: private */
    public final zendesk.ui.android.conversation.imagecell.a q(zendesk.messaging.android.internal.model.e shape, zendesk.messaging.android.internal.model.a direction) {
        zendesk.messaging.android.internal.model.e eVar = zendesk.messaging.android.internal.model.e.STANDALONE;
        if (shape == eVar && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.INBOUND_SINGLE;
        }
        zendesk.messaging.android.internal.model.e eVar2 = zendesk.messaging.android.internal.model.e.GROUP_TOP;
        if (shape == eVar2 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.INBOUND_TOP;
        }
        zendesk.messaging.android.internal.model.e eVar3 = zendesk.messaging.android.internal.model.e.GROUP_MIDDLE;
        if (shape == eVar3 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.INBOUND_MIDDLE;
        }
        zendesk.messaging.android.internal.model.e eVar4 = zendesk.messaging.android.internal.model.e.GROUP_BOTTOM;
        if (shape == eVar4 && direction == zendesk.messaging.android.internal.model.a.INBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.INBOUND_BOTTOM;
        }
        if (shape == eVar && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.OUTBOUND_SINGLE;
        }
        if (shape == eVar2 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.OUTBOUND_TOP;
        }
        if (shape == eVar3 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.OUTBOUND_MIDDLE;
        }
        if (shape == eVar4 && direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            return zendesk.ui.android.conversation.imagecell.a.OUTBOUND_BOTTOM;
        }
        return zendesk.ui.android.conversation.imagecell.a.INBOUND_SINGLE;
    }

    public static /* synthetic */ int d(e eVar, int i2, float f2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            f2 = 0.5f;
        }
        return eVar.c(i2, f2);
    }

    @ColorInt
    public final int c(@ColorInt int $this$adjustAlpha, float factor) {
        return Color.argb(kotlin.math.b.b(((float) Color.alpha($this$adjustAlpha)) * factor), Color.red($this$adjustAlpha), Color.green($this$adjustAlpha), Color.blue($this$adjustAlpha));
    }
}
