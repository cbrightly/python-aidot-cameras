package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yt implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yt a = new yt();

    private /* synthetic */ yt() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TemperatureMeasurementCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.TemperatureMeasurementCluster.AttributeListAttributeCallback) obj);
    }
}
