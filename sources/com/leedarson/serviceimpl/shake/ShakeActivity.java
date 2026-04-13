package com.leedarson.serviceimpl.shake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.secret.JNIUtil;
import com.leedarson.serviceimpl.bledebug.view.BleDebugActivity;
import com.leedarson.serviceimpl.shake.blectrl.BleCtrlAct;
import com.leedarson.serviceimpl.shake.sandbox.FileExploreActvitiy;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.event.SharkChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.prefs.aes.LDSAESUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.yanzhenjie.andserver.util.h;
import gdut.bsx.share2.a;
import io.reactivex.functions.e;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import meshsdk.util.SharedPreferenceHelper;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import timber.log.a;

public class ShakeActivity extends Activity implements View.OnClickListener {
    private static String c = "http://172.24.141.14:3000";
    public static ChangeQuickRedirect changeQuickRedirect;
    private Button a1;
    private CheckBox a2;
    private Uri d = null;
    private EditText f;
    private Button p0;
    private Button p1;
    private CheckBox p2;
    private Button p3;
    /* access modifiers changed from: private */
    public BleC075Service p4;
    /* access modifiers changed from: private */
    public EditText q;
    private Button x;
    private Button y;
    private Button z;
    private CompoundButton.OnCheckedChangeListener z4 = new a(this);

    /* access modifiers changed from: protected */
    @SensorsDataInstrumented
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PushAutoTrackHelper.onNewIntent(this, intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8672, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setContentView(R$layout.activity_shake);
            this.p1 = (Button) findViewById(R$id.share_log);
            this.f = (EditText) findViewById(R$id.address_edit);
            this.x = (Button) findViewById(R$id.cancel_btn);
            this.y = (Button) findViewById(R$id.sure_btn);
            this.q = (EditText) findViewById(R$id.et_mac);
            this.z = (Button) findViewById(R$id.sand_box_explore);
            this.p0 = (Button) findViewById(R$id.stop_btn);
            this.a1 = (Button) findViewById(R$id.rest_btn);
            this.z.setOnClickListener(this);
            this.p0.setOnClickListener(this);
            this.a1.setOnClickListener(this);
            this.p2 = (CheckBox) findViewById(R$id.check_use_webrtc);
            this.a2 = (CheckBox) findViewById(R$id.check_use_mobile);
            this.p2.setChecked(SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "use_webrtc", true));
            this.a2.setChecked(SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "use_mobile", false));
            this.p2.setOnCheckedChangeListener(this.z4);
            this.a2.setOnCheckedChangeListener(this.z4);
            this.f.setText(SharePreferenceUtils.getPrefString(this, "mainPage", c));
            this.x.setOnClickListener(this);
            this.y.setOnClickListener(this);
            this.p1.setOnClickListener(this);
            Button button = (Button) findViewById(R$id.export_log);
            this.p3 = button;
            button.setOnClickListener(this);
            findViewById(R$id.open_test_popup).setOnClickListener(this);
            findViewById(R$id.btn_ble_debug).setOnClickListener(this);
            findViewById(R$id.btn_ble_ctrl_demo).setOnClickListener(this);
            findViewById(R$id.btn_back_up_data).setOnClickListener(this);
            this.p4 = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            findViewById(R$id.btn_matter_demo).setOnClickListener(this);
            i();
        }
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8673, new Class[0], Void.TYPE).isSupported) {
            ((TextView) findViewById(R$id.tv_onCreate2loadH5)).setText("程序开始 - 加载web:" + Constans.onCreate2loadH5);
            ((TextView) findViewById(R$id.tv_loadH52DidRender)).setText("加载web - DidRender:" + Constans.loadH52DidRender);
            ((TextView) findViewById(R$id.tv_launchHttpServer)).setText("httpserver启动时长:" + Constans.launchHttpServer);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: j */
    public /* synthetic */ void k(CompoundButton buttonView, boolean isChecked) {
        if (PatchProxy.proxy(new Object[]{buttonView, new Byte(isChecked ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8688, new Class[]{CompoundButton.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(buttonView);
            return;
        }
        int viewId = buttonView.getId();
        if (viewId == R$id.check_use_webrtc) {
            SharePreferenceUtils.setPrefBoolean(this, "use_webrtc", isChecked);
        } else if (viewId == R$id.check_use_mobile) {
            SharePreferenceUtils.setPrefBoolean(this, "use_mobile", isChecked);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(buttonView);
    }

    @RequiresApi(api = 24)
    @SensorsDataInstrumented
    public void onClick(View view) {
        SystemService systemService;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8674, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (view2.getId() == R$id.cancel_btn) {
            finish();
        } else if (view2.getId() == R$id.sure_btn) {
            if (this.f.getText().toString().isEmpty()) {
                SharePreferenceUtils.setPrefString(this, "url", "");
                org.greenrobot.eventbus.c.c().l(new SharkChangeEvent());
                finish();
            } else if (this.f.getText().toString().startsWith(NetworkManager.MOCK_SCHEME_HTTP) || this.f.getText().toString().startsWith(NetworkManager.MOCK_SCHEME_HTTPS)) {
                SharePreferenceUtils.setPrefString(this, "url", this.f.getText().toString().trim());
                SharePreferenceUtils.setPrefString(this, "mainPage", this.f.getText().toString().trim());
                org.greenrobot.eventbus.c.c().l(new SharkChangeEvent());
                finish();
            } else {
                Toast.makeText(this, "格式不正确", 0).show();
            }
        } else if (view2.getId() == R$id.share_log) {
            l();
        } else if (view2.getId() == R$id.export_log) {
            exportCrashlogTask();
        } else if (view2.getId() == R$id.open_test_popup) {
            m();
        } else if (view2.getId() == R$id.sand_box_explore) {
            startActivity(new Intent(this, FileExploreActvitiy.class));
        } else if (view2.getId() == R$id.rest_btn) {
            if (this.p4 != null) {
                if (w.R()) {
                    if (!EasyPermissions.a(this, "android.permission.BLUETOOTH_ADVERTISE")) {
                        new com.tbruyelle.rxpermissions2.b(this).l("android.permission.BLUETOOTH_ADVERTISE").Y(new a(), new b());
                    }
                }
                this.p4.setActionReset((this.q.getText() == null || this.q.getText().toString() == "") ? "1cd6bdc05bfd" : this.q.getText().toString());
            }
        } else if (view2.getId() == R$id.stop_btn) {
            BleC075Service bleC075Service = this.p4;
            if (bleC075Service != null) {
                bleC075Service.setActionStop();
            }
        } else if (view2.getId() == R$id.btn_ble_debug) {
            bleDebugTask();
        } else if (view2.getId() == R$id.btn_matter_demo) {
            ((MatterService) com.alibaba.android.arouter.launcher.a.c().g(MatterService.class)).openDebug(this);
        } else if (view2.getId() == R$id.btn_ble_ctrl_demo) {
            startActivity(new Intent(this, BleCtrlAct.class));
        } else if (view2.getId() == R$id.btn_back_up_data && (systemService = (SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)) != null) {
            systemService.startBackUpLog(this);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class a implements e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8690, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 8689, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                timber.log.a.a(permission.toString(), new Object[0]);
                ShakeActivity.this.p4.setActionReset((ShakeActivity.this.q.getText() == null || ShakeActivity.this.q.getText().toString() == "") ? "1cd6bdc05bfd" : ShakeActivity.this.q.getText().toString());
            }
        }
    }

    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8691, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }

    @RequiresApi(api = 24)
    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8675, new Class[0], Void.TYPE).isSupported) {
            View view = getLayoutInflater().inflate(R$layout.test_popup_layout, (ViewGroup) null);
            EditText et_content = (EditText) view.findViewById(R$id.et_content);
            et_content.append(n(SharedPreferenceHelper.getAll(this)));
            et_content.append(n(SharePreferenceUtils.getAll(this)));
            ((Button) view.findViewById(R$id.btn_send)).setOnClickListener(new c());
            PopupWindow mPop = new PopupWindow(view, -1, -1);
            mPop.setOutsideTouchable(true);
            mPop.setFocusable(true);
            mPop.showAtLocation(findViewById(R$id.main_view), 17, 0, 0);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            View view2 = view;
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    @RequiresApi(api = 24)
    private String n(Map<String, ?> all) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{all}, this, changeQuickRedirect, false, 8676, new Class[]{Map.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer defStr = new StringBuffer();
        Object[] objects = all.keySet().toArray();
        Arrays.sort(objects);
        for (Object key : objects) {
            defStr.append(key);
            defStr.append(" = ");
            Object value = all.get(key);
            if (value instanceof String) {
                defStr.append(LDSAESUtils.decrypt(JNIUtil.getInstance().getStr4(), value.toString()));
            } else {
                defStr.append(value.toString());
            }
            defStr.append("\n");
        }
        return defStr.toString();
    }

    private void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8677, new Class[0], Void.TYPE).isSupported) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType(h.ALL_VALUE);
            intent.addCategory("android.intent.category.OPENABLE");
            try {
                startActivityForResult(Intent.createChooser(intent, "选择文件"), 100);
                overridePendingTransition(0, 0);
            } catch (Exception e) {
                Toast.makeText(this, "请先安装文件管理器", 0).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8678, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            a.b g = timber.log.a.g(Constants.SERVICE_DEBUG);
            g.c("====DemoActivityrequestCode=" + requestCode + " resultCode=" + resultCode, new Object[0]);
            if (requestCode == 100 && resultCode == -1 && data != null) {
                this.d = data.getData();
                o();
            } else if (requestCode == 120 && resultCode == -1) {
                Toast.makeText(getApplicationContext(), "分享成功", 1).show();
            }
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8679, new Class[0], Void.TYPE).isSupported) {
            new a.b(this).k(h.ALL_VALUE).m(g()).n("Share File").l(120).j().c();
        }
    }

    public Uri g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8680, new Class[0], Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        if (this.d == null) {
            Toast.makeText(getApplicationContext(), "没有选择需要分享的日志文件", 1).show();
        }
        return this.d;
    }

    public String f(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8681, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            return context.getResources().getString(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean h(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 8682, new Class[]{String[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : EasyPermissions.a(this, perms);
    }

    @pub.devrel.easypermissions.a(11)
    public void exportCrashlogTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8683, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (h(perms)) {
                e();
            } else {
                EasyPermissions.f(new c.b((Activity) this, 11, perms).g("").d(R$string.ok).b(R$string.cancel).a());
            }
        }
    }

    @pub.devrel.easypermissions.a(12)
    public void bleDebugTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8684, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            if (h(perms)) {
                startActivity(new Intent(this, BleDebugActivity.class));
            } else {
                EasyPermissions.f(new c.b((Activity) this, 12, perms).h(R$style.DialogButton).d(R$string.ok).b(R$string.cancel).a());
            }
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8685, new Class[0], Void.TYPE).isSupported) {
            String savePath = "";
            try {
                String CrashLogDir = String.format(Locale.US, "%s/crashLog/", new Object[]{getApplicationContext().getFilesDir().getPath()});
                if (new File(CrashLogDir).exists()) {
                    File logFile = new File(CrashLogDir + "crashLog.log");
                    if (logFile.exists() && "mounted".equals(Environment.getExternalStorageState())) {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        StringBuilder sb = new StringBuilder();
                        sb.append(path);
                        String str = File.separator;
                        sb.append(str);
                        sb.append(f(this));
                        sb.append(str);
                        sb.append("crashLog");
                        String logFilePath = sb.toString();
                        File logExternalFile = new File(logFilePath + str + "crashLog.txt");
                        savePath = logExternalFile.getAbsolutePath();
                        File logDir = logExternalFile.getParentFile();
                        if (!logDir.exists()) {
                            logDir.mkdirs();
                        }
                        if (logExternalFile.exists()) {
                            logExternalFile.delete();
                        }
                        int bytesum = 0;
                        logExternalFile.createNewFile();
                        InputStream inStream = new FileInputStream(logFile);
                        FileOutputStream fs = new FileOutputStream(logExternalFile);
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int read = inStream.read(buffer);
                            int byteread = read;
                            if (read == -1) {
                                break;
                            }
                            bytesum += byteread;
                            System.out.println(bytesum);
                            fs.write(buffer, 0, byteread);
                        }
                        inStream.close();
                        fs.close();
                    }
                }
                Toast.makeText(this, "导出结束！导出路径：" + savePath, 0).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    File appSpecificExternalDir = getExternalCacheDir();
                    Locale locale = Locale.US;
                    String format = String.format(locale, "%s/log", new Object[]{getApplicationContext().getFilesDir().getPath()});
                    c(format, String.format(locale, "%s/log", new Object[]{appSpecificExternalDir.getAbsolutePath() + "/ALog"}));
                } else {
                    Locale locale2 = Locale.US;
                    String format2 = String.format(locale2, "%s/log", new Object[]{getApplicationContext().getFilesDir().getPath()});
                    c(format2, String.format(locale2, "%s/log", new Object[]{Environment.getExternalStorageDirectory().getAbsolutePath() + "/ALog"}));
                }
                d(new File(String.format(Locale.US, "%s/log", new Object[]{getApplicationContext().getFilesDir().getPath()})));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void c(String oldPath, String newPath) {
        File temp;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{oldPath, newPath}, this, changeQuickRedirect, false, 8686, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                new File(newPath).mkdirs();
                File a3 = new File(oldPath);
                if (a3.exists()) {
                    String[] file = a3.list();
                    for (int i = 0; i < file.length; i++) {
                        String str = File.separator;
                        if (oldPath.endsWith(str)) {
                            temp = new File(oldPath + file[i]);
                        } else {
                            temp = new File(oldPath + str + file[i]);
                        }
                        if (temp.isFile()) {
                            FileInputStream input = new FileInputStream(temp);
                            FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                            byte[] b2 = new byte[5120];
                            while (true) {
                                int read = input.read(b2);
                                int len = read;
                                if (read == -1) {
                                    break;
                                }
                                output.write(b2, 0, len);
                            }
                            output.flush();
                            output.close();
                            input.close();
                        }
                        if (temp.isDirectory()) {
                            c(oldPath + "/" + file[i], newPath + "/" + file[i]);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("复制整个文件夹内容操作出错");
                e.printStackTrace();
            }
        }
    }

    public static void d(File directory) {
        if (!PatchProxy.proxy(new Object[]{directory}, (Object) null, changeQuickRedirect, true, 8687, new Class[]{File.class}, Void.TYPE).isSupported) {
            try {
                if (!directory.isDirectory()) {
                    directory.delete();
                    return;
                }
                File[] files = directory.listFiles();
                if (files.length == 0) {
                    directory.delete();
                    return;
                }
                for (File file : files) {
                    if (file.isDirectory()) {
                        d(file);
                    } else {
                        file.delete();
                    }
                }
                directory.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
