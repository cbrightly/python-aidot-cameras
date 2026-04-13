package org.spongycastle.jce.provider;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXPolicyNode implements PolicyNode {
    protected List a;
    protected int b;
    protected Set c;
    protected PolicyNode d;
    protected Set e;
    protected String f;
    protected boolean g;

    public PKIXPolicyNode(List _children, int _depth, Set _expectedPolicies, PolicyNode _parent, Set _policyQualifiers, String _validPolicy, boolean _critical) {
        this.a = _children;
        this.b = _depth;
        this.c = _expectedPolicies;
        this.d = _parent;
        this.e = _policyQualifiers;
        this.f = _validPolicy;
        this.g = _critical;
    }

    public void a(PKIXPolicyNode _child) {
        this.a.add(_child);
        _child.f(this);
    }

    public Iterator getChildren() {
        return this.a.iterator();
    }

    public int getDepth() {
        return this.b;
    }

    public Set getExpectedPolicies() {
        return this.c;
    }

    public PolicyNode getParent() {
        return this.d;
    }

    public Set getPolicyQualifiers() {
        return this.e;
    }

    public String getValidPolicy() {
        return this.f;
    }

    public boolean c() {
        return !this.a.isEmpty();
    }

    public boolean isCritical() {
        return this.g;
    }

    public void d(PKIXPolicyNode _child) {
        this.a.remove(_child);
    }

    public void e(boolean _critical) {
        this.g = _critical;
    }

    public void f(PKIXPolicyNode _parent) {
        this.d = _parent;
    }

    public String toString() {
        return g("");
    }

    public String g(String _indent) {
        StringBuffer _buf = new StringBuffer();
        _buf.append(_indent);
        _buf.append(this.f);
        _buf.append(" {\n");
        for (int i = 0; i < this.a.size(); i++) {
            _buf.append(((PKIXPolicyNode) this.a.get(i)).g(_indent + "    "));
        }
        _buf.append(_indent);
        _buf.append("}\n");
        return _buf.toString();
    }

    public Object clone() {
        return b();
    }

    public PKIXPolicyNode b() {
        Set _expectedPolicies = new HashSet();
        for (String str : this.c) {
            _expectedPolicies.add(new String(str));
        }
        Set _policyQualifiers = new HashSet();
        for (String str2 : this.e) {
            _policyQualifiers.add(new String(str2));
        }
        PKIXPolicyNode _node = new PKIXPolicyNode(new ArrayList(), this.b, _expectedPolicies, (PolicyNode) null, _policyQualifiers, new String(this.f), this.g);
        for (PKIXPolicyNode b2 : this.a) {
            PKIXPolicyNode _child = b2.b();
            _child.f(_node);
            _node.a(_child);
        }
        return _node;
    }
}
