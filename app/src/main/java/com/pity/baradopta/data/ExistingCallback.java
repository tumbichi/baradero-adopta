package com.pity.baradopta.data;

public interface ExistingCallback<T extends Object> {
    void isInExistence(T t);
    void notInExistence();
}
