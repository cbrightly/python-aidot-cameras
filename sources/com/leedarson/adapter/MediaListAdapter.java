package com.leedarson.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.LocalMedia;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;

public class MediaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private ArrayList<LocalMedia> b;
    private a c;

    public interface a {
        void onItemClick(View view, int i);
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 73, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.b.get(position).isHeader()) {
            return 1;
        }
        return 2;
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Object[] objArr = {parent, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 74, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        return new DateItemHolder(LayoutInflater.from(this.a).inflate(R$layout.item_date, parent, false));
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder baseHolder, int position) {
        if (!PatchProxy.proxy(new Object[]{baseHolder, new Integer(position)}, this, changeQuickRedirect, false, 75, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            LocalMedia media = this.b.get(position);
            String todayTimeSpan = PubUtils.getDateForMMMTime(System.currentTimeMillis());
            if (media.isHeader()) {
                ((DateItemHolder) baseHolder).a.setText(todayTimeSpan.equals(media.getFormaDate()) ? PubUtils.getString(BaseApplication.b(), R$string.today) : media.getFormaDate());
                return;
            }
            LDSTextView a2 = ((DateItemHolder) baseHolder).a;
            a2.setText(media.getDuration() + " ");
        }
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (!PatchProxy.proxy(new Object[]{recyclerView}, this, changeQuickRedirect, false, 76, new Class[]{RecyclerView.class}, Void.TYPE).isSupported) {
            super.onAttachedToRecyclerView(recyclerView);
            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public int getSpanSize(int position) {
                        Object[] objArr = {new Integer(position)};
                        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                        Class cls = Integer.TYPE;
                        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 81, new Class[]{cls}, cls);
                        if (proxy.isSupported) {
                            return ((Integer) proxy.result).intValue();
                        }
                        Log.e("MediaListAdapter", "getSpanSize: " + position);
                        if (MediaListAdapter.this.getItemViewType(position) == 2) {
                            return ((GridLayoutManager) layoutManager).getSpanCount();
                        }
                        return 1;
                    }
                });
            }
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 77, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        ArrayList<LocalMedia> arrayList = this.b;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public void setOnItemClickListener(a listener) {
        this.c = listener;
    }

    public class MediaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        private a c;

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 82, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            a aVar = this.c;
            if (aVar != null) {
                aVar.onItemClick(v, getAdapterPosition());
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class DateItemHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public LDSTextView a;

        public DateItemHolder(@NonNull View itemView) {
            super(itemView);
            this.a = (LDSTextView) itemView.findViewById(R$id.txt_date);
        }
    }
}
