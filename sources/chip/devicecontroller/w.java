package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class w implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ w a = new w();

    private /* synthetic */ w() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).timedInvokeRequest((ChipClusters.DefaultClusterCallback) obj, 10000);
    }
}
