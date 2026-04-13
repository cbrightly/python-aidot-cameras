package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xt0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xt0 a = new xt0();

    private /* synthetic */ xt0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AdministratorCommissioningCluster) baseChipCluster).readAdminVendorIdAttribute((ChipClusters.AdministratorCommissioningCluster.AdminVendorIdAttributeCallback) obj);
    }
}
