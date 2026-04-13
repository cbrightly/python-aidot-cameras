package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class l80 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ l80 a = new l80();

    private /* synthetic */ l80() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).readLifetimeEnergyConsumedAttribute((ChipClusters.PumpConfigurationAndControlCluster.LifetimeEnergyConsumedAttributeCallback) obj);
    }
}
