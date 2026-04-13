package com.didichuxing.doraemonkit.kit.loginfo.reader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogcatReaderLoader implements Parcelable {
    public static final Parcelable.Creator<LogcatReaderLoader> CREATOR = new Parcelable.Creator<LogcatReaderLoader>() {
        public LogcatReaderLoader createFromParcel(Parcel in) {
            return new LogcatReaderLoader(in);
        }

        public LogcatReaderLoader[] newArray(int size) {
            return new LogcatReaderLoader[size];
        }
    };
    private Map<String, String> lastLines;
    private boolean multiple;
    private boolean recordingMode;

    private LogcatReaderLoader(Parcel in) {
        this.lastLines = new HashMap();
        boolean z = false;
        this.recordingMode = in.readInt() == 1;
        this.multiple = in.readInt() == 1 ? true : z;
        Bundle bundle = in.readBundle();
        for (String key : bundle.keySet()) {
            this.lastLines.put(key, bundle.getString(key));
        }
    }

    private LogcatReaderLoader(List<String> buffers, boolean recordingMode2) {
        this.lastLines = new HashMap();
        this.recordingMode = recordingMode2;
        this.multiple = buffers.size() <= 1 ? false : true;
        for (String buffer : buffers) {
            this.lastLines.put(buffer, recordingMode2 ? LogcatHelper.getLastLogLine(buffer) : null);
        }
    }

    public static LogcatReaderLoader create(boolean recordingMode2) {
        List<String> buffers = new ArrayList<>();
        buffers.add(LogcatHelper.BUFFER_MAIN);
        return new LogcatReaderLoader(buffers, recordingMode2);
    }

    public LogcatReader loadReader() {
        return new SingleLogcatReader(this.recordingMode, this.lastLines.keySet().iterator().next(), this.lastLines.values().iterator().next());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recordingMode ? 1 : 0);
        dest.writeInt(this.multiple ? 1 : 0);
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : this.lastLines.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        dest.writeBundle(bundle);
    }
}
