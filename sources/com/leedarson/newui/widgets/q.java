package com.leedarson.newui.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.EventBean;
import com.leedarson.newui.ai.widget.AiMarkLayoutView;
import com.leedarson.newui.cloud_play_back.repos.beans.EventItemDetailBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.serviceinterface.utils.PubUtils;
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

/* compiled from: LDSMoreBottomDialog */
public class q extends com.leedarson.view.dialogs.c implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    LDSTextView A4;
    View B4;
    View C4;
    View D4;
    ImageView E4;
    ImageView F4;
    ImageView G4;
    LDSTextView H4;
    LDSTextView I4;
    LDSTextView J4;
    View K4;
    LDSImageView a1;
    TagFlowLayout a2;
    ImageView f;
    LDSTextView p0;
    ImageView p1;
    LinearLayout p2;
    RelativeLayout p3;
    RelativeLayout p4;
    TextView q;
    private e x;
    LDSTextView y;
    LDSTextView z;
    AiMarkLayoutView z4;

    /* compiled from: LDSMoreBottomDialog */
    public interface e {
        void a();

        void b();

        void c();
    }

    public q(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void e(int layoutId) {
        Object[] objArr = {new Integer(layoutId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5630, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.e(layoutId);
            j();
        }
    }

    /* access modifiers changed from: package-private */
    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5631, new Class[0], Void.TYPE).isSupported) {
            this.f = (ImageView) findViewById(R$id.iv_close);
            this.q = (TextView) findViewById(R$id.tv_title);
            this.f.setOnClickListener(new m(this));
            this.q.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_more));
            this.y = (LDSTextView) findViewById(R$id.tv_name);
            this.z = (LDSTextView) findViewById(R$id.tv_time);
            this.p0 = (LDSTextView) findViewById(R$id.tv_size);
            this.a1 = (LDSImageView) findViewById(R$id.iv_thumbnail);
            this.a2 = (TagFlowLayout) findViewById(R$id.fl_keyword);
            this.p3 = (RelativeLayout) findViewById(R$id.rl_image);
            this.p2 = (LinearLayout) findViewById(R$id.ll_event);
            this.z4 = (AiMarkLayoutView) findViewById(R$id.aiMarkLayout);
            this.A4 = (LDSTextView) findViewById(R$id.tvEventStatuesTag);
            this.p4 = (RelativeLayout) findViewById(R$id.rlThumbContainer);
            this.p1 = (ImageView) findViewById(R$id.imgNotUploadHolder);
            View findViewById = findViewById(R$id.layout_share);
            this.B4 = findViewById;
            findViewById.setOnClickListener(this);
            View findViewById2 = findViewById(R$id.layout_download);
            this.C4 = findViewById2;
            findViewById2.setOnClickListener(this);
            View findViewById3 = findViewById(R$id.layout_del);
            this.D4 = findViewById3;
            findViewById3.setOnClickListener(this);
            this.E4 = (ImageView) findViewById(R$id.img_share);
            this.H4 = (LDSTextView) findViewById(R$id.tv_share);
            this.F4 = (ImageView) findViewById(R$id.img_download);
            this.I4 = (LDSTextView) findViewById(R$id.tv_download);
            this.G4 = (ImageView) findViewById(R$id.img_del);
            this.J4 = (LDSTextView) findViewById(R$id.tv_del);
            this.K4 = findViewById(R$id.line_del);
            o(false);
            getWindow().setGravity(80);
            getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: k */
    public /* synthetic */ void l(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5641, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void p(e moreCallHandler) {
        this.x = moreCallHandler;
    }

    public void n(EventListItemBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 5632, new Class[]{EventListItemBean.class}, Void.TYPE).isSupported) {
            this.p0.setText("");
            if (eventBean != null) {
                this.z4.b();
                this.A4.setVisibility(8);
                this.y.setText(eventBean.getDeviceName());
                this.z.setText(com.leedarson.utils.e.d(getContext(), eventBean.eventTime, "hh:mm"));
                String[] eventDescs = eventBean.getEventDescList();
                if (eventDescs != null) {
                    this.a2.setAdapter(new a(Arrays.asList(eventDescs)));
                }
                i(eventBean);
                g(eventBean);
                this.p2.setVisibility(0);
                return;
            }
            this.p2.setVisibility(8);
        }
    }

    /* compiled from: LDSMoreBottomDialog */
    public class a extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 5643, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5642, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(q.this.getContext()).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public void q(EventItemDetailBean data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5633, new Class[]{EventItemDetailBean.class}, Void.TYPE).isSupported) {
            this.p0.setText("");
            if (data != null) {
                this.z4.b();
                this.A4.setVisibility(8);
                this.y.setText(data.getDeviceName());
                this.z.setText(com.leedarson.utils.e.d(getContext(), Long.parseLong(data.getEventTime()), "hh:mm"));
                if (!TextUtils.isEmpty(data.getEventType())) {
                    List<String> eventDescList = new ArrayList<>();
                    String[] tagsRaw = data.getEventType().split(",");
                    for (String add : tagsRaw) {
                        eventDescList.add(add);
                    }
                    this.a2.setAdapter(new b(eventDescList));
                }
                this.p2.setVisibility(0);
                h(data);
                return;
            }
            this.p2.setVisibility(8);
        }
    }

    /* compiled from: LDSMoreBottomDialog */
    public class b extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 5645, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5644, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(q.this.getContext()).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public void r(long videoSize) {
        if (!PatchProxy.proxy(new Object[]{new Long(videoSize)}, this, changeQuickRedirect, false, 5634, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (videoSize > 0) {
                this.p0.setText(String.format(Locale.US, "%.2fM", new Object[]{Float.valueOf((((float) videoSize) / 1024.0f) / 1024.0f)}));
                return;
            }
            this.p0.setText("");
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5635, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.layout_share) {
            dismiss();
            e eVar = this.x;
            if (eVar != null) {
                eVar.c();
            }
        } else if (viewId == R$id.layout_download) {
            dismiss();
            e eVar2 = this.x;
            if (eVar2 != null) {
                eVar2.b();
            }
        } else if (viewId == R$id.layout_del) {
            dismiss();
            e eVar3 = this.x;
            if (eVar3 != null) {
                eVar3.a();
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: package-private */
    public void i(EventBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 5636, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
            this.p4.setAlpha(1.0f);
            this.A4.setAlpha(1.0f);
            this.z4.setAlpha(1.0f);
            this.p1.setVisibility(8);
            if (eventBean.expiredFlag == 1) {
                this.A4.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_playback_video_statue_expired));
                this.A4.setVisibility(0);
                this.p4.setAlpha(0.5f);
                this.z4.setAlpha(0.5f);
            } else if (eventBean.getPlayFlag() == 0) {
                this.A4.setText(eventBean.getTagName());
                this.A4.setVisibility(0);
                this.p4.setAlpha(0.5f);
                this.z4.setAlpha(0.5f);
                this.p1.setVisibility(0);
            } else {
                this.A4.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void h(EventItemDetailBean detailBean) {
        if (!PatchProxy.proxy(new Object[]{detailBean}, this, changeQuickRedirect, false, 5637, new Class[]{EventItemDetailBean.class}, Void.TYPE).isSupported) {
            this.z4.setVisibility(8);
            this.p1.setVisibility(8);
            c cVar = new c(detailBean);
            int i = R$drawable.shape_c4_all;
            this.a1.b(detailBean.getPicUrl() + "", 16, cVar, i);
            if (TextUtils.isEmpty(detailBean.getPicUrl())) {
                this.a1.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i));
                this.p1.setVisibility(0);
            } else {
                String thumbnailTag = (String) this.a1.getTag();
                if (thumbnailTag != null) {
                    if (!thumbnailTag.equals(detailBean.getPicUrl() + "")) {
                        this.a1.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i));
                        this.a1.a(detailBean.getPicUrl() + "", 16);
                    }
                }
                this.a1.a(detailBean.getPicUrl() + "", 16);
            }
            this.a1.setTag(detailBean.getPicUrl() + "");
        }
    }

    /* compiled from: LDSMoreBottomDialog */
    public class c implements LDSImageView.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ EventItemDetailBean a;

        c(EventItemDetailBean eventItemDetailBean) {
            this.a = eventItemDetailBean;
        }

        public void b(View targetImg, String url, int width, int height) {
        }

        public void a(View view, String url) {
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 5646, new Class[]{View.class, String.class}, Void.TYPE).isSupported) {
                if (url.equals(this.a.getPicUrl())) {
                    q.this.p1.setVisibility(0);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void g(EventBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 5638, new Class[]{EventBean.class}, Void.TYPE).isSupported) {
            this.z4.setVisibility(8);
            this.p1.setVisibility(8);
            d dVar = new d(eventBean);
            int i = R$drawable.shape_c4_all;
            this.a1.b(eventBean.picUrl + "", 16, dVar, i);
            if (TextUtils.isEmpty(eventBean.picUrl)) {
                this.a1.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i));
                this.p1.setVisibility(0);
            } else {
                String thumbnailTag = (String) this.a1.getTag();
                if (thumbnailTag != null) {
                    if (!thumbnailTag.equals(eventBean.picUrl + "")) {
                        this.a1.setImageDrawable(ContextCompat.getDrawable(BaseApplication.b(), i));
                        this.a1.a(eventBean.picUrl + "", 16);
                    }
                }
                this.a1.a(eventBean.picUrl + "", 16);
            }
            this.a1.setTag(eventBean.picUrl + "");
        }
    }

    /* compiled from: LDSMoreBottomDialog */
    public class d implements LDSImageView.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ EventBean a;

        d(EventBean eventBean) {
            this.a = eventBean;
        }

        public void b(View view, String url, int i, int i2) {
            Object[] objArr = {view, url, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5647, new Class[]{View.class, String.class, cls, cls}, Void.TYPE).isSupported) {
                if (url.equals(this.a.picUrl)) {
                    ArrayList<ArrayList<String>> arrayList = this.a.positions;
                    if (arrayList == null || arrayList.size() <= 0) {
                        q.this.z4.setVisibility(8);
                        return;
                    }
                    q.this.z4.setVisibility(0);
                    q.this.z4.a(this.a.getAiMarksList());
                }
            }
        }

        public void a(View view, String url) {
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 5648, new Class[]{View.class, String.class}, Void.TYPE).isSupported) {
                if (url.equals(this.a.picUrl)) {
                    q.this.p1.setVisibility(0);
                }
            }
        }
    }

    public void o(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5639, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.B4.setEnabled(enable);
            this.C4.setEnabled(enable);
            this.D4.setEnabled(enable);
            this.E4.setEnabled(enable);
            this.F4.setEnabled(enable);
            this.G4.setEnabled(enable);
            if (enable) {
                LDSTextView lDSTextView = this.H4;
                Resources resources = getContext().getResources();
                int i = R$color.grid_default_color;
                lDSTextView.setTextColor(resources.getColor(i));
                this.I4.setTextColor(getContext().getResources().getColor(i));
                this.J4.setTextColor(getContext().getResources().getColor(i));
                return;
            }
            LDSTextView lDSTextView2 = this.H4;
            Resources resources2 = getContext().getResources();
            int i2 = R$color.grid_disable_color;
            lDSTextView2.setTextColor(resources2.getColor(i2));
            this.I4.setTextColor(getContext().getResources().getColor(i2));
            this.J4.setTextColor(getContext().getResources().getColor(i2));
        }
    }

    public void m(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5640, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.D4.setVisibility(visibility);
            this.K4.setVisibility(visibility);
        }
    }
}
