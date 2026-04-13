package com.leedarson.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.EventBean;
import com.leedarson.newui.ai.widget.AiMarkLayoutView;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.e;
import com.leedarson.view.LDSImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a;
    private int b;
    private final LayoutInflater c;
    /* access modifiers changed from: private */
    public final Context d;
    private List<EventListItemBean> e;
    private b f;
    private c g;

    public interface b {
        void a(View view, int i);
    }

    public interface c {
        void onItemClick(View view, int i);
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 44, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            b((ViewHolder) viewHolder, i);
        }
    }

    public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 45, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : c(viewGroup, i);
    }

    public ViewHolder c(ViewGroup parent, int i) {
        Object[] objArr = {parent, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 40, new Class[]{ViewGroup.class, Integer.TYPE}, ViewHolder.class);
        if (proxy.isSupported) {
            return (ViewHolder) proxy.result;
        }
        return new ViewHolder(this.c.inflate(R$layout.item_event, parent, false), this.g, this.f);
    }

    public void b(ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 41, new Class[]{ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            EventListItemBean eventBean = this.e.get(position);
            holder.j.b();
            holder.k.setVisibility(8);
            if (this.b == position) {
                if (eventBean.getHasVideo() == 1) {
                    holder.d.setVisibility(0);
                } else {
                    holder.d.setVisibility(8);
                }
                holder.h.setBackgroundResource(R$drawable.selected_border_bg);
            } else {
                holder.d.setVisibility(8);
                holder.h.setBackgroundColor(-1);
            }
            holder.a.setText(eventBean.getDeviceName());
            holder.b.setText(e.d(this.d, eventBean.eventTime, "hh:mm"));
            String[] eventDescs = eventBean.getEventDescList();
            if (eventDescs != null && this.a) {
                holder.f.setAdapter(new a(Arrays.asList(eventDescs)));
            }
            if (eventBean.getFeedback() == 1) {
                holder.d.setEnabled(false);
                holder.d.setClickable(false);
                holder.d.setImageDrawable(this.d.getDrawable(R$drawable.ic_events_icon_feedback02));
            } else {
                holder.d.setEnabled(true);
                holder.d.setClickable(true);
                holder.d.setImageDrawable(this.d.getDrawable(R$drawable.ic_events_icon_feedback01));
            }
            holder.b(eventBean);
            holder.a(eventBean);
        }
    }

    public class a extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 47, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 46, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(EventListAdapter.this.d).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 42, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        List<EventListItemBean> list = this.e;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        LDSTextView a;
        LDSTextView b;
        LDSImageView c;
        ImageView d;
        ImageView e;
        TagFlowLayout f;
        LinearLayout g;
        RelativeLayout h;
        RelativeLayout i;
        AiMarkLayoutView j;
        LDSTextView k;

        ViewHolder(View view, c mListener, b feedBackListener) {
            super(view);
            this.a = (LDSTextView) view.findViewById(R$id.tv_name);
            this.b = (LDSTextView) view.findViewById(R$id.tv_time);
            this.c = (LDSImageView) view.findViewById(R$id.iv_thumbnail);
            this.d = (ImageView) view.findViewById(R$id.iv_comment);
            this.f = (TagFlowLayout) view.findViewById(R$id.fl_keyword);
            this.h = (RelativeLayout) view.findViewById(R$id.rl_image);
            this.g = (LinearLayout) view.findViewById(R$id.ll_event);
            this.j = (AiMarkLayoutView) view.findViewById(R$id.aiMarkLayout);
            this.k = (LDSTextView) view.findViewById(R$id.tvEventStatuesTag);
            this.i = (RelativeLayout) view.findViewById(R$id.rlThumbContainer);
            this.e = (ImageView) view.findViewById(R$id.imgNotUploadHolder);
            this.d.setOnClickListener(new e(this, mListener, feedBackListener));
            this.g.setOnClickListener(new d(this, mListener));
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: c */
        public /* synthetic */ void d(c mListener, b feedBackListener, View view) {
            Class[] clsArr = {c.class, b.class, View.class};
            if (PatchProxy.proxy(new Object[]{mListener, feedBackListener, view}, this, changeQuickRedirect, false, 51, clsArr, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            if (mListener != null) {
                feedBackListener.a(v, getLayoutPosition() - 1);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: e */
        public /* synthetic */ void f(c mListener, View view) {
            if (PatchProxy.proxy(new Object[]{mListener, view}, this, changeQuickRedirect, false, 50, new Class[]{c.class, View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition() - 1);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* access modifiers changed from: package-private */
        public void b(EventBean eventBean) {
            if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 48, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
                this.i.setAlpha(1.0f);
                this.k.setAlpha(1.0f);
                this.j.setAlpha(1.0f);
                this.e.setVisibility(8);
                if (eventBean.expiredFlag == 1) {
                    this.k.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_playback_video_statue_expired));
                    this.k.setVisibility(0);
                    this.i.setAlpha(0.5f);
                    this.j.setAlpha(0.5f);
                } else if (eventBean.getPlayFlag() == 0) {
                    this.k.setText(eventBean.getTagName());
                    this.k.setVisibility(0);
                    this.i.setAlpha(0.5f);
                    this.j.setAlpha(0.5f);
                    this.e.setVisibility(0);
                } else {
                    this.k.setVisibility(8);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(EventBean eventBean) {
            if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 49, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
                this.j.setVisibility(8);
                this.e.setVisibility(8);
                a aVar = new a(eventBean);
                int i2 = R$drawable.shape_c4_all;
                this.c.b(eventBean.picUrl + "", 16, aVar, i2);
                if (TextUtils.isEmpty(eventBean.picUrl)) {
                    this.c.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i2));
                    this.e.setVisibility(0);
                } else {
                    String thumbnailTag = (String) this.c.getTag();
                    if (thumbnailTag != null) {
                        if (!thumbnailTag.equals(eventBean.picUrl + "")) {
                            this.c.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i2));
                            this.c.a(eventBean.picUrl + "", 16);
                        }
                    }
                    this.c.a(eventBean.picUrl + "", 16);
                }
                this.c.setTag(eventBean.picUrl + "");
            }
        }

        public class a implements LDSImageView.d {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ EventBean a;

            a(EventBean eventBean) {
                this.a = eventBean;
            }

            public void b(View view, String url, int i, int i2) {
                Object[] objArr = {view, url, new Integer(i), new Integer(i2)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 52, new Class[]{View.class, String.class, cls, cls}, Void.TYPE).isSupported) {
                    if (url.equals(this.a.picUrl)) {
                        ArrayList<ArrayList<String>> arrayList = this.a.positions;
                        if (arrayList == null || arrayList.size() <= 0) {
                            ViewHolder.this.j.setVisibility(8);
                            return;
                        }
                        ViewHolder.this.j.setVisibility(0);
                        ViewHolder.this.j.a(this.a.getAiMarksList());
                    }
                }
            }

            public void a(View view, String url) {
                if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 53, new Class[]{View.class, String.class}, Void.TYPE).isSupported) {
                    if (url.equals(this.a.picUrl)) {
                        ViewHolder.this.e.setVisibility(0);
                    }
                }
            }
        }
    }

    public void setOnItemClickListener(c listener) {
        this.g = listener;
    }

    public void setOnFeedBackClickListener(b listener) {
        this.f = listener;
    }
}
