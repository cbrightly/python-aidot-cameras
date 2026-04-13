package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ua1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ua1 a = new ua1();

    private /* synthetic */ ua1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).writeColorPointBXAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
