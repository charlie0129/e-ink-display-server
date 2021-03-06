package com.einkdisplay.einkdisplayserver.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String message;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "displayid", referencedColumnName = "id")
    private EInkDisplay display;

    public Message() {
    }

    public Message(LocalDateTime time,
                   String message,
                   User user,
                   EInkDisplay display) {
        this.time = time;
        this.message = message;
        this.user = user;
        this.display = display;
    }

    public EInkDisplay getEInkDisplay() {
        return display;
    }

    public void setEInkDisplay(EInkDisplay eInkDisplay) {
        this.display = eInkDisplay;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getMessage() {
        return message;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(Message msg) {
        return this.id.compareTo(msg.id);
    }
}
