package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class l50 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ l50 a = new l50();

    private /* synthetic */ l50() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt56sAttribute((ChipClusters.TestClusterCluster.NullableInt56sAttributeCallback) obj);
    }
}
