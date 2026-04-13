package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.R$string;

/* compiled from: MessageLogLabelProvider.kt */
public final class q {
    @NotNull
    private final Context a;

    public q(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    @NotNull
    public final String c() {
        String string = this.a.getString(R$string.zuia_conversation_message_label_new);
        k.d(string, "context.getString(R.stri…sation_message_label_new)");
        return string;
    }

    @NotNull
    public final String b() {
        String string = this.a.getString(R$string.zuia_conversation_message_label_just_now);
        k.d(string, "context.getString(R.stri…n_message_label_just_now)");
        return string;
    }

    @NotNull
    public final String e(@NotNull String timestamp) {
        k.e(timestamp, "timestamp");
        String string = this.a.getString(R$string.zuia_conversation_message_label_sent_absolute, new Object[]{timestamp});
        k.d(string, "context.getString(\n     …,\n        timestamp\n    )");
        return string;
    }

    @NotNull
    public final String f() {
        String string = this.a.getString(R$string.zuia_conversation_message_label_sent_relative);
        k.d(string, "context.getString(R.stri…sage_label_sent_relative)");
        return string;
    }

    @NotNull
    public final String d() {
        String string = this.a.getString(R$string.zuia_conversation_message_label_sending);
        k.d(string, "context.getString(R.stri…on_message_label_sending)");
        return string;
    }

    @NotNull
    public final String g() {
        String string = this.a.getString(R$string.zuia_conversation_message_label_tap_to_retry);
        k.d(string, "context.getString(R.stri…ssage_label_tap_to_retry)");
        return string;
    }

    @NotNull
    public final String a() {
        String string = this.a.getString(R$string.zma_form_submission_error);
        k.d(string, "context.getString(R.stri…ma_form_submission_error)");
        return string;
    }
}
