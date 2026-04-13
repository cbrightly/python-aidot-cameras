package org.spongycastle.crypto.engines;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.NaccacheSternKeyParameters;
import org.spongycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class NaccacheSternEngine implements AsymmetricBlockCipher {
    private static BigInteger a = BigInteger.valueOf(0);
    private static BigInteger b = BigInteger.valueOf(1);
    private boolean c;
    private NaccacheSternKeyParameters d;
    private Vector[] e = null;
    private boolean f = false;

    public void a(boolean forEncryption, CipherParameters param) {
        this.c = forEncryption;
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        this.d = (NaccacheSternKeyParameters) param;
        if (!this.c) {
            if (this.f) {
                System.out.println("Constructing lookup Array");
            }
            NaccacheSternPrivateKeyParameters priv = (NaccacheSternPrivateKeyParameters) this.d;
            Vector primes = priv.f();
            this.e = new Vector[primes.size()];
            for (int i = 0; i < primes.size(); i++) {
                BigInteger actualPrime = (BigInteger) primes.elementAt(i);
                int actualPrimeValue = actualPrime.intValue();
                this.e[i] = new Vector();
                this.e[i].addElement(b);
                if (this.f) {
                    PrintStream printStream = System.out;
                    printStream.println("Constructing lookup ArrayList for " + actualPrimeValue);
                }
                BigInteger accJ = a;
                for (int j = 1; j < actualPrimeValue; j++) {
                    accJ = accJ.add(priv.e());
                    this.e[i].addElement(priv.b().modPow(accJ.divide(actualPrime), priv.d()));
                }
            }
        }
    }

    public int c() {
        if (this.c) {
            return ((this.d.c() + 7) / 8) - 1;
        }
        return this.d.d().toByteArray().length;
    }

    public int b() {
        if (this.c) {
            return this.d.d().toByteArray().length;
        }
        return ((this.d.c() + 7) / 8) - 1;
    }

    public byte[] d(byte[] in, int inOff, int len) {
        byte[] block;
        if (this.d == null) {
            throw new IllegalStateException("NaccacheStern engine not initialised");
        } else if (len > c() + 1) {
            throw new DataLengthException("input too large for Naccache-Stern cipher.\n");
        } else if (this.c || len >= c()) {
            if (inOff == 0 && len == in.length) {
                block = in;
            } else {
                block = new byte[len];
                System.arraycopy(in, inOff, block, 0, len);
            }
            BigInteger input = new BigInteger(1, block);
            if (this.f) {
                PrintStream printStream = System.out;
                printStream.println("input as BigInteger: " + input);
            }
            if (this.c) {
                return f(input);
            }
            Vector plain = new Vector();
            NaccacheSternPrivateKeyParameters priv = (NaccacheSternPrivateKeyParameters) this.d;
            Vector primes = priv.f();
            for (int i = 0; i < primes.size(); i++) {
                BigInteger exp = input.modPow(priv.e().divide((BigInteger) primes.elementAt(i)), priv.d());
                Vector[] vectorArr = this.e;
                Vector al = vectorArr[i];
                if (vectorArr[i].size() != ((BigInteger) primes.elementAt(i)).intValue()) {
                    if (this.f) {
                        PrintStream printStream2 = System.out;
                        printStream2.println("Prime is " + primes.elementAt(i) + ", lookup table has size " + al.size());
                    }
                    throw new InvalidCipherTextException("Error in lookup Array for " + ((BigInteger) primes.elementAt(i)).intValue() + ": Size mismatch. Expected ArrayList with length " + ((BigInteger) primes.elementAt(i)).intValue() + " but found ArrayList of length " + this.e[i].size());
                }
                int lookedup = al.indexOf(exp);
                if (lookedup == -1) {
                    if (this.f) {
                        PrintStream printStream3 = System.out;
                        printStream3.println("Actual prime is " + primes.elementAt(i));
                        PrintStream printStream4 = System.out;
                        printStream4.println("Decrypted value is " + exp);
                        PrintStream printStream5 = System.out;
                        printStream5.println("LookupList for " + primes.elementAt(i) + " with size " + this.e[i].size() + " is: ");
                        for (int j = 0; j < this.e[i].size(); j++) {
                            System.out.println(this.e[i].elementAt(j));
                        }
                    }
                    throw new InvalidCipherTextException("Lookup failed");
                }
                plain.addElement(BigInteger.valueOf((long) lookedup));
            }
            return e(plain, primes).toByteArray();
        } else {
            throw new InvalidCipherTextException("BlockLength does not match modulus for Naccache-Stern cipher.\n");
        }
    }

    public byte[] f(BigInteger plain) {
        byte[] output = this.d.d().toByteArray();
        Arrays.F(output, (byte) 0);
        byte[] tmp = this.d.b().modPow(plain, this.d.d()).toByteArray();
        System.arraycopy(tmp, 0, output, output.length - tmp.length, tmp.length);
        if (this.f) {
            PrintStream printStream = System.out;
            printStream.println("Encrypted value is:  " + new BigInteger(output));
        }
        return output;
    }

    private static BigInteger e(Vector congruences, Vector primes) {
        BigInteger retval = a;
        BigInteger all = b;
        for (int i = 0; i < primes.size(); i++) {
            all = all.multiply((BigInteger) primes.elementAt(i));
        }
        for (int i2 = 0; i2 < primes.size(); i2++) {
            BigInteger a2 = (BigInteger) primes.elementAt(i2);
            BigInteger b2 = all.divide(a2);
            retval = retval.add(b2.multiply(b2.modInverse(a2)).multiply((BigInteger) congruences.elementAt(i2)));
        }
        return retval.mod(all);
    }
}
