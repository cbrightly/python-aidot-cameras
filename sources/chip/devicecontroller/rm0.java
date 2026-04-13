package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rm0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rm0 a = new rm0();

    private /* synthetic */ rm0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).readPumpStatusAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
