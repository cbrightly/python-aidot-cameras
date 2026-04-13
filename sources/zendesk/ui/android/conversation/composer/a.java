package zendesk.ui.android.conversation.composer;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ MessageComposerAttachmentMenu c;

    public /* synthetic */ a(MessageComposerAttachmentMenu messageComposerAttachmentMenu) {
        this.c = messageComposerAttachmentMenu;
    }

    public final void onClick(View view) {
        MessageComposerAttachmentMenu.b(this.c, view);
    }
}
