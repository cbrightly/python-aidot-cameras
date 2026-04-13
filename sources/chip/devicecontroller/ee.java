package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ee implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ee a = new ee();

    private /* synthetic */ ee() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AccountLoginCluster) baseChipCluster).login((ChipClusters.DefaultClusterCallback) obj, (String) map.get("tempAccountIdentifier"), (String) map.get("setupPIN"), 10000);
    }
}
