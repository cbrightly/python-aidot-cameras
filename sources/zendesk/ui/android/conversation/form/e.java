package zendesk.ui.android.conversation.form;

import android.view.View;
import android.widget.AdapterView;
import zendesk.ui.android.conversation.form.i;

/* compiled from: lambda */
public final /* synthetic */ class e implements AdapterView.OnItemClickListener {
    public final /* synthetic */ i.b c;
    public final /* synthetic */ FieldView d;

    public /* synthetic */ e(i.b bVar, FieldView fieldView) {
        this.c = bVar;
        this.d = fieldView;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        FieldView.q(this.c, this.d, adapterView, view, i, j);
    }
}
