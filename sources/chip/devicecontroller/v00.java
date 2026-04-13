package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class v00 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ v00 a = new v00();

    private /* synthetic */ v00() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readRxOtherCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
