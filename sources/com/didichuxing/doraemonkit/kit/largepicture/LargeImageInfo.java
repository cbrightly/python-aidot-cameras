package com.didichuxing.doraemonkit.kit.largepicture;

public class LargeImageInfo {
    private double fileSize;
    private String framework = "other";
    private int height;
    private double memorySize;
    private String url;
    private int width;

    public void setUrl(String url2) {
        this.url = url2;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(double fileSize2) {
        this.fileSize = fileSize2;
    }

    public void setMemorySize(double memorySize2) {
        this.memorySize = memorySize2;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public String getUrl() {
        return this.url;
    }

    public double getMemorySize() {
        return this.memorySize;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getFramework() {
        return this.framework;
    }

    public void setFramework(String framework2) {
        this.framework = framework2;
    }

    public String toString() {
        return "LargeImageInfo{framework='" + this.framework + '\'' + ", url='" + this.url + '\'' + ", fileSize='" + this.fileSize + '\'' + ", memorySize='" + this.memorySize + '\'' + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
