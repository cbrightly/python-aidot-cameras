package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ui implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ui a = new ui();

    private /* synthetic */ ui() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.IlluminanceMeasurementCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.IlluminanceMeasurementCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
