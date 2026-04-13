package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class w80 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ w80 a = new w80();

    private /* synthetic */ w80() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.EthernetNetworkDiagnosticsCluster) baseChipCluster).readPacketTxCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
