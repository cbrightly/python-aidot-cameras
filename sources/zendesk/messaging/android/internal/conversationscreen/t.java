package zendesk.messaging.android.internal.conversationscreen;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.y;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.FieldOption;
import zendesk.ui.android.conversation.form.j;

/* compiled from: RenderingUpdates.kt */
public final class t {
    /* access modifiers changed from: private */
    public static final j b(Field $this$toFieldResponseState) {
        String str;
        String b = $this$toFieldResponseState.b();
        if ($this$toFieldResponseState instanceof Field.Text) {
            str = ((Field.Text) $this$toFieldResponseState).i();
        } else if ($this$toFieldResponseState instanceof Field.Email) {
            str = ((Field.Email) $this$toFieldResponseState).g();
        } else if ($this$toFieldResponseState instanceof Field.Select) {
            FieldOption fieldOption = (FieldOption) y.U(((Field.Select) $this$toFieldResponseState).h());
            str = fieldOption == null ? null : fieldOption.a();
            if (str == null) {
                str = "";
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return new j(b, str);
    }
}
