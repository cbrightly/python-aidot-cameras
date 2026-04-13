package chip.devicecontroller.model;

import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class c implements BiConsumer {
    public final /* synthetic */ StringBuilder a;

    public /* synthetic */ c(StringBuilder sb) {
        this.a = sb;
    }

    public final void accept(Object obj, Object obj2) {
        EndpointState.lambda$toString$0(this.a, (Long) obj, (ClusterState) obj2);
    }
}
