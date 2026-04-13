package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zn0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zn0 a = new zn0();

    private /* synthetic */ zn0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IlluminanceMeasurementCluster) baseChipCluster).readMeasuredValueAttribute((ChipClusters.IlluminanceMeasurementCluster.MeasuredValueAttributeCallback) obj);
    }
}
