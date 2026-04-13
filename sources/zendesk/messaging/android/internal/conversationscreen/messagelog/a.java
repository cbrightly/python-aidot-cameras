package zendesk.messaging.android.internal.conversationscreen.messagelog;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ MessageLogView c;

    public /* synthetic */ a(MessageLogView messageLogView) {
        this.c = messageLogView;
    }

    public final void run() {
        MessageLogView.k(this.c);
    }
}
