package zendesk.messaging.android.internal.conversationscreen.messagelog;

import android.view.View;
import android.view.ViewTreeObserver;

/* compiled from: lambda */
public final /* synthetic */ class b implements ViewTreeObserver.OnGlobalFocusChangeListener {
    public final /* synthetic */ MessageLogView c;

    public /* synthetic */ b(MessageLogView messageLogView) {
        this.c = messageLogView;
    }

    public final void onGlobalFocusChanged(View view, View view2) {
        MessageLogView.c(this.c, view, view2);
    }
}
