package com.meridian.backend.repository;

import com.meridian.backend.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByTickerIdOrderByRecordedAtDesc(Long tickerId);
}