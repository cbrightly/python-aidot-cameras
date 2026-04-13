package com.leedarson.newui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.event.FullScreenEvent;
import com.leedarson.event.KVSWebrtcConnectEvent;
import com.leedarson.event.TutkConnectEvent;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.newui.view.t;
import com.leedarson.newui.view.w;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.c;
import org.greenrobot.eventbus.l;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class EasyLiveFragment extends BaseFragment {
    public static ChangeQuickRedirect changeQuickRedirect;
    private float A4 = 0.0f;
    private LiveCameraView a1;
    private int a2 = 0;
    /* access modifiers changed from: private */
    public View p1;
    private String p2;
    private IpcDeviceBean p3;
    private String p4;
    private float z4 = 0.0f;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 1989, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            c.c().p(this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1990, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            c.c().r(this);
        }
    }

    public int r1() {
        return R$layout.fragment_easy_live;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1991, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.a1 = (LiveCameraView) view.findViewById(R$id.cameraview);
            this.p1 = view.findViewById(R$id.layout_loading);
            initData();
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1992, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            try {
                if (!v1()) {
                    getActivity().setRequestedOrientation(0);
                    getActivity().getWindow().setFlags(1024, 1024);
                }
                int i = this.a2;
                if (i == 1) {
                    J1();
                } else if (i == 2) {
                    K1();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1993, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            LiveCameraView liveCameraView = this.a1;
            if (liveCameraView != null) {
                liveCameraView.v0();
            }
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 1994, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                if (this.z4 > 0.0f && this.A4 > 0.0f) {
                    ViewGroup.LayoutParams camParams = this.a1.getLayoutParams();
                    camParams.width = (int) this.z4;
                    camParams.height = (int) this.A4;
                    this.a1.requestLayout();
                }
                this.a1.setVisibility(0);
                this.a1.z0(true);
                return;
            }
            this.a1.setVisibility(8);
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1995, new Class[0], Void.TYPE).isSupported) {
            try {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    JSONObject jsonObject = new JSONObject(bundle.getString("message"));
                    String callbackKey = jsonObject.getString("callbackKey");
                    if (jsonObject.has("deviceId")) {
                        this.p4 = jsonObject.getString("deviceId");
                        if (!TextUtils.isEmpty(callbackKey)) {
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("deviceId", (Object) this.p4);
                                jsonObject1.put("code", 200);
                                c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onFullScreen(FullScreenEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1996, new Class[]{FullScreenEvent.class}, Void.TYPE).isSupported) {
            if (getActivity() != null && event != null) {
                this.A4 = event.getHeight();
                this.z4 = event.getWidth();
                if (v1()) {
                    int i = getResources().getDisplayMetrics().widthPixels;
                    int i2 = getResources().getDisplayMetrics().heightPixels;
                    ViewGroup.LayoutParams camParams = this.a1.getLayoutParams();
                    camParams.width = (int) this.z4;
                    camParams.height = (int) this.A4;
                    this.a1.requestLayout();
                }
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onConnectTutk(TutkConnectEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1997, new Class[]{TutkConnectEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData())) {
                this.a2 = 1;
                this.p2 = event.getData();
                J1();
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("deviceId", (Object) this.p4);
                    c.c().l(new JsBridgeCallbackEvent(event.getCallback(), jsonObject1.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void J1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1998, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject data = new JSONObject(this.p2);
                String p2pId = data.getString("p2pId");
                String account = data.getString("account");
                String password = data.getString("password");
                String isDTLS = data.has("isDTLS") ? data.getString("isDTLS") : "0";
                String string = data.getString("deviceId");
                this.p4 = string;
                this.a1.T(1, string, p2pId, account, password, isDTLS, false, "");
                this.a1.t0();
                this.p1.setVisibility(0);
                this.a1.setOnCameraStateListener(new a());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class a implements w {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2001, new Class[0], Void.TYPE).isSupported) {
                EasyLiveFragment.this.p1.setVisibility(8);
            }
        }

        public void b() {
        }

        public void a(int code) {
        }

        public void c(t step) {
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onKVSWebrtcConnectEvent(KVSWebrtcConnectEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1999, new Class[]{KVSWebrtcConnectEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                this.a2 = 2;
                D1("onKVSWebrtcConnectEvent: ");
                this.p3 = IpcServiceImpl.o(event.getDeviceId());
                K1();
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("deviceId", (Object) this.p4);
                    c.c().l(new JsBridgeCallbackEvent(event.getCallback(), jsonObject1.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void K1() {
        /*
            r14 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2000(0x7d0, float:2.803E-42)
            r2 = r14
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r14
            com.leedarson.bean.IpcDeviceBean r2 = r1.p3
            if (r2 != 0) goto L_0x001c
            return
        L_0x001c:
            com.leedarson.newui.view.LiveCameraView r3 = r1.a1
            float r2 = r2.getAspectRatio()
            r4 = 1071877689(0x3fe38e39, float:1.7777778)
            r3.m0(r2, r4)
            com.leedarson.newui.view.LiveCameraView r5 = r1.a1
            r6 = 2
            com.leedarson.bean.IpcDeviceBean r2 = r1.p3
            java.lang.String r7 = r2.id
            r12 = 0
            java.lang.String r13 = r2.modelId
            java.lang.String r8 = ""
            java.lang.String r9 = ""
            java.lang.String r10 = ""
            java.lang.String r11 = ""
            r5.T(r6, r7, r8, r9, r10, r11, r12, r13)
            com.leedarson.newui.view.LiveCameraView r2 = r1.a1
            r2.t0()
            android.view.View r2 = r1.p1
            r2.setVisibility(r0)
            com.leedarson.newui.view.LiveCameraView r0 = r1.a1
            com.leedarson.newui.EasyLiveFragment$b r2 = new com.leedarson.newui.EasyLiveFragment$b
            r2.<init>()
            r0.setOnCameraStateListener(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.EasyLiveFragment.K1():void");
    }

    public class b implements w {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2002, new Class[0], Void.TYPE).isSupported) {
                EasyLiveFragment.this.p1.setVisibility(8);
            }
        }

        public void b() {
        }

        public void a(int code) {
        }

        public void c(t step) {
        }
    }
}
