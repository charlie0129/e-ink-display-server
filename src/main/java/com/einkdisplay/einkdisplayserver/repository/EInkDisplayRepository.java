package com.einkdisplay.einkdisplayserver.repository;

import com.einkdisplay.einkdisplayserver.model.EInkDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EInkDisplayRepository extends JpaRepository<EInkDisplay, Long> {
}
