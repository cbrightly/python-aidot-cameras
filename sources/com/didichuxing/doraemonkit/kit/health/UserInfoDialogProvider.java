package com.didichuxing.doraemonkit.kit.health;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;

public class UserInfoDialogProvider extends DialogProvider<Object> {
    private EditText mCaseName;
    private TextView mClose;
    private TextView mNegative;
    private TextView mPositive;
    private EditText mUserName;

    UserInfoDialogProvider(Object data, DialogListener listener) {
        super(data, listener);
    }

    public int getLayoutId() {
        return R.layout.dk_dialog_userinfo;
    }

    /* access modifiers changed from: protected */
    public void findViews(View view) {
        this.mPositive = (TextView) view.findViewById(R.id.positive);
        this.mNegative = (TextView) view.findViewById(R.id.negative);
        this.mClose = (TextView) view.findViewById(R.id.close);
        this.mCaseName = (EditText) view.findViewById(R.id.edit_case_name);
        this.mUserName = (EditText) view.findViewById(R.id.edit_user_name);
    }

    /* access modifiers changed from: protected */
    public void bindData(Object data) {
    }

    /* access modifiers changed from: protected */
    public View getPositiveView() {
        return this.mPositive;
    }

    /* access modifiers changed from: protected */
    public View getNegativeView() {
        return this.mNegative;
    }

    /* access modifiers changed from: protected */
    public View getCancelView() {
        return this.mClose;
    }

    /* access modifiers changed from: package-private */
    public boolean uploadAppHealthInfo(UploadAppHealthCallback uploadAppHealthCallBack) {
        if (!userInfoCheck()) {
            e0.n("请填写测试用例和测试人");
            return false;
        }
        AppHealthInfoUtil.getInstance().setBaseInfo(this.mCaseName.getText().toString(), this.mUserName.getText().toString());
        AppHealthInfoUtil.getInstance().post(uploadAppHealthCallBack);
        return true;
    }

    private boolean userInfoCheck() {
        EditText editText;
        EditText editText2 = this.mCaseName;
        if (editText2 == null || TextUtils.isEmpty(editText2.getText().toString()) || (editText = this.mUserName) == null || TextUtils.isEmpty(editText.getText().toString())) {
            return false;
        }
        return true;
    }

    public boolean isCancellable() {
        return false;
    }
}
