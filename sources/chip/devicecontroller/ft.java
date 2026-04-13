package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ft implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ft a = new ft();

    private /* synthetic */ ft() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WakeOnLanCluster) baseChipCluster).readMACAddressAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
