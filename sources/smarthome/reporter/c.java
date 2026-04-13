package smarthome.reporter;

import com.leedarson.base.http.bean.HttpAccessLogBean;
import io.reactivex.functions.b;

/* compiled from: lambda */
public final /* synthetic */ class c implements b {
    public final /* synthetic */ HttpServiceReporter a;

    public /* synthetic */ c(HttpServiceReporter httpServiceReporter) {
        this.a = httpServiceReporter;
    }

    public final Object apply(Object obj, Object obj2) {
        return this.a.o((HttpAccessLogBean) obj, (HttpAccessLogBean) obj2);
    }
}
