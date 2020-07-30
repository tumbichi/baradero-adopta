package com.pity.appperros1.data.repository;

public interface DataCallback<T extends Object> {
    void onSuccess(T t);
    void onFailure(String error);
}
