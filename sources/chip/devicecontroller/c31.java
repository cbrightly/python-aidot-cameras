package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class c31 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ c31 a = new c31();

    private /* synthetic */ c31() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.NetworkCommissioningCluster) baseChipCluster).readConnectMaxTimeSecondsAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
