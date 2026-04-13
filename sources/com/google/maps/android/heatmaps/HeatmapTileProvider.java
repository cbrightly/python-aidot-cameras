package com.google.maps.android.heatmaps;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.collection.LongSparseArray;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.quadtree.PointQuadTree;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HeatmapTileProvider implements TileProvider {
    public static final Gradient DEFAULT_GRADIENT;
    private static final int[] DEFAULT_GRADIENT_COLORS;
    private static final float[] DEFAULT_GRADIENT_START_POINTS;
    private static final int DEFAULT_MAX_ZOOM = 11;
    private static final int DEFAULT_MIN_ZOOM = 5;
    public static final double DEFAULT_OPACITY = 0.7d;
    public static final int DEFAULT_RADIUS = 20;
    private static final int MAX_RADIUS = 50;
    private static final int MAX_ZOOM_LEVEL = 22;
    private static final int MIN_RADIUS = 10;
    private static final int SCREEN_SIZE = 1280;
    private static final int TILE_DIM = 512;
    static final double WORLD_WIDTH = 1.0d;
    private Bounds mBounds;
    private int[] mColorMap;
    private double mCustomMaxIntensity;
    private Collection<WeightedLatLng> mData;
    private Gradient mGradient;
    private double[] mKernel;
    private double[] mMaxIntensity;
    private double mOpacity;
    private int mRadius;
    private PointQuadTree<WeightedLatLng> mTree;

    static {
        int[] iArr = {Color.rgb(102, 225, 0), Color.rgb(255, 0, 0)};
        DEFAULT_GRADIENT_COLORS = iArr;
        float[] fArr = {0.2f, 1.0f};
        DEFAULT_GRADIENT_START_POINTS = fArr;
        DEFAULT_GRADIENT = new Gradient(iArr, fArr);
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Collection<WeightedLatLng> data;
        /* access modifiers changed from: private */
        public Gradient gradient = HeatmapTileProvider.DEFAULT_GRADIENT;
        /* access modifiers changed from: private */
        public double intensity = 0.0d;
        /* access modifiers changed from: private */
        public double opacity = 0.7d;
        /* access modifiers changed from: private */
        public int radius = 20;

        public Builder data(Collection<LatLng> val) {
            return weightedData(HeatmapTileProvider.wrapData(val));
        }

        public Builder weightedData(Collection<WeightedLatLng> val) {
            this.data = val;
            if (!val.isEmpty()) {
                return this;
            }
            throw new IllegalArgumentException("No input points.");
        }

        public Builder radius(int val) {
            this.radius = val;
            if (val >= 10 && val <= 50) {
                return this;
            }
            throw new IllegalArgumentException("Radius not within bounds.");
        }

        public Builder gradient(Gradient val) {
            this.gradient = val;
            return this;
        }

        public Builder opacity(double val) {
            this.opacity = val;
            if (val >= 0.0d && val <= 1.0d) {
                return this;
            }
            throw new IllegalArgumentException("Opacity must be in range [0, 1]");
        }

        public Builder maxIntensity(double val) {
            this.intensity = val;
            return this;
        }

        public HeatmapTileProvider build() {
            if (this.data != null) {
                return new HeatmapTileProvider(this);
            }
            throw new IllegalStateException("No input data: you must use either .data or .weightedData before building");
        }
    }

    private HeatmapTileProvider(Builder builder) {
        this.mData = builder.data;
        this.mRadius = builder.radius;
        this.mGradient = builder.gradient;
        this.mOpacity = builder.opacity;
        this.mCustomMaxIntensity = builder.intensity;
        int i = this.mRadius;
        this.mKernel = generateKernel(i, ((double) i) / 3.0d);
        setGradient(this.mGradient);
        setWeightedData(this.mData);
    }

    public void setWeightedData(Collection<WeightedLatLng> data) {
        this.mData = data;
        if (!data.isEmpty()) {
            Bounds bounds = getBounds(this.mData);
            this.mBounds = bounds;
            this.mTree = new PointQuadTree<>(bounds);
            for (WeightedLatLng l : this.mData) {
                this.mTree.add(l);
            }
            this.mMaxIntensity = getMaxIntensities(this.mRadius);
            return;
        }
        throw new IllegalArgumentException("No input points.");
    }

    public void setData(Collection<LatLng> data) {
        setWeightedData(wrapData(data));
    }

    /* access modifiers changed from: private */
    public static Collection<WeightedLatLng> wrapData(Collection<LatLng> data) {
        ArrayList<WeightedLatLng> weightedData = new ArrayList<>();
        for (LatLng l : data) {
            weightedData.add(new WeightedLatLng(l));
        }
        return weightedData;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x00bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.maps.model.Tile getTile(int r41, int r42, int r43) {
        /*
            r40 = this;
            r0 = r40
            r1 = r41
            r2 = r42
            r3 = r43
            double r4 = (double) r3
            r6 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r4 = java.lang.Math.pow(r6, r4)
            r8 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r4 = r8 / r4
            int r10 = r0.mRadius
            double r11 = (double) r10
            double r11 = r11 * r4
            r13 = 4647714815446351872(0x4080000000000000, double:512.0)
            double r11 = r11 / r13
            double r6 = r6 * r11
            double r6 = r6 + r4
            r13 = 2
            int r10 = r10 * r13
            int r10 = r10 + 512
            double r14 = (double) r10
            double r14 = r6 / r14
            r16 = r14
            double r13 = (double) r1
            double r13 = r13 * r4
            double r13 = r13 - r11
            int r15 = r1 + 1
            double r8 = (double) r15
            double r8 = r8 * r4
            double r8 = r8 + r11
            r27 = r6
            double r6 = (double) r2
            double r6 = r6 * r4
            double r6 = r6 - r11
            int r15 = r2 + 1
            double r1 = (double) r15
            double r1 = r1 * r4
            double r1 = r1 + r11
            r20 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            r22 = 0
            int r22 = (r13 > r22 ? 1 : (r13 == r22 ? 0 : -1))
            if (r22 >= 0) goto L_0x0064
            com.google.maps.android.geometry.Bounds r22 = new com.google.maps.android.geometry.Bounds
            r18 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r30 = r13 + r18
            r32 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r29 = r22
            r34 = r6
            r36 = r1
            r29.<init>(r30, r32, r34, r36)
            r18 = r22
            r20 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            com.google.maps.android.quadtree.PointQuadTree<com.google.maps.android.heatmaps.WeightedLatLng> r10 = r0.mTree
            r38 = r4
            r4 = r18
            java.util.Collection r15 = r10.search(r4)
            goto L_0x0087
        L_0x0064:
            r38 = r4
            r4 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r10 <= 0) goto L_0x0087
            com.google.maps.android.geometry.Bounds r10 = new com.google.maps.android.geometry.Bounds
            r30 = 0
            double r32 = r8 - r4
            r29 = r10
            r34 = r6
            r36 = r1
            r29.<init>(r30, r32, r34, r36)
            r4 = r10
            r20 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            com.google.maps.android.quadtree.PointQuadTree<com.google.maps.android.heatmaps.WeightedLatLng> r5 = r0.mTree
            java.util.Collection r15 = r5.search(r4)
            r4 = r20
            goto L_0x0089
        L_0x0087:
            r4 = r20
        L_0x0089:
            com.google.maps.android.geometry.Bounds r10 = new com.google.maps.android.geometry.Bounds
            r18 = r10
            r19 = r13
            r21 = r8
            r23 = r6
            r25 = r1
            r18.<init>(r19, r21, r23, r25)
            com.google.maps.android.geometry.Bounds r29 = new com.google.maps.android.geometry.Bounds
            r30 = r1
            com.google.maps.android.geometry.Bounds r1 = r0.mBounds
            r32 = r8
            double r8 = r1.minX
            double r19 = r8 - r11
            double r8 = r1.maxX
            double r21 = r8 + r11
            double r8 = r1.minY
            double r23 = r8 - r11
            double r1 = r1.maxY
            double r25 = r1 + r11
            r18 = r29
            r18.<init>(r19, r21, r23, r25)
            r1 = r29
            boolean r2 = r10.intersects(r1)
            if (r2 != 0) goto L_0x00c0
            com.google.android.gms.maps.model.Tile r2 = com.google.android.gms.maps.model.TileProvider.NO_TILE
            return r2
        L_0x00c0:
            com.google.maps.android.quadtree.PointQuadTree<com.google.maps.android.heatmaps.WeightedLatLng> r2 = r0.mTree
            java.util.Collection r2 = r2.search(r10)
            boolean r8 = r2.isEmpty()
            if (r8 == 0) goto L_0x00cf
            com.google.android.gms.maps.model.Tile r8 = com.google.android.gms.maps.model.TileProvider.NO_TILE
            return r8
        L_0x00cf:
            int r8 = r0.mRadius
            int r9 = r8 * 2
            int r9 = r9 + 512
            r18 = r1
            r1 = 2
            int r8 = r8 * r1
            int r8 = r8 + 512
            int[] r1 = new int[r1]
            r19 = 1
            r1[r19] = r8
            r8 = 0
            r1[r8] = r9
            java.lang.Class<double> r8 = double.class
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r8, r1)
            double[][] r1 = (double[][]) r1
            java.util.Iterator r8 = r2.iterator()
        L_0x00f0:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0126
            java.lang.Object r9 = r8.next()
            com.google.maps.android.heatmaps.WeightedLatLng r9 = (com.google.maps.android.heatmaps.WeightedLatLng) r9
            r19 = r2
            com.google.maps.android.geometry.Point r2 = r9.getPoint()
            r20 = r11
            r12 = r10
            double r10 = r2.x
            double r10 = r10 - r13
            double r10 = r10 / r16
            int r10 = (int) r10
            r22 = r12
            double r11 = r2.y
            double r11 = r11 - r6
            double r11 = r11 / r16
            int r11 = (int) r11
            r12 = r1[r10]
            r23 = r12[r11]
            double r25 = r9.getIntensity()
            double r23 = r23 + r25
            r12[r11] = r23
            r2 = r19
            r11 = r20
            r10 = r22
            goto L_0x00f0
        L_0x0126:
            r19 = r2
            r22 = r10
            r20 = r11
            java.util.Iterator r2 = r15.iterator()
        L_0x0130:
            boolean r8 = r2.hasNext()
            if (r8 == 0) goto L_0x015a
            java.lang.Object r8 = r2.next()
            com.google.maps.android.heatmaps.WeightedLatLng r8 = (com.google.maps.android.heatmaps.WeightedLatLng) r8
            com.google.maps.android.geometry.Point r9 = r8.getPoint()
            double r10 = r9.x
            double r10 = r10 + r4
            double r10 = r10 - r13
            double r10 = r10 / r16
            int r10 = (int) r10
            double r11 = r9.y
            double r11 = r11 - r6
            double r11 = r11 / r16
            int r11 = (int) r11
            r12 = r1[r10]
            r23 = r12[r11]
            double r25 = r8.getIntensity()
            double r23 = r23 + r25
            r12[r11] = r23
            goto L_0x0130
        L_0x015a:
            double[] r2 = r0.mKernel
            double[][] r2 = convolve(r1, r2)
            int[] r8 = r0.mColorMap
            double[] r9 = r0.mMaxIntensity
            r10 = r9[r3]
            android.graphics.Bitmap r8 = colorize(r2, r8, r10)
            com.google.android.gms.maps.model.Tile r9 = convertBitmap(r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.heatmaps.HeatmapTileProvider.getTile(int, int, int):com.google.android.gms.maps.model.Tile");
    }

    public void setGradient(Gradient gradient) {
        this.mGradient = gradient;
        this.mColorMap = gradient.generateColorMap(this.mOpacity);
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        this.mKernel = generateKernel(radius, ((double) radius) / 3.0d);
        this.mMaxIntensity = getMaxIntensities(this.mRadius);
    }

    public void setOpacity(double opacity) {
        this.mOpacity = opacity;
        setGradient(this.mGradient);
    }

    public void setMaxIntensity(double intensity) {
        this.mCustomMaxIntensity = intensity;
        setWeightedData(this.mData);
    }

    private double[] getMaxIntensities(int radius) {
        double[] maxIntensityArray = new double[22];
        if (this.mCustomMaxIntensity != 0.0d) {
            for (int i = 0; i < 22; i++) {
                maxIntensityArray[i] = this.mCustomMaxIntensity;
            }
            return maxIntensityArray;
        }
        for (int i2 = 5; i2 < 11; i2++) {
            maxIntensityArray[i2] = getMaxValue(this.mData, this.mBounds, radius, (int) (Math.pow(2.0d, (double) (i2 - 3)) * 1280.0d));
            if (i2 == 5) {
                for (int j = 0; j < i2; j++) {
                    maxIntensityArray[j] = maxIntensityArray[i2];
                }
            }
        }
        for (int i3 = 11; i3 < 22; i3++) {
            maxIntensityArray[i3] = maxIntensityArray[10];
        }
        return maxIntensityArray;
    }

    private static Tile convertBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return new Tile(512, 512, stream.toByteArray());
    }

    static Bounds getBounds(Collection<WeightedLatLng> points) {
        Iterator<WeightedLatLng> iter = points.iterator();
        WeightedLatLng first = iter.next();
        double minX = first.getPoint().x;
        double maxX = first.getPoint().x;
        double minY = first.getPoint().y;
        double maxY = first.getPoint().y;
        while (iter.hasNext()) {
            WeightedLatLng l = iter.next();
            double x = l.getPoint().x;
            double y = l.getPoint().y;
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        return new Bounds(minX, maxX, minY, maxY);
    }

    static double[] generateKernel(int radius, double sd) {
        double[] kernel = new double[((radius * 2) + 1)];
        for (int i = -radius; i <= radius; i++) {
            kernel[i + radius] = Math.exp(((double) ((-i) * i)) / ((2.0d * sd) * sd));
        }
        return kernel;
    }

    static double[][] convolve(double[][] grid, double[] kernel) {
        double[][] dArr = grid;
        double[] dArr2 = kernel;
        Class<double> cls = double.class;
        int radius = (int) Math.floor(((double) dArr2.length) / 2.0d);
        int dimOld = dArr.length;
        int dim = dimOld - (radius * 2);
        int lowerLimit = radius;
        int i = 1;
        int upperLimit = (radius + dim) - 1;
        int[] iArr = new int[2];
        iArr[1] = dimOld;
        iArr[0] = dimOld;
        double[][] intermediate = (double[][]) Array.newInstance(cls, iArr);
        int x = 0;
        while (true) {
            double d = 0.0d;
            if (x >= dimOld) {
                break;
            }
            int y = 0;
            while (y < dimOld) {
                double val = dArr[x][y];
                if (val != d) {
                    int xUpperLimit = (upperLimit < x + radius ? upperLimit : x + radius) + 1;
                    for (int x2 = lowerLimit > x - radius ? lowerLimit : x - radius; x2 < xUpperLimit; x2++) {
                        double[] dArr3 = intermediate[x2];
                        dArr3[y] = dArr3[y] + (dArr2[x2 - (x - radius)] * val);
                    }
                }
                y++;
                d = 0.0d;
            }
            x++;
        }
        int[] iArr2 = new int[2];
        iArr2[1] = dim;
        iArr2[0] = dim;
        double[][] outputGrid = (double[][]) Array.newInstance(cls, iArr2);
        int x3 = lowerLimit;
        while (x3 < upperLimit + 1) {
            int y2 = 0;
            while (y2 < dimOld) {
                double val2 = intermediate[x3][y2];
                if (val2 != 0.0d) {
                    int yUpperLimit = (upperLimit < y2 + radius ? upperLimit : y2 + radius) + i;
                    for (int y22 = lowerLimit > y2 - radius ? lowerLimit : y2 - radius; y22 < yUpperLimit; y22++) {
                        double[] dArr4 = outputGrid[x3 - radius];
                        int i2 = y22 - radius;
                        dArr4[i2] = dArr4[i2] + (dArr2[y22 - (y2 - radius)] * val2);
                    }
                }
                y2++;
                i = 1;
            }
            x3++;
            i = 1;
        }
        return outputGrid;
    }

    static Bitmap colorize(double[][] grid, int[] colorMap, double max) {
        double[][] dArr = grid;
        int[] iArr = colorMap;
        int maxColor = iArr[iArr.length - 1];
        double colorMapScaling = ((double) (iArr.length - 1)) / max;
        int dim = dArr.length;
        int[] colors = new int[(dim * dim)];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                double val = dArr[j][i];
                int index = (i * dim) + j;
                int col = (int) (val * colorMapScaling);
                if (val == 0.0d) {
                    colors[index] = 0;
                } else if (col < iArr.length) {
                    colors[index] = iArr[col];
                } else {
                    colors[index] = maxColor;
                }
            }
        }
        Bitmap tile = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);
        tile.setPixels(colors, 0, dim, 0, 0, dim, dim);
        return tile;
    }

    static double getMaxValue(Collection<WeightedLatLng> points, Bounds bounds, int radius, int screenDim) {
        Bounds bounds2 = bounds;
        double minX = bounds2.minX;
        double maxX = bounds2.maxX;
        double minY = bounds2.minY;
        double y = bounds2.maxY;
        double scale = ((double) ((int) (((double) (screenDim / (radius * 2))) + 0.5d))) / (maxX - minX > y - minY ? maxX - minX : y - minY);
        LongSparseArray<LongSparseArray<Double>> buckets = new LongSparseArray<>();
        double max = 0.0d;
        for (WeightedLatLng l : points) {
            double maxX2 = maxX;
            double x = l.getPoint().x;
            double maxY = y;
            double minX2 = minX;
            int xBucket = (int) ((x - minX) * scale);
            int yBucket = (int) ((l.getPoint().y - minY) * scale);
            double d = x;
            LongSparseArray<Double> column = buckets.get((long) xBucket);
            if (column == null) {
                column = new LongSparseArray<>();
                buckets.put((long) xBucket, column);
            }
            Double value = column.get((long) yBucket);
            if (value == null) {
                value = Double.valueOf(0.0d);
            }
            Double value2 = Double.valueOf(value.doubleValue() + l.getIntensity());
            double minY2 = minY;
            column.put((long) yBucket, value2);
            if (value2.doubleValue() > max) {
                max = value2.doubleValue();
            }
            Bounds bounds3 = bounds;
            maxX = maxX2;
            y = maxY;
            minX = minX2;
            minY = minY2;
        }
        return max;
    }
}
