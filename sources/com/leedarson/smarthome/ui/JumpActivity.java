package com.leedarson.smarthome.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.v;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONObject;

public class JumpActivity extends AppCompatActivity {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10720, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            SharePreferenceUtils.getPrefString(this, "accessToken", "");
            SharePreferenceUtils.getPrefString(this, "refreshToken", "");
            LDSBaseMqttService _mqttService = (LDSBaseMqttService) a.c().g(LDSBaseMqttService.class);
            if (_mqttService != null) {
                _mqttService.init(BaseApplication.b(), (JSONObject) null, (String) null);
            }
            startActivity(new Intent(this, MainActivity.class));
            finish();
            v.d("Application#attach#Splash#onCreate", "App应用启动中");
        }
    }
}
