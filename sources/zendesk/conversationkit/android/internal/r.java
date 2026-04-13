package zendesk.conversationkit.android.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.a;
import zendesk.conversationkit.android.c;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.o;
import zendesk.conversationkit.android.internal.s;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.User;
import zendesk.conversationkit.android.model.h;

/* compiled from: EffectProcessor.kt */
public final class r {
    @NotNull
    private final p a;
    @NotNull
    private final b b;

    public r(@NotNull p effectMapper, @NotNull b accessLevelBuilder) {
        k.e(effectMapper, "effectMapper");
        k.e(accessLevelBuilder, "accessLevelBuilder");
        this.a = effectMapper;
        this.b = accessLevelBuilder;
    }

    @NotNull
    public final s a(@NotNull o effect) {
        k.e(effect, "effect");
        List mappedEvents = this.a.a(effect);
        if (k.a(effect, o.i.a)) {
            return new s.b((a) null, (List) null, (List) null, new g.a(c.b.INSTANCE), 7, (DefaultConstructorMarker) null);
        }
        if (effect instanceof o.y) {
            return s((o.y) effect, mappedEvents);
        }
        if (effect instanceof o.x) {
            return r((o.x) effect);
        }
        if (effect instanceof o.w) {
            return q((o.w) effect);
        }
        if (effect instanceof o.d) {
            return d((o.d) effect);
        }
        if (effect instanceof o.g) {
            return f((o.g) effect);
        }
        if (effect instanceof o.j) {
            return h((o.j) effect);
        }
        if (effect instanceof o.b) {
            return b((o.b) effect);
        }
        if (effect instanceof o.k) {
            return i((o.k) effect, mappedEvents);
        }
        if (effect instanceof o.m) {
            return new s.b((a) null, mappedEvents, (List) null, (g) null, 13, (DefaultConstructorMarker) null);
        }
        if (effect instanceof o.c) {
            return c((o.c) effect);
        }
        if (effect instanceof o.u) {
            return o((o.u) effect, mappedEvents);
        }
        if (effect instanceof o.f) {
            return e((o.f) effect);
        }
        if (effect instanceof o.h) {
            return g((o.h) effect, mappedEvents);
        }
        if (effect instanceof o.t) {
            return new s.b((a) null, mappedEvents, (List) null, ((o.t) effect).b(), 5, (DefaultConstructorMarker) null);
        } else if (effect instanceof o.n) {
            return k((o.n) effect, mappedEvents);
        } else {
            if (effect instanceof o.s) {
                return n((o.s) effect, mappedEvents);
            }
            if (effect instanceof o.l) {
                return j((o.l) effect, mappedEvents);
            }
            if (effect instanceof o.v) {
                return p((o.v) effect, mappedEvents);
            }
            if (effect instanceof o.q) {
                return l((o.q) effect, mappedEvents);
            }
            if (effect instanceof o.r) {
                return m(mappedEvents);
            }
            if (!(effect instanceof o.a)) {
                return new s.b((a) null, mappedEvents, (List) null, (g) null, 13, (DefaultConstructorMarker) null);
            }
            return new s.b((a) null, mappedEvents, (List) null, new g.b(((o.a) effect).b()), 5, (DefaultConstructorMarker) null);
        }
    }

    private final s s(o.y effect, List<? extends d> mappedEvents) {
        return new s.b(this.b.a(effect.c(), effect.b()), mappedEvents, (List) null, effect.d(), 4, (DefaultConstructorMarker) null);
    }

    private final s r(o.x effect) {
        return new s.a(this.b.b(effect.b()), (List) null, (List) null, c.f.a, 6, (DefaultConstructorMarker) null);
    }

    private final s q(o.w effect) {
        return new s.a(this.b.b(effect.c()), (List) null, (List) null, new c.e(effect.b()), 6, (DefaultConstructorMarker) null);
    }

    private final s d(o.d effect) {
        g<h> c = effect.c();
        if (c instanceof g.b) {
            return new s.a(this.b.a(effect.b(), (h) ((g.b) effect.c()).a()), (List) null, (List) null, c.b.a, 6, (DefaultConstructorMarker) null);
        }
        if (c instanceof g.a) {
            return new s.b(this.b.c(), (List) null, (List) null, effect.c(), 6, (DefaultConstructorMarker) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final s f(o.g effect) {
        a newAccessLevel;
        List arrayList = new ArrayList();
        if (effect.f() instanceof g.b) {
            User user = (User) ((g.b) effect.f()).a();
            newAccessLevel = this.b.d(effect.d(), effect.c(), user, effect.b());
            List $this$processCreateUserResult_u24lambda_u2d1 = arrayList;
            if (!user.d().isEmpty()) {
                $this$processCreateUserResult_u24lambda_u2d1.add(c.w.a);
            }
            String pendingPushToken = effect.e();
            if (pendingPushToken != null) {
                $this$processCreateUserResult_u24lambda_u2d1.add(new c.y(pendingPushToken));
            }
        } else {
            newAccessLevel = null;
        }
        return new s.b(newAccessLevel, (List) null, arrayList, effect.f(), 2, (DefaultConstructorMarker) null);
    }

    private final s h(o.j effect) {
        g<User> e = effect.e();
        if (e instanceof g.a) {
            return new s.b((a) null, (List) null, (List) null, e, 7, (DefaultConstructorMarker) null);
        }
        if (e instanceof g.b) {
            a d = this.b.d(effect.d(), effect.c(), (User) ((g.b) e).a(), effect.b());
            List arrayList = new ArrayList();
            List $this$processLoginUserResult_u24lambda_u2d2 = arrayList;
            if (!((User) ((g.b) e).a()).d().isEmpty()) {
                $this$processLoginUserResult_u24lambda_u2d2.add(c.w.a);
            }
            return new s.b(d, (List) null, arrayList, e, 2, (DefaultConstructorMarker) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final s.b b(o.b effect) {
        g result = effect.b();
        if (result instanceof g.a) {
            return new s.b((a) null, (List) null, (List) null, result, 7, (DefaultConstructorMarker) null);
        }
        if (result instanceof g.b) {
            return new s.b((a) null, (List) null, (List) null, result, 7, (DefaultConstructorMarker) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final s i(o.k effect, List<? extends d> mappedEvents) {
        g result = effect.d();
        if (result instanceof g.a) {
            return new s.b((a) null, (List) null, (List) null, result, 7, (DefaultConstructorMarker) null);
        } else if (result instanceof g.b) {
            return new s.b(this.b.a(effect.c(), effect.b()), mappedEvents, (List) null, effect.d(), 4, (DefaultConstructorMarker) null);
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    private final s c(o.c effect) {
        a newAccessLevel;
        List supplementaryActions = new ArrayList();
        if (effect.e() != null) {
            newAccessLevel = this.b.d(effect.c(), effect.d().a(), effect.e(), effect.b());
            supplementaryActions.add(new c.m(effect.e()));
            if (!effect.e().d().isEmpty()) {
                supplementaryActions.add(c.w.a);
            }
        } else {
            newAccessLevel = null;
        }
        return new s.b(newAccessLevel, (List) null, supplementaryActions, effect.d(), 2, (DefaultConstructorMarker) null);
    }

    private final s e(o.f effect) {
        List supplementaryActions;
        if (effect.b() instanceof g.b) {
            supplementaryActions = p.b(c.w.a);
        } else {
            supplementaryActions = q.g();
        }
        return new s.b((a) null, (List) null, supplementaryActions, effect.b(), 3, (DefaultConstructorMarker) null);
    }

    private final s g(o.h effect, List<? extends d> mappedEvents) {
        List supplementaryActions;
        if (!(effect.b() instanceof g.b) || !effect.c()) {
            supplementaryActions = q.g();
        } else {
            supplementaryActions = p.b(new c.q(((Conversation) ((g.b) effect.b()).a()).i()));
        }
        return new s.b((a) null, mappedEvents, supplementaryActions, effect.b(), 1, (DefaultConstructorMarker) null);
    }

    private final s k(o.n effect, List<? extends d> mappedEvents) {
        if (effect.a() != a.CONNECTED) {
            return new s.b((a) null, mappedEvents, (List) null, (g) null, 13, (DefaultConstructorMarker) null);
        }
        return new s.a((a) null, mappedEvents, (List) null, c.w.a, 5, (DefaultConstructorMarker) null);
    }

    private final s n(o.s effect, List<? extends d> mappedEvents) {
        if (effect.a() != a.CONNECTED_REALTIME) {
            return new s.b((a) null, mappedEvents, (List) null, (g) null, 13, (DefaultConstructorMarker) null);
        }
        return new s.a((a) null, mappedEvents, (List) null, c.r.a, 5, (DefaultConstructorMarker) null);
    }

    private final s j(o.l effect, List<? extends d> mappedEvents) {
        return new s.a((a) null, mappedEvents, (List) null, new c.t(effect.d(), effect.c()), 5, (DefaultConstructorMarker) null);
    }

    private final s p(o.v effect, List<? extends d> mappedEvents) {
        return new s.b((a) null, mappedEvents, (List) null, effect.e(), 5, (DefaultConstructorMarker) null);
    }

    private final s l(o.q effect, List<? extends d> mappedEvents) {
        return new s.a((a) null, mappedEvents, (List) null, new c.y(effect.b()), 5, (DefaultConstructorMarker) null);
    }

    private final s m(List<? extends d> mappedEvents) {
        return new s.b((a) null, mappedEvents, (List) null, (g) null, 13, (DefaultConstructorMarker) null);
    }

    private final s o(o.u effect, List<? extends d> mappedEvents) {
        List supplementaryActions = new ArrayList();
        String deviceLocale = Locale.getDefault().toLanguageTag();
        if ((effect.b() instanceof g.b) && !k.a(((User) ((g.b) effect.b()).a()).j(), deviceLocale)) {
            k.d(deviceLocale, "deviceLocale");
            supplementaryActions.add(new c.x(deviceLocale));
        }
        return new s.b((a) null, mappedEvents, supplementaryActions, effect.b(), 1, (DefaultConstructorMarker) null);
    }
}
