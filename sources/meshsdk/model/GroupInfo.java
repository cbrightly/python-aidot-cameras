package meshsdk.model;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;

public class GroupInfo implements Serializable {
    public static final String TYPE_MUSIC = "music";
    public static final String TYPE_NORMAL = "normal";
    public int address;
    public int groupId;
    public String groupType = TYPE_NORMAL;
    public String name;

    public String toString() {
        return "{" + "\"groupId\":" + this.groupId + ",\"name\":\"" + this.name + StringUtil.DOUBLE_QUOTE + ",\"address\":" + this.address + '}';
    }
}
