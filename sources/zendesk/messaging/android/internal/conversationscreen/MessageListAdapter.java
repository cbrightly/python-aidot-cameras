package zendesk.messaging.android.internal.conversationscreen;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.DiffUtil;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.android.internal.adapterdelegate.AdapterDelegatesManager;
import zendesk.messaging.android.internal.adapterdelegate.AsyncListDifferDelegationAdapter;
import zendesk.messaging.android.internal.conversationscreen.delegates.MessageContainerAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.QuickReplyAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.TimestampDividerAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.TypingIndicatorAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.UnreadMessagesDividerAdapterDelegate;
import zendesk.messaging.android.internal.m;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: MessageListAdapter.kt */
public final class MessageListAdapter extends AsyncListDifferDelegationAdapter<b> {
    @NotNull
    public static final Companion b = new Companion((DefaultConstructorMarker) null);
    @NotNull
    private final MessageContainerAdapterDelegate c;
    @NotNull
    private final QuickReplyAdapterDelegate d;
    @NotNull
    private final UnreadMessagesDividerAdapterDelegate e;
    @Nullable
    @ColorInt
    private Integer f;
    @Nullable
    @ColorInt
    private Integer g;
    @Nullable
    @ColorInt
    private Integer h;

    public MessageListAdapter() {
        this((MessageContainerAdapterDelegate) null, (QuickReplyAdapterDelegate) null, (UnreadMessagesDividerAdapterDelegate) null, 7, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MessageListAdapter(MessageContainerAdapterDelegate messageContainerAdapterDelegate, QuickReplyAdapterDelegate quickReplyAdapterDelegate, UnreadMessagesDividerAdapterDelegate unreadMessagesDividerAdapterDelegate, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new MessageContainerAdapterDelegate((l) null, (m) null, (p) null, (l) null, (l) null, (Map) null, 63, (DefaultConstructorMarker) null) : messageContainerAdapterDelegate, (i & 2) != 0 ? new QuickReplyAdapterDelegate((l) null, 1, (DefaultConstructorMarker) null) : quickReplyAdapterDelegate, (i & 4) != 0 ? new UnreadMessagesDividerAdapterDelegate() : unreadMessagesDividerAdapterDelegate);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MessageListAdapter(@NotNull MessageContainerAdapterDelegate messageContainerAdapterDelegate, @NotNull QuickReplyAdapterDelegate quickReplyAdapterDelegate, @NotNull UnreadMessagesDividerAdapterDelegate unreadMessagesDividerAdapterDelegate) {
        super(b, new AdapterDelegatesManager(messageContainerAdapterDelegate, unreadMessagesDividerAdapterDelegate, new TimestampDividerAdapterDelegate(), new TypingIndicatorAdapterDelegate(), quickReplyAdapterDelegate));
        k.e(messageContainerAdapterDelegate, "messageContainerAdapterDelegate");
        k.e(quickReplyAdapterDelegate, "quickReplyAdapterDelegate");
        k.e(unreadMessagesDividerAdapterDelegate, "unreadMessagesDividerAdapterDelegate");
        this.c = messageContainerAdapterDelegate;
        this.d = quickReplyAdapterDelegate;
        this.e = unreadMessagesDividerAdapterDelegate;
        this.f = messageContainerAdapterDelegate.i();
        this.g = messageContainerAdapterDelegate.h();
        this.h = unreadMessagesDividerAdapterDelegate.h();
    }

    public final void j(@Nullable Integer value) {
        this.f = value;
        this.c.t(value);
    }

    public final void a(@Nullable Integer value) {
        this.g = value;
        this.c.m(value);
        this.d.l(value);
    }

    public final void c(@Nullable Integer value) {
        this.h = value;
        this.e.l(value);
    }

    public final void h(@NotNull l<? super MessageAction.Reply, x> value) {
        k.e(value, "value");
        this.d.k(value);
    }

    public final void d(@NotNull l<? super b.a, x> value) {
        k.e(value, "value");
        this.c.o(value);
    }

    public final void i(@NotNull m value) {
        k.e(value, "value");
        this.c.s(value);
    }

    public final void e(@NotNull p<? super List<? extends Field>, ? super b.a, x> value) {
        k.e(value, "value");
        this.c.p(value);
    }

    public final void g(@NotNull l<? super Boolean, x> value) {
        k.e(value, "value");
        this.c.r(value);
    }

    public final void f(@NotNull l<? super DisplayedField, x> value) {
        k.e(value, "value");
        this.c.q(value);
    }

    public final void b(@NotNull Map<Integer, DisplayedField> value) {
        k.e(value, "value");
        this.c.n(value);
    }

    /* compiled from: MessageListAdapter.kt */
    public static final class Companion extends DiffUtil.ItemCallback<b> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: b */
        public boolean areItemsTheSame(@NotNull b oldItem, @NotNull b newItem) {
            k.e(oldItem, "oldItem");
            k.e(newItem, "newItem");
            if ((oldItem instanceof b.e) && (newItem instanceof b.e)) {
                return k.a(((b.e) oldItem).b(), ((b.e) newItem).b());
            }
            if ((oldItem instanceof b.c) && (newItem instanceof b.c)) {
                return k.a(((b.c) oldItem).a(), ((b.c) newItem).a());
            }
            if ((oldItem instanceof b.a) && (newItem instanceof b.a)) {
                return k.a(((b.a) oldItem).e().h(), ((b.a) newItem).e().h());
            }
            if ((oldItem instanceof b.C0546b) && (newItem instanceof b.C0546b)) {
                return k.a(((b.C0546b) oldItem).b(), ((b.C0546b) newItem).b());
            }
            if (!(oldItem instanceof b.d) || !(newItem instanceof b.d)) {
                return false;
            }
            return k.a(((b.d) oldItem).a(), ((b.d) newItem).a());
        }

        /* renamed from: a */
        public boolean areContentsTheSame(@NotNull b oldItem, @NotNull b newItem) {
            k.e(oldItem, "oldItem");
            k.e(newItem, "newItem");
            return k.a(oldItem, newItem);
        }
    }
}
