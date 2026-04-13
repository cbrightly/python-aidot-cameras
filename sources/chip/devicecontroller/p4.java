package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p4 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p4 a = new p4();

    private /* synthetic */ p4() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).testEnumsRequest((ChipClusters.TestClusterCluster.TestEnumsResponseCallback) obj, (Integer) map.get("arg1"), (Integer) map.get("arg2"));
    }
}
