package smarthome.reporter;

import android.content.Context;
import com.leedarson.log.mgr.u;
import com.leedarson.log.tracker.BaseStepBean;
import java.util.HashMap;

/* compiled from: WebViewDiagnosisReporter */
public class q {
    private static q a;
    private String b;
    private HashMap<String, Long> c;

    public static q b() {
        if (a == null) {
            a = new q();
        }
        return a;
    }

    public void f(Context context, String traceId) {
        this.b = traceId;
        r tracker = new r(context);
        tracker.j(traceId, "HttpServer", "diagnosisWebView");
        u.d().b(tracker);
    }

    public void a(BaseStepBean bean) {
        u.d().a(this.b, bean);
    }

    public void e() {
        u.d().e(this.b);
    }

    public void d(String step) {
        if (this.c == null) {
            this.c = new HashMap<>();
        }
        this.c.put(step, Long.valueOf(System.currentTimeMillis()));
    }

    public long c(String step) {
        HashMap<String, Long> hashMap = this.c;
        if (hashMap == null || !hashMap.containsKey(step)) {
            return -1;
        }
        return this.c.get(step).longValue();
    }
}
