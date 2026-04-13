package smarthome.reporter;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class h implements g {
    public final /* synthetic */ HttpServiceReporter a;
    public final /* synthetic */ String b;

    public /* synthetic */ h(HttpServiceReporter httpServiceReporter, String str) {
        this.a = httpServiceReporter;
        this.b = str;
    }

    public final void subscribe(f fVar) {
        this.a.u(this.b, fVar);
    }
}
