package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rk1 a = new rk1();

    private /* synthetic */ rk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).writeOptionsAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
