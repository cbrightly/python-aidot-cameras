package com.leedarson.event;

import com.leedarson.bean.RoiPoint;
import java.util.List;

public class GetRoiPointEvent {
    public int angle;
    public List<RoiPoint> points;

    public GetRoiPointEvent(int angle2, List<RoiPoint> points2) {
        this.points = points2;
        this.angle = angle2;
    }
}
