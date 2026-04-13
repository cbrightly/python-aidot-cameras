package zendesk.ui.android.conversation.file;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.internal.d;

/* compiled from: FileView.kt */
public final class FileView extends RelativeLayout implements zendesk.ui.android.a<a> {
    @NotNull
    private final ImageView c;
    @NotNull
    private final TextView d;
    @NotNull
    private final TextView f;
    /* access modifiers changed from: private */
    @NotNull
    public a q;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FileView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FileView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FileView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FileView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.q = new a();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_TextCellStyle, false);
        RelativeLayout.inflate(context, R$layout.zuia_view_file_cell, this);
        setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        int horizontalPadding = context.getResources().getDimensionPixelSize(R$dimen.zuia_horizontal_message_padding);
        int verticalPadding = context.getResources().getDimensionPixelSize(R$dimen.zuia_vertical_message_padding);
        setPaddingRelative(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        setClickable(true);
        setFocusable(true);
        View findViewById = findViewById(R$id.zuia_file_icon);
        k.d(findViewById, "findViewById(R.id.zuia_file_icon)");
        this.c = (ImageView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_file_name);
        k.d(findViewById2, "findViewById(R.id.zuia_file_name)");
        this.d = (TextView) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_file_size);
        k.d(findViewById3, "findViewById(R.id.zuia_file_size)");
        this.f = (TextView) findViewById3;
        a(a.INSTANCE);
    }

    /* compiled from: FileView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<a, a> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final a invoke(@NotNull a it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super a, a> renderingUpdate) {
        int textColor;
        Integer b2;
        k.e(renderingUpdate, "renderingUpdate");
        a invoke = renderingUpdate.invoke(this.q);
        this.q = invoke;
        this.d.setText(invoke.b().d());
        this.f.setText(c(this.q.b().e()));
        Integer c2 = this.q.b().c();
        if (c2 != null) {
            setBackgroundResource(c2.intValue());
        }
        if (!(getBackground() == null || (b2 = this.q.b().b()) == null)) {
            Drawable background = getBackground();
            k.d(background, "background");
            background.setTint(b2.intValue());
        }
        Integer g = this.q.b().g();
        if (g == null) {
            Context context = getContext();
            k.d(context, "context");
            textColor = d.b(context, 16842904);
        } else {
            textColor = g.intValue();
        }
        this.d.setTextColor(textColor);
        this.f.setTextColor(textColor);
        Integer f2 = this.q.b().f();
        if (f2 != null) {
            this.c.setColorFilter(f2.intValue(), PorterDuff.Mode.SRC_IN);
        }
        setOnClickListener(zendesk.ui.android.internal.k.b(0, new b(this), 1, (Object) null));
    }

    /* compiled from: FileView.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ FileView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(FileView fileView) {
            super(0);
            this.this$0 = fileView;
        }

        public final void invoke() {
            this.this$0.q.a().invoke();
        }
    }

    private final String c(long fileSize) {
        long j;
        Context context = getContext();
        if (Build.VERSION.SDK_INT >= 26) {
            long j2 = (long) 1000;
            long j3 = fileSize * j2 * j2;
            long j4 = (long) 1024;
            j = (j3 / j4) / j4;
        } else {
            j = fileSize;
        }
        String formatFileSize = Formatter.formatFileSize(context, j);
        k.d(formatFileSize, "formatFileSize(\n        … 1024 else fileSize\n    )");
        return formatFileSize;
    }
}
