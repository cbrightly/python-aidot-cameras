package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class om0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ om0 a = new om0();

    private /* synthetic */ om0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DiagnosticLogsCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.DiagnosticLogsCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
