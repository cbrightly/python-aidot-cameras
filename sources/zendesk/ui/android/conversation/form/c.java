package zendesk.ui.android.conversation.form;

import android.view.View;
import zendesk.ui.android.conversation.form.i;

/* compiled from: lambda */
public final /* synthetic */ class c implements View.OnFocusChangeListener {
    public final /* synthetic */ i.c c;
    public final /* synthetic */ FieldView d;

    public /* synthetic */ c(i.c cVar, FieldView fieldView) {
        this.c = cVar;
        this.d = fieldView;
    }

    public final void onFocusChange(View view, boolean z) {
        FieldView.o(this.c, this.d, view, z);
    }
}
