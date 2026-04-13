package zendesk.conversationkit.android.internal.user;

import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import okhttp3.o;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.User;
import zendesk.conversationkit.android.model.f;

/* compiled from: UserExtensions.kt */
public final class b {
    @NotNull
    public static final String a(@NotNull User $this$authorization) {
        k.e($this$authorization, "<this>");
        f authenticationType = $this$authorization.c();
        if (authenticationType instanceof f.a) {
            return k.l("Bearer ", ((f.a) authenticationType).a());
        }
        if (authenticationType instanceof f.b) {
            return o.c($this$authorization.h(), ((f.b) authenticationType).a(), (Charset) null, 4, (Object) null);
        }
        return "";
    }
}
