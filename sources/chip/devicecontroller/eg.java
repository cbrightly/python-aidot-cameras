package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class eg implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ eg a = new eg();

    private /* synthetic */ eg() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorPointRIntensityAttribute((ChipClusters.ColorControlCluster.ColorPointRIntensityAttributeCallback) obj);
    }
}
