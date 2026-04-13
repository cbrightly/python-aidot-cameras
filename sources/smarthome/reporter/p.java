package smarthome.reporter;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class p implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ p(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.E((Throwable) obj);
    }
}
