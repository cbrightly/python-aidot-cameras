package org.glassfish.grizzly.http.util;

import com.tencent.bugly.Bugly;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public final class StringCache {
    static int accessCount = 0;
    static ByteEntry[] bcCache = null;
    static int bcCount = 0;
    static final HashMap<ByteEntry, int[]> bcStats = new HashMap<>(cacheSize);
    static boolean byteEnabled = "true".equals(System.getProperty("tomcat.util.buf.StringCache.byte.enabled", Bugly.SDK_IS_DEV));
    static int cacheSize = Integer.parseInt(System.getProperty("tomcat.util.buf.StringCache.cacheSize", "200"));
    static CharEntry[] ccCache = null;
    static int ccCount = 0;
    static final HashMap<CharEntry, int[]> ccStats = new HashMap<>(cacheSize);
    static boolean charEnabled = "true".equals(System.getProperty("tomcat.util.buf.StringCache.char.enabled", Bugly.SDK_IS_DEV));
    static int hitCount = 0;
    private static final Logger logger = Grizzly.logger(StringCache.class);
    static int trainThreshold = Integer.parseInt(System.getProperty("tomcat.util.buf.StringCache.trainThreshold", "20000"));

    public static int getCacheSize() {
        return cacheSize;
    }

    public static void setCacheSize(int cacheSize2) {
        cacheSize = cacheSize2;
    }

    public static boolean getByteEnabled() {
        return byteEnabled;
    }

    public static void setByteEnabled(boolean byteEnabled2) {
        byteEnabled = byteEnabled2;
    }

    public static boolean getCharEnabled() {
        return charEnabled;
    }

    public static void setCharEnabled(boolean charEnabled2) {
        charEnabled = charEnabled2;
    }

    public static int getTrainThreshold() {
        return trainThreshold;
    }

    public static void setTrainThreshold(int trainThreshold2) {
        trainThreshold = trainThreshold2;
    }

    public static int getAccessCount() {
        return accessCount;
    }

    public static int getHitCount() {
        return hitCount;
    }

    public static void reset() {
        hitCount = 0;
        accessCount = 0;
        synchronized (bcStats) {
            bcCache = null;
            bcCount = 0;
        }
        synchronized (ccStats) {
            ccCache = null;
            ccCount = 0;
        }
    }

    public static String toString(ByteChunk bc) {
        int size;
        if (bcCache == null) {
            String value = bc.toStringInternal();
            if (byteEnabled) {
                HashMap<ByteEntry, int[]> hashMap = bcStats;
                synchronized (hashMap) {
                    if (bcCache != null) {
                        return value;
                    }
                    int i = bcCount;
                    if (i > trainThreshold) {
                        long t1 = System.currentTimeMillis();
                        TreeMap<Integer, ArrayList<ByteEntry>> tempMap = new TreeMap<>();
                        Iterator<ByteEntry> entries = hashMap.keySet().iterator();
                        while (entries.hasNext()) {
                            ByteEntry entry = entries.next();
                            Integer count = Integer.valueOf(bcStats.get(entry)[0]);
                            ArrayList<ByteEntry> list = tempMap.get(count);
                            if (list == null) {
                                list = new ArrayList<>();
                                tempMap.put(count, list);
                            }
                            list.add(entry);
                        }
                        int size2 = bcStats.size();
                        int i2 = cacheSize;
                        if (size2 > i2) {
                            size2 = i2;
                        }
                        ByteEntry[] tempbcCache = new ByteEntry[size2];
                        ByteChunk tempChunk = new ByteChunk();
                        int n = 0;
                        while (n < size2) {
                            Object key = tempMap.lastKey();
                            ArrayList<ByteEntry> list2 = tempMap.get(key);
                            int i3 = 0;
                            while (i3 < list2.size() && n < size2) {
                                ByteEntry entry2 = list2.get(i3);
                                byte[] bArr = entry2.name;
                                Iterator<ByteEntry> entries2 = entries;
                                tempChunk.setBytes(bArr, 0, bArr.length);
                                int insertPos = findClosest(tempChunk, tempbcCache, n);
                                if (insertPos == n) {
                                    tempbcCache[n + 1] = entry2;
                                    size = size2;
                                } else {
                                    size = size2;
                                    System.arraycopy(tempbcCache, insertPos + 1, tempbcCache, insertPos + 2, (n - insertPos) - 1);
                                    tempbcCache[insertPos + 1] = entry2;
                                }
                                n++;
                                i3++;
                                entries = entries2;
                                size2 = size;
                            }
                            tempMap.remove(key);
                            entries = entries;
                            size2 = size2;
                        }
                        int i4 = size2;
                        bcCount = 0;
                        bcStats.clear();
                        bcCache = tempbcCache;
                        Logger logger2 = logger;
                        Level level = Level.FINEST;
                        if (logger2.isLoggable(level)) {
                            long t2 = System.currentTimeMillis();
                            logger2.log(level, "ByteCache generation time: " + (t2 - t1) + "ms");
                        }
                    } else {
                        bcCount = i + 1;
                        ByteEntry entry3 = new ByteEntry();
                        entry3.value = value;
                        int[] count2 = hashMap.get(entry3);
                        if (count2 == null) {
                            int end = bc.getEnd();
                            int start = bc.getStart();
                            entry3.name = new byte[bc.getLength()];
                            System.arraycopy(bc.getBuffer(), start, entry3.name, 0, end - start);
                            entry3.charset = bc.getCharset();
                            hashMap.put(entry3, new int[]{1});
                        } else {
                            count2[0] = count2[0] + 1;
                        }
                    }
                }
            }
            return value;
        }
        accessCount++;
        String result = find(bc);
        if (result == null) {
            return bc.toStringInternal();
        }
        hitCount++;
        return result;
    }

    public static String toString(CharChunk cc) {
        int size;
        if (ccCache == null) {
            String value = cc.toStringInternal();
            if (charEnabled) {
                HashMap<CharEntry, int[]> hashMap = ccStats;
                synchronized (hashMap) {
                    if (ccCache != null) {
                        return value;
                    }
                    int i = ccCount;
                    if (i > trainThreshold) {
                        long t1 = System.currentTimeMillis();
                        TreeMap<Integer, ArrayList<CharEntry>> tempMap = new TreeMap<>();
                        Iterator<CharEntry> entries = hashMap.keySet().iterator();
                        while (entries.hasNext()) {
                            CharEntry entry = entries.next();
                            Integer count = Integer.valueOf(ccStats.get(entry)[0]);
                            ArrayList<CharEntry> list = tempMap.get(count);
                            if (list == null) {
                                list = new ArrayList<>();
                                tempMap.put(count, list);
                            }
                            list.add(entry);
                        }
                        int size2 = ccStats.size();
                        int i2 = cacheSize;
                        if (size2 > i2) {
                            size2 = i2;
                        }
                        CharEntry[] tempccCache = new CharEntry[size2];
                        CharChunk tempChunk = new CharChunk();
                        int n = 0;
                        while (n < size2) {
                            Object key = tempMap.lastKey();
                            ArrayList<CharEntry> list2 = tempMap.get(key);
                            int i3 = 0;
                            while (i3 < list2.size() && n < size2) {
                                CharEntry entry2 = list2.get(i3);
                                char[] cArr = entry2.name;
                                Iterator<CharEntry> entries2 = entries;
                                tempChunk.setChars(cArr, 0, cArr.length);
                                int insertPos = findClosest(tempChunk, tempccCache, n);
                                if (insertPos == n) {
                                    tempccCache[n + 1] = entry2;
                                    size = size2;
                                } else {
                                    size = size2;
                                    System.arraycopy(tempccCache, insertPos + 1, tempccCache, insertPos + 2, (n - insertPos) - 1);
                                    tempccCache[insertPos + 1] = entry2;
                                }
                                n++;
                                i3++;
                                entries = entries2;
                                size2 = size;
                            }
                            tempMap.remove(key);
                            entries = entries;
                            size2 = size2;
                        }
                        int i4 = size2;
                        ccCount = 0;
                        ccStats.clear();
                        ccCache = tempccCache;
                        Logger logger2 = logger;
                        Level level = Level.FINEST;
                        if (logger2.isLoggable(level)) {
                            long t2 = System.currentTimeMillis();
                            logger2.log(level, "CharCache generation time: " + (t2 - t1) + "ms");
                        }
                    } else {
                        ccCount = i + 1;
                        CharEntry entry3 = new CharEntry();
                        entry3.value = value;
                        int[] count2 = hashMap.get(entry3);
                        if (count2 == null) {
                            int end = cc.getEnd();
                            int start = cc.getStart();
                            entry3.name = new char[cc.getLength()];
                            System.arraycopy(cc.getBuffer(), start, entry3.name, 0, end - start);
                            hashMap.put(entry3, new int[]{1});
                        } else {
                            count2[0] = count2[0] + 1;
                        }
                    }
                }
            }
            return value;
        }
        accessCount++;
        String result = find(cc);
        if (result == null) {
            return cc.toStringInternal();
        }
        hitCount++;
        return result;
    }

    protected static int compare(ByteChunk name, byte[] compareTo) {
        int result = 0;
        byte[] b = name.getBuffer();
        int start = name.getStart();
        int end = name.getEnd();
        int len = compareTo.length;
        if (end - start < len) {
            len = end - start;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (b[i + start] > compareTo[i]) {
                result = 1;
            } else if (b[i + start] < compareTo[i]) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length > end - start) {
            return -1;
        }
        if (compareTo.length < end - start) {
            return 1;
        }
        return result;
    }

    protected static String find(ByteChunk name) {
        ByteEntry[] byteEntryArr = bcCache;
        int pos = findClosest(name, byteEntryArr, byteEntryArr.length);
        if (pos < 0 || compare(name, bcCache[pos].name) != 0 || !name.getCharset().equals(bcCache[pos].charset)) {
            return null;
        }
        return bcCache[pos].value;
    }

    protected static int findClosest(ByteChunk name, ByteEntry[] array, int len) {
        int a = 0;
        int b = len - 1;
        if (b == -1 || compare(name, array[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = compare(name, array[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (compare(name, array[b].name) < 0) {
            return a;
        }
        return b;
    }

    protected static int compare(CharChunk name, char[] compareTo) {
        int result = 0;
        char[] c = name.getBuffer();
        int start = name.getStart();
        int end = name.getEnd();
        int len = compareTo.length;
        if (end - start < len) {
            len = end - start;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (c[i + start] > compareTo[i]) {
                result = 1;
            } else if (c[i + start] < compareTo[i]) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length > end - start) {
            return -1;
        }
        if (compareTo.length < end - start) {
            return 1;
        }
        return result;
    }

    protected static String find(CharChunk name) {
        CharEntry[] charEntryArr = ccCache;
        int pos = findClosest(name, charEntryArr, charEntryArr.length);
        if (pos < 0 || compare(name, ccCache[pos].name) != 0) {
            return null;
        }
        return ccCache[pos].value;
    }

    protected static int findClosest(CharChunk name, CharEntry[] array, int len) {
        int a = 0;
        int b = len - 1;
        if (b == -1 || compare(name, array[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = compare(name, array[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (compare(name, array[b].name) < 0) {
            return a;
        }
        return b;
    }

    public static class ByteEntry {
        public Charset charset = null;
        public byte[] name = null;
        public String value = null;

        protected ByteEntry() {
        }

        public String toString() {
            return this.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public boolean equals(Object obj) {
            return (obj instanceof ByteEntry) && this.value.equals(((ByteEntry) obj).value);
        }
    }

    public static class CharEntry {
        public char[] name = null;
        public String value = null;

        protected CharEntry() {
        }

        public String toString() {
            return this.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public boolean equals(Object obj) {
            return (obj instanceof CharEntry) && this.value.equals(((CharEntry) obj).value);
        }
    }
}
