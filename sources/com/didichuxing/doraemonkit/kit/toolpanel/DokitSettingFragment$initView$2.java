package com.didichuxing.doraemonkit.kit.toolpanel;

import android.content.Intent;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemClickListener;
import kotlin.jvm.internal.k;
import kotlin.l;
import net.sqlcipher.database.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\t\u001a\u00020\u00062\u000e\u0010\u0001\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"Lcom/didichuxing/doraemonkit/widget/bravh/BaseQuickAdapter;", "<anonymous parameter 0>", "Landroid/view/View;", "<anonymous parameter 1>", "", "position", "Lkotlin/x;", "onItemClick", "(Lcom/didichuxing/doraemonkit/widget/bravh/BaseQuickAdapter;Landroid/view/View;I)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitSettingFragment.kt */
public final class DokitSettingFragment$initView$2 implements OnItemClickListener {
    final /* synthetic */ DokitSettingFragment this$0;

    DokitSettingFragment$initView$2(DokitSettingFragment dokitSettingFragment) {
        this.this$0 = dokitSettingFragment;
    }

    public final void onItemClick(@NotNull BaseQuickAdapter<?, ?> $noName_0, @NotNull View $noName_1, int position) {
        k.f($noName_0, "<anonymous parameter 0>");
        k.f($noName_1, "<anonymous parameter 1>");
        switch (position) {
            case 0:
                if (this.this$0.getActivity() != null) {
                    Intent intent = new Intent(this.this$0.getActivity(), UniversalActivity.class);
                    intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    intent.putExtra(BundleKey.FRAGMENT_INDEX, 30);
                    FragmentActivity activity = this.this$0.getActivity();
                    if (activity == null) {
                        k.n();
                    }
                    activity.startActivity(intent);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
