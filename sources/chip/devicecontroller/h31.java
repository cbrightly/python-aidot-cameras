package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class h31 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ h31 a = new h31();

    private /* synthetic */ h31() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ElectricalMeasurementCluster) baseChipCluster).readRmsCurrentMinAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
