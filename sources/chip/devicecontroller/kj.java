package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class kj implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ kj a = new kj();

    private /* synthetic */ kj() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readProductURLAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
