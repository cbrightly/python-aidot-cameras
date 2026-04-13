package com.amazonaws.metrics;

import com.amazonaws.internal.MetricAware;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.h;

public class MetricInputStreamEntity extends h {
    private static final int BUFFER_SIZE = 2048;
    private final ByteThroughputHelper helper;

    public MetricInputStreamEntity(ThroughputMetricType metricType, InputStream instream, long length) {
        super(instream, length);
        this.helper = new ByteThroughputHelper(metricType);
    }

    public void writeTo(OutputStream outstream) {
        if (!(outstream instanceof MetricAware) || !((MetricAware) outstream).isMetricActivated()) {
            writeToWithMetrics(outstream);
        } else {
            super.writeTo(outstream);
        }
    }

    private void writeToWithMetrics(OutputStream outstream) {
        OutputStream outputStream = outstream;
        if (outputStream != null) {
            InputStream content = getContent();
            long length = getContentLength();
            InputStream instream = content;
            try {
                byte[] buffer = new byte[2048];
                long j = 0;
                if (length < 0) {
                    while (true) {
                        int read = instream.read(buffer);
                        int l = read;
                        if (read == -1) {
                            break;
                        }
                        long startNano = this.helper.startTiming();
                        outputStream.write(buffer, 0, l);
                        this.helper.increment(l, startNano);
                    }
                } else {
                    long remaining = length;
                    while (true) {
                        if (remaining <= j) {
                            break;
                        }
                        int l2 = instream.read(buffer, 0, (int) Math.min(2048, remaining));
                        if (l2 == -1) {
                            break;
                        }
                        long startNano2 = this.helper.startTiming();
                        outputStream.write(buffer, 0, l2);
                        this.helper.increment(l2, startNano2);
                        remaining -= (long) l2;
                        j = 0;
                    }
                }
            } finally {
                this.helper.reportMetrics();
                instream.close();
            }
        } else {
            throw new IllegalArgumentException("Output stream may not be null");
        }
    }
}
