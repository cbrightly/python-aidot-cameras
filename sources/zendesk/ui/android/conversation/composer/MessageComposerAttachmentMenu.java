package zendesk.ui.android.conversation.composer;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;

/* compiled from: MessageComposerAttachmentMenu.kt */
public final class MessageComposerAttachmentMenu extends FrameLayout {
    private boolean c = true;
    private boolean d = true;
    @Nullable
    private l<? super Integer, x> f;
    @NotNull
    private final g q = i.b(new a(this));
    @NotNull
    private final g x = i.b(new b(this));

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MessageComposerAttachmentMenu(@NotNull Context context) {
        super(context);
        k.e(context, "context");
        FrameLayout.inflate(context, R$layout.zuia_view_attachment_menu, this);
        getCameraTextView().setOnClickListener(new b(this));
        getGalleryTextView().setOnClickListener(new a(this));
    }

    /* compiled from: MessageComposerAttachmentMenu.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<View> {
        final /* synthetic */ MessageComposerAttachmentMenu this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(MessageComposerAttachmentMenu messageComposerAttachmentMenu) {
            super(0);
            this.this$0 = messageComposerAttachmentMenu;
        }

        public final View invoke() {
            return this.this$0.findViewById(R$id.menu_item_camera);
        }
    }

    /* compiled from: MessageComposerAttachmentMenu.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<View> {
        final /* synthetic */ MessageComposerAttachmentMenu this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(MessageComposerAttachmentMenu messageComposerAttachmentMenu) {
            super(0);
            this.this$0 = messageComposerAttachmentMenu;
        }

        public final View invoke() {
            return this.this$0.findViewById(R$id.menu_item_gallery);
        }
    }

    private final View getCameraTextView() {
        Object value = this.q.getValue();
        k.d(value, "<get-cameraTextView>(...)");
        return (View) value;
    }

    private final View getGalleryTextView() {
        Object value = this.x.getValue();
        k.d(value, "<get-galleryTextView>(...)");
        return (View) value;
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void a(MessageComposerAttachmentMenu this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        l<? super Integer, x> lVar = this$0.f;
        if (lVar != null) {
            lVar.invoke(Integer.valueOf(R$id.menu_item_camera));
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void b(MessageComposerAttachmentMenu this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        l<? super Integer, x> lVar = this$0.f;
        if (lVar != null) {
            lVar.invoke(Integer.valueOf(R$id.menu_item_gallery));
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public final void setOnItemClickListener(@NotNull l<? super Integer, x> listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.f = listener;
    }

    public final void setGallerySupported(boolean gallerySupported) {
        int i;
        this.c = gallerySupported;
        View galleryTextView = getGalleryTextView();
        if (gallerySupported) {
            i = 0;
        } else {
            i = 8;
        }
        galleryTextView.setVisibility(i);
    }

    public final void setCameraSupported(boolean cameraSupported) {
        int i;
        this.d = cameraSupported;
        View cameraTextView = getCameraTextView();
        if (cameraSupported) {
            i = 0;
        } else {
            i = 8;
        }
        cameraTextView.setVisibility(i);
    }
}
