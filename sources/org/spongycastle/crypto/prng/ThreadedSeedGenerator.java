package org.spongycastle.crypto.prng;

public class ThreadedSeedGenerator {

    public class SeedGenerator implements Runnable {
        private volatile int c;
        private volatile boolean d;

        public void run() {
            while (!this.d) {
                this.c++;
            }
        }
    }
}
