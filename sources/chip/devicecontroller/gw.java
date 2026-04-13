package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gw implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gw a = new gw();

    private /* synthetic */ gw() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).readOnLevelAttribute((ChipClusters.LevelControlCluster.OnLevelAttributeCallback) obj);
    }
}
