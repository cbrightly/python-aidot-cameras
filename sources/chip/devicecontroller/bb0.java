package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class bb0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ bb0 a = new bb0();

    private /* synthetic */ bb0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt64sAttribute((ChipClusters.TestClusterCluster.NullableInt64sAttributeCallback) obj);
    }
}
