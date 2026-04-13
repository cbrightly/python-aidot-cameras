package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ng0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ng0 a = new ng0();

    private /* synthetic */ ng0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).readEffectiveOperationModeAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
