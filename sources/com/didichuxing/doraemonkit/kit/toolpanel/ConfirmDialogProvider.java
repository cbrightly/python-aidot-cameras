package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.View;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConfirmDialogProvider.kt */
public final class ConfirmDialogProvider extends DialogProvider<Object> {
    private TextView mTvContent;
    private TextView mTvNegative;
    private TextView mTvPositive;

    public ConfirmDialogProvider(@Nullable Object data, @Nullable DialogListener listener) {
        super(data, listener);
    }

    public int getLayoutId() {
        return R.layout.dk_dialog_confirm;
    }

    /* access modifiers changed from: protected */
    public void findViews(@NotNull View view) {
        k.f(view, "view");
        View findViewById = view.findViewById(R.id.tv_content);
        k.b(findViewById, "view.findViewById(R.id.tv_content)");
        this.mTvContent = (TextView) findViewById;
        View findViewById2 = view.findViewById(R.id.positive);
        k.b(findViewById2, "view.findViewById(R.id.positive)");
        this.mTvPositive = (TextView) findViewById2;
        View findViewById3 = view.findViewById(R.id.negative);
        k.b(findViewById3, "view.findViewById(R.id.negative)");
        this.mTvNegative = (TextView) findViewById3;
    }

    /* access modifiers changed from: protected */
    public void bindData(@Nullable Object data) {
        if (data instanceof String) {
            TextView textView = this.mTvContent;
            if (textView == null) {
                k.t("mTvContent");
            }
            textView.setText((CharSequence) data);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View getPositiveView() {
        TextView textView = this.mTvPositive;
        if (textView == null) {
            k.t("mTvPositive");
        }
        return textView;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View getNegativeView() {
        TextView textView = this.mTvNegative;
        if (textView == null) {
            k.t("mTvNegative");
        }
        return textView;
    }

    public boolean isCancellable() {
        return false;
    }
}
