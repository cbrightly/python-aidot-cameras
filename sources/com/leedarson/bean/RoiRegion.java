package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.List;

public class RoiRegion {
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<String> point1;
    private List<String> point2;
    private List<String> point3;
    private List<String> point4;

    public List<String> getPoint4() {
        return this.point4;
    }

    public void setPoint4(List<String> point42) {
        this.point4 = point42;
    }

    public List<String> getPoint1() {
        return this.point1;
    }

    public void setPoint1(List<String> point12) {
        this.point1 = point12;
    }

    public List<String> getPoint2() {
        return this.point2;
    }

    public void setPoint2(List<String> point22) {
        this.point2 = point22;
    }

    public List<String> getPoint3() {
        return this.point3;
    }

    public void setPoint3(List<String> point32) {
        this.point3 = point32;
    }
}
