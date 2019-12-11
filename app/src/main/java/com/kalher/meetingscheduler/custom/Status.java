package com.kalher.meetingscheduler.custom;

public enum Status {

    SUCCESS(0, "Data fetched successfully"),

    FAILED(1, "Something went wrong!"),

    NO_NETWORK_CONNECTION(2, "No network connection"),

    FETCHING(3, "Loading"),
    ;

    private int id;
    private String message;

    Status(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
