package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class b60 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ b60 a = new b60();

    private /* synthetic */ b60() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).readLanguageAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
