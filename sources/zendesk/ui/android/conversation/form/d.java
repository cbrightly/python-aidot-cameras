package zendesk.ui.android.conversation.form;

import android.view.View;
import zendesk.ui.android.conversation.form.i;

/* compiled from: lambda */
public final /* synthetic */ class d implements View.OnFocusChangeListener {
    public final /* synthetic */ i.a c;
    public final /* synthetic */ FieldView d;

    public /* synthetic */ d(i.a aVar, FieldView fieldView) {
        this.c = aVar;
        this.d = fieldView;
    }

    public final void onFocusChange(View view, boolean z) {
        FieldView.p(this.c, this.d, view, z);
    }
}
