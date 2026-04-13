package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wg0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wg0 a = new wg0();

    private /* synthetic */ wg0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).readStartOfWeekAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
