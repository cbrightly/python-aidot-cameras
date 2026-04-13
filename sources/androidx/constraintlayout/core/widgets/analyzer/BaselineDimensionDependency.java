package androidx.constraintlayout.core.widgets.analyzer;

public class BaselineDimensionDependency extends DimensionDependency {
    public BaselineDimensionDependency(WidgetRun run) {
        super(run);
    }

    public void update(DependencyNode node) {
        WidgetRun widgetRun = this.run;
        ((VerticalWidgetRun) widgetRun).baseline.margin = widgetRun.widget.getBaselineDistance();
        this.resolved = true;
    }
}
