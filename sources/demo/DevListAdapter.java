package demo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.co1;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.matter.R$id;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.ToastUtil;
import java.util.List;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DevListAdapter.kt */
public final class DevListAdapter extends BaseQuickAdapter<MatterDeviceBean, BaseViewHolder> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DevListAdapter(int layoutResId, @NotNull List<MatterDeviceBean> datas) {
        super(layoutResId, datas);
        k.e(datas, "datas");
    }

    /* access modifiers changed from: protected */
    /* renamed from: x */
    public void d(@NotNull BaseViewHolder holder, @NotNull MatterDeviceBean item) {
        k.e(holder, "holder");
        k.e(item, "item");
        holder.setText(R$id.tv_mt_id, (CharSequence) String.valueOf(item.getNodeId()));
        holder.setText(R$id.tv_mt_version, (CharSequence) item.getVendorId());
        ((CheckBox) holder.getView(R$id.toggle_btn)).setOnCheckedChangeListener(new a(this, item));
        ((TextView) holder.getView(R$id.tv_mt_name)).setOnClickListener(new b(this, item));
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void y(DevListAdapter this$0, MatterDeviceBean $item, CompoundButton buttonView, boolean isChecked) {
        k.e(this$0, "this$0");
        k.e($item, "$item");
        this$0.D(isChecked, $item);
        SensorsDataAutoTrackHelper.trackViewOnClick(buttonView);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void z(DevListAdapter this$0, MatterDeviceBean $item, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        k.e($item, "$item");
        this$0.C($item);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    @f(c = "demo.DevListAdapter$toggle$1", f = "DevListAdapter.kt", l = {38, 53}, m = "invokeSuspend")
    /* compiled from: DevListAdapter.kt */
    public static final class b extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ long $deviceId;
        final /* synthetic */ MatterDeviceBean $item;
        final /* synthetic */ boolean $on;
        int label;
        final /* synthetic */ DevListAdapter this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(boolean z, long j, MatterDeviceBean matterDeviceBean, DevListAdapter devListAdapter, d<? super b> dVar) {
            super(2, dVar);
            this.$on = z;
            this.$deviceId = j;
            this.$item = matterDeviceBean;
            this.this$0 = devListAdapter;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new b(this.$on, this.$deviceId, this.$item, this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    if (!this.$on) {
                        com.leedarson.serviceimpl.ctrl.k i = i.a.i();
                        long j = this.$deviceId;
                        C0238b bVar = new C0238b(this.this$0, this.$item);
                        this.label = 2;
                        if (i.p(j, bVar, this) != d) {
                            break;
                        } else {
                            return d;
                        }
                    } else {
                        com.leedarson.serviceimpl.ctrl.k i2 = i.a.i();
                        long j2 = this.$deviceId;
                        a aVar = new a(this.$item, this.this$0);
                        this.label = 1;
                        if (i2.q(j2, aVar, this) != d) {
                            break;
                        } else {
                            return d;
                        }
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                case 2:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }

        /* compiled from: DevListAdapter.kt */
        public static final class a implements ChipClusters.DefaultClusterCallback {
            final /* synthetic */ MatterDeviceBean a;
            final /* synthetic */ DevListAdapter b;

            a(MatterDeviceBean $item, DevListAdapter $receiver) {
                this.a = $item;
                this.b = $receiver;
            }

            public void onSuccess() {
                this.a.setOnoff(1);
                ToastUtil.showShort(this.b.getContext(), "指令下发成功");
            }

            public void onError(@Nullable Exception p0) {
                ToastUtil.showShort(this.b.getContext(), k.l("指令失败:", p0 == null ? null : p0.getMessage()));
            }
        }

        /* renamed from: demo.DevListAdapter$b$b  reason: collision with other inner class name */
        /* compiled from: DevListAdapter.kt */
        public static final class C0238b implements ChipClusters.DefaultClusterCallback {
            final /* synthetic */ DevListAdapter a;
            final /* synthetic */ MatterDeviceBean b;

            C0238b(DevListAdapter $receiver, MatterDeviceBean $item) {
                this.a = $receiver;
                this.b = $item;
            }

            public void onSuccess() {
                ToastUtil.showShort(this.a.getContext(), "指令下发成功");
                this.b.setOnoff(0);
            }

            public void onError(@Nullable Exception p0) {
                ToastUtil.showShort(this.a.getContext(), k.l("指令失败:", p0 == null ? null : p0.getMessage()));
            }
        }
    }

    public final void D(boolean on, @NotNull MatterDeviceBean item) {
        k.e(item, "item");
        z1 unused = j.d(s1.c, (g) null, (q0) null, new b(on, item.getNodeId(), item, this, (d<? super b>) null), 3, (Object) null);
    }

    @f(c = "demo.DevListAdapter$readVendor$1", f = "DevListAdapter.kt", l = {73}, m = "invokeSuspend")
    /* compiled from: DevListAdapter.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ MatterDeviceBean $item;
        int label;
        final /* synthetic */ DevListAdapter this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(MatterDeviceBean matterDeviceBean, DevListAdapter devListAdapter, d<? super a> dVar) {
            super(2, dVar);
            this.$item = matterDeviceBean;
            this.this$0 = devListAdapter;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new a(this.$item, this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    com.leedarson.serviceimpl.ctrl.k i = i.a.i();
                    long nodeId = this.$item.getNodeId();
                    C0237a aVar = new C0237a(this.$item, this.this$0);
                    this.label = 1;
                    if (i.t(nodeId, aVar, this) != d) {
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

        /* renamed from: demo.DevListAdapter$a$a  reason: collision with other inner class name */
        /* compiled from: DevListAdapter.kt */
        public static final class C0237a implements ChipClusters.BasicCluster.VendorIDAttributeCallback {
            final /* synthetic */ MatterDeviceBean a;
            final /* synthetic */ DevListAdapter b;

            public /* synthetic */ void onSubscriptionEstablished() {
                co1.a(this);
            }

            C0237a(MatterDeviceBean $item, DevListAdapter $receiver) {
                this.a = $item;
                this.b = $receiver;
            }

            public void onSuccess(@Nullable Integer p0) {
                this.a.setVendorId(String.valueOf(p0));
                this.b.notifyDataSetChanged();
            }

            public void onError(@Nullable Exception p0) {
                ToastUtil.showShort(this.b.getContext(), String.valueOf(p0));
            }
        }
    }

    public final void C(@NotNull MatterDeviceBean item) {
        k.e(item, "item");
        z1 unused = j.d(s1.c, (g) null, (q0) null, new a(item, this, (d<? super a>) null), 3, (Object) null);
    }
}
