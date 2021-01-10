package com.pity.baradopta.data;

public interface DataCallback<T extends Object> {
    void onSuccess(T t);
    void onFailure(String error);
}
