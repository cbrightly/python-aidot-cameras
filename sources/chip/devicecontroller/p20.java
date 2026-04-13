package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p20 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p20 a = new p20();

    private /* synthetic */ p20() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.GeneralDiagnosticsCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
