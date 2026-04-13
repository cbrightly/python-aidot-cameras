package zendesk.ui.android.conversation.composer;

import android.widget.ImageButton;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    public final /* synthetic */ ImageButton c;
    public final /* synthetic */ MessageComposerView d;

    public /* synthetic */ e(ImageButton imageButton, MessageComposerView messageComposerView) {
        this.c = imageButton;
        this.d = messageComposerView;
    }

    public final void run() {
        MessageComposerView.n(this.c, this.d);
    }
}
