package zendesk.messaging.android.internal.conversationscreen.messagelog;

import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: MessageLogListeners.kt */
public final class f {
    @NotNull
    private static final l<MessageAction.Reply, x> a = e.INSTANCE;
    @NotNull
    private static final l<b.a, x> b = d.INSTANCE;
    @NotNull
    private static final p<List<? extends Field>, b.a, x> c = a.INSTANCE;
    @NotNull
    private static final kotlin.jvm.functions.a<x> d = C0540f.INSTANCE;
    @NotNull
    private static final l<Boolean, x> e = c.INSTANCE;
    @NotNull
    private static final l<DisplayedField, x> f = b.INSTANCE;

    /* compiled from: MessageLogListeners.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<MessageAction.Reply, x> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((MessageAction.Reply) p1);
            return x.a;
        }

        public final void invoke(@NotNull MessageAction.Reply $noName_0) {
            k.e($noName_0, "$noName_0");
        }
    }

    @NotNull
    public static final l<MessageAction.Reply, x> e() {
        return a;
    }

    /* compiled from: MessageLogListeners.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<b.a, x> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((b.a) p1);
            return x.a;
        }

        public final void invoke(@NotNull b.a $noName_0) {
            k.e($noName_0, "$noName_0");
        }
    }

    @NotNull
    public static final l<b.a, x> d() {
        return b;
    }

    /* compiled from: MessageLogListeners.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<List<? extends Field>, b.a, x> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
            invoke((List<? extends Field>) (List) p1, (b.a) p2);
            return x.a;
        }

        public final void invoke(@NotNull List<? extends Field> $noName_0, @NotNull b.a $noName_1) {
            k.e($noName_0, "$noName_0");
            k.e($noName_1, "$noName_1");
        }
    }

    @NotNull
    public static final p<List<? extends Field>, b.a, x> a() {
        return c;
    }

    /* renamed from: zendesk.messaging.android.internal.conversationscreen.messagelog.f$f  reason: collision with other inner class name */
    /* compiled from: MessageLogListeners.kt */
    public static final class C0540f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        public static final C0540f INSTANCE = new C0540f();

        C0540f() {
            super(0);
        }

        public final void invoke() {
        }
    }

    @NotNull
    public static final kotlin.jvm.functions.a<x> f() {
        return d;
    }

    /* compiled from: MessageLogListeners.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<Boolean, x> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Boolean) p1).booleanValue());
            return x.a;
        }

        public final void invoke(boolean $noName_0) {
        }
    }

    @NotNull
    public static final l<Boolean, x> c() {
        return e;
    }

    /* compiled from: MessageLogListeners.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<DisplayedField, x> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((DisplayedField) p1);
            return x.a;
        }

        public final void invoke(@NotNull DisplayedField $noName_0) {
            k.e($noName_0, "$noName_0");
        }
    }

    @NotNull
    public static final l<DisplayedField, x> b() {
        return f;
    }
}
