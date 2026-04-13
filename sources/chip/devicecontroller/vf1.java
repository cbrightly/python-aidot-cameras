package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vf1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vf1 a = new vf1();

    private /* synthetic */ vf1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).writeDefaultMoveRateAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
