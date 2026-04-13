package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yl a = new yl();

    private /* synthetic */ yl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LocalizationConfigurationCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.LocalizationConfigurationCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
