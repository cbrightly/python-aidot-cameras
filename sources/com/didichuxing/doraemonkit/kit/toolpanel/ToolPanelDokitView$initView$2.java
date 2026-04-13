package com.didichuxing.doraemonkit.kit.toolpanel;

import androidx.recyclerview.widget.GridLayoutManager;
import com.didichuxing.doraemonkit.widget.bravh.listener.GridSpanSizeLookup;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"Landroidx/recyclerview/widget/GridLayoutManager;", "<anonymous parameter 0>", "", "viewType", "<anonymous parameter 2>", "getSpanSize", "(Landroidx/recyclerview/widget/GridLayoutManager;II)I", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: ToolPanelDokitView.kt */
public final class ToolPanelDokitView$initView$2 implements GridSpanSizeLookup {
    public static final ToolPanelDokitView$initView$2 INSTANCE = new ToolPanelDokitView$initView$2();

    ToolPanelDokitView$initView$2() {
    }

    public final int getSpanSize(@NotNull GridLayoutManager $noName_0, int viewType, int $noName_2) {
        k.f($noName_0, "<anonymous parameter 0>");
        if (viewType == 201) {
            return 1;
        }
        return 4;
    }
}
