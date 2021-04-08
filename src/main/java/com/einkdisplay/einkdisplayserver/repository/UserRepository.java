package com.einkdisplay.einkdisplayserver.repository;

import com.einkdisplay.einkdisplayserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}