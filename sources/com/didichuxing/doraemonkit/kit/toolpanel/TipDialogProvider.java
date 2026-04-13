package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.View;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TipDialogProvider.kt */
public final class TipDialogProvider extends DialogProvider<Object> {
    private TextView mTip;

    public TipDialogProvider(@Nullable Object data, @Nullable DialogListener listener) {
        super(data, listener);
    }

    public int getLayoutId() {
        return R.layout.dk_dialog_tip;
    }

    /* access modifiers changed from: protected */
    public void findViews(@NotNull View view) {
        k.f(view, "view");
        View findViewById = view.findViewById(R.id.tv_tip);
        k.b(findViewById, "view.findViewById(R.id.tv_tip)");
        this.mTip = (TextView) findViewById;
    }

    /* access modifiers changed from: protected */
    public void bindData(@Nullable Object data) {
        if (data instanceof String) {
            TextView textView = this.mTip;
            if (textView == null) {
                k.t("mTip");
            }
            textView.setText((CharSequence) data);
        }
    }
}
