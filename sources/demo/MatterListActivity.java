package demo;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.f;
import com.google.chip.chiptool.util.MatterSpUtil;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.leedarson.serviceimpl.matter.R$id;
import com.leedarson.serviceimpl.matter.R$layout;
import com.leedarson.serviceinterface.CameraService;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterListActivity.kt */
public final class MatterListActivity extends BaseActivity {
    @Nullable
    private DevListAdapter a1;
    @NotNull
    private List<MatterDeviceBean> p0 = new ArrayList();

    @NotNull
    public final List<MatterDeviceBean> Z() {
        return this.p0;
    }

    /* access modifiers changed from: protected */
    public int O() {
        return R$layout.layout_matter_list;
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void b0(MatterListActivity this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.finish();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: protected */
    public void init() {
        ((ImageView) findViewById(R$id.iv_back)).setOnClickListener(new e(this));
        ((TextView) findViewById(R$id.tv_add_device)).setOnClickListener(new d(this));
        ((Button) findViewById(R$id.btnParse)).setOnClickListener(new c(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        this.a1 = new DevListAdapter(R$layout.item_mt_dev, this.p0);
        int i = R$id.recycler_view;
        ((RecyclerView) findViewById(i)).setLayoutManager(layoutManager);
        ((RecyclerView) findViewById(i)).setItemAnimator(new DefaultItemAnimator());
        ((RecyclerView) findViewById(i)).setAdapter(this.a1);
        DevListAdapter devListAdapter = this.a1;
        if (devListAdapter != null) {
            devListAdapter.setOnItemLongClickListener(new a(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void c0(MatterListActivity this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        CameraService cameraService = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
        if (cameraService != null) {
            cameraService.scanQrcodeForNative(this$0);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void e0(MatterListActivity this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, MtCodeTestAct.class));
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* compiled from: MatterListActivity.kt */
    public static final class a implements f {
        final /* synthetic */ MatterListActivity a;

        a(MatterListActivity $receiver) {
            this.a = $receiver;
        }

        public boolean a(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view, int position) {
            k.e(adapter, "adapter");
            k.e(view, "view");
            this.a.k0(this.a.Z().get(position));
            return true;
        }
    }

    /* access modifiers changed from: private */
    public final void k0(MatterDeviceBean bean) {
        new AlertDialog.Builder(this).setMessage("delete?").setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).setPositiveButton("Confirm", new b(bean, this)).show();
    }

    /* compiled from: MatterListActivity.kt */
    public static final class b implements DialogInterface.OnClickListener {
        final /* synthetic */ MatterDeviceBean c;
        final /* synthetic */ MatterListActivity d;

        b(MatterDeviceBean $bean, MatterListActivity $receiver) {
            this.c = $bean;
            this.d = $receiver;
        }

        @SensorsDataInstrumented
        public void onClick(@Nullable DialogInterface dialogInterface, int i) {
            int i2 = i;
            DialogInterface dialogInterface2 = dialogInterface;
            com.leedarson.serviceimpl.sql.a.a.a(this.c);
            this.d.a0();
            SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        a0();
    }

    /* access modifiers changed from: protected */
    public void R() {
    }

    /* access modifiers changed from: private */
    public final void a0() {
        this.p0.clear();
        com.leedarson.serviceimpl.sql.a aVar = com.leedarson.serviceimpl.sql.a.a;
        MatterSpUtil matterSpUtil = MatterSpUtil.INSTANCE;
        Application application = getApplication();
        k.d(application, "application");
        List nodeInfoList = aVar.b(matterSpUtil.getHouseId(application));
        k.c(nodeInfoList);
        this.p0.addAll(nodeInfoList);
        DevListAdapter devListAdapter = this.a1;
        if (devListAdapter != null) {
            devListAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == -1) {
            Bundle bundle = data == null ? null : data.getExtras();
            String code = bundle == null ? null : bundle.getString("result_string");
            com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, k.l("扫码结果:", code), (String) null, 2, (Object) null);
            Intent intent = new Intent(this, ProvisionActivity.class);
            intent.putExtra("qrcode", code);
            startActivity(intent);
        }
    }
}
