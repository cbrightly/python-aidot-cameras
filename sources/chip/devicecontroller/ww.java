package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ww implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ww a = new ww();

    private /* synthetic */ ww() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatUserInterfaceConfigurationCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.ThermostatUserInterfaceConfigurationCluster.AttributeListAttributeCallback) obj);
    }
}
