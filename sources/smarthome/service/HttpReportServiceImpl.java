package smarthome.service;

import android.content.Context;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.serviceinterface.HttpReportService;
import smarthome.reporter.HttpServiceReporter;
import smarthome.utils.l;

public class HttpReportServiceImpl implements HttpReportService {
    public void init(Context context) {
    }

    public void onBackgroundAndFrontSwitch(boolean isBackground) {
    }

    public String getNetWorkInfoDetail() {
        return HttpServiceReporter.i().h();
    }

    public String getUuid() {
        return l.f(BaseApplication.b());
    }
}
