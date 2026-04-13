package zendesk.messaging.android.internal.conversationscreen;

import android.content.Intent;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.android.internal.f;

/* compiled from: ImageViewerActivity.kt */
public final class l {
    static final /* synthetic */ k<Object>[] a;
    @NotNull
    private static final f.a b = new f.a("INTENT_URI");
    @NotNull
    private static final f.a c = new f.a("INTENT_CREDENTIALS");

    static {
        Class<l> cls = l.class;
        a = new k[]{a0.f(new o(cls, "uri", "getUri(Landroid/content/Intent;)Ljava/lang/String;", 1)), a0.f(new o(cls, "credentials", "getCredentials(Landroid/content/Intent;)Ljava/lang/String;", 1))};
    }

    /* access modifiers changed from: private */
    public static final String f(Intent $this$uri) {
        return b.b($this$uri, a[0]);
    }

    /* access modifiers changed from: private */
    public static final void h(Intent $this$uri, String str) {
        b.a($this$uri, a[0], str);
    }

    /* access modifiers changed from: private */
    public static final String e(Intent $this$credentials) {
        return c.b($this$credentials, a[1]);
    }

    /* access modifiers changed from: private */
    public static final void g(Intent $this$credentials, String str) {
        c.a($this$credentials, a[1], str);
    }
}
