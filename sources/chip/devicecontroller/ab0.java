package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ab0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ab0 a = new ab0();

    private /* synthetic */ ab0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readInt16uAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
