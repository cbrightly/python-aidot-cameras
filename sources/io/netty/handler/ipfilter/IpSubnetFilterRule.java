package io.netty.handler.ipfilter;

import io.netty.util.internal.SocketUtils;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class IpSubnetFilterRule implements IpFilterRule {
    private final IpFilterRule filterRule;

    public IpSubnetFilterRule(String ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
        try {
            this.filterRule = selectFilterRule(SocketUtils.addressByName(ipAddress), cidrPrefix, ruleType);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("ipAddress", e);
        }
    }

    public IpSubnetFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
        this.filterRule = selectFilterRule(ipAddress, cidrPrefix, ruleType);
    }

    private static IpFilterRule selectFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
        if (ipAddress == null) {
            throw new NullPointerException("ipAddress");
        } else if (ruleType == null) {
            throw new NullPointerException("ruleType");
        } else if (ipAddress instanceof Inet4Address) {
            return new Ip4SubnetFilterRule((Inet4Address) ipAddress, cidrPrefix, ruleType);
        } else {
            if (ipAddress instanceof Inet6Address) {
                return new Ip6SubnetFilterRule((Inet6Address) ipAddress, cidrPrefix, ruleType);
            }
            throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
        }
    }

    public boolean matches(InetSocketAddress remoteAddress) {
        return this.filterRule.matches(remoteAddress);
    }

    public IpFilterRuleType ruleType() {
        return this.filterRule.ruleType();
    }

    public static final class Ip4SubnetFilterRule implements IpFilterRule {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int networkAddress;
        private final IpFilterRuleType ruleType;
        private final int subnetMask;

        private Ip4SubnetFilterRule(Inet4Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType2) {
            if (cidrPrefix < 0 || cidrPrefix > 32) {
                throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", new Object[]{Integer.valueOf(cidrPrefix)}));
            }
            int prefixToSubnetMask = prefixToSubnetMask(cidrPrefix);
            this.subnetMask = prefixToSubnetMask;
            this.networkAddress = prefixToSubnetMask & ipToInt(ipAddress);
            this.ruleType = ruleType2;
        }

        public boolean matches(InetSocketAddress remoteAddress) {
            return (this.subnetMask & ipToInt((Inet4Address) remoteAddress.getAddress())) == this.networkAddress;
        }

        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static int ipToInt(Inet4Address ipAddress) {
            byte[] octets = ipAddress.getAddress();
            if (octets.length == 4) {
                return ((octets[0] & 255) << 24) | ((octets[1] & 255) << MappingData.PATH) | ((octets[2] & 255) << 8) | (octets[3] & 255);
            }
            throw new AssertionError();
        }

        private static int prefixToSubnetMask(int cidrPrefix) {
            return (int) ((-1 << (32 - cidrPrefix)) & -1);
        }
    }

    public static final class Ip6SubnetFilterRule implements IpFilterRule {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);
        private final BigInteger networkAddress;
        private final IpFilterRuleType ruleType;
        private final BigInteger subnetMask;

        private Ip6SubnetFilterRule(Inet6Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType2) {
            if (cidrPrefix < 0 || cidrPrefix > 128) {
                throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", new Object[]{Integer.valueOf(cidrPrefix)}));
            }
            BigInteger prefixToSubnetMask = prefixToSubnetMask(cidrPrefix);
            this.subnetMask = prefixToSubnetMask;
            this.networkAddress = ipToInt(ipAddress).and(prefixToSubnetMask);
            this.ruleType = ruleType2;
        }

        public boolean matches(InetSocketAddress remoteAddress) {
            return ipToInt((Inet6Address) remoteAddress.getAddress()).and(this.subnetMask).equals(this.networkAddress);
        }

        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static BigInteger ipToInt(Inet6Address ipAddress) {
            byte[] octets = ipAddress.getAddress();
            if (octets.length == 16) {
                return new BigInteger(octets);
            }
            throw new AssertionError();
        }

        private static BigInteger prefixToSubnetMask(int cidrPrefix) {
            return MINUS_ONE.shiftLeft(128 - cidrPrefix);
        }
    }
}
