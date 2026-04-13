package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.Locale;

public final class NettyRuntime {
    private static final AvailableProcessorsHolder holder = new AvailableProcessorsHolder();

    public static class AvailableProcessorsHolder {
        private int availableProcessors;

        AvailableProcessorsHolder() {
        }

        /* access modifiers changed from: package-private */
        public synchronized void setAvailableProcessors(int availableProcessors2) {
            ObjectUtil.checkPositive(availableProcessors2, "availableProcessors");
            int i = this.availableProcessors;
            if (i == 0) {
                this.availableProcessors = availableProcessors2;
            } else {
                throw new IllegalStateException(String.format(Locale.ROOT, "availableProcessors is already set to [%d], rejecting [%d]", new Object[]{Integer.valueOf(i), Integer.valueOf(availableProcessors2)}));
            }
        }

        /* access modifiers changed from: package-private */
        @SuppressForbidden(reason = "to obtain default number of available processors")
        public synchronized int availableProcessors() {
            if (this.availableProcessors == 0) {
                setAvailableProcessors(SystemPropertyUtil.getInt("io.netty.availableProcessors", Runtime.getRuntime().availableProcessors()));
            }
            return this.availableProcessors;
        }
    }

    public static void setAvailableProcessors(int availableProcessors) {
        holder.setAvailableProcessors(availableProcessors);
    }

    public static int availableProcessors() {
        return holder.availableProcessors();
    }

    private NettyRuntime() {
    }
}
