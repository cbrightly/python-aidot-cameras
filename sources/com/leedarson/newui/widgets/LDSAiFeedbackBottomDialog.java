package com.leedarson.newui.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.EventTagAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.EventTagBean;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LDSAiFeedbackBottomDialog extends com.leedarson.view.dialogs.c {
    public static ChangeQuickRedirect changeQuickRedirect;
    RelativeLayout A4;
    RelativeLayout B4;
    e C4;
    TextView a1;
    RecyclerView a2;
    ImageView f;
    TextView p0;
    ImageView p1;
    private EventTagAdapter p2;
    private List<EventTagBean> p3 = new ArrayList();
    LinearLayout p4;
    ImageView q;
    TextView x;
    TextView y;
    TextView z;
    RelativeLayout z4;

    public interface e {
        void a();

        void b();

        void onClose();
    }

    public void w(e mHandlerCallback) {
        this.C4 = mHandlerCallback;
    }

    public LDSAiFeedbackBottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void e(int layoutId) {
        Object[] objArr = {new Integer(layoutId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5582, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.e(layoutId);
            h();
        }
    }

    /* access modifiers changed from: package-private */
    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5583, new Class[0], Void.TYPE).isSupported) {
            this.f = (ImageView) findViewById(R$id.iv_close);
            this.p1 = (ImageView) findViewById(R$id.pg2GoBack);
            this.x = (TextView) findViewById(R$id.tv_next);
            this.y = (TextView) findViewById(R$id.tv_title);
            this.z4 = (RelativeLayout) findViewById(R$id.rl_tip);
            this.z = (TextView) findViewById(R$id.pg2tv_title);
            this.q = (ImageView) findViewById(R$id.pg2iv_close);
            this.f.setOnClickListener(new e(this));
            this.z.setText(PubUtils.getString(BaseApplication.b(), R$string.ipc_playback_ai_feed_back_share_this_video));
            TextView textView = this.y;
            BaseApplication b2 = BaseApplication.b();
            int i = R$string.tag_this_video;
            textView.setText(PubUtils.getString(b2, i));
            this.q.setOnClickListener(new g(this));
            this.p4 = (LinearLayout) findViewById(R$id.lnBottomAction);
            this.x.setText(PubUtils.getString(BaseApplication.b(), R$string.ipc_playback_ai_feed_back_next));
            this.y.setText(PubUtils.getString(BaseApplication.b(), i));
            this.x.setEnabled(false);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R$id.rlPageSecond);
            this.A4 = relativeLayout;
            relativeLayout.setVisibility(8);
            RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R$id.rlPageOne);
            this.B4 = relativeLayout2;
            relativeLayout2.setVisibility(0);
            this.p0 = (TextView) findViewById(R$id.tvNo);
            this.a1 = (TextView) findViewById(R$id.tvYes);
            this.p0.setOnClickListener(new d(this));
            this.a1.setOnClickListener(new c(this));
            this.x.setOnClickListener(new a(this));
            this.p1.setOnClickListener(new f(this));
            this.a2 = (RecyclerView) findViewById(R$id.rv_feedback);
            final GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public int getSpanSize(int position) {
                    Object[] objArr = {new Integer(position)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls = Integer.TYPE;
                    PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5595, new Class[]{cls}, cls);
                    if (proxy.isSupported) {
                        return ((Integer) proxy.result).intValue();
                    }
                    if (LDSAiFeedbackBottomDialog.this.a2.getAdapter().getItemViewType(position) == 99) {
                        return mLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            this.a2.setLayoutManager(mLayoutManager);
            EventTagAdapter eventTagAdapter = new EventTagAdapter(getContext(), this.p3);
            this.p2 = eventTagAdapter;
            this.a2.setAdapter(eventTagAdapter);
            this.p2.setOnItemClickListener(new b(this));
            getWindow().setGravity(80);
            getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: i */
    public /* synthetic */ void j(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5594, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        e eVar = this.C4;
        if (eVar != null) {
            eVar.onClose();
        }
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: k */
    public /* synthetic */ void l(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5593, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        e eVar = this.C4;
        if (eVar != null) {
            eVar.onClose();
        }
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: m */
    public /* synthetic */ void n(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5592, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        e eVar = this.C4;
        if (eVar != null) {
            eVar.a();
        }
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: o */
    public /* synthetic */ void p(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5591, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        e eVar = this.C4;
        if (eVar != null) {
            eVar.b();
        }
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: q */
    public /* synthetic */ void r(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5590, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        try {
            x();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: s */
    public /* synthetic */ void t(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5589, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        y();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: u */
    public /* synthetic */ void v(View view, int position) {
        if (!PatchProxy.proxy(new Object[]{view, new Integer(position)}, this, changeQuickRedirect, false, 5588, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
            Log.d("ViewHolder", "eventTagAdapter.setOnItemClickListener--> position = " + position);
            if (TextUtils.isEmpty(g())) {
                this.x.setEnabled(false);
                this.x.setBackground(BaseApplication.b().getResources().getDrawable(R$drawable.shape_c12_disable));
                return;
            }
            this.x.setEnabled(true);
            this.x.setBackground(BaseApplication.b().getResources().getDrawable(R$drawable.shape_c12_enable));
        }
    }

    public void z(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 5584, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                this.p3.clear();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    this.p3.add((EventTagBean) new Gson().fromJson(item.toString(), EventTagBean.class));
                }
                this.p2.notifyDataSetChanged();
                show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public String g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5585, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String customEventCode = "";
        List<EventTagBean> eventTagBeans = this.p2.d();
        for (int i = 0; i < eventTagBeans.size(); i++) {
            if (eventTagBeans.get(i).isSelect()) {
                String eventCode = eventTagBeans.get(i).getEventCode();
                if (TextUtils.isEmpty(customEventCode)) {
                    customEventCode = eventCode;
                } else {
                    customEventCode = customEventCode + "," + eventCode;
                }
            }
        }
        return customEventCode;
    }

    private void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5586, new Class[0], Void.TYPE).isSupported) {
            TranslateAnimation pageOneLeave = new TranslateAnimation(1, 0.0f, 1, -1.0f, 1, 0.0f, 1, 0.0f);
            TranslateAnimation pageTwoCome = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            pageOneLeave.setDuration(300);
            pageTwoCome.setDuration(300);
            this.B4.setVisibility(0);
            this.A4.setVisibility(0);
            pageOneLeave.setFillAfter(false);
            pageTwoCome.setFillAfter(false);
            pageOneLeave.setAnimationListener(new a());
            pageTwoCome.setAnimationListener(new b());
            this.B4.startAnimation(pageOneLeave);
            this.A4.startAnimation(pageTwoCome);
        }
    }

    public class a implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5596, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                LDSAiFeedbackBottomDialog.this.B4.setVisibility(8);
                LDSAiFeedbackBottomDialog.this.A4.setVisibility(0);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public class b implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5597, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                LDSAiFeedbackBottomDialog.this.B4.setVisibility(8);
                LDSAiFeedbackBottomDialog.this.A4.setVisibility(0);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5587, new Class[0], Void.TYPE).isSupported) {
            this.B4.setVisibility(0);
            this.A4.setVisibility(0);
            TranslateAnimation pageTwoCome = new TranslateAnimation(1, -1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            TranslateAnimation pageTwoCome2 = new TranslateAnimation(1, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
            pageTwoCome.setDuration(300);
            pageTwoCome2.setDuration(300);
            pageTwoCome.setFillAfter(false);
            pageTwoCome2.setFillAfter(false);
            pageTwoCome.setAnimationListener(new c());
            pageTwoCome2.setAnimationListener(new d());
            this.B4.startAnimation(pageTwoCome);
            this.A4.startAnimation(pageTwoCome2);
        }
    }

    public class c implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5598, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                LDSAiFeedbackBottomDialog.this.B4.setVisibility(0);
                LDSAiFeedbackBottomDialog.this.A4.setVisibility(8);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public class d implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5599, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                LDSAiFeedbackBottomDialog.this.B4.setVisibility(0);
                LDSAiFeedbackBottomDialog.this.A4.setVisibility(8);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
}
