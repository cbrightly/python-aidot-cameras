package com.leedarson.serviceimpl.bledebug.view;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class GattTestActivity extends BaseActivity implements a, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public TextView A4;
    /* access modifiers changed from: private */
    public TextView B4;
    /* access modifiers changed from: private */
    public Handler C4;
    private Runnable D4 = new d();
    private RecyclerView a1;
    /* access modifiers changed from: private */
    public ConnectAdapter a2;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.bean.a p0;
    private ArrayList<ConnectBean> p1 = new ArrayList<>();
    private com.leedarson.serviceimpl.bledebug.c p2;
    /* access modifiers changed from: private */
    public Button p3;
    /* access modifiers changed from: private */
    public TextView p4;
    /* access modifiers changed from: private */
    public TextView z4;

    public int O() {
        return R$layout.activity_gatt_test;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6870, new Class[0], Void.TYPE).isSupported) {
            getWindow().addFlags(128);
            findViewById(R$id.btn_start_script).setOnClickListener(this);
            this.p4 = (TextView) findViewById(R$id.tv_test_desc);
            this.z4 = (TextView) findViewById(R$id.tv_total);
            this.A4 = (TextView) findViewById(R$id.tv_success_total);
            this.B4 = (TextView) findViewById(R$id.tv_success_rate);
            Button button = (Button) findViewById(R$id.btn_connect);
            this.p3 = button;
            button.setOnClickListener(this);
            RecyclerView recyclerView = (RecyclerView) findViewById(R$id.recyclerView);
            this.a1 = recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ConnectAdapter connectAdapter = new ConnectAdapter(R$layout.item_gatt_conn, this.p1);
            this.a2 = connectAdapter;
            this.a1.setAdapter(connectAdapter);
            com.leedarson.serviceimpl.bledebug.c cVar = new com.leedarson.serviceimpl.bledebug.c(this, this, this.p1);
            this.p2 = cVar;
            cVar.x(this, this.p0);
            g0();
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6871, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bledebug.bean.a aVar = (com.leedarson.serviceimpl.bledebug.bean.a) getIntent().getSerializableExtra("config");
            this.p0 = aVar;
            Iterator<String> it = aVar.macList.iterator();
            while (it.hasNext()) {
                this.p1.add(new ConnectBean(it.next()));
            }
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6878, new Class[0], Void.TYPE).isSupported) {
                GattTestActivity.this.a2.notifyDataSetChanged();
            }
        }
    }

    public void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6872, new Class[0], Void.TYPE).isSupported) {
            runOnUiThread(new a());
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;

        b(int i, int i2, int i3) {
            this.c = i;
            this.d = i2;
            this.f = i3;
        }

        public void run() {
            String str;
            String str2;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6879, new Class[0], Void.TYPE).isSupported) {
                TextView Z = GattTestActivity.this.p4;
                if (this.c == GattTestActivity.this.p0.testCount) {
                    str = "自动化结束";
                } else {
                    str = "正在进行第" + this.c + "轮自动化...";
                }
                Z.setText(str);
                GattTestActivity.this.z4.setText("总个数:" + this.d);
                GattTestActivity.this.A4.setText("成功个数:" + this.f);
                TextView c0 = GattTestActivity.this.B4;
                int i = this.d;
                if (i == 0) {
                    str2 = "0";
                } else {
                    str2 = String.format(Locale.US, "成功率:%.2f", new Object[]{Float.valueOf((((float) this.f) * 1.0f) / ((float) i))});
                }
                c0.setText(str2);
            }
        }
    }

    public void C(int successCount, int total, int index) {
        Object[] objArr = {new Integer(successCount), new Integer(total), new Integer(index)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6873, clsArr, Void.TYPE).isSupported) {
            runOnUiThread(new b(index, total, successCount));
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6880, new Class[0], Void.TYPE).isSupported) {
                GattTestActivity.this.p3.setVisibility(0);
            }
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6874, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bledebug.a.a("发现所有设备");
            runOnUiThread(new c());
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 6875, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (v.getId() == R$id.btn_start_script) {
            this.p3.setVisibility(8);
            this.p2.y();
        } else if (v.getId() == R$id.btn_connect) {
            this.p2.v();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6876, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            this.p2.z();
            Handler handler = this.C4;
            if (handler != null) {
                handler.removeCallbacks(this.D4);
            }
        }
    }

    private void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6877, new Class[0], Void.TYPE).isSupported) {
            if (this.p0.containEnv(1)) {
                com.leedarson.serviceimpl.bledebug.a.a("主线程忙碌开启");
                Handler handler = new Handler(Looper.getMainLooper());
                this.C4 = handler;
                handler.post(this.D4);
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6881, new Class[0], Void.TYPE).isSupported) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GattTestActivity.this.C4.postDelayed(this, 1000);
            }
        }
    }
}
