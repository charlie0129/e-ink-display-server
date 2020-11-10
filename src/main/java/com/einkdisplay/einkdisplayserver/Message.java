package com.einkdisplay.einkdisplayserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Message implements Comparable<Message>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private LocalDateTime time;
    private long userID;

    public Message(LocalDateTime time, String message, long userID) {
        this.time = time;
        this.message = message;
        this.userID=userID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Message(){

    }

    public String getMessage() {
        return message;
    }



    public LocalDateTime getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    @Override
    public int compareTo(Message msg) {
        return this.id.compareTo(msg.id);
    }
}
