package com.leedarson.hummandetect;

public class DetectRoi {
    public int height;
    public int result;
    public int width;
    public int x;
    public int y;

    public DetectRoi(int result2, int x2, int y2, int width2, int height2) {
        this.result = result2;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
    }
}
