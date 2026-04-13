package demo;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.google.chip.chiptool.util.MatterSpUtil;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.listener.d;
import com.leedarson.serviceimpl.matter.R$id;
import com.leedarson.serviceimpl.matter.R$layout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.reactivex.functions.e;
import java.util.Arrays;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProvisionActivity.kt */
public final class ProvisionActivity extends BaseActivity {
    @Nullable
    private CHIPDeviceInfo a1;
    public String p0;
    @NotNull
    private final d p1 = new c(this);

    @NotNull
    public final String c0() {
        String str = this.p0;
        if (str != null) {
            return str;
        }
        k.t("qrcode");
        throw null;
    }

    public final void j0(@NotNull String str) {
        k.e(str, "<set-?>");
        this.p0 = str;
    }

    @Nullable
    public final CHIPDeviceInfo a0() {
        return this.a1;
    }

    /* access modifiers changed from: protected */
    public int O() {
        return R$layout.layout_provision;
    }

    /* access modifiers changed from: protected */
    public void init() {
        Button $this$init_u24lambda_u2d1 = (Button) findViewById(R$id.btn_provision);
        $this$init_u24lambda_u2d1.setEnabled(a0() != null);
        $this$init_u24lambda_u2d1.setOnClickListener(new h(this));
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void e0(ProvisionActivity this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        ((LinearLayout) this$0.findViewById(R$id.ll_provision)).setVisibility(0);
        ((TextView) this$0.findViewById(R$id.tv_progress)).setText("");
        this$0.Z();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: protected */
    public void R() {
        j0(String.valueOf(getIntent().getStringExtra("qrcode")));
        this.a1 = i.a.z(c0());
        int i = R$id.tv_info;
        TextView textView = (TextView) findViewById(i);
        CHIPDeviceInfo cHIPDeviceInfo = this.a1;
        textView.setText(cHIPDeviceInfo == null ? null : cHIPDeviceInfo.getFormat());
        ((TextView) findViewById(i)).append("\nDCL校验:");
    }

    public final void Z() {
        String[] pers = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        io.reactivex.disposables.b Y = new com.tbruyelle.rxpermissions2.b(this).l((String[]) Arrays.copyOf(pers, pers.length)).Y(new a(new x(), this), new b());
    }

    /* compiled from: ProvisionActivity.kt */
    public static final class a implements e<com.tbruyelle.rxpermissions2.a> {
        final /* synthetic */ x c;
        final /* synthetic */ ProvisionActivity d;

        a(x $num, ProvisionActivity $receiver) {
            this.c = $num;
            this.d = $receiver;
        }

        /* renamed from: a */
        public void accept(@Nullable com.tbruyelle.rxpermissions2.a permission) {
            k.c(permission);
            if (permission.b) {
                x xVar = this.c;
                int i = xVar.element + 1;
                xVar.element = i;
                if (i == 2) {
                    String ssid = ((EditText) this.d.findViewById(R$id.et_ssid)).getText().toString();
                    String pwd = ((EditText) this.d.findViewById(R$id.et_pwd)).getText().toString();
                    ((Button) this.d.findViewById(R$id.btn_provision)).isEnabled();
                    if (!TextUtils.isEmpty(ssid)) {
                        i.a.b(this.d.c0(), this.d.b0(), ssid, pwd);
                    } else {
                        i.c(i.a, this.d.c0(), this.d.b0(), (String) null, (String) null, 12, (Object) null);
                    }
                }
            }
        }
    }

    /* compiled from: ProvisionActivity.kt */
    public static final class b implements e<Throwable> {
        b() {
        }

        /* renamed from: a */
        public void accept(@Nullable Throwable t) {
        }
    }

    /* compiled from: ProvisionActivity.kt */
    public static final class c implements d {
        final /* synthetic */ ProvisionActivity a;

        c(ProvisionActivity $receiver) {
            this.a = $receiver;
        }

        public void f(@NotNull CHIPDeviceInfo info) {
            k.e(info, "info");
            ProvisionActivity provisionActivity = this.a;
            provisionActivity.runOnUiThread(new i(provisionActivity, info));
        }

        /* access modifiers changed from: private */
        public static final void i(ProvisionActivity this$0, CHIPDeviceInfo $info) {
            k.e(this$0, "this$0");
            k.e($info, "$info");
            ((TextView) this$0.findViewById(R$id.tv_info)).setText($info.getFormat());
        }

        public void g(@NotNull String str) {
            k.e(str, "str");
            this.a.X(k.l(str, "\n"));
        }

        public void a(long nodeId, @Nullable String mac) {
            MatterDeviceBean bean = new MatterDeviceBean();
            i iVar = i.a;
            bean.setFabricId(iVar.n());
            MatterSpUtil matterSpUtil = MatterSpUtil.INSTANCE;
            Context applicationContext = this.a.getApplicationContext();
            k.d(applicationContext, "applicationContext");
            bean.setHouseId(matterSpUtil.getHouseId(applicationContext));
            bean.setNodeId(nodeId);
            com.leedarson.serviceimpl.sql.a.a.d(bean);
            ProvisionActivity provisionActivity = this.a;
            provisionActivity.X("onSuccess nodeId:" + nodeId + "\nfabricId:" + iVar.m());
        }

        public void c(int code, @Nullable String err, @Nullable Exception exception) {
            ProvisionActivity provisionActivity = this.a;
            provisionActivity.X(err + " ,errCode:" + code + 10);
        }

        public void d(long nodeId) {
        }

        public void b() {
            ((Button) this.a.findViewById(R$id.btn_provision)).setEnabled(true);
        }

        public void e(int stateCode, @NotNull String message, @NotNull String desc) {
            k.e(message, "message");
            k.e(desc, "desc");
        }
    }

    @NotNull
    public final d b0() {
        return this.p1;
    }

    public final void X(@NotNull String string) {
        k.e(string, TypedValues.Custom.S_STRING);
        runOnUiThread(new g(this, string));
    }

    /* access modifiers changed from: private */
    public static final void Y(ProvisionActivity this$0, String $string) {
        k.e(this$0, "this$0");
        k.e($string, "$string");
        ((TextView) this$0.findViewById(R$id.tv_progress)).append($string);
    }
}
