package zendesk.ui.android.conversation.composer;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ MessageComposerAttachmentMenu c;

    public /* synthetic */ b(MessageComposerAttachmentMenu messageComposerAttachmentMenu) {
        this.c = messageComposerAttachmentMenu;
    }

    public final void onClick(View view) {
        MessageComposerAttachmentMenu.a(this.c, view);
    }
}
