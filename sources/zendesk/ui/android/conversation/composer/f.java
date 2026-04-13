package zendesk.ui.android.conversation.composer;

import android.widget.ImageButton;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ ImageButton c;

    public /* synthetic */ f(ImageButton imageButton) {
        this.c = imageButton;
    }

    public final void run() {
        MessageComposerView.m(this.c);
    }
}
