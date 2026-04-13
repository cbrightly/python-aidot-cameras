package smarthome.reporter;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class d implements f {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ d(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final Object apply(Object obj) {
        return this.c.G((Boolean) obj);
    }
}
