package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class br0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ br0 a = new br0();

    private /* synthetic */ br0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readActiveRadioFaultsAttribute((ChipClusters.GeneralDiagnosticsCluster.ActiveRadioFaultsAttributeCallback) obj);
    }
}
