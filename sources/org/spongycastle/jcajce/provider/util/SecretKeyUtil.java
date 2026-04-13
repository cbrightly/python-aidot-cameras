package org.spongycastle.jcajce.provider.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.util.Integers;

public class SecretKeyUtil {
    private static Map a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(PKCSObjectIdentifiers.m0.s(), Integers.b(Opcodes.CHECKCAST));
        a.put(NISTObjectIdentifiers.u, Integers.b(128));
        a.put(NISTObjectIdentifiers.C, Integers.b(Opcodes.CHECKCAST));
        a.put(NISTObjectIdentifiers.K, Integers.b(256));
        a.put(NTTObjectIdentifiers.a, Integers.b(128));
        a.put(NTTObjectIdentifiers.b, Integers.b(Opcodes.CHECKCAST));
        a.put(NTTObjectIdentifiers.c, Integers.b(256));
    }
}
