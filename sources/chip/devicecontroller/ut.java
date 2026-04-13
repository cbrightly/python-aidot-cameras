package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ut implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ut a = new ut();

    private /* synthetic */ ut() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readRangeRestrictedInt8sAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
