package zendesk.ui.android.conversation.form;

import android.view.KeyEvent;
import android.widget.TextView;
import kotlin.jvm.functions.a;

/* compiled from: lambda */
public final /* synthetic */ class h implements TextView.OnEditorActionListener {
    public final /* synthetic */ FormView c;
    public final /* synthetic */ a d;

    public /* synthetic */ h(FormView formView, a aVar) {
        this.c = formView;
        this.d = aVar;
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return FormView.o(this.c, this.d, textView, i, keyEvent);
    }
}
