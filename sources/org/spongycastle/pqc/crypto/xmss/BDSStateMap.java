package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.util.Integers;

public class BDSStateMap implements Serializable {
    private final Map<Integer, BDS> bdsState = new TreeMap();

    BDSStateMap() {
    }

    BDSStateMap(XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed) {
        for (long index = 0; index < globalIndex; index++) {
            a(params, index, publicSeed, secretKeySeed);
        }
    }

    BDSStateMap(BDSStateMap stateMap, XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed) {
        for (Integer key : stateMap.bdsState.keySet()) {
            this.bdsState.put(key, stateMap.bdsState.get(key));
        }
        a(params, globalIndex, publicSeed, secretKeySeed);
    }

    private void a(XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed) {
        XMSSParameters xmssParams = params.h();
        int xmssHeight = xmssParams.d();
        long indexTree = XMSSUtil.j(globalIndex, xmssHeight);
        int indexLeaf = XMSSUtil.i(globalIndex, xmssHeight);
        OTSHashAddress otsHashAddress = (OTSHashAddress) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().h(indexTree)).p(indexLeaf).l();
        if (indexLeaf < (1 << xmssHeight) - 1) {
            if (get(0) == null || indexLeaf == 0) {
                put(0, new BDS(xmssParams, publicSeed, secretKeySeed, otsHashAddress));
            }
            update(0, publicSeed, secretKeySeed, otsHashAddress);
        }
        for (int layer = 1; layer < params.d(); layer++) {
            int indexLeaf2 = XMSSUtil.i(indexTree, xmssHeight);
            indexTree = XMSSUtil.j(indexTree, xmssHeight);
            OTSHashAddress otsHashAddress2 = (OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(layer)).h(indexTree)).p(indexLeaf2).l();
            if (indexLeaf2 < (1 << xmssHeight) - 1 && XMSSUtil.m(globalIndex, xmssHeight, layer)) {
                if (get(layer) == null) {
                    put(layer, new BDS(params.h(), publicSeed, secretKeySeed, otsHashAddress2));
                }
                update(layer, publicSeed, secretKeySeed, otsHashAddress2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setXMSS(XMSSParameters xmss) {
        for (Integer key : this.bdsState.keySet()) {
            BDS bds = this.bdsState.get(key);
            bds.setXMSS(xmss);
            bds.validate();
        }
    }

    public boolean isEmpty() {
        return this.bdsState.isEmpty();
    }

    public BDS get(int index) {
        return this.bdsState.get(Integers.b(index));
    }

    public BDS update(int index, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress) {
        return this.bdsState.put(Integers.b(index), this.bdsState.get(Integers.b(index)).getNextState(publicSeed, secretKeySeed, otsHashAddress));
    }

    public void put(int index, BDS bds) {
        this.bdsState.put(Integers.b(index), bds);
    }
}
