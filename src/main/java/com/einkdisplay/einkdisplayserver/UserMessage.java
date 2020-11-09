package com.einkdisplay.einkdisplayserver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Vector;

public class UserMessage {

    private final Vector<MessageBody> userMessages = new Vector<>();

    public UserMessage(String message) {
        userMessages.add(new MessageBody(LocalDateTime.now(), message));
    }

    public UserMessage() {
    }

    public void addMessage(String message){
        userMessages.add(new MessageBody(LocalDateTime.now(), message));
    }

    public MessageBody getLastMessage(){
        return userMessages.lastElement();
    }

    public Vector<MessageBody> getAllMessages(){
        return userMessages;
    }
}
