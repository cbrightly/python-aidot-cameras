package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class dv implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ dv a = new dv();

    private /* synthetic */ dv() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PressureMeasurementCluster) baseChipCluster).readMinScaledValueAttribute((ChipClusters.PressureMeasurementCluster.MinScaledValueAttributeCallback) obj);
    }
}
