package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class hi0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ hi0 a = new hi0();

    private /* synthetic */ hi0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.TestClusterCluster.AttributeListAttributeCallback) obj);
    }
}
