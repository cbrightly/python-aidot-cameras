package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tb1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tb1 a = new tb1();

    private /* synthetic */ tb1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeInt56uAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
