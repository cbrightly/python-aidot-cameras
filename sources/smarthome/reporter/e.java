package smarthome.reporter;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class e implements f {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ e(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final Object apply(Object obj) {
        return this.c.A((Boolean) obj);
    }
}
