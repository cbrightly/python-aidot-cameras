package zendesk.ui.android.internal;

import kotlin.text.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: Patterns.kt */
public final class g {
    @NotNull
    public static final g a = new g();
    @NotNull
    private static final j b = new j("((\"[^\"\\f\\n\\r\\t\\cK\b]+\")|([.\\w=!#$%&'*+\\-~/^`|{}]+))@((\\[(((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9])))])|(((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9]))\\.((25[0-5])|(2[0-4][0-9])|([0-1]?[0-9]?[0-9])))|((([A-Za-z0-9\\-])+\\.)+[A-Za-z\\-]+))");

    private g() {
    }

    @NotNull
    public final j a() {
        return b;
    }
}
