package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class n20 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ n20 a = new n20();

    private /* synthetic */ n20() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.SoftwareDiagnosticsCluster) baseChipCluster).readCurrentHeapFreeAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
