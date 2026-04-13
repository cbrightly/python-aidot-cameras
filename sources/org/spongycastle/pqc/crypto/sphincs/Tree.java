package org.spongycastle.pqc.crypto.sphincs;

public class Tree {
    Tree() {
    }

    public static class leafaddr {
        int a;
        long b;
        long c;

        public leafaddr() {
        }

        public leafaddr(leafaddr leafaddr) {
            this.a = leafaddr.a;
            this.b = leafaddr.b;
            this.c = leafaddr.c;
        }
    }

    static void b(HashFunctions hs, byte[] leaf, int leafOff, byte[] wots_pk, int pkOff, byte[] masks, int masksOff) {
        int l;
        byte[] bArr = wots_pk;
        int i = pkOff;
        int l2 = 67;
        int i2 = 0;
        while (i2 < 7) {
            int j = 0;
            while (j < (l2 >>> 1)) {
                hs.c(wots_pk, i + (j * 32), wots_pk, i + (j * 2 * 32), masks, masksOff + (i2 * 2 * 32));
                j++;
            }
            if ((l2 & 1) != 0) {
                System.arraycopy(bArr, ((l2 - 1) * 32) + i, bArr, ((l2 >>> 1) * 32) + i, 32);
                l = (l2 >>> 1) + 1;
            } else {
                l = l2 >>> 1;
            }
            l2 = l;
            i2++;
            int i3 = j;
        }
        byte[] bArr2 = leaf;
        int i4 = leafOff;
        System.arraycopy(bArr, i, leaf, leafOff, 32);
    }

    static void c(HashFunctions hs, byte[] node, int nodeOff, int height, byte[] sk, leafaddr leaf, byte[] masks, int masksOff) {
        leafaddr a = new leafaddr(leaf);
        byte[] stack = new byte[((height + 1) * 32)];
        int[] stacklevels = new int[(height + 1)];
        int i = 1;
        int lastnode = (int) (a.c + ((long) (1 << height)));
        int stackoffset = 0;
        while (a.c < ((long) lastnode)) {
            a(hs, stack, stackoffset * 32, masks, masksOff, sk, a);
            stacklevels[stackoffset] = 0;
            int stackoffset2 = stackoffset + 1;
            while (stackoffset2 > i && stacklevels[stackoffset2 - 1] == stacklevels[stackoffset2 - 2]) {
                int i2 = i;
                int[] stacklevels2 = stacklevels;
                hs.c(stack, (stackoffset2 - 2) * 32, stack, (stackoffset2 - 2) * 32, masks, masksOff + ((stacklevels[stackoffset2 - 1] + 7) * 2 * 32));
                int i3 = stackoffset2 - 2;
                stacklevels2[i3] = stacklevels2[i3] + i2;
                stackoffset2--;
                i = i2;
                stack = stack;
                lastnode = lastnode;
                stacklevels = stacklevels2;
            }
            a.c++;
            stackoffset = stackoffset2;
            i = i;
            stack = stack;
            lastnode = lastnode;
            stacklevels = stacklevels;
        }
        int[] iArr = stacklevels;
        byte[] stack2 = stack;
        for (int i4 = 0; i4 < 32; i4++) {
            node[nodeOff + i4] = stack2[i4];
        }
    }

    static void a(HashFunctions hs, byte[] leaf, int leafOff, byte[] masks, int masksOff, byte[] sk, leafaddr a) {
        byte[] seed = new byte[32];
        byte[] pk = new byte[2144];
        Wots w = new Wots();
        HashFunctions hashFunctions = hs;
        Seed.a(hs, seed, 0, sk, a);
        HashFunctions hashFunctions2 = hs;
        byte[] bArr = masks;
        int i = masksOff;
        w.d(hashFunctions2, pk, 0, seed, 0, bArr, i);
        b(hashFunctions2, leaf, leafOff, pk, 0, bArr, i);
    }
}
