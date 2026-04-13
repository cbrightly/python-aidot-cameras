package com.didichuxing.doraemonkit.widget.dialog;

public interface DialogListener {
    void onCancel();

    boolean onNegative();

    boolean onPositive();
}
