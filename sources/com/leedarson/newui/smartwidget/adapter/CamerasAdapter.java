package com.leedarson.newui.smartwidget.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$id;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.SecurityCamEntity;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.cloud_play_back.view.o0;
import com.leedarson.newui.cloud_play_back.view.p0;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.smartwidget.widgets.SecurityPlaybackDropItemView;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.HashMap;
import java.util.List;

public class CamerasAdapter extends BaseQuickAdapter<SecurityCamEntity, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<Integer, BaseViewHolder> N4 = new HashMap<>();
    private boolean O4;
    /* access modifiers changed from: private */
    public e P4;

    public interface e {
        void c(String str);

        void d(int i);

        void e(int i);

        void f(int i, boolean z);

        void g(int i);

        void h();
    }

    public /* bridge */ /* synthetic */ void d(@NonNull BaseViewHolder baseViewHolder, Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 4769, clsArr, Void.TYPE).isSupported) {
            B(baseViewHolder, (SecurityCamEntity) obj);
        }
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 4770, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            q((BaseViewHolder) viewHolder, i);
        }
    }

    public CamerasAdapter(int layoutResId, @Nullable List<SecurityCamEntity> data, boolean cloudPlayback) {
        super(layoutResId, data);
        this.O4 = cloudPlayback;
    }

    public void q(@NonNull BaseViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 4759, new Class[]{BaseViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onBindViewHolder(holder, position);
        }
    }

    public void B(@NonNull BaseViewHolder baseViewHolder, SecurityCamEntity securityCamEntity) {
        Class[] clsArr = {BaseViewHolder.class, SecurityCamEntity.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, securityCamEntity}, this, changeQuickRedirect, false, 4760, clsArr, Void.TYPE).isSupported) {
            if (securityCamEntity != null && securityCamEntity.getDeviceBean() != null) {
                int position = getItemPosition(securityCamEntity);
                if (!this.N4.containsKey(Integer.valueOf(position))) {
                    this.N4.put(Integer.valueOf(position), baseViewHolder);
                }
                baseViewHolder.setText(R$id.tv_camera_name, (CharSequence) securityCamEntity.getDeviceBean().name);
                baseViewHolder.findView(R$id.layout_title).setOnClickListener(new a(this, position));
                LiveCameraView cameraView = (LiveCameraView) baseViewHolder.findView(R$id.cameraview);
                cameraView.m0(securityCamEntity.getDeviceBean().getAspectRatio(), 1.7777778f);
                F(baseViewHolder, securityCamEntity, position);
                cameraView.T(securityCamEntity.getItemType(), securityCamEntity.getDeviceBean().id, securityCamEntity.getDeviceBean().p2pId, securityCamEntity.getDeviceBean().account, securityCamEntity.getDeviceBean().password, securityCamEntity.getDeviceBean().props.isDTLS, securityCamEntity.getDeviceBean().share.booleanValue(), securityCamEntity.getDeviceBean().modelId);
                I(position, cameraView);
                if (securityCamEntity.getDeviceBean().isCriticalBattery()) {
                    y(cameraView);
                } else if (securityCamEntity.getDeviceBean().props.TurnOnOff) {
                    z(cameraView);
                } else {
                    A(cameraView);
                }
                cameraView.J(securityCamEntity.isMute());
                if (!this.O4) {
                    View tv_current = baseViewHolder.getView(R$id.tv_current);
                    View rlPlaybackTagItem = baseViewHolder.getView(R$id.rlPlaybackTagItem);
                    ViewGroup parentView = (ViewGroup) tv_current.getParent();
                    if (parentView != null) {
                        parentView.removeView(tv_current);
                    }
                    ViewGroup parentView2 = (ViewGroup) rlPlaybackTagItem.getParent();
                    if (parentView2 != null) {
                        parentView2.removeView(rlPlaybackTagItem);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: G */
    public /* synthetic */ void H(int position, View view) {
        Object[] objArr = {new Integer(position), view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4771, new Class[]{Integer.TYPE, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        e eVar = this.P4;
        if (eVar != null) {
            eVar.e(position);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void F(BaseViewHolder baseViewHolder, SecurityCamEntity securityCamEntity, int i) {
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, securityCamEntity, new Integer(i)}, this, changeQuickRedirect, false, 4761, new Class[]{BaseViewHolder.class, SecurityCamEntity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            SecurityCamEntity securityCamEntity2 = securityCamEntity;
            int position = i;
            SecurityPlaybackDropItemView playbackDropItemView = (SecurityPlaybackDropItemView) baseViewHolder.findView(R$id.security_drop_view);
            IJKPlayBackPlayerView playbackView = (IJKPlayBackPlayerView) baseViewHolder.findView(R$id.playbackView);
            playbackView.setSpkNSLevel(securityCamEntity2.getDeviceBean().getSpkNSLevel());
            playbackView.M1(securityCamEntity2.getDeviceBean().getAspectRatio(), 1.7777778f);
            playbackView.setVisibility(4);
            playbackView.l5 = 1;
            playbackView.f0();
            if (securityCamEntity2.getEventList() != null && securityCamEntity2.getEventList().size() > 0) {
                playbackDropItemView.setEventList(securityCamEntity2.getEventList());
            }
            playbackView.r(securityCamEntity2.isMute());
            playbackView.set_mScreenChangeHandler(new a(position));
            playbackView.setMuteChangeHandler(new b(position));
            playbackDropItemView.set_eventHandler(new c(position, playbackView, (LiveCameraView) baseViewHolder.findView(R$id.cameraview), baseViewHolder, securityCamEntity2));
            playbackDropItemView.h(playbackView);
        }
    }

    public class a implements p0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        a(int i) {
            this.c = i;
        }

        public boolean f0() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4772, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            try {
                if (CamerasAdapter.this.getContext().getResources().getConfiguration().orientation != 2 && CamerasAdapter.this.getContext().getResources().getConfiguration().orientation == 1) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void x0() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4773, new Class[0], Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.g(this.c);
                }
            }
        }

        public void M() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4774, new Class[0], Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.g(this.c);
                }
            }
        }
    }

    public class b implements o0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        b(int i) {
            this.a = i;
        }

        public void a(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4775, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.f(this.a, mute);
                }
            }
        }
    }

    public class c implements SecurityPlaybackDropItemView.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ IJKPlayBackPlayerView b;
        final /* synthetic */ LiveCameraView c;
        final /* synthetic */ BaseViewHolder d;
        final /* synthetic */ SecurityCamEntity e;

        c(int i, IJKPlayBackPlayerView iJKPlayBackPlayerView, LiveCameraView liveCameraView, BaseViewHolder baseViewHolder, SecurityCamEntity securityCamEntity) {
            this.a = i;
            this.b = iJKPlayBackPlayerView;
            this.c = liveCameraView;
            this.d = baseViewHolder;
            this.e = securityCamEntity;
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4776, new Class[0], Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.d(this.a);
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4777, new Class[0], Void.TYPE).isSupported) {
                ((SecurityCamEntity) CamerasAdapter.this.getData().get(this.a)).setLive(true);
                this.b.setVisibility(8);
                this.c.setVisibility(0);
                this.d.setText(R$id.tv_current, (CharSequence) PubUtils.getString(CamerasAdapter.this.getContext(), R$string.security_camera_live));
                this.b.w();
                this.c.setVisibility(0);
                this.b.setVisibility(4);
                if (this.e.getDeviceBean().isCriticalBattery()) {
                    CamerasAdapter.this.y(this.c);
                } else if (this.e.getDeviceBean().props.TurnOnOff) {
                    this.c.l0();
                } else {
                    CamerasAdapter.this.A(this.c);
                }
                this.b.T1();
            }
        }

        public void e(EventListItemBean event, int i) {
            Object[] objArr = {event, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4778, new Class[]{EventListItemBean.class, Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("CamGroup_event_play");
                ((SecurityCamEntity) CamerasAdapter.this.getData().get(this.a)).setLive(false);
                this.b.setVisibility(0);
                this.c.setVisibility(8);
                if (event != null) {
                    String time = com.leedarson.utils.e.d(BaseApplication.b(), event.eventTime, "hh:mm");
                    this.d.setText(R$id.tv_current, (CharSequence) time);
                    this.b.setUpPlayerTitle(time);
                }
                this.b.C();
                this.c.f0();
                if (((SecurityCamEntity) CamerasAdapter.this.getData().get(this.a)).getItemType() == 2) {
                    this.c.L();
                }
                this.c.setVisibility(4);
                this.b.setVisibility(0);
            }
        }

        public void c(PlayBackSourceBean sourceBean, EventListItemBean bean) {
            Class[] clsArr = {PlayBackSourceBean.class, EventListItemBean.class};
            if (!PatchProxy.proxy(new Object[]{sourceBean, bean}, this, changeQuickRedirect, false, 4779, clsArr, Void.TYPE).isSupported) {
                this.b.f();
                this.b.B4 = bean.getEventUuid();
                this.b.A4 = bean.getDeviceId();
                this.b.F1(sourceBean);
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4780, new Class[0], Void.TYPE).isSupported) {
                this.b.u(PubUtils.getString(CamerasAdapter.this.getContext(), R$string.can_not_play));
            }
        }
    }

    public void z(LiveCameraView cameraView) {
        if (!PatchProxy.proxy(new Object[]{cameraView}, this, changeQuickRedirect, false, 4762, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            cameraView.L();
        }
    }

    public void A(LiveCameraView cameraView) {
        if (!PatchProxy.proxy(new Object[]{cameraView}, this, changeQuickRedirect, false, 4763, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            cameraView.M();
        }
    }

    public void y(LiveCameraView cameraView) {
        if (!PatchProxy.proxy(new Object[]{cameraView}, this, changeQuickRedirect, false, 4764, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            cameraView.K();
        }
    }

    public class d implements LiveCameraView.q {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        d(int i) {
            this.a = i;
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4781, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.f(this.a, mute);
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4782, new Class[0], Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.g(this.a);
                }
            }
        }

        public void d() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4783, new Class[0], Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.h();
                }
            }
        }

        public void c(String deviceId) {
            if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4784, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (CamerasAdapter.this.P4 != null) {
                    CamerasAdapter.this.P4.c(deviceId);
                }
            }
        }
    }

    public void I(int position, LiveCameraView cameraView) {
        Object[] objArr = {new Integer(position), cameraView};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4765, new Class[]{Integer.TYPE, LiveCameraView.class}, Void.TYPE).isSupported) {
            cameraView.setOnSecurityCamClickListener(new d(position));
        }
    }

    public BaseViewHolder E(int position) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 4766, new Class[]{Integer.TYPE}, BaseViewHolder.class);
        return proxy.isSupported ? (BaseViewHolder) proxy.result : this.N4.get(Integer.valueOf(position));
    }

    public LiveCameraView C(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4767, new Class[]{Integer.TYPE}, LiveCameraView.class);
        if (proxy.isSupported) {
            return (LiveCameraView) proxy.result;
        }
        BaseViewHolder viewHolder = E(position);
        if (viewHolder == null) {
            return null;
        }
        return (LiveCameraView) viewHolder.getViewOrNull(R$id.cameraview);
    }

    public IJKPlayBackPlayerView D(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4768, new Class[]{Integer.TYPE}, IJKPlayBackPlayerView.class);
        if (proxy.isSupported) {
            return (IJKPlayBackPlayerView) proxy.result;
        }
        BaseViewHolder viewHolder = E(position);
        if (viewHolder == null) {
            return null;
        }
        return (IJKPlayBackPlayerView) viewHolder.getViewOrNull(R$id.playbackView);
    }

    public void setAdapterViewClickListener(e adapterViewClickListener) {
        this.P4 = adapterViewClickListener;
    }
}
