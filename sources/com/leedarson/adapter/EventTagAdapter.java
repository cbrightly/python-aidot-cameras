package com.leedarson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.EventTagBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class EventTagAdapter extends RecyclerView.Adapter {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private final int b = 100;
    private int c = 0;
    /* access modifiers changed from: private */
    public List<EventTagBean> d;
    private a e;

    public interface a {
        void onItemClick(View view, int i);
    }

    static /* synthetic */ int b(EventTagAdapter x0) {
        int i = x0.c + 1;
        x0.c = i;
        return i;
    }

    static /* synthetic */ int c(EventTagAdapter x0) {
        int i = x0.c;
        x0.c = i - 1;
        return i;
    }

    public EventTagAdapter(Context context, List<EventTagBean> list) {
        this.a = context;
        this.d = list;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 54, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        return new LabelHolder(LayoutInflater.from(this.a).inflate(R$layout.adapter_activity_label_content, (ViewGroup) null), this.e);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 55, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ((LabelHolder) holder).c(position);
        }
    }

    public List<EventTagBean> d() {
        return this.d;
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 56, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.d.size();
    }

    public int getItemViewType(int position) {
        return 100;
    }

    public class LabelHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public LDSTextView a;
        private final LinearLayout b;
        /* access modifiers changed from: private */
        public a c;

        public LabelHolder(View inflate, a mListener) {
            super(inflate);
            this.a = (LDSTextView) inflate.findViewById(R$id.tv_content);
            this.b = (LinearLayout) inflate.findViewById(R$id.llContent);
            this.c = mListener;
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int c;

            a(int i) {
                this.c = i;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 58, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View v = view;
                if (((EventTagBean) EventTagAdapter.this.d.get(this.c)).isSelect()) {
                    EventTagAdapter.c(EventTagAdapter.this);
                }
                if (LabelHolder.this.a.isSelected()) {
                    LabelHolder.this.a.setSelected(false);
                    ((EventTagBean) EventTagAdapter.this.d.get(this.c)).setSelect(false);
                } else {
                    EventTagAdapter.b(EventTagAdapter.this);
                    LabelHolder.this.a.setSelected(true);
                    ((EventTagBean) EventTagAdapter.this.d.get(this.c)).setSelect(true);
                }
                if (LabelHolder.this.c != null) {
                    LabelHolder.this.c.onItemClick(v, LabelHolder.this.getAdapterPosition());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }

        public void c(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 57, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                this.a.setText(((EventTagBean) EventTagAdapter.this.d.get(position)).getDesc());
                this.a.setOnClickListener(new a(position));
            }
        }
    }

    public void setOnItemClickListener(a listener) {
        this.e = listener;
    }
}
