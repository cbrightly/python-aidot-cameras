package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tk1 a = new tk1();

    private /* synthetic */ tk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IdentifyCluster) baseChipCluster).writeIdentifyTimeAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
