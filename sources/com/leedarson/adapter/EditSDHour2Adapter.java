package com.leedarson.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.b;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.f;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$id;
import com.leedarson.bean.SDRecord;
import com.leedarson.smartcamera.utils.d;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;

public class EditSDHour2Adapter extends BaseQuickAdapter<SDRecord, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String N4;
    private String O4;
    private boolean P4 = false;

    public /* bridge */ /* synthetic */ void d(@NonNull BaseViewHolder baseViewHolder, Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 21, clsArr, Void.TYPE).isSupported) {
            x(baseViewHolder, (SDRecord) obj);
        }
    }

    public EditSDHour2Adapter(int layoutResId, @Nullable List<SDRecord> data, String p2pId, String eventStr) {
        super(layoutResId, data);
        this.N4 = p2pId;
        this.O4 = eventStr;
    }

    public void y(boolean checkMode) {
        if (!PatchProxy.proxy(new Object[]{new Byte(checkMode ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 19, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.P4 = checkMode;
            notifyItemRangeChanged(0, getData().size());
        }
    }

    public void x(@NonNull BaseViewHolder holder, SDRecord record) {
        if (!PatchProxy.proxy(new Object[]{holder, record}, this, changeQuickRedirect, false, 20, new Class[]{BaseViewHolder.class, SDRecord.class}, Void.TYPE).isSupported) {
            String str = this.O4;
            String str2 = this.N4;
            String url = d.d(str, str2, (record.getTimestamp() / 1000) + "");
            ImageView imageView = (ImageView) holder.findView(R$id.iv_thumbnail);
            ((h) ((h) ((h) b.t(getContext()).i().M0(url).d0(0)).j(0)).f(i.d)).a(new f().t0(new com.bumptech.glide.load.resource.bitmap.i(), new x(16))).D0(new a(imageView, imageView));
            holder.setText(R$id.tv_time, (CharSequence) e.j(record.getTimestamp(), "HH:mm"));
            ImageView checkBox = (ImageView) holder.findView(R$id.iv_check);
            if (this.P4) {
                checkBox.setSelected(record.isCheck());
                checkBox.setVisibility(0);
                return;
            }
            checkBox.setVisibility(8);
        }
    }

    public class a extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView view, ImageView imageView) {
            super(view);
            this.p1 = imageView;
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 23, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 22, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
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
}
