package zendesk.messaging.android.internal.conversationscreen.attachments;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class b implements DialogInterface.OnCancelListener {
    public final /* synthetic */ AttachmentActivity c;

    public /* synthetic */ b(AttachmentActivity attachmentActivity) {
        this.c = attachmentActivity;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        AttachmentActivity.b0(this.c, dialogInterface);
    }
}
