package com.didichuxing.doraemonkit.okgo.adapter;

public class DefaultCallAdapter<T> implements CallAdapter<T, Call<T>> {
    public Call<T> adapt(Call<T> call, AdapterParam param) {
        return call;
    }
}
