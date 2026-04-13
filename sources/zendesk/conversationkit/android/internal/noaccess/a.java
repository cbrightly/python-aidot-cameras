package zendesk.conversationkit.android.internal.noaccess;

import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.e;
import zendesk.conversationkit.android.internal.o;

/* compiled from: NoAccessActionProcessor.kt */
public final class a implements e {
    @NotNull
    public static final C0511a a = new C0511a((DefaultConstructorMarker) null);

    @Nullable
    public Object a(@NotNull c action, @NotNull d<? super o> $completion) {
        if (action instanceof c.u) {
            return new o.x(((c.u) action).a());
        }
        if (action instanceof c.v) {
            return new o.w(((c.v) action).b(), ((c.v) action).a());
        }
        zendesk.logger.a.h("NoAccessActionProcessor", action + " cannot processed.", new Object[0]);
        return o.i.a;
    }

    /* renamed from: zendesk.conversationkit.android.internal.noaccess.a$a  reason: collision with other inner class name */
    /* compiled from: NoAccessActionProcessor.kt */
    public static final class C0511a {
        public /* synthetic */ C0511a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0511a() {
        }
    }
}
