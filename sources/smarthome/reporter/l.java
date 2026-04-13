package smarthome.reporter;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class l implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ l(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.K((Throwable) obj);
    }
}
