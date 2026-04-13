package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jk1 a = new jk1();

    private /* synthetic */ jk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt32uAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
