package zendesk.ui.android.conversation.form;

import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormRendering.kt */
public final class p<T> {
    @NotNull
    private final s a;
    @NotNull
    private final List<i<T>> b;
    @NotNull
    private final l<List<? extends T>, x> c;
    @NotNull
    private final l<List<? extends T>, x> d;
    @NotNull
    private final l<Boolean, x> e;
    @NotNull
    private final l<DisplayedField, x> f;
    @NotNull
    private final Map<Integer, DisplayedField> g;

    public p() {
        this((s) null, (List) null, (l) null, (l) null, (l) null, (l) null, (Map) null, NeedPermissionEvent.PER_IPC_SPEAK_PERM, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ p b(p pVar, s sVar, List<i<T>> list, l<List<? extends T>, x> lVar, l<List<? extends T>, x> lVar2, l<Boolean, x> lVar3, l<DisplayedField, x> lVar4, Map<Integer, DisplayedField> map, int i, Object obj) {
        if ((i & 1) != 0) {
            sVar = pVar.a;
        }
        if ((i & 2) != 0) {
            list = pVar.b;
        }
        List<i<T>> list2 = list;
        if ((i & 4) != 0) {
            lVar = pVar.c;
        }
        l<List<? extends T>, x> lVar5 = lVar;
        if ((i & 8) != 0) {
            lVar2 = pVar.d;
        }
        l<List<? extends T>, x> lVar6 = lVar2;
        if ((i & 16) != 0) {
            lVar3 = pVar.e;
        }
        l<Boolean, x> lVar7 = lVar3;
        if ((i & 32) != 0) {
            lVar4 = pVar.f;
        }
        l<DisplayedField, x> lVar8 = lVar4;
        if ((i & 64) != 0) {
            map = pVar.g;
        }
        return pVar.a(sVar, list2, lVar5, lVar6, lVar7, lVar8, map);
    }

    @NotNull
    public final p<T> a(@NotNull s sVar, @NotNull List<? extends i<T>> list, @NotNull l<? super List<? extends T>, x> lVar, @NotNull l<? super List<? extends T>, x> lVar2, @NotNull l<? super Boolean, x> lVar3, @NotNull l<? super DisplayedField, x> lVar4, @NotNull Map<Integer, DisplayedField> map) {
        k.e(sVar, Constants.ACTION_STATE);
        k.e(list, "fieldRenderings");
        k.e(lVar, "onFormCompleted");
        k.e(lVar2, "onFormChanged");
        k.e(lVar3, "onFormFocusChanged");
        k.e(lVar4, "onFormDisplayedFieldsChanged");
        k.e(map, "mapOfDisplayedFields");
        return new p(sVar, list, lVar, lVar2, lVar3, lVar4, map);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        return k.a(this.a, pVar.a) && k.a(this.b, pVar.b) && k.a(this.c, pVar.c) && k.a(this.d, pVar.d) && k.a(this.e, pVar.e) && k.a(this.f, pVar.f) && k.a(this.g, pVar.g);
    }

    public int hashCode() {
        return (((((((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode();
    }

    @NotNull
    public String toString() {
        return "FormRendering(state=" + this.a + ", fieldRenderings=" + this.b + ", onFormCompleted=" + this.c + ", onFormChanged=" + this.d + ", onFormFocusChanged=" + this.e + ", onFormDisplayedFieldsChanged=" + this.f + ", mapOfDisplayedFields=" + this.g + ')';
    }

    public p(@NotNull s state, @NotNull List<? extends i<T>> fieldRenderings, @NotNull l<? super List<? extends T>, x> onFormCompleted, @NotNull l<? super List<? extends T>, x> onFormChanged, @NotNull l<? super Boolean, x> onFormFocusChanged, @NotNull l<? super DisplayedField, x> onFormDisplayedFieldsChanged, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields) {
        k.e(state, Constants.ACTION_STATE);
        k.e(fieldRenderings, "fieldRenderings");
        k.e(onFormCompleted, "onFormCompleted");
        k.e(onFormChanged, "onFormChanged");
        k.e(onFormFocusChanged, "onFormFocusChanged");
        k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
        k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
        this.a = state;
        this.b = fieldRenderings;
        this.c = onFormCompleted;
        this.d = onFormChanged;
        this.e = onFormFocusChanged;
        this.f = onFormDisplayedFieldsChanged;
        this.g = mapOfDisplayedFields;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ p(zendesk.ui.android.conversation.form.s r8, java.util.List r9, kotlin.jvm.functions.l r10, kotlin.jvm.functions.l r11, kotlin.jvm.functions.l r12, kotlin.jvm.functions.l r13, java.util.Map r14, int r15, kotlin.jvm.internal.DefaultConstructorMarker r16) {
        /*
            r7 = this;
            r0 = r15 & 1
            if (r0 == 0) goto L_0x000d
            zendesk.ui.android.conversation.form.s r0 = new zendesk.ui.android.conversation.form.s
            r1 = 0
            r2 = 3
            r3 = 0
            r0.<init>(r3, r1, r2, r3)
            goto L_0x000e
        L_0x000d:
            r0 = r8
        L_0x000e:
            r1 = r15 & 2
            if (r1 == 0) goto L_0x0017
            java.util.List r1 = kotlin.collections.q.g()
            goto L_0x0018
        L_0x0017:
            r1 = r9
        L_0x0018:
            r2 = r15 & 4
            if (r2 == 0) goto L_0x001f
            zendesk.ui.android.conversation.form.p$a r2 = zendesk.ui.android.conversation.form.p.a.INSTANCE
            goto L_0x0020
        L_0x001f:
            r2 = r10
        L_0x0020:
            r3 = r15 & 8
            if (r3 == 0) goto L_0x0027
            zendesk.ui.android.conversation.form.p$b r3 = zendesk.ui.android.conversation.form.p.b.INSTANCE
            goto L_0x0028
        L_0x0027:
            r3 = r11
        L_0x0028:
            r4 = r15 & 16
            if (r4 == 0) goto L_0x002f
            zendesk.ui.android.conversation.form.p$c r4 = zendesk.ui.android.conversation.form.p.c.INSTANCE
            goto L_0x0030
        L_0x002f:
            r4 = r12
        L_0x0030:
            r5 = r15 & 32
            if (r5 == 0) goto L_0x0037
            zendesk.ui.android.conversation.form.p$d r5 = zendesk.ui.android.conversation.form.p.d.INSTANCE
            goto L_0x0038
        L_0x0037:
            r5 = r13
        L_0x0038:
            r6 = r15 & 64
            if (r6 == 0) goto L_0x0042
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            goto L_0x0043
        L_0x0042:
            r6 = r14
        L_0x0043:
            r8 = r7
            r9 = r0
            r10 = r1
            r11 = r2
            r12 = r3
            r13 = r4
            r14 = r5
            r15 = r6
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.p.<init>(zendesk.ui.android.conversation.form.s, java.util.List, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, java.util.Map, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final s i() {
        return this.a;
    }

    /* compiled from: FormRendering.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<List<? extends T>, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((List) p1);
            return x.a;
        }

        public final void invoke(@NotNull List<? extends T> it) {
            k.e(it, "it");
        }
    }

    @NotNull
    public final List<i<T>> c() {
        return this.b;
    }

    /* compiled from: FormRendering.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<List<? extends T>, x> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((List) p1);
            return x.a;
        }

        public final void invoke(@NotNull List<? extends T> it) {
            k.e(it, "it");
        }
    }

    @NotNull
    public final l<List<? extends T>, x> f() {
        return this.c;
    }

    /* compiled from: FormRendering.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<Boolean, x> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Boolean) p1).booleanValue());
            return x.a;
        }

        public final void invoke(boolean it) {
        }
    }

    @NotNull
    public final l<List<? extends T>, x> e() {
        return this.d;
    }

    /* compiled from: FormRendering.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<DisplayedField, x> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((DisplayedField) p1);
            return x.a;
        }

        public final void invoke(@NotNull DisplayedField it) {
            k.e(it, "it");
        }
    }

    @NotNull
    public final l<Boolean, x> h() {
        return this.e;
    }

    @NotNull
    public final l<DisplayedField, x> g() {
        return this.f;
    }

    @NotNull
    public final Map<Integer, DisplayedField> d() {
        return this.g;
    }

    /* compiled from: FormRendering.kt */
    public static final class e<T> {
        @NotNull
        private p<T> a = new p((s) null, (List) null, (l) null, (l) null, (l) null, (l) null, (Map) null, NeedPermissionEvent.PER_IPC_SPEAK_PERM, (DefaultConstructorMarker) null);

        @NotNull
        public final e<T> g(@NotNull l<? super s, s> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            p<T> pVar = this.a;
            this.a = p.b(pVar, stateUpdate.invoke(pVar.i()), (List) null, (l) null, (l) null, (l) null, (l) null, (Map) null, 126, (Object) null);
            return this;
        }

        @NotNull
        public final e<T> b(@NotNull List<? extends i<T>> fieldRenderings) {
            k.e(fieldRenderings, "fieldRenderings");
            this.a = p.b(this.a, (s) null, y.C0(fieldRenderings), (l) null, (l) null, (l) null, (l) null, (Map) null, 125, (Object) null);
            return this;
        }

        @NotNull
        public final e<T> d(@NotNull l<? super List<? extends T>, x> onFormCompleted) {
            k.e(onFormCompleted, "onFormCompleted");
            this.a = p.b(this.a, (s) null, (List) null, onFormCompleted, (l) null, (l) null, (l) null, (Map) null, 123, (Object) null);
            return this;
        }

        @NotNull
        public final e<T> f(@NotNull l<? super Boolean, x> onFormFocusChanged) {
            k.e(onFormFocusChanged, "onFormFocusChanged");
            this.a = p.b(this.a, (s) null, (List) null, (l) null, (l) null, onFormFocusChanged, (l) null, (Map) null, 111, (Object) null);
            return this;
        }

        @NotNull
        public final e<T> e(@NotNull l<? super DisplayedField, x> onFormDisplayedFieldsChanged) {
            k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
            this.a = p.b(this.a, (s) null, (List) null, (l) null, (l) null, (l) null, onFormDisplayedFieldsChanged, (Map) null, 95, (Object) null);
            return this;
        }

        @NotNull
        public final e<T> c(@NotNull Map<Integer, DisplayedField> mapOfDisplayedFields) {
            k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
            this.a = p.b(this.a, (s) null, (List) null, (l) null, (l) null, (l) null, (l) null, mapOfDisplayedFields, 63, (Object) null);
            return this;
        }

        @NotNull
        public final p<T> a() {
            return this.a;
        }
    }
}
