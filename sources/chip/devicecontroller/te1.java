package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class te1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ te1 a = new te1();

    private /* synthetic */ te1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeInt48uAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
