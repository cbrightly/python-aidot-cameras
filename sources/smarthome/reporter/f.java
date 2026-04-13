package smarthome.reporter;

import io.reactivex.functions.e;
import smarthome.reporter.HttpServiceReporter;

/* compiled from: lambda */
public final /* synthetic */ class f implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ f(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.I((HttpServiceReporter.AppNetCheckReporterBean) obj);
    }
}
