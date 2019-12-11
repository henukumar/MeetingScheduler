package com.kalher.meetingscheduler.custom.exception;

import java.io.IOException;

public class NoNetworkException extends IOException {

    @Override
    public String getMessage() {
        return "No network connection";
    }

}
