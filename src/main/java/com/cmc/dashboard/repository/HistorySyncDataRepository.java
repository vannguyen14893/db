package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.dashboard.model.HistorySyncData;

public interface HistorySyncDataRepository extends JpaRepository<HistorySyncData, Integer> {

}
