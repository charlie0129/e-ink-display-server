package com.einkdisplay.einkdisplayserver.repository;

import com.einkdisplay.einkdisplayserver.model.EInkDisplay;
import com.einkdisplay.einkdisplayserver.model.Message;
import com.einkdisplay.einkdisplayserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    ArrayList<Message> findByDisplayOrderByTimeDesc(EInkDisplay display);
    ArrayList<Message> findByUserOrderByTimeDesc(User user);
    ArrayList<Message> findTop20ByUserOrderByIdDesc(User user);
    ArrayList<Message> findAll();
    ArrayList<Message> findByOrderByIdDesc();
}