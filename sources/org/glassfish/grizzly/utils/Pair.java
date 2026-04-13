package org.glassfish.grizzly.utils;

public class Pair<K, L> implements PoolableObject {
    private K first;
    private L second;

    public Pair() {
    }

    public Pair(K first2, L second2) {
        this.first = first2;
        this.second = second2;
    }

    public K getFirst() {
        return this.first;
    }

    public void setFirst(K first2) {
        this.first = first2;
    }

    public L getSecond() {
        return this.second;
    }

    public void setSecond(L second2) {
        this.second = second2;
    }

    public void prepare() {
    }

    public void release() {
        this.first = null;
        this.second = null;
    }

    public String toString() {
        return "Pair{key=" + this.first + " value=" + this.second + '}';
    }
}
