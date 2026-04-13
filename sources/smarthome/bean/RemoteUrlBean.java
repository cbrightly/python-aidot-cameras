package smarthome.bean;

import java.util.List;

public class RemoteUrlBean {
    private String defaultUrl;
    private List<CountryUrl> list;

    public String getDefaultUrl() {
        return this.defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl2) {
        this.defaultUrl = defaultUrl2;
    }

    public List<CountryUrl> getList() {
        return this.list;
    }

    public void setList(List<CountryUrl> list2) {
        this.list = list2;
    }
}
