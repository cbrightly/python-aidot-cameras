package smarthome.reporter;

import io.reactivex.functions.b;
import smarthome.reporter.HttpServiceReporter;

/* compiled from: lambda */
public final /* synthetic */ class i implements b {
    public final /* synthetic */ HttpServiceReporter a;

    public /* synthetic */ i(HttpServiceReporter httpServiceReporter) {
        this.a = httpServiceReporter;
    }

    public final Object apply(Object obj, Object obj2) {
        return this.a.m((HttpServiceReporter.PingInfoBean) obj, (HttpServiceReporter.PingInfoBean) obj2);
    }
}
