package com.einkdisplay.einkdisplayserver;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message implements Comparable<Message>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    public Message(LocalDateTime time, String message, User user) {
        this.time = time;
        this.message = message;
        this.user=user;
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

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(Message msg) {
        return this.id.compareTo(msg.id);
    }
}
