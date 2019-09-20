package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cmc.dashboard.model.HistoryData;

public interface HistoryDataRepository extends JpaRepository<HistoryData, Integer>{

	
	public List<HistoryData> findByTableNameAndRowId(String tableName,Long rowId);
}
