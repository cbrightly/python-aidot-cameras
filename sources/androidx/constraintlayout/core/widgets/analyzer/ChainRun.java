package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
    private int chainStyle;
    ArrayList<WidgetRun> widgets = new ArrayList<>();

    public ChainRun(ConstraintWidget widget, int orientation) {
        super(widget);
        this.orientation = orientation;
        build();
    }

    public String toString() {
        StringBuilder log = new StringBuilder("ChainRun ");
        log.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            log.append("<");
            log.append(it.next());
            log.append("> ");
        }
        return log.toString();
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        int count = this.widgets.size();
        for (int i = 0; i < count; i++) {
            if (!this.widgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public long getWrapDimension() {
        int count = this.widgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < count; i++) {
            WidgetRun run = this.widgets.get(i);
            wrapDimension = wrapDimension + ((long) run.start.margin) + run.getWrapDimension() + ((long) run.end.margin);
        }
        return wrapDimension;
    }

    private void build() {
        ConstraintWidget current = this.widget;
        ConstraintWidget previous = current.getPreviousChainMember(this.orientation);
        while (previous != null) {
            current = previous;
            previous = current.getPreviousChainMember(this.orientation);
        }
        this.widget = current;
        this.widgets.add(current.getRun(this.orientation));
        ConstraintWidget next = current.getNextChainMember(this.orientation);
        while (next != null) {
            ConstraintWidget current2 = next;
            this.widgets.add(current2.getRun(this.orientation));
            next = current2.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            WidgetRun run = it.next();
            int i = this.orientation;
            if (i == 0) {
                run.widget.horizontalChainRun = this;
            } else if (i == 1) {
                run.widget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl()) && this.widgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.widgets;
            this.widget = arrayList.get(arrayList.size() - 1).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f5 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r28) {
        /*
            r27 = this;
            r0 = r27
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x0490
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.end
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x0010
            goto L_0x0490
        L_0x0010:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.getParent()
            r2 = 0
            boolean r3 = r1 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r3 == 0) goto L_0x0022
            r3 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r3 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r3
            boolean r2 = r3.isRtl()
        L_0x0022:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = r0.end
            int r3 = r3.value
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r4 = r0.start
            int r4 = r4.value
            int r3 = r3 - r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r8 = r0.widgets
            int r8 = r8.size()
            r9 = -1
            r10 = 0
        L_0x0037:
            r11 = 8
            if (r10 >= r8) goto L_0x0050
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r12 = r0.widgets
            java.lang.Object r12 = r12.get(r10)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r12 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.widget
            int r13 = r13.getVisibility()
            if (r13 != r11) goto L_0x004f
            int r10 = r10 + 1
            goto L_0x0037
        L_0x004f:
            r9 = r10
        L_0x0050:
            r10 = -1
            int r12 = r8 + -1
        L_0x0053:
            if (r12 < 0) goto L_0x006a
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r12)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            if (r14 != r11) goto L_0x0069
            int r12 = r12 + -1
            goto L_0x0053
        L_0x0069:
            r10 = r12
        L_0x006a:
            r12 = 0
        L_0x006b:
            r15 = 2
            if (r12 >= r15) goto L_0x011d
            r17 = 0
            r15 = r17
        L_0x0072:
            if (r15 >= r8) goto L_0x010a
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r15)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            if (r14 != r11) goto L_0x0088
            r19 = r1
            goto L_0x0102
        L_0x0088:
            int r7 = r7 + 1
            if (r15 <= 0) goto L_0x0093
            if (r15 < r9) goto L_0x0093
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r4 = r4 + r14
        L_0x0093:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r11 = r14.value
            r19 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r13.dimensionBehavior
            r20 = r7
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 == r7) goto L_0x00a3
            r1 = 1
            goto L_0x00a4
        L_0x00a3:
            r1 = 0
        L_0x00a4:
            if (r1 == 0) goto L_0x00c6
            int r7 = r0.orientation
            if (r7 != 0) goto L_0x00b5
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r14 = r14.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r14.dimension
            boolean r14 = r14.resolved
            if (r14 != 0) goto L_0x00b5
            return
        L_0x00b5:
            r14 = 1
            if (r7 != r14) goto L_0x00c3
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r13.widget
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r7 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r7 = r7.dimension
            boolean r7 = r7.resolved
            if (r7 != 0) goto L_0x00c3
            return
        L_0x00c3:
            r21 = r1
            goto L_0x00db
        L_0x00c6:
            int r7 = r13.matchConstraintsType
            r21 = r1
            r1 = 1
            if (r7 != r1) goto L_0x00d5
            if (r12 != 0) goto L_0x00d5
            r1 = 1
            int r11 = r14.wrapValue
            int r5 = r5 + 1
            goto L_0x00dd
        L_0x00d5:
            boolean r1 = r14.resolved
            if (r1 == 0) goto L_0x00db
            r1 = 1
            goto L_0x00dd
        L_0x00db:
            r1 = r21
        L_0x00dd:
            if (r1 != 0) goto L_0x00f0
            int r5 = r5 + 1
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r13.widget
            float[] r7 = r7.mWeight
            int r14 = r0.orientation
            r7 = r7[r14]
            r14 = 0
            int r21 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r21 < 0) goto L_0x00ef
            float r6 = r6 + r7
        L_0x00ef:
            goto L_0x00f1
        L_0x00f0:
            int r4 = r4 + r11
        L_0x00f1:
            int r7 = r8 + -1
            if (r15 >= r7) goto L_0x0100
            if (r15 >= r10) goto L_0x0100
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r7 = r13.end
            int r7 = r7.margin
            int r7 = -r7
            int r4 = r4 + r7
            r7 = r20
            goto L_0x0102
        L_0x0100:
            r7 = r20
        L_0x0102:
            int r15 = r15 + 1
            r1 = r19
            r11 = 8
            goto L_0x0072
        L_0x010a:
            r19 = r1
            if (r4 < r3) goto L_0x011f
            if (r5 != 0) goto L_0x0111
            goto L_0x011f
        L_0x0111:
            r7 = 0
            r5 = 0
            r4 = 0
            r6 = 0
            int r12 = r12 + 1
            r1 = r19
            r11 = 8
            goto L_0x006b
        L_0x011d:
            r19 = r1
        L_0x011f:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            int r1 = r1.value
            if (r2 == 0) goto L_0x0129
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r11 = r0.end
            int r1 = r11.value
        L_0x0129:
            r11 = 1056964608(0x3f000000, float:0.5)
            if (r4 <= r3) goto L_0x0140
            r12 = 1073741824(0x40000000, float:2.0)
            if (r2 == 0) goto L_0x0139
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 + r12
            goto L_0x0140
        L_0x0139:
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 - r12
        L_0x0140:
            r12 = 0
            if (r5 <= 0) goto L_0x024d
            int r13 = r3 - r4
            float r13 = (float) r13
            float r14 = (float) r5
            float r13 = r13 / r14
            float r13 = r13 + r11
            int r12 = (int) r13
            r13 = 0
            r14 = 0
        L_0x014c:
            if (r14 >= r8) goto L_0x01f9
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r15 = r0.widgets
            java.lang.Object r15 = r15.get(r14)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r15 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r15
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.widget
            int r11 = r11.getVisibility()
            r21 = r1
            r1 = 8
            if (r11 != r1) goto L_0x0170
            r25 = r2
            r22 = r4
            r24 = r6
            r26 = r7
            r23 = r12
            r18 = 0
            goto L_0x01e7
        L_0x0170:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r15.dimensionBehavior
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r11) goto L_0x01db
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r15.dimension
            boolean r11 = r1.resolved
            if (r11 != 0) goto L_0x01db
            r11 = r12
            r18 = 0
            int r22 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1))
            if (r22 <= 0) goto L_0x019b
            r22 = r11
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.widget
            float[] r11 = r11.mWeight
            r23 = r12
            int r12 = r0.orientation
            r11 = r11[r12]
            int r12 = r3 - r4
            float r12 = (float) r12
            float r12 = r12 * r11
            float r12 = r12 / r6
            r20 = 1056964608(0x3f000000, float:0.5)
            float r12 = r12 + r20
            int r12 = (int) r12
            r11 = r12
            goto L_0x019f
        L_0x019b:
            r22 = r11
            r23 = r12
        L_0x019f:
            r12 = r11
            r22 = r4
            int r4 = r0.orientation
            if (r4 != 0) goto L_0x01af
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            r24 = r6
            int r6 = r4.mMatchConstraintMaxWidth
            int r4 = r4.mMatchConstraintMinWidth
            goto L_0x01b7
        L_0x01af:
            r24 = r6
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            int r6 = r4.mMatchConstraintMaxHeight
            int r4 = r4.mMatchConstraintMinHeight
        L_0x01b7:
            r25 = r2
            int r2 = r15.matchConstraintsType
            r26 = r7
            r7 = 1
            if (r2 != r7) goto L_0x01c6
            int r1 = r1.wrapValue
            int r12 = java.lang.Math.min(r12, r1)
        L_0x01c6:
            int r1 = java.lang.Math.max(r4, r12)
            if (r6 <= 0) goto L_0x01d0
            int r1 = java.lang.Math.min(r6, r1)
        L_0x01d0:
            if (r1 == r11) goto L_0x01d5
            int r13 = r13 + 1
            r11 = r1
        L_0x01d5:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r2 = r15.dimension
            r2.resolve(r11)
            goto L_0x01e7
        L_0x01db:
            r25 = r2
            r22 = r4
            r24 = r6
            r26 = r7
            r23 = r12
            r18 = 0
        L_0x01e7:
            int r14 = r14 + 1
            r1 = r21
            r4 = r22
            r12 = r23
            r6 = r24
            r2 = r25
            r7 = r26
            r11 = 1056964608(0x3f000000, float:0.5)
            goto L_0x014c
        L_0x01f9:
            r21 = r1
            r25 = r2
            r22 = r4
            r24 = r6
            r26 = r7
            r23 = r12
            if (r13 <= 0) goto L_0x023e
            int r5 = r5 - r13
            r1 = 0
            r2 = 0
        L_0x020a:
            if (r2 >= r8) goto L_0x023c
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r4 = r0.widgets
            java.lang.Object r4 = r4.get(r2)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r4.widget
            int r6 = r6.getVisibility()
            r7 = 8
            if (r6 != r7) goto L_0x021f
            goto L_0x0239
        L_0x021f:
            if (r2 <= 0) goto L_0x0228
            if (r2 < r9) goto L_0x0228
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.start
            int r6 = r6.margin
            int r1 = r1 + r6
        L_0x0228:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r6 = r4.dimension
            int r6 = r6.value
            int r1 = r1 + r6
            int r6 = r8 + -1
            if (r2 >= r6) goto L_0x0239
            if (r2 >= r10) goto L_0x0239
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.end
            int r6 = r6.margin
            int r6 = -r6
            int r1 = r1 + r6
        L_0x0239:
            int r2 = r2 + 1
            goto L_0x020a
        L_0x023c:
            r4 = r1
            goto L_0x0240
        L_0x023e:
            r4 = r22
        L_0x0240:
            int r1 = r0.chainStyle
            r2 = 2
            if (r1 != r2) goto L_0x024a
            if (r13 != 0) goto L_0x024a
            r1 = 0
            r0.chainStyle = r1
        L_0x024a:
            r12 = r23
            goto L_0x0257
        L_0x024d:
            r21 = r1
            r25 = r2
            r22 = r4
            r24 = r6
            r26 = r7
        L_0x0257:
            if (r4 <= r3) goto L_0x025d
            r1 = 2
            r0.chainStyle = r1
            goto L_0x025e
        L_0x025d:
            r1 = 2
        L_0x025e:
            if (r26 <= 0) goto L_0x0266
            if (r5 != 0) goto L_0x0266
            if (r9 != r10) goto L_0x0266
            r0.chainStyle = r1
        L_0x0266:
            int r1 = r0.chainStyle
            r2 = 1
            if (r1 != r2) goto L_0x031f
            r1 = 0
            r7 = r26
            if (r7 <= r2) goto L_0x0277
            int r6 = r3 - r4
            int r11 = r7 + -1
            int r1 = r6 / r11
            goto L_0x027e
        L_0x0277:
            if (r7 != r2) goto L_0x027e
            int r2 = r3 - r4
            r6 = 2
            int r1 = r2 / 2
        L_0x027e:
            if (r5 <= 0) goto L_0x0281
            r1 = 0
        L_0x0281:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x0285:
            if (r6 >= r8) goto L_0x0318
            r11 = r6
            if (r25 == 0) goto L_0x028e
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x028e:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x02ae
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            goto L_0x0312
        L_0x02ae:
            if (r6 <= 0) goto L_0x02b5
            if (r25 == 0) goto L_0x02b4
            int r2 = r2 - r1
            goto L_0x02b5
        L_0x02b4:
            int r2 = r2 + r1
        L_0x02b5:
            if (r6 <= 0) goto L_0x02c6
            if (r6 < r9) goto L_0x02c6
            if (r25 == 0) goto L_0x02c1
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 - r14
            goto L_0x02c6
        L_0x02c1:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 + r14
        L_0x02c6:
            if (r25 == 0) goto L_0x02ce
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x02d3
        L_0x02ce:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x02d3:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r15 = r14.value
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r13.dimensionBehavior
            r17 = r11
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r11) goto L_0x02e8
            int r1 = r13.matchConstraintsType
            r11 = 1
            if (r1 != r11) goto L_0x02e8
            int r15 = r14.wrapValue
        L_0x02e8:
            if (r25 == 0) goto L_0x02ec
            int r2 = r2 - r15
            goto L_0x02ed
        L_0x02ec:
            int r2 = r2 + r15
        L_0x02ed:
            if (r25 == 0) goto L_0x02f5
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x02fa
        L_0x02f5:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x02fa:
            r1 = 1
            r13.resolved = r1
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x0312
            if (r6 >= r10) goto L_0x0312
            if (r25 == 0) goto L_0x030c
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x0312
        L_0x030c:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x0312:
            int r6 = r6 + 1
            r1 = r16
            goto L_0x0285
        L_0x0318:
            r16 = r1
            r1 = r2
            r26 = r7
            goto L_0x048f
        L_0x031f:
            r7 = r26
            if (r1 != 0) goto L_0x03cc
            int r1 = r3 - r4
            int r2 = r7 + 1
            int r1 = r1 / r2
            if (r5 <= 0) goto L_0x032b
            r1 = 0
        L_0x032b:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x032f:
            if (r6 >= r8) goto L_0x03c5
            r11 = r6
            if (r25 == 0) goto L_0x0338
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x0338:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x035a
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            r26 = r7
            goto L_0x03bd
        L_0x035a:
            if (r25 == 0) goto L_0x035e
            int r2 = r2 - r1
            goto L_0x035f
        L_0x035e:
            int r2 = r2 + r1
        L_0x035f:
            if (r6 <= 0) goto L_0x0370
            if (r6 < r9) goto L_0x0370
            if (r25 == 0) goto L_0x036b
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 - r14
            goto L_0x0370
        L_0x036b:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 + r14
        L_0x0370:
            if (r25 == 0) goto L_0x0378
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x037d
        L_0x0378:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x037d:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r15 = r14.value
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r13.dimensionBehavior
            r26 = r7
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r7) goto L_0x0396
            int r1 = r13.matchConstraintsType
            r7 = 1
            if (r1 != r7) goto L_0x0396
            int r1 = r14.wrapValue
            int r15 = java.lang.Math.min(r15, r1)
        L_0x0396:
            if (r25 == 0) goto L_0x039a
            int r2 = r2 - r15
            goto L_0x039b
        L_0x039a:
            int r2 = r2 + r15
        L_0x039b:
            if (r25 == 0) goto L_0x03a3
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x03a8
        L_0x03a3:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x03a8:
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x03bd
            if (r6 >= r10) goto L_0x03bd
            if (r25 == 0) goto L_0x03b7
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x03bd
        L_0x03b7:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x03bd:
            int r6 = r6 + 1
            r1 = r16
            r7 = r26
            goto L_0x032f
        L_0x03c5:
            r16 = r1
            r26 = r7
            r1 = r2
            goto L_0x048f
        L_0x03cc:
            r26 = r7
            r2 = 2
            if (r1 != r2) goto L_0x048d
            int r1 = r0.orientation
            if (r1 != 0) goto L_0x03dc
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            float r1 = r1.getHorizontalBiasPercent()
            goto L_0x03e2
        L_0x03dc:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            float r1 = r1.getVerticalBiasPercent()
        L_0x03e2:
            if (r25 == 0) goto L_0x03e9
            r2 = 1065353216(0x3f800000, float:1.0)
            float r1 = r2 - r1
        L_0x03e9:
            int r2 = r3 - r4
            float r2 = (float) r2
            float r2 = r2 * r1
            r6 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r6
            int r2 = (int) r2
            if (r2 < 0) goto L_0x03f5
            if (r5 <= 0) goto L_0x03f6
        L_0x03f5:
            r2 = 0
        L_0x03f6:
            if (r25 == 0) goto L_0x03fb
            int r6 = r21 - r2
            goto L_0x03fd
        L_0x03fb:
            int r6 = r21 + r2
        L_0x03fd:
            r7 = 0
        L_0x03fe:
            if (r7 >= r8) goto L_0x0489
            r11 = r7
            if (r25 == 0) goto L_0x0407
            int r13 = r7 + 1
            int r11 = r8 - r13
        L_0x0407:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x0427
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r6)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r6)
            r16 = r1
            r1 = 1
            goto L_0x0481
        L_0x0427:
            if (r7 <= 0) goto L_0x0438
            if (r7 < r9) goto L_0x0438
            if (r25 == 0) goto L_0x0433
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r6 = r6 - r14
            goto L_0x0438
        L_0x0433:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r6 = r6 + r14
        L_0x0438:
            if (r25 == 0) goto L_0x0440
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r6)
            goto L_0x0445
        L_0x0440:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r6)
        L_0x0445:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r15 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r0 = r13.dimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x0459
            int r0 = r13.matchConstraintsType
            r1 = 1
            if (r0 != r1) goto L_0x045a
            int r15 = r14.wrapValue
            goto L_0x045a
        L_0x0459:
            r1 = 1
        L_0x045a:
            if (r25 == 0) goto L_0x045e
            int r6 = r6 - r15
            goto L_0x045f
        L_0x045e:
            int r6 = r6 + r15
        L_0x045f:
            if (r25 == 0) goto L_0x0467
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r13.start
            r0.resolve(r6)
            goto L_0x046c
        L_0x0467:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r13.end
            r0.resolve(r6)
        L_0x046c:
            int r0 = r8 + -1
            if (r7 >= r0) goto L_0x0481
            if (r7 >= r10) goto L_0x0481
            if (r25 == 0) goto L_0x047b
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r13.end
            int r0 = r0.margin
            int r0 = -r0
            int r6 = r6 - r0
            goto L_0x0481
        L_0x047b:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r13.end
            int r0 = r0.margin
            int r0 = -r0
            int r6 = r6 + r0
        L_0x0481:
            int r7 = r7 + 1
            r0 = r27
            r1 = r16
            goto L_0x03fe
        L_0x0489:
            r16 = r1
            r1 = r6
            goto L_0x048f
        L_0x048d:
            r1 = r21
        L_0x048f:
            return
        L_0x0490:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.ChainRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }

    public void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).applyToWidget();
        }
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun run = this.widgets.get(i);
            if (run.widget.getVisibility() != 8) {
                return run.widget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int i = this.widgets.size() - 1; i >= 0; i--) {
            WidgetRun run = this.widgets.get(i);
            if (run.widget.getVisibility() != 8) {
                return run.widget;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int count = this.widgets.size();
        if (count >= 1) {
            ConstraintWidget firstWidget = this.widgets.get(0).widget;
            ConstraintWidget lastWidget = this.widgets.get(count - 1).widget;
            if (this.orientation == 0) {
                ConstraintAnchor startAnchor = firstWidget.mLeft;
                ConstraintAnchor endAnchor = lastWidget.mRight;
                DependencyNode startTarget = getTarget(startAnchor, 0);
                int startMargin = startAnchor.getMargin();
                ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
                if (firstVisibleWidget != null) {
                    startMargin = firstVisibleWidget.mLeft.getMargin();
                }
                if (startTarget != null) {
                    addTarget(this.start, startTarget, startMargin);
                }
                DependencyNode endTarget = getTarget(endAnchor, 0);
                int endMargin = endAnchor.getMargin();
                ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
                if (lastVisibleWidget != null) {
                    endMargin = lastVisibleWidget.mRight.getMargin();
                }
                if (endTarget != null) {
                    addTarget(this.end, endTarget, -endMargin);
                }
            } else {
                ConstraintAnchor startAnchor2 = firstWidget.mTop;
                ConstraintAnchor endAnchor2 = lastWidget.mBottom;
                DependencyNode startTarget2 = getTarget(startAnchor2, 1);
                int startMargin2 = startAnchor2.getMargin();
                ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
                if (firstVisibleWidget2 != null) {
                    startMargin2 = firstVisibleWidget2.mTop.getMargin();
                }
                if (startTarget2 != null) {
                    addTarget(this.start, startTarget2, startMargin2);
                }
                DependencyNode endTarget2 = getTarget(endAnchor2, 1);
                int endMargin2 = endAnchor2.getMargin();
                ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
                if (lastVisibleWidget2 != null) {
                    endMargin2 = lastVisibleWidget2.mBottom.getMargin();
                }
                if (endTarget2 != null) {
                    addTarget(this.end, endTarget2, -endMargin2);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }
}
