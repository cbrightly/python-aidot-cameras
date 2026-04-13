package zendesk.ui.android.conversation.composer;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.internal.i;
import zendesk.ui.android.internal.l;

/* compiled from: MessageComposerView.kt */
public final class MessageComposerView extends FrameLayout implements zendesk.ui.android.a<g> {
    @NotNull
    private static final c c = new c((DefaultConstructorMarker) null);
    @NotNull
    private final ImageButton d;
    @NotNull
    private final ImageButton f;
    @Nullable
    private ViewPropertyAnimator p0;
    /* access modifiers changed from: private */
    @NotNull
    public final EditText q;
    @NotNull
    private final FrameLayout x;
    /* access modifiers changed from: private */
    @NotNull
    public g y;
    @NotNull
    private final ConstraintLayout z;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageComposerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageComposerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageComposerView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MessageComposerView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MessageComposerView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.y = new g();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_MessageComposer, false);
        FrameLayout.inflate(context, R$layout.zuia_view_message_composer, this);
        View findViewById = findViewById(R$id.zuia_composer_container);
        k.d(findViewById, "findViewById(R.id.zuia_composer_container)");
        FrameLayout frameLayout = (FrameLayout) findViewById;
        this.x = frameLayout;
        View findViewById2 = findViewById(R$id.zuia_attach_button);
        k.d(findViewById2, "findViewById(R.id.zuia_attach_button)");
        this.f = (ImageButton) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_text_field);
        k.d(findViewById3, "findViewById(R.id.zuia_text_field)");
        EditText $this$addTextChangedListener_u24default$iv$iv = (EditText) findViewById3;
        this.q = $this$addTextChangedListener_u24default$iv$iv;
        View findViewById4 = findViewById(R$id.zuia_send_button);
        k.d(findViewById4, "findViewById(R.id.zuia_send_button)");
        this.d = (ImageButton) findViewById4;
        View findViewById5 = findViewById(R$id.zuia_message_composer_view);
        k.d(findViewById5, "findViewById(R.id.zuia_message_composer_view)");
        this.z = (ConstraintLayout) findViewById5;
        context.getTheme().resolveAttribute(R$attr.colorOnSurface, new TypedValue(), true);
        l.g(frameLayout, 0, getResources().getDimension(R$dimen.zuia_message_composer_radius), 1, (Object) null);
        $this$addTextChangedListener_u24default$iv$iv.addTextChangedListener(new g(this));
        $this$addTextChangedListener_u24default$iv$iv.addTextChangedListener(i.b(0, new a(this), 1, (Object) null));
        a(b.INSTANCE);
    }

    /* compiled from: MessageComposerView.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Editable, x> {
        final /* synthetic */ MessageComposerView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(MessageComposerView messageComposerView) {
            super(1);
            this.this$0 = messageComposerView;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Editable) p1);
            return x.a;
        }

        public final void invoke(@Nullable Editable text) {
            boolean z = false;
            if (text != null && (!w.A(text))) {
                z = true;
            }
            if (z) {
                this.this$0.l(true);
            }
            this.this$0.y.c().invoke(kotlin.text.x.e1(String.valueOf(text)).toString());
        }
    }

    /* compiled from: TextView.kt */
    public static final class g implements TextWatcher {
        final /* synthetic */ MessageComposerView c;

        public g(MessageComposerView messageComposerView) {
            this.c = messageComposerView;
        }

        public void afterTextChanged(@Nullable Editable s) {
            Editable editable = s;
        }

        public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            CharSequence charSequence = text;
            int i = count;
            int i2 = start;
            int i3 = after;
        }

        public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            int i = before;
            CharSequence text2 = text;
            int i2 = start;
            int i3 = count;
            boolean z = false;
            if (text2 != null && (!w.A(text2))) {
                z = true;
            }
            if (z) {
                this.c.y.d().invoke();
            }
        }
    }

    /* compiled from: MessageComposerView.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g, g> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final g invoke(@NotNull g it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super g, g> lVar) {
        k.e(lVar, "renderingUpdate");
        g invoke = lVar.invoke(this.y);
        this.y = invoke;
        setEnabled(invoke.e().e());
        boolean z2 = false;
        this.q.setFilters(this.y.e().g() < 0 ? new InputFilter[0] : new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(this.y.e().g())});
        Integer b2 = this.y.e().b();
        if (b2 != null) {
            int intValue = b2.intValue();
            this.f.setColorFilter(intValue);
            this.d.setColorFilter(intValue);
        }
        this.d.setOnClickListener(zendesk.ui.android.internal.k.b(0, new d(this), 1, (Object) null));
        this.z.setVisibility(this.y.e().i());
        this.f.setOnClickListener(zendesk.ui.android.internal.k.b(0, new e(this), 1, (Object) null));
        String d2 = this.y.e().d();
        if (d2.length() > 0) {
            z2 = true;
        }
        if (z2) {
            this.q.setText(d2);
        }
        if (this.q.hasFocus()) {
            EditText editText = this.q;
            editText.setSelection(editText.getText().toString().length());
        }
    }

    /* compiled from: MessageComposerView.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ MessageComposerView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(MessageComposerView messageComposerView) {
            super(0);
            this.this$0 = messageComposerView;
        }

        public final void invoke() {
            kotlin.jvm.functions.l<String, x> b = this.this$0.y.b();
            String obj = this.this$0.q.getText().toString();
            if (obj != null) {
                b.invoke(kotlin.text.x.e1(obj).toString());
                this.this$0.q.setText((CharSequence) null);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
        }
    }

    /* compiled from: MessageComposerView.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ MessageComposerView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(MessageComposerView messageComposerView) {
            super(0);
            this.this$0 = messageComposerView;
        }

        public final void invoke() {
            this.this$0.k();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        j(enabled);
        if (enabled) {
            this.q.setEnabled(true);
            this.q.setMaxLines(5);
            Editable text = this.q.getText();
            k.d(text, "textField.text");
            l(true ^ w.A(text));
            return;
        }
        this.q.setEnabled(false);
        this.q.setMaxLines(1);
        l(false);
    }

    /* access modifiers changed from: private */
    public final void l(boolean enabled) {
        ImageButton $this$renderSendButton_u24lambda_u2d8 = this.d;
        int i = 1;
        if (($this$renderSendButton_u24lambda_u2d8.getVisibility() == 0) != enabled) {
            float height = ((float) $this$renderSendButton_u24lambda_u2d8.getHeight()) / 2.0f;
            if (this.d.getLayoutDirection() == 1) {
                i = -1;
            }
            float xTranslation = height * ((float) i);
            ViewPropertyAnimator viewPropertyAnimator = this.p0;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
            }
            if (enabled) {
                $this$renderSendButton_u24lambda_u2d8.setAlpha(0.0f);
                $this$renderSendButton_u24lambda_u2d8.setVisibility(0);
                $this$renderSendButton_u24lambda_u2d8.setTranslationX(xTranslation);
                ViewPropertyAnimator it = $this$renderSendButton_u24lambda_u2d8.animate().translationX(0.0f).setDuration(300).setInterpolator(new DecelerateInterpolator()).withStartAction(new f($this$renderSendButton_u24lambda_u2d8)).withEndAction(new e($this$renderSendButton_u24lambda_u2d8, this));
                it.start();
                x xVar = x.a;
                this.p0 = it;
                return;
            }
            $this$renderSendButton_u24lambda_u2d8.setTranslationX(0.0f);
            ViewPropertyAnimator it2 = $this$renderSendButton_u24lambda_u2d8.animate().translationX(xTranslation).setDuration(300).setInterpolator(new AccelerateInterpolator()).withStartAction(new c($this$renderSendButton_u24lambda_u2d8)).withEndAction(new d($this$renderSendButton_u24lambda_u2d8, this));
            it2.start();
            x xVar2 = x.a;
            this.p0 = it2;
        }
    }

    /* access modifiers changed from: private */
    public static final void m(ImageButton $this_apply) {
        k.e($this_apply, "$this_apply");
        $this_apply.animate().alpha(1.0f).setStartDelay(100).setDuration(200).setInterpolator(new LinearInterpolator()).start();
    }

    /* access modifiers changed from: private */
    public static final void n(ImageButton $this_apply, MessageComposerView this$0) {
        k.e($this_apply, "$this_apply");
        k.e(this$0, "this$0");
        $this_apply.setVisibility(0);
        this$0.p0 = null;
    }

    /* access modifiers changed from: private */
    public static final void o(ImageButton $this_apply) {
        k.e($this_apply, "$this_apply");
        $this_apply.animate().alpha(0.0f).setDuration(200).start();
    }

    /* access modifiers changed from: private */
    public static final void p(ImageButton $this_apply, MessageComposerView this$0) {
        k.e($this_apply, "$this_apply");
        k.e(this$0, "this$0");
        $this_apply.setVisibility(8);
        this$0.p0 = null;
    }

    private final void j(boolean enabled) {
        int i = 8;
        if (enabled) {
            boolean h = this.y.e().h();
            ImageButton imageButton = this.f;
            if (enabled && (this.y.e().f() || this.y.e().c())) {
                i = 0;
            }
            imageButton.setVisibility(i);
            return;
        }
        this.f.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public final void k() {
        Context context = getContext();
        k.d(context, "context");
        MessageComposerAttachmentMenu menuView = new MessageComposerAttachmentMenu(context);
        menuView.setGallerySupported(this.y.e().f());
        menuView.setCameraSupported(this.y.e().c());
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        menuView.setOnItemClickListener(new f(this, dialog));
        dialog.getBehavior().setState(3);
        dialog.getBehavior().setSkipCollapsed(true);
        dialog.setContentView((View) menuView);
        dialog.show();
    }

    /* compiled from: MessageComposerView.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Integer, x> {
        final /* synthetic */ BottomSheetDialog $dialog;
        final /* synthetic */ MessageComposerView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(MessageComposerView messageComposerView, BottomSheetDialog bottomSheetDialog) {
            super(1);
            this.this$0 = messageComposerView;
            this.$dialog = bottomSheetDialog;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Number) p1).intValue());
            return x.a;
        }

        public final void invoke(int itemId) {
            this.this$0.y.a().invoke(Integer.valueOf(itemId));
            this.$dialog.dismiss();
        }
    }

    /* compiled from: MessageComposerView.kt */
    public static final class c {
        public /* synthetic */ c(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private c() {
        }
    }
}
