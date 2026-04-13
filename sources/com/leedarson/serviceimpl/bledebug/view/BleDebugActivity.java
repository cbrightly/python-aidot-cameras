package com.leedarson.serviceimpl.bledebug.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.l;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BleDebugActivity extends BaseActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public JSONArray A4 = new JSONArray();
    CheckBox a1;
    CheckBox a2;
    TextView p0;
    CheckBox p1;
    CheckBox p2;
    CheckBox p3;
    EditText p4;
    com.leedarson.serviceimpl.bledebug.bean.a z4;

    static /* synthetic */ void X(BleDebugActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6860, new Class[]{BleDebugActivity.class}, Void.TYPE).isSupported) {
            x0.c0();
        }
    }

    static /* synthetic */ void Z(BleDebugActivity x0, String[] x1) {
        Class[] clsArr = {BleDebugActivity.class, String[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6861, clsArr, Void.TYPE).isSupported) {
            x0.b0(x1);
        }
    }

    public int O() {
        return R$layout.activity_ble_debug;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6855, new Class[0], Void.TYPE).isSupported) {
            this.z4 = new com.leedarson.serviceimpl.bledebug.bean.a();
            initView();
            a0();
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6856, new Class[0], Void.TYPE).isSupported) {
            this.p4 = (EditText) findViewById(R$id.et_conn_interval);
            this.p0 = (TextView) findViewById(R$id.tv_macs);
            this.a1 = (CheckBox) findViewById(R$id.cb_main_thread_busy);
            this.p1 = (CheckBox) findViewById(R$id.cb_conn_serial);
            this.a2 = (CheckBox) findViewById(R$id.cb_conn_with_scan);
            this.p2 = (CheckBox) findViewById(R$id.cb_op_background);
            this.p3 = (CheckBox) findViewById(R$id.cb_close_when_connected);
            findViewById(R$id.iv_back).setOnClickListener(new a());
            findViewById(R$id.btn_submit).setOnClickListener(new b());
            findViewById(R$id.tv_setting).setOnClickListener(new c());
            this.p1.setOnCheckedChangeListener(new d());
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 6862, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            BleDebugActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 6863, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            BleDebugActivity.X(BleDebugActivity.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 6864, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            AlertDialog.Builder builder = new AlertDialog.Builder(BleDebugActivity.this);
            EditText editText = new EditText(BleDebugActivity.this);
            builder.setTitle("修改mac");
            JSONArray macList = BleDebugActivity.this.A4;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < macList.length()) {
                try {
                    if (i != macList.length() - 1) {
                        sb.append(macList.get(i).toString());
                        sb.append(",");
                    } else {
                        sb.append(macList.get(i).toString());
                    }
                    i++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            editText.setText(sb.toString());
            builder.setView(editText);
            builder.setNegativeButton("取消", (DialogInterface.OnClickListener) null);
            builder.setPositiveButton("确定", new a(editText));
            builder.show();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public class a implements DialogInterface.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ EditText c;

            a(EditText editText) {
                this.c = editText;
            }

            @SensorsDataInstrumented
            public void onClick(DialogInterface dialogInterface, int i) {
                Object[] objArr = {dialogInterface, new Integer(i)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6865, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                    return;
                }
                int i2 = i;
                DialogInterface dialogInterface2 = dialogInterface;
                try {
                    BleDebugActivity.Z(BleDebugActivity.this, this.c.getText().toString().toUpperCase().trim().split(","));
                } catch (Exception E) {
                    BleDebugActivity bleDebugActivity = BleDebugActivity.this;
                    Toast.makeText(bleDebugActivity, "err:" + E, 0).show();
                }
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
            }
        }
    }

    public class d implements CompoundButton.OnCheckedChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        @SensorsDataInstrumented
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int i = 0;
            Object[] objArr = {compoundButton, new Byte(isChecked ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6866, new Class[]{CompoundButton.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
                return;
            }
            CompoundButton compoundButton2 = compoundButton;
            View findViewById = BleDebugActivity.this.findViewById(R$id.ll_conn_interval);
            if (!isChecked) {
                i = 8;
            }
            findViewById.setVisibility(i);
            SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
        }
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6857, new Class[0], Void.TYPE).isSupported) {
            ArrayList<String> arrayList = this.z4.macList;
            if (arrayList == null || arrayList.size() == 0) {
                Toast.makeText(this, "设备为空", 0).show();
                return;
            }
            if (this.p1.isChecked()) {
                this.z4.env |= 2;
                this.z4.connectInterval = Integer.parseInt(this.p4.getText().toString());
            }
            if (this.a1.isChecked()) {
                com.leedarson.serviceimpl.bledebug.bean.a aVar = this.z4;
                aVar.env |= 1;
                aVar.connectInterval = 0;
            }
            if (this.a2.isChecked()) {
                this.z4.env |= 4;
            }
            this.z4.opInBackground = this.p2.isChecked();
            this.z4.closeWhenConnected = this.p3.isChecked();
            com.leedarson.serviceimpl.bledebug.d.a();
            Intent intent = new Intent(this, GattTestActivity.class);
            intent.putExtra("config", this.z4);
            startActivity(intent);
        }
    }

    public void R() {
    }

    private void a0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6858, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONArray optJSONArray = new JSONObject(l.d(new File(getCacheDir() + File.separator + "ble.json"))).optJSONArray("macList");
                this.A4 = optJSONArray;
                this.p0.setText(optJSONArray.toString());
                this.z4.setMacList(this.A4);
            } catch (Exception e) {
                e.printStackTrace();
                this.p0.setText(this.A4.toString());
            }
        }
    }

    private void b0(String[] arr) {
        if (!PatchProxy.proxy(new Object[]{arr}, this, changeQuickRedirect, false, 6859, new Class[]{String[].class}, Void.TYPE).isSupported) {
            try {
                this.A4 = new JSONArray();
                for (String item : arr) {
                    this.A4.put((Object) item);
                }
                JSONObject obj = new JSONObject();
                obj.put("macList", (Object) this.A4);
                l.h(getCacheDir(), "ble.json", obj.toString(), false);
                this.p0.setText(this.A4.toString());
                this.z4.setMacList(this.A4);
            } catch (Exception e) {
                e.printStackTrace();
                this.p0.setText(this.A4.toString());
            }
        }
    }
}
