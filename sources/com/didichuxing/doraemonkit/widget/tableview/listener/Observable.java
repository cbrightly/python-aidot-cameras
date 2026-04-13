package com.didichuxing.doraemonkit.widget.tableview.listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
    public final ArrayList<T> observables = new ArrayList<>();

    public abstract void notifyObservers(List<T> list);

    public void register(T observer) {
        if (observer != null) {
            synchronized (this.observables) {
                if (!this.observables.contains(observer)) {
                    this.observables.add(observer);
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    public void unRegister(T observer) {
        if (observer == null) {
            throw new NullPointerException();
        } else if (this.observables.contains(observer)) {
            this.observables.remove(observer);
        }
    }

    public void unRegisterAll() {
        synchronized (this.observables) {
            this.observables.clear();
        }
    }

    public int countObservers() {
        int size;
        synchronized (this.observables) {
            size = this.observables.size();
        }
        return size;
    }
}
