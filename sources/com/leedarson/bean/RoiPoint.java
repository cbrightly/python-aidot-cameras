package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class RoiPoint {
    public static ChangeQuickRedirect changeQuickRedirect;
    private XYpoint point1;
    private XYpoint point2;
    private XYpoint point3;
    private XYpoint point4;

    public XYpoint getPoint4() {
        return this.point4;
    }

    public void setPoint4(XYpoint point42) {
        this.point4 = point42;
    }

    public XYpoint getPoint1() {
        return this.point1;
    }

    public void setPoint1(XYpoint point12) {
        this.point1 = point12;
    }

    public XYpoint getPoint2() {
        return this.point2;
    }

    public void setPoint2(XYpoint point22) {
        this.point2 = point22;
    }

    public XYpoint getPoint3() {
        return this.point3;
    }

    public void setPoint3(XYpoint point32) {
        this.point3 = point32;
    }
}
