package com.leedarson.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.h;
import com.bumptech.glide.load.resource.bitmap.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.f;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.EventBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.Arrays;
import java.util.List;

public class EditEventsAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final LayoutInflater a;
    /* access modifiers changed from: private */
    public final Context b;
    private List<EventListItemBean> c;
    private b d;

    public interface b {
        void onItemClick(View view, int i);
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 14, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            b((ViewHolder) viewHolder, i);
        }
    }

    public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 15, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : c(viewGroup, i);
    }

    public EditEventsAdapter(Context context, List<EventListItemBean> list) {
        this.c = list;
        this.b = context;
        this.a = LayoutInflater.from(context);
    }

    public ViewHolder c(ViewGroup parent, int i) {
        Object[] objArr = {parent, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11, new Class[]{ViewGroup.class, Integer.TYPE}, ViewHolder.class);
        if (proxy.isSupported) {
            return (ViewHolder) proxy.result;
        }
        return new ViewHolder(this.a.inflate(R$layout.item_event_edit, parent, false), this.d);
    }

    public void b(ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 12, new Class[]{ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            EventListItemBean eventBean = this.c.get(position);
            holder.h = eventBean;
            h<Bitmap> i = com.bumptech.glide.b.t(this.b).i();
            i.M0(eventBean.picUrl + "").a(new f().t0(new i(), new x(16))).H0(holder.c);
            holder.a.setText(eventBean.getDeviceName());
            holder.d.setSelected(eventBean.isChecked());
            holder.b.setText(e.d(this.b, eventBean.eventTime, "hh:mm"));
            String[] eventDescs = eventBean.getEventDescList();
            if (eventDescs != null) {
                holder.e.setAdapter(new a(Arrays.asList(eventDescs)));
            }
        }
    }

    public class a extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 17, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 16, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(EditEventsAdapter.this.b).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        List<EventListItemBean> list = this.c;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LDSTextView a;
        LDSTextView b;
        ImageView c;
        ImageView d;
        TagFlowLayout e;
        RelativeLayout f;
        b g;
        EventBean h;

        ViewHolder(View view, b mListener) {
            super(view);
            this.a = (LDSTextView) view.findViewById(R$id.tv_name);
            this.b = (LDSTextView) view.findViewById(R$id.tv_time);
            this.d = (ImageView) view.findViewById(R$id.iv_check);
            this.c = (ImageView) view.findViewById(R$id.iv_thumbnail);
            this.e = (TagFlowLayout) view.findViewById(R$id.fl_keyword);
            this.f = (RelativeLayout) view.findViewById(R$id.rl_image);
            this.g = mListener;
            view.setOnClickListener(new a(mListener));
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ b c;

            a(b bVar) {
                this.c = bVar;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 18, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View v = view;
                Log.d("ViewHolder", "onClick--> position = " + ViewHolder.this.getPosition());
                EventBean eventBean = ViewHolder.this.h;
                eventBean.setChecked(true ^ eventBean.isChecked());
                ViewHolder viewHolder = ViewHolder.this;
                viewHolder.d.setSelected(viewHolder.h.isChecked());
                b bVar = this.c;
                if (bVar != null) {
                    bVar.onItemClick(v, ViewHolder.this.getAdapterPosition());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public void setOnItemClickListener(b listener) {
        this.d = listener;
    }
}
