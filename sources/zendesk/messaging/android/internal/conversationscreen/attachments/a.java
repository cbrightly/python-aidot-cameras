package zendesk.messaging.android.internal.conversationscreen.attachments;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ AttachmentActivity c;
    public final /* synthetic */ BottomSheetDialog d;

    public /* synthetic */ a(AttachmentActivity attachmentActivity, BottomSheetDialog bottomSheetDialog) {
        this.c = attachmentActivity;
        this.d = bottomSheetDialog;
    }

    public final void onClick(View view) {
        AttachmentActivity.a0(this.c, this.d, view);
    }
}
