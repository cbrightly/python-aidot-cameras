package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class id0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ id0 a = new id0();

    private /* synthetic */ id0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt24uAttribute((ChipClusters.TestClusterCluster.NullableInt24uAttributeCallback) obj);
    }
}
