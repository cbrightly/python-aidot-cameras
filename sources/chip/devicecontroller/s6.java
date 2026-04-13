package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class s6 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ s6 a = new s6();

    private /* synthetic */ s6() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralCommissioningCluster) baseChipCluster).commissioningComplete((ChipClusters.GeneralCommissioningCluster.CommissioningCompleteResponseCallback) obj);
    }
}
