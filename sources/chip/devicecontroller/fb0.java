package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fb0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fb0 a = new fb0();

    private /* synthetic */ fb0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readChannelAttribute((ChipClusters.ThreadNetworkDiagnosticsCluster.ChannelAttributeCallback) obj);
    }
}
