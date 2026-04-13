package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ek1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ek1 a = new ek1();

    private /* synthetic */ ek1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ModeSelectCluster) baseChipCluster).writeStartUpModeAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
