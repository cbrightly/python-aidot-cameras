package smarthome.reporter;

import io.reactivex.functions.e;
import smarthome.reporter.HttpServiceReporter;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ b(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.C((HttpServiceReporter.AppNetCheckReporterBean) obj);
    }
}
