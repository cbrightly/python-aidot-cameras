package com.leedarson.newui.multiview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.newui.multiview.MultiViewAdapter;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.view.recyclerview.LDSVisibleItemRecyclerView;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import java.util.Set;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.coroutines.jvm.internal.f;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.flow.t;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: MultiViewFragment.kt */
public final class MultiViewFragment extends BaseFragment {
    @NotNull
    public static final a a1 = new a((DefaultConstructorMarker) null);
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public MultiViewAdapter a2;
    @NotNull
    private final g p1 = i.b(new e(this));
    private boolean p2 = true;

    public static final /* synthetic */ MultiViewViewModel J1(MultiViewFragment $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 4231, new Class[]{MultiViewFragment.class}, MultiViewViewModel.class);
        return proxy.isSupported ? (MultiViewViewModel) proxy.result : $this.L1();
    }

    public static final /* synthetic */ void K1(MultiViewFragment $this, String exceptDevice) {
        Class[] clsArr = {MultiViewFragment.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, exceptDevice}, (Object) null, changeQuickRedirect, true, 4232, clsArr, Void.TYPE).isSupported) {
            $this.U1(exceptDevice);
        }
    }

    /* compiled from: MultiViewFragment.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<MultiViewViewModel> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewFragment this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(MultiViewFragment multiViewFragment) {
            super(0);
            this.this$0 = multiViewFragment;
        }

        @NotNull
        public final MultiViewViewModel invoke() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4249, new Class[0], MultiViewViewModel.class);
            if (proxy.isSupported) {
                return (MultiViewViewModel) proxy.result;
            }
            FragmentActivity requireActivity = this.this$0.requireActivity();
            k.d(requireActivity, "requireActivity()");
            return (MultiViewViewModel) new ViewModelProvider(requireActivity).get(MultiViewViewModel.class);
        }
    }

    private final MultiViewViewModel L1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4219, new Class[0], MultiViewViewModel.class);
        return proxy.isSupported ? (MultiViewViewModel) proxy.result : (MultiViewViewModel) this.p1.getValue();
    }

    public int r1() {
        return R$layout.fragment_multi_view;
    }

    public void t1(@Nullable View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4220, new Class[]{View.class}, Void.TYPE).isSupported) {
            MultiViewAdapter multiViewAdapter = new MultiViewAdapter();
            MultiViewAdapter $this$initBaseView_u24lambda_u2d0 = multiViewAdapter;
            $this$initBaseView_u24lambda_u2d0.setList(L1().g());
            $this$initBaseView_u24lambda_u2d0.I(new b(this, $this$initBaseView_u24lambda_u2d0));
            x xVar = x.a;
            this.a2 = multiViewAdapter;
            View view2 = getView();
            View view3 = null;
            LDSVisibleItemRecyclerView $this$initBaseView_u24lambda_u2d1 = (LDSVisibleItemRecyclerView) (view2 == null ? null : view2.findViewById(R$id.recyclerView_list));
            $this$initBaseView_u24lambda_u2d1.setLayoutManager(new LinearLayoutManager(getActivity()));
            MultiViewAdapter multiViewAdapter2 = this.a2;
            if (multiViewAdapter2 != null) {
                $this$initBaseView_u24lambda_u2d1.setAdapter(multiViewAdapter2);
                $this$initBaseView_u24lambda_u2d1.setVisibleItemsListener(new c(this));
                View view4 = getView();
                ((ImageView) (view4 == null ? null : view4.findViewById(R$id.iv_edit))).setOnClickListener(new f(this));
                View view5 = getView();
                ((ImageView) (view5 == null ? null : view5.findViewById(R$id.iv_back))).setOnClickListener(new e(this));
                if (L1().g().size() <= 1) {
                    View view6 = getView();
                    if (view6 != null) {
                        view3 = view6.findViewById(R$id.tv_one_devices);
                    }
                    ((LDSTextView) view3).setVisibility(0);
                }
                O1();
                return;
            }
            k.t("mAdapter");
            throw null;
        }
    }

    /* compiled from: MultiViewFragment.kt */
    public static final class b implements MultiViewAdapter.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewFragment a;
        final /* synthetic */ MultiViewAdapter b;

        b(MultiViewFragment $receiver, MultiViewAdapter $receiver2) {
            this.a = $receiver;
            this.b = $receiver2;
        }

        public void a(@NotNull IpcDeviceComparableBean ipcDeviceComparableBean) {
            if (!PatchProxy.proxy(new Object[]{ipcDeviceComparableBean}, this, changeQuickRedirect, false, 4234, new Class[]{IpcDeviceComparableBean.class}, Void.TYPE).isSupported) {
                IpcDeviceComparableBean item = ipcDeviceComparableBean;
                k.e(item, "item");
                MultiViewAdapter I1 = this.a.a2;
                if (I1 != null) {
                    I1.F();
                    MultiViewFragment multiViewFragment = this.a;
                    String str = item.getIpcDeviceBean().id;
                    k.d(str, "item.ipcDeviceBean.id");
                    MultiViewFragment.K1(multiViewFragment, str);
                    try {
                        IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                        if (ipcService != null) {
                            JSONObject dataObj = new JSONObject();
                            dataObj.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.LIVE_PAGE);
                            JSONObject paramObj = new JSONObject();
                            paramObj.put("deviceId", (Object) item.getIpcDeviceBean().id);
                            paramObj.put("needFinish", true);
                            paramObj.put("cloudPlayback", item.getIpcDeviceBean().cloudPlayback);
                            if (k.a(SharePreferenceUtils.getPrefString(this.b.getContext(), "owner", ""), SharePreferenceUtils.getPrefString(this.b.getContext(), "userId", ""))) {
                                if (!item.getIpcDeviceBean().share.booleanValue()) {
                                    paramObj.put("isOwner", true);
                                    dataObj.put("params", (Object) paramObj);
                                    ipcService.handleData(this.a.getActivity(), "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                                }
                            }
                            paramObj.put("isOwner", false);
                            dataObj.put("params", (Object) paramObj);
                            ipcService.handleData(this.a.getActivity(), "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    k.t("mAdapter");
                    throw null;
                }
            }
        }

        public void b(int resId) {
            Object[] objArr = {new Integer(resId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4235, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                this.a.F1(resId);
            }
        }
    }

    /* compiled from: MultiViewFragment.kt */
    public static final class c implements LDSVisibleItemRecyclerView.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewFragment a;

        c(MultiViewFragment $receiver) {
            this.a = $receiver;
        }

        public void a(@NotNull List<Integer> items) {
            if (!PatchProxy.proxy(new Object[]{items}, this, changeQuickRedirect, false, 4236, new Class[]{List.class}, Void.TYPE).isSupported) {
                k.e(items, FirebaseAnalytics.Param.ITEMS);
                MultiViewAdapter I1 = this.a.a2;
                if (I1 != null) {
                    I1.J(items);
                    MultiViewAdapter I12 = this.a.a2;
                    if (I12 != null) {
                        Set subList = y.v0(y.C0(q.h(I12.getData())), items);
                        MultiViewAdapter I13 = this.a.a2;
                        if (I13 != null) {
                            I13.L(y.C0(subList));
                        } else {
                            k.t("mAdapter");
                            throw null;
                        }
                    } else {
                        k.t("mAdapter");
                        throw null;
                    }
                } else {
                    k.t("mAdapter");
                    throw null;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void M1(MultiViewFragment this$0, View view) {
        Class[] clsArr = {MultiViewFragment.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4228, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        MultiViewAdapter multiViewAdapter = this$0.a2;
        if (multiViewAdapter != null) {
            multiViewAdapter.A();
            MultiViewAdapter multiViewAdapter2 = this$0.a2;
            if (multiViewAdapter2 != null) {
                multiViewAdapter2.K();
                this$0.L1().j(2);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            k.t("mAdapter");
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            throw null;
        }
        k.t("mAdapter");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
        throw null;
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void N1(MultiViewFragment this$0, View view) {
        Class[] clsArr = {MultiViewFragment.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4229, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.L1().c();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private final void O1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4221, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().p(this);
            z1 unused = j.d(LifecycleOwnerKt.getLifecycleScope(this), (kotlin.coroutines.g) null, (q0) null, new d(this, (kotlin.coroutines.d<? super d>) null), 3, (Object) null);
        }
    }

    @f(c = "com.leedarson.newui.multiview.MultiViewFragment$initObserver$1", f = "MultiViewFragment.kt", l = {140}, m = "invokeSuspend")
    /* compiled from: MultiViewFragment.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ MultiViewFragment this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(MultiViewFragment multiViewFragment, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = multiViewFragment;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4238, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new d(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4240, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4239, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @f(c = "com.leedarson.newui.multiview.MultiViewFragment$initObserver$1$1", f = "MultiViewFragment.kt", l = {141}, m = "invokeSuspend")
        /* compiled from: MultiViewFragment.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ MultiViewFragment this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(MultiViewFragment multiViewFragment, kotlin.coroutines.d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = multiViewFragment;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4242, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new a(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4244, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4243, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @f(c = "com.leedarson.newui.multiview.MultiViewFragment$initObserver$1$1$1", f = "MultiViewFragment.kt", l = {}, m = "invokeSuspend")
            /* renamed from: com.leedarson.newui.multiview.MultiViewFragment$d$a$a  reason: collision with other inner class name */
            /* compiled from: MultiViewFragment.kt */
            public static final class C0115a extends kotlin.coroutines.jvm.internal.l implements p<Boolean, kotlin.coroutines.d<? super x>, Object> {
                public static ChangeQuickRedirect changeQuickRedirect;
                int label;
                final /* synthetic */ MultiViewFragment this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0115a(MultiViewFragment multiViewFragment, kotlin.coroutines.d<? super C0115a> dVar) {
                    super(2, dVar);
                    this.this$0 = multiViewFragment;
                }

                @NotNull
                public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4246, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                    return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new C0115a(this.this$0, dVar);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    Class<Object> cls = Object.class;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4248, new Class[]{cls, cls}, Object.class);
                    return proxy.isSupported ? proxy.result : invoke(((Boolean) obj).booleanValue(), (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
                }

                @Nullable
                public final Object invoke(boolean z, @Nullable kotlin.coroutines.d<? super x> dVar) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(z ? (byte) 1 : 0), dVar}, this, changeQuickRedirect, false, 4247, new Class[]{Boolean.TYPE, kotlin.coroutines.d.class}, Object.class);
                    return proxy.isSupported ? proxy.result : ((C0115a) create(Boolean.valueOf(z), dVar)).invokeSuspend(x.a);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    String str;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4245, new Class[]{Object.class}, Object.class);
                    if (proxy.isSupported) {
                        return proxy.result;
                    }
                    kotlin.coroutines.intrinsics.c.d();
                    switch (this.label) {
                        case 0:
                            kotlin.p.b(obj);
                            MultiViewAdapter I1 = this.this$0.a2;
                            if (I1 != null) {
                                I1.F();
                                MultiViewFragment multiViewFragment = this.this$0;
                                if (MultiViewFragment.J1(multiViewFragment).e() == null) {
                                    str = "";
                                } else {
                                    IpcDeviceBean e = MultiViewFragment.J1(this.this$0).e();
                                    k.c(e);
                                    str = e.id;
                                }
                                k.d(str, "if (viewModel.currentDeviceBean == null){\"\"} else{ viewModel.currentDeviceBean!!.id}");
                                MultiViewFragment.K1(multiViewFragment, str);
                                FragmentActivity activity = this.this$0.getActivity();
                                if (activity != null) {
                                    activity.finish();
                                }
                                return x.a;
                            }
                            k.t("mAdapter");
                            throw null;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4241, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        t<Boolean> f = MultiViewFragment.J1(this.this$0).f();
                        C0115a aVar = new C0115a(this.this$0, (kotlin.coroutines.d<? super C0115a>) null);
                        this.label = 1;
                        if (kotlinx.coroutines.flow.e.i(f, aVar, this) != d) {
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        kotlin.p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return x.a;
            }
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4237, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    MultiViewFragment multiViewFragment = this.this$0;
                    Lifecycle.State state = Lifecycle.State.CREATED;
                    a aVar = new a(multiViewFragment, (kotlin.coroutines.d<? super a>) null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle((LifecycleOwner) multiViewFragment, state, (p<? super o0, ? super kotlin.coroutines.d<? super x>, ? extends Object>) aVar, (kotlin.coroutines.d<? super x>) this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    private final void U1(String exceptDevice) {
        if (!PatchProxy.proxy(new Object[]{exceptDevice}, this, changeQuickRedirect, false, 4222, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                MultiViewAdapter multiViewAdapter = this.a2;
                if (multiViewAdapter != null) {
                    multiViewAdapter.G(exceptDevice);
                } else {
                    k.t("mAdapter");
                    throw null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4223, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            if (!this.p2) {
                View view = getView();
                LDSVisibleItemRecyclerView it = (LDSVisibleItemRecyclerView) (view == null ? null : view.findViewById(R$id.recyclerView_list));
                if (it != null) {
                    it.onScrollStateChanged(0);
                }
            }
            this.p2 = false;
        }
    }

    public final void S1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4224, new Class[0], Void.TYPE).isSupported) {
            View view = getView();
            View view2 = null;
            LDSVisibleItemRecyclerView it = (LDSVisibleItemRecyclerView) (view == null ? null : view.findViewById(R$id.recyclerView_list));
            if (it != null) {
                MultiViewAdapter multiViewAdapter = this.a2;
                if (multiViewAdapter != null) {
                    multiViewAdapter.setList(L1().g());
                    View view3 = getView();
                    if (view3 != null) {
                        view2 = view3.findViewById(R$id.recyclerView_list);
                    }
                    ((LDSVisibleItemRecyclerView) view2).postDelayed(new d(it), 100);
                    return;
                }
                k.t("mAdapter");
                throw null;
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void T1(LDSVisibleItemRecyclerView $it) {
        if (!PatchProxy.proxy(new Object[]{$it}, (Object) null, changeQuickRedirect, true, 4230, new Class[]{LDSVisibleItemRecyclerView.class}, Void.TYPE).isSupported) {
            k.e($it, "$it");
            $it.onScrollStateChanged(0);
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4225, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            MultiViewAdapter multiViewAdapter = this.a2;
            if (multiViewAdapter != null) {
                multiViewAdapter.A();
                MultiViewAdapter multiViewAdapter2 = this.a2;
                if (multiViewAdapter2 != null) {
                    multiViewAdapter2.K();
                } else {
                    k.t("mAdapter");
                    throw null;
                }
            } else {
                k.t("mAdapter");
                throw null;
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4226, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            org.greenrobot.eventbus.c.c().r(this);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public final void onPartialUpdateEvent(@NotNull PartialUpdateEvent partialUpdateEvent) {
        Object obj;
        boolean z = true;
        Boolean bool = true;
        if (!PatchProxy.proxy(new Object[]{partialUpdateEvent}, this, changeQuickRedirect, false, 4227, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            PartialUpdateEvent event = partialUpdateEvent;
            k.e(event, NotificationCompat.CATEGORY_EVENT);
            try {
                JSONObject $this$onPartialUpdateEvent_u24lambda_u2d11 = new JSONObject(event.getData());
                String deviceId = $this$onPartialUpdateEvent_u24lambda_u2d11.getString("id");
                if (deviceId == null) {
                    obj = null;
                } else {
                    JSONObject it = $this$onPartialUpdateEvent_u24lambda_u2d11.optJSONObject("props");
                    if (it == null) {
                        it = $this$onPartialUpdateEvent_u24lambda_u2d11.optJSONObject("extensions");
                    }
                    if (it == null) {
                        obj = null;
                    } else {
                        Object opt = it.opt("TurnOnOff");
                        if (opt == null) {
                            obj = null;
                        } else {
                            Object $this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8 = opt;
                            if (!(k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, "1") ? true : k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, 1) ? true : k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, bool))) {
                                if (!(k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, "0") ? true : k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, 0))) {
                                    z = k.a($this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8, false);
                                }
                                if (z) {
                                    bool = false;
                                } else {
                                    bool = null;
                                }
                            }
                            if (bool != null) {
                                boolean $this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8_u24lambda_u2d7 = bool.booleanValue();
                                MultiViewAdapter multiViewAdapter = this.a2;
                                if (multiViewAdapter != null) {
                                    multiViewAdapter.M(deviceId, $this$onPartialUpdateEvent_u24lambda_u2d11_u24lambda_u2d10_u24lambda_u2d9_u24lambda_u2d8_u24lambda_u2d7);
                                } else {
                                    k.t("mAdapter");
                                    throw null;
                                }
                            }
                            obj = opt;
                        }
                    }
                }
                Object obj2 = obj;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: MultiViewFragment.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final MultiViewFragment a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4233, new Class[0], MultiViewFragment.class);
            if (proxy.isSupported) {
                return (MultiViewFragment) proxy.result;
            }
            Bundle args = new Bundle();
            MultiViewFragment fragment = new MultiViewFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
