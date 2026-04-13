package zendesk.messaging.android.internal.conversationscreen.messagelog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ LinearLayoutManager c;
    public final /* synthetic */ int d;
    public final /* synthetic */ RecyclerView f;

    public /* synthetic */ c(LinearLayoutManager linearLayoutManager, int i, RecyclerView recyclerView) {
        this.c = linearLayoutManager;
        this.d = i;
        this.f = recyclerView;
    }

    public final void run() {
        MessageLogView.j(this.c, this.d, this.f);
    }
}
