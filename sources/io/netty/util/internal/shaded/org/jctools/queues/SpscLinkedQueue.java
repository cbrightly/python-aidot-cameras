package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;

public class SpscLinkedQueue<E> extends BaseLinkedQueue<E> {
    public /* bridge */ /* synthetic */ int capacity() {
        return super.capacity();
    }

    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer) {
        return super.drain(consumer);
    }

    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer, int i) {
        return super.drain(consumer, i);
    }

    public /* bridge */ /* synthetic */ void drain(MessagePassingQueue.Consumer consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    public /* bridge */ /* synthetic */ boolean relaxedOffer(Object obj) {
        return super.relaxedOffer(obj);
    }

    public /* bridge */ /* synthetic */ Object relaxedPeek() {
        return super.relaxedPeek();
    }

    public /* bridge */ /* synthetic */ Object relaxedPoll() {
        return super.relaxedPoll();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscLinkedQueue() {
        LinkedQueueNode<E> node = newNode();
        spProducerNode(node);
        spConsumerNode(node);
        node.soNext((LinkedQueueNode<E>) null);
    }

    public boolean offer(E e) {
        if (e != null) {
            LinkedQueueNode<E> nextNode = newNode(e);
            lpProducerNode().soNext(nextNode);
            spProducerNode(nextNode);
            return true;
        }
        throw new NullPointerException();
    }

    public E poll() {
        return relaxedPoll();
    }

    public E peek() {
        return relaxedPeek();
    }

    public int fill(MessagePassingQueue.Supplier<E> s) {
        long result = 0;
        do {
            fill(s, 4096);
            result += 4096;
        } while (result <= 2147479551);
        return (int) result;
    }

    public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
        if (limit == 0) {
            return 0;
        }
        LinkedQueueNode<E> tail = newNode(s.get());
        LinkedQueueNode<E> head = tail;
        for (int i = 1; i < limit; i++) {
            LinkedQueueNode<E> temp = newNode(s.get());
            tail.soNext(temp);
            tail = temp;
        }
        lpProducerNode().soNext(head);
        spProducerNode(tail);
        return limit;
    }

    public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
        LinkedQueueNode<E> chaserNode = this.producerNode;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; i++) {
                LinkedQueueNode<E> nextNode = newNode(s.get());
                chaserNode.soNext(nextNode);
                chaserNode = nextNode;
                this.producerNode = chaserNode;
            }
        }
    }
}
