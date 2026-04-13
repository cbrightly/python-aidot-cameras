package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ik implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ik a = new ik();

    private /* synthetic */ ik() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.LevelControlCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
