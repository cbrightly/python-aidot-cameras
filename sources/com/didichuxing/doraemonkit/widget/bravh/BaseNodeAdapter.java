package com.didichuxing.doraemonkit.widget.bravh;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.DiffUtil;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseExpandNode;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.NodeFooterImp;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseItemProvider;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseNodeProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseNodeAdapter.kt */
public abstract class BaseNodeAdapter extends BaseProviderMultiAdapter<BaseNode> {
    private final HashSet<Integer> fullSpanNodeTypeSet;

    public BaseNodeAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    public final int collapse(@IntRange(from = 0) int i) {
        return collapse$default(this, i, false, false, (Object) null, 14, (Object) null);
    }

    public final int collapse(@IntRange(from = 0) int i, boolean z) {
        return collapse$default(this, i, z, false, (Object) null, 12, (Object) null);
    }

    public final int collapse(@IntRange(from = 0) int i, boolean z, boolean z2) {
        return collapse$default(this, i, z, z2, (Object) null, 8, (Object) null);
    }

    public final int collapseAndChild(@IntRange(from = 0) int i) {
        return collapseAndChild$default(this, i, false, false, (Object) null, 14, (Object) null);
    }

    public final int collapseAndChild(@IntRange(from = 0) int i, boolean z) {
        return collapseAndChild$default(this, i, z, false, (Object) null, 12, (Object) null);
    }

    public final int collapseAndChild(@IntRange(from = 0) int i, boolean z, boolean z2) {
        return collapseAndChild$default(this, i, z, z2, (Object) null, 8, (Object) null);
    }

    public final int expand(@IntRange(from = 0) int i) {
        return expand$default(this, i, false, false, (Object) null, 14, (Object) null);
    }

    public final int expand(@IntRange(from = 0) int i, boolean z) {
        return expand$default(this, i, z, false, (Object) null, 12, (Object) null);
    }

    public final int expand(@IntRange(from = 0) int i, boolean z, boolean z2) {
        return expand$default(this, i, z, z2, (Object) null, 8, (Object) null);
    }

    public final int expandAndChild(@IntRange(from = 0) int i) {
        return expandAndChild$default(this, i, false, false, (Object) null, 14, (Object) null);
    }

    public final int expandAndChild(@IntRange(from = 0) int i, boolean z) {
        return expandAndChild$default(this, i, z, false, (Object) null, 12, (Object) null);
    }

    public final int expandAndChild(@IntRange(from = 0) int i, boolean z, boolean z2) {
        return expandAndChild$default(this, i, z, z2, (Object) null, 8, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i) {
        expandAndCollapseOther$default(this, i, false, false, false, false, (Object) null, (Object) null, 126, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i, boolean z) {
        expandAndCollapseOther$default(this, i, z, false, false, false, (Object) null, (Object) null, 124, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i, boolean z, boolean z2) {
        expandAndCollapseOther$default(this, i, z, z2, false, false, (Object) null, (Object) null, 120, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i, boolean z, boolean z2, boolean z3) {
        expandAndCollapseOther$default(this, i, z, z2, z3, false, (Object) null, (Object) null, 112, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i, boolean z, boolean z2, boolean z3, boolean z4) {
        expandAndCollapseOther$default(this, i, z, z2, z3, z4, (Object) null, (Object) null, 96, (Object) null);
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int i, boolean z, boolean z2, boolean z3, boolean z4, @Nullable Object obj) {
        expandAndCollapseOther$default(this, i, z, z2, z3, z4, obj, (Object) null, 64, (Object) null);
    }

    public final int expandOrCollapse(@IntRange(from = 0) int i) {
        return expandOrCollapse$default(this, i, false, false, (Object) null, 14, (Object) null);
    }

    public final int expandOrCollapse(@IntRange(from = 0) int i, boolean z) {
        return expandOrCollapse$default(this, i, z, false, (Object) null, 12, (Object) null);
    }

    public final int expandOrCollapse(@IntRange(from = 0) int i, boolean z, boolean z2) {
        return expandOrCollapse$default(this, i, z, z2, (Object) null, 8, (Object) null);
    }

    public BaseNodeAdapter(@Nullable List<BaseNode> nodeList) {
        super((List) null);
        this.fullSpanNodeTypeSet = new HashSet<>();
        if (!(nodeList == null || nodeList.isEmpty())) {
            getData().addAll(flatData$default(this, nodeList, (Boolean) null, 2, (Object) null));
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseNodeAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final void addNodeProvider(@NotNull BaseNodeProvider provider) {
        k.f(provider, "provider");
        addItemProvider(provider);
    }

    public final void addFullSpanNodeProvider(@NotNull BaseNodeProvider provider) {
        k.f(provider, "provider");
        this.fullSpanNodeTypeSet.add(Integer.valueOf(provider.getItemViewType()));
        addItemProvider(provider);
    }

    public final void addFooterNodeProvider(@NotNull BaseNodeProvider provider) {
        k.f(provider, "provider");
        addFullSpanNodeProvider(provider);
    }

    public void addItemProvider(@NotNull BaseItemProvider<BaseNode> provider) {
        k.f(provider, "provider");
        if (provider instanceof BaseNodeProvider) {
            super.addItemProvider(provider);
            return;
        }
        throw new IllegalStateException("Please add BaseNodeProvider, no BaseItemProvider!");
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || this.fullSpanNodeTypeSet.contains(Integer.valueOf(type));
    }

    public void setNewInstance(@Nullable List<BaseNode> list) {
        super.setNewInstance(flatData$default(this, list != null ? list : new ArrayList<>(), (Boolean) null, 2, (Object) null));
    }

    public void setList(@Nullable Collection<? extends BaseNode> list) {
        super.setList(flatData$default(this, list != null ? list : new ArrayList<>(), (Boolean) null, 2, (Object) null));
    }

    public void addData(int position, @NotNull BaseNode data) {
        k.f(data, "data");
        addData(position, (Collection<? extends BaseNode>) q.c(data));
    }

    public void addData(@NotNull BaseNode data) {
        k.f(data, "data");
        addData((Collection<? extends BaseNode>) q.c(data));
    }

    public void addData(int position, @NotNull Collection<? extends BaseNode> newData) {
        k.f(newData, "newData");
        super.addData(position, flatData$default(this, newData, (Boolean) null, 2, (Object) null));
    }

    public void addData(@NotNull Collection<? extends BaseNode> newData) {
        k.f(newData, "newData");
        super.addData(flatData$default(this, newData, (Boolean) null, 2, (Object) null));
    }

    public void remove(int position) {
        notifyItemRangeRemoved(getHeaderLayoutCount() + position, removeAt(position));
        compatibilityDataSizeChanged(0);
    }

    public void setData(int index, @NotNull BaseNode data) {
        k.f(data, "data");
        int removeCount = removeAt(index);
        List newFlatData = flatData$default(this, q.c(data), (Boolean) null, 2, (Object) null);
        getData().addAll(index, newFlatData);
        if (removeCount == newFlatData.size()) {
            notifyItemRangeChanged(getHeaderLayoutCount() + index, removeCount);
            return;
        }
        notifyItemRangeRemoved(getHeaderLayoutCount() + index, removeCount);
        notifyItemRangeInserted(getHeaderLayoutCount() + index, newFlatData.size());
    }

    public void setDiffNewData(@Nullable List<BaseNode> list) {
        if (hasEmptyView()) {
            setNewInstance(list);
        } else {
            super.setDiffNewData(flatData$default(this, list != null ? list : new ArrayList<>(), (Boolean) null, 2, (Object) null));
        }
    }

    public void setDiffNewData(@NotNull DiffUtil.DiffResult diffResult, @NotNull List<BaseNode> list) {
        k.f(diffResult, "diffResult");
        k.f(list, "list");
        if (hasEmptyView()) {
            setNewInstance(list);
        } else {
            super.setDiffNewData(diffResult, flatData$default(this, list, (Boolean) null, 2, (Object) null));
        }
    }

    private final int removeAt(int position) {
        if (position >= getData().size()) {
            return 0;
        }
        int removeCount = removeChildAt(position);
        getData().remove(position);
        int removeCount2 = removeCount + 1;
        BaseNode node = (BaseNode) getData().get(position);
        if (!(node instanceof NodeFooterImp) || ((NodeFooterImp) node).getFooterNode() == null) {
            return removeCount2;
        }
        getData().remove(position);
        return removeCount2 + 1;
    }

    private final int removeChildAt(int position) {
        boolean z = false;
        if (position >= getData().size()) {
            return 0;
        }
        BaseNode node = (BaseNode) getData().get(position);
        List<BaseNode> childNode = node.getChildNode();
        if (childNode == null || childNode.isEmpty()) {
            z = true;
        }
        if (z) {
            return 0;
        }
        if (!(node instanceof BaseExpandNode)) {
            List<BaseNode> childNode2 = node.getChildNode();
            if (childNode2 == null) {
                k.n();
            }
            List items = flatData$default(this, childNode2, (Boolean) null, 2, (Object) null);
            getData().removeAll(items);
            return items.size();
        } else if (!((BaseExpandNode) node).isExpanded()) {
            return 0;
        } else {
            List<BaseNode> childNode3 = node.getChildNode();
            if (childNode3 == null) {
                k.n();
            }
            List items2 = flatData$default(this, childNode3, (Boolean) null, 2, (Object) null);
            getData().removeAll(items2);
            return items2.size();
        }
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, @NotNull BaseNode data) {
        k.f(parentNode, "parentNode");
        k.f(data, "data");
        List it = parentNode.getChildNode();
        if (it != null) {
            it.add(data);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
                addData(getData().indexOf(parentNode) + it.size(), data);
            }
        }
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, int childIndex, @NotNull BaseNode data) {
        k.f(parentNode, "parentNode");
        k.f(data, "data");
        List it = parentNode.getChildNode();
        if (it != null) {
            it.add(childIndex, data);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
                addData(getData().indexOf(parentNode) + 1 + childIndex, data);
            }
        }
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, int childIndex, @NotNull Collection<? extends BaseNode> newData) {
        k.f(parentNode, "parentNode");
        k.f(newData, "newData");
        List it = parentNode.getChildNode();
        if (it != null) {
            it.addAll(childIndex, newData);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
                addData(getData().indexOf(parentNode) + 1 + childIndex, newData);
            }
        }
    }

    public final void nodeRemoveData(@NotNull BaseNode parentNode, int childIndex) {
        k.f(parentNode, "parentNode");
        List it = parentNode.getChildNode();
        if (it != null && childIndex < it.size()) {
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
                remove(getData().indexOf(parentNode) + 1 + childIndex);
                BaseNode remove = it.remove(childIndex);
                return;
            }
            it.remove(childIndex);
        }
    }

    public final void nodeRemoveData(@NotNull BaseNode parentNode, @NotNull BaseNode childNode) {
        k.f(parentNode, "parentNode");
        k.f(childNode, "childNode");
        List it = parentNode.getChildNode();
        if (it == null) {
            return;
        }
        if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
            remove(childNode);
            it.remove(childNode);
            return;
        }
        it.remove(childNode);
    }

    public final void nodeSetData(@NotNull BaseNode parentNode, int childIndex, @NotNull BaseNode data) {
        k.f(parentNode, "parentNode");
        k.f(data, "data");
        List it = parentNode.getChildNode();
        if (it != null && childIndex < it.size()) {
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
                setData(getData().indexOf(parentNode) + 1 + childIndex, data);
                it.set(childIndex, data);
                return;
            }
            it.set(childIndex, data);
        }
    }

    public final void nodeReplaceChildData(@NotNull BaseNode parentNode, @NotNull Collection<? extends BaseNode> newData) {
        k.f(parentNode, "parentNode");
        k.f(newData, "newData");
        List it = parentNode.getChildNode();
        if (it == null) {
            return;
        }
        if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).isExpanded()) {
            int parentIndex = getData().indexOf(parentNode);
            int removeCount = removeChildAt(parentIndex);
            it.clear();
            it.addAll(newData);
            List newFlatData = flatData$default(this, newData, (Boolean) null, 2, (Object) null);
            getData().addAll(parentIndex + 1, newFlatData);
            int positionStart = parentIndex + 1 + getHeaderLayoutCount();
            if (removeCount == newFlatData.size()) {
                notifyItemRangeChanged(positionStart, removeCount);
                return;
            }
            notifyItemRangeRemoved(positionStart, removeCount);
            notifyItemRangeInserted(positionStart, newFlatData.size());
            return;
        }
        it.clear();
        it.addAll(newData);
    }

    static /* synthetic */ List flatData$default(BaseNodeAdapter baseNodeAdapter, Collection collection, Boolean bool, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                bool = null;
            }
            return baseNodeAdapter.flatData(collection, bool);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: flatData");
    }

    private final List<BaseNode> flatData(Collection<? extends BaseNode> list, Boolean isExpanded) {
        BaseNode it;
        ArrayList newList = new ArrayList();
        for (BaseNode element : list) {
            newList.add(element);
            boolean z = false;
            if (element instanceof BaseExpandNode) {
                if (k.a(isExpanded, true) || ((BaseExpandNode) element).isExpanded()) {
                    List childNode = element.getChildNode();
                    if (childNode == null || childNode.isEmpty()) {
                        z = true;
                    }
                    if (!z) {
                        newList.addAll(flatData(childNode, isExpanded));
                    }
                }
                if (isExpanded != null) {
                    ((BaseExpandNode) element).setExpanded(isExpanded.booleanValue());
                }
            } else {
                List childNode2 = element.getChildNode();
                if (childNode2 == null || childNode2.isEmpty()) {
                    z = true;
                }
                if (!z) {
                    newList.addAll(flatData(childNode2, isExpanded));
                }
            }
            if ((element instanceof NodeFooterImp) && (it = ((NodeFooterImp) element).getFooterNode()) != null) {
                newList.add(it);
            }
        }
        return newList;
    }

    static /* synthetic */ int collapse$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, boolean z3, Object obj, int i2, Object obj2) {
        boolean z4;
        boolean z5;
        Object obj3;
        if (obj2 == null) {
            boolean z6 = (i2 & 2) != 0 ? false : z;
            if ((i2 & 4) != 0) {
                z4 = true;
            } else {
                z4 = z2;
            }
            if ((i2 & 8) != 0) {
                z5 = true;
            } else {
                z5 = z3;
            }
            if ((i2 & 16) != 0) {
                obj3 = null;
            } else {
                obj3 = obj;
            }
            return baseNodeAdapter.collapse(i, z6, z4, z5, obj3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapse");
    }

    private final int collapse(@IntRange(from = 0) int position, boolean isChangeChildCollapse, boolean animate, boolean notify, Object parentPayload) {
        BaseNode node = (BaseNode) getData().get(position);
        if (!(node instanceof BaseExpandNode) || !((BaseExpandNode) node).isExpanded()) {
            return 0;
        }
        int adapterPosition = getHeaderLayoutCount() + position;
        ((BaseExpandNode) node).setExpanded(false);
        List<BaseNode> childNode = node.getChildNode();
        if (childNode == null || childNode.isEmpty()) {
            notifyItemChanged(adapterPosition, parentPayload);
            return 0;
        }
        List<BaseNode> childNode2 = node.getChildNode();
        if (childNode2 == null) {
            k.n();
        }
        List items = flatData(childNode2, isChangeChildCollapse ? false : null);
        int size = items.size();
        getData().removeAll(items);
        if (notify) {
            if (animate) {
                notifyItemChanged(adapterPosition, parentPayload);
                notifyItemRangeRemoved(adapterPosition + 1, size);
            } else {
                notifyDataSetChanged();
            }
        }
        return size;
    }

    static /* synthetic */ int expand$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, boolean z3, Object obj, int i2, Object obj2) {
        boolean z4;
        boolean z5;
        Object obj3;
        if (obj2 == null) {
            boolean z6 = (i2 & 2) != 0 ? false : z;
            if ((i2 & 4) != 0) {
                z4 = true;
            } else {
                z4 = z2;
            }
            if ((i2 & 8) != 0) {
                z5 = true;
            } else {
                z5 = z3;
            }
            if ((i2 & 16) != 0) {
                obj3 = null;
            } else {
                obj3 = obj;
            }
            return baseNodeAdapter.expand(i, z6, z4, z5, obj3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expand");
    }

    private final int expand(@IntRange(from = 0) int position, boolean isChangeChildExpand, boolean animate, boolean notify, Object parentPayload) {
        BaseNode node = (BaseNode) getData().get(position);
        if (!(node instanceof BaseExpandNode) || ((BaseExpandNode) node).isExpanded()) {
            return 0;
        }
        int adapterPosition = getHeaderLayoutCount() + position;
        ((BaseExpandNode) node).setExpanded(true);
        List<BaseNode> childNode = node.getChildNode();
        if (childNode == null || childNode.isEmpty()) {
            notifyItemChanged(adapterPosition, parentPayload);
            return 0;
        }
        List<BaseNode> childNode2 = node.getChildNode();
        if (childNode2 == null) {
            k.n();
        }
        List items = flatData(childNode2, isChangeChildExpand ? true : null);
        int size = items.size();
        getData().addAll(position + 1, items);
        if (notify) {
            if (animate) {
                notifyItemChanged(adapterPosition, parentPayload);
                notifyItemRangeInserted(adapterPosition + 1, size);
            } else {
                notifyDataSetChanged();
            }
        }
        return size;
    }

    public static /* synthetic */ int collapse$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                z = true;
            }
            if ((i2 & 4) != 0) {
                z2 = true;
            }
            if ((i2 & 8) != 0) {
                obj = null;
            }
            return baseNodeAdapter.collapse(i, z, z2, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapse");
    }

    public final int collapse(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return collapse(position, false, animate, notify, parentPayload);
    }

    public static /* synthetic */ int expand$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                z = true;
            }
            if ((i2 & 4) != 0) {
                z2 = true;
            }
            if ((i2 & 8) != 0) {
                obj = null;
            }
            return baseNodeAdapter.expand(i, z, z2, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expand");
    }

    public final int expand(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return expand(position, false, animate, notify, parentPayload);
    }

    public static /* synthetic */ int expandOrCollapse$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                z = true;
            }
            if ((i2 & 4) != 0) {
                z2 = true;
            }
            if ((i2 & 8) != 0) {
                obj = null;
            }
            return baseNodeAdapter.expandOrCollapse(i, z, z2, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandOrCollapse");
    }

    public final int expandOrCollapse(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        BaseNode node = (BaseNode) getData().get(position);
        if (!(node instanceof BaseExpandNode)) {
            return 0;
        }
        if (((BaseExpandNode) node).isExpanded()) {
            return collapse(position, false, animate, notify, parentPayload);
        }
        return expand(position, false, animate, notify, parentPayload);
    }

    public static /* synthetic */ int expandAndChild$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                z = true;
            }
            if ((i2 & 4) != 0) {
                z2 = true;
            }
            if ((i2 & 8) != 0) {
                obj = null;
            }
            return baseNodeAdapter.expandAndChild(i, z, z2, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandAndChild");
    }

    public final int expandAndChild(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return expand(position, true, animate, notify, parentPayload);
    }

    public static /* synthetic */ int collapseAndChild$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                z = true;
            }
            if ((i2 & 4) != 0) {
                z2 = true;
            }
            if ((i2 & 8) != 0) {
                obj = null;
            }
            return baseNodeAdapter.collapseAndChild(i, z, z2, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapseAndChild");
    }

    public final int collapseAndChild(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return collapse(position, true, animate, notify, parentPayload);
    }

    public static /* synthetic */ void expandAndCollapseOther$default(BaseNodeAdapter baseNodeAdapter, int i, boolean z, boolean z2, boolean z3, boolean z4, Object obj, Object obj2, int i2, Object obj3) {
        boolean z5;
        boolean z6;
        Object obj4;
        if (obj3 == null) {
            boolean z7 = (i2 & 2) != 0 ? false : z;
            boolean z8 = true;
            if ((i2 & 4) != 0) {
                z5 = true;
            } else {
                z5 = z2;
            }
            if ((i2 & 8) != 0) {
                z6 = true;
            } else {
                z6 = z3;
            }
            if ((i2 & 16) == 0) {
                z8 = z4;
            }
            Object obj5 = null;
            if ((i2 & 32) != 0) {
                obj4 = null;
            } else {
                obj4 = obj;
            }
            if ((i2 & 64) == 0) {
                obj5 = obj2;
            }
            baseNodeAdapter.expandAndCollapseOther(i, z7, z5, z6, z8, obj4, obj5);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandAndCollapseOther");
    }

    public final void expandAndCollapseOther(@IntRange(from = 0) int position, boolean isExpandedChild, boolean isCollapseChild, boolean animate, boolean notify, @Nullable Object expandPayload, @Nullable Object collapsePayload) {
        int firstPosition;
        int dataSize;
        int expandCount = expand(position, isExpandedChild, animate, notify, expandPayload);
        if (expandCount != 0) {
            int parentPosition = findParentNode(position);
            int dataSize2 = 0;
            if (parentPosition == -1) {
                firstPosition = 0;
            } else {
                firstPosition = parentPosition + 1;
            }
            int newPosition = position;
            if (position - firstPosition > 0) {
                int i = firstPosition;
                do {
                    i++;
                    newPosition -= collapse(i, isCollapseChild, animate, notify, collapsePayload);
                } while (i < newPosition);
            }
            if (parentPosition == -1) {
                dataSize = getData().size() - 1;
            } else {
                List<BaseNode> childNode = ((BaseNode) getData().get(parentPosition)).getChildNode();
                if (childNode != null) {
                    dataSize2 = childNode.size();
                }
                dataSize = parentPosition + dataSize2 + expandCount;
            }
            if (newPosition + expandCount < dataSize) {
                int i2 = newPosition + expandCount + 1;
                while (i2 <= dataSize) {
                    i2++;
                    dataSize -= collapse(i2, isCollapseChild, animate, notify, collapsePayload);
                }
            }
        }
    }

    public final int findParentNode(@NotNull BaseNode node) {
        k.f(node, "node");
        int pos = getData().indexOf(node);
        if (pos == -1 || pos == 0) {
            return -1;
        }
        for (int i = pos - 1; i >= 0; i--) {
            List<BaseNode> childNode = ((BaseNode) getData().get(i)).getChildNode();
            if (childNode != null && childNode.contains(node)) {
                return i;
            }
        }
        return -1;
    }

    public final int findParentNode(@IntRange(from = 0) int position) {
        if (position == 0) {
            return -1;
        }
        BaseNode node = (BaseNode) getData().get(position);
        for (int i = position - 1; i >= 0; i--) {
            List<BaseNode> childNode = ((BaseNode) getData().get(i)).getChildNode();
            if (childNode != null && childNode.contains(node)) {
                return i;
            }
        }
        return -1;
    }
}
