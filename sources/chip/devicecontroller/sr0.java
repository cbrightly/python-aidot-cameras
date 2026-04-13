package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sr0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sr0 a = new sr0();

    private /* synthetic */ sr0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxAckRequestedCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
