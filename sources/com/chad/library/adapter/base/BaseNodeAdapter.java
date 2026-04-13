package com.chad.library.adapter.base;

import com.chad.library.adapter.base.entity.node.a;
import com.chad.library.adapter.base.entity.node.b;
import com.chad.library.adapter.base.entity.node.c;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseNodeAdapter.kt */
public abstract class BaseNodeAdapter extends BaseProviderMultiAdapter<b> {
    private final HashSet<Integer> O4;

    public BaseNodeAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    public BaseNodeAdapter(@Nullable List<b> nodeList) {
        super((List) null);
        this.O4 = new HashSet<>();
        if (!(nodeList == null || nodeList.isEmpty())) {
            getData().addAll(C(this, nodeList, (Boolean) null, 2, (Object) null));
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseNodeAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || this.O4.contains(Integer.valueOf(type));
    }

    static /* synthetic */ List C(BaseNodeAdapter baseNodeAdapter, Collection collection, Boolean bool, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                bool = null;
            }
            return baseNodeAdapter.flatData(collection, bool);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: flatData");
    }

    private final List<b> flatData(Collection<? extends b> list, Boolean isExpanded) {
        b it;
        ArrayList newList = new ArrayList();
        for (b element : list) {
            newList.add(element);
            boolean z = false;
            if (element instanceof a) {
                if (k.a(isExpanded, true) || ((a) element).b()) {
                    List childNode = element.a();
                    if (childNode == null || childNode.isEmpty()) {
                        z = true;
                    }
                    if (!z) {
                        newList.addAll(flatData(childNode, isExpanded));
                    }
                }
                if (isExpanded != null) {
                    ((a) element).c(isExpanded.booleanValue());
                }
            } else {
                List childNode2 = element.a();
                if (childNode2 == null || childNode2.isEmpty()) {
                    z = true;
                }
                if (!z) {
                    newList.addAll(flatData(childNode2, isExpanded));
                }
            }
            if ((element instanceof c) && (it = ((c) element).getFooterNode()) != null) {
                newList.add(it);
            }
        }
        return newList;
    }
}
