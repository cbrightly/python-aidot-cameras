package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vt implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vt a = new vt();

    private /* synthetic */ vt() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorPointGIntensityAttribute((ChipClusters.ColorControlCluster.ColorPointGIntensityAttributeCallback) obj);
    }
}
