package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperWidget extends ConstraintWidget implements Helper {
    public ConstraintWidget[] mWidgets = new ConstraintWidget[4];
    public int mWidgetsCount = 0;

    public void updateConstraints(ConstraintWidgetContainer container) {
    }

    public void add(ConstraintWidget widget) {
        if (widget != this && widget != null) {
            int i = this.mWidgetsCount + 1;
            ConstraintWidget[] constraintWidgetArr = this.mWidgets;
            if (i > constraintWidgetArr.length) {
                this.mWidgets = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr, constraintWidgetArr.length * 2);
            }
            ConstraintWidget[] constraintWidgetArr2 = this.mWidgets;
            int i2 = this.mWidgetsCount;
            constraintWidgetArr2[i2] = widget;
            this.mWidgetsCount = i2 + 1;
        }
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(src, map);
        HelperWidget srcHelper = (HelperWidget) src;
        this.mWidgetsCount = 0;
        int count = srcHelper.mWidgetsCount;
        for (int i = 0; i < count; i++) {
            add(map.get(srcHelper.mWidgets[i]));
        }
    }

    public void removeAllIds() {
        this.mWidgetsCount = 0;
        Arrays.fill(this.mWidgets, (Object) null);
    }

    public void addDependents(ArrayList<WidgetGroup> dependencyLists, int orientation, WidgetGroup group) {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            group.add(this.mWidgets[i]);
        }
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            Grouping.findDependents(this.mWidgets[i2], orientation, dependencyLists, group);
        }
    }

    public int findGroupInDependents(int orientation) {
        int i;
        int i2;
        for (int i3 = 0; i3 < this.mWidgetsCount; i3++) {
            ConstraintWidget widget = this.mWidgets[i3];
            if (orientation == 0 && (i2 = widget.horizontalGroup) != -1) {
                return i2;
            }
            if (orientation == 1 && (i = widget.verticalGroup) != -1) {
                return i;
            }
        }
        return -1;
    }
}
