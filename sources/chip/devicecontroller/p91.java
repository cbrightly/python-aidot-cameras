package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p91 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p91 a = new p91();

    private /* synthetic */ p91() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.RelativeHumidityMeasurementCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.RelativeHumidityMeasurementCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
