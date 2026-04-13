package zendesk.ui.android.conversation.imagerviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import coil.d;
import coil.memory.MemoryCache;
import coil.request.f;
import coil.request.i;
import com.leedarson.bean.Constants;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.conversation.header.ConversationHeaderView;
import zendesk.ui.android.conversation.header.c;

/* compiled from: ImageViewerView.kt */
public final class ImageViewerView extends FrameLayout implements zendesk.ui.android.a<a> {
    @NotNull
    private final ConversationHeaderView c;
    @NotNull
    private final ImageView d;
    /* access modifiers changed from: private */
    @NotNull
    public a f;
    @NotNull
    private final l<zendesk.ui.android.conversation.header.b, zendesk.ui.android.conversation.header.b> q;
    @Nullable
    private f x;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ImageViewerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ImageViewerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ImageViewerView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ImageViewerView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ImageViewerView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.f = new a();
        this.q = new b(this);
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_ConversationHeader, false);
        FrameLayout.inflate(context, R$layout.zuia_view_image_viewer, this);
        View findViewById = findViewById(R$id.zuia_header_view);
        k.d(findViewById, "findViewById(R.id.zuia_header_view)");
        this.c = (ConversationHeaderView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_image_view);
        k.d(findViewById2, "findViewById(R.id.zuia_image_view)");
        this.d = (ImageView) findViewById2;
        a(a.INSTANCE);
    }

    /* compiled from: ImageViewerView.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.header.b, zendesk.ui.android.conversation.header.b> {
        final /* synthetic */ ImageViewerView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ImageViewerView imageViewerView) {
            super(1);
            this.this$0 = imageViewerView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.header.b invoke(@NotNull zendesk.ui.android.conversation.header.b conversationHeaderRendering) {
            k.e(conversationHeaderRendering, "conversationHeaderRendering");
            return conversationHeaderRendering.c().g(new a(this.this$0)).d(new C0570b(this.this$0)).a();
        }

        /* compiled from: ImageViewerView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<c, c> {
            final /* synthetic */ ImageViewerView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ImageViewerView imageViewerView) {
                super(1);
                this.this$0 = imageViewerView;
            }

            @NotNull
            public final c invoke(@NotNull c state) {
                k.e(state, Constants.ACTION_STATE);
                return c.b(state, (String) null, (String) null, (Uri) null, this.this$0.f.b().d(), this.this$0.f.b().c(), 7, (Object) null);
            }
        }

        /* renamed from: zendesk.ui.android.conversation.imagerviewer.ImageViewerView$b$b  reason: collision with other inner class name */
        /* compiled from: ImageViewerView.kt */
        public static final class C0570b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ ImageViewerView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0570b(ImageViewerView imageViewerView) {
                super(0);
                this.this$0 = imageViewerView;
            }

            public final void invoke() {
                this.this$0.f.a().invoke();
            }
        }
    }

    /* compiled from: ImageViewerView.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<a, a> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final a invoke(@NotNull a it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull l<? super a, a> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        this.f = renderingUpdate.invoke(this.f);
        this.c.a(this.q);
        String uri = this.f.b().e();
        if (uri != null) {
            zendesk.ui.android.internal.f fVar = zendesk.ui.android.internal.f.a;
            Context context = getContext();
            k.d(context, "context");
            d imageLoader = fVar.c(context);
            MemoryCache memoryCache = imageLoader.c();
            MemoryCache.Key.a aVar = MemoryCache.Key.c;
            Bitmap cachedBitmap = memoryCache.b(aVar.a(uri));
            if (cachedBitmap != null) {
                this.d.setImageBitmap(cachedBitmap);
                return;
            }
            Context context2 = getContext();
            k.d(context2, "context");
            this.x = imageLoader.a(new i.a(context2).e(uri).j(aVar.a(uri)).w(this.d).b());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        f fVar = this.x;
        if (fVar != null) {
            fVar.dispose();
        }
    }
}
