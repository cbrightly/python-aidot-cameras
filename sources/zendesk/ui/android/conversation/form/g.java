package zendesk.ui.android.conversation.form;

import android.view.KeyEvent;
import android.widget.TextView;

/* compiled from: lambda */
public final /* synthetic */ class g implements TextView.OnEditorActionListener {
    public final /* synthetic */ FormView c;

    public /* synthetic */ g(FormView formView) {
        this.c = formView;
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return FormView.r(this.c, textView, i, keyEvent);
    }
}
