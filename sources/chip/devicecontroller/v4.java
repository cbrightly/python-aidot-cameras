package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class v4 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ v4 a = new v4();

    private /* synthetic */ v4() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).testUnknownCommand((ChipClusters.DefaultClusterCallback) obj);
    }
}
