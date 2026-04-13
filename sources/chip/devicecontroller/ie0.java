package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ie0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ie0 a = new ie0();

    private /* synthetic */ ie0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readRangeRestrictedInt8uAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
