package com.leedarson.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.c;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$id;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.logger.a;
import com.leedarson.bean.LocalMedia;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.e;
import com.leedarson.view.LDSImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MediaQuickAdapter extends BaseSectionQuickAdapter<LocalMedia, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean P4 = true;
    private boolean Q4 = false;

    public /* bridge */ /* synthetic */ void d(@NotNull BaseViewHolder baseViewHolder, Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 87, clsArr, Void.TYPE).isSupported) {
            z(baseViewHolder, (LocalMedia) obj);
        }
    }

    public /* bridge */ /* synthetic */ void x(@NotNull BaseViewHolder baseViewHolder, @NotNull c cVar) {
        Class[] clsArr = {BaseViewHolder.class, c.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, cVar}, this, changeQuickRedirect, false, 86, clsArr, Void.TYPE).isSupported) {
            A(baseViewHolder, (LocalMedia) cVar);
        }
    }

    public MediaQuickAdapter(int sectionHeadResId, int layoutResId, @Nullable List<LocalMedia> data) {
        super(sectionHeadResId, layoutResId, data);
        setNormalLayout(layoutResId);
    }

    public void C(boolean video) {
        this.P4 = video;
    }

    public void B(boolean checkMode) {
        Object[] objArr = {new Byte(checkMode ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 83, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.Q4 = checkMode;
            notifyDataSetChanged();
        }
    }

    public void A(@NotNull BaseViewHolder baseViewHolder, @NotNull LocalMedia media) {
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, media}, this, changeQuickRedirect, false, 84, new Class[]{BaseViewHolder.class, LocalMedia.class}, Void.TYPE).isSupported) {
            baseViewHolder.setText(R$id.txt_date, (CharSequence) PubUtils.getDateForMMMTime(System.currentTimeMillis()).equals(media.getFormaDate()) ? PubUtils.getString(BaseApplication.b(), R$string.today) : media.getFormaDate());
        }
    }

    public void z(@NotNull BaseViewHolder holder, LocalMedia media) {
        if (!PatchProxy.proxy(new Object[]{holder, media}, this, changeQuickRedirect, false, 85, new Class[]{BaseViewHolder.class, LocalMedia.class}, Void.TYPE).isSupported) {
            if (this.P4) {
                holder.setVisible(R$id.img_play, true);
                int i = R$id.txt_duration;
                holder.setVisible(i, true);
                holder.setText(i, (CharSequence) e.c(media.getDuration() / 1000));
            } else {
                holder.setVisible(R$id.img_play, false);
                holder.setVisible(R$id.txt_duration, false);
            }
            if (this.Q4) {
                holder.setVisible(R$id.img_check, true);
            } else {
                holder.setVisible(R$id.img_check, false);
            }
            ((ImageView) holder.findView(R$id.img_check)).setSelected(media.isChecked());
            int i2 = R$id.image;
            ImageView imageView = (ImageView) holder.findView(i2);
            if (holder.findView(i2) instanceof LDSImageView) {
                LDSImageView ldsImageView = (LDSImageView) holder.findView(i2);
                int i3 = R$id.item_tag;
                if (ldsImageView.getTag(i3) == null || !ldsImageView.getTag(i3).toString().equals(media.getPath())) {
                    a.c(this, "###############   itemTag=" + ldsImageView.getTag(i3) + "   currentPath=" + media.getPath());
                    ldsImageView.setImgUrl(media.getPath());
                }
                a.c(this, "###############>>>>>>>>>>>>>>>>>>     currentPath=" + media.getPath());
                ldsImageView.setTag(i3, media.getPath());
            }
        }
    }
}
