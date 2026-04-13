package com.ldf.calendar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import com.ldf.calendar.component.a;
import com.ldf.calendar.interf.c;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.Day;
import com.ldf.calendar.view.a;

/* compiled from: CalendarRenderer */
public class b {
    private a[] a = new a[6];
    private Calendar b;
    private a c;
    private com.ldf.calendar.interf.a d;
    private Context e;
    private c f;
    private com.ldf.calendar.model.a g;
    private com.ldf.calendar.model.a h;
    private int i = 0;

    public b(Calendar calendar, a attr, Context context) {
        this.b = calendar;
        this.c = attr;
        this.e = context;
    }

    public void b(Canvas canvas) {
        for (int row = 0; row < 6; row++) {
            if (this.a[row] != null) {
                for (int col = 0; col < 7; col++) {
                    a[] aVarArr = this.a;
                    if (aVarArr[row].b[col] != null) {
                        this.d.a(canvas, aVarArr[row].b[col]);
                    }
                }
            }
        }
    }

    public void j(int col, int row) {
        if (col < 7 && row < 6 && this.a[row] != null) {
            if (this.c.a() != a.C0078a.MONTH) {
                this.a[row].b[col].f(c.SELECT);
                com.ldf.calendar.model.a a2 = this.a[row].b[col].a();
                this.h = a2;
                CalendarViewAdapter.j(a2);
                this.f.b(this.h);
                this.g = this.h;
            } else if (this.a[row].b[col].d() == c.CURRENT_MONTH) {
                this.a[row].b[col].f(c.SELECT);
                com.ldf.calendar.model.a a3 = this.a[row].b[col].a();
                this.h = a3;
                CalendarViewAdapter.j(a3);
                this.f.b(this.h);
                this.g = this.h;
            } else if (this.a[row].b[col].d() == c.PAST_MONTH) {
                com.ldf.calendar.model.a a4 = this.a[row].b[col].a();
                this.h = a4;
                CalendarViewAdapter.j(a4);
                this.f.a(-1);
                this.f.b(this.h);
            } else if (this.a[row].b[col].d() == c.NEXT_MONTH) {
                com.ldf.calendar.model.a a5 = this.a[row].b[col].a();
                this.h = a5;
                CalendarViewAdapter.j(a5);
                this.f.a(1);
                this.f.b(this.h);
            }
        }
    }

    public void q(int rowIndex) {
        com.ldf.calendar.model.a currentWeekLastDay;
        if (this.c.b() == a.b.Sunday) {
            currentWeekLastDay = com.ldf.calendar.a.h(this.g);
        } else {
            currentWeekLastDay = com.ldf.calendar.a.i(this.g);
        }
        int day = currentWeekLastDay.day;
        for (int i2 = 6; i2 >= 0; i2--) {
            com.ldf.calendar.model.a date = currentWeekLastDay.modifyDay(day);
            com.ldf.calendar.view.a[] aVarArr = this.a;
            if (aVarArr[rowIndex] == null) {
                aVarArr[rowIndex] = new com.ldf.calendar.view.a(rowIndex);
            }
            if (this.a[rowIndex].b[i2] != null) {
                if (date.equals(CalendarViewAdapter.f())) {
                    this.a[rowIndex].b[i2].f(c.SELECT);
                    this.a[rowIndex].b[i2].e(date);
                } else {
                    this.a[rowIndex].b[i2].f(c.CURRENT_MONTH);
                    this.a[rowIndex].b[i2].e(date);
                }
            } else if (date.equals(CalendarViewAdapter.f())) {
                this.a[rowIndex].b[i2] = new Day(c.SELECT, date, rowIndex, i2);
            } else {
                this.a[rowIndex].b[i2] = new Day(c.CURRENT_MONTH, date, rowIndex, i2);
            }
            day--;
        }
    }

    private void h() {
        com.ldf.calendar.model.a aVar = this.g;
        int lastMonthDays = com.ldf.calendar.a.g(aVar.year, aVar.month - 1);
        com.ldf.calendar.model.a aVar2 = this.g;
        int currentMonthDays = com.ldf.calendar.a.g(aVar2.year, aVar2.month);
        com.ldf.calendar.model.a aVar3 = this.g;
        int firstDayPosition = com.ldf.calendar.a.e(aVar3.year, aVar3.month, this.c.b());
        Log.e("ldf", "firstDayPosition = " + firstDayPosition);
        int day = 0;
        for (int row = 0; row < 6; row++) {
            day = d(lastMonthDays, currentMonthDays, firstDayPosition, day, row);
        }
    }

    private int d(int lastMonthDays, int currentMonthDays, int firstDayWeek, int day, int row) {
        for (int col = 0; col < 7; col++) {
            int position = col + (row * 7);
            if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
                day++;
                c(day, row, col);
            } else if (position < firstDayWeek) {
                g(lastMonthDays, firstDayWeek, row, col, position);
            } else if (position >= firstDayWeek + currentMonthDays) {
                i(currentMonthDays, firstDayWeek, row, col, position);
            }
        }
        return day;
    }

    private void c(int day, int row, int col) {
        com.ldf.calendar.model.a date = this.g.modifyDay(day);
        com.ldf.calendar.view.a[] aVarArr = this.a;
        if (aVarArr[row] == null) {
            aVarArr[row] = new com.ldf.calendar.view.a(row);
        }
        if (this.a[row].b[col] != null) {
            if (date.equals(CalendarViewAdapter.f())) {
                this.a[row].b[col].e(date);
                this.a[row].b[col].f(c.SELECT);
            } else {
                this.a[row].b[col].e(date);
                this.a[row].b[col].f(c.CURRENT_MONTH);
            }
        } else if (date.equals(CalendarViewAdapter.f())) {
            this.a[row].b[col] = new Day(c.SELECT, date, row, col);
        } else {
            this.a[row].b[col] = new Day(c.CURRENT_MONTH, date, row, col);
        }
        if (date.equals(this.g)) {
            this.i = row;
        }
    }

    private void i(int currentMonthDays, int firstDayWeek, int row, int col, int position) {
        com.ldf.calendar.model.a aVar = this.g;
        com.ldf.calendar.model.a date = new com.ldf.calendar.model.a(aVar.year, aVar.month + 1, ((position - firstDayWeek) - currentMonthDays) + 1);
        com.ldf.calendar.view.a[] aVarArr = this.a;
        if (aVarArr[row] == null) {
            aVarArr[row] = new com.ldf.calendar.view.a(row);
        }
        com.ldf.calendar.view.a[] aVarArr2 = this.a;
        if (aVarArr2[row].b[col] != null) {
            aVarArr2[row].b[col].e(date);
            this.a[row].b[col].f(c.NEXT_MONTH);
            return;
        }
        aVarArr2[row].b[col] = new Day(c.NEXT_MONTH, date, row, col);
    }

    private void g(int lastMonthDays, int firstDayWeek, int row, int col, int position) {
        com.ldf.calendar.model.a aVar = this.g;
        com.ldf.calendar.model.a date = new com.ldf.calendar.model.a(aVar.year, aVar.month - 1, lastMonthDays - ((firstDayWeek - position) - 1));
        com.ldf.calendar.view.a[] aVarArr = this.a;
        if (aVarArr[row] == null) {
            aVarArr[row] = new com.ldf.calendar.view.a(row);
        }
        com.ldf.calendar.view.a[] aVarArr2 = this.a;
        if (aVarArr2[row].b[col] != null) {
            aVarArr2[row].b[col].e(date);
            this.a[row].b[col].f(c.PAST_MONTH);
            return;
        }
        aVarArr2[row].b[col] = new Day(c.PAST_MONTH, date, row, col);
    }

    public void o(com.ldf.calendar.model.a seedDate) {
        if (seedDate != null) {
            this.g = seedDate;
        } else {
            this.g = new com.ldf.calendar.model.a();
        }
        p();
    }

    public void p() {
        h();
        this.b.invalidate();
    }

    public com.ldf.calendar.model.a e() {
        return this.g;
    }

    public void a() {
        for (int i2 = 0; i2 < 6; i2++) {
            if (this.a[i2] != null) {
                int j = 0;
                while (true) {
                    if (j >= 7) {
                        break;
                    } else if (this.a[i2].b[j].d() == c.SELECT) {
                        this.a[i2].b[j].f(c.CURRENT_MONTH);
                        k();
                        break;
                    } else {
                        j++;
                    }
                }
            }
        }
    }

    public void k() {
        this.i = 0;
    }

    public int f() {
        return this.i;
    }

    public void n(int selectedRowIndex) {
        this.i = selectedRowIndex;
    }

    public void l(a attr) {
        this.c = attr;
    }

    public void setOnSelectDateListener(c onSelectDateListener) {
        this.f = onSelectDateListener;
    }

    public void m(com.ldf.calendar.interf.a dayRenderer) {
        this.d = dayRenderer;
    }
}
