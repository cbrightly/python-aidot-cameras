package chip.devicecontroller.model;

import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements BiConsumer {
    public final /* synthetic */ StringBuilder a;

    public /* synthetic */ a(StringBuilder sb) {
        this.a = sb;
    }

    public final void accept(Object obj, Object obj2) {
        ClusterState.lambda$toString$1(this.a, (Long) obj, (EventState) obj2);
    }
}
