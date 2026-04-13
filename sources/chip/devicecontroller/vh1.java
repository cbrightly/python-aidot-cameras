package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vh1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vh1 a = new vh1();

    private /* synthetic */ vh1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeRangeRestrictedInt16uAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
