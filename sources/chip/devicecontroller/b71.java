package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class b71 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ b71 a = new b71();

    private /* synthetic */ b71() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readRebootCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
