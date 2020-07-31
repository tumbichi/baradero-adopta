package com.pity.appperros1.data;

public interface ExistingCallback<T extends Object> {
    void isInExistence(T t);
    void notInExistence();
}
