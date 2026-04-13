package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ti implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ti a = new ti();

    private /* synthetic */ ti() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AccessControlCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.AccessControlCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
