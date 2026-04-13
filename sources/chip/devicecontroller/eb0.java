package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class eb0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ eb0 a = new eb0();

    private /* synthetic */ eb0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.ThreadNetworkDiagnosticsCluster.AttributeListAttributeCallback) obj);
    }
}
