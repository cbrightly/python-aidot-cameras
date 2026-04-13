package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mj0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mj0 a = new mj0();

    private /* synthetic */ mj0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.NetworkCommissioningCluster) baseChipCluster).readLastConnectErrorValueAttribute((ChipClusters.NetworkCommissioningCluster.LastConnectErrorValueAttributeCallback) obj);
    }
}
