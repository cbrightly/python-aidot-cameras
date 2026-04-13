package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fv0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fv0 a = new fv0();

    private /* synthetic */ fv0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readInt56uAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
