package zendesk.ui.android.conversation.composer;

import android.widget.ImageButton;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ ImageButton c;
    public final /* synthetic */ MessageComposerView d;

    public /* synthetic */ d(ImageButton imageButton, MessageComposerView messageComposerView) {
        this.c = imageButton;
        this.d = messageComposerView;
    }

    public final void run() {
        MessageComposerView.p(this.c, this.d);
    }
}
