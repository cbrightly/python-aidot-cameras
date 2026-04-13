package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import com.google.firebase.messaging.Constants;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class k1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ k1 a = new k1();

    private /* synthetic */ k1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OperationalCredentialsCluster) baseChipCluster).updateFabricLabel((ChipClusters.OperationalCredentialsCluster.NOCResponseCallback) obj, (String) map.get(Constants.ScionAnalytics.PARAM_LABEL));
    }
}
