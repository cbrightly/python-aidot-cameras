package zendesk.ui.android.conversation.form;

import android.view.View;
import zendesk.ui.android.conversation.form.i;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnFocusChangeListener {
    public final /* synthetic */ i.b c;
    public final /* synthetic */ FieldView d;

    public /* synthetic */ a(i.b bVar, FieldView fieldView) {
        this.c = bVar;
        this.d = fieldView;
    }

    public final void onFocusChange(View view, boolean z) {
        FieldView.r(this.c, this.d, view, z);
    }
}
