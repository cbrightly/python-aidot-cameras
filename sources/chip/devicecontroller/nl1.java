package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class nl1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ nl1 a = new nl1();

    private /* synthetic */ nl1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeInt16uAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
