package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tg implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tg a = new tg();

    private /* synthetic */ tg() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DiagnosticLogsCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.DiagnosticLogsCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
