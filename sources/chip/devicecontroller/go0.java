package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class go0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ go0 a = new go0();

    private /* synthetic */ go0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ElectricalMeasurementCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.ElectricalMeasurementCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
