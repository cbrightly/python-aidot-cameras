package chip.devicecontroller.model;

import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements BiConsumer {
    public final /* synthetic */ StringBuilder a;

    public /* synthetic */ d(StringBuilder sb) {
        this.a = sb;
    }

    public final void accept(Object obj, Object obj2) {
        NodeState.lambda$toString$0(this.a, (Integer) obj, (EndpointState) obj2);
    }
}
