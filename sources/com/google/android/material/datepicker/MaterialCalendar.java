package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Iterator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class MaterialCalendar<S> extends PickerFragment<S> {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
    private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
    @VisibleForTesting
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    @VisibleForTesting
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    @VisibleForTesting
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    @VisibleForTesting
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
    private static final int SMOOTH_SCROLL_MAX = 3;
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    /* access modifiers changed from: private */
    @Nullable
    public CalendarConstraints calendarConstraints;
    private CalendarSelector calendarSelector;
    /* access modifiers changed from: private */
    public CalendarStyle calendarStyle;
    /* access modifiers changed from: private */
    @Nullable
    public Month current;
    /* access modifiers changed from: private */
    @Nullable
    public DateSelector<S> dateSelector;
    /* access modifiers changed from: private */
    public View dayFrame;
    /* access modifiers changed from: private */
    public RecyclerView recyclerView;
    @StyleRes
    private int themeResId;
    private View yearFrame;
    /* access modifiers changed from: private */
    public RecyclerView yearSelector;

    public enum CalendarSelector {
        DAY,
        YEAR
    }

    public interface OnDayClickListener {
        void onDayClick(long j);
    }

    @NonNull
    public static <T> MaterialCalendar<T> newInstance(@NonNull DateSelector<T> dateSelector2, @StyleRes int themeResId2, @NonNull CalendarConstraints calendarConstraints2) {
        MaterialCalendar<T> materialCalendar = new MaterialCalendar<>();
        Bundle args = new Bundle();
        args.putInt(THEME_RES_ID_KEY, themeResId2);
        args.putParcelable(GRID_SELECTOR_KEY, dateSelector2);
        args.putParcelable(CALENDAR_CONSTRAINTS_KEY, calendarConstraints2);
        args.putParcelable(CURRENT_MONTH_KEY, calendarConstraints2.getOpenAt());
        materialCalendar.setArguments(args);
        return materialCalendar;
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(THEME_RES_ID_KEY, this.themeResId);
        bundle.putParcelable(GRID_SELECTOR_KEY, this.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, this.calendarConstraints);
        bundle.putParcelable(CURRENT_MONTH_KEY, this.current);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.themeResId = activeBundle.getInt(THEME_RES_ID_KEY);
        this.dateSelector = (DateSelector) activeBundle.getParcelable(GRID_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.current = (Month) activeBundle.getParcelable(CURRENT_MONTH_KEY);
    }

    @NonNull
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        int orientation;
        int layout;
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(themedContext);
        LayoutInflater themedInflater = layoutInflater.cloneInContext(themedContext);
        Month earliestMonth = this.calendarConstraints.getStart();
        if (MaterialDatePicker.isFullscreen(themedContext)) {
            layout = R.layout.mtrl_calendar_vertical;
            orientation = 1;
        } else {
            layout = R.layout.mtrl_calendar_horizontal;
            orientation = 0;
        }
        View root = themedInflater.inflate(layout, viewGroup, false);
        GridView daysHeader = (GridView) root.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(daysHeader, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo((Object) null);
            }
        });
        daysHeader.setAdapter(new DaysOfWeekAdapter());
        daysHeader.setNumColumns(earliestMonth.daysInWeek);
        daysHeader.setEnabled(false);
        this.recyclerView = (RecyclerView) root.findViewById(R.id.mtrl_calendar_months);
        GridView gridView = daysHeader;
        final int i = orientation;
        this.recyclerView.setLayoutManager(new SmoothCalendarLayoutManager(getContext(), orientation, false) {
            /* access modifiers changed from: protected */
            public void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] ints) {
                if (i == 0) {
                    ints[0] = MaterialCalendar.this.recyclerView.getWidth();
                    ints[1] = MaterialCalendar.this.recyclerView.getWidth();
                    return;
                }
                ints[0] = MaterialCalendar.this.recyclerView.getHeight();
                ints[1] = MaterialCalendar.this.recyclerView.getHeight();
            }
        });
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(themedContext, this.dateSelector, this.calendarConstraints, new OnDayClickListener() {
            public void onDayClick(long day) {
                if (MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(day)) {
                    MaterialCalendar.this.dateSelector.select(day);
                    Iterator it = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((OnSelectionChangedListener) it.next()).onSelectionChanged(MaterialCalendar.this.dateSelector.getSelection());
                    }
                    MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
                    if (MaterialCalendar.this.yearSelector != null) {
                        MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int columns = themedContext.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView2 = (RecyclerView) root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView2;
        if (recyclerView2 != null) {
            recyclerView2.setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager((Context) themedContext, columns, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(createItemDecoration());
        }
        if (root.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            addActionsToMonthNavigation(root, monthsPagerAdapter);
        }
        if (!MaterialDatePicker.isFullscreen(themedContext)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(monthsPagerAdapter.getPosition(this.current));
        return root;
    }

    @NonNull
    private RecyclerView.ItemDecoration createItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            private final Calendar endItem = UtcDates.getUtcCalendar();
            private final Calendar startItem = UtcDates.getUtcCalendar();

            public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                int firstHighlightPosition;
                Iterator<Pair<Long, Long>> it;
                Pair<Long, Long> range;
                GridLayoutManager layoutManager;
                YearGridAdapter adapter;
                int i;
                if ((recyclerView.getAdapter() instanceof YearGridAdapter) && (recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                    YearGridAdapter adapter2 = (YearGridAdapter) recyclerView.getAdapter();
                    GridLayoutManager layoutManager2 = (GridLayoutManager) recyclerView.getLayoutManager();
                    Iterator<Pair<Long, Long>> it2 = MaterialCalendar.this.dateSelector.getSelectedRanges().iterator();
                    while (it2.hasNext()) {
                        Pair<Long, Long> range2 = it2.next();
                        F f = range2.first;
                        if (f == null) {
                            GridLayoutManager gridLayoutManager = layoutManager2;
                            Iterator<Pair<Long, Long>> it3 = it2;
                            Pair<Long, Long> pair = range2;
                        } else if (range2.second != null) {
                            this.startItem.setTimeInMillis(((Long) f).longValue());
                            this.endItem.setTimeInMillis(((Long) range2.second).longValue());
                            int firstHighlightPosition2 = adapter2.getPositionForYear(this.startItem.get(1));
                            int lastHighlightPosition = adapter2.getPositionForYear(this.endItem.get(1));
                            View firstView = layoutManager2.findViewByPosition(firstHighlightPosition2);
                            View lastView = layoutManager2.findViewByPosition(lastHighlightPosition);
                            int firstRow = firstHighlightPosition2 / layoutManager2.getSpanCount();
                            int lastRow = lastHighlightPosition / layoutManager2.getSpanCount();
                            int row = firstRow;
                            while (row <= lastRow) {
                                View viewInRow = layoutManager2.findViewByPosition(layoutManager2.getSpanCount() * row);
                                if (viewInRow == null) {
                                    adapter = adapter2;
                                    layoutManager = layoutManager2;
                                    it = it2;
                                    range = range2;
                                    firstHighlightPosition = firstHighlightPosition2;
                                } else {
                                    int top = viewInRow.getTop() + MaterialCalendar.this.calendarStyle.year.getTopInset();
                                    adapter = adapter2;
                                    int bottom = viewInRow.getBottom() - MaterialCalendar.this.calendarStyle.year.getBottomInset();
                                    int left = row == firstRow ? firstView.getLeft() + (firstView.getWidth() / 2) : 0;
                                    if (row == lastRow) {
                                        i = lastView.getLeft() + (lastView.getWidth() / 2);
                                    } else {
                                        i = recyclerView.getWidth();
                                    }
                                    int right = i;
                                    layoutManager = layoutManager2;
                                    int i2 = left;
                                    it = it2;
                                    int right2 = right;
                                    range = range2;
                                    int i3 = right2;
                                    firstHighlightPosition = firstHighlightPosition2;
                                    canvas.drawRect((float) left, (float) top, (float) right2, (float) bottom, MaterialCalendar.this.calendarStyle.rangeFill);
                                }
                                row++;
                                adapter2 = adapter;
                                layoutManager2 = layoutManager;
                                range2 = range;
                                it2 = it;
                                firstHighlightPosition2 = firstHighlightPosition;
                            }
                            GridLayoutManager gridLayoutManager2 = layoutManager2;
                            Iterator<Pair<Long, Long>> it4 = it2;
                            Pair<Long, Long> pair2 = range2;
                            int i4 = firstHighlightPosition2;
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Month getCurrentMonth() {
        return this.current;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public CalendarConstraints getCalendarConstraints() {
        return this.calendarConstraints;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentMonth(Month moveTo) {
        MonthsPagerAdapter adapter = (MonthsPagerAdapter) this.recyclerView.getAdapter();
        int moveToPosition = adapter.getPosition(moveTo);
        int distance = moveToPosition - adapter.getPosition(this.current);
        boolean isForward = true;
        boolean jump = Math.abs(distance) > 3;
        if (distance <= 0) {
            isForward = false;
        }
        this.current = moveTo;
        if (jump && isForward) {
            this.recyclerView.scrollToPosition(moveToPosition - 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else if (jump) {
            this.recyclerView.scrollToPosition(moveToPosition + 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else {
            postSmoothRecyclerViewScroll(moveToPosition);
        }
    }

    @Nullable
    public DateSelector<S> getDateSelector() {
        return this.dateSelector;
    }

    /* access modifiers changed from: package-private */
    public CalendarStyle getCalendarStyle() {
        return this.calendarStyle;
    }

    @Px
    static int getDayHeight(@NonNull Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
    }

    /* access modifiers changed from: package-private */
    public void setSelector(CalendarSelector selector) {
        this.calendarSelector = selector;
        if (selector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter) this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
        } else if (selector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }

    /* access modifiers changed from: package-private */
    public void toggleVisibleSelector() {
        CalendarSelector calendarSelector2 = this.calendarSelector;
        CalendarSelector calendarSelector3 = CalendarSelector.YEAR;
        if (calendarSelector2 == calendarSelector3) {
            setSelector(CalendarSelector.DAY);
        } else if (calendarSelector2 == CalendarSelector.DAY) {
            setSelector(calendarSelector3);
        }
    }

    private void addActionsToMonthNavigation(@NonNull View root, @NonNull final MonthsPagerAdapter monthsPagerAdapter) {
        final MaterialButton monthDropSelect = (MaterialButton) root.findViewById(R.id.month_navigation_fragment_toggle);
        monthDropSelect.setTag(SELECTOR_TOGGLE_TAG);
        ViewCompat.setAccessibilityDelegate(monthDropSelect, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                String str;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
                    str = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
                } else {
                    str = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
                }
                accessibilityNodeInfoCompat.setHintText(str);
            }
        });
        MaterialButton monthPrev = (MaterialButton) root.findViewById(R.id.month_navigation_previous);
        monthPrev.setTag(NAVIGATION_PREV_TAG);
        MaterialButton monthNext = (MaterialButton) root.findViewById(R.id.month_navigation_next);
        monthNext.setTag(NAVIGATION_NEXT_TAG);
        this.yearFrame = root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.dayFrame = root.findViewById(R.id.mtrl_calendar_day_selector_frame);
        setSelector(CalendarSelector.DAY);
        monthDropSelect.setText(this.current.getLongName(root.getContext()));
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int currentItem;
                if (dx < 0) {
                    currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                } else {
                    currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                }
                Month unused = MaterialCalendar.this.current = monthsPagerAdapter.getPageMonth(currentItem);
                monthDropSelect.setText(monthsPagerAdapter.getPageTitle(currentItem));
            }

            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    CharSequence announcementText = monthDropSelect.getText();
                    if (Build.VERSION.SDK_INT >= 16) {
                        recyclerView.announceForAccessibility(announcementText);
                    } else {
                        recyclerView.sendAccessibilityEvent(2048);
                    }
                }
            }
        });
        monthDropSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MaterialCalendar.this.toggleVisibleSelector();
            }
        });
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                if (currentItem + 1 < MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem + 1));
                }
            }
        });
        monthPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                if (currentItem - 1 >= 0) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem - 1));
                }
            }
        });
    }

    private void postSmoothRecyclerViewScroll(final int position) {
        this.recyclerView.post(new Runnable() {
            public void run() {
                MaterialCalendar.this.recyclerView.smoothScrollToPosition(position);
            }
        });
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) this.recyclerView.getLayoutManager();
    }

    public boolean addOnSelectionChangedListener(@NonNull OnSelectionChangedListener<S> listener) {
        return super.addOnSelectionChangedListener(listener);
    }
}
