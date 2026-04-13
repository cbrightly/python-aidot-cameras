package smarthome.lds;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.v;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import java.util.regex.Pattern;
import smarthome.reporter.HttpServiceReporter;

/* compiled from: LDSApplication */
public class a extends BaseApplication {
    protected String C4 = "";
    protected String D4 = "";

    public void onCreate() {
        Log.i("GhuntStart", w.r() + "----Native main start");
        super.onCreate();
    }

    public void z() {
        com.leedarson.log.sensorsdata.a.b().k(this.y);
        if ("M071-AiDot".equals(this.y) || "M071-Linkind".equals(this.y)) {
            B();
        } else {
            timber.log.a.g("SA.LDSApplication").a("非M071-AiDot 或 M071-Linkind，不初始化神策", new Object[0]);
        }
    }

    public void B() {
        String str = this.D4;
        String SA_SERVER_URL = SharePreferenceUtils.getPrefString(this, "operatorLogServerUrl", "");
        if (TextUtils.isEmpty(SA_SERVER_URL)) {
            Log.w("SA.LDSApplication", "init initSensorsData but url is empty!  ,real operatorLogServerUrl:" + SharePreferenceUtils.getPrefString(this, "operatorLogServerUrl", ""));
        } else if (!Pattern.compile("(http|https):\\/\\/([\\w.]+\\/?)\\S*").matcher(SA_SERVER_URL).find()) {
            Log.e("SA.LDSApplication", "init initSensorsData but pattern is not matches !!!");
        } else {
            com.leedarson.log.sensorsdata.a.b().e(this, SA_SERVER_URL, !o());
            Log.i("SA.LDSApplication", "init initSensorsData success:" + SA_SERVER_URL);
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        v.d("Application#attach#Splash#onCreate", "App应用启动中");
    }

    private void A() {
        HttpServiceReporter reporter = HttpServiceReporter.i();
        reporter.k();
        reporter.T();
    }

    public void l() {
        super.l();
        z();
        A();
    }
}
