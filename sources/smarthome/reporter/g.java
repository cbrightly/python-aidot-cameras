package smarthome.reporter;

import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class g implements io.reactivex.g {
    public final /* synthetic */ HttpServiceReporter a;
    public final /* synthetic */ String b;

    public /* synthetic */ g(HttpServiceReporter httpServiceReporter, String str) {
        this.a = httpServiceReporter;
        this.b = str;
    }

    public final void subscribe(f fVar) {
        this.a.s(this.b, fVar);
    }
}
