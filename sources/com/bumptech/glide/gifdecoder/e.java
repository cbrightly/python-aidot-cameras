package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: StandardGifDecoder */
public class e implements a {
    private static final String a = e.class.getSimpleName();
    @ColorInt
    private int[] b;
    @ColorInt
    private final int[] c;
    private final a.C0022a d;
    private ByteBuffer e;
    private byte[] f;
    private short[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    @ColorInt
    private int[] k;
    private int l;
    private c m;
    private Bitmap n;
    private boolean o;
    private int p;
    private int q;
    private int r;
    private int s;
    @Nullable
    private Boolean t;
    @NonNull
    private Bitmap.Config u;

    public e(@NonNull a.C0022a provider, c gifHeader, ByteBuffer rawData, int sampleSize) {
        this(provider);
        p(gifHeader, rawData, sampleSize);
    }

    public e(@NonNull a.C0022a provider) {
        this.c = new int[256];
        this.u = Bitmap.Config.ARGB_8888;
        this.d = provider;
        this.m = new c();
    }

    @NonNull
    public ByteBuffer getData() {
        return this.e;
    }

    public void a() {
        this.l = (this.l + 1) % this.m.c;
    }

    public int l(int n2) {
        if (n2 < 0) {
            return -1;
        }
        c cVar = this.m;
        if (n2 < cVar.c) {
            return cVar.e.get(n2).i;
        }
        return -1;
    }

    public int d() {
        int i2;
        if (this.m.c <= 0 || (i2 = this.l) < 0) {
            return 0;
        }
        return l(i2);
    }

    public int b() {
        return this.m.c;
    }

    public int f() {
        return this.l;
    }

    public void e() {
        this.l = -1;
    }

    public int g() {
        return this.e.limit() + this.j.length + (this.k.length * 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e5, code lost:
        return null;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.graphics.Bitmap getNextFrame() {
        /*
            r9 = this;
            monitor-enter(r9)
            com.bumptech.glide.gifdecoder.c r0 = r9.m     // Catch:{ all -> 0x00e6 }
            int r0 = r0.c     // Catch:{ all -> 0x00e6 }
            r1 = 3
            r2 = 1
            if (r0 <= 0) goto L_0x000d
            int r0 = r9.l     // Catch:{ all -> 0x00e6 }
            if (r0 >= 0) goto L_0x0039
        L_0x000d:
            java.lang.String r0 = a     // Catch:{ all -> 0x00e6 }
            boolean r3 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00e6 }
            if (r3 == 0) goto L_0x0037
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e6 }
            r3.<init>()     // Catch:{ all -> 0x00e6 }
            java.lang.String r4 = "Unable to decode frame, frameCount="
            r3.append(r4)     // Catch:{ all -> 0x00e6 }
            com.bumptech.glide.gifdecoder.c r4 = r9.m     // Catch:{ all -> 0x00e6 }
            int r4 = r4.c     // Catch:{ all -> 0x00e6 }
            r3.append(r4)     // Catch:{ all -> 0x00e6 }
            java.lang.String r4 = ", framePointer="
            r3.append(r4)     // Catch:{ all -> 0x00e6 }
            int r4 = r9.l     // Catch:{ all -> 0x00e6 }
            r3.append(r4)     // Catch:{ all -> 0x00e6 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00e6 }
            android.util.Log.d(r0, r3)     // Catch:{ all -> 0x00e6 }
        L_0x0037:
            r9.p = r2     // Catch:{ all -> 0x00e6 }
        L_0x0039:
            int r0 = r9.p     // Catch:{ all -> 0x00e6 }
            r3 = 0
            if (r0 == r2) goto L_0x00c6
            r4 = 2
            if (r0 != r4) goto L_0x0043
            goto L_0x00c6
        L_0x0043:
            r0 = 0
            r9.p = r0     // Catch:{ all -> 0x00e6 }
            byte[] r5 = r9.f     // Catch:{ all -> 0x00e6 }
            if (r5 != 0) goto L_0x0054
            com.bumptech.glide.gifdecoder.a$a r5 = r9.d     // Catch:{ all -> 0x00e6 }
            r6 = 255(0xff, float:3.57E-43)
            byte[] r5 = r5.b(r6)     // Catch:{ all -> 0x00e6 }
            r9.f = r5     // Catch:{ all -> 0x00e6 }
        L_0x0054:
            com.bumptech.glide.gifdecoder.c r5 = r9.m     // Catch:{ all -> 0x00e6 }
            java.util.List<com.bumptech.glide.gifdecoder.b> r5 = r5.e     // Catch:{ all -> 0x00e6 }
            int r6 = r9.l     // Catch:{ all -> 0x00e6 }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ all -> 0x00e6 }
            com.bumptech.glide.gifdecoder.b r5 = (com.bumptech.glide.gifdecoder.b) r5     // Catch:{ all -> 0x00e6 }
            r6 = 0
            int r7 = r9.l     // Catch:{ all -> 0x00e6 }
            int r7 = r7 - r2
            if (r7 < 0) goto L_0x0071
            com.bumptech.glide.gifdecoder.c r8 = r9.m     // Catch:{ all -> 0x00e6 }
            java.util.List<com.bumptech.glide.gifdecoder.b> r8 = r8.e     // Catch:{ all -> 0x00e6 }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x00e6 }
            com.bumptech.glide.gifdecoder.b r8 = (com.bumptech.glide.gifdecoder.b) r8     // Catch:{ all -> 0x00e6 }
            r6 = r8
        L_0x0071:
            int[] r8 = r5.k     // Catch:{ all -> 0x00e6 }
            if (r8 == 0) goto L_0x0076
            goto L_0x007a
        L_0x0076:
            com.bumptech.glide.gifdecoder.c r8 = r9.m     // Catch:{ all -> 0x00e6 }
            int[] r8 = r8.a     // Catch:{ all -> 0x00e6 }
        L_0x007a:
            r9.b = r8     // Catch:{ all -> 0x00e6 }
            if (r8 != 0) goto L_0x00a0
            java.lang.String r0 = a     // Catch:{ all -> 0x00e6 }
            boolean r1 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00e6 }
            if (r1 == 0) goto L_0x009c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e6 }
            r1.<init>()     // Catch:{ all -> 0x00e6 }
            java.lang.String r4 = "No valid color table found for frame #"
            r1.append(r4)     // Catch:{ all -> 0x00e6 }
            int r4 = r9.l     // Catch:{ all -> 0x00e6 }
            r1.append(r4)     // Catch:{ all -> 0x00e6 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00e6 }
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x00e6 }
        L_0x009c:
            r9.p = r2     // Catch:{ all -> 0x00e6 }
            monitor-exit(r9)
            return r3
        L_0x00a0:
            boolean r1 = r5.f     // Catch:{ all -> 0x00e6 }
            if (r1 == 0) goto L_0x00c0
            int[] r1 = r9.c     // Catch:{ all -> 0x00e6 }
            int r3 = r8.length     // Catch:{ all -> 0x00e6 }
            java.lang.System.arraycopy(r8, r0, r1, r0, r3)     // Catch:{ all -> 0x00e6 }
            int[] r1 = r9.c     // Catch:{ all -> 0x00e6 }
            r9.b = r1     // Catch:{ all -> 0x00e6 }
            int r3 = r5.h     // Catch:{ all -> 0x00e6 }
            r1[r3] = r0     // Catch:{ all -> 0x00e6 }
            int r0 = r5.g     // Catch:{ all -> 0x00e6 }
            if (r0 != r4) goto L_0x00c0
            int r0 = r9.l     // Catch:{ all -> 0x00e6 }
            if (r0 != 0) goto L_0x00c0
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x00e6 }
            r9.t = r0     // Catch:{ all -> 0x00e6 }
        L_0x00c0:
            android.graphics.Bitmap r0 = r9.q(r5, r6)     // Catch:{ all -> 0x00e6 }
            monitor-exit(r9)
            return r0
        L_0x00c6:
            java.lang.String r0 = a     // Catch:{ all -> 0x00e6 }
            boolean r1 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00e6 }
            if (r1 == 0) goto L_0x00e4
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e6 }
            r1.<init>()     // Catch:{ all -> 0x00e6 }
            java.lang.String r2 = "Unable to decode frame, status="
            r1.append(r2)     // Catch:{ all -> 0x00e6 }
            int r2 = r9.p     // Catch:{ all -> 0x00e6 }
            r1.append(r2)     // Catch:{ all -> 0x00e6 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00e6 }
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x00e6 }
        L_0x00e4:
            monitor-exit(r9)
            return r3
        L_0x00e6:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.e.getNextFrame():android.graphics.Bitmap");
    }

    public void clear() {
        this.m = null;
        byte[] bArr = this.j;
        if (bArr != null) {
            this.d.e(bArr);
        }
        int[] iArr = this.k;
        if (iArr != null) {
            this.d.f(iArr);
        }
        Bitmap bitmap = this.n;
        if (bitmap != null) {
            this.d.a(bitmap);
        }
        this.n = null;
        this.e = null;
        this.t = null;
        byte[] bArr2 = this.f;
        if (bArr2 != null) {
            this.d.e(bArr2);
        }
    }

    public synchronized void p(@NonNull c header, @NonNull ByteBuffer buffer, int sampleSize) {
        if (sampleSize > 0) {
            int sampleSize2 = Integer.highestOneBit(sampleSize);
            this.p = 0;
            this.m = header;
            this.l = -1;
            ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
            this.e = asReadOnlyBuffer;
            asReadOnlyBuffer.position(0);
            this.e.order(ByteOrder.LITTLE_ENDIAN);
            this.o = false;
            Iterator<b> it = header.e.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().g == 3) {
                    this.o = true;
                    break;
                }
            }
            this.q = sampleSize2;
            int i2 = header.f;
            this.s = i2 / sampleSize2;
            int i3 = header.g;
            this.r = i3 / sampleSize2;
            this.j = this.d.b(i2 * i3);
            this.k = this.d.d(this.s * this.r);
        } else {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + sampleSize);
        }
    }

    public void c(@NonNull Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.u = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    private Bitmap q(b currentFrame, b previousFrame) {
        int i2;
        int i3;
        Bitmap bitmap;
        int[] dest = this.k;
        if (previousFrame == null) {
            Bitmap bitmap2 = this.n;
            if (bitmap2 != null) {
                this.d.a(bitmap2);
            }
            this.n = null;
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && previousFrame.g == 3 && this.n == null) {
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && (i3 = previousFrame.g) > 0) {
            if (i3 == 2) {
                int c2 = 0;
                if (!currentFrame.f) {
                    c cVar = this.m;
                    c2 = cVar.l;
                    if (currentFrame.k != null && cVar.j == currentFrame.h) {
                        c2 = 0;
                    }
                }
                int i4 = previousFrame.d;
                int i5 = this.q;
                int downsampledIH = i4 / i5;
                int downsampledIY = previousFrame.b / i5;
                int downsampledIW = previousFrame.c / i5;
                int downsampledIX = previousFrame.a / i5;
                int i6 = this.s;
                int topLeft = (downsampledIY * i6) + downsampledIX;
                int bottomLeft = (i6 * downsampledIH) + topLeft;
                int left = topLeft;
                while (left < bottomLeft) {
                    int right = left + downsampledIW;
                    for (int pointer = left; pointer < right; pointer++) {
                        dest[pointer] = c2;
                    }
                    left += this.s;
                }
            } else if (i3 == 3 && (bitmap = this.n) != null) {
                int i7 = this.s;
                bitmap.getPixels(dest, 0, i7, 0, 0, i7, this.r);
            }
        }
        k(currentFrame);
        if (currentFrame.e || this.q != 1) {
            i(currentFrame);
        } else {
            j(currentFrame);
        }
        if (this.o && ((i2 = currentFrame.g) == 0 || i2 == 1)) {
            if (this.n == null) {
                this.n = m();
            }
            Bitmap bitmap3 = this.n;
            int i8 = this.s;
            bitmap3.setPixels(dest, 0, i8, 0, 0, i8, this.r);
        }
        Bitmap result = m();
        int i9 = this.s;
        result.setPixels(dest, 0, i9, 0, 0, i9, this.r);
        return result;
    }

    private void j(b currentFrame) {
        b bVar = currentFrame;
        int[] dest = this.k;
        int downsampledIH = bVar.d;
        int downsampledIY = bVar.b;
        int downsampledIW = bVar.c;
        int downsampledIX = bVar.a;
        boolean isFirstFrame = this.l == 0;
        int width = this.s;
        byte[] mainPixels = this.j;
        int[] act = this.b;
        byte transparentColorIndex = -1;
        int i2 = 0;
        while (i2 < downsampledIH) {
            int k2 = (i2 + downsampledIY) * width;
            int dx = k2 + downsampledIX;
            int dlim = dx + downsampledIW;
            if (k2 + width < dlim) {
                dlim = k2 + width;
            }
            int sx = bVar.c * i2;
            int dx2 = dx;
            while (dx2 < dlim) {
                int downsampledIH2 = downsampledIH;
                byte byteCurrentColorIndex = mainPixels[sx];
                int downsampledIY2 = downsampledIY;
                byte downsampledIY3 = byteCurrentColorIndex & 255;
                if (downsampledIY3 != transparentColorIndex) {
                    int color = act[downsampledIY3];
                    if (color != 0) {
                        dest[dx2] = color;
                    } else {
                        transparentColorIndex = byteCurrentColorIndex;
                    }
                }
                sx++;
                dx2++;
                downsampledIH = downsampledIH2;
                downsampledIY = downsampledIY2;
            }
            int i3 = downsampledIY;
            i2++;
            bVar = currentFrame;
        }
        int i4 = downsampledIY;
        Boolean bool = this.t;
        this.t = Boolean.valueOf((bool != null && bool.booleanValue()) || (this.t == null && isFirstFrame && transparentColorIndex != -1));
    }

    private void i(b currentFrame) {
        boolean z;
        int downsampledIW;
        int downsampledIY;
        b bVar = currentFrame;
        int[] dest = this.k;
        int i2 = bVar.d;
        int i3 = this.q;
        int downsampledIH = i2 / i3;
        int downsampledIY2 = bVar.b / i3;
        int downsampledIW2 = bVar.c / i3;
        int downsampledIX = bVar.a / i3;
        int iline = 0;
        boolean isFirstFrame = this.l == 0;
        int sampleSize = this.q;
        int downsampledWidth = this.s;
        int downsampledHeight = this.r;
        byte[] mainPixels = this.j;
        int[] act = this.b;
        int pass = 1;
        Boolean isFirstFrameTransparent = this.t;
        int inc = 8;
        int i4 = 0;
        while (i4 < downsampledIH) {
            int line = i4;
            boolean isFirstFrameTransparent2 = isFirstFrameTransparent;
            if (bVar.e) {
                if (iline >= downsampledIH) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            int line2 = line + downsampledIY2;
            int downsampledIH2 = downsampledIH;
            boolean isNotDownsampling = sampleSize == 1;
            if (line2 < downsampledHeight) {
                int k2 = line2 * downsampledWidth;
                int dx = k2 + downsampledIX;
                int i5 = line2;
                int dlim = dx + downsampledIW2;
                downsampledIY = downsampledIY2;
                if (k2 + downsampledWidth < dlim) {
                    dlim = k2 + downsampledWidth;
                }
                downsampledIW = downsampledIW2;
                int sx = i4 * sampleSize * bVar.c;
                if (isNotDownsampling) {
                    int dx2 = dx;
                    while (dx2 < dlim) {
                        boolean isNotDownsampling2 = isNotDownsampling;
                        int averageColor = act[mainPixels[sx] & 255];
                        if (averageColor != 0) {
                            dest[dx2] = averageColor;
                        } else if (isFirstFrame && isFirstFrameTransparent2 == null) {
                            isFirstFrameTransparent2 = true;
                        }
                        sx += sampleSize;
                        dx2++;
                        isNotDownsampling = isNotDownsampling2;
                    }
                    isFirstFrameTransparent = isFirstFrameTransparent2;
                } else {
                    int maxPositionInSource = ((dlim - dx) * sampleSize) + sx;
                    int dx3 = dx;
                    while (dx3 < dlim) {
                        int dlim2 = dlim;
                        int averageColor2 = h(sx, maxPositionInSource, bVar.c);
                        if (averageColor2 != 0) {
                            dest[dx3] = averageColor2;
                        } else if (isFirstFrame && isFirstFrameTransparent2 == null) {
                            isFirstFrameTransparent2 = true;
                        }
                        sx += sampleSize;
                        dx3++;
                        dlim = dlim2;
                    }
                    isFirstFrameTransparent = isFirstFrameTransparent2;
                }
            } else {
                int i6 = line2;
                downsampledIY = downsampledIY2;
                downsampledIW = downsampledIW2;
                isFirstFrameTransparent = isFirstFrameTransparent2;
            }
            i4++;
            downsampledIH = downsampledIH2;
            downsampledIY2 = downsampledIY;
            downsampledIW2 = downsampledIW;
        }
        Boolean isFirstFrameTransparent3 = isFirstFrameTransparent;
        int i7 = downsampledIY2;
        int i8 = downsampledIW2;
        if (this.t == null) {
            if (isFirstFrameTransparent3 == null) {
                z = false;
            } else {
                z = isFirstFrameTransparent3.booleanValue();
            }
            this.t = Boolean.valueOf(z);
        }
    }

    @ColorInt
    private int h(int positionInMainPixels, int maxPositionInMainPixels, int currentFrameIw) {
        int alphaSum = 0;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int totalAdded = 0;
        for (int i2 = positionInMainPixels; i2 < this.q + positionInMainPixels; i2++) {
            byte[] bArr = this.j;
            if (i2 >= bArr.length || i2 >= maxPositionInMainPixels) {
                break;
            }
            int currentColor = this.b[bArr[i2] & 255];
            if (currentColor != 0) {
                alphaSum += (currentColor >> 24) & 255;
                redSum += (currentColor >> 16) & 255;
                greenSum += (currentColor >> 8) & 255;
                blueSum += currentColor & 255;
                totalAdded++;
            }
        }
        for (int i3 = positionInMainPixels + currentFrameIw; i3 < positionInMainPixels + currentFrameIw + this.q; i3++) {
            byte[] bArr2 = this.j;
            if (i3 >= bArr2.length || i3 >= maxPositionInMainPixels) {
                break;
            }
            int currentColor2 = this.b[bArr2[i3] & 255];
            if (currentColor2 != 0) {
                alphaSum += (currentColor2 >> 24) & 255;
                redSum += (currentColor2 >> 16) & 255;
                greenSum += (currentColor2 >> 8) & 255;
                blueSum += currentColor2 & 255;
                totalAdded++;
            }
        }
        if (totalAdded == 0) {
            return 0;
        }
        return ((alphaSum / totalAdded) << 24) | ((redSum / totalAdded) << 16) | ((greenSum / totalAdded) << 8) | (blueSum / totalAdded);
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [short[]] */
    /* JADX WARNING: type inference failed for: r0v19, types: [short] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k(com.bumptech.glide.gifdecoder.b r30) {
        /*
            r29 = this;
            r0 = r29
            r1 = r30
            if (r1 == 0) goto L_0x000d
            java.nio.ByteBuffer r2 = r0.e
            int r3 = r1.j
            r2.position(r3)
        L_0x000d:
            if (r1 != 0) goto L_0x0017
            com.bumptech.glide.gifdecoder.c r2 = r0.m
            int r3 = r2.f
            int r2 = r2.g
            int r3 = r3 * r2
            goto L_0x001c
        L_0x0017:
            int r2 = r1.c
            int r3 = r1.d
            int r3 = r3 * r2
        L_0x001c:
            r2 = r3
            byte[] r3 = r0.j
            if (r3 == 0) goto L_0x0024
            int r3 = r3.length
            if (r3 >= r2) goto L_0x002c
        L_0x0024:
            com.bumptech.glide.gifdecoder.a$a r3 = r0.d
            byte[] r3 = r3.b(r2)
            r0.j = r3
        L_0x002c:
            byte[] r3 = r0.j
            short[] r4 = r0.g
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 != 0) goto L_0x0038
            short[] r4 = new short[r5]
            r0.g = r4
        L_0x0038:
            short[] r4 = r0.g
            byte[] r6 = r0.h
            if (r6 != 0) goto L_0x0042
            byte[] r6 = new byte[r5]
            r0.h = r6
        L_0x0042:
            byte[] r6 = r0.h
            byte[] r7 = r0.i
            if (r7 != 0) goto L_0x004e
            r7 = 4097(0x1001, float:5.741E-42)
            byte[] r7 = new byte[r7]
            r0.i = r7
        L_0x004e:
            byte[] r7 = r0.i
            int r8 = r29.o()
            r9 = 1
            int r10 = r9 << r8
            int r11 = r10 + 1
            int r12 = r10 + 2
            r13 = -1
            int r14 = r8 + 1
            int r15 = r9 << r14
            int r15 = r15 - r9
            r16 = 0
            r5 = r16
        L_0x0065:
            r9 = 0
            if (r5 >= r10) goto L_0x0071
            r4[r5] = r9
            byte r9 = (byte) r5
            r6[r5] = r9
            int r5 = r5 + 1
            r9 = 1
            goto L_0x0065
        L_0x0071:
            byte[] r1 = r0.f
            r17 = r9
            r18 = r9
            r19 = r9
            r20 = r9
            r21 = r9
            r22 = r9
            r23 = r9
            r24 = r9
            r28 = r24
            r24 = r5
            r5 = r28
        L_0x0089:
            if (r5 >= r2) goto L_0x0164
            if (r21 != 0) goto L_0x00a1
            int r21 = r29.n()
            if (r21 > 0) goto L_0x009c
            r25 = r5
            r5 = 3
            r0.p = r5
            r27 = r1
            goto L_0x0168
        L_0x009c:
            r25 = r5
            r17 = 0
            goto L_0x00a3
        L_0x00a1:
            r25 = r5
        L_0x00a3:
            byte r5 = r1[r17]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << r22
            int r23 = r23 + r5
            int r22 = r22 + 8
            r5 = 1
            int r17 = r17 + 1
            r5 = -1
            int r21 = r21 + -1
            r26 = r20
            r5 = r22
        L_0x00b7:
            if (r5 < r14) goto L_0x0154
            r0 = r23 & r15
            int r23 = r23 >> r14
            int r5 = r5 - r14
            if (r0 != r10) goto L_0x00d0
            int r14 = r8 + 1
            r16 = 1
            int r20 = r16 << r14
            int r15 = r20 + -1
            int r12 = r10 + 2
            r13 = -1
            r24 = r0
            r0 = r29
            goto L_0x00b7
        L_0x00d0:
            r16 = 1
            if (r0 != r11) goto L_0x00df
            r24 = r0
            r22 = r5
            r5 = r25
            r20 = r26
            r0 = r29
            goto L_0x0089
        L_0x00df:
            r27 = r1
            r1 = -1
            if (r13 != r1) goto L_0x00f6
            byte r20 = r6[r0]
            r3[r9] = r20
            int r9 = r9 + 1
            int r25 = r25 + 1
            r13 = r0
            r26 = r0
            r24 = r0
            r1 = r27
            r0 = r29
            goto L_0x00b7
        L_0x00f6:
            r20 = r0
            if (r0 < r12) goto L_0x0105
            r24 = r0
            r1 = r26
            byte r0 = (byte) r1
            r7[r19] = r0
            int r19 = r19 + 1
            r0 = r13
            goto L_0x0109
        L_0x0105:
            r24 = r0
            r1 = r26
        L_0x0109:
            if (r0 < r10) goto L_0x0114
            byte r24 = r6[r0]
            r7[r19] = r24
            int r19 = r19 + 1
            short r0 = r4[r0]
            goto L_0x0109
        L_0x0114:
            r26 = r1
            byte r1 = r6[r0]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r24 = r0
            byte r0 = (byte) r1
            r3[r9] = r0
            int r9 = r9 + 1
            int r25 = r25 + 1
        L_0x0123:
            if (r19 <= 0) goto L_0x0130
            int r19 = r19 + -1
            byte r0 = r7[r19]
            r3[r9] = r0
            int r9 = r9 + 1
            int r25 = r25 + 1
            goto L_0x0123
        L_0x0130:
            r0 = 4096(0x1000, float:5.74E-42)
            if (r12 >= r0) goto L_0x014a
            short r0 = (short) r13
            r4[r12] = r0
            byte r0 = (byte) r1
            r6[r12] = r0
            int r12 = r12 + 1
            r0 = r12 & r15
            if (r0 != 0) goto L_0x0148
            r0 = 4096(0x1000, float:5.74E-42)
            if (r12 >= r0) goto L_0x014a
            int r14 = r14 + 1
            int r15 = r15 + r12
            goto L_0x014a
        L_0x0148:
            r0 = 4096(0x1000, float:5.74E-42)
        L_0x014a:
            r13 = r20
            r0 = r29
            r26 = r1
            r1 = r27
            goto L_0x00b7
        L_0x0154:
            r27 = r1
            r0 = 4096(0x1000, float:5.74E-42)
            r16 = 1
            r0 = r29
            r22 = r5
            r5 = r25
            r20 = r26
            goto L_0x0089
        L_0x0164:
            r27 = r1
            r25 = r5
        L_0x0168:
            r0 = 0
            java.util.Arrays.fill(r3, r9, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.e.k(com.bumptech.glide.gifdecoder.b):void");
    }

    private int o() {
        return this.e.get() & 255;
    }

    private int n() {
        int blockSize = o();
        if (blockSize <= 0) {
            return blockSize;
        }
        ByteBuffer byteBuffer = this.e;
        byteBuffer.get(this.f, 0, Math.min(blockSize, byteBuffer.remaining()));
        return blockSize;
    }

    private Bitmap m() {
        Boolean bool = this.t;
        Bitmap result = this.d.c(this.s, this.r, (bool == null || bool.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.u);
        result.setHasAlpha(true);
        return result;
    }
}
