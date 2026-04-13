package zendesk.messaging.android.internal.conversationscreen;

import android.util.Log;
import androidx.annotation.ColorInt;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.FieldOption;
import zendesk.ui.android.conversation.form.DisplayedField;
import zendesk.ui.android.conversation.form.i;
import zendesk.ui.android.conversation.form.m;
import zendesk.ui.android.conversation.form.p;
import zendesk.ui.android.conversation.form.q;
import zendesk.ui.android.conversation.form.r;
import zendesk.ui.android.conversation.form.u;

/* compiled from: RenderingUpdates.kt */
public final class s {
    @NotNull
    public static final s a = new s();

    private s() {
    }

    /* compiled from: RenderingUpdates.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<p<Field>, p<Field>> {
        final /* synthetic */ Integer $colorAccent;
        final /* synthetic */ List<Field> $fields;
        final /* synthetic */ Map<Integer, DisplayedField> $mapOfDisplayedFields;
        final /* synthetic */ kotlin.jvm.functions.l<List<? extends Field>, x> $onFormCompleted;
        final /* synthetic */ kotlin.jvm.functions.l<DisplayedField, x> $onFormDisplayedFieldsChanged;
        final /* synthetic */ kotlin.jvm.functions.l<Boolean, x> $onFormFocusChanged;
        final /* synthetic */ boolean $pending;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(List<? extends Field> list, kotlin.jvm.functions.l<? super List<? extends Field>, x> lVar, kotlin.jvm.functions.l<? super Boolean, x> lVar2, kotlin.jvm.functions.l<? super DisplayedField, x> lVar3, Map<Integer, DisplayedField> map, Integer num, boolean z) {
            super(1);
            this.$fields = list;
            this.$onFormCompleted = lVar;
            this.$onFormFocusChanged = lVar2;
            this.$onFormDisplayedFieldsChanged = lVar3;
            this.$mapOfDisplayedFields = map;
            this.$colorAccent = num;
            this.$pending = z;
        }

        @NotNull
        public final p<Field> invoke(@NotNull p<Field> it) {
            Iterable $this$mapNotNull$iv;
            Object it$iv$iv;
            k.e(it, "it");
            Log.d("PODO", this.$fields.toString());
            p.e g2 = new p.e().g(new C0541a(this.$colorAccent, this.$pending));
            Iterable<Field> $this$mapNotNull$iv2 = this.$fields;
            ArrayList arrayList = new ArrayList();
            for (Field field : $this$mapNotNull$iv2) {
                if (field instanceof Field.Text) {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    it$iv$iv = new i.c.d(new b(field)).b(new c(field)).a();
                } else {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    if (field instanceof Field.Email) {
                        it$iv$iv = new i.a.d(new d(field)).b(new e(field)).a();
                    } else if (field instanceof Field.Select) {
                        it$iv$iv = new i.b.d(new f(field)).b(new g(field)).a();
                    } else {
                        it$iv$iv = null;
                    }
                }
                if (it$iv$iv != null) {
                    arrayList.add(it$iv$iv);
                }
                p<Field> pVar = it;
                $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            }
            return g2.b(arrayList).d(this.$onFormCompleted).f(this.$onFormFocusChanged).e(this.$onFormDisplayedFieldsChanged).c(this.$mapOfDisplayedFields).a();
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.s$a$a  reason: collision with other inner class name */
        /* compiled from: RenderingUpdates.kt */
        public static final class C0541a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.form.s, zendesk.ui.android.conversation.form.s> {
            final /* synthetic */ Integer $colorAccent;
            final /* synthetic */ boolean $pending;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0541a(Integer num, boolean z) {
                super(1);
                this.$colorAccent = num;
                this.$pending = z;
            }

            @NotNull
            public final zendesk.ui.android.conversation.form.s invoke(@NotNull zendesk.ui.android.conversation.form.s state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(this.$colorAccent, this.$pending);
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<m.c, Field> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final Field invoke(@NotNull m.c state) {
                k.e(state, Constants.ACTION_STATE);
                Field.Text text = (Field.Text) this.$field;
                String h = state.h();
                if (h == null) {
                    h = "";
                }
                return Field.Text.f(text, (String) null, (String) null, (String) null, (String) null, 0, 0, h, 63, (Object) null);
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class c extends l implements kotlin.jvm.functions.l<m.c, m.c> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final m.c invoke(@NotNull m.c it) {
                k.e(it, "it");
                return new m.c.a().d(((Field.Text) this.$field).h()).c(((Field.Text) this.$field).g()).e(this.$field.d()).b(this.$field.b()).f(((Field.Text) this.$field).i()).a();
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class d extends l implements kotlin.jvm.functions.l<m.a, Field> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final Field invoke(@NotNull m.a state) {
                k.e(state, Constants.ACTION_STATE);
                Field.Email email = (Field.Email) this.$field;
                String f = state.f();
                if (f == null) {
                    f = "";
                }
                return Field.Email.f(email, (String) null, (String) null, (String) null, (String) null, f, 15, (Object) null);
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class e extends l implements kotlin.jvm.functions.l<m.a, m.a> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            e(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final m.a invoke(@NotNull m.a it) {
                k.e(it, "it");
                return new m.a.C0566a().c(this.$field.b()).d(this.$field.d()).b(((Field.Email) this.$field).g()).a();
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class f extends l implements kotlin.jvm.functions.l<m.b, Field> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            f(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final Field invoke(@NotNull m.b state) {
                k.e(state, Constants.ACTION_STATE);
                Field field = this.$field;
                Field.Select select = (Field.Select) field;
                Iterable $this$filterTo$iv$iv = ((Field.Select) field).g();
                ArrayList arrayList = new ArrayList();
                for (T next : $this$filterTo$iv$iv) {
                    FieldOption option = (FieldOption) next;
                    Iterable<u> $this$mapTo$iv$iv = state.g();
                    ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (u it : $this$mapTo$iv$iv) {
                        arrayList2.add(it.a());
                    }
                    if (arrayList2.contains(option.b())) {
                        arrayList.add(next);
                    }
                }
                return Field.Select.f(select, (String) null, (String) null, (String) null, (String) null, (List) null, 0, arrayList, 63, (Object) null);
            }
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class g extends l implements kotlin.jvm.functions.l<m.b, m.b> {
            final /* synthetic */ Field $field;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            g(Field field) {
                super(1);
                this.$field = field;
            }

            @NotNull
            public final m.b invoke(@NotNull m.b it) {
                k.e(it, "it");
                m.b.a d = new m.b.a().b(this.$field.b()).d(this.$field.d());
                Iterable<FieldOption> $this$mapTo$iv$iv = ((Field.Select) this.$field).g();
                ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (FieldOption it2 : $this$mapTo$iv$iv) {
                    arrayList.add(new u(it2.b(), it2.a()));
                }
                m.b.a c = d.c(arrayList);
                Iterable<FieldOption> $this$mapTo$iv$iv2 = ((Field.Select) this.$field).h();
                ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
                for (FieldOption it3 : $this$mapTo$iv$iv2) {
                    arrayList2.add(new u(it3.b(), it3.a()));
                }
                return c.e(arrayList2).a();
            }
        }
    }

    @NotNull
    public final kotlin.jvm.functions.l<p<Field>, p<Field>> a(@NotNull List<? extends Field> fields, @NotNull kotlin.jvm.functions.l<? super List<? extends Field>, x> onFormCompleted, @NotNull kotlin.jvm.functions.l<? super Boolean, x> onFormFocusChanged, @Nullable @ColorInt Integer colorAccent, boolean pending, @NotNull kotlin.jvm.functions.l<? super DisplayedField, x> onFormDisplayedFieldsChanged, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields) {
        List<? extends Field> list = fields;
        k.e(fields, "fields");
        k.e(onFormCompleted, "onFormCompleted");
        k.e(onFormFocusChanged, "onFormFocusChanged");
        k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
        k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
        return new a(fields, onFormCompleted, onFormFocusChanged, onFormDisplayedFieldsChanged, mapOfDisplayedFields, colorAccent, pending);
    }

    /* compiled from: RenderingUpdates.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<q, q> {
        final /* synthetic */ List<Field> $fields;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(List<? extends Field> list) {
            super(1);
            this.$fields = list;
        }

        @NotNull
        public final q invoke(@NotNull q it) {
            k.e(it, "it");
            return new q.a().d(new a(this.$fields)).a();
        }

        /* compiled from: RenderingUpdates.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.form.r, zendesk.ui.android.conversation.form.r> {
            final /* synthetic */ List<Field> $fields;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(List<? extends Field> list) {
                super(1);
                this.$fields = list;
            }

            @NotNull
            public final zendesk.ui.android.conversation.form.r invoke(@NotNull zendesk.ui.android.conversation.form.r it) {
                k.e(it, "it");
                r.a aVar = new r.a();
                Iterable<Field> $this$mapTo$iv$iv = this.$fields;
                ArrayList arrayList = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
                for (Field it2 : $this$mapTo$iv$iv) {
                    arrayList.add(t.b(it2));
                }
                return aVar.b(arrayList).a();
            }
        }
    }

    @NotNull
    public final kotlin.jvm.functions.l<q, q> b(@NotNull List<? extends Field> fields) {
        k.e(fields, "fields");
        return new b(fields);
    }
}
