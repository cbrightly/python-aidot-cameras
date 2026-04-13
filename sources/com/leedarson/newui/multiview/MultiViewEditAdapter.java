package com.leedarson.newui.multiview;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.b;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.f;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.d;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MultiViewEditAdapter.kt */
public final class MultiViewEditAdapter extends BaseQuickAdapter<IpcDeviceComparableBean, BaseViewHolder> implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String N4;

    public MultiViewEditAdapter() {
        super(R$layout.item_multi_view_edit_portrait, (List) null, 2, (DefaultConstructorMarker) null);
    }

    public /* bridge */ /* synthetic */ void d(BaseViewHolder holder, Object item) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{holder, item}, this, changeQuickRedirect, false, 4207, clsArr, Void.TYPE).isSupported) {
            x(holder, (IpcDeviceComparableBean) item);
        }
    }

    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        if (!PatchProxy.proxy(new Object[]{recyclerView}, this, changeQuickRedirect, false, 4205, new Class[]{RecyclerView.class}, Void.TYPE).isSupported) {
            k.e(recyclerView, "recyclerView");
            super.onAttachedToRecyclerView(recyclerView);
            String prefString = SharePreferenceUtils.getPrefString(getContext(), "userId", "");
            k.d(prefString, "getPrefString(context,\"userId\",\"\")");
            this.N4 = prefString;
        }
    }

    public void x(@NotNull BaseViewHolder holder, @NotNull IpcDeviceComparableBean item) {
        if (!PatchProxy.proxy(new Object[]{holder, item}, this, changeQuickRedirect, false, 4206, new Class[]{BaseViewHolder.class, IpcDeviceComparableBean.class}, Void.TYPE).isSupported) {
            k.e(holder, "holder");
            k.e(item, "item");
            ((TextView) holder.getView(R$id.tv_multi_view_edit)).setText(item.getIpcDeviceBean().name);
            ImageView $this$convert_u24lambda_u2d0 = (ImageView) holder.getView(R$id.img_multi_view_edit);
            h<Bitmap> i = b.t($this$convert_u24lambda_u2d0.getContext()).i();
            StringBuilder sb = new StringBuilder();
            sb.append($this$convert_u24lambda_u2d0.getContext().getFilesDir().getPath());
            sb.append("/web/static/media/");
            sb.append(item.getIpcDeviceBean().id);
            String str = this.N4;
            if (str != null) {
                sb.append(str);
                sb.append("_tempPhoto.jpg");
                ((h) ((h) i.M0(sb.toString()).j(0)).f(i.d)).a(new f().t0(new com.bumptech.glide.load.resource.bitmap.i(), new x(16))).D0(new a($this$convert_u24lambda_u2d0));
                return;
            }
            k.t("userId");
            throw null;
        }
    }

    /* compiled from: MultiViewEditAdapter.kt */
    public static final class a extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView $receiver) {
            super($receiver);
            this.p1 = $receiver;
        }

        public /* bridge */ /* synthetic */ void n(Object p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 4209, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) p0);
            }
        }

        public void p(@Nullable Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 4208, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    ImageView imageView = this.p1;
                    Bitmap it = resource;
                    if (it.getHeight() > it.getWidth()) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    imageView.setImageBitmap(resource);
                }
            }
        }
    }
}
