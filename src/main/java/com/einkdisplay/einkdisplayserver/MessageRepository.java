package com.einkdisplay.einkdisplayserver;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    ArrayList<Message> findByUser(User user);
    ArrayList<Message> findByUserOrderByIdDesc(User user);
    ArrayList<Message> findTop10ByUserOrderByIdDesc(User user);
}