package com.kalher.meetingscheduler.custom.wrapper;

import com.kalher.meetingscheduler.custom.Status;

public class DataWrapper<T> {
    private Status status;
    private String message;
    private T data;

    public DataWrapper(Status status, T data){
        this.status = status;
        this.message = status.getMessage();
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
