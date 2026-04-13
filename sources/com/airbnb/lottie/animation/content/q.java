package com.airbnb.lottie.animation.content;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.m;
import com.airbnb.lottie.model.content.n;
import com.airbnb.lottie.model.layer.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RoundedCornersContent */
public class q implements s, a.b {
    private final e0 a;
    private final String b;
    private final a<Float, Float> c;
    @Nullable
    private n d;

    public q(e0 lottieDrawable, b layer, m roundedCorners) {
        this.a = lottieDrawable;
        this.b = roundedCorners.c();
        a<Float, Float> j = roundedCorners.b().j();
        this.c = j;
        layer.g(j);
        j.a(this);
    }

    public void a() {
        this.a.invalidateSelf();
    }

    public void b(List<c> list, List<c> list2) {
    }

    public a<Float, Float> g() {
        return this.c;
    }

    public n c(n startingShapeData) {
        List<com.airbnb.lottie.model.a> list;
        boolean isEndOfCurve;
        float roundedness;
        int i;
        boolean isClosed;
        List<com.airbnb.lottie.model.a> a2 = startingShapeData.a();
        if (a2.size() <= 2) {
            return startingShapeData;
        }
        float roundedness2 = this.c.h().floatValue();
        if (roundedness2 == 0.0f) {
            return startingShapeData;
        }
        n modifiedShapeData = i(startingShapeData);
        modifiedShapeData.f(startingShapeData.b().x, startingShapeData.b().y);
        List<com.airbnb.lottie.model.a> a3 = modifiedShapeData.a();
        int modifiedCurvesIndex = 0;
        boolean isClosed2 = startingShapeData.d();
        int i2 = 0;
        while (i2 < a2.size()) {
            com.airbnb.lottie.model.a startingCurve = a2.get(i2);
            com.airbnb.lottie.model.a previousCurve = a2.get(e(i2 - 1, a2.size()));
            com.airbnb.lottie.model.a previousPreviousCurve = a2.get(e(i2 - 2, a2.size()));
            PointF vertex = (i2 != 0 || isClosed2) ? previousCurve.c() : startingShapeData.b();
            PointF inPoint = (i2 != 0 || isClosed2) ? previousCurve.b() : vertex;
            PointF outPoint = startingCurve.a();
            PointF previousVertex = previousPreviousCurve.c();
            PointF nextVertex = startingCurve.c();
            if (startingShapeData.d() || i2 != 0) {
                list = a2;
            } else {
                list = a2;
                if (i2 == a2.size() - 1) {
                    isEndOfCurve = true;
                    if (inPoint.equals(vertex) || outPoint.equals(vertex) || isEndOfCurve) {
                        roundedness = roundedness2;
                        isClosed = isClosed2;
                        i = i2;
                        com.airbnb.lottie.model.a startingCurve2 = startingCurve;
                        com.airbnb.lottie.model.a previousCurve2 = previousCurve;
                        com.airbnb.lottie.model.a aVar = previousPreviousCurve;
                        PointF pointF = vertex;
                        PointF pointF2 = inPoint;
                        com.airbnb.lottie.model.a previousCurveData = a3.get(e(modifiedCurvesIndex - 1, a3.size()));
                        previousCurveData.e(previousCurve2.b().x, previousCurve2.b().y);
                        previousCurveData.f(previousCurve2.c().x, previousCurve2.c().y);
                        a3.get(modifiedCurvesIndex).d(startingCurve2.a().x, startingCurve2.a().y);
                    } else {
                        boolean z = isEndOfCurve;
                        float f = vertex.x;
                        float dxToPreviousVertex = f - previousVertex.x;
                        isClosed = isClosed2;
                        float f2 = vertex.y;
                        com.airbnb.lottie.model.a aVar2 = previousPreviousCurve;
                        float dyToPreviousVertex = f2 - previousVertex.y;
                        PointF pointF3 = inPoint;
                        float dxToNextVertex = nextVertex.x - f;
                        float dyToNextVertex = nextVertex.y - f2;
                        com.airbnb.lottie.model.a aVar3 = previousCurve;
                        com.airbnb.lottie.model.a aVar4 = startingCurve;
                        i = i2;
                        float dToPreviousVertex = (float) Math.hypot((double) dxToPreviousVertex, (double) dyToPreviousVertex);
                        float f3 = dyToPreviousVertex;
                        float dToNextVertex = (float) Math.hypot((double) dxToNextVertex, (double) dyToNextVertex);
                        float previousVertexPercent = Math.min(roundedness2 / dToPreviousVertex, 0.5f);
                        float nextVertexPercent = Math.min(roundedness2 / dToNextVertex, 0.5f);
                        float f4 = vertex.x;
                        float f5 = dyToNextVertex;
                        float newVertex1X = ((previousVertex.x - f4) * previousVertexPercent) + f4;
                        float f6 = dxToPreviousVertex;
                        float dxToPreviousVertex2 = vertex.y;
                        roundedness = roundedness2;
                        float newVertex1Y = ((previousVertex.y - dxToPreviousVertex2) * previousVertexPercent) + dxToPreviousVertex2;
                        float f7 = dToPreviousVertex;
                        float newVertex2X = ((nextVertex.x - f4) * nextVertexPercent) + f4;
                        float f8 = dToNextVertex;
                        float newVertex2Y = ((nextVertex.y - dxToPreviousVertex2) * nextVertexPercent) + dxToPreviousVertex2;
                        float f9 = previousVertexPercent;
                        float previousVertexPercent2 = newVertex1X - ((newVertex1X - f4) * 0.5519f);
                        float f10 = nextVertexPercent;
                        float newVertex1OutPointY = newVertex1Y - ((newVertex1Y - dxToPreviousVertex2) * 0.5519f);
                        float newVertex2InPointX = newVertex2X - ((newVertex2X - f4) * 0.5519f);
                        float newVertex2InPointY = newVertex2Y - ((newVertex2Y - dxToPreviousVertex2) * 0.5519f);
                        PointF pointF4 = vertex;
                        float f11 = dxToNextVertex;
                        com.airbnb.lottie.model.a previousCurveData2 = a3.get(e(modifiedCurvesIndex - 1, a3.size()));
                        com.airbnb.lottie.model.a currentCurveData = a3.get(modifiedCurvesIndex);
                        previousCurveData2.e(newVertex1X, newVertex1Y);
                        previousCurveData2.f(newVertex1X, newVertex1Y);
                        if (i == 0) {
                            modifiedShapeData.f(newVertex1X, newVertex1Y);
                        }
                        currentCurveData.d(previousVertexPercent2, newVertex1OutPointY);
                        modifiedCurvesIndex++;
                        com.airbnb.lottie.model.a previousCurveData3 = currentCurveData;
                        previousCurveData3.e(newVertex2InPointX, newVertex2InPointY);
                        previousCurveData3.f(newVertex2X, newVertex2Y);
                        a3.get(modifiedCurvesIndex).d(newVertex2X, newVertex2Y);
                    }
                    modifiedCurvesIndex++;
                    i2 = i + 1;
                    a2 = list;
                    isClosed2 = isClosed;
                    roundedness2 = roundedness;
                }
            }
            isEndOfCurve = false;
            if (inPoint.equals(vertex) && outPoint.equals(vertex)) {
            }
            roundedness = roundedness2;
            isClosed = isClosed2;
            i = i2;
            com.airbnb.lottie.model.a startingCurve22 = startingCurve;
            com.airbnb.lottie.model.a previousCurve22 = previousCurve;
            com.airbnb.lottie.model.a aVar5 = previousPreviousCurve;
            PointF pointF5 = vertex;
            PointF pointF22 = inPoint;
            com.airbnb.lottie.model.a previousCurveData4 = a3.get(e(modifiedCurvesIndex - 1, a3.size()));
            previousCurveData4.e(previousCurve22.b().x, previousCurve22.b().y);
            previousCurveData4.f(previousCurve22.c().x, previousCurve22.c().y);
            a3.get(modifiedCurvesIndex).d(startingCurve22.a().x, startingCurve22.a().y);
            modifiedCurvesIndex++;
            i2 = i + 1;
            a2 = list;
            isClosed2 = isClosed;
            roundedness2 = roundedness;
        }
        return modifiedShapeData;
    }

    @NonNull
    private n i(n startingShapeData) {
        List<com.airbnb.lottie.model.a> a2 = startingShapeData.a();
        boolean isClosed = startingShapeData.d();
        int vertices = 0;
        int i = a2.size() - 1;
        while (true) {
            boolean isEndOfCurve = false;
            if (i < 0) {
                break;
            }
            com.airbnb.lottie.model.a startingCurve = a2.get(i);
            com.airbnb.lottie.model.a previousCurve = a2.get(e(i - 1, a2.size()));
            PointF vertex = (i != 0 || isClosed) ? previousCurve.c() : startingShapeData.b();
            PointF inPoint = (i != 0 || isClosed) ? previousCurve.b() : vertex;
            PointF outPoint = startingCurve.a();
            if (!startingShapeData.d() && i == 0 && i == a2.size() - 1) {
                isEndOfCurve = true;
            }
            if (!inPoint.equals(vertex) || !outPoint.equals(vertex) || isEndOfCurve) {
                vertices++;
            } else {
                vertices += 2;
            }
            i--;
        }
        n nVar = this.d;
        if (nVar == null || nVar.a().size() != vertices) {
            List<CubicCurveData> newCurves = new ArrayList<>(vertices);
            for (int i2 = 0; i2 < vertices; i2++) {
                newCurves.add(new com.airbnb.lottie.model.a());
            }
            this.d = new n(new PointF(0.0f, 0.0f), false, newCurves);
        }
        this.d.e(isClosed);
        return this.d;
    }

    private static int e(int x, int y) {
        return x - (d(x, y) * y);
    }

    private static int d(int x, int y) {
        int r = x / y;
        if ((x ^ y) >= 0 || r * y == x) {
            return r;
        }
        return r - 1;
    }
}
