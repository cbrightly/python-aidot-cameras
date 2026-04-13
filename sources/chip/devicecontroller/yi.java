package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yi implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yi a = new yi();

    private /* synthetic */ yi() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ElectricalMeasurementCluster) baseChipCluster).readRmsVoltageMinAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
