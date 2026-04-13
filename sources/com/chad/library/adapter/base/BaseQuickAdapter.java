package com.chad.library.adapter.base;

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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.chad.library.adapter.base.a;
import com.chad.library.adapter.base.diff.b;
import com.chad.library.adapter.base.listener.f;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseQuickAdapter.kt */
public abstract class BaseQuickAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> implements a {
    public static final a c = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public com.chad.library.adapter.base.listener.a A4;
    private com.chad.library.adapter.base.listener.d B4;
    private f C4;
    private com.chad.library.adapter.base.listener.b D4;
    private com.chad.library.adapter.base.listener.c E4;
    private com.chad.library.adapter.base.module.c F4;
    private com.chad.library.adapter.base.module.a G4;
    @Nullable
    private com.chad.library.adapter.base.module.b H4;
    @NotNull
    public WeakReference<RecyclerView> I4;
    @Nullable
    private RecyclerView J4;
    private final LinkedHashSet<Integer> K4;
    private final LinkedHashSet<Integer> L4;
    private final int M4;
    private boolean a1;
    private com.chad.library.adapter.base.diff.a<T> a2;
    @NotNull
    private List<T> d;
    private boolean f;
    private boolean p0;
    @Nullable
    private com.chad.library.adapter.base.animation.b p1;
    private LinearLayout p2;
    private LinearLayout p3;
    private FrameLayout p4;
    private boolean q;
    private boolean x;
    private boolean y;
    private boolean z;
    private int z4;

    /* access modifiers changed from: protected */
    public abstract void d(@NotNull VH vh, T t);

    @NotNull
    public com.chad.library.adapter.base.module.a b(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        k.e(baseQuickAdapter, "baseQuickAdapter");
        return a.C0055a.a(this, baseQuickAdapter);
    }

    public BaseQuickAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.M4 = layoutResId;
        this.d = data != null ? data : new ArrayList<>();
        this.x = true;
        this.a1 = true;
        this.z4 = -1;
        checkModule();
        this.K4 = new LinkedHashSet<>();
        this.L4 = new LinkedHashSet<>();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseQuickAdapter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list);
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @NotNull
    public final List<T> getData() {
        return this.d;
    }

    public final boolean getHeaderViewAsFlow() {
        return this.y;
    }

    public final boolean getFooterViewAsFlow() {
        return this.z;
    }

    @NotNull
    public final com.chad.library.adapter.base.module.a i() {
        com.chad.library.adapter.base.module.a aVar = this.G4;
        if (aVar != null) {
            k.c(aVar);
            return aVar;
        }
        throw new IllegalStateException("Please first implements DraggableModule".toString());
    }

    @Nullable
    public final com.chad.library.adapter.base.module.b j() {
        return this.H4;
    }

    @Nullable
    public final RecyclerView k() {
        return this.J4;
    }

    @NotNull
    public final RecyclerView p() {
        RecyclerView recyclerView = this.J4;
        if (recyclerView != null) {
            k.c(recyclerView);
            return recyclerView;
        }
        throw new IllegalStateException("Please get it after onAttachedToRecyclerView()".toString());
    }

    @NotNull
    public final Context getContext() {
        Context context = p().getContext();
        k.d(context, "recyclerView.context");
        return context;
    }

    private final void checkModule() {
        if (this instanceof com.chad.library.adapter.base.module.d) {
            this.G4 = b(this);
        }
    }

    /* access modifiers changed from: protected */
    public void e(@NotNull VH holder, T item, @NotNull List<? extends Object> payloads) {
        k.e(holder, "holder");
        k.e(payloads, "payloads");
    }

    @NotNull
    /* renamed from: t */
    public VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        switch (viewType) {
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.HEADER_VIEW /*268435729*/:
                LinearLayout linearLayout = this.p2;
                if (linearLayout == null) {
                    k.t("mHeaderLayout");
                }
                ViewParent headerLayoutVp = linearLayout.getParent();
                if (headerLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) headerLayoutVp;
                    LinearLayout linearLayout2 = this.p2;
                    if (linearLayout2 == null) {
                        k.t("mHeaderLayout");
                    }
                    viewGroup.removeView(linearLayout2);
                }
                LinearLayout linearLayout3 = this.p2;
                if (linearLayout3 == null) {
                    k.t("mHeaderLayout");
                }
                return g(linearLayout3);
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.LOAD_MORE_VIEW /*268436002*/:
                com.chad.library.adapter.base.module.b bVar = this.H4;
                k.c(bVar);
                BaseViewHolder baseViewHolder = g(bVar.j().b(parent));
                com.chad.library.adapter.base.module.b bVar2 = this.H4;
                k.c(bVar2);
                bVar2.s(baseViewHolder);
                return baseViewHolder;
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW /*268436275*/:
                LinearLayout linearLayout4 = this.p3;
                if (linearLayout4 == null) {
                    k.t("mFooterLayout");
                }
                ViewParent footerLayoutVp = linearLayout4.getParent();
                if (footerLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) footerLayoutVp;
                    LinearLayout linearLayout5 = this.p3;
                    if (linearLayout5 == null) {
                        k.t("mFooterLayout");
                    }
                    viewGroup2.removeView(linearLayout5);
                }
                LinearLayout linearLayout6 = this.p3;
                if (linearLayout6 == null) {
                    k.t("mFooterLayout");
                }
                return g(linearLayout6);
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW /*268436821*/:
                FrameLayout frameLayout = this.p4;
                if (frameLayout == null) {
                    k.t("mEmptyLayout");
                }
                ViewParent emptyLayoutVp = frameLayout.getParent();
                if (emptyLayoutVp instanceof ViewGroup) {
                    ViewGroup viewGroup3 = (ViewGroup) emptyLayoutVp;
                    FrameLayout frameLayout2 = this.p4;
                    if (frameLayout2 == null) {
                        k.t("mEmptyLayout");
                    }
                    viewGroup3.removeView(frameLayout2);
                }
                FrameLayout frameLayout3 = this.p4;
                if (frameLayout3 == null) {
                    k.t("mEmptyLayout");
                }
                return g(frameLayout3);
            default:
                BaseViewHolder viewHolder = s(parent, viewType);
                c(viewHolder, viewType);
                com.chad.library.adapter.base.module.a aVar = this.G4;
                if (aVar != null) {
                    aVar.h(viewHolder);
                }
                u(viewHolder, viewType);
                return viewHolder;
        }
    }

    public int getItemCount() {
        if (hasEmptyView()) {
            int count = 1;
            if (this.f && hasHeaderLayout()) {
                count = 1 + 1;
            }
            if (!this.q || !hasFooterLayout()) {
                return count;
            }
            return count + 1;
        }
        com.chad.library.adapter.base.module.b bVar = this.H4;
        int loadMoreCount = 1;
        if (bVar == null || !bVar.m()) {
            loadMoreCount = 0;
        }
        return getHeaderLayoutCount() + getDefItemCount() + getFooterLayoutCount() + loadMoreCount;
    }

    public int getItemViewType(int position) {
        int adjPosition;
        int numFooters = 0;
        if (hasEmptyView()) {
            if (this.f && hasHeaderLayout()) {
                numFooters = 1;
            }
            int i = numFooters;
            switch (position) {
                case 0:
                    if (i != 0) {
                        return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.HEADER_VIEW;
                    }
                    return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW;
                case 1:
                    if (i != 0) {
                        return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW;
                    }
                    return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW;
                case 2:
                    return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW;
                default:
                    return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW;
            }
        } else {
            boolean header = hasHeaderLayout();
            if (header && position == 0) {
                return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.HEADER_VIEW;
            }
            if (header) {
                adjPosition = position - 1;
            } else {
                adjPosition = position;
            }
            int dataSize = this.d.size();
            if (adjPosition < dataSize) {
                return getDefItemViewType(adjPosition);
            }
            int adjPosition2 = adjPosition - dataSize;
            if (hasFooterLayout()) {
                numFooters = 1;
            }
            if (adjPosition2 < numFooters) {
                return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW;
            }
            return com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.LOAD_MORE_VIEW;
        }
    }

    /* renamed from: q */
    public void onBindViewHolder(@NotNull VH holder, int position) {
        k.e(holder, "holder");
        com.chad.library.adapter.base.module.c cVar = this.F4;
        if (cVar != null) {
            cVar.a(position);
        }
        com.chad.library.adapter.base.module.b bVar = this.H4;
        if (bVar != null) {
            bVar.f(position);
        }
        switch (holder.getItemViewType()) {
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.HEADER_VIEW /*268435729*/:
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW /*268436275*/:
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW /*268436821*/:
                return;
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.LOAD_MORE_VIEW /*268436002*/:
                com.chad.library.adapter.base.module.b it = this.H4;
                if (it != null) {
                    it.j().a(holder, position, it.i());
                    return;
                }
                return;
            default:
                d(holder, getItem(position - getHeaderLayoutCount()));
                return;
        }
    }

    /* renamed from: r */
    public void onBindViewHolder(@NotNull VH holder, int position, @NotNull List<Object> payloads) {
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        com.chad.library.adapter.base.module.c cVar = this.F4;
        if (cVar != null) {
            cVar.a(position);
        }
        com.chad.library.adapter.base.module.b bVar = this.H4;
        if (bVar != null) {
            bVar.f(position);
        }
        switch (holder.getItemViewType()) {
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.HEADER_VIEW /*268435729*/:
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.FOOTER_VIEW /*268436275*/:
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.EMPTY_VIEW /*268436821*/:
                return;
            case com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter.LOAD_MORE_VIEW /*268436002*/:
                com.chad.library.adapter.base.module.b it = this.H4;
                if (it != null) {
                    it.j().a(holder, position, it.i());
                    return;
                }
                return;
            default:
                e(holder, getItem(position - getHeaderLayoutCount()), payloads);
                return;
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    /* renamed from: v */
    public void onViewAttachedToWindow(@NotNull VH holder) {
        k.e(holder, "holder");
        super.onViewAttachedToWindow(holder);
        if (isFixedViewType(holder.getItemViewType())) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        k.e(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        this.I4 = new WeakReference<>(recyclerView);
        this.J4 = recyclerView;
        com.chad.library.adapter.base.module.a aVar = this.G4;
        if (aVar != null) {
            aVar.a(recyclerView);
        }
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new BaseQuickAdapter$onAttachedToRecyclerView$1(this, manager, ((GridLayoutManager) manager).getSpanSizeLookup()));
        }
    }

    public void onDetachedFromRecyclerView(@NotNull RecyclerView recyclerView) {
        k.e(recyclerView, "recyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
        this.J4 = null;
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return type == 268436821 || type == 268435729 || type == 268436275 || type == 268436002;
    }

    public T getItem(@IntRange(from = 0) int position) {
        return this.d.get(position);
    }

    public int getItemPosition(@Nullable T item) {
        if (item == null || !(!this.d.isEmpty())) {
            return -1;
        }
        return this.d.indexOf(item);
    }

    @NotNull
    public final LinkedHashSet<Integer> getChildClickViewIds() {
        return this.K4;
    }

    @NotNull
    public final LinkedHashSet<Integer> getChildLongClickViewIds() {
        return this.L4;
    }

    /* access modifiers changed from: protected */
    public void c(@NotNull VH vh, int i) {
        k.e(vh, "viewHolder");
        if (this.B4 != null) {
            vh.itemView.setOnClickListener(new b(this, vh));
        }
        if (this.C4 != null) {
            vh.itemView.setOnLongClickListener(new c(this, vh));
        }
        if (this.D4 != null) {
            Iterator it = getChildClickViewIds().iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                View view = vh.itemView;
                k.d(num, "id");
                View findViewById = view.findViewById(num.intValue());
                if (findViewById != null) {
                    if (!findViewById.isClickable()) {
                        findViewById.setClickable(true);
                    }
                    findViewById.setOnClickListener(new d(this, vh));
                }
            }
        }
        if (this.E4 != null) {
            Iterator it2 = getChildLongClickViewIds().iterator();
            while (it2.hasNext()) {
                Integer num2 = (Integer) it2.next();
                View view2 = vh.itemView;
                k.d(num2, "id");
                View findViewById2 = view2.findViewById(num2.intValue());
                if (findViewById2 != null) {
                    if (!findViewById2.isLongClickable()) {
                        findViewById2.setLongClickable(true);
                    }
                    findViewById2.setOnLongClickListener(new e(this, vh));
                }
            }
        }
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class b implements View.OnClickListener {
        final /* synthetic */ BaseQuickAdapter c;
        final /* synthetic */ BaseViewHolder d;

        b(BaseQuickAdapter baseQuickAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseQuickAdapter;
            this.d = baseViewHolder;
        }

        @SensorsDataInstrumented
        public final void onClick(View view) {
            View v = view;
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            BaseQuickAdapter baseQuickAdapter = this.c;
            k.d(v, "v");
            baseQuickAdapter.setOnItemClick(v, position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class c implements View.OnLongClickListener {
        final /* synthetic */ BaseQuickAdapter c;
        final /* synthetic */ BaseViewHolder d;

        c(BaseQuickAdapter baseQuickAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseQuickAdapter;
            this.d = baseViewHolder;
        }

        public final boolean onLongClick(View v) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            BaseQuickAdapter baseQuickAdapter = this.c;
            k.d(v, "v");
            return baseQuickAdapter.setOnItemLongClick(v, position2);
        }
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class d implements View.OnClickListener {
        final /* synthetic */ BaseQuickAdapter c;
        final /* synthetic */ BaseViewHolder d;

        d(BaseQuickAdapter baseQuickAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseQuickAdapter;
            this.d = baseViewHolder;
        }

        @SensorsDataInstrumented
        public final void onClick(View view) {
            View v = view;
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            BaseQuickAdapter baseQuickAdapter = this.c;
            k.d(v, "v");
            baseQuickAdapter.setOnItemChildClick(v, position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseQuickAdapter.kt */
    public static final class e implements View.OnLongClickListener {
        final /* synthetic */ BaseQuickAdapter c;
        final /* synthetic */ BaseViewHolder d;

        e(BaseQuickAdapter baseQuickAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseQuickAdapter;
            this.d = baseViewHolder;
        }

        public final boolean onLongClick(View v) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            BaseQuickAdapter baseQuickAdapter = this.c;
            k.d(v, "v");
            return baseQuickAdapter.setOnItemChildLongClick(v, position2);
        }
    }

    /* access modifiers changed from: protected */
    public void setOnItemClick(@NotNull View v, int position) {
        k.e(v, "v");
        com.chad.library.adapter.base.listener.d dVar = this.B4;
        if (dVar != null) {
            dVar.a(this, v, position);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setOnItemLongClick(@NotNull View v, int position) {
        k.e(v, "v");
        f fVar = this.C4;
        if (fVar != null) {
            return fVar.a(this, v, position);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void setOnItemChildClick(@NotNull View v, int position) {
        k.e(v, "v");
        com.chad.library.adapter.base.listener.b bVar = this.D4;
        if (bVar != null) {
            bVar.a(this, v, position);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setOnItemChildLongClick(@NotNull View v, int position) {
        k.e(v, "v");
        com.chad.library.adapter.base.listener.c cVar = this.E4;
        if (cVar != null) {
            return cVar.a(this, v, position);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void u(@NotNull VH viewHolder, int viewType) {
        k.e(viewHolder, "viewHolder");
    }

    /* access modifiers changed from: protected */
    public int getDefItemCount() {
        return this.d.size();
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH s(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        return h(parent, this.M4);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH h(@NotNull ViewGroup parent, @LayoutRes int layoutResId) {
        k.e(parent, "parent");
        return g(com.chad.library.adapter.base.util.a.a(parent, layoutResId));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH g(@NotNull View view) {
        BaseViewHolder vh;
        k.e(view, "view");
        Class temp = getClass();
        Class z2 = null;
        while (z2 == null && temp != null) {
            z2 = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        if (z2 == null) {
            vh = new BaseViewHolder(view);
        } else {
            vh = f(z2, view);
        }
        return vh != null ? vh : new BaseViewHolder(view);
    }

    private final Class<?> getInstancedGenericKClass(Class<?> z2) {
        Class<BaseViewHolder> cls = BaseViewHolder.class;
        try {
            Type type = z2.getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                return null;
            }
            for (Type temp : ((ParameterizedType) type).getActualTypeArguments()) {
                if (temp instanceof Class) {
                    if (cls.isAssignableFrom((Class) temp)) {
                        return (Class) temp;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if ((rawType instanceof Class) && cls.isAssignableFrom((Class) rawType)) {
                        return (Class) rawType;
                    }
                } else {
                    continue;
                }
            }
            return null;
        } catch (GenericSignatureFormatError e2) {
            e2.printStackTrace();
            return null;
        } catch (TypeNotPresentException e3) {
            e3.printStackTrace();
            return null;
        } catch (MalformedParameterizedTypeException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private final VH f(Class<?> z2, View view) {
        try {
            if (z2.isMemberClass()) {
                if (!Modifier.isStatic(z2.getModifiers())) {
                    Constructor declaredConstructor = z2.getDeclaredConstructor(new Class[]{getClass(), View.class});
                    k.d(declaredConstructor, "z.getDeclaredConstructor…aClass, View::class.java)");
                    Constructor constructor = declaredConstructor;
                    constructor.setAccessible(true);
                    VH newInstance = constructor.newInstance(new Object[]{this, view});
                    if (newInstance != null) {
                        return (BaseViewHolder) newInstance;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type VH");
                }
            }
            Constructor declaredConstructor2 = z2.getDeclaredConstructor(new Class[]{View.class});
            k.d(declaredConstructor2, "z.getDeclaredConstructor(View::class.java)");
            Constructor constructor2 = declaredConstructor2;
            constructor2.setAccessible(true);
            VH newInstance2 = constructor2.newInstance(new Object[]{view});
            if (newInstance2 != null) {
                return (BaseViewHolder) newInstance2;
            }
            throw new NullPointerException("null cannot be cast to non-null type VH");
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void setFullSpan(@NotNull RecyclerView.ViewHolder holder) {
        k.e(holder, "holder");
        View view = holder.itemView;
        k.d(view, "holder.itemView");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    @Nullable
    public final View getViewByPosition(int position, @IdRes int viewId) {
        BaseViewHolder viewHolder;
        RecyclerView recyclerView = this.J4;
        if (recyclerView == null || (viewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(position)) == null) {
            return null;
        }
        return viewHolder.getViewOrNull(viewId);
    }

    public final boolean hasHeaderLayout() {
        LinearLayout linearLayout = this.p2;
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

    public final int getHeaderLayoutCount() {
        if (hasHeaderLayout()) {
            return 1;
        }
        return 0;
    }

    public final boolean hasFooterLayout() {
        LinearLayout linearLayout = this.p3;
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

    public final int getFooterLayoutCount() {
        if (hasFooterLayout()) {
            return 1;
        }
        return 0;
    }

    public final void setEmptyView(@NotNull View emptyView) {
        ViewGroup.LayoutParams layoutParams;
        k.e(emptyView, "emptyView");
        int oldItemCount = getItemCount();
        boolean insert = false;
        if (this.p4 == null) {
            FrameLayout frameLayout = new FrameLayout(emptyView.getContext());
            this.p4 = frameLayout;
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
                FrameLayout frameLayout2 = this.p4;
                if (frameLayout2 == null) {
                    k.t("mEmptyLayout");
                }
                ViewGroup.LayoutParams lp = frameLayout2.getLayoutParams();
                lp.width = it2.width;
                lp.height = it2.height;
                FrameLayout frameLayout3 = this.p4;
                if (frameLayout3 == null) {
                    k.t("mEmptyLayout");
                }
                frameLayout3.setLayoutParams(lp);
            }
        }
        FrameLayout frameLayout4 = this.p4;
        if (frameLayout4 == null) {
            k.t("mEmptyLayout");
        }
        frameLayout4.removeAllViews();
        FrameLayout frameLayout5 = this.p4;
        if (frameLayout5 == null) {
            k.t("mEmptyLayout");
        }
        frameLayout5.addView(emptyView);
        this.x = true;
        if (insert && hasEmptyView()) {
            int position = 0;
            if (this.f && hasHeaderLayout()) {
                position = 0 + 1;
            }
            if (getItemCount() > oldItemCount) {
                notifyItemInserted(position);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public final void setEmptyView(int layoutResId) {
        RecyclerView it = this.J4;
        if (it != null) {
            View view = LayoutInflater.from(it.getContext()).inflate(layoutResId, it, false);
            k.d(view, "view");
            setEmptyView(view);
        }
    }

    public final boolean hasEmptyView() {
        FrameLayout frameLayout = this.p4;
        if (frameLayout != null) {
            if (frameLayout == null) {
                k.t("mEmptyLayout");
            }
            if (frameLayout.getChildCount() != 0 && this.x) {
                return this.d.isEmpty();
            }
            return false;
        }
        return false;
    }

    private final void addAnimation(RecyclerView.ViewHolder holder) {
        if (!this.p0) {
            return;
        }
        if (!this.a1 || holder.getLayoutPosition() > this.z4) {
            com.chad.library.adapter.base.animation.b animation = this.p1;
            if (animation == null) {
                animation = new com.chad.library.adapter.base.animation.a(0.0f, 1, (DefaultConstructorMarker) null);
            }
            View view = holder.itemView;
            k.d(view, "holder.itemView");
            for (Animator it : animation.animators(view)) {
                startAnim(it, holder.getLayoutPosition());
            }
            this.z4 = holder.getLayoutPosition();
        }
    }

    /* access modifiers changed from: protected */
    public void startAnim(@NotNull Animator anim, int index) {
        k.e(anim, "anim");
        anim.start();
    }

    public void setList(@Nullable Collection<? extends T> list) {
        List<T> list2 = this.d;
        boolean z2 = false;
        if (list != list2) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                z2 = true;
            }
            if (!z2) {
                this.d.addAll(list);
            }
        } else {
            if (list == null || list.isEmpty()) {
                z2 = true;
            }
            if (!z2) {
                ArrayList newList = new ArrayList(list);
                this.d.clear();
                this.d.addAll(newList);
            } else {
                this.d.clear();
            }
        }
        com.chad.library.adapter.base.module.b bVar = this.H4;
        if (bVar != null) {
            bVar.q();
        }
        this.z4 = -1;
        notifyDataSetChanged();
        com.chad.library.adapter.base.module.b bVar2 = this.H4;
        if (bVar2 != null) {
            bVar2.g();
        }
    }

    public final void setDiffCallback(@NotNull DiffUtil.ItemCallback<T> diffCallback) {
        k.e(diffCallback, "diffCallback");
        w(new b.a(diffCallback).a());
    }

    public final void w(@NotNull com.chad.library.adapter.base.diff.b<T> config) {
        k.e(config, "config");
        this.a2 = new com.chad.library.adapter.base.diff.a<>(this, config);
    }

    public void setOnItemClickListener(@Nullable com.chad.library.adapter.base.listener.d listener) {
        this.B4 = listener;
    }

    public void setOnItemLongClickListener(@Nullable f listener) {
        this.C4 = listener;
    }

    public void setOnItemChildClickListener(@Nullable com.chad.library.adapter.base.listener.b listener) {
        this.D4 = listener;
    }

    public void setOnItemChildLongClickListener(@Nullable com.chad.library.adapter.base.listener.c listener) {
        this.E4 = listener;
    }

    @Nullable
    public final com.chad.library.adapter.base.listener.d n() {
        return this.B4;
    }

    @Nullable
    public final f o() {
        return this.C4;
    }

    @Nullable
    public final com.chad.library.adapter.base.listener.b l() {
        return this.D4;
    }

    @Nullable
    public final com.chad.library.adapter.base.listener.c m() {
        return this.E4;
    }
}
