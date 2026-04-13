package chip.devicecontroller.model;

import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements BiConsumer {
    public final /* synthetic */ StringBuilder a;

    public /* synthetic */ b(StringBuilder sb) {
        this.a = sb;
    }

    public final void accept(Object obj, Object obj2) {
        ClusterState.lambda$toString$0(this.a, (Long) obj, (AttributeState) obj2);
    }
}
