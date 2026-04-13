package smarthome.reporter;

import io.reactivex.functions.e;
import smarthome.reporter.HttpServiceReporter;

/* compiled from: lambda */
public final /* synthetic */ class m implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ m(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.w((HttpServiceReporter.AppNetCheckReporterBean) obj);
    }
}
