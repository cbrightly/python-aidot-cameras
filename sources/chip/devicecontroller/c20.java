package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class c20 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ c20 a = new c20();

    private /* synthetic */ c20() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readTotalOperationalHoursAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
