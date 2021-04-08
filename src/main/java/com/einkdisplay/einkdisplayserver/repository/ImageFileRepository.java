package com.einkdisplay.einkdisplayserver.repository;

import com.einkdisplay.einkdisplayserver.model.ImageFile;
import com.einkdisplay.einkdisplayserver.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, String> {
    ArrayList<ImageFile> findByMessage(Message message);
    @Transactional
    void deleteByMessage(Message message);
}
