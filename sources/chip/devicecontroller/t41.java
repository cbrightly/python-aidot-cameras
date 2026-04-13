package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t41 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t41 a = new t41();

    private /* synthetic */ t41() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readParentChangeCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
