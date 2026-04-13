package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jn0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jn0 a = new jn0();

    private /* synthetic */ jn0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.SoftwareDiagnosticsCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.SoftwareDiagnosticsCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
