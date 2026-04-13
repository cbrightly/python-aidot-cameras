package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qi implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qi a = new qi();

    private /* synthetic */ qi() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TemperatureMeasurementCluster) baseChipCluster).readToleranceAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
