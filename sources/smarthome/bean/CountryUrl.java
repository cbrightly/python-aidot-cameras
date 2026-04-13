package smarthome.bean;

import java.util.List;

public class CountryUrl {
    private List<String> contries;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public List<String> getCountries() {
        return this.contries;
    }

    public void setCountries(List<String> contries2) {
        this.contries = contries2;
    }
}
