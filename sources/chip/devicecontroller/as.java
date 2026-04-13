package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class as implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ as a = new as();

    private /* synthetic */ as() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IlluminanceMeasurementCluster) baseChipCluster).readLightSensorTypeAttribute((ChipClusters.IlluminanceMeasurementCluster.LightSensorTypeAttributeCallback) obj);
    }
}
