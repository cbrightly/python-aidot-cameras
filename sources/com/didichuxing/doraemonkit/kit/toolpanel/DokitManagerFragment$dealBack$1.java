package com.didichuxing.doraemonkit.kit.toolpanel;

import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.widget.dialog.SimpleDialogListener;

/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$dealBack$1 extends SimpleDialogListener {
    final /* synthetic */ DokitManagerFragment this$0;

    DokitManagerFragment$dealBack$1(DokitManagerFragment $outer) {
        this.this$0 = $outer;
    }

    public boolean onPositive() {
        this.this$0.saveSystemKits();
        this.this$0.finish();
        return true;
    }

    public boolean onNegative() {
        DokitConstant.GLOBAL_KITS.putAll(this.this$0.mBakGlobalKits);
        this.this$0.finish();
        return true;
    }
}
