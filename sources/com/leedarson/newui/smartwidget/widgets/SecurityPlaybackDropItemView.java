package com.leedarson.newui.smartwidget.widgets;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.SecurityEventEntity;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.z;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseWrapBean;
import com.leedarson.newui.repos.o;
import com.leedarson.newui.smartwidget.adapter.SecurityEventAdapter;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;

public class SecurityPlaybackDropItemView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    List<EventListItemBean> A4;
    private EventListItemBean B4;
    private Dialog C4;
    private LDSTextView D4;
    private LDSTextView E4;
    private LDSTextView F4;
    private LDSTextView G4;
    private Toast H4;
    private io.reactivex.disposables.b a1;
    private IJKPlayBackPlayerView a2;
    /* access modifiers changed from: private */
    public RecyclerView c;
    private Context d;
    private View f;
    private o p0;
    b p1;
    private io.reactivex.disposables.b p2;
    private io.reactivex.processors.b<EventListItemBean> p3;
    SecurityEventAdapter p4;
    private int q;
    private int x;
    CenterLinearLayoutManger y;
    private int z;
    ArrayList<SecurityEventEntity> z4;

    public interface b {
        void a();

        void b();

        void c(PlayBackSourceBean playBackSourceBean, EventListItemBean eventListItemBean);

        void d();

        void e(EventListItemBean eventListItemBean, int i);
    }

    static /* synthetic */ void b(SecurityPlaybackDropItemView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4820, new Class[]{SecurityPlaybackDropItemView.class}, Void.TYPE).isSupported) {
            x0.j();
        }
    }

    static /* synthetic */ int c(SecurityPlaybackDropItemView x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4821, new Class[]{SecurityPlaybackDropItemView.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.i();
    }

    static /* synthetic */ void d(SecurityPlaybackDropItemView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4822, new Class[]{SecurityPlaybackDropItemView.class}, Void.TYPE).isSupported) {
            x0.l();
        }
    }

    static /* synthetic */ void e(SecurityPlaybackDropItemView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4823, new Class[]{SecurityPlaybackDropItemView.class}, Void.TYPE).isSupported) {
            x0.H();
        }
    }

    static /* synthetic */ void g(SecurityPlaybackDropItemView x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 4824, new Class[]{SecurityPlaybackDropItemView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.K(x1);
        }
    }

    public SecurityPlaybackDropItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SecurityPlaybackDropItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityPlaybackDropItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SecurityPlaybackDropItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.q = 0;
        this.x = 0;
        this.z = 8;
        this.p0 = new o();
        this.p3 = io.reactivex.processors.b.Y();
        this.z4 = new ArrayList<>();
        this.C4 = null;
        this.d = context;
        p();
    }

    public void set_eventHandler(b _eventHandler) {
        this.p1 = _eventHandler;
    }

    private void k(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 4793, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public void h(IJKPlayBackPlayerView playerView) {
        if (!PatchProxy.proxy(new Object[]{playerView}, this, changeQuickRedirect, false, 4794, new Class[]{IJKPlayBackPlayerView.class}, Void.TYPE).isSupported) {
            this.a2 = playerView;
            SecurityEventAdapter securityEventAdapter = this.p4;
            if (securityEventAdapter != null) {
                securityEventAdapter.notifyDataSetChanged();
            }
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4796, new Class[0], Void.TYPE).isSupported) {
            LayoutInflater.from(this.d).inflate(R$layout.security_playback_drop_item_layout, this, true);
            this.c = (RecyclerView) findViewById(R$id.recyclerView);
            n();
            View findViewById = findViewById(R$id.live_layout);
            this.f = findViewById;
            findViewById.setOnClickListener(new d(this));
            this.x = getResources().getDisplayMetrics().widthPixels;
            l();
            k(this.a1);
            this.p2 = this.p3.e(600, TimeUnit.MILLISECONDS).c(l.c()).I(new c(this), h.c);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: y */
    public /* synthetic */ void z(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4817, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.y.smoothScrollToPosition(this.c, new RecyclerView.State(), this.z4.size() - 1);
        K(this.z4.size() - 1);
        this.p4.notifyDataSetChanged();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: A */
    public /* synthetic */ void B(EventListItemBean eventListItemBean) {
        if (!PatchProxy.proxy(new Object[]{eventListItemBean}, this, changeQuickRedirect, false, 4816, new Class[]{EventListItemBean.class}, Void.TYPE).isSupported) {
            a(eventListItemBean);
        }
    }

    static /* synthetic */ void C(Throwable throwable) {
    }

    private void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4797, new Class[0], Void.TYPE).isSupported) {
            this.z4.add(new SecurityEventEntity(1, (EventListItemBean) null));
            this.z4.add(new SecurityEventEntity(3, (EventListItemBean) null));
            this.c.post(new e(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: w */
    public /* synthetic */ void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4815, new Class[0], Void.TYPE).isSupported) {
            this.p4 = new SecurityEventAdapter(this.z4, this.c.getWidth());
            CenterLinearLayoutManger centerLinearLayoutManger = new CenterLinearLayoutManger(getContext());
            this.y = centerLinearLayoutManger;
            centerLinearLayoutManger.setOrientation(0);
            this.c.setLayoutManager(this.y);
            this.c.setAdapter(this.p4);
            List<EventListItemBean> list = this.A4;
            if (list != null) {
                G(list);
            }
            this.c.scrollToPosition(this.p4.getItemCount() - 1);
            o();
            this.p4.setAdapterViewClickListener(new a());
        }
    }

    public class a implements SecurityEventAdapter.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4825, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (position != 0) {
                    SecurityPlaybackDropItemView securityPlaybackDropItemView = SecurityPlaybackDropItemView.this;
                    securityPlaybackDropItemView.y.smoothScrollToPosition(securityPlaybackDropItemView.c, new RecyclerView.State(), position);
                }
                SecurityPlaybackDropItemView.g(SecurityPlaybackDropItemView.this, position);
            }
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4798, new Class[0], Void.TYPE).isSupported) {
            this.c.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 4826, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("[hyf]" + this, "Scroll state=" + newState + " offsetx=" + recyclerView.computeHorizontalScrollOffset());
                        if (newState == 0) {
                            SecurityPlaybackDropItemView.b(SecurityPlaybackDropItemView.this);
                        }
                    }
                }

                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    Object[] objArr = {recyclerView, new Integer(dx), new Integer(dy)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls = Integer.TYPE;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4827, new Class[]{RecyclerView.class, cls, cls}, Void.TYPE).isSupported) {
                        super.onScrolled(recyclerView, dx, dy);
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (dx != 0 && (layoutManager instanceof LinearLayoutManager)) {
                            if (SecurityPlaybackDropItemView.c(SecurityPlaybackDropItemView.this) == SecurityPlaybackDropItemView.this.z4.size() - 1) {
                                SecurityPlaybackDropItemView.d(SecurityPlaybackDropItemView.this);
                            } else {
                                SecurityPlaybackDropItemView.e(SecurityPlaybackDropItemView.this);
                            }
                        }
                    }
                }
            });
        }
    }

    private void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4799, new Class[0], Void.TYPE).isSupported) {
            int childCount = this.c.getChildCount();
            int i = 0;
            while (i < childCount) {
                View view = this.c.getChildAt(i);
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                int pXLeft = locations[0];
                int pXRight = view.getWidth() + pXLeft;
                int halfScreenX = this.x / 2;
                RecyclerView recyclerView = this.c;
                int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                if (pXLeft > halfScreenX || halfScreenX > pXRight) {
                    i++;
                } else {
                    int scrollToPosition = i + firstItem;
                    if (((SecurityEventEntity) this.p4.getData().get(scrollToPosition)).getItemType() == 0) {
                        this.y.smoothScrollToPosition(this.c, new RecyclerView.State(), scrollToPosition);
                    }
                    K(i + firstItem);
                    this.p4.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    private int i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4800, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int childCount = this.c.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.c.getChildAt(i);
            int[] locations = new int[2];
            view.getLocationOnScreen(locations);
            int pXLeft = locations[0];
            int pXRight = view.getWidth() + pXLeft;
            int halfScreenX = this.x / 2;
            RecyclerView recyclerView = this.c;
            int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
            if (pXLeft <= halfScreenX && halfScreenX <= pXRight) {
                return i + firstItem;
            }
        }
        return childCount;
    }

    private void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4801, new Class[0], Void.TYPE).isSupported) {
            if (this.f.getVisibility() != 8) {
                this.f.setVisibility(8);
            }
        }
    }

    private void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4802, new Class[0], Void.TYPE).isSupported) {
            if (this.f.getVisibility() != 0) {
                this.f.setVisibility(0);
            }
        }
    }

    private void K(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 4803, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (position <= 0 || this.p4.z() != position) {
                Log.d("[hyf]" + this, "updateCurrentIndex: " + position);
                if (position > 0) {
                    this.p4.B(position);
                }
                if (this.p1 != null) {
                    switch (this.z4.get(position).getItemType()) {
                        case 0:
                            this.p1.e(this.z4.get(position).getEventListItemBean(), position);
                            this.p3.onNext(this.z4.get(position).getEventListItemBean());
                            return;
                        case 1:
                            this.p1.b();
                            return;
                        case 3:
                            this.p1.a();
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    private void a(EventListItemBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 4804, new Class[]{EventListItemBean.class}, Void.TYPE).isSupported) {
            k(this.a1);
            Log.d("TAG", "_exchangePlayerUrl: " + eventBean.getHasVideo() + "==" + eventBean.expiredFlag);
            if (eventBean.getHasVideo() == 1 && eventBean.expiredFlag == 0) {
                this.a2.P1();
                EventListItemBean eventListItemBean = this.B4;
                if (eventListItemBean == null || !eventListItemBean.getEventUuid().equals(eventBean.getEventUuid()) || !this.a2.h()) {
                    this.B4 = eventBean;
                    this.a2.w();
                    this.a2.C();
                    this.a2.v();
                    this.a2.setAiMarkList(eventBean.getAiMarksList());
                    IJKPlayBackPlayerView iJKPlayBackPlayerView = this.a2;
                    iJKPlayBackPlayerView.setVideoCover(eventBean.picUrl + "");
                    if (eventBean.checkLocalVideoSourceAvailable()) {
                        this.B4 = eventBean;
                        F(eventBean, eventBean.playerReource);
                        return;
                    }
                    this.a1 = this.p0.f(eventBean).c(l.c()).I(new g(this, eventBean), new f(this, eventBean));
                    return;
                }
                return;
            }
            this.a2.F1((PlayBackSourceBean) null);
            this.a2.T1();
            this.a2.g0(eventBean.picUrl);
            this.a2.E1(eventBean.getTagName());
            J(eventBean.getTagTip());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public /* synthetic */ void r(EventListItemBean eventBean, LDSBasePageBean eventUrlResponseWrapBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean, eventUrlResponseWrapBean}, this, changeQuickRedirect, false, 4814, new Class[]{EventListItemBean.class, LDSBasePageBean.class}, Void.TYPE).isSupported) {
            T t = eventUrlResponseWrapBean.data;
            if (t == null || ((EventUrlResponseWrapBean) t).eventUrlList == null || ((EventUrlResponseWrapBean) t).eventUrlList.size() <= 0 || ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0).videoUrlList == null || ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0).videoUrlList.size() <= 0) {
                this.a2.F1((PlayBackSourceBean) null);
                this.a2.T1();
                this.a2.C();
                return;
            }
            this.p0.d(eventBean.getDeviceId(), eventBean.getEventUuid(), 1);
            if (this.p1 != null) {
                F(eventBean, ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: s */
    public /* synthetic */ void t(EventListItemBean eventBean, Throwable throwable) {
        Class[] clsArr = {EventListItemBean.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{eventBean, throwable}, this, changeQuickRedirect, false, 4813, clsArr, Void.TYPE).isSupported) {
            if (throwable instanceof ApiException) {
                ApiException exception = (ApiException) throwable;
                if (exception.getCode() == z.b) {
                    I(exception.getMsg());
                } else {
                    this.a2.u(PubUtils.getString(getContext(), R$string.can_not_play));
                }
                this.a2.g0(eventBean.picUrl);
            }
            this.p1.d();
        }
    }

    private void F(EventListItemBean eventListItemBean, EventUrlResponseItemBean urlResponseItemBean) {
        if (!PatchProxy.proxy(new Object[]{eventListItemBean, urlResponseItemBean}, this, changeQuickRedirect, false, 4805, new Class[]{EventListItemBean.class, EventUrlResponseItemBean.class}, Void.TYPE).isSupported) {
            PlayBackSourceBean data = new PlayBackSourceBean();
            data.url = urlResponseItemBean.videoUrlList.get(0).url;
            eventListItemBean.playerReource = urlResponseItemBean;
            data.coverUrl = this.B4.picUrl;
            data.eventPlayUrls = urlResponseItemBean;
            this.a2.B4 = eventListItemBean.getEventUuid();
            this.a2.A4 = eventListItemBean.getDeviceId();
            this.p1.c(data, eventListItemBean);
        }
    }

    public void setEventList(List<EventListItemBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 4806, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.A4 = list;
            G(list);
        }
    }

    private void G(List<EventListItemBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 4807, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (this.p4 != null && list != null && list.size() != 0) {
                this.z4.clear();
                this.z4.add(new SecurityEventEntity(1, (EventListItemBean) null));
                for (EventListItemBean itemBean : list) {
                    this.z4.add(new SecurityEventEntity(0, itemBean));
                }
                this.z4.add(new SecurityEventEntity(3, (EventListItemBean) null));
                this.c.scrollToPosition(this.p4.getItemCount() - 1);
                this.p4.notifyDataSetChanged();
            }
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4808, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            this.C4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.C4.setCanceledOnTouchOutside(false);
            this.D4 = (LDSTextView) this.C4.findViewById(R$id.tip_title_tv);
            this.E4 = (LDSTextView) this.C4.findViewById(R$id.tip_content_tv);
            this.F4 = (LDSTextView) this.C4.findViewById(R$id.left_btn_tv);
            this.G4 = (LDSTextView) this.C4.findViewById(R$id.right_btn_tv);
            this.F4.setOnClickListener(new b(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: u */
    public /* synthetic */ void v(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4812, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.C4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void I(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4809, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.C4 == null) {
                m();
            }
            if (!TextUtils.isEmpty(msg)) {
                this.D4.setText(msg);
                this.D4.setVisibility(0);
            }
            this.F4.setText(PubUtils.getString(getContext(), R$string.next_time));
            this.G4.setText(PubUtils.getString(getContext(), R$string.learn_more));
            this.E4.setText(PubUtils.getString(getContext(), R$string.record_error_tip_content));
            this.G4.setOnClickListener(new a(this));
            if (!this.C4.isShowing()) {
                this.C4.show();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: D */
    public /* synthetic */ void E(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4811, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.C4.dismiss();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
            new JSONObject().put("deviceId", (Object) this.B4.getDeviceId());
            c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void J(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 4810, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    View toastRoot = LayoutInflater.from(getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    LDSTextView tv2 = (LDSTextView) toastRoot.findViewById(R$id.toast_notice);
                    Toast toast = this.H4;
                    if (toast != null) {
                        toast.cancel();
                    }
                    Toast toast2 = new Toast(getContext());
                    this.H4 = toast2;
                    toast2.setDuration(0);
                    this.H4.setGravity(17, 0, 0);
                    this.H4.setView(toastRoot);
                    tv2.setText(str);
                    this.H4.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
