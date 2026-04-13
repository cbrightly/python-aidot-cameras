package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ka1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ka1 a = new ka1();

    private /* synthetic */ ka1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readPrimary4IntensityAttribute((ChipClusters.ColorControlCluster.Primary4IntensityAttributeCallback) obj);
    }
}
