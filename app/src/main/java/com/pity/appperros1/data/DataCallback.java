package com.pity.appperros1.data;

public interface DataCallback<T extends Object> {
    void onSuccess(T t);
    void onFailure(String error);
}
