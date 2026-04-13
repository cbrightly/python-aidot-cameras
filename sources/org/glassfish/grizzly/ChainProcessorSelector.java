package org.glassfish.grizzly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ChainProcessorSelector implements ProcessorSelector, List<ProcessorSelector> {
    private final List<ProcessorSelector> selectorChain;

    public ChainProcessorSelector() {
        this((List<ProcessorSelector>) new ArrayList());
    }

    public ChainProcessorSelector(ProcessorSelector... selectorChain2) {
        this((List<ProcessorSelector>) new ArrayList(Arrays.asList(selectorChain2)));
    }

    public ChainProcessorSelector(List<ProcessorSelector> selectorChain2) {
        this.selectorChain = selectorChain2;
    }

    public Processor select(IOEvent ioEvent, Connection connection) {
        for (ProcessorSelector processorSelector : this.selectorChain) {
            Processor processor = processorSelector.select(ioEvent, connection);
            if (processor != null) {
                return processor;
            }
        }
        return null;
    }

    public int size() {
        return this.selectorChain.size();
    }

    public boolean isEmpty() {
        return this.selectorChain.isEmpty();
    }

    public boolean contains(Object o) {
        return this.selectorChain.contains(o);
    }

    public Iterator<ProcessorSelector> iterator() {
        return this.selectorChain.iterator();
    }

    public Object[] toArray() {
        return this.selectorChain.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.selectorChain.toArray(a);
    }

    public boolean add(ProcessorSelector o) {
        return this.selectorChain.add(o);
    }

    public boolean remove(Object o) {
        return this.selectorChain.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.selectorChain.containsAll(c);
    }

    public boolean addAll(Collection<? extends ProcessorSelector> c) {
        return this.selectorChain.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends ProcessorSelector> c) {
        return this.selectorChain.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return this.selectorChain.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.selectorChain.retainAll(c);
    }

    public void clear() {
        this.selectorChain.clear();
    }

    public ProcessorSelector get(int index) {
        return this.selectorChain.get(index);
    }

    public ProcessorSelector set(int index, ProcessorSelector element) {
        return this.selectorChain.set(index, element);
    }

    public void add(int index, ProcessorSelector element) {
        this.selectorChain.add(index, element);
    }

    public ProcessorSelector remove(int index) {
        return this.selectorChain.remove(index);
    }

    public int indexOf(Object o) {
        return this.selectorChain.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return this.selectorChain.lastIndexOf(o);
    }

    public ListIterator<ProcessorSelector> listIterator() {
        return this.selectorChain.listIterator();
    }

    public ListIterator<ProcessorSelector> listIterator(int index) {
        return this.selectorChain.listIterator(index);
    }

    public List<ProcessorSelector> subList(int fromIndex, int toIndex) {
        return this.selectorChain.subList(fromIndex, toIndex);
    }
}
