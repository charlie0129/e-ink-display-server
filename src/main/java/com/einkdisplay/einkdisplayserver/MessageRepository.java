package com.einkdisplay.einkdisplayserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    ArrayList<Message> findByDisplayOrderByIdDesc(EInkDisplay display);
    ArrayList<Message> findByUserOrderByIdDesc(User user);
    ArrayList<Message> findTop20ByUserOrderByIdDesc(User user);
    ArrayList<Message> findAll();
}