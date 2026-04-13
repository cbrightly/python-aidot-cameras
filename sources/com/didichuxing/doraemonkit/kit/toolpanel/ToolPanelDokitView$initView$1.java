package com.didichuxing.doraemonkit.kit.toolpanel;

import android.content.Intent;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: ToolPanelDokitView.kt */
public final class ToolPanelDokitView$initView$1 implements TitleBar.OnTitleBarClickListener {
    final /* synthetic */ ToolPanelDokitView this$0;

    ToolPanelDokitView$initView$1(ToolPanelDokitView $outer) {
        this.this$0 = $outer;
    }

    public void onLeftClick() {
        this.this$0.detach();
    }

    public void onRightClick() {
        if (!this.this$0.isNormalMode()) {
            DoraemonKit.hideToolPanel();
        }
        if (this.this$0.getActivity() != null) {
            Intent intent = new Intent(this.this$0.getActivity(), UniversalActivity.class);
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.putExtra(BundleKey.FRAGMENT_INDEX, 29);
            this.this$0.getActivity().startActivity(intent);
        }
    }
}
