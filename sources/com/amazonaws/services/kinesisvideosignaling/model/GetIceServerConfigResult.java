package com.amazonaws.services.kinesisvideosignaling.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetIceServerConfigResult implements Serializable {
    private List<IceServer> iceServerList = new ArrayList();

    public List<IceServer> getIceServerList() {
        return this.iceServerList;
    }

    public void setIceServerList(Collection<IceServer> iceServerList2) {
        if (iceServerList2 == null) {
            this.iceServerList = null;
        } else {
            this.iceServerList = new ArrayList(iceServerList2);
        }
    }

    public GetIceServerConfigResult withIceServerList(IceServer... iceServerList2) {
        if (getIceServerList() == null) {
            this.iceServerList = new ArrayList(iceServerList2.length);
        }
        for (IceServer value : iceServerList2) {
            this.iceServerList.add(value);
        }
        return this;
    }

    public GetIceServerConfigResult withIceServerList(Collection<IceServer> iceServerList2) {
        setIceServerList(iceServerList2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIceServerList() != null) {
            sb.append("IceServerList: " + getIceServerList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getIceServerList() == null ? 0 : getIceServerList().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetIceServerConfigResult)) {
            return false;
        }
        GetIceServerConfigResult other = (GetIceServerConfigResult) obj;
        if ((other.getIceServerList() == null) ^ (getIceServerList() == null)) {
            return false;
        }
        if (other.getIceServerList() == null || other.getIceServerList().equals(getIceServerList())) {
            return true;
        }
        return false;
    }
}
