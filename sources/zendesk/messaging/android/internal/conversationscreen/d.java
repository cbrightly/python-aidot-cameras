package zendesk.messaging.android.internal.conversationscreen;

import android.content.Intent;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.android.internal.f;

/* compiled from: ConversationActivity.kt */
public final class d {
    static final /* synthetic */ k<Object>[] a = {a0.f(new o(d.class, "credentials", "getCredentials(Landroid/content/Intent;)Ljava/lang/String;", 1))};
    @NotNull
    private static final f.a b = new f.a("CREDENTIALS");

    /* access modifiers changed from: private */
    public static final String c(Intent $this$credentials) {
        return b.b($this$credentials, a[0]);
    }

    /* access modifiers changed from: private */
    public static final void d(Intent $this$credentials, String str) {
        b.a($this$credentials, a[0], str);
    }
}
