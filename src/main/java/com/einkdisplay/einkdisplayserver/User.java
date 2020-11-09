package com.einkdisplay.einkdisplayserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.einkdisplay.einkdisplayserver.MessageBody;

import java.time.LocalDateTime;
import java.util.Vector;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private final Vector<MessageBody> messages = new Vector<>();

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMessage(String message) {
        messages.add(new MessageBody(LocalDateTime.now(), message));
    }

    public Vector<MessageBody> getMessages() {
        return messages;
    }

//    public MessageBody getLastMessage(){
//        return messages.lastElement();
//    }



    public Vector<MessageBody> getMessages(int index) throws IndexOutOfBoundsException {
        Vector<MessageBody> ret = new Vector<>();
        if (index >= 0) {
            for (int i = 0; i <= index; i++) {
                if (messages.size() > 0)
                ret.add(messages.get(i));
            }
        } else {
            for (int i = -1; i >= index; i--) {
                if (messages.size() > 0)
                ret.add(messages.get((messages.size()+i)));
            }
        }
        return ret;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }


}
