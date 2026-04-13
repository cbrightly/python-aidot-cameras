package io.netty.handler.ipfilter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;

@ChannelHandler.Sharable
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final IpFilterRule[] rules;

    public RuleBasedIpFilter(IpFilterRule... rules2) {
        if (rules2 != null) {
            this.rules = rules2;
            return;
        }
        throw new NullPointerException("rules");
    }

    /* access modifiers changed from: protected */
    public boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) {
        IpFilterRule rule;
        IpFilterRule[] ipFilterRuleArr = this.rules;
        int length = ipFilterRuleArr.length;
        int i = 0;
        while (i < length && (rule = ipFilterRuleArr[i]) != null) {
            if (rule.matches(remoteAddress)) {
                return rule.ruleType() == IpFilterRuleType.ACCEPT;
            }
            i++;
        }
        return true;
    }
}
