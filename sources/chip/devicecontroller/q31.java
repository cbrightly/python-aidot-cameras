package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class q31 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ q31 a = new q31();

    private /* synthetic */ q31() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readProductNameAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
