package com.didichuxing.doraemonkit.widget.bravh;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapterModuleImp;
import com.didichuxing.doraemonkit.widget.bravh.animation.AlphaInAnimation;
import com.didichuxing.doraemonkit.widget.bravh.animation.BaseAnimation;
import com.didichuxing.doraemonkit.widget.bravh.animation.ScaleInAnimation;
import com.didichuxing.doraemonkit.widget.bravh.animation.SlideInBottomAnimation;
import com.didichuxing.doraemonkit.widget.bravh.animation.SlideInLeftAnimation;
import com.didichuxing.doraemonkit.widget.bravh.animation.SlideInRightAnimation;
import com.didichuxing.doraemonkit.widget.bravh.diff.BrvahAsyncDiffer;
import com.didichuxing.doraemonkit.widget.bravh.diff.BrvahAsyncDifferConfig;
import com.didichuxing.doraemonkit.widget.bravh.diff.BrvahListUpdateCallback;
import com.didichuxing.doraemonkit.widget.bravh.listener.BaseListenerImp;
import com.didichuxing.doraemonkit.widget.bravh.listener.GridSpanSizeLookup;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemChildClickListener;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemChildLongClickListener;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemClickListener;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemLongClickListener;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseDraggableModule;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseLoadMoreModule;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseUpFetchModule;
import com.didichuxing.doraemonkit.widget.bravh.module.DraggableModule;
import com.didichuxing.doraemonkit.widget.bravh.module.LoadMoreModule;
import com.didichuxing.doraemonkit.widget.bravh.module.UpFetchModule;
import com.didichuxing.doraemonkit.widget.bravh.util.AdapterUtilsKt;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseQuickAdapter.kt */
public abstract class BaseQuickAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> implements BaseQuickAdapterModuleImp, BaseListenerImp {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int EMPTY_VIEW = 268436821;
    public static final int FOOTER_VIEW = 268436275;
    public static final int HEADER_VIEW = 268435729;
    public static final int LOAD_MORE_VIEW = 268436002;
    @Nullable
    private BaseAnimation adapterAnimation;
    private boolean animationEnable;
    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> childLongClickViewIds;
    @Nullable
    private Context context;
    @NotNull
    private List<T> data;
    private boolean footerViewAsFlow;
    private boolean footerWithEmptyEnable;
    private boolean headerViewAsFlow;
    private boolean headerWithEmptyEnable;
    private boolean isAnimationFirstOnly;
    private boolean isUseEmpty;
    private final int layoutResId;
    private BrvahAsyncDiffer<T> mDiffHelper;
    private BaseDraggableModule mDraggableModule;
    /* access modifiers changed from: private */
    public FrameLayout mEmptyLayout;
    /* access modifiers changed from: private */
    public LinearLayout mFooterLayout;
    /* access modifiers changed from: private */
    public LinearLayout mHeaderLayout;
    private int mLastPosition;
    private BaseLoadMoreModule mLoadMoreModule;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    /* access modifiers changed from: private */
    public GridSpanSizeLookup mSpanSizeLookup;
    private BaseUpFetchModule mUpFetchModule;
    @NotNull
    public WeakReference<RecyclerView> weakRecyclerView;

    /* compiled from: BaseQuickAdapter.kt */
    public enum AnimationType {
        AlphaIn,
        ScaleIn,
        SlideInBottom,
        SlideInLeft,
        SlideInRight
    }

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 4, 0})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimationType.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[AnimationType.AlphaIn.ordinal()] = 1;
            iArr[AnimationType.ScaleIn.ordinal()] = 2;
            iArr[AnimationType.SlideInBottom.ordinal()] = 3;
            iArr[AnimationType.SlideInLeft.ordinal()] = 4;
            iArr[AnimationType.SlideInRight.ordinal()] = 5;
        }
    }

    public BaseQuickAdapter(@LayoutRes int i) {
        this(i, (List) null, 2, (DefaultConstructorMarker) null);
    }

    public final int addFooterView(@NotNull View view) {
        return addFooterView$default(this, view, 0, 0, 6, (Object) null);
    }

    public final int addFooterView(@NotNull View view, int i) {
        return addFooterView$default(this, view, i, 0, 4, (Object) null);
    }

    public final int addHeaderView(@NotNull View view) {
        return addHeaderView$default(this, view, 0, 0, 6, (Object) null);
    }

    public final int addHeaderView(@NotNull View view, int i) {
        return addHeaderView$default(this, view, i, 0, 4, (Object) null);
    }

    /* access modifiers changed from: protected */
    public abstract void convert(@NotNull VH vh, T t);

    public final int setFooterView(@NotNull View view) {
        return setFooterView$default(this, view, 0, 0, 6, (Object) null);
    }

    public final int setFooterView(@NotNull View view, int i) {
        return setFooterView$default(this, view, i, 0, 4, (Object) null);
    }

    public final int setHeaderView(@NotNull View view) {
        return setHeaderView$default(this, view, 0, 0, 6, (Object) null);
    }

    public final int setHeaderView(@NotNull View view, int i) {
        return setHeaderView$default(this, view, i, 0, 4, (Object) null);
    }

    public static final /* synthetic */ FrameLayout access$getMEmptyLayout$p(BaseQuickAdapter $this) {
        FrameLayout frameLayout = $this.mEmptyLayout;
        if (frameLayout == null) {
            k.t("mEmptyLayout");
        }
        return frameLayout;
    }

    public static final /* synthetic */ LinearLayout access$getMFooterLayout$p(BaseQuickAdapter $this) {
        LinearLayout linearLayout = $this.mFooterLayout;
        if (linearLayout == null) {
            k.t("mFooterLayout");
        }
        return linearLayout;
    }

    public static final /* synthetic */ LinearLayout access$getMHeaderLayout$p(BaseQuickAdapter $this) {
        LinearLayout linearLayout = $this.mHeaderLayout;
        if (linearLayout == null) {
            k.t("mHeaderLayout");
        }
        return linearLayout;
    }

    @NotNull
    public BaseDraggableModule addDraggableModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        k.f(baseQuickAdapter, "baseQuickAdapter");
        return BaseQuickAdapterModuleImp.DefaultImpls.addDraggableModule(this, baseQuickAdapter);
    }

    @NotNull
    public BaseLoadMoreModule addLoadMoreModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        k.f(baseQuickAdapter, "baseQuickAdapter");
        return BaseQuickAdapterModuleImp.DefaultImpls.addLoadMoreModule(this, baseQuickAdapter);
    }

    @NotNull
    public BaseUpFetchModule addUpFetchModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        k.f(baseQuickAdapter, "baseQuickAdapter");
        return BaseQuickAdapterModuleImp.DefaultImpls.addUpFetchModule(this, baseQuickAdapter);
    }

    public BaseQuickAdapter(@LayoutRes int layoutResId2, @Nullable List<T> data2) {
        this.layoutResId = layoutResId2;
        this.data = data2 != null ? data2 : new ArrayList<>();
        this.isUseEmpty = true;
        this.isAnimationFirstOnly = true;
        this.mLastPosition = -1;
        checkModule();
        this.childClickViewIds = new LinkedHashSet<>();
        this.childLongClickViewIds = new LinkedHashSet<>();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseQuickAdapter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list);
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @NotNull
    public final List<T> getData() {
        return this.data;
    }

    public final void setData$doraemonkit_release(@NotNull List<T> list) {
        k.f(list, "<set-?>");
        this.data = list;
    }

    public final boolean getHeaderWithEmptyEnable() {
        return this.headerWithEmptyEnable;
    }

    public final void setHeaderWithEmptyEnable(boolean z) {
        this.headerWithEmptyEnable = z;
    }

    public final boolean getFooterWithEmptyEnable() {
        return this.footerWithEmptyEnable;
    }

    public final void setFooterWithEmptyEnable(boolean z) {
        this.footerWithEmptyEnable = z;
    }

    public final boolean isUseEmpty() {
        return this.isUseEmpty;
    }

    public final void setUseEmpty(boolean z) {
        this.isUseEmpty = z;
    }

    public final boolean getHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public final void setHeaderViewAsFlow(boolean z) {
        this.headerViewAsFlow = z;
    }

    public final boolean getFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public final void setFooterViewAsFlow(boolean z) {
        this.footerViewAsFlow = z;
    }

    public final boolean getAnimationEnable() {
        return this.animationEnable;
    }

    public final void setAnimationEnable(boolean z) {
        this.animationEnable = z;
    }

    public final boolean isAnimationFirstOnly() {
        return this.isAnimationFirstOnly;
    }

    public final void setAnimationFirstOnly(boolean z) {
        this.isAnimationFirstOnly = z;
    }

    @Nullable
    public final BaseAnimation getAdapterAnimation() {
        return this.adapterAnimation;
    }

    public final void setAdapterAnimation(@Nullable BaseAnimation value) {
        this.animationEnable = true;
        this.adapterAnimation = value;
    }

    @NotNull
    public final BaseLoadMoreModule getLoadMoreModule() {
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            if (baseLoadMoreModule == null) {
                k.n();
            }
            return baseLoadMoreModule;
        }
        throw new IllegalStateException("Please first implements LoadMoreModule".toString());
    }

    @NotNull
    public final BaseUpFetchModule getUpFetchModule() {
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule != null) {
            if (baseUpFetchModule == null) {
                k.n();
            }
            return baseUpFetchModule;
        }
        throw new IllegalStateException("Please first implements UpFetchModule".toString());
    }

    @NotNull
    public final BaseDraggableModule getDraggableModule() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            if (baseDraggableModule == null) {
                k.n();
            }
            return baseDraggableModule;
        }
        throw new IllegalStateException("Please first implements DraggableModule".toString());
    }

    @Nullable
    public final Context getContext() {
        return this.context;
    }

    public final void setContext(@Nullable Context context2) {
        this.context = context2;
    }

    @NotNull
    public final WeakReference<RecyclerView> getWeakRecyclerView() {
        WeakReference<RecyclerView> weakReference = this.weakRecyclerView;
        if (weakReference == null) {
            k.t("weakRecyclerView");
        }
        return weakReference;
    }

    public final void setWeakRecyclerView(@NotNull WeakReference<RecyclerView> weakReference) {
        k.f(weakReference, "<set-?>");
        this.weakRecyclerView = weakReference;
    }

    private final void checkModule() {
        if (this instanceof LoadMoreModule) {
            this.mLoadMoreModule = addLoadMoreModule(this);
        }
        if (this instanceof UpFetchModule) {
            this.mUpFetchModule = addUpFetchModule(this);
        }
        if (this instanceof DraggableModule) {
            this.mDraggableModule = addDraggableModule(this);
        }
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull VH holder, T item, @NotNull List<? extends Object> payloads) {
        k.f(holder, "holder");
        k.f(payloads, "payloads");
    }

    @NotNull
    public VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        switch (viewType) {
            case HEADER_VIEW /*268435729*/:
                LinearLayout linearLayout = this.mHeaderLayout;
                if (linearLayout == null) {
                    k.t("mHeaderLayout");
                }
                ViewParent headerLayoutVp = linearLayout.getParent();
                if (headerLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) headerLayoutVp;
                    LinearLayout linearLayout2 = this.mHeaderLayout;
                    if (linearLayout2 == null) {
                        k.t("mHeaderLayout");
                    }
                    viewGroup.removeView(linearLayout2);
                }
                LinearLayout linearLayout3 = this.mHeaderLayout;
                if (linearLayout3 == null) {
                    k.t("mHeaderLayout");
                }
                return createBaseViewHolder(linearLayout3);
            case LOAD_MORE_VIEW /*268436002*/:
                BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
                if (baseLoadMoreModule == null) {
                    k.n();
                }
                BaseViewHolder baseViewHolder = createBaseViewHolder(baseLoadMoreModule.getLoadMoreView().getRootView(parent));
                BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
                if (baseLoadMoreModule2 == null) {
                    k.n();
                }
                baseLoadMoreModule2.setupViewHolder$doraemonkit_release(baseViewHolder);
                return baseViewHolder;
            case FOOTER_VIEW /*268436275*/:
                LinearLayout linearLayout4 = this.mFooterLayout;
                if (linearLayout4 == null) {
                    k.t("mFooterLayout");
                }
                ViewParent footerLayoutVp = linearLayout4.getParent();
                if (footerLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) footerLayoutVp;
                    LinearLayout linearLayout5 = this.mFooterLayout;
                    if (linearLayout5 == null) {
                        k.t("mFooterLayout");
                    }
                    viewGroup2.removeView(linearLayout5);
                }
                LinearLayout linearLayout6 = this.mFooterLayout;
                if (linearLayout6 == null) {
                    k.t("mFooterLayout");
                }
                return createBaseViewHolder(linearLayout6);
            case EMPTY_VIEW /*268436821*/:
                FrameLayout frameLayout = this.mEmptyLayout;
                if (frameLayout == null) {
                    k.t("mEmptyLayout");
                }
                ViewParent emptyLayoutVp = frameLayout.getParent();
                if (emptyLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup3 = (ViewGroup) emptyLayoutVp;
                    FrameLayout frameLayout2 = this.mEmptyLayout;
                    if (frameLayout2 == null) {
                        k.t("mEmptyLayout");
                    }
                    viewGroup3.removeView(frameLayout2);
                }
                FrameLayout frameLayout3 = this.mEmptyLayout;
                if (frameLayout3 == null) {
                    k.t("mEmptyLayout");
                }
                return createBaseViewHolder(frameLayout3);
            default:
                BaseViewHolder viewHolder = onCreateDefViewHolder(parent, viewType);
                bindViewClickListener(viewHolder, viewType);
                BaseDraggableModule baseDraggableModule = this.mDraggableModule;
                if (baseDraggableModule != null) {
                    baseDraggableModule.initView$doraemonkit_release(viewHolder);
                }
                onItemViewHolderCreated(viewHolder, viewType);
                return viewHolder;
        }
    }

    public int getItemCount() {
        if (hasEmptyView()) {
            int count = 1;
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                count = 1 + 1;
            }
            if (!this.footerWithEmptyEnable || !hasFooterLayout()) {
                return count;
            }
            return count + 1;
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        int loadMoreCount = 1;
        if (baseLoadMoreModule == null || !baseLoadMoreModule.hasLoadMoreView()) {
            loadMoreCount = 0;
        }
        return getHeaderLayoutCount() + getDefItemCount() + getFooterLayoutCount() + loadMoreCount;
    }

    public int getItemViewType(int position) {
        int adjPosition;
        int numFooters = 0;
        if (hasEmptyView()) {
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                numFooters = 1;
            }
            int i = numFooters;
            switch (position) {
                case 0:
                    if (i != 0) {
                        return HEADER_VIEW;
                    }
                    return EMPTY_VIEW;
                case 1:
                    if (i != 0) {
                        return EMPTY_VIEW;
                    }
                    return FOOTER_VIEW;
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;
            }
        } else {
            boolean header = hasHeaderLayout();
            if (header && position == 0) {
                return HEADER_VIEW;
            }
            if (header) {
                adjPosition = position - 1;
            } else {
                adjPosition = position;
            }
            int dataSize = this.data.size();
            if (adjPosition < dataSize) {
                return getDefItemViewType(adjPosition);
            }
            int adjPosition2 = adjPosition - dataSize;
            if (hasFooterLayout()) {
                numFooters = 1;
            }
            if (adjPosition2 < numFooters) {
                return FOOTER_VIEW;
            }
            return LOAD_MORE_VIEW;
        }
    }

    public void onBindViewHolder(@NotNull VH holder, int position) {
        k.f(holder, "holder");
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule != null) {
            baseUpFetchModule.autoUpFetch$doraemonkit_release(position);
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.autoLoadMore$doraemonkit_release(position);
        }
        switch (holder.getItemViewType()) {
            case HEADER_VIEW /*268435729*/:
            case FOOTER_VIEW /*268436275*/:
            case EMPTY_VIEW /*268436821*/:
                return;
            case LOAD_MORE_VIEW /*268436002*/:
                BaseLoadMoreModule it = this.mLoadMoreModule;
                if (it != null) {
                    it.getLoadMoreView().convert(holder, position, it.getLoadMoreStatus());
                    return;
                }
                return;
            default:
                convert(holder, getItem(position - getHeaderLayoutCount()));
                return;
        }
    }

    public void onBindViewHolder(@NotNull VH holder, int position, @NotNull List<Object> payloads) {
        k.f(holder, "holder");
        k.f(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        BaseUpFetchModule baseUpFetchModule = this.mUpFetchModule;
        if (baseUpFetchModule != null) {
            baseUpFetchModule.autoUpFetch$doraemonkit_release(position);
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.autoLoadMore$doraemonkit_release(position);
        }
        switch (holder.getItemViewType()) {
            case HEADER_VIEW /*268435729*/:
            case FOOTER_VIEW /*268436275*/:
            case EMPTY_VIEW /*268436821*/:
                return;
            case LOAD_MORE_VIEW /*268436002*/:
                BaseLoadMoreModule it = this.mLoadMoreModule;
                if (it != null) {
                    it.getLoadMoreView().convert(holder, position, it.getLoadMoreStatus());
                    return;
                }
                return;
            default:
                convert(holder, getItem(position - getHeaderLayoutCount()), payloads);
                return;
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void onViewAttachedToWindow(@NotNull VH holder) {
        k.f(holder, "holder");
        super.onViewAttachedToWindow(holder);
        if (isFixedViewType(holder.getItemViewType())) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        k.f(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        this.weakRecyclerView = new WeakReference<>(recyclerView);
        this.context = recyclerView.getContext();
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            baseDraggableModule.attachToRecyclerView(recyclerView);
        }
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new BaseQuickAdapter$onAttachedToRecyclerView$1(this, manager, ((GridLayoutManager) manager).getSpanSizeLookup()));
        }
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return type == 268436821 || type == 268435729 || type == 268436275 || type == 268436002;
    }

    public T getItem(@IntRange(from = 0) int position) {
        return this.data.get(position);
    }

    @Nullable
    public T getItemOrNull(@IntRange(from = 0) int position) {
        return y.V(this.data, position);
    }

    public int getItemPosition(@Nullable T item) {
        if (item == null || !(!this.data.isEmpty())) {
            return -1;
        }
        return this.data.indexOf(item);
    }

    @NotNull
    public final LinkedHashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    public final void addChildClickViewIds(@NotNull @IdRes int... viewIds) {
        k.f(viewIds, "viewIds");
        for (int viewId : viewIds) {
            this.childClickViewIds.add(Integer.valueOf(viewId));
        }
    }

    @NotNull
    public final LinkedHashSet<Integer> getChildLongClickViewIds() {
        return this.childLongClickViewIds;
    }

    public final void addChildLongClickViewIds(@NotNull @IdRes int... viewIds) {
        k.f(viewIds, "viewIds");
        for (int viewId : viewIds) {
            this.childLongClickViewIds.add(Integer.valueOf(viewId));
        }
    }

    /* access modifiers changed from: protected */
    public void bindViewClickListener(@NotNull VH viewHolder, int viewType) {
        k.f(viewHolder, "viewHolder");
        if (this.mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$1(this, viewHolder));
        }
        if (this.mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$2(this, viewHolder));
        }
        if (this.mOnItemChildClickListener != null) {
            Iterator it = getChildClickViewIds().iterator();
            while (it.hasNext()) {
                Integer id = (Integer) it.next();
                View view = viewHolder.itemView;
                k.b(id, "id");
                View childView = view.findViewById(id.intValue());
                if (childView != null) {
                    if (!childView.isClickable()) {
                        childView.setClickable(true);
                    }
                    childView.setOnClickListener(new BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$3(this, viewHolder));
                }
            }
        }
        if (this.mOnItemChildLongClickListener != null) {
            Iterator it2 = getChildLongClickViewIds().iterator();
            while (it2.hasNext()) {
                Integer id2 = (Integer) it2.next();
                View view2 = viewHolder.itemView;
                k.b(id2, "id");
                View childView2 = view2.findViewById(id2.intValue());
                if (childView2 != null) {
                    if (!childView2.isLongClickable()) {
                        childView2.setLongClickable(true);
                    }
                    childView2.setOnLongClickListener(new BaseQuickAdapter$bindViewClickListener$$inlined$let$lambda$4(this, viewHolder));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setOnItemClick(@NotNull View v, int position) {
        k.f(v, "v");
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(this, v, position);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setOnItemLongClick(@NotNull View v, int position) {
        k.f(v, "v");
        OnItemLongClickListener onItemLongClickListener = this.mOnItemLongClickListener;
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onItemLongClick(this, v, position);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void setOnItemChildClick(@NotNull View v, int position) {
        k.f(v, "v");
        OnItemChildClickListener onItemChildClickListener = this.mOnItemChildClickListener;
        if (onItemChildClickListener != null) {
            onItemChildClickListener.onItemChildClick(this, v, position);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setOnItemChildLongClick(@NotNull View v, int position) {
        k.f(v, "v");
        OnItemChildLongClickListener onItemChildLongClickListener = this.mOnItemChildLongClickListener;
        if (onItemChildLongClickListener != null) {
            return onItemChildLongClickListener.onItemChildLongClick(this, v, position);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onItemViewHolderCreated(@NotNull VH viewHolder, int viewType) {
        k.f(viewHolder, "viewHolder");
    }

    /* access modifiers changed from: protected */
    public int getDefItemCount() {
        return this.data.size();
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        return createBaseViewHolder(parent, this.layoutResId);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH createBaseViewHolder(@NotNull ViewGroup parent, @LayoutRes int layoutResId2) {
        k.f(parent, "parent");
        return createBaseViewHolder(AdapterUtilsKt.getItemView(parent, layoutResId2));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH createBaseViewHolder(@NotNull View view) {
        BaseViewHolder vh;
        k.f(view, "view");
        Class temp = getClass();
        Class z = null;
        while (z == null && temp != null) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        if (z == null) {
            vh = new BaseViewHolder(view);
        } else {
            vh = createBaseGenericKInstance(z, view);
        }
        return vh != null ? vh : new BaseViewHolder(view);
    }

    private final Class<?> getInstancedGenericKClass(Class<?> z) {
        Class<BaseViewHolder> cls = BaseViewHolder.class;
        try {
            Type type = z.getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                return null;
            }
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            k.b(types, "type.actualTypeArguments");
            for (Type temp : types) {
                if (temp instanceof Class) {
                    if (cls.isAssignableFrom((Class) temp)) {
                        return (Class) temp;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    k.b(rawType, "temp.rawType");
                    if ((rawType instanceof Class) && cls.isAssignableFrom((Class) rawType)) {
                        return (Class) rawType;
                    }
                } else {
                    continue;
                }
            }
            return null;
        } catch (GenericSignatureFormatError e) {
            e.printStackTrace();
            return null;
        } catch (TypeNotPresentException e2) {
            e2.printStackTrace();
            return null;
        } catch (MalformedParameterizedTypeException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private final VH createBaseGenericKInstance(Class<?> z, View view) {
        try {
            if (z.isMemberClass()) {
                if (!Modifier.isStatic(z.getModifiers())) {
                    Constructor declaredConstructor = z.getDeclaredConstructor(new Class[]{getClass(), View.class});
                    k.b(declaredConstructor, "z.getDeclaredConstructor…aClass, View::class.java)");
                    Constructor constructor = declaredConstructor;
                    constructor.setAccessible(true);
                    VH newInstance = constructor.newInstance(new Object[]{this, view});
                    if (newInstance != null) {
                        return (BaseViewHolder) newInstance;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type VH");
                }
            }
            Constructor declaredConstructor2 = z.getDeclaredConstructor(new Class[]{View.class});
            k.b(declaredConstructor2, "z.getDeclaredConstructor(View::class.java)");
            Constructor constructor2 = declaredConstructor2;
            constructor2.setAccessible(true);
            VH newInstance2 = constructor2.newInstance(new Object[]{view});
            if (newInstance2 != null) {
                return (BaseViewHolder) newInstance2;
            }
            throw new TypeCastException("null cannot be cast to non-null type VH");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void setFullSpan(@NotNull RecyclerView.ViewHolder holder) {
        k.f(holder, "holder");
        View view = holder.itemView;
        k.b(view, "holder.itemView");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    @Nullable
    public final View getViewByPosition(int position, @IdRes int viewId) {
        WeakReference<RecyclerView> weakReference = this.weakRecyclerView;
        if (weakReference == null) {
            k.t("weakRecyclerView");
        }
        RecyclerView recyclerView = (RecyclerView) weakReference.get();
        if (recyclerView == null) {
            return null;
        }
        k.b(recyclerView, "weakRecyclerView.get() ?: return null");
        BaseViewHolder viewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
        if (viewHolder != null) {
            return viewHolder.getViewOrNull(viewId);
        }
        return null;
    }

    public static /* synthetic */ int addHeaderView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = -1;
            }
            if ((i3 & 4) != 0) {
                i2 = 1;
            }
            return baseQuickAdapter.addHeaderView(view, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addHeaderView");
    }

    public final int addHeaderView(@NotNull View view, int index, int orientation) {
        int position;
        RecyclerView.LayoutParams layoutParams;
        k.f(view, "view");
        if (this.mHeaderLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mHeaderLayout = linearLayout;
            linearLayout.setOrientation(orientation);
            LinearLayout linearLayout2 = this.mHeaderLayout;
            if (linearLayout2 == null) {
                k.t("mHeaderLayout");
            }
            if (orientation == 1) {
                layoutParams = new RecyclerView.LayoutParams(-1, -2);
            } else {
                layoutParams = new RecyclerView.LayoutParams(-2, -1);
            }
            linearLayout2.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout3 = this.mHeaderLayout;
        if (linearLayout3 == null) {
            k.t("mHeaderLayout");
        }
        int childCount = linearLayout3.getChildCount();
        int mIndex = index;
        if (index < 0 || index > childCount) {
            mIndex = childCount;
        }
        LinearLayout linearLayout4 = this.mHeaderLayout;
        if (linearLayout4 == null) {
            k.t("mHeaderLayout");
        }
        linearLayout4.addView(view, mIndex);
        LinearLayout linearLayout5 = this.mHeaderLayout;
        if (linearLayout5 == null) {
            k.t("mHeaderLayout");
        }
        if (linearLayout5.getChildCount() == 1 && (position = getHeaderViewPosition()) != -1) {
            notifyItemInserted(position);
        }
        return mIndex;
    }

    public static /* synthetic */ int setHeaderView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = 1;
            }
            return baseQuickAdapter.setHeaderView(view, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setHeaderView");
    }

    public final int setHeaderView(@NotNull View view, int index, int orientation) {
        k.f(view, "view");
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout != null) {
            if (linearLayout == null) {
                k.t("mHeaderLayout");
            }
            if (linearLayout.getChildCount() > index) {
                LinearLayout linearLayout2 = this.mHeaderLayout;
                if (linearLayout2 == null) {
                    k.t("mHeaderLayout");
                }
                linearLayout2.removeViewAt(index);
                LinearLayout linearLayout3 = this.mHeaderLayout;
                if (linearLayout3 == null) {
                    k.t("mHeaderLayout");
                }
                linearLayout3.addView(view, index);
                return index;
            }
        }
        return addHeaderView(view, index, orientation);
    }

    public final boolean hasHeaderLayout() {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null) {
            return false;
        }
        if (linearLayout == null) {
            k.t("mHeaderLayout");
        }
        if (linearLayout.getChildCount() > 0) {
            return true;
        }
        return false;
    }

    public final void removeHeaderView(@NotNull View header) {
        int position;
        k.f(header, "header");
        if (hasHeaderLayout()) {
            LinearLayout linearLayout = this.mHeaderLayout;
            if (linearLayout == null) {
                k.t("mHeaderLayout");
            }
            linearLayout.removeView(header);
            LinearLayout linearLayout2 = this.mHeaderLayout;
            if (linearLayout2 == null) {
                k.t("mHeaderLayout");
            }
            if (linearLayout2.getChildCount() == 0 && (position = getHeaderViewPosition()) != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public final void removeAllHeaderView() {
        if (hasHeaderLayout()) {
            LinearLayout linearLayout = this.mHeaderLayout;
            if (linearLayout == null) {
                k.t("mHeaderLayout");
            }
            linearLayout.removeAllViews();
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public final int getHeaderViewPosition() {
        if (!hasEmptyView() || this.headerWithEmptyEnable) {
            return 0;
        }
        return -1;
    }

    public final int getHeaderLayoutCount() {
        if (hasHeaderLayout()) {
            return 1;
        }
        return 0;
    }

    @Nullable
    public final LinearLayout getHeaderLayout() {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null) {
            return null;
        }
        if (linearLayout != null) {
            return linearLayout;
        }
        k.t("mHeaderLayout");
        return linearLayout;
    }

    public static /* synthetic */ int addFooterView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = -1;
            }
            if ((i3 & 4) != 0) {
                i2 = 1;
            }
            return baseQuickAdapter.addFooterView(view, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addFooterView");
    }

    public final int addFooterView(@NotNull View view, int index, int orientation) {
        int position;
        RecyclerView.LayoutParams layoutParams;
        k.f(view, "view");
        if (this.mFooterLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mFooterLayout = linearLayout;
            linearLayout.setOrientation(orientation);
            LinearLayout linearLayout2 = this.mFooterLayout;
            if (linearLayout2 == null) {
                k.t("mFooterLayout");
            }
            if (orientation == 1) {
                layoutParams = new RecyclerView.LayoutParams(-1, -2);
            } else {
                layoutParams = new RecyclerView.LayoutParams(-2, -1);
            }
            linearLayout2.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout3 = this.mFooterLayout;
        if (linearLayout3 == null) {
            k.t("mFooterLayout");
        }
        int childCount = linearLayout3.getChildCount();
        int mIndex = index;
        if (index < 0 || index > childCount) {
            mIndex = childCount;
        }
        LinearLayout linearLayout4 = this.mFooterLayout;
        if (linearLayout4 == null) {
            k.t("mFooterLayout");
        }
        linearLayout4.addView(view, mIndex);
        LinearLayout linearLayout5 = this.mFooterLayout;
        if (linearLayout5 == null) {
            k.t("mFooterLayout");
        }
        if (linearLayout5.getChildCount() == 1 && (position = getFooterViewPosition()) != -1) {
            notifyItemInserted(position);
        }
        return mIndex;
    }

    public static /* synthetic */ int setFooterView$default(BaseQuickAdapter baseQuickAdapter, View view, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = 1;
            }
            return baseQuickAdapter.setFooterView(view, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setFooterView");
    }

    public final int setFooterView(@NotNull View view, int index, int orientation) {
        k.f(view, "view");
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout != null) {
            if (linearLayout == null) {
                k.t("mFooterLayout");
            }
            if (linearLayout.getChildCount() > index) {
                LinearLayout linearLayout2 = this.mFooterLayout;
                if (linearLayout2 == null) {
                    k.t("mFooterLayout");
                }
                linearLayout2.removeViewAt(index);
                LinearLayout linearLayout3 = this.mFooterLayout;
                if (linearLayout3 == null) {
                    k.t("mFooterLayout");
                }
                linearLayout3.addView(view, index);
                return index;
            }
        }
        return addFooterView(view, index, orientation);
    }

    public final void removeFooterView(@NotNull View footer) {
        int position;
        k.f(footer, "footer");
        if (hasFooterLayout()) {
            LinearLayout linearLayout = this.mFooterLayout;
            if (linearLayout == null) {
                k.t("mFooterLayout");
            }
            linearLayout.removeView(footer);
            LinearLayout linearLayout2 = this.mFooterLayout;
            if (linearLayout2 == null) {
                k.t("mFooterLayout");
            }
            if (linearLayout2.getChildCount() == 0 && (position = getFooterViewPosition()) != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public final void removeAllFooterView() {
        if (hasFooterLayout()) {
            LinearLayout linearLayout = this.mFooterLayout;
            if (linearLayout == null) {
                k.t("mFooterLayout");
            }
            linearLayout.removeAllViews();
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public final boolean hasFooterLayout() {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null) {
            return false;
        }
        if (linearLayout == null) {
            k.t("mFooterLayout");
        }
        if (linearLayout.getChildCount() > 0) {
            return true;
        }
        return false;
    }

    public final int getFooterViewPosition() {
        if (!hasEmptyView()) {
            return getHeaderLayoutCount() + this.data.size();
        }
        int position = 1;
        if (this.headerWithEmptyEnable && hasHeaderLayout()) {
            position = 1 + 1;
        }
        if (this.footerWithEmptyEnable) {
            return position;
        }
        return -1;
    }

    public final int getFooterLayoutCount() {
        if (hasFooterLayout()) {
            return 1;
        }
        return 0;
    }

    @Nullable
    public final LinearLayout getFooterLayout() {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null) {
            return null;
        }
        if (linearLayout != null) {
            return linearLayout;
        }
        k.t("mFooterLayout");
        return linearLayout;
    }

    public final void setEmptyView(@NotNull View emptyView) {
        ViewGroup.LayoutParams layoutParams;
        k.f(emptyView, "emptyView");
        int oldItemCount = getItemCount();
        boolean insert = false;
        if (this.mEmptyLayout == null) {
            FrameLayout frameLayout = new FrameLayout(emptyView.getContext());
            this.mEmptyLayout = frameLayout;
            if (frameLayout == null) {
                k.t("mEmptyLayout");
            }
            ViewGroup.LayoutParams it = emptyView.getLayoutParams();
            if (it != null) {
                layoutParams = new ViewGroup.LayoutParams(it.width, it.height);
            } else {
                layoutParams = new ViewGroup.LayoutParams(-1, -1);
            }
            frameLayout.setLayoutParams(layoutParams);
            insert = true;
        } else {
            ViewGroup.LayoutParams it2 = emptyView.getLayoutParams();
            if (it2 != null) {
                FrameLayout frameLayout2 = this.mEmptyLayout;
                if (frameLayout2 == null) {
                    k.t("mEmptyLayout");
                }
                ViewGroup.LayoutParams lp = frameLayout2.getLayoutParams();
                lp.width = it2.width;
                lp.height = it2.height;
                FrameLayout frameLayout3 = this.mEmptyLayout;
                if (frameLayout3 == null) {
                    k.t("mEmptyLayout");
                }
                frameLayout3.setLayoutParams(lp);
            }
        }
        FrameLayout frameLayout4 = this.mEmptyLayout;
        if (frameLayout4 == null) {
            k.t("mEmptyLayout");
        }
        frameLayout4.removeAllViews();
        FrameLayout frameLayout5 = this.mEmptyLayout;
        if (frameLayout5 == null) {
            k.t("mEmptyLayout");
        }
        frameLayout5.addView(emptyView);
        this.isUseEmpty = true;
        if (insert && hasEmptyView()) {
            int position = 0;
            if (this.headerWithEmptyEnable && hasHeaderLayout()) {
                position = 0 + 1;
            }
            if (getItemCount() > oldItemCount) {
                notifyItemInserted(position);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public final void setEmptyView(int layoutResId2) {
        WeakReference<RecyclerView> weakReference = this.weakRecyclerView;
        if (weakReference == null) {
            k.t("weakRecyclerView");
        }
        RecyclerView it = (RecyclerView) weakReference.get();
        if (it != null) {
            k.b(it, "it");
            View view = LayoutInflater.from(it.getContext()).inflate(layoutResId2, it, false);
            k.b(view, "view");
            setEmptyView(view);
        }
    }

    public final void removeEmptyView() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout != null) {
            if (frameLayout == null) {
                k.t("mEmptyLayout");
            }
            frameLayout.removeAllViews();
        }
    }

    public final boolean hasEmptyView() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout != null) {
            if (frameLayout == null) {
                k.t("mEmptyLayout");
            }
            if (frameLayout.getChildCount() != 0 && this.isUseEmpty) {
                return this.data.isEmpty();
            }
            return false;
        }
        return false;
    }

    @Nullable
    public final FrameLayout getEmptyLayout() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout == null) {
            return null;
        }
        if (frameLayout != null) {
            return frameLayout;
        }
        k.t("mEmptyLayout");
        return frameLayout;
    }

    private final void addAnimation(RecyclerView.ViewHolder holder) {
        if (!this.animationEnable) {
            return;
        }
        if (!this.isAnimationFirstOnly || holder.getLayoutPosition() > this.mLastPosition) {
            BaseAnimation animation = this.adapterAnimation;
            if (animation == null) {
                animation = new AlphaInAnimation(0.0f, 1, (DefaultConstructorMarker) null);
            }
            View view = holder.itemView;
            k.b(view, "holder.itemView");
            for (Animator it : animation.animators(view)) {
                startAnim(it, holder.getLayoutPosition());
            }
            this.mLastPosition = holder.getLayoutPosition();
        }
    }

    /* access modifiers changed from: protected */
    public void startAnim(@NotNull Animator anim, int index) {
        k.f(anim, "anim");
        anim.start();
    }

    public final void setAnimationWithDefault(@NotNull AnimationType animationType) {
        BaseAnimation baseAnimation;
        k.f(animationType, "animationType");
        switch (WhenMappings.$EnumSwitchMapping$0[animationType.ordinal()]) {
            case 1:
                baseAnimation = new AlphaInAnimation(0.0f, 1, (DefaultConstructorMarker) null);
                break;
            case 2:
                baseAnimation = new ScaleInAnimation(0.0f, 1, (DefaultConstructorMarker) null);
                break;
            case 3:
                baseAnimation = new SlideInBottomAnimation();
                break;
            case 4:
                baseAnimation = new SlideInLeftAnimation();
                break;
            case 5:
                baseAnimation = new SlideInRightAnimation();
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        setAdapterAnimation(baseAnimation);
    }

    public void setNewData(@Nullable List<T> data2) {
        setNewInstance(data2);
    }

    public void setNewInstance(@Nullable List<T> list) {
        if (list != this.data) {
            this.data = list != null ? list : new ArrayList<>();
            BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
            if (baseLoadMoreModule != null) {
                baseLoadMoreModule.reset$doraemonkit_release();
            }
            this.mLastPosition = -1;
            notifyDataSetChanged();
            BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
            if (baseLoadMoreModule2 != null) {
                baseLoadMoreModule2.checkDisableLoadMoreIfNotFullPage();
            }
        }
    }

    public void replaceData(@NotNull Collection<? extends T> newData) {
        k.f(newData, "newData");
        setList(newData);
    }

    public void setList(@Nullable Collection<? extends T> list) {
        List<T> list2 = this.data;
        boolean z = false;
        if (list != list2) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                z = true;
            }
            if (!z) {
                this.data.addAll(list);
            }
        } else {
            if (list == null || list.isEmpty()) {
                z = true;
            }
            if (!z) {
                ArrayList newList = new ArrayList(list);
                this.data.clear();
                this.data.addAll(newList);
            } else {
                this.data.clear();
            }
        }
        BaseLoadMoreModule baseLoadMoreModule = this.mLoadMoreModule;
        if (baseLoadMoreModule != null) {
            baseLoadMoreModule.reset$doraemonkit_release();
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
        BaseLoadMoreModule baseLoadMoreModule2 = this.mLoadMoreModule;
        if (baseLoadMoreModule2 != null) {
            baseLoadMoreModule2.checkDisableLoadMoreIfNotFullPage();
        }
    }

    public void setData(@IntRange(from = 0) int index, T data2) {
        if (index < this.data.size()) {
            this.data.set(index, data2);
            notifyItemChanged(getHeaderLayoutCount() + index);
        }
    }

    public void addData(@IntRange(from = 0) int position, T data2) {
        this.data.add(position, data2);
        notifyItemInserted(getHeaderLayoutCount() + position);
        compatibilityDataSizeChanged(1);
    }

    public void addData(@NonNull T data2) {
        this.data.add(data2);
        notifyItemInserted(this.data.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addData(@IntRange(from = 0) int position, @NotNull Collection<? extends T> newData) {
        k.f(newData, "newData");
        this.data.addAll(position, newData);
        notifyItemRangeInserted(getHeaderLayoutCount() + position, newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@NotNull @NonNull Collection<? extends T> newData) {
        k.f(newData, "newData");
        this.data.addAll(newData);
        notifyItemRangeInserted((this.data.size() - newData.size()) + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void remove(@IntRange(from = 0) int position) {
        if (position < this.data.size()) {
            this.data.remove(position);
            int internalPosition = getHeaderLayoutCount() + position;
            notifyItemRemoved(internalPosition);
            compatibilityDataSizeChanged(0);
            notifyItemRangeChanged(internalPosition, this.data.size() - internalPosition);
        }
    }

    public void remove(T data2) {
        int index = this.data.indexOf(data2);
        if (index != -1) {
            remove(index);
        }
    }

    /* access modifiers changed from: protected */
    public final void compatibilityDataSizeChanged(int size) {
        if (this.data.size() == size) {
            notifyDataSetChanged();
        }
    }

    public final void setDiffCallback(@NotNull DiffUtil.ItemCallback<T> diffCallback) {
        k.f(diffCallback, "diffCallback");
        setDiffConfig(new BrvahAsyncDifferConfig.Builder(diffCallback).build());
    }

    public final void setDiffConfig(@NotNull BrvahAsyncDifferConfig<T> config) {
        k.f(config, "config");
        this.mDiffHelper = new BrvahAsyncDiffer<>(this, config);
    }

    @NotNull
    public final BrvahAsyncDiffer<T> getDiffHelper() {
        BrvahAsyncDiffer<T> brvahAsyncDiffer = this.mDiffHelper;
        if (brvahAsyncDiffer != null) {
            if (brvahAsyncDiffer == null) {
                k.n();
            }
            return brvahAsyncDiffer;
        }
        throw new IllegalStateException("Please use setDiffCallback() or setDiffConfig() first!".toString());
    }

    public void setDiffNewData(@Nullable List<T> list) {
        if (hasEmptyView()) {
            setNewInstance(list);
            return;
        }
        BrvahAsyncDiffer<T> brvahAsyncDiffer = this.mDiffHelper;
        if (brvahAsyncDiffer != null) {
            BrvahAsyncDiffer.submitList$default(brvahAsyncDiffer, list, (Runnable) null, 2, (Object) null);
        }
    }

    public void setDiffNewData(@NotNull @NonNull DiffUtil.DiffResult diffResult, @NotNull List<T> list) {
        k.f(diffResult, "diffResult");
        k.f(list, "list");
        if (hasEmptyView()) {
            setNewInstance(list);
            return;
        }
        diffResult.dispatchUpdatesTo((ListUpdateCallback) new BrvahListUpdateCallback(this));
        this.data = list;
    }

    public void setGridSpanSizeLookup(@Nullable GridSpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnItemChildClickListener(@Nullable OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    public void setOnItemChildLongClickListener(@Nullable OnItemChildLongClickListener listener) {
        this.mOnItemChildLongClickListener = listener;
    }

    @Nullable
    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    @Nullable
    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }
}
