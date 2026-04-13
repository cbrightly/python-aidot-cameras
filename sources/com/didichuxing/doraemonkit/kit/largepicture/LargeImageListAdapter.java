package com.didichuxing.doraemonkit.kit.largepicture;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.f;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.picasso.DokitPicasso;
import com.didichuxing.doraemonkit.picasso.MemoryPolicy;
import com.didichuxing.doraemonkit.util.ClipboardUtils;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.text.DecimalFormat;

public class LargeImageListAdapter extends AbsRecyclerAdapter<AbsViewBinder<LargeImageInfo>, LargeImageInfo> {
    /* access modifiers changed from: private */
    public DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");

    public LargeImageListAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<LargeImageInfo> createViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_large_img_list, parent, false);
    }

    public class ItemViewHolder extends AbsViewBinder<LargeImageInfo> {
        private Button btnCopy;
        private ImageView iv;
        private TextView tvFileSize;
        private TextView tvFrameWork;
        private TextView tvLink;
        private TextView tvMemorySize;
        private TextView tvSize;

        public ItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.iv = (ImageView) getView(R.id.iv);
            this.tvLink = (TextView) getView(R.id.tv_link);
            this.tvFrameWork = (TextView) getView(R.id.tv_framework);
            this.tvFileSize = (TextView) getView(R.id.tv_file_size);
            this.tvMemorySize = (TextView) getView(R.id.tv_memory_size);
            this.tvSize = (TextView) getView(R.id.tv_size);
            this.btnCopy = (Button) getView(R.id.btn_copy);
        }

        public void bind(final LargeImageInfo largeImageInfo) {
            try {
                int resourceUrl = Integer.parseInt(largeImageInfo.getUrl());
                DokitPicasso.with(DoraemonKit.APPLICATION).load(resourceUrl).memoryPolicy(MemoryPolicy.NO_CACHE, new MemoryPolicy[0]).resize(f.e(100.0f), f.e(100.0f)).centerCrop().into(this.iv);
                TextView textView = this.tvLink;
                textView.setText("resource id:" + resourceUrl);
            } catch (Exception e) {
                DokitPicasso.with(DoraemonKit.APPLICATION).load(largeImageInfo.getUrl()).memoryPolicy(MemoryPolicy.NO_CACHE, new MemoryPolicy[0]).resize(f.e(100.0f), f.e(100.0f)).centerCrop().into(this.iv);
                this.tvLink.setText(largeImageInfo.getUrl());
            }
            if (largeImageInfo.getMemorySize() == 0.0d) {
                this.tvFrameWork.setText(String.format("framework:%s", new Object[]{"network"}));
                this.tvMemorySize.setVisibility(8);
                this.tvSize.setVisibility(8);
            } else {
                this.tvFrameWork.setText(String.format("framework:%s", new Object[]{largeImageInfo.getFramework()}));
                this.tvMemorySize.setVisibility(0);
                this.tvSize.setVisibility(0);
            }
            if (largeImageInfo.getFileSize() == 0.0d) {
                this.tvFileSize.setVisibility(8);
            } else {
                this.tvFileSize.setVisibility(0);
            }
            TextView textView2 = this.tvFileSize;
            textView2.setText(String.format("fileSize:%s", new Object[]{LargeImageListAdapter.this.mDecimalFormat.format(largeImageInfo.getFileSize()) + "KB"}));
            TextView textView3 = this.tvMemorySize;
            textView3.setText(String.format("memorySize:%s", new Object[]{LargeImageListAdapter.this.mDecimalFormat.format(largeImageInfo.getMemorySize()) + "MB"}));
            this.tvSize.setText(String.format("width:%s   height:%s", new Object[]{Integer.valueOf(largeImageInfo.getWidth()), Integer.valueOf(largeImageInfo.getHeight())}));
            this.btnCopy.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    ClipboardUtils.copyUri(Uri.parse(largeImageInfo.getUrl()));
                    e0.n("image url  has copied");
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }
}
