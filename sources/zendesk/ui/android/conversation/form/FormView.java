package zendesk.ui.android.conversation.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$string;

/* compiled from: FormView.kt */
public final class FormView<T> extends FrameLayout implements zendesk.ui.android.a<p<T>> {
    /* access modifiers changed from: private */
    @NotNull
    public p<T> c;
    @NotNull
    private final FormButtonView d;
    @NotNull
    private final LinearLayout f;
    /* access modifiers changed from: private */
    @NotNull
    public final List<T> q;
    /* access modifiers changed from: private */
    @NotNull
    public final List<FieldView> x;
    @NotNull
    private final TextView y;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FormView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FormView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.c = new p((s) null, (List) null, (l) null, (l) null, (l) null, (l) null, (Map) null, NeedPermissionEvent.PER_IPC_SPEAK_PERM, (DefaultConstructorMarker) null);
        this.q = new ArrayList();
        this.x = new ArrayList();
        FrameLayout.inflate(context, R$layout.zuia_view_form, this);
        View findViewById = findViewById(R$id.zuia_form_fields_container);
        k.d(findViewById, "findViewById(R.id.zuia_form_fields_container)");
        this.f = (LinearLayout) findViewById;
        View findViewById2 = findViewById(R$id.zuia_submit_button);
        k.d(findViewById2, "findViewById(R.id.zuia_submit_button)");
        this.d = (FormButtonView) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_form_layout);
        k.d(findViewById3, "findViewById<ViewGroup>(R.id.zuia_form_layout)");
        zendesk.ui.android.internal.l.g(findViewById3, 0, 0.0f, 3, (Object) null);
        View findViewById4 = findViewById(R$id.zuia_form_field_counter_label);
        k.d(findViewById4, "findViewById(R.id.zuia_form_field_counter_label)");
        this.y = (TextView) findViewById4;
    }

    public void a(@NotNull l<? super p<T>, p<T>> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        this.c = renderingUpdate.invoke(this.c);
        this.d.a(new d(this));
        this.f.removeAllViews();
        this.x.clear();
        this.q.clear();
        List<T> list = this.q;
        Iterable<i> $this$mapTo$iv$iv = this.c.c();
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (i it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(it.a());
        }
        list.addAll(destination$iv$iv);
        if (this.c.d().isEmpty()) {
            i(this, 0, (DisplayedField) null, this.c.c().size(), 2, (Object) null);
            return;
        }
        for (Map.Entry field : this.c.d().entrySet()) {
            h(((DisplayedField) field.getValue()).a(), (DisplayedField) field.getValue(), this.c.c().size());
        }
        for (FieldView fieldView : this.x) {
            FieldView.A(fieldView, false, 1, (Object) null);
        }
    }

    /* compiled from: FormView.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<n, n> {
        final /* synthetic */ FormView<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(FormView<T> formView) {
            super(1);
            this.this$0 = formView;
        }

        @NotNull
        public final n invoke(@NotNull n formButtonRendering) {
            k.e(formButtonRendering, "formButtonRendering");
            return formButtonRendering.c().g(new a(this.this$0)).a();
        }

        /* compiled from: FormView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<o, o> {
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(FormView<T> formView) {
                super(1);
                this.this$0 = formView;
            }

            @NotNull
            public final o invoke(@NotNull o state) {
                k.e(state, Constants.ACTION_STATE);
                boolean c = this.this$0.c.i().c();
                Integer b = this.this$0.c.i().b();
                String string = this.this$0.getResources().getString(R$string.zuia_form_next_button);
                k.d(string, "getString(R.string.zuia_form_next_button)");
                return state.a(string, c, b);
            }
        }
    }

    static /* synthetic */ void i(FormView formView, int i, DisplayedField displayedField, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            displayedField = null;
        }
        formView.h(i, displayedField, i2);
    }

    private final void h(int currentIndex, DisplayedField displayedField, int numberOfFields) {
        if (y.V(this.x, currentIndex) == null && currentIndex < numberOfFields) {
            int nextIndex = currentIndex + 1;
            boolean isLastField = currentIndex == numberOfFields + -1;
            LinearLayout linearLayout = this.f;
            Context context = getContext();
            k.d(context, "context");
            FieldView $this$addFieldView_u24lambda_u2d3 = new FieldView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
            $this$addFieldView_u24lambda_u2d3.a(new a(this, currentIndex, displayedField, nextIndex));
            this.x.add($this$addFieldView_u24lambda_u2d3);
            x xVar = x.a;
            LinearLayout.LayoutParams $this$addFieldView_u24lambda_u2d4 = new LinearLayout.LayoutParams(-1, -2);
            $this$addFieldView_u24lambda_u2d4.bottomMargin = getResources().getDimensionPixelSize(R$dimen.zuia_vertical_spacing_xlarge);
            linearLayout.addView($this$addFieldView_u24lambda_u2d3, $this$addFieldView_u24lambda_u2d4);
            n(currentIndex, new b(this, nextIndex, numberOfFields));
            q(isLastField);
            s(currentIndex, numberOfFields);
            t(currentIndex);
        }
    }

    /* compiled from: FormView.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<i<?>, i<?>> {
        final /* synthetic */ int $currentIndex;
        final /* synthetic */ DisplayedField $displayedField;
        final /* synthetic */ int $nextIndex;
        final /* synthetic */ FormView<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(FormView<T> formView, int i, DisplayedField displayedField, int i2) {
            super(1);
            this.this$0 = formView;
            this.$currentIndex = i;
            this.$displayedField = displayedField;
            this.$nextIndex = i2;
        }

        @NotNull
        public final i<?> invoke(@NotNull i<?> it) {
            k.e(it, "it");
            return t.j(t.i(t.g(t.h(t.f((i) this.this$0.c.c().get(this.$currentIndex), this.this$0.c.i().b()), this.$currentIndex, this.this$0.c.g(), new C0562a(this.this$0, this.$currentIndex)), new b(this.this$0, this.$nextIndex)), this.this$0.c.h()), this.$displayedField, new c(this.this$0, this.$currentIndex));
        }

        /* renamed from: zendesk.ui.android.conversation.form.FormView$a$a  reason: collision with other inner class name */
        /* compiled from: FormView.kt */
        public static final class C0562a extends kotlin.jvm.internal.l implements l<T, x> {
            final /* synthetic */ int $currentIndex;
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0562a(FormView<T> formView, int i) {
                super(1);
                this.this$0 = formView;
                this.$currentIndex = i;
            }

            public final void invoke(T fieldState) {
                this.this$0.q.set(this.$currentIndex, fieldState);
                this.this$0.c.e().invoke(this.this$0.q);
            }
        }

        /* compiled from: FormView.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<List<? extends u>, x> {
            final /* synthetic */ int $nextIndex;
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(FormView<T> formView, int i) {
                super(1);
                this.this$0 = formView;
                this.$nextIndex = i;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((List<u>) (List) p1);
                return x.a;
            }

            public final void invoke(@NotNull List<u> it) {
                k.e(it, "it");
                FieldView fieldView = (FieldView) y.V(this.this$0.x, this.$nextIndex);
                if (fieldView != null) {
                    this.this$0.p(fieldView);
                }
            }
        }

        /* compiled from: FormView.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<T, x> {
            final /* synthetic */ int $currentIndex;
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(FormView<T> formView, int i) {
                super(1);
                this.this$0 = formView;
                this.$currentIndex = i;
            }

            public final void invoke(T fieldState) {
                this.this$0.q.set(this.$currentIndex, fieldState);
            }
        }
    }

    /* compiled from: FormView.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ int $nextIndex;
        final /* synthetic */ int $numberOfFields;
        final /* synthetic */ FormView<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(FormView<T> formView, int i, int i2) {
            super(0);
            this.this$0 = formView;
            this.$nextIndex = i;
            this.$numberOfFields = i2;
        }

        /* compiled from: FormView.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ int $nextIndex;
            final /* synthetic */ int $numberOfFields;
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(FormView<T> formView, int i, int i2) {
                super(0);
                this.this$0 = formView;
                this.$nextIndex = i;
                this.$numberOfFields = i2;
            }

            public final void invoke() {
                FormView.i(this.this$0, this.$nextIndex, (DisplayedField) null, this.$numberOfFields, 2, (Object) null);
            }
        }

        public final void invoke() {
            FormView<T> formView = this.this$0;
            int i = this.$nextIndex;
            formView.k(i, new a(formView, i, this.$numberOfFields));
        }
    }

    private final void n(int index, kotlin.jvm.functions.a<x> progressToNextFieldView) {
        EditText $this$nextActionButtonClicked_u24lambda_u2d6;
        FieldView fieldView = (FieldView) y.V(this.x, index);
        if (!(fieldView == null || ($this$nextActionButtonClicked_u24lambda_u2d6 = (EditText) fieldView.findViewById(R$id.zuia_field_input)) == null)) {
            $this$nextActionButtonClicked_u24lambda_u2d6.setImeOptions(5);
            $this$nextActionButtonClicked_u24lambda_u2d6.setOnEditorActionListener(new h(this, progressToNextFieldView));
        }
        this.d.a(new c(this, progressToNextFieldView));
    }

    /* access modifiers changed from: private */
    public static final boolean o(FormView this$0, kotlin.jvm.functions.a $progressToNextFieldView, TextView $noName_0, int actionId, KeyEvent $noName_2) {
        k.e(this$0, "this$0");
        k.e($progressToNextFieldView, "$progressToNextFieldView");
        if (actionId != 5 || !this$0.j()) {
            return false;
        }
        $progressToNextFieldView.invoke();
        return false;
    }

    /* compiled from: FormView.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<n, n> {
        final /* synthetic */ kotlin.jvm.functions.a<x> $progressToNextFieldView;
        final /* synthetic */ FormView<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(FormView<T> formView, kotlin.jvm.functions.a<x> aVar) {
            super(1);
            this.this$0 = formView;
            this.$progressToNextFieldView = aVar;
        }

        @NotNull
        public final n invoke(@NotNull n formButtonRendering) {
            k.e(formButtonRendering, "formButtonRendering");
            return formButtonRendering.c().d(new a(this.this$0, this.$progressToNextFieldView)).a();
        }

        /* compiled from: FormView.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ kotlin.jvm.functions.a<x> $progressToNextFieldView;
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(FormView<T> formView, kotlin.jvm.functions.a<x> aVar) {
                super(0);
                this.this$0 = formView;
                this.$progressToNextFieldView = aVar;
            }

            public final void invoke() {
                if (this.this$0.j()) {
                    this.$progressToNextFieldView.invoke();
                }
                FormView<T> formView = this.this$0;
                formView.p((FieldView) y.d0(formView.x));
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean j() {
        Iterable $this$filterTo$iv$iv = this.x;
        ArrayList arrayList = new ArrayList();
        for (T next : $this$filterTo$iv$iv) {
            if (FieldView.A((FieldView) next, false, 1, (Object) null)) {
                arrayList.add(next);
            }
        }
        return arrayList.containsAll(this.x);
    }

    private final void q(boolean enableSendActionButton) {
        if (enableSendActionButton) {
            this.d.a(new e(this));
            EditText $this$sendActionButtonClicked_u24lambda_u2d9 = (EditText) ((FieldView) y.d0(this.x)).findViewById(R$id.zuia_field_input);
            $this$sendActionButtonClicked_u24lambda_u2d9.setImeOptions(4);
            $this$sendActionButtonClicked_u24lambda_u2d9.setOnEditorActionListener(new g(this));
        }
    }

    /* compiled from: FormView.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<n, n> {
        final /* synthetic */ FormView<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(FormView<T> formView) {
            super(1);
            this.this$0 = formView;
        }

        @NotNull
        public final n invoke(@NotNull n formButtonRendering) {
            k.e(formButtonRendering, "formButtonRendering");
            return formButtonRendering.c().d(new a(this.this$0)).g(new b(this.this$0)).a();
        }

        /* compiled from: FormView.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(FormView<T> formView) {
                super(0);
                this.this$0 = formView;
            }

            public final void invoke() {
                Iterable $this$filterTo$iv$iv = this.this$0.x;
                ArrayList arrayList = new ArrayList();
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    if (FieldView.A((FieldView) element$iv$iv, false, 1, (Object) null)) {
                        arrayList.add(element$iv$iv);
                    }
                }
                if (arrayList.containsAll(this.this$0.x)) {
                    this.this$0.c.f().invoke(y.C0(this.this$0.q));
                    for (FieldView view : this.this$0.x) {
                        view.clearFocus();
                    }
                } else if (!this.this$0.c.i().c()) {
                    FormView<T> formView = this.this$0;
                    for (Object element$iv : formView.x) {
                        if (!FieldView.A((FieldView) element$iv, false, 1, (Object) null)) {
                            formView.p((FieldView) element$iv);
                            return;
                        }
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            }
        }

        /* compiled from: FormView.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<o, o> {
            final /* synthetic */ FormView<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(FormView<T> formView) {
                super(1);
                this.this$0 = formView;
            }

            @NotNull
            public final o invoke(@NotNull o state) {
                k.e(state, Constants.ACTION_STATE);
                boolean c = this.this$0.c.i().c();
                String string = this.this$0.getResources().getString(R$string.zuia_form_send_button);
                k.d(string, "getString(R.string.zuia_form_send_button)");
                return o.b(state, string, c, (Integer) null, 4, (Object) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final boolean r(FormView this$0, TextView $noName_0, int actionId, KeyEvent $noName_2) {
        k.e(this$0, "this$0");
        if (actionId != 4) {
            return false;
        }
        this$0.d.performClick();
        return true;
    }

    private final void s(int index, int size) {
        this.y.setText(getResources().getString(R$string.zuia_form_field_counter_label, new Object[]{Integer.valueOf(index + 1), Integer.valueOf(size)}));
    }

    /* access modifiers changed from: private */
    public final void k(int index, kotlin.jvm.functions.a<x> displayFieldView) {
        displayFieldView.invoke();
        FieldView fieldView = (FieldView) y.V(this.x, index);
        if (fieldView != null) {
            p(fieldView);
        }
    }

    private final void t(int index) {
        if (this.c.d().get(Integer.valueOf(index)) == null) {
            this.c.g().invoke(new DisplayedField(index, (String) null, 2, (DefaultConstructorMarker) null));
        }
    }

    /* access modifiers changed from: private */
    public final void p(FieldView $this$requestViewFocus) {
        EditText editText = (EditText) $this$requestViewFocus.findViewById(R$id.zuia_field_input);
        if (editText != null) {
            zendesk.ui.android.internal.l.b(editText);
        }
    }
}
