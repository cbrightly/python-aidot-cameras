package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class lh0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lh0 a = new lh0();

    private /* synthetic */ lh0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt8uAttribute((ChipClusters.TestClusterCluster.NullableInt8uAttributeCallback) obj);
    }
}
