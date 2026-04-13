package meshsdk.cache;

import meshsdk.callback.MeshCustomcmdCallback;

public class CacheHanderMsgWrapper {
    public String cacheKey;
    public MeshCustomcmdCallback callback;
    public int whatMsg;

    public CacheHanderMsgWrapper(int whatMsg2, String cacheKey2, MeshCustomcmdCallback callback2) {
        this.whatMsg = whatMsg2;
        this.cacheKey = cacheKey2;
        this.callback = callback2;
    }
}
