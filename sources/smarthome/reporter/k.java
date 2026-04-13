package smarthome.reporter;

import io.reactivex.e;
import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class k implements f {
    public final /* synthetic */ HttpServiceReporter c;

    public /* synthetic */ k(HttpServiceReporter httpServiceReporter) {
        this.c = httpServiceReporter;
    }

    public final Object apply(Object obj) {
        return this.c.q((e) obj);
    }
}
