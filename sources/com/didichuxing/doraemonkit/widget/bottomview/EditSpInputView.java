package com.didichuxing.doraemonkit.widget.bottomview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.didichuxing.doraemonkit.kit.fileexplorer.SpBean;

public class EditSpInputView extends AssociationView {
    private final EditText editText;
    private SpBean spBean;

    public EditSpInputView(Context context, SpBean spBean2, int inputType) {
        this.spBean = spBean2;
        EditText editText2 = new EditText(context);
        this.editText = editText2;
        editText2.setText(spBean2.value.toString());
        editText2.setInputType(131072 | inputType);
        editText2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        editText2.setSelection(spBean2.value.toString().length());
    }

    public Object submit() {
        return this.spBean.toDefaultClass(this.editText.getText().toString());
    }

    public void cancel() {
    }

    public View getView() {
        return this.editText;
    }

    public boolean isCanSubmit() {
        return true;
    }

    public void onShow() {
    }

    public void onHide() {
    }
}
