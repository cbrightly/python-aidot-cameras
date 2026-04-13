package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class u70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ u70 a = new u70();

    private /* synthetic */ u70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readInt24sAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
