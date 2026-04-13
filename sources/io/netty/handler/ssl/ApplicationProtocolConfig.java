package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;

public final class ApplicationProtocolConfig {
    public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
    private final Protocol protocol;
    private final SelectedListenerFailureBehavior selectedBehavior;
    private final SelectorFailureBehavior selectorBehavior;
    private final List<String> supportedProtocols;

    public enum Protocol {
        NONE,
        NPN,
        ALPN,
        NPN_AND_ALPN
    }

    public enum SelectedListenerFailureBehavior {
        ACCEPT,
        FATAL_ALERT,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public enum SelectorFailureBehavior {
        FATAL_ALERT,
        NO_ADVERTISE,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorBehavior2, SelectedListenerFailureBehavior selectedBehavior2, Iterable<String> supportedProtocols2) {
        this(protocol2, selectorBehavior2, selectedBehavior2, ApplicationProtocolUtil.toList(supportedProtocols2));
    }

    public ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorBehavior2, SelectedListenerFailureBehavior selectedBehavior2, String... supportedProtocols2) {
        this(protocol2, selectorBehavior2, selectedBehavior2, ApplicationProtocolUtil.toList(supportedProtocols2));
    }

    private ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorBehavior2, SelectedListenerFailureBehavior selectedBehavior2, List<String> supportedProtocols2) {
        this.supportedProtocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(supportedProtocols2, "supportedProtocols"));
        this.protocol = (Protocol) ObjectUtil.checkNotNull(protocol2, "protocol");
        this.selectorBehavior = (SelectorFailureBehavior) ObjectUtil.checkNotNull(selectorBehavior2, "selectorBehavior");
        this.selectedBehavior = (SelectedListenerFailureBehavior) ObjectUtil.checkNotNull(selectedBehavior2, "selectedBehavior");
        Protocol protocol3 = Protocol.NONE;
        if (protocol2 == protocol3) {
            throw new IllegalArgumentException("protocol (" + protocol3 + ") must not be " + protocol3 + '.');
        } else if (supportedProtocols2.isEmpty()) {
            throw new IllegalArgumentException("supportedProtocols must be not empty");
        }
    }

    private ApplicationProtocolConfig() {
        this.supportedProtocols = Collections.emptyList();
        this.protocol = Protocol.NONE;
        this.selectorBehavior = SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        this.selectedBehavior = SelectedListenerFailureBehavior.ACCEPT;
    }

    public List<String> supportedProtocols() {
        return this.supportedProtocols;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.selectorBehavior;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.selectedBehavior;
    }
}
