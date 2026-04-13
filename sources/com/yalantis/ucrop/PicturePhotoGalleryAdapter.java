package com.yalantis.ucrop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class PicturePhotoGalleryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<LocalMedia> list;
    /* access modifiers changed from: private */
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }

    public PicturePhotoGalleryAdapter(List<LocalMedia> list2) {
        this.list = list2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ucrop_picture_gf_adapter_edit_list, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        LocalMedia photoInfo = this.list.get(position);
        String path = photoInfo.getPath();
        int i = 0;
        if (photoInfo.isCut()) {
            holder.iv_dot.setVisibility(0);
            holder.iv_dot.setImageResource(R.drawable.ucrop_oval_true);
        } else {
            holder.iv_dot.setVisibility(4);
        }
        if (PictureMimeType.isHasVideo(photoInfo.getMimeType())) {
            holder.mIvPhoto.setVisibility(8);
            holder.mIvVideo.setVisibility(0);
            holder.mIvVideo.setImageResource(R.drawable.ucrop_ic_default_video);
            return;
        }
        holder.mIvPhoto.setVisibility(0);
        holder.mIvVideo.setVisibility(8);
        TextView textView = holder.tvGif;
        if (!PictureMimeType.isGif(photoInfo.getMimeType())) {
            i = 8;
        }
        textView.setVisibility(i);
        ImageEngine imageEngine = PictureSelectionConfig.imageEngine;
        if (imageEngine != null) {
            imageEngine.loadGridImage(holder.itemView.getContext(), path, holder.mIvPhoto);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                if (PicturePhotoGalleryAdapter.this.listener != null) {
                    PicturePhotoGalleryAdapter.this.listener.onItemClick(holder.getAbsoluteAdapterPosition(), v);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public int getItemCount() {
        List<LocalMedia> list2 = this.list;
        if (list2 != null) {
            return list2.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_dot;
        ImageView mIvPhoto;
        ImageView mIvVideo;
        TextView tvGif;

        public ViewHolder(View view) {
            super(view);
            this.mIvPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            this.mIvVideo = (ImageView) view.findViewById(R.id.iv_video);
            this.iv_dot = (ImageView) view.findViewById(R.id.iv_dot);
            this.tvGif = (TextView) view.findViewById(R.id.tv_gif);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener2) {
        this.listener = listener2;
    }
}
