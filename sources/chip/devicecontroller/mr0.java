package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mr0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mr0 a = new mr0();

    private /* synthetic */ mr0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ElectricalMeasurementCluster) baseChipCluster).readClusterRevisionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
