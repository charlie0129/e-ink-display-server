package com.einkdisplay.einkdisplayserver;

import java.util.HashMap;

public class UserMessage {

    private final HashMap <Long, String> userMessages = new HashMap<>();

    public UserMessage(long id, String content) {
        userMessages.put(id, content);
    }

    public UserMessage() {
    }

    public HashMap<Long, String> getUserMessages() {
        return userMessages;
    }
}
