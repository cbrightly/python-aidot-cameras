package smarthome.bean;

public class GuidePageBean {
    private String backColor;
    private String title;
    private String titleBarColor;
    private String titleColor;

    public String getBackColor() {
        return this.backColor;
    }

    public void setBackColor(String backColor2) {
        this.backColor = backColor2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getTitleColor() {
        return this.titleColor;
    }

    public void setTitleColor(String titleColor2) {
        this.titleColor = titleColor2;
    }

    public String getTitleBarColor() {
        return this.titleBarColor;
    }

    public void setTitleBarColor(String titleBarColor2) {
        this.titleBarColor = titleBarColor2;
    }

    public String toString() {
        return "GuidePageBean{backColor='" + this.backColor + '\'' + ", title='" + this.title + '\'' + ", titleColor='" + this.titleColor + '\'' + ", titleBarColor='" + this.titleBarColor + '\'' + '}';
    }
}
