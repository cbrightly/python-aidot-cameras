package com.leedarson.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.CloudEventEntity;
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
import java.util.Locale;

public class EventList2Adapter extends RecyclerView.Adapter<ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a = true;
    private int b;
    private final LayoutInflater c;
    /* access modifiers changed from: private */
    public final Context d;
    private List<CloudEventEntity> e;
    private b f;
    private c g;

    public interface b {
        void a(View view, int i);
    }

    public interface c {
        void a(int i, boolean z);
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 29, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            c((ViewHolder) viewHolder, i);
        }
    }

    public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 30, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : d(viewGroup, i);
    }

    public EventList2Adapter(Context context, List<CloudEventEntity> list) {
        this.e = list;
        this.d = context;
        this.c = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 24, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            if (position < this.e.size() || position == 0) {
                return this.e.get(position).getItemType();
            }
            List<CloudEventEntity> list = this.e;
            return list.get(list.size() - 1).getItemType();
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public ViewHolder d(ViewGroup parent, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parent, new Integer(viewType)}, this, changeQuickRedirect, false, 25, new Class[]{ViewGroup.class, Integer.TYPE}, ViewHolder.class);
        if (proxy.isSupported) {
            return (ViewHolder) proxy.result;
        }
        if (viewType == 1) {
            return new ViewHolder(this.c.inflate(R$layout.item_event_fold, parent, false), this.g);
        }
        return new ViewHolder(this.c.inflate(R$layout.item_event, parent, false), this.g, this.f);
    }

    public void c(ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 26, new Class[]{ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (position != 0 && position >= this.e.size()) {
                return;
            }
            if (getItemViewType(position) == 1) {
                holder.n.setText(String.format(Locale.US, PubUtils.getString(this.d, R$string.lds_incom_records), new Object[]{Integer.valueOf(this.e.get(position).getFoldLists().size())}));
                return;
            }
            EventListItemBean eventBean = this.e.get(position).getEventListItemBean();
            if (position + 1 <= this.e.size() && getItemViewType(position + 1) == 1) {
                holder.l.setVisibility(8);
            }
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
            holder.c(eventBean);
            holder.b(eventBean);
        }
    }

    public class a extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 32, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 31, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(EventList2Adapter.this.d).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 27, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        List<CloudEventEntity> list = this.e;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void e(int currentPosition) {
        this.b = currentPosition;
    }

    public int b() {
        return this.b;
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
        View l;
        private View m;
        /* access modifiers changed from: private */
        public TextView n;

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
            this.l = view.findViewById(R$id.view_event_line);
            this.d.setOnClickListener(new b(this, mListener, feedBackListener));
            this.g.setOnClickListener(new a(this, mListener));
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: d */
        public /* synthetic */ void e(c mListener, b feedBackListener, View view) {
            Class[] clsArr = {c.class, b.class, View.class};
            if (PatchProxy.proxy(new Object[]{mListener, feedBackListener, view}, this, changeQuickRedirect, false, 37, clsArr, Void.TYPE).isSupported) {
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
        /* renamed from: f */
        public /* synthetic */ void g(c mListener, View view) {
            if (PatchProxy.proxy(new Object[]{mListener, view}, this, changeQuickRedirect, false, 36, new Class[]{c.class, View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (mListener != null) {
                mListener.a(getLayoutPosition() - 1, false);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        ViewHolder(View view, c mListener) {
            super(view);
            this.m = view.findViewById(R$id.layout_fold);
            this.n = (TextView) view.findViewById(R$id.tv_fold);
            this.m.setOnClickListener(new c(this, mListener));
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: h */
        public /* synthetic */ void i(c mListener, View view) {
            if (PatchProxy.proxy(new Object[]{mListener, view}, this, changeQuickRedirect, false, 35, new Class[]{c.class, View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (mListener != null) {
                mListener.a(getLayoutPosition() - 1, true);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* access modifiers changed from: package-private */
        public void c(EventBean eventBean) {
            if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 33, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
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
        public void b(EventBean eventBean) {
            if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 34, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
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
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 38, new Class[]{View.class, String.class, cls, cls}, Void.TYPE).isSupported) {
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
                if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 39, new Class[]{View.class, String.class}, Void.TYPE).isSupported) {
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

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f(int r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 28
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            java.util.List<com.leedarson.bean.CloudEventEntity> r1 = r0.e
            if (r1 == 0) goto L_0x0040
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x0040
            java.util.List<com.leedarson.bean.CloudEventEntity> r1 = r0.e
            int r2 = r0.b
            java.lang.Object r1 = r1.get(r2)
            com.leedarson.bean.CloudEventEntity r1 = (com.leedarson.bean.CloudEventEntity) r1
            com.leedarson.newui.repos.beans.EventListItemBean r1 = r1.getEventListItemBean()
            r1.setFeedback(r9)
        L_0x0040:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.adapter.EventList2Adapter.f(int):void");
    }
}
