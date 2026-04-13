package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

public class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int direction;
    public boolean dual = false;
    WidgetRun firstRun = null;
    int groupIndex = 0;
    WidgetRun lastRun = null;
    public int position = 0;
    ArrayList<WidgetRun> runs = new ArrayList<>();

    public RunGroup(WidgetRun run, int dir) {
        int i = index;
        this.groupIndex = i;
        index = i + 1;
        this.firstRun = run;
        this.lastRun = run;
        this.direction = dir;
    }

    public void add(WidgetRun run) {
        this.runs.add(run);
        this.lastRun = run;
    }

    private long traverseStart(DependencyNode node, long startPosition) {
        WidgetRun run = node.run;
        if (run instanceof HelperReferences) {
            return startPosition;
        }
        long position2 = startPosition;
        int count = node.dependencies.size();
        for (int i = 0; i < count; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode nextNode = (DependencyNode) dependency;
                if (nextNode.run != run) {
                    position2 = Math.max(position2, traverseStart(nextNode, ((long) nextNode.margin) + startPosition));
                }
            }
        }
        if (node != run.start) {
            return position2;
        }
        long dimension = run.getWrapDimension();
        return Math.max(Math.max(position2, traverseStart(run.end, startPosition + dimension)), (startPosition + dimension) - ((long) run.end.margin));
    }

    private long traverseEnd(DependencyNode node, long startPosition) {
        WidgetRun run = node.run;
        if (run instanceof HelperReferences) {
            return startPosition;
        }
        long position2 = startPosition;
        int count = node.dependencies.size();
        for (int i = 0; i < count; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode nextNode = (DependencyNode) dependency;
                if (nextNode.run != run) {
                    position2 = Math.min(position2, traverseEnd(nextNode, ((long) nextNode.margin) + startPosition));
                }
            }
        }
        if (node != run.end) {
            return position2;
        }
        long dimension = run.getWrapDimension();
        return Math.min(Math.min(position2, traverseEnd(run.start, startPosition - dimension)), (startPosition - dimension) - ((long) run.start.margin));
    }

    public long computeWrapSize(ConstraintWidgetContainer container, int orientation) {
        ConstraintWidgetContainer constraintWidgetContainer = container;
        int i = orientation;
        WidgetRun widgetRun = this.firstRun;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0;
        }
        DependencyNode containerStart = (i == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).start;
        DependencyNode containerEnd = (i == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).end;
        boolean runWithStartTarget = widgetRun.start.targets.contains(containerStart);
        boolean runWithEndTarget = this.firstRun.end.targets.contains(containerEnd);
        long dimension = this.firstRun.getWrapDimension();
        if (!runWithStartTarget || !runWithEndTarget) {
            if (runWithStartTarget) {
                DependencyNode dependencyNode = this.firstRun.start;
                return Math.max(traverseStart(dependencyNode, (long) dependencyNode.margin), ((long) this.firstRun.start.margin) + dimension);
            } else if (runWithEndTarget) {
                DependencyNode dependencyNode2 = this.firstRun.end;
                return Math.max(-traverseEnd(dependencyNode2, (long) dependencyNode2.margin), ((long) (-this.firstRun.end.margin)) + dimension);
            } else {
                WidgetRun widgetRun2 = this.firstRun;
                return (((long) widgetRun2.start.margin) + widgetRun2.getWrapDimension()) - ((long) this.firstRun.end.margin);
            }
        } else {
            long maxPosition = traverseStart(this.firstRun.start, 0);
            long minPosition = traverseEnd(this.firstRun.end, 0);
            long endGap = maxPosition - dimension;
            WidgetRun widgetRun3 = this.firstRun;
            int i2 = widgetRun3.end.margin;
            DependencyNode dependencyNode3 = containerStart;
            long j = maxPosition;
            if (endGap >= ((long) (-i2))) {
                endGap += (long) i2;
            }
            int i3 = widgetRun3.start.margin;
            long j2 = minPosition;
            long startGap = ((-minPosition) - dimension) - ((long) i3);
            if (startGap >= ((long) i3)) {
                startGap -= (long) i3;
            }
            float bias = widgetRun3.widget.getBiasPercent(i);
            long gap = 0;
            if (bias > 0.0f) {
                gap = (long) ((((float) startGap) / bias) + (((float) endGap) / (1.0f - bias)));
            }
            long j3 = ((long) ((((float) gap) * bias) + 0.5f)) + dimension;
            WidgetRun widgetRun4 = this.firstRun;
            long j4 = gap;
            float f = bias;
            return (((long) widgetRun4.start.margin) + (j3 + ((long) ((((float) gap) * (1.0f - bias)) + 0.5f)))) - ((long) widgetRun4.end.margin);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        r3 = (androidx.constraintlayout.core.widgets.analyzer.DependencyNode) r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        r3 = (androidx.constraintlayout.core.widgets.analyzer.DependencyNode) r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean defineTerminalWidget(androidx.constraintlayout.core.widgets.analyzer.WidgetRun r8, int r9) {
        /*
            r7 = this;
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r8.widget
            boolean[] r0 = r0.isTerminalWidget
            boolean r0 = r0[r9]
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency> r0 = r0.dependencies
            java.util.Iterator r0 = r0.iterator()
        L_0x0012:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x005c
            java.lang.Object r2 = r0.next()
            androidx.constraintlayout.core.widgets.analyzer.Dependency r2 = (androidx.constraintlayout.core.widgets.analyzer.Dependency) r2
            boolean r3 = r2 instanceof androidx.constraintlayout.core.widgets.analyzer.DependencyNode
            if (r3 == 0) goto L_0x005b
            r3 = r2
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.core.widgets.analyzer.DependencyNode) r3
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = r3.run
            if (r4 != r8) goto L_0x002a
            goto L_0x0012
        L_0x002a:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r4 = r4.start
            if (r3 != r4) goto L_0x005b
            boolean r4 = r8 instanceof androidx.constraintlayout.core.widgets.analyzer.ChainRun
            if (r4 == 0) goto L_0x004c
            r4 = r8
            androidx.constraintlayout.core.widgets.analyzer.ChainRun r4 = (androidx.constraintlayout.core.widgets.analyzer.ChainRun) r4
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r5 = r4.widgets
            java.util.Iterator r5 = r5.iterator()
        L_0x003b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x004b
            java.lang.Object r6 = r5.next()
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r6 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r6
            r7.defineTerminalWidget(r6, r9)
            goto L_0x003b
        L_0x004b:
            goto L_0x0056
        L_0x004c:
            boolean r4 = r8 instanceof androidx.constraintlayout.core.widgets.analyzer.HelperReferences
            if (r4 != 0) goto L_0x0056
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r8.widget
            boolean[] r4 = r4.isTerminalWidget
            r4[r9] = r1
        L_0x0056:
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = r3.run
            r7.defineTerminalWidget(r4, r9)
        L_0x005b:
            goto L_0x0012
        L_0x005c:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r8.end
            java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency> r0 = r0.dependencies
            java.util.Iterator r0 = r0.iterator()
        L_0x0064:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x00ae
            java.lang.Object r2 = r0.next()
            androidx.constraintlayout.core.widgets.analyzer.Dependency r2 = (androidx.constraintlayout.core.widgets.analyzer.Dependency) r2
            boolean r3 = r2 instanceof androidx.constraintlayout.core.widgets.analyzer.DependencyNode
            if (r3 == 0) goto L_0x00ad
            r3 = r2
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.core.widgets.analyzer.DependencyNode) r3
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = r3.run
            if (r4 != r8) goto L_0x007c
            goto L_0x0064
        L_0x007c:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r4 = r4.start
            if (r3 != r4) goto L_0x00ad
            boolean r4 = r8 instanceof androidx.constraintlayout.core.widgets.analyzer.ChainRun
            if (r4 == 0) goto L_0x009e
            r4 = r8
            androidx.constraintlayout.core.widgets.analyzer.ChainRun r4 = (androidx.constraintlayout.core.widgets.analyzer.ChainRun) r4
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r5 = r4.widgets
            java.util.Iterator r5 = r5.iterator()
        L_0x008d:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x009d
            java.lang.Object r6 = r5.next()
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r6 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r6
            r7.defineTerminalWidget(r6, r9)
            goto L_0x008d
        L_0x009d:
            goto L_0x00a8
        L_0x009e:
            boolean r4 = r8 instanceof androidx.constraintlayout.core.widgets.analyzer.HelperReferences
            if (r4 != 0) goto L_0x00a8
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r8.widget
            boolean[] r4 = r4.isTerminalWidget
            r4[r9] = r1
        L_0x00a8:
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = r3.run
            r7.defineTerminalWidget(r4, r9)
        L_0x00ad:
            goto L_0x0064
        L_0x00ae:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.RunGroup.defineTerminalWidget(androidx.constraintlayout.core.widgets.analyzer.WidgetRun, int):boolean");
    }

    public void defineTerminalWidgets(boolean horizontalCheck, boolean verticalCheck) {
        if (horizontalCheck) {
            WidgetRun widgetRun = this.firstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (verticalCheck) {
            WidgetRun widgetRun2 = this.firstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
