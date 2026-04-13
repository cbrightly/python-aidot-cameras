package zendesk.ui.android.conversation.form;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnFocusChangeListener {
    public final /* synthetic */ FieldView c;

    public /* synthetic */ b(FieldView fieldView) {
        this.c = fieldView;
    }

    public final void onFocusChange(View view, boolean z) {
        FieldView.j(this.c, view, z);
    }
}
