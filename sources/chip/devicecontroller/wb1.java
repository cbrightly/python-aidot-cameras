package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wb1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wb1 a = new wb1();

    private /* synthetic */ wb1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).writeOnTransitionTimeAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
