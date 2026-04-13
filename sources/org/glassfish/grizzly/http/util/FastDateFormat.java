package org.glassfish.grizzly.http.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class FastDateFormat extends DateFormat {
    private static final long serialVersionUID = -1;
    final DateFormat df;
    final transient FieldPosition fp = new FieldPosition(8);
    long lastSec = -1;
    final StringBuffer sb = new StringBuffer();

    public FastDateFormat(DateFormat df2) {
        this.df = df2;
    }

    public Date parse(String text, ParsePosition pos) {
        return this.df.parse(text, pos);
    }

    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        long dt = date.getTime();
        long ds = dt / 1000;
        if (ds != this.lastSec) {
            this.sb.setLength(0);
            this.df.format(date, this.sb, this.fp);
            this.lastSec = ds;
        } else {
            int ms = (int) (dt % 1000);
            int pos = this.fp.getEndIndex();
            int begin = this.fp.getBeginIndex();
            if (pos > 0) {
                if (pos > begin) {
                    pos--;
                    this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
                }
                int ms2 = ms / 10;
                if (pos > begin) {
                    pos--;
                    this.sb.setCharAt(pos, Character.forDigit(ms2 % 10, 10));
                }
                int ms3 = ms2 / 10;
                if (pos > begin) {
                    this.sb.setCharAt(pos - 1, Character.forDigit(ms3 % 10, 10));
                }
            }
        }
        toAppendTo.append(this.sb.toString());
        return toAppendTo;
    }

    public StringBuilder format(Date date, StringBuilder toAppendTo, FieldPosition fieldPosition) {
        long dt = date.getTime();
        long ds = dt / 1000;
        if (ds != this.lastSec) {
            this.sb.setLength(0);
            this.df.format(date, this.sb, this.fp);
            this.lastSec = ds;
        } else {
            int ms = (int) (dt % 1000);
            int pos = this.fp.getEndIndex();
            int begin = this.fp.getBeginIndex();
            if (pos > 0) {
                if (pos > begin) {
                    pos--;
                    this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
                }
                int ms2 = ms / 10;
                if (pos > begin) {
                    pos--;
                    this.sb.setCharAt(pos, Character.forDigit(ms2 % 10, 10));
                }
                int ms3 = ms2 / 10;
                if (pos > begin) {
                    this.sb.setCharAt(pos - 1, Character.forDigit(ms3 % 10, 10));
                }
            }
        }
        toAppendTo.append(this.sb.toString());
        return toAppendTo;
    }
}
