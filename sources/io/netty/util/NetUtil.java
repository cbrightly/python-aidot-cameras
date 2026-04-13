package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;

public final class NetUtil {
    private static final int IPV4_MAX_CHAR_BETWEEN_SEPARATOR = 3;
    private static final boolean IPV4_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv4Stack", false);
    private static final int IPV4_SEPARATORS = 3;
    private static final int IPV6_BYTE_COUNT = 16;
    private static final int IPV6_MAX_CHAR_BETWEEN_SEPARATOR = 4;
    private static final int IPV6_MAX_CHAR_COUNT = 39;
    private static final int IPV6_MAX_SEPARATORS = 8;
    private static final int IPV6_MIN_SEPARATORS = 2;
    private static final int IPV6_WORD_COUNT = 8;
    public static final InetAddress LOCALHOST;
    public static final Inet4Address LOCALHOST4;
    public static final Inet6Address LOCALHOST6;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN = ((Integer) AccessController.doPrivileged(new PrivilegedAction<Integer>() {
        public Integer run() {
            int somaxconn = PlatformDependent.isWindows() ? 200 : 128;
            File file = new File("/proc/sys/net/core/somaxconn");
            BufferedReader in = null;
            try {
                if (file.exists()) {
                    in = new BufferedReader(new FileReader(file));
                    somaxconn = Integer.parseInt(in.readLine());
                    if (NetUtil.logger.isDebugEnabled()) {
                        NetUtil.logger.debug("{}: {}", file, Integer.valueOf(somaxconn));
                    }
                } else if (NetUtil.logger.isDebugEnabled()) {
                    NetUtil.logger.debug("{}: {} (non-existent)", file, Integer.valueOf(somaxconn));
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                NetUtil.logger.debug("Failed to get SOMAXCONN from: {}", file, e2);
                if (in != null) {
                    in.close();
                }
            } catch (Throwable th) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e3) {
                    }
                }
                throw th;
            }
            return Integer.valueOf(somaxconn);
        }
    })).intValue();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NetUtil.class);

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0109, code lost:
        if (r9 == null) goto L_0x0119;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0117, code lost:
        if (r9 != null) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0119, code lost:
        logger.debug("Using hard-coded IPv4 localhost address: {}", (java.lang.Object) r5);
        r9 = r5;
     */
    static {
        /*
            java.lang.String r0 = "Failed to find the loopback interface"
            java.lang.String r1 = "Using hard-coded IPv4 localhost address: {}"
            java.lang.String r2 = "java.net.preferIPv4Stack"
            r3 = 0
            boolean r2 = io.netty.util.internal.SystemPropertyUtil.getBoolean(r2, r3)
            IPV4_PREFERRED = r2
            java.lang.Class<io.netty.util.NetUtil> r2 = io.netty.util.NetUtil.class
            io.netty.util.internal.logging.InternalLogger r2 = io.netty.util.internal.logging.InternalLoggerFactory.getInstance((java.lang.Class<?>) r2)
            logger = r2
            r2 = 4
            byte[] r2 = new byte[r2]
            r2 = {127, 0, 0, 1} // fill-array
            r4 = 16
            byte[] r4 = new byte[r4]
            r4 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1} // fill-array
            r5 = 0
            java.net.InetAddress r6 = java.net.InetAddress.getByAddress(r2)     // Catch:{ Exception -> 0x002b }
            java.net.Inet4Address r6 = (java.net.Inet4Address) r6     // Catch:{ Exception -> 0x002b }
            r5 = r6
            goto L_0x002f
        L_0x002b:
            r6 = move-exception
            io.netty.util.internal.PlatformDependent.throwException(r6)
        L_0x002f:
            LOCALHOST4 = r5
            r6 = 0
            java.net.InetAddress r7 = java.net.InetAddress.getByAddress(r4)     // Catch:{ Exception -> 0x003a }
            java.net.Inet6Address r7 = (java.net.Inet6Address) r7     // Catch:{ Exception -> 0x003a }
            r6 = r7
            goto L_0x003e
        L_0x003a:
            r7 = move-exception
            io.netty.util.internal.PlatformDependent.throwException(r7)
        L_0x003e:
            LOCALHOST6 = r6
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Enumeration r8 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch:{ SocketException -> 0x0066 }
            if (r8 == 0) goto L_0x0065
        L_0x004b:
            boolean r9 = r8.hasMoreElements()     // Catch:{ SocketException -> 0x0066 }
            if (r9 == 0) goto L_0x0065
            java.lang.Object r9 = r8.nextElement()     // Catch:{ SocketException -> 0x0066 }
            java.net.NetworkInterface r9 = (java.net.NetworkInterface) r9     // Catch:{ SocketException -> 0x0066 }
            java.util.Enumeration r10 = io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r9)     // Catch:{ SocketException -> 0x0066 }
            boolean r10 = r10.hasMoreElements()     // Catch:{ SocketException -> 0x0066 }
            if (r10 == 0) goto L_0x0064
            r7.add(r9)     // Catch:{ SocketException -> 0x0066 }
        L_0x0064:
            goto L_0x004b
        L_0x0065:
            goto L_0x006e
        L_0x0066:
            r8 = move-exception
            io.netty.util.internal.logging.InternalLogger r9 = logger
            java.lang.String r10 = "Failed to retrieve the list of available network interfaces"
            r9.warn((java.lang.String) r10, (java.lang.Throwable) r8)
        L_0x006e:
            r8 = 0
            r9 = 0
            java.util.Iterator r10 = r7.iterator()
        L_0x0074:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x009b
            java.lang.Object r11 = r10.next()
            java.net.NetworkInterface r11 = (java.net.NetworkInterface) r11
            java.util.Enumeration r12 = io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r11)
        L_0x0084:
            boolean r13 = r12.hasMoreElements()
            if (r13 == 0) goto L_0x009a
            java.lang.Object r13 = r12.nextElement()
            java.net.InetAddress r13 = (java.net.InetAddress) r13
            boolean r14 = r13.isLoopbackAddress()
            if (r14 == 0) goto L_0x0099
            r8 = r11
            r9 = r13
            goto L_0x009b
        L_0x0099:
            goto L_0x0084
        L_0x009a:
            goto L_0x0074
        L_0x009b:
            if (r8 != 0) goto L_0x00d5
            java.util.Iterator r10 = r7.iterator()     // Catch:{ SocketException -> 0x00cf }
        L_0x00a1:
            boolean r11 = r10.hasNext()     // Catch:{ SocketException -> 0x00cf }
            if (r11 == 0) goto L_0x00c7
            java.lang.Object r11 = r10.next()     // Catch:{ SocketException -> 0x00cf }
            java.net.NetworkInterface r11 = (java.net.NetworkInterface) r11     // Catch:{ SocketException -> 0x00cf }
            boolean r12 = r11.isLoopback()     // Catch:{ SocketException -> 0x00cf }
            if (r12 == 0) goto L_0x00c6
            java.util.Enumeration r12 = io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r11)     // Catch:{ SocketException -> 0x00cf }
            boolean r13 = r12.hasMoreElements()     // Catch:{ SocketException -> 0x00cf }
            if (r13 == 0) goto L_0x00c6
            r8 = r11
            java.lang.Object r10 = r12.nextElement()     // Catch:{ SocketException -> 0x00cf }
            java.net.InetAddress r10 = (java.net.InetAddress) r10     // Catch:{ SocketException -> 0x00cf }
            r9 = r10
            goto L_0x00c7
        L_0x00c6:
            goto L_0x00a1
        L_0x00c7:
            if (r8 != 0) goto L_0x00ce
            io.netty.util.internal.logging.InternalLogger r10 = logger     // Catch:{ SocketException -> 0x00cf }
            r10.warn(r0)     // Catch:{ SocketException -> 0x00cf }
        L_0x00ce:
            goto L_0x00d5
        L_0x00cf:
            r10 = move-exception
            io.netty.util.internal.logging.InternalLogger r11 = logger
            r11.warn((java.lang.String) r0, (java.lang.Throwable) r10)
        L_0x00d5:
            if (r8 == 0) goto L_0x00f6
            io.netty.util.internal.logging.InternalLogger r0 = logger
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r10 = r8.getName()
            r1[r3] = r10
            r3 = 1
            java.lang.String r10 = r8.getDisplayName()
            r1[r3] = r10
            r3 = 2
            java.lang.String r10 = r9.getHostAddress()
            r1[r3] = r10
            java.lang.String r3 = "Loopback interface: {} ({}, {})"
            r0.debug((java.lang.String) r3, (java.lang.Object[]) r1)
            goto L_0x011f
        L_0x00f6:
            if (r9 != 0) goto L_0x011f
            java.net.Inet6Address r0 = LOCALHOST6     // Catch:{ Exception -> 0x0116, all -> 0x010c }
            java.net.NetworkInterface r0 = java.net.NetworkInterface.getByInetAddress(r0)     // Catch:{ Exception -> 0x0116, all -> 0x010c }
            if (r0 == 0) goto L_0x0109
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ Exception -> 0x0116, all -> 0x010c }
            java.lang.String r3 = "Using hard-coded IPv6 localhost address: {}"
            r0.debug((java.lang.String) r3, (java.lang.Object) r6)     // Catch:{ Exception -> 0x0116, all -> 0x010c }
            r0 = r6
            r9 = r0
        L_0x0109:
            if (r9 != 0) goto L_0x011f
            goto L_0x0119
        L_0x010c:
            r0 = move-exception
            if (r9 != 0) goto L_0x0115
            io.netty.util.internal.logging.InternalLogger r3 = logger
            r3.debug((java.lang.String) r1, (java.lang.Object) r5)
            r9 = r5
        L_0x0115:
            throw r0
        L_0x0116:
            r0 = move-exception
            if (r9 != 0) goto L_0x011f
        L_0x0119:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            r0.debug((java.lang.String) r1, (java.lang.Object) r5)
            r9 = r5
        L_0x011f:
            LOOPBACK_IF = r8
            LOCALHOST = r9
            io.netty.util.NetUtil$1 r0 = new io.netty.util.NetUtil$1
            r0.<init>()
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            SOMAXCONN = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.NetUtil.<clinit>():void");
    }

    public static boolean isIpV4StackPreferred() {
        return IPV4_PREFERRED;
    }

    public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
        if (isValidIpV4Address(ipAddressString)) {
            return validIpV4ToBytes(ipAddressString);
        }
        if (!isValidIpV6Address(ipAddressString)) {
            return null;
        }
        if (ipAddressString.charAt(0) == '[') {
            ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
        }
        int percentPos = ipAddressString.indexOf(37);
        if (percentPos >= 0) {
            ipAddressString = ipAddressString.substring(0, percentPos);
        }
        return getIPv6ByName(ipAddressString, true);
    }

    private static int decimalDigit(String str, int pos) {
        return str.charAt(pos) - '0';
    }

    private static byte ipv4WordToByte(String ip, int from, int toExclusive) {
        int ret = decimalDigit(ip, from);
        int from2 = from + 1;
        if (from2 == toExclusive) {
            return (byte) ret;
        }
        int ret2 = (ret * 10) + decimalDigit(ip, from2);
        int from3 = from2 + 1;
        if (from3 == toExclusive) {
            return (byte) ret2;
        }
        return (byte) ((ret2 * 10) + decimalDigit(ip, from3));
    }

    static byte[] validIpV4ToBytes(String ip) {
        int indexOf = ip.indexOf(46, 1);
        int i = indexOf;
        int i2 = i + 1;
        int indexOf2 = ip.indexOf(46, i + 2);
        int i3 = indexOf2;
        int i4 = ip.indexOf(46, i3 + 2);
        return new byte[]{ipv4WordToByte(ip, 0, indexOf), ipv4WordToByte(ip, i2, indexOf2), ipv4WordToByte(ip, i3 + 1, i4), ipv4WordToByte(ip, i4 + 1, ip.length())};
    }

    public static boolean isValidIpV6Address(String ip) {
        int start;
        int compressBegin;
        int colons;
        int end = ip.length();
        if (end < 2) {
            return false;
        }
        char c = ip.charAt(0);
        if (c == '[') {
            end--;
            if (ip.charAt(end) != ']') {
                return false;
            }
            start = 1;
            c = ip.charAt(1);
        } else {
            start = 0;
        }
        if (c != ':') {
            colons = 0;
            compressBegin = -1;
        } else if (ip.charAt(start + 1) != ':') {
            return false;
        } else {
            colons = 2;
            compressBegin = start;
            start += 2;
        }
        int wordLen = 0;
        int i = start;
        while (true) {
            if (i < end) {
                char c2 = ip.charAt(i);
                if (!isValidHexChar(c2)) {
                    switch (c2) {
                        case '%':
                            end = i;
                            break;
                        case '.':
                            if ((compressBegin < 0 && colons != 6) || ((colons == 7 && compressBegin >= start) || colons > 7)) {
                                return false;
                            }
                            int ipv4Start = i - wordLen;
                            int j = ipv4Start - 2;
                            if (isValidIPv4MappedChar(ip.charAt(j))) {
                                if (!isValidIPv4MappedChar(ip.charAt(j - 1)) || !isValidIPv4MappedChar(ip.charAt(j - 2)) || !isValidIPv4MappedChar(ip.charAt(j - 3))) {
                                    return false;
                                }
                                j -= 5;
                            }
                            while (j >= start) {
                                char tmpChar = ip.charAt(j);
                                if (tmpChar != '0' && tmpChar != ':') {
                                    return false;
                                }
                                j--;
                            }
                            int ipv4End = ip.indexOf(37, ipv4Start + 7);
                            if (ipv4End < 0) {
                                ipv4End = end;
                            }
                            return isValidIpV4Address(ip, ipv4Start, ipv4End);
                        case ':':
                            if (colons > 7) {
                                return false;
                            }
                            if (ip.charAt(i - 1) != ':') {
                                wordLen = 0;
                            } else if (compressBegin >= 0) {
                                return false;
                            } else {
                                compressBegin = i - 1;
                            }
                            colons++;
                            continue;
                        default:
                            return false;
                    }
                } else if (wordLen >= 4) {
                    return false;
                } else {
                    wordLen++;
                }
                i++;
            }
        }
        if (compressBegin >= 0) {
            if (compressBegin + 2 != end) {
                if (wordLen <= 0) {
                    return false;
                }
                if (colons >= 8 && compressBegin > start) {
                    return false;
                }
            }
            return true;
        } else if (colons != 7 || wordLen <= 0) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidIpV4Word(CharSequence word, int from, int toExclusive) {
        int len = toExclusive - from;
        if (len >= 1 && len <= 3) {
            char charAt = word.charAt(from);
            char c0 = charAt;
            if (charAt >= '0') {
                if (len == 3) {
                    char charAt2 = word.charAt(from + 1);
                    char c1 = charAt2;
                    if (charAt2 < '0') {
                        return false;
                    }
                    char charAt3 = word.charAt(from + 2);
                    char c2 = charAt3;
                    if (charAt3 < '0') {
                        return false;
                    }
                    if (c0 > '1' || c1 > '9' || c2 > '9') {
                        if (c0 != '2' || c1 > '5') {
                            return false;
                        }
                        if (c2 > '5' && (c1 >= '5' || c2 > '9')) {
                            return false;
                        }
                    }
                    return true;
                } else if (c0 > '9') {
                    return false;
                } else {
                    if (len == 1 || isValidNumericChar(word.charAt(from + 1))) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean isValidHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    private static boolean isValidNumericChar(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isValidIPv4MappedChar(char c) {
        return c == 'f' || c == 'F';
    }

    private static boolean isValidIPv4MappedSeparators(byte b0, byte b1, boolean mustBeZero) {
        return b0 == b1 && (b0 == 0 || (!mustBeZero && b1 == -1));
    }

    private static boolean isValidIPv4Mapped(byte[] bytes, int currentIndex, int compressBegin, int compressLength) {
        boolean mustBeZero = compressBegin + compressLength >= 14;
        if (currentIndex > 12 || currentIndex < 2 || ((mustBeZero && compressBegin >= 12) || !isValidIPv4MappedSeparators(bytes[currentIndex - 1], bytes[currentIndex - 2], mustBeZero) || !PlatformDependent.isZero(bytes, 0, currentIndex - 3))) {
            return false;
        }
        return true;
    }

    public static boolean isValidIpV4Address(String ip) {
        return isValidIpV4Address(ip, 0, ip.length());
    }

    private static boolean isValidIpV4Address(String ip, int from, int toExcluded) {
        int len = toExcluded - from;
        if (len <= 15 && len >= 7) {
            int indexOf = ip.indexOf(46, from + 1);
            int i = indexOf;
            if (indexOf > 0 && isValidIpV4Word(ip, from, i)) {
                int i2 = i + 2;
                int from2 = i2;
                int indexOf2 = ip.indexOf(46, i2);
                int i3 = indexOf2;
                if (indexOf2 > 0 && isValidIpV4Word(ip, from2 - 1, i3)) {
                    int i4 = i3 + 2;
                    int from3 = i4;
                    int indexOf3 = ip.indexOf(46, i4);
                    int i5 = indexOf3;
                    return indexOf3 > 0 && isValidIpV4Word(ip, from3 + -1, i5) && isValidIpV4Word(ip, i5 + 1, toExcluded);
                }
            }
        }
    }

    public static Inet6Address getByName(CharSequence ip) {
        return getByName(ip, true);
    }

    public static Inet6Address getByName(CharSequence ip, boolean ipv4Mapped) {
        byte[] bytes = getIPv6ByName(ip, ipv4Mapped);
        if (bytes == null) {
            return null;
        }
        try {
            return Inet6Address.getByAddress((String) null, bytes, -1);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0165, code lost:
        if ((r8 - r7) <= 3) goto L_0x0169;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] getIPv6ByName(java.lang.CharSequence r21, boolean r22) {
        /*
            r0 = r21
            r1 = 16
            byte[] r1 = new byte[r1]
            int r2 = r21.length()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = -1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x0013:
            r15 = 8
            r18 = 0
            r13 = 4
            r14 = 2
            if (r8 >= r2) goto L_0x0159
            char r19 = r0.charAt(r8)
            switch(r19) {
                case 46: goto L_0x009e;
                case 58: goto L_0x0032;
                default: goto L_0x0022;
            }
        L_0x0022:
            boolean r12 = isValidHexChar(r19)
            if (r12 == 0) goto L_0x0158
            if (r10 <= 0) goto L_0x0142
            boolean r12 = isValidNumericChar(r19)
            if (r12 != 0) goto L_0x0142
            goto L_0x0158
        L_0x0032:
            int r9 = r9 + 1
            int r12 = r8 - r7
            if (r12 > r13) goto L_0x009d
            if (r10 > 0) goto L_0x009d
            if (r9 > r15) goto L_0x009d
            int r12 = r5 + 1
            int r15 = r1.length
            if (r12 < r15) goto L_0x0042
            goto L_0x009d
        L_0x0042:
            int r12 = r8 - r7
            int r12 = 4 - r12
            int r12 = r12 << r14
            int r6 = r6 << r12
            if (r4 <= 0) goto L_0x004c
            int r4 = r4 + -2
        L_0x004c:
            int r12 = r5 + 1
            r15 = r6 & 15
            int r15 = r15 << r13
            int r16 = r6 >> 4
            r16 = r16 & 15
            r15 = r15 | r16
            byte r15 = (byte) r15
            r1[r5] = r15
            int r5 = r12 + 1
            int r15 = r6 >> 8
            r15 = r15 & 15
            int r13 = r15 << 4
            int r15 = r6 >> 12
            r15 = r15 & 15
            r13 = r13 | r15
            byte r13 = (byte) r13
            r1[r12] = r13
            int r12 = r8 + 1
            if (r12 >= r2) goto L_0x0099
            char r13 = r0.charAt(r12)
            r15 = 58
            if (r13 != r15) goto L_0x0099
            int r12 = r12 + 1
            if (r3 != 0) goto L_0x0098
            if (r12 >= r2) goto L_0x0083
            char r13 = r0.charAt(r12)
            if (r13 != r15) goto L_0x0083
            goto L_0x0098
        L_0x0083:
            int r9 = r9 + 1
            if (r9 != r14) goto L_0x008c
            if (r6 != 0) goto L_0x008c
            r17 = 1
            goto L_0x008e
        L_0x008c:
            r17 = 0
        L_0x008e:
            r11 = r17
            r3 = r5
            int r13 = r1.length
            int r13 = r13 - r3
            int r4 = r13 + -2
            int r8 = r8 + 1
            goto L_0x0099
        L_0x0098:
            return r18
        L_0x0099:
            r6 = 0
            r7 = -1
            goto L_0x0154
        L_0x009d:
            return r18
        L_0x009e:
            int r10 = r10 + 1
            int r12 = r8 - r7
            r13 = 3
            if (r12 > r13) goto L_0x0141
            if (r7 < 0) goto L_0x0141
            if (r10 > r13) goto L_0x0141
            if (r9 <= 0) goto L_0x00b1
            int r13 = r5 + r4
            r15 = 12
            if (r13 < r15) goto L_0x0141
        L_0x00b1:
            int r13 = r8 + 1
            if (r13 >= r2) goto L_0x0141
            int r13 = r1.length
            if (r5 >= r13) goto L_0x0141
            r13 = 1
            if (r10 != r13) goto L_0x0116
            if (r22 == 0) goto L_0x0141
            if (r5 == 0) goto L_0x00c5
            boolean r13 = isValidIPv4Mapped(r1, r5, r3, r4)
            if (r13 == 0) goto L_0x0141
        L_0x00c5:
            r13 = 3
            if (r12 != r13) goto L_0x00ec
            int r13 = r8 + -1
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 == 0) goto L_0x0141
            int r13 = r8 + -2
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 == 0) goto L_0x0141
            int r13 = r8 + -3
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 == 0) goto L_0x0141
        L_0x00ec:
            if (r12 != r14) goto L_0x0106
            int r13 = r8 + -1
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 == 0) goto L_0x0141
            int r13 = r8 + -2
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 == 0) goto L_0x0141
        L_0x0106:
            r13 = 1
            if (r12 != r13) goto L_0x0116
            int r13 = r8 + -1
            char r13 = r0.charAt(r13)
            boolean r13 = isValidNumericChar(r13)
            if (r13 != 0) goto L_0x0116
            goto L_0x0141
        L_0x0116:
            int r13 = 3 - r12
            int r13 = r13 << r14
            int r6 = r6 << r13
            r13 = r6 & 15
            int r13 = r13 * 100
            int r14 = r6 >> 4
            r14 = r14 & 15
            r15 = 10
            int r14 = r14 * r15
            int r13 = r13 + r14
            int r14 = r6 >> 8
            r14 = r14 & 15
            int r13 = r13 + r14
            if (r13 < 0) goto L_0x0140
            r7 = 255(0xff, float:3.57E-43)
            if (r13 <= r7) goto L_0x0132
            goto L_0x0140
        L_0x0132:
            int r7 = r5 + 1
            byte r14 = (byte) r13
            r1[r5] = r14
            r5 = 0
            r6 = -1
            r20 = r6
            r6 = r5
            r5 = r7
            r7 = r20
            goto L_0x0154
        L_0x0140:
            return r18
        L_0x0141:
            return r18
        L_0x0142:
            if (r7 >= 0) goto L_0x0146
            r7 = r8
            goto L_0x014b
        L_0x0146:
            int r12 = r8 - r7
            if (r12 <= r13) goto L_0x014b
            return r18
        L_0x014b:
            int r12 = io.netty.util.internal.StringUtil.decodeHexNibble(r19)
            int r13 = r8 - r7
            int r13 = r13 << r14
            int r12 = r12 << r13
            int r6 = r6 + r12
        L_0x0154:
            r12 = 1
            int r8 = r8 + r12
            goto L_0x0013
        L_0x0158:
            return r18
        L_0x0159:
            if (r3 <= 0) goto L_0x015d
            r12 = 1
            goto L_0x015e
        L_0x015d:
            r12 = 0
        L_0x015e:
            if (r10 <= 0) goto L_0x01c1
            if (r7 <= 0) goto L_0x0168
            int r13 = r8 - r7
            r15 = 3
            if (r13 > r15) goto L_0x01c0
            goto L_0x0169
        L_0x0168:
            r15 = 3
        L_0x0169:
            if (r10 != r15) goto L_0x01c0
            int r13 = r1.length
            if (r5 < r13) goto L_0x016f
            goto L_0x01c0
        L_0x016f:
            if (r9 != 0) goto L_0x0174
            r4 = 12
            goto L_0x0197
        L_0x0174:
            if (r9 < r14) goto L_0x01bf
            if (r12 != 0) goto L_0x0184
            r13 = 6
            if (r9 != r13) goto L_0x0184
            r13 = 0
            char r15 = r0.charAt(r13)
            r13 = 58
            if (r15 != r13) goto L_0x0195
        L_0x0184:
            if (r12 == 0) goto L_0x01bf
            r13 = 8
            if (r9 >= r13) goto L_0x01bf
            r13 = 0
            char r15 = r0.charAt(r13)
            r13 = 58
            if (r15 != r13) goto L_0x0195
            if (r3 > r14) goto L_0x01bf
        L_0x0195:
            int r4 = r4 + -2
        L_0x0197:
            int r13 = r8 - r7
            r15 = 3
            int r13 = 3 - r13
            int r13 = r13 << r14
            int r6 = r6 << r13
            r13 = r6 & 15
            int r13 = r13 * 100
            int r14 = r6 >> 4
            r14 = r14 & 15
            r15 = 10
            int r14 = r14 * r15
            int r13 = r13 + r14
            int r14 = r6 >> 8
            r14 = r14 & 15
            int r7 = r13 + r14
            if (r7 < 0) goto L_0x01be
            r13 = 255(0xff, float:3.57E-43)
            if (r7 <= r13) goto L_0x01b7
            goto L_0x01be
        L_0x01b7:
            int r13 = r5 + 1
            byte r14 = (byte) r7
            r1[r5] = r14
            goto L_0x0255
        L_0x01be:
            return r18
        L_0x01bf:
            return r18
        L_0x01c0:
            return r18
        L_0x01c1:
            int r15 = r2 + -1
            if (r7 <= 0) goto L_0x01c9
            int r14 = r8 - r7
            if (r14 > r13) goto L_0x029d
        L_0x01c9:
            r14 = 2
            if (r9 < r14) goto L_0x029d
            if (r12 != 0) goto L_0x01e3
            int r14 = r9 + 1
            r13 = 8
            if (r14 != r13) goto L_0x029d
            r13 = 0
            char r14 = r0.charAt(r13)
            r13 = 58
            if (r14 == r13) goto L_0x029d
            char r14 = r0.charAt(r15)
            if (r14 == r13) goto L_0x029d
        L_0x01e3:
            if (r12 == 0) goto L_0x0203
            r13 = 8
            if (r9 > r13) goto L_0x029d
            if (r9 != r13) goto L_0x0203
            r13 = 2
            if (r3 > r13) goto L_0x01f7
            r13 = 0
            char r14 = r0.charAt(r13)
            r13 = 58
            if (r14 != r13) goto L_0x029d
        L_0x01f7:
            r13 = 14
            if (r3 < r13) goto L_0x0203
            char r13 = r0.charAt(r15)
            r14 = 58
            if (r13 != r14) goto L_0x029d
        L_0x0203:
            int r13 = r5 + 1
            int r14 = r1.length
            if (r13 >= r14) goto L_0x029d
            if (r7 >= 0) goto L_0x0214
            int r13 = r15 + -1
            char r13 = r0.charAt(r13)
            r14 = 58
            if (r13 != r14) goto L_0x029d
        L_0x0214:
            r13 = 2
            if (r3 <= r13) goto L_0x0222
            r13 = 0
            char r14 = r0.charAt(r13)
            r13 = 58
            if (r14 != r13) goto L_0x0222
            goto L_0x029d
        L_0x0222:
            if (r7 < 0) goto L_0x0230
            int r13 = r8 - r7
            r14 = 4
            if (r13 > r14) goto L_0x0230
            int r13 = r8 - r7
            int r13 = 4 - r13
            r14 = 2
            int r13 = r13 << r14
            int r6 = r6 << r13
        L_0x0230:
            int r13 = r5 + 1
            r14 = r6 & 15
            r16 = 4
            int r14 = r14 << 4
            int r16 = r6 >> 4
            r16 = r16 & 15
            r14 = r14 | r16
            byte r14 = (byte) r14
            r1[r5] = r14
            int r5 = r13 + 1
            int r14 = r6 >> 8
            r14 = r14 & 15
            r16 = 4
            int r14 = r14 << 4
            int r16 = r6 >> 12
            r16 = r16 & 15
            r14 = r14 | r16
            byte r14 = (byte) r14
            r1[r13] = r14
            r13 = r5
        L_0x0255:
            int r5 = r13 + r4
            if (r11 != 0) goto L_0x0271
            int r8 = r1.length
            if (r5 < r8) goto L_0x025d
            goto L_0x0271
        L_0x025d:
            r5 = 0
        L_0x025e:
            if (r5 >= r4) goto L_0x0291
            int r7 = r5 + r3
            int r13 = r7 + r4
            int r8 = r1.length
            if (r13 >= r8) goto L_0x0291
            byte r8 = r1[r7]
            r1[r13] = r8
            r8 = 0
            r1[r7] = r8
            int r5 = r5 + 1
            goto L_0x025e
        L_0x0271:
            int r8 = r1.length
            if (r5 < r8) goto L_0x0276
            int r3 = r3 + 1
        L_0x0276:
            r5 = r13
        L_0x0277:
            int r8 = r1.length
            if (r5 >= r8) goto L_0x0291
            int r8 = r1.length
            r14 = 1
            int r8 = r8 - r14
            r7 = r8
        L_0x027e:
            if (r7 < r3) goto L_0x0289
            int r8 = r7 + -1
            byte r8 = r1[r8]
            r1[r7] = r8
            int r7 = r7 + -1
            goto L_0x027e
        L_0x0289:
            r8 = 0
            r1[r7] = r8
            int r3 = r3 + 1
            int r5 = r5 + 1
            goto L_0x0277
        L_0x0291:
            if (r10 <= 0) goto L_0x029c
            r8 = 11
            r14 = -1
            r1[r8] = r14
            r8 = 10
            r1[r8] = r14
        L_0x029c:
            return r1
        L_0x029d:
            return r18
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.NetUtil.getIPv6ByName(java.lang.CharSequence, boolean):byte[]");
    }

    public static String toSocketAddressString(InetSocketAddress addr) {
        StringBuilder sb;
        String port = String.valueOf(addr.getPort());
        if (addr.isUnresolved()) {
            String hostString = PlatformDependent.javaVersion() >= 7 ? addr.getHostString() : addr.getHostName();
            sb = newSocketAddressStringBuilder(hostString, port, !isValidIpV6Address(hostString));
        } else {
            InetAddress address = addr.getAddress();
            sb = newSocketAddressStringBuilder(toAddressString(address), port, address instanceof Inet4Address);
        }
        sb.append(':');
        sb.append(port);
        return sb.toString();
    }

    public static String toSocketAddressString(String host, int port) {
        String portStr = String.valueOf(port);
        StringBuilder newSocketAddressStringBuilder = newSocketAddressStringBuilder(host, portStr, !isValidIpV6Address(host));
        newSocketAddressStringBuilder.append(':');
        newSocketAddressStringBuilder.append(portStr);
        return newSocketAddressStringBuilder.toString();
    }

    private static StringBuilder newSocketAddressStringBuilder(String host, String port, boolean ipv4) {
        int hostLen = host.length();
        if (ipv4) {
            StringBuilder sb = new StringBuilder(hostLen + 1 + port.length());
            sb.append(host);
            return sb;
        }
        StringBuilder stringBuilder = new StringBuilder(hostLen + 3 + port.length());
        if (hostLen > 1 && host.charAt(0) == '[' && host.charAt(hostLen - 1) == ']') {
            stringBuilder.append(host);
            return stringBuilder;
        }
        stringBuilder.append('[');
        stringBuilder.append(host);
        stringBuilder.append(']');
        return stringBuilder;
    }

    public static String toAddressString(InetAddress ip) {
        return toAddressString(ip, false);
    }

    public static String toAddressString(InetAddress ip, boolean ipv4Mapped) {
        boolean isIpv4Mapped;
        int currentLength;
        InetAddress inetAddress = ip;
        if (inetAddress instanceof Inet4Address) {
            return ip.getHostAddress();
        }
        if (inetAddress instanceof Inet6Address) {
            byte[] bytes = ip.getAddress();
            int[] words = new int[8];
            for (int i = 0; i < words.length; i++) {
                words[i] = ((bytes[i << 1] & 255) << 8) | (bytes[(i << 1) + 1] & 255);
            }
            int currentStart = -1;
            int shortestStart = -1;
            int shortestLength = 0;
            int i2 = 0;
            while (i2 < words.length) {
                if (words[i2] == 0) {
                    if (currentStart < 0) {
                        currentStart = i2;
                    }
                } else if (currentStart >= 0) {
                    int currentLength2 = i2 - currentStart;
                    if (currentLength2 > shortestLength) {
                        shortestStart = currentStart;
                        shortestLength = currentLength2;
                    }
                    currentStart = -1;
                }
                i2++;
            }
            if (currentStart >= 0 && (currentLength = i2 - currentStart) > shortestLength) {
                shortestStart = currentStart;
                shortestLength = currentLength;
            }
            if (shortestLength == 1) {
                shortestLength = 0;
                shortestStart = -1;
            }
            int shortestEnd = shortestStart + shortestLength;
            StringBuilder b = new StringBuilder(39);
            if (shortestEnd < 0) {
                b.append(Integer.toHexString(words[0]));
                for (int i3 = 1; i3 < words.length; i3++) {
                    b.append(':');
                    b.append(Integer.toHexString(words[i3]));
                }
            } else {
                if (inRangeEndExclusive(0, shortestStart, shortestEnd)) {
                    b.append("::");
                    isIpv4Mapped = ipv4Mapped && shortestEnd == 5 && words[5] == 65535;
                } else {
                    b.append(Integer.toHexString(words[0]));
                    isIpv4Mapped = false;
                }
                int i4 = 1;
                while (i4 < words.length) {
                    if (!inRangeEndExclusive(i4, shortestStart, shortestEnd)) {
                        if (!inRangeEndExclusive(i4 - 1, shortestStart, shortestEnd)) {
                            if (!isIpv4Mapped || i4 == 6) {
                                b.append(':');
                            } else {
                                b.append('.');
                            }
                        }
                        if (!isIpv4Mapped || i4 <= 5) {
                            b.append(Integer.toHexString(words[i4]));
                        } else {
                            b.append(words[i4] >> 8);
                            b.append('.');
                            b.append(words[i4] & 255);
                        }
                    } else if (!inRangeEndExclusive(i4 - 1, shortestStart, shortestEnd)) {
                        b.append("::");
                    }
                    i4++;
                }
                int i5 = i4;
            }
            return b.toString();
        }
        throw new IllegalArgumentException("Unhandled type: " + ip.getClass());
    }

    private static boolean inRangeEndExclusive(int value, int start, int end) {
        return value >= start && value < end;
    }

    private NetUtil() {
    }
}
