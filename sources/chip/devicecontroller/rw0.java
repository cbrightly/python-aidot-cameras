package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rw0 a = new rw0();

    private /* synthetic */ rw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readProductIDAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
