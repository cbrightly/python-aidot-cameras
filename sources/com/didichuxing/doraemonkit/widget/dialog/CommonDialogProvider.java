package com.didichuxing.doraemonkit.widget.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;

public class CommonDialogProvider extends DialogProvider<DialogInfo> {
    private TextView mDesc;
    private TextView mNegative;
    private TextView mPositive;
    private TextView mTitle;

    public CommonDialogProvider(DialogInfo data, DialogListener listener) {
        super(data, listener);
    }

    public int getLayoutId() {
        return R.layout.dk_dialog_common;
    }

    /* access modifiers changed from: protected */
    public void findViews(View view) {
        this.mPositive = (TextView) view.findViewById(R.id.positive);
        this.mNegative = (TextView) view.findViewById(R.id.negative);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mDesc = (TextView) view.findViewById(R.id.desc);
    }

    /* access modifiers changed from: protected */
    public void bindData(DialogInfo data) {
        this.mTitle.setText(data.title);
        if (TextUtils.isEmpty(data.desc)) {
            this.mDesc.setVisibility(8);
            return;
        }
        this.mDesc.setVisibility(0);
        this.mDesc.setText(data.desc);
    }

    /* access modifiers changed from: protected */
    public View getPositiveView() {
        return this.mPositive;
    }

    /* access modifiers changed from: protected */
    public View getNegativeView() {
        return this.mNegative;
    }

    public boolean isCancellable() {
        return false;
    }
}
