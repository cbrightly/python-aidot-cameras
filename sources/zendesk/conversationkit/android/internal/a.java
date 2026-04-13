package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.User;

/* compiled from: AccessLevel.kt */
public abstract class a {
    @NotNull
    private final String a;

    public /* synthetic */ a(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private a(String logName) {
        this.a = logName;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @Nullable
    public final User a() {
        d0 d0Var = this instanceof d0 ? (d0) this : null;
        if (d0Var == null) {
            return null;
        }
        return d0Var.c().K();
    }
}
