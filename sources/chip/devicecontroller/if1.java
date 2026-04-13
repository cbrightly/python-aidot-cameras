package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class if1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ if1 a = new if1();

    private /* synthetic */ if1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).writeOptionsAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
