package com.leedarson.newui.pages.adapters;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.b;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.f;
import com.leedarson.R$id;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;
import com.leedarson.smartcamera.utils.d;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SDEventEditViewHolder.kt */
public final class SDEventEditViewHolder extends RecyclerView.ViewHolder {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SDEventEditViewHolder(@NotNull View itemView) {
        super(itemView);
        k.e(itemView, "itemView");
    }

    public final void a(@NotNull SDRecord sDRecord, int position, @Nullable SDCardEventEditAdapter.a callback, @NotNull String str, @NotNull String str2) {
        Class<String> cls = String.class;
        Object[] objArr = {sDRecord, new Integer(position), callback, str, str2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4315, new Class[]{SDRecord.class, Integer.TYPE, SDCardEventEditAdapter.a.class, cls, cls}, Void.TYPE).isSupported) {
            String eventStr = str;
            SDRecord itemData = sDRecord;
            String p2pId = str2;
            k.e(itemData, "itemData");
            k.e(eventStr, "eventStr");
            k.e(p2pId, "p2pId");
            ((LDSTextView) this.itemView.findViewById(R$id.tv_time)).setText(e.j(itemData.getTimestamp(), "HH:mm"));
            ImageView $this$bindView_u24lambda_u2d1 = (ImageView) this.itemView.findViewById(R$id.iv_check);
            $this$bindView_u24lambda_u2d1.setVisibility(0);
            $this$bindView_u24lambda_u2d1.setSelected(itemData.isCheck());
            ((h) ((h) ((h) b.t(this.itemView.getContext()).i().M0(d.d(eventStr, p2pId, String.valueOf(itemData.getTimestamp() / ((long) 1000)))).d0(0)).j(0)).f(i.d)).a(new f().t0(new com.bumptech.glide.load.resource.bitmap.i(), new x(16))).D0(new a((ImageView) this.itemView.findViewById(R$id.iv_thumbnail)));
            this.itemView.setOnClickListener(new b(callback, position, itemData));
        }
    }

    /* compiled from: SDEventEditViewHolder.kt */
    public static final class a extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView $_thumbnail) {
            super($_thumbnail);
            this.p1 = $_thumbnail;
        }

        public /* bridge */ /* synthetic */ void n(Object p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 4318, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) p0);
            }
        }

        public void p(@Nullable Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 4317, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    if (resource.getHeight() > resource.getWidth()) {
                        this.p1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        this.p1.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    this.p1.setImageBitmap(resource);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void b(SDCardEventEditAdapter.a $callback, int $position, SDRecord $itemData, View view) {
        Object[] objArr = {$callback, new Integer($position), $itemData, view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 4316, new Class[]{SDCardEventEditAdapter.a.class, Integer.TYPE, SDRecord.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e($itemData, "$itemData");
        if ($callback != null) {
            $callback.b($position, $itemData);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
