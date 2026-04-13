package smarthome.reporter;

import io.reactivex.e;
import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class n implements f {
    public final /* synthetic */ e c;

    public /* synthetic */ n(e eVar) {
        this.c = eVar;
    }

    public final Object apply(Object obj) {
        e eVar = this.c;
        HttpServiceReporter.N(eVar, (Long) obj);
        return eVar;
    }
}
