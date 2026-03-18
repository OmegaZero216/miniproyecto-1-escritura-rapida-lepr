package com.example.escritura_rapida.controller;

/**
 * Implemented by controllers that need to receive data on navigation.
 *
 * @param <T> data type accepted by the controller
 */
public interface DataReceiver<T> {
    /**
     * Receives data passed by the navigation manager.
     *
     * @param data data instance
     */
    void setData(T data);
}
