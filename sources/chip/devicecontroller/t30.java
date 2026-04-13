package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t30 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t30 a = new t30();

    private /* synthetic */ t30() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PressureMeasurementCluster) baseChipCluster).readClusterRevisionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
