package zendesk.ui.android.conversation.form;

import androidx.annotation.ColorInt;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.ui.android.conversation.form.i;
import zendesk.ui.android.conversation.form.m;

/* compiled from: FormView.kt */
public final class t {
    /* access modifiers changed from: private */
    public static final <T> i<T> g(i<T> $this$withSelectChangedInterceptor, l<? super List<u>, x> interceptor) {
        if (!($this$withSelectChangedInterceptor instanceof i.b)) {
            return $this$withSelectChangedInterceptor;
        }
        return i.b.d((i.b) $this$withSelectChangedInterceptor, (m.b) null, (l) null, new a(interceptor, $this$withSelectChangedInterceptor), (l) null, (l) null, 27, (Object) null);
    }

    /* compiled from: FormView.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<List<? extends u>, x> {
        final /* synthetic */ l<List<u>, x> $interceptor;
        final /* synthetic */ i<T> $this_withSelectChangedInterceptor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(l<? super List<u>, x> lVar, i<T> iVar) {
            super(1);
            this.$interceptor = lVar;
            this.$this_withSelectChangedInterceptor = iVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((List<u>) (List) p1);
            return x.a;
        }

        public final void invoke(@NotNull List<u> selectOptions) {
            k.e(selectOptions, "selectOptions");
            this.$interceptor.invoke(selectOptions);
            ((i.b) this.$this_withSelectChangedInterceptor).g().invoke(selectOptions);
        }
    }

    /* compiled from: FormView.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<Boolean, x> {
        final /* synthetic */ l<Boolean, x> $onFieldFocusChanged;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(l<? super Boolean, x> lVar) {
            super(1);
            this.$onFieldFocusChanged = lVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Boolean) p1).booleanValue());
            return x.a;
        }

        public final void invoke(boolean it) {
            this.$onFieldFocusChanged.invoke(Boolean.valueOf(it));
        }
    }

    /* access modifiers changed from: private */
    public static final <T> i<T> i(i<T> $this$withStateFocusChanged, l<? super Boolean, x> onFieldFocusChanged) {
        if ($this$withStateFocusChanged instanceof i.c) {
            return i.c.d((i.c) $this$withStateFocusChanged, (m.c) null, (l) null, (l) null, (l) null, new e(onFieldFocusChanged), 15, (Object) null);
        }
        if ($this$withStateFocusChanged instanceof i.a) {
            return i.a.d((i.a) $this$withStateFocusChanged, (m.a) null, (l) null, (l) null, (l) null, new f(onFieldFocusChanged), 15, (Object) null);
        }
        if ($this$withStateFocusChanged instanceof i.b) {
            return i.b.d((i.b) $this$withStateFocusChanged, (m.b) null, (l) null, (l) null, (l) null, new g(onFieldFocusChanged), 15, (Object) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* compiled from: FormView.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<Boolean, x> {
        final /* synthetic */ l<Boolean, x> $onFieldFocusChanged;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(l<? super Boolean, x> lVar) {
            super(1);
            this.$onFieldFocusChanged = lVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Boolean) p1).booleanValue());
            return x.a;
        }

        public final void invoke(boolean it) {
            this.$onFieldFocusChanged.invoke(Boolean.valueOf(it));
        }
    }

    /* compiled from: FormView.kt */
    public static final class g extends kotlin.jvm.internal.l implements l<Boolean, x> {
        final /* synthetic */ l<Boolean, x> $onFieldFocusChanged;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(l<? super Boolean, x> lVar) {
            super(1);
            this.$onFieldFocusChanged = lVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Boolean) p1).booleanValue());
            return x.a;
        }

        public final void invoke(boolean it) {
            this.$onFieldFocusChanged.invoke(Boolean.valueOf(it));
        }
    }

    /* access modifiers changed from: private */
    public static final <T> i<T> h(i<T> $this$withStateChangedInterceptor, int currentIndex, l<? super DisplayedField, x> onFormDisplayedFieldsChanged, l<? super T, x> interceptor) {
        if ($this$withStateChangedInterceptor instanceof i.c) {
            return i.c.d((i.c) $this$withStateChangedInterceptor, (m.c) null, new b(interceptor, $this$withStateChangedInterceptor, onFormDisplayedFieldsChanged, currentIndex), (l) null, (l) null, (l) null, 29, (Object) null);
        }
        if ($this$withStateChangedInterceptor instanceof i.a) {
            return i.a.d((i.a) $this$withStateChangedInterceptor, (m.a) null, new c(interceptor, $this$withStateChangedInterceptor, onFormDisplayedFieldsChanged, currentIndex), (l) null, (l) null, (l) null, 29, (Object) null);
        }
        if ($this$withStateChangedInterceptor instanceof i.b) {
            return i.b.d((i.b) $this$withStateChangedInterceptor, (m.b) null, new d(interceptor, $this$withStateChangedInterceptor, onFormDisplayedFieldsChanged, currentIndex), (l) null, (l) null, (l) null, 29, (Object) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* compiled from: FormView.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<m.c, x> {
        final /* synthetic */ int $currentIndex;
        final /* synthetic */ l<T, x> $interceptor;
        final /* synthetic */ l<DisplayedField, x> $onFormDisplayedFieldsChanged;
        final /* synthetic */ i<T> $this_withStateChangedInterceptor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(l<? super T, x> lVar, i<T> iVar, l<? super DisplayedField, x> lVar2, int i) {
            super(1);
            this.$interceptor = lVar;
            this.$this_withStateChangedInterceptor = iVar;
            this.$onFormDisplayedFieldsChanged = lVar2;
            this.$currentIndex = i;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((m.c) p1);
            return x.a;
        }

        public final void invoke(@NotNull m.c textState) {
            k.e(textState, "textState");
            this.$interceptor.invoke(((i.c) this.$this_withStateChangedInterceptor).e().invoke(textState));
            ((i.c) this.$this_withStateChangedInterceptor).g().invoke(textState);
            this.$onFormDisplayedFieldsChanged.invoke(new DisplayedField(this.$currentIndex, textState.h()));
        }
    }

    /* compiled from: FormView.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<m.a, x> {
        final /* synthetic */ int $currentIndex;
        final /* synthetic */ l<T, x> $interceptor;
        final /* synthetic */ l<DisplayedField, x> $onFormDisplayedFieldsChanged;
        final /* synthetic */ i<T> $this_withStateChangedInterceptor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(l<? super T, x> lVar, i<T> iVar, l<? super DisplayedField, x> lVar2, int i) {
            super(1);
            this.$interceptor = lVar;
            this.$this_withStateChangedInterceptor = iVar;
            this.$onFormDisplayedFieldsChanged = lVar2;
            this.$currentIndex = i;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((m.a) p1);
            return x.a;
        }

        public final void invoke(@NotNull m.a emailState) {
            k.e(emailState, "emailState");
            this.$interceptor.invoke(((i.a) this.$this_withStateChangedInterceptor).e().invoke(emailState));
            ((i.a) this.$this_withStateChangedInterceptor).h().invoke(emailState);
            this.$onFormDisplayedFieldsChanged.invoke(new DisplayedField(this.$currentIndex, emailState.f()));
        }
    }

    /* compiled from: FormView.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<m.b, x> {
        final /* synthetic */ int $currentIndex;
        final /* synthetic */ l<T, x> $interceptor;
        final /* synthetic */ l<DisplayedField, x> $onFormDisplayedFieldsChanged;
        final /* synthetic */ i<T> $this_withStateChangedInterceptor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l<? super T, x> lVar, i<T> iVar, l<? super DisplayedField, x> lVar2, int i) {
            super(1);
            this.$interceptor = lVar;
            this.$this_withStateChangedInterceptor = iVar;
            this.$onFormDisplayedFieldsChanged = lVar2;
            this.$currentIndex = i;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((m.b) p1);
            return x.a;
        }

        public final void invoke(@NotNull m.b selectState) {
            k.e(selectState, "selectState");
            this.$interceptor.invoke(((i.b) this.$this_withStateChangedInterceptor).e().invoke(selectState));
            ((i.b) this.$this_withStateChangedInterceptor).h().invoke(selectState);
            this.$onFormDisplayedFieldsChanged.invoke(new DisplayedField(this.$currentIndex, ((u) y.S(selectState.g())).a()));
        }
    }

    /* access modifiers changed from: private */
    public static final <T> i<T> f(i<T> $this$withBorderColorOverride, @ColorInt Integer borderColor) {
        if (borderColor == null) {
            return $this$withBorderColorOverride;
        }
        borderColor.intValue();
        Integer a2 = $this$withBorderColorOverride.b().a();
        if (a2 != null) {
            int intValue = a2.intValue();
            return $this$withBorderColorOverride;
        } else if ($this$withBorderColorOverride instanceof i.c) {
            return i.c.d((i.c) $this$withBorderColorOverride, m.c.e(((i.c) $this$withBorderColorOverride).b(), (String) null, 0, 0, (String) null, (String) null, borderColor, 31, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
        } else {
            if ($this$withBorderColorOverride instanceof i.a) {
                return i.a.d((i.a) $this$withBorderColorOverride, m.a.e(((i.a) $this$withBorderColorOverride).b(), (String) null, (String) null, (String) null, borderColor, 7, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            }
            if ($this$withBorderColorOverride instanceof i.b) {
                return i.b.d((i.b) $this$withBorderColorOverride, m.b.e(((i.b) $this$withBorderColorOverride).b(), (List) null, (List) null, (String) null, (String) null, borderColor, 15, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    /* access modifiers changed from: private */
    public static final <T> i<T> j(i<T> $this$withStateInputCached, DisplayedField displayedField, l<? super T, x> interceptor) {
        i<T> iVar = $this$withStateInputCached;
        l<? super T, x> lVar = interceptor;
        if ((displayedField == null ? null : displayedField.b()) == null) {
            return iVar;
        }
        if (iVar instanceof i.c) {
            i.c text = i.c.d((i.c) iVar, m.c.e(((i.c) iVar).b(), displayedField.b(), 0, 0, (String) null, (String) null, (Integer) null, 62, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            lVar.invoke(((i.c) iVar).e().invoke(text.b()));
            return text;
        } else if (iVar instanceof i.a) {
            i.a email = i.a.d((i.a) iVar, m.a.e(((i.a) iVar).b(), displayedField.b(), (String) null, (String) null, (Integer) null, 14, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            lVar.invoke(((i.a) iVar).e().invoke(email.b()));
            return email;
        } else if (iVar instanceof i.b) {
            i.b bVar = (i.b) iVar;
            m.b i = ((i.b) iVar).b();
            Iterable $this$filterTo$iv$iv = ((i.b) iVar).b().f();
            ArrayList arrayList = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                if (k.a(((u) next).a(), displayedField.b())) {
                    arrayList.add(next);
                }
            }
            i.b select = i.b.d(bVar, m.b.e(i, (List) null, arrayList, (String) null, (String) null, (Integer) null, 29, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            lVar.invoke(((i.b) iVar).e().invoke(select.b()));
            return select;
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }
}
