package com.einkdisplay.einkdisplayserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long phone;

//    private final Vector<Message> messages = new Vector<>();

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

    public void setId(Long id) {
        this.id = id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

//    public void addMessage(String message) {
//        messages.add(new Message(LocalDateTime.now(), message));
//    }
//
//    public Vector<Message> getMessages() {
//        return messages;
//    }
//
////    public MessageBody getLastMessage(){
////        return messages.lastElement();
////    }
//
//
//
//    public Vector<Message> getMessages(int index) throws IndexOutOfBoundsException {
//        Vector<Message> ret = new Vector<>();
//        if (index >= 0) {
//            for (int i = 0; i <= index; i++) {
//                if (messages.size() > 0)
//                ret.add(messages.get(i));
//            }
//        } else {
//            for (int i = -1; i >= index; i--) {
//                if (messages.size() > 0)
//                ret.add(messages.get((messages.size()+i)));
//            }
//        }
//        return ret;
//    }

    public User(String name, long phone) {
        this.name = name;
        this.phone = phone;
    }

    public User() {
    }


}
