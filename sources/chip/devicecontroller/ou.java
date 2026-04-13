package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ou implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ou a = new ou();

    private /* synthetic */ ou() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).readEnablePrivacyModeButtonAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
