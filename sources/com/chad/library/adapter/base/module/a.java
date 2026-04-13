package com.chad.library.adapter.base.module;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.R$id;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.listener.e;
import com.chad.library.adapter.base.listener.g;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.Collections;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DraggableModule.kt */
public class a {
    public static final C0060a a = new C0060a((DefaultConstructorMarker) null);
    private boolean b;
    private boolean c;
    private int d;
    @NotNull
    public ItemTouchHelper e;
    @NotNull
    public DragAndSwipeCallback f;
    @Nullable
    private View.OnTouchListener g;
    @Nullable
    private View.OnLongClickListener h;
    @Nullable
    private e i;
    @Nullable
    private g j;
    private boolean k = true;
    private final BaseQuickAdapter<?, ?> l;

    public a(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        k.e(baseQuickAdapter, "baseQuickAdapter");
        this.l = baseQuickAdapter;
        g();
    }

    public final boolean i() {
        return this.b;
    }

    public final void s(boolean z) {
        this.b = z;
    }

    public final boolean k() {
        return this.c;
    }

    public final void u(boolean z) {
        this.c = z;
    }

    public final void v(int i2) {
        this.d = i2;
    }

    @NotNull
    public final ItemTouchHelper b() {
        ItemTouchHelper itemTouchHelper = this.e;
        if (itemTouchHelper == null) {
            k.t("itemTouchHelper");
        }
        return itemTouchHelper;
    }

    @NotNull
    public final DragAndSwipeCallback c() {
        DragAndSwipeCallback dragAndSwipeCallback = this.f;
        if (dragAndSwipeCallback == null) {
            k.t("itemTouchHelperCallback");
        }
        return dragAndSwipeCallback;
    }

    /* access modifiers changed from: protected */
    public final void setMOnToggleViewTouchListener(@Nullable View.OnTouchListener onTouchListener) {
        this.g = onTouchListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnToggleViewLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.h = onLongClickListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnItemDragListener(@Nullable e eVar) {
        this.i = eVar;
    }

    /* access modifiers changed from: protected */
    public final void setMOnItemSwipeListener(@Nullable g gVar) {
        this.j = gVar;
    }

    private final void g() {
        DragAndSwipeCallback dragAndSwipeCallback = new DragAndSwipeCallback(this);
        this.f = dragAndSwipeCallback;
        if (dragAndSwipeCallback == null) {
            k.t("itemTouchHelperCallback");
        }
        this.e = new ItemTouchHelper(dragAndSwipeCallback);
    }

    public final void h(@NotNull BaseViewHolder holder) {
        View toggleView;
        k.e(holder, "holder");
        if (this.b && e() && (toggleView = holder.itemView.findViewById(this.d)) != null) {
            toggleView.setTag(R$id.BaseQuickAdapter_viewholder_support, holder);
            if (j()) {
                toggleView.setOnLongClickListener(this.h);
            } else {
                toggleView.setOnTouchListener(this.g);
            }
        }
    }

    public final void a(@NotNull RecyclerView recyclerView) {
        k.e(recyclerView, "recyclerView");
        ItemTouchHelper itemTouchHelper = this.e;
        if (itemTouchHelper == null) {
            k.t("itemTouchHelper");
        }
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public boolean e() {
        return this.d != 0;
    }

    public boolean j() {
        return this.k;
    }

    public void t(boolean value) {
        this.k = value;
        if (value) {
            this.g = null;
            this.h = new b(this);
            return;
        }
        this.g = new c(this);
        this.h = null;
    }

    /* compiled from: DraggableModule.kt */
    public static final class b implements View.OnLongClickListener {
        final /* synthetic */ a c;

        b(a aVar) {
            this.c = aVar;
        }

        public final boolean onLongClick(View v) {
            if (!this.c.i()) {
                return true;
            }
            ItemTouchHelper b = this.c.b();
            Object tag = v.getTag(R$id.BaseQuickAdapter_viewholder_support);
            if (tag != null) {
                b.startDrag((RecyclerView.ViewHolder) tag);
                return true;
            }
            throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.ViewHolder");
        }
    }

    /* compiled from: DraggableModule.kt */
    public static final class c implements View.OnTouchListener {
        final /* synthetic */ a c;

        c(a aVar) {
            this.c = aVar;
        }

        public final boolean onTouch(View v, MotionEvent event) {
            k.d(event, NotificationCompat.CATEGORY_EVENT);
            if (event.getAction() != 0 || this.c.j()) {
                return false;
            }
            if (this.c.i()) {
                ItemTouchHelper b = this.c.b();
                Object tag = v.getTag(R$id.BaseQuickAdapter_viewholder_support);
                if (tag != null) {
                    b.startDrag((RecyclerView.ViewHolder) tag);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.ViewHolder");
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public final int d(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.e(viewHolder, "viewHolder");
        return viewHolder.getAdapterPosition() - this.l.getHeaderLayoutCount();
    }

    public void n(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.e(viewHolder, "viewHolder");
        e eVar = this.i;
        if (eVar != null) {
            eVar.onItemDragStart(viewHolder, d(viewHolder));
        }
    }

    public void m(@NotNull RecyclerView.ViewHolder source, @NotNull RecyclerView.ViewHolder target) {
        k.e(source, "source");
        k.e(target, TypedValues.AttributesType.S_TARGET);
        int from = d(source);
        int to = d(target);
        if (f(from) && f(to)) {
            if (from < to) {
                for (int i2 = from; i2 < to; i2++) {
                    Collections.swap(this.l.getData(), i2, i2 + 1);
                }
            } else {
                int i3 = to + 1;
                if (from >= i3) {
                    int i4 = from;
                    while (true) {
                        Collections.swap(this.l.getData(), i4, i4 - 1);
                        if (i4 == i3) {
                            break;
                        }
                        i4--;
                    }
                }
            }
            this.l.notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
        }
        e eVar = this.i;
        if (eVar != null) {
            eVar.onItemDragMoving(source, from, target, to);
        }
    }

    public void l(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.e(viewHolder, "viewHolder");
        e eVar = this.i;
        if (eVar != null) {
            eVar.onItemDragEnd(viewHolder, d(viewHolder));
        }
    }

    public void p(@NotNull RecyclerView.ViewHolder viewHolder) {
        g gVar;
        k.e(viewHolder, "viewHolder");
        if (this.c && (gVar = this.j) != null) {
            gVar.onItemSwipeStart(viewHolder, d(viewHolder));
        }
    }

    public void o(@NotNull RecyclerView.ViewHolder viewHolder) {
        g gVar;
        k.e(viewHolder, "viewHolder");
        if (this.c && (gVar = this.j) != null) {
            gVar.clearView(viewHolder, d(viewHolder));
        }
    }

    public void q(@NotNull RecyclerView.ViewHolder viewHolder) {
        g gVar;
        k.e(viewHolder, "viewHolder");
        int pos = d(viewHolder);
        if (f(pos)) {
            this.l.getData().remove(pos);
            this.l.notifyItemRemoved(viewHolder.getAdapterPosition());
            if (this.c && (gVar = this.j) != null) {
                gVar.onItemSwiped(viewHolder, pos);
            }
        }
    }

    public void r(@Nullable Canvas canvas, @Nullable RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        g gVar;
        if (this.c && (gVar = this.j) != null) {
            gVar.onItemSwipeMoving(canvas, viewHolder, dX, dY, isCurrentlyActive);
        }
    }

    private final boolean f(int position) {
        return position >= 0 && position < this.l.getData().size();
    }

    public void setOnItemDragListener(@Nullable e onItemDragListener) {
        this.i = onItemDragListener;
    }

    public void setOnItemSwipeListener(@Nullable g onItemSwipeListener) {
        this.j = onItemSwipeListener;
    }

    /* renamed from: com.chad.library.adapter.base.module.a$a  reason: collision with other inner class name */
    /* compiled from: DraggableModule.kt */
    public static final class C0060a {
        private C0060a() {
        }

        public /* synthetic */ C0060a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
