package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yg0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yg0 a = new yg0();

    private /* synthetic */ yg0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readLeaderRoleCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
