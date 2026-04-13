package com.leedarson.newui.multiview;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.listener.e;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MultiViewEditFragment.kt */
public final class MultiViewEditFragment extends BaseFragment {
    @NotNull
    public static final a a1 = new a((DefaultConstructorMarker) null);
    public static ChangeQuickRedirect changeQuickRedirect;
    private MultiViewEditAdapter a2;
    @NotNull
    private final g p1 = i.b(new c(this));

    /* compiled from: MultiViewEditFragment.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<MultiViewViewModel> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewEditFragment this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(MultiViewEditFragment multiViewEditFragment) {
            super(0);
            this.this$0 = multiViewEditFragment;
        }

        @NotNull
        public final MultiViewViewModel invoke() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4217, new Class[0], MultiViewViewModel.class);
            if (proxy.isSupported) {
                return (MultiViewViewModel) proxy.result;
            }
            FragmentActivity requireActivity = this.this$0.requireActivity();
            k.d(requireActivity, "requireActivity()");
            return (MultiViewViewModel) new ViewModelProvider(requireActivity).get(MultiViewViewModel.class);
        }
    }

    private final MultiViewViewModel I1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4210, new Class[0], MultiViewViewModel.class);
        return proxy.isSupported ? (MultiViewViewModel) proxy.result : (MultiViewViewModel) this.p1.getValue();
    }

    public int r1() {
        return R$layout.fragment_multi_view_edit;
    }

    public void t1(@Nullable View view) {
        LDSTextView lDSTextView;
        LDSTextView lDSTextView2;
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4211, new Class[]{View.class}, Void.TYPE).isSupported) {
            MultiViewEditAdapter multiViewEditAdapter = new MultiViewEditAdapter();
            MultiViewEditAdapter $this$initBaseView_u24lambda_u2d0 = multiViewEditAdapter;
            $this$initBaseView_u24lambda_u2d0.setList(I1().g());
            $this$initBaseView_u24lambda_u2d0.i().s(true);
            $this$initBaseView_u24lambda_u2d0.i().t(true);
            $this$initBaseView_u24lambda_u2d0.i().v(R$id.img_drag);
            $this$initBaseView_u24lambda_u2d0.i().c().setDragMoveFlags(3);
            $this$initBaseView_u24lambda_u2d0.i().setOnItemDragListener(new b());
            x xVar = x.a;
            this.a2 = multiViewEditAdapter;
            View view2 = getView();
            RecyclerView $this$initBaseView_u24lambda_u2d1 = (RecyclerView) (view2 == null ? null : view2.findViewById(R$id.recyclerView_edit));
            $this$initBaseView_u24lambda_u2d1.setLayoutManager(new LinearLayoutManager(getActivity()));
            MultiViewEditAdapter multiViewEditAdapter2 = this.a2;
            if (multiViewEditAdapter2 != null) {
                $this$initBaseView_u24lambda_u2d1.setAdapter(multiViewEditAdapter2);
                if (!(view == null || (lDSTextView2 = (LDSTextView) view.findViewById(R$id.tv_cancel)) == null)) {
                    lDSTextView2.setOnClickListener(new b(this));
                }
                if (view != null && (lDSTextView = (LDSTextView) view.findViewById(R$id.tv_save)) != null) {
                    lDSTextView.setOnClickListener(new c(this));
                    return;
                }
                return;
            }
            k.t("mAdapter");
            throw null;
        }
    }

    /* compiled from: MultiViewEditFragment.kt */
    public static final class b implements e {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onItemDragStart(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
            View $this$onItemDragStart_u24lambda_u2d0;
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 4215, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                if (viewHolder != null && ($this$onItemDragStart_u24lambda_u2d0 = viewHolder.itemView) != null) {
                    $this$onItemDragStart_u24lambda_u2d0.setScaleX(1.03f);
                    $this$onItemDragStart_u24lambda_u2d0.setScaleY(1.03f);
                }
            }
        }

        public void onItemDragMoving(@Nullable RecyclerView.ViewHolder source, int from, @Nullable RecyclerView.ViewHolder target, int to) {
        }

        public void onItemDragEnd(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
            View $this$onItemDragEnd_u24lambda_u2d1;
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 4216, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                if (viewHolder != null && ($this$onItemDragEnd_u24lambda_u2d1 = viewHolder.itemView) != null) {
                    $this$onItemDragEnd_u24lambda_u2d1.setScaleX(1.0f);
                    $this$onItemDragEnd_u24lambda_u2d1.setScaleY(1.0f);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void J1(MultiViewEditFragment this$0, View view) {
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4212, new Class[]{MultiViewEditFragment.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.I1().j(1);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void K1(MultiViewEditFragment this$0, View view) {
        int i = 0;
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4213, new Class[]{MultiViewEditFragment.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        MultiViewViewModel I1 = this$0.I1();
        MultiViewEditAdapter multiViewEditAdapter = this$0.a2;
        if (multiViewEditAdapter != null) {
            I1.i(multiViewEditAdapter.getData());
            MultiViewEditAdapter multiViewEditAdapter2 = this$0.a2;
            if (multiViewEditAdapter2 != null) {
                int size = multiViewEditAdapter2.getData().size() - 1;
                if (size >= 0) {
                    do {
                        int index = i;
                        i++;
                        BaseApplication b2 = BaseApplication.b();
                        String str = SharePreferenceUtils.KEY_MULTI_VIEW_DEVICE_INDEX;
                        MultiViewEditAdapter multiViewEditAdapter3 = this$0.a2;
                        if (multiViewEditAdapter3 != null) {
                            SharePreferenceUtils.setPrefInt(b2, k.l(str, ((IpcDeviceComparableBean) multiViewEditAdapter3.getData().get(index)).getIpcDeviceBean().id), index + 1000);
                        } else {
                            k.t("mAdapter");
                            SensorsDataAutoTrackHelper.trackViewOnClick(view);
                            throw null;
                        }
                    } while (i <= size);
                }
                this$0.I1().j(1);
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

    /* compiled from: MultiViewEditFragment.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final MultiViewEditFragment a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4214, new Class[0], MultiViewEditFragment.class);
            if (proxy.isSupported) {
                return (MultiViewEditFragment) proxy.result;
            }
            Bundle args = new Bundle();
            MultiViewEditFragment fragment = new MultiViewEditFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
