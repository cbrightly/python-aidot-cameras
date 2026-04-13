package meshsdk.model;

import java.io.Serializable;

public class MeshNetKey implements MeshKey, Serializable {
    public int index;
    public byte[] key;
    public String name;

    public MeshNetKey(String name2, int index2, byte[] key2) {
        this.name = name2;
        this.index = index2;
        this.key = key2;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public byte[] getKey() {
        return this.key;
    }
}
