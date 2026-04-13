package zendesk.messaging.android.internal.conversationscreen.messagelog;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class d implements View.OnLayoutChangeListener {
    public final /* synthetic */ MessageLogView c;

    public /* synthetic */ d(MessageLogView messageLogView) {
        this.c = messageLogView;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        MessageLogView.b(this.c, view, i, i2, i3, i4, i5, i6, i7, i8);
    }
}
