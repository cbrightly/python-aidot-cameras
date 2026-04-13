package zendesk.ui.android.conversation.form;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$string;
import zendesk.ui.android.R$style;
import zendesk.ui.android.conversation.form.i;
import zendesk.ui.android.conversation.form.m;
import zendesk.ui.android.conversation.receipt.MessageReceiptView;
import zendesk.ui.android.conversation.receipt.b;
import zendesk.ui.android.conversation.receipt.c;
import zendesk.ui.android.internal.g;

/* compiled from: FieldView.kt */
public final class FieldView extends FrameLayout implements zendesk.ui.android.a<i<?>> {
    @NotNull
    private final MessageReceiptView c;
    @NotNull
    private final TextView d;
    @NotNull
    private final TextInputLayout f;
    @NotNull
    private final MaterialAutoCompleteTextView q;
    /* access modifiers changed from: private */
    @NotNull
    public i<?> x;
    @Nullable
    private TextWatcher y;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FieldView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FieldView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FieldView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FieldView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.x = new i.c(new m.c((String) null, 0, 0, (String) null, (String) null, (Integer) null, 63, (DefaultConstructorMarker) null), (l) null, (l) null, f.INSTANCE, (l) null, 22, (DefaultConstructorMarker) null);
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_Field, false);
        FrameLayout.inflate(context, R$layout.zuia_view_field, this);
        setClipToPadding(false);
        setClipChildren(false);
        View findViewById = findViewById(R$id.zuia_error_indicator);
        k.d(findViewById, "findViewById(R.id.zuia_error_indicator)");
        this.c = (MessageReceiptView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_field_layout);
        k.d(findViewById2, "findViewById(R.id.zuia_field_layout)");
        TextInputLayout textInputLayout = (TextInputLayout) findViewById2;
        this.f = textInputLayout;
        int[][] iArr = {new int[]{16842908}, new int[]{16843623}, new int[0]};
        int i = R$attr.colorAccent;
        textInputLayout.setBoxStrokeColorStateList(new ColorStateList(iArr, new int[]{zendesk.ui.android.internal.d.b(context, i), zendesk.ui.android.internal.d.b(context, i), zendesk.ui.android.internal.d.a(zendesk.ui.android.internal.d.b(context, R$attr.colorOnSurface), 0.12f)}));
        View findViewById3 = findViewById(R$id.zuia_field_label);
        k.d(findViewById3, "findViewById(R.id.zuia_field_label)");
        this.d = (TextView) findViewById3;
        View findViewById4 = findViewById(R$id.zuia_field_input);
        k.d(findViewById4, "findViewById(R.id.zuia_field_input)");
        this.q = (MaterialAutoCompleteTextView) findViewById4;
        View $this$_init__u24lambda_u2d0 = textInputLayout.findViewById(R$id.text_input_end_icon);
        int minSize = $this$_init__u24lambda_u2d0.getResources().getDimensionPixelSize(R$dimen.zuia_control_min_size);
        $this$_init__u24lambda_u2d0.setMinimumWidth(minSize);
        $this$_init__u24lambda_u2d0.setMinimumHeight(minSize);
        $this$_init__u24lambda_u2d0.requestLayout();
        this.y = null;
        a(new a(this));
    }

    /* compiled from: FieldView.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<m.c, m.c> {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        @NotNull
        public final m.c invoke(@NotNull m.c it) {
            k.e(it, "it");
            return it;
        }
    }

    /* compiled from: TextView.kt */
    public static final class c implements TextWatcher {
        final /* synthetic */ i.c c;
        final /* synthetic */ FieldView d;

        public c(i.c cVar, FieldView fieldView) {
            this.c = cVar;
            this.d = fieldView;
        }

        public void afterTextChanged(@Nullable Editable s) {
            i.c cVar = this.c;
            i.c $this$renderFormField_u24lambda_u2d9_u24lambda_u2d8 = i.c.d(cVar, m.c.e(cVar.b(), String.valueOf(s), 0, 0, (String) null, (String) null, (Integer) null, 62, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            this.d.x = $this$renderFormField_u24lambda_u2d9_u24lambda_u2d8;
            FieldView fieldView = this.d;
            boolean unused = fieldView.y(fieldView.x.b(), true);
            l<String, x> h = this.c.h();
            String h2 = $this$renderFormField_u24lambda_u2d9_u24lambda_u2d8.b().h();
            if (h2 == null) {
                h2 = "";
            }
            h.invoke(h2);
            this.c.g().invoke($this$renderFormField_u24lambda_u2d9_u24lambda_u2d8.b());
        }

        public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            CharSequence charSequence = text;
            int i = count;
            int i2 = start;
            int i3 = after;
        }

        public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            CharSequence charSequence = text;
            int i = before;
            int i2 = start;
            int i3 = count;
        }
    }

    /* compiled from: TextView.kt */
    public static final class d implements TextWatcher {
        final /* synthetic */ i.a c;
        final /* synthetic */ FieldView d;

        public d(i.a aVar, FieldView fieldView) {
            this.c = aVar;
            this.d = fieldView;
        }

        public void afterTextChanged(@Nullable Editable s) {
            i.a aVar = this.c;
            i.a $this$renderFormField_u24lambda_u2d12_u24lambda_u2d11 = i.a.d(aVar, m.a.e(aVar.b(), String.valueOf(s), (String) null, (String) null, (Integer) null, 14, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
            this.d.x = $this$renderFormField_u24lambda_u2d12_u24lambda_u2d11;
            FieldView fieldView = this.d;
            boolean unused = fieldView.y(fieldView.x.b(), true);
            l<String, x> f = this.c.f();
            String f2 = $this$renderFormField_u24lambda_u2d12_u24lambda_u2d11.b().f();
            if (f2 == null) {
                f2 = "";
            }
            f.invoke(f2);
            this.c.h().invoke($this$renderFormField_u24lambda_u2d12_u24lambda_u2d11.b());
        }

        public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            CharSequence charSequence = text;
            int i = count;
            int i2 = start;
            int i3 = after;
        }

        public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            CharSequence charSequence = text;
            int i = before;
            int i2 = start;
            int i3 = count;
        }
    }

    /* compiled from: FieldView.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<i<?>, i<?>> {
        final /* synthetic */ FieldView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(FieldView fieldView) {
            super(1);
            this.this$0 = fieldView;
        }

        @NotNull
        public final i<?> invoke(@NotNull i<?> it) {
            k.e(it, "it");
            return this.this$0.x;
        }
    }

    public boolean requestFocus(int direction, @Nullable Rect previouslyFocusedRect) {
        if (previouslyFocusedRect != null) {
            return this.q.requestFocus(direction, previouslyFocusedRect);
        }
        return false;
    }

    public void a(@NotNull l<? super i<?>, ? extends i<?>> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        i<?> iVar = (i) renderingUpdate.invoke(this.x);
        this.x = iVar;
        Integer a2 = iVar.b().a();
        if (a2 != null) {
            this.f.setBoxStrokeColor(a2.intValue());
        }
        this.f.setErrorIconDrawable((Drawable) null);
        this.d.setText(this.x.b().b());
        View $this$isGone$iv = this.d;
        String b2 = this.x.b().b();
        boolean z = true;
        int i = 0;
        $this$isGone$iv.setVisibility(b2 == null || w.A(b2) ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
        if (layoutParams != null) {
            ViewGroup.MarginLayoutParams $this$render_u24lambda_u2d1 = (ViewGroup.MarginLayoutParams) layoutParams;
            String b3 = this.x.b().b();
            if (b3 != null && !w.A(b3)) {
                z = false;
            }
            if (!z) {
                i = getResources().getDimensionPixelSize(R$dimen.zuia_spacing_small);
            }
            $this$render_u24lambda_u2d1.bottomMargin = i;
            this.d.setLayoutParams($this$render_u24lambda_u2d1);
            this.q.removeTextChangedListener(this.y);
            this.q.setHint(this.x.b().c());
            this.q.setOnFocusChangeListener(new b(this));
            i<?> iVar2 = this.x;
            if (iVar2 instanceof i.c) {
                n((i.c) iVar2);
            } else if (iVar2 instanceof i.a) {
                l((i.a) iVar2);
            } else if (iVar2 instanceof i.b) {
                m((i.b) iVar2);
            }
        } else {
            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        }
    }

    /* access modifiers changed from: private */
    public static final void j(FieldView this$0, View $noName_0, boolean $noName_1) {
        k.e(this$0, "this$0");
        this$0.y(this$0.x.b(), true);
        u(this$0, false, 1, (Object) null);
    }

    private final void m(i.b<?> fieldRendering) {
        this.f.setEndIconMode(3);
        MaterialAutoCompleteTextView materialAutoCompleteTextView = this.q;
        MaterialShapeDrawable createWithElevationOverlay = MaterialShapeDrawable.createWithElevationOverlay(getContext());
        MaterialShapeDrawable $this$renderFormField_u24lambda_u2d3 = createWithElevationOverlay;
        $this$renderFormField_u24lambda_u2d3.setStrokeWidth(getResources().getDimension(R$dimen.zuia_divider_size));
        Context context = getContext();
        k.d(context, "context");
        $this$renderFormField_u24lambda_u2d3.setStrokeColor(ColorStateList.valueOf(zendesk.ui.android.internal.d.a(zendesk.ui.android.internal.d.b(context, R$attr.colorOnSurface), 0.12f)));
        $this$renderFormField_u24lambda_u2d3.setCornerSize(getResources().getDimension(R$dimen.zuia_message_cell_radius));
        x xVar = x.a;
        materialAutoCompleteTextView.setDropDownBackgroundDrawable(createWithElevationOverlay);
        MaterialAutoCompleteTextView materialAutoCompleteTextView2 = this.q;
        Context context2 = getContext();
        int i = R$layout.zuia_item_field_option;
        Iterable<u> $this$mapTo$iv$iv = fieldRendering.b().f();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (u it : $this$mapTo$iv$iv) {
            arrayList.add(it.b());
        }
        materialAutoCompleteTextView2.setAdapter(new ArrayAdapter(context2, i, arrayList));
        MaterialAutoCompleteTextView materialAutoCompleteTextView3 = this.q;
        u uVar = (u) y.U(fieldRendering.b().g());
        String b2 = uVar == null ? null : uVar.b();
        if (b2 == null) {
            b2 = "";
        }
        materialAutoCompleteTextView3.setText(b2, false);
        this.q.setOnItemClickListener(new e(fieldRendering, this));
        this.q.setOnFocusChangeListener(new a(fieldRendering, this));
        this.q.setInputType(0);
        this.q.setKeyListener((KeyListener) null);
        this.q.setShowSoftInputOnFocus(false);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void q(i.b $fieldRendering, FieldView this$0, AdapterView adapterView, View view, int position, long $noName_3) {
        AdapterView adapterView2 = adapterView;
        View view2 = view;
        i.b bVar = $fieldRendering;
        k.e($fieldRendering, "$fieldRendering");
        k.e(this$0, "this$0");
        i.b $this$renderFormField_u24lambda_u2d6_u24lambda_u2d5 = i.b.d($fieldRendering, m.b.e($fieldRendering.b(), (List) null, p.b($fieldRendering.b().f().get(position)), (String) null, (String) null, (Integer) null, 29, (Object) null), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
        this$0.x = $this$renderFormField_u24lambda_u2d6_u24lambda_u2d5;
        this$0.w($this$renderFormField_u24lambda_u2d6_u24lambda_u2d5.b(), true);
        $this$renderFormField_u24lambda_u2d6_u24lambda_u2d5.h().invoke($this$renderFormField_u24lambda_u2d6_u24lambda_u2d5.b());
        $this$renderFormField_u24lambda_u2d6_u24lambda_u2d5.g().invoke($this$renderFormField_u24lambda_u2d6_u24lambda_u2d5.b().g());
        SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
    }

    /* access modifiers changed from: private */
    public static final void r(i.b $fieldRendering, FieldView this$0, View $noName_0, boolean hasFocus) {
        k.e($fieldRendering, "$fieldRendering");
        k.e(this$0, "this$0");
        $fieldRendering.f().invoke(Boolean.valueOf(hasFocus));
        this$0.z(true);
        u(this$0, false, 1, (Object) null);
    }

    private final void n(i.c<?> fieldRendering) {
        this.q.setInputType(8192);
        this.q.setText(fieldRendering.b().h());
        this.f.setEndIconVisible(false);
        c textWatcher$iv$iv = new c(fieldRendering, this);
        this.q.addTextChangedListener(textWatcher$iv$iv);
        this.y = textWatcher$iv$iv;
        this.q.setOnFocusChangeListener(new c(fieldRendering, this));
    }

    /* access modifiers changed from: private */
    public static final void o(i.c $fieldRendering, FieldView this$0, View $noName_0, boolean hasFocus) {
        k.e($fieldRendering, "$fieldRendering");
        k.e(this$0, "this$0");
        $fieldRendering.f().invoke(Boolean.valueOf(hasFocus));
        u(this$0, false, 1, (Object) null);
    }

    private final void l(i.a<?> fieldRendering) {
        this.q.setInputType(33);
        this.q.setText(fieldRendering.b().f());
        this.f.setEndIconVisible(false);
        d textWatcher$iv$iv = new d(fieldRendering, this);
        this.q.addTextChangedListener(textWatcher$iv$iv);
        this.y = textWatcher$iv$iv;
        this.q.setOnFocusChangeListener(new d(fieldRendering, this));
    }

    /* access modifiers changed from: private */
    public static final void p(i.a $fieldRendering, FieldView this$0, View $noName_0, boolean hasFocus) {
        k.e($fieldRendering, "$fieldRendering");
        k.e(this$0, "this$0");
        $fieldRendering.g().invoke(Boolean.valueOf(hasFocus));
        u(this$0, false, 1, (Object) null);
    }

    static /* synthetic */ void u(FieldView fieldView, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = !fieldView.z(true);
        }
        fieldView.t(z);
    }

    private final void t(boolean hasError) {
        int i;
        if (hasError) {
            TextInputLayout textInputLayout = this.f;
            Context context = getContext();
            k.d(context, "context");
            zendesk.ui.android.internal.l.g(textInputLayout, zendesk.ui.android.internal.d.b(context, R$attr.colorError), 0.0f, 2, (Object) null);
        } else if (this.q.hasFocus()) {
            TextInputLayout textInputLayout2 = this.f;
            Integer a2 = this.x.b().a();
            if (a2 == null) {
                Context context2 = getContext();
                k.d(context2, "context");
                i = zendesk.ui.android.internal.d.b(context2, R$attr.colorAccent);
            } else {
                i = a2.intValue();
            }
            zendesk.ui.android.internal.l.d(textInputLayout2, i, 0.0f, 0.0f, 0.0f, 14, (Object) null);
        } else {
            zendesk.ui.android.internal.l.g(this.f, 0, 0.0f, 3, (Object) null);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(@Nullable SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(@Nullable SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    public static /* synthetic */ boolean A(FieldView fieldView, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return fieldView.z(z);
    }

    public final boolean z(boolean includeFocus) {
        return y(this.x.b(), includeFocus);
    }

    /* access modifiers changed from: private */
    public final boolean y(m $this$validate, boolean includeFocus) {
        if ($this$validate instanceof m.c) {
            return x((m.c) $this$validate, includeFocus);
        }
        if ($this$validate instanceof m.a) {
            return v((m.a) $this$validate, includeFocus);
        }
        if ($this$validate instanceof m.b) {
            return w((m.b) $this$validate, includeFocus);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean x(m.c $this$validate, boolean includeFocus) {
        boolean hasFocus = this.q.hasFocus();
        String h = $this$validate.h();
        if (h == null) {
            h = "";
        }
        int textLength = h.length();
        if (textLength > $this$validate.f()) {
            String string = getResources().getString(R$string.zuia_form_field_max_character_error, new Object[]{Integer.valueOf($this$validate.f())});
            k.d(string, "resources.getString(R.st…aracter_error, maxLength)");
            return k(string);
        } else if (includeFocus && hasFocus) {
            return s();
        } else {
            if (textLength == 0) {
                String string2 = getResources().getString(R$string.zuia_form_field_required_label);
                k.d(string2, "resources.getString(R.st…orm_field_required_label)");
                return k(string2);
            } else if (textLength >= $this$validate.g()) {
                return s();
            } else {
                String string3 = getResources().getString(R$string.zuia_form_field_min_character_error, new Object[]{Integer.valueOf($this$validate.g())});
                k.d(string3, "resources.getString(R.st…aracter_error, minLength)");
                return k(string3);
            }
        }
    }

    private final boolean v(m.a $this$validate, boolean includeFocus) {
        boolean hasFocus = this.q.hasFocus();
        if (includeFocus && hasFocus) {
            return s();
        }
        j a2 = g.a.a();
        String f2 = $this$validate.f();
        if (f2 == null) {
            f2 = "";
        }
        if (a2.matches(f2)) {
            return s();
        }
        String f3 = $this$validate.f();
        if (f3 == null || w.A(f3)) {
            String string = getResources().getString(R$string.zuia_form_field_required_label);
            k.d(string, "resources.getString(R.st…orm_field_required_label)");
            return k(string);
        }
        String string2 = getResources().getString(R$string.zuia_form_field_invalid_email_error);
        k.d(string2, "resources.getString(R.st…ield_invalid_email_error)");
        return k(string2);
    }

    private final boolean w(m.b $this$validate, boolean includeFocus) {
        boolean hasFocus = this.q.hasFocus();
        if (includeFocus && hasFocus) {
            return s();
        }
        if (!$this$validate.g().isEmpty()) {
            return s();
        }
        String string = getResources().getString(R$string.zuia_form_field_required_label);
        k.d(string, "resources.getString(R.st…orm_field_required_label)");
        return k(string);
    }

    /* compiled from: FieldView.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.receipt.b, zendesk.ui.android.conversation.receipt.b> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        @NotNull
        public final zendesk.ui.android.conversation.receipt.b invoke(@NotNull zendesk.ui.android.conversation.receipt.b it) {
            k.e(it, "it");
            return new b.a().a();
        }
    }

    private final boolean s() {
        this.c.a(e.INSTANCE);
        t(false);
        return true;
    }

    /* compiled from: FieldView.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.receipt.b, zendesk.ui.android.conversation.receipt.b> {
        final /* synthetic */ String $error;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str) {
            super(1);
            this.$error = str;
        }

        @NotNull
        public final zendesk.ui.android.conversation.receipt.b invoke(@NotNull zendesk.ui.android.conversation.receipt.b it) {
            k.e(it, "it");
            return new b.a().d(new a(this.$error)).a();
        }

        /* compiled from: FieldView.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.receipt.c, zendesk.ui.android.conversation.receipt.c> {
            final /* synthetic */ String $error;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str) {
                super(1);
                this.$error = str;
            }

            @NotNull
            public final zendesk.ui.android.conversation.receipt.c invoke(@NotNull zendesk.ui.android.conversation.receipt.c it) {
                k.e(it, "it");
                return new c.a().c(this.$error).e(zendesk.ui.android.conversation.receipt.a.INBOUND_FAILED).a();
            }
        }
    }

    private final boolean k(String error) {
        this.c.a(new b(error));
        t(true);
        return false;
    }
}
