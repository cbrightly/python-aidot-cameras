package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ql implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ql a = new ql();

    private /* synthetic */ ql() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IlluminanceMeasurementCluster) baseChipCluster).readMinMeasuredValueAttribute((ChipClusters.IlluminanceMeasurementCluster.MinMeasuredValueAttributeCallback) obj);
    }
}
