package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class nb1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ nb1 a = new nb1();

    private /* synthetic */ nb1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).writeLampManufacturerAttribute((ChipClusters.DefaultClusterCallback) obj, (String) map.get("value"));
    }
}
