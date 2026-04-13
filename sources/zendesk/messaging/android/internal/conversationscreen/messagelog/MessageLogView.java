package zendesk.messaging.android.internal.conversationscreen.messagelog;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.conversationscreen.MessageListAdapter;
import zendesk.messaging.android.internal.conversationscreen.delegates.MessageContainerAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.QuickReplyAdapterDelegate;
import zendesk.messaging.android.internal.conversationscreen.delegates.UnreadMessagesDividerAdapterDelegate;

/* compiled from: MessageLogView.kt */
public final class MessageLogView extends FrameLayout implements zendesk.ui.android.a<g> {
    @NotNull
    private static final b c = new b((DefaultConstructorMarker) null);
    @NotNull
    private final RecyclerView d;
    @NotNull
    private final MessageListAdapter f;
    @NotNull
    private g q;
    @NotNull
    private LinearLayoutManager x;
    /* access modifiers changed from: private */
    @NotNull
    public AtomicInteger y;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageLogView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageLogView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageLogView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MessageLogView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MessageLogView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.q = new g();
        this.x = new LinearLayoutManager(context, 1, false);
        this.y = new AtomicInteger(0);
        FrameLayout.inflate(context, R$layout.zma_view_message_log, this);
        View findViewById = findViewById(R$id.zma_message_list_recycler_view);
        k.d(findViewById, "findViewById(R.id.zma_message_list_recycler_view)");
        RecyclerView recyclerView = (RecyclerView) findViewById;
        this.d = recyclerView;
        MessageListAdapter messageListAdapter = new MessageListAdapter((MessageContainerAdapterDelegate) null, (QuickReplyAdapterDelegate) null, (UnreadMessagesDividerAdapterDelegate) null, 7, (DefaultConstructorMarker) null);
        this.f = messageListAdapter;
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(this.x);
        recyclerView.addOnLayoutChangeListener(new d(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(this) {
            @NotNull
            private AtomicInteger a = new AtomicInteger(0);
            final /* synthetic */ MessageLogView b;

            {
                this.b = $receiver;
            }

            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                k.e(recyclerView, "recyclerView");
                this.a.compareAndSet(0, newState);
                switch (newState) {
                    case 0:
                        if (!this.a.compareAndSet(2, newState)) {
                            this.a.compareAndSet(1, newState);
                            return;
                        }
                        return;
                    case 1:
                        this.a.compareAndSet(0, newState);
                        return;
                    case 2:
                        this.a.compareAndSet(1, newState);
                        return;
                    default:
                        return;
                }
            }

            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                k.e(recyclerView, "recyclerView");
                if (this.a.get() != 0) {
                    this.b.y.getAndAdd(dy);
                }
            }
        });
        recyclerView.getViewTreeObserver().addOnGlobalFocusChangeListener(new b(this));
        a(a.INSTANCE);
    }

    /* access modifiers changed from: private */
    public static final void b(MessageLogView this$0, View $noName_0, int $noName_1, int $noName_2, int $noName_3, int bottom, int $noName_5, int $noName_6, int $noName_7, int oldBottom) {
        k.e(this$0, "this$0");
        int y2 = oldBottom - bottom;
        if (Math.abs(y2) <= 0) {
            return;
        }
        if (y2 > 0 || Math.abs(this$0.y.get()) >= Math.abs(y2)) {
            this$0.d.scrollBy(0, Math.abs(y2));
        } else {
            this$0.d.scrollBy(0, this$0.y.get());
        }
    }

    /* access modifiers changed from: private */
    public static final void c(MessageLogView this$0, View $noName_0, View $noName_1) {
        k.e(this$0, "this$0");
        this$0.i(this$0.d, 0.15d);
    }

    /* compiled from: MessageLogView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<g, g> {
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

    public void a(@NotNull kotlin.jvm.functions.l<? super g, g> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        g invoke = renderingUpdate.invoke(this.q);
        this.q = invoke;
        Integer f2 = invoke.g().f();
        if (f2 != null) {
            this.d.setEdgeEffectFactory(new MessageLogView$render$1$1(f2.intValue()));
        }
        MessageListAdapter $this$render_u24lambda_u2d4 = this.f;
        $this$render_u24lambda_u2d4.j(this.q.g().f());
        $this$render_u24lambda_u2d4.h(this.q.e());
        $this$render_u24lambda_u2d4.d(this.q.a());
        $this$render_u24lambda_u2d4.i(this.q.f());
        $this$render_u24lambda_u2d4.e(this.q.b());
        $this$render_u24lambda_u2d4.g(this.q.d());
        $this$render_u24lambda_u2d4.a(this.q.g().b());
        $this$render_u24lambda_u2d4.c(this.q.g().e());
        $this$render_u24lambda_u2d4.b(this.q.g().c());
        $this$render_u24lambda_u2d4.f(this.q.c());
        $this$render_u24lambda_u2d4.submitList(this.q.g().d(), new a(this));
    }

    /* access modifiers changed from: private */
    public static final void k(MessageLogView this$0) {
        k.e(this$0, "this$0");
        boolean isAtTheBottom = true;
        int lastMessagePosition = this$0.q.g().d().size() - 1;
        RecyclerView.LayoutManager layoutManager = this$0.d.getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager == null) {
            isAtTheBottom = false;
        } else if (linearLayoutManager.findLastVisibleItemPosition() != lastMessagePosition - 1) {
            isAtTheBottom = false;
        }
        if (isAtTheBottom || this$0.q.g().g()) {
            this$0.d.scrollToPosition(lastMessagePosition);
        }
    }

    private final void i(RecyclerView $this$onScrollToBottomIfKeyboardShown, double keyboardRatio) {
        RecyclerView.Adapter $this$onScrollToBottomIfKeyboardShown_u24lambda_u2d8;
        Rect rectangle = new Rect();
        $this$onScrollToBottomIfKeyboardShown.getWindowVisibleDisplayFrame(rectangle);
        int screenHeight = $this$onScrollToBottomIfKeyboardShown.getRootView().getHeight();
        int keyBoardHeight = screenHeight - rectangle.bottom;
        RecyclerView.LayoutManager layoutManager = $this$onScrollToBottomIfKeyboardShown.getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (((double) keyBoardHeight) > ((double) screenHeight) * keyboardRatio && ($this$onScrollToBottomIfKeyboardShown_u24lambda_u2d8 = $this$onScrollToBottomIfKeyboardShown.getAdapter()) != null) {
            int lastItemPosition = $this$onScrollToBottomIfKeyboardShown_u24lambda_u2d8.getItemCount() - 1;
            if (linearLayoutManager != null) {
                LinearLayoutManager layoutManager2 = linearLayoutManager;
                layoutManager2.scrollToPosition(lastItemPosition);
                $this$onScrollToBottomIfKeyboardShown.post(new c(layoutManager2, lastItemPosition, $this$onScrollToBottomIfKeyboardShown));
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void j(LinearLayoutManager $layoutManager, int $lastItemPosition, RecyclerView $this_onScrollToBottomIfKeyboardShown) {
        k.e($layoutManager, "$layoutManager");
        k.e($this_onScrollToBottomIfKeyboardShown, "$this_onScrollToBottomIfKeyboardShown");
        View lastItemView = $layoutManager.findViewByPosition($lastItemPosition);
        if (lastItemView != null) {
            $layoutManager.scrollToPositionWithOffset($lastItemPosition, $this_onScrollToBottomIfKeyboardShown.getMeasuredHeight() - lastItemView.getMeasuredHeight());
        }
    }

    /* compiled from: MessageLogView.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
