package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qj0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qj0 a = new qj0();

    private /* synthetic */ qj0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).readOperationModeAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
