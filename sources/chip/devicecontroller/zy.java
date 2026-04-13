package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zy implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zy a = new zy();

    private /* synthetic */ zy() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IdentifyCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.IdentifyCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
