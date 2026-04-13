package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class h30 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ h30 a = new h30();

    private /* synthetic */ h30() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).readLocalTemperatureAttribute((ChipClusters.ThermostatCluster.LocalTemperatureAttributeCallback) obj);
    }
}
