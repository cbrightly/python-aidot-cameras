package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vw0 a = new vw0();

    private /* synthetic */ vw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ElectricalMeasurementCluster) baseChipCluster).readActivePowerAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
