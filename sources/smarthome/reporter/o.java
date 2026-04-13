package smarthome.reporter;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class o implements e {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ o(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final void accept(Object obj) {
        this.c.y((Throwable) obj);
    }
}
