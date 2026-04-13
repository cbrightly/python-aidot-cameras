package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class c2 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ c2 a = new c2();

    private /* synthetic */ c2() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).testListInt8UReverseRequest((ChipClusters.TestClusterCluster.TestListInt8UReverseResponseCallback) obj, (ArrayList) map.get("arg1"));
    }
}
