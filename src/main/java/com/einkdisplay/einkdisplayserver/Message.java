package com.einkdisplay.einkdisplayserver;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "displayid", referencedColumnName = "id")
    private EInkDisplay eInkDisplay;

    public Message() {
    }

    public Message(LocalDateTime time,
                   String message,
                   User user,
                   EInkDisplay display) {
        this.time = time;
        this.message = message;
        this.user = user;
        this.eInkDisplay = display;
    }

    public EInkDisplay getEInkDisplay() {
        return eInkDisplay;
    }

    public void setEInkDisplay(EInkDisplay eInkDisplay) {
        this.eInkDisplay = eInkDisplay;
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

    public void setUser(User user) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(Message msg) {
        return this.id.compareTo(msg.id);
    }
}
