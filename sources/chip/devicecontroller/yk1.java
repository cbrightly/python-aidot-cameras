package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yk1 a = new yk1();

    private /* synthetic */ yk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableBitmap64Attribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
