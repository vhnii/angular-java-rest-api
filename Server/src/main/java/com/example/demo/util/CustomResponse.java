package com.example.demo.util;

import java.util.function.IntPredicate;

import com.example.demo.person.Person;

public class CustomResponse<T> {
    private T data;
    private int status;
    // private String message;
    private Message msg;

    // public CustomResponse(T data, int status, String message) {
    public CustomResponse(T data, int status, Message msg) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // public String getMessage() {
    public Message getMessage() {
        return msg;
    }

    // public void setMessage(String message) {
    public void setMessage(Message msg) {
        this.msg = msg;
    }
}
