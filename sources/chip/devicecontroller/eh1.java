package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class eh1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ eh1 a = new eh1();

    private /* synthetic */ eh1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).writeLampTypeAttribute((ChipClusters.DefaultClusterCallback) obj, (String) map.get("value"));
    }
}
