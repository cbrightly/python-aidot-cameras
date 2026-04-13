package chip.clusterinfo;

import java.util.Map;

public interface ClusterCommandCallback {
    void onFailure(Exception exc);

    void onSuccess(Map<CommandResponseInfo, Object> map);
}
