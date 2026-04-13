package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mp implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mp a = new mp();

    private /* synthetic */ mp() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.ColorControlCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
