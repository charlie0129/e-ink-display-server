package com.einkdisplay.einkdisplayserver;

import java.time.LocalDateTime;

public class MessageBody {
    private final String message;
    private final LocalDateTime time;

    public MessageBody(LocalDateTime time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
