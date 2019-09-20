package com.cmc.dashboard.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.cmc.dashboard.dto.DeliveryUnitDTO;
import com.cmc.dashboard.dto.DuInternalDuDTO;
import com.cmc.dashboard.dto.DuStatisticDTO;
import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.dto.ProjectInDeliveryUnitDto;
import com.cmc.dashboard.dto.ResourcesAvailableDTO;

public interface DeliveryUnitService {
	List<DuInternalDuDTO> GetDeliveryUnitById(int month, int year);
	
	List<DuInternalDuDTO> GetDUStatistic(String startDateMonth, String endDateMonth, String monthDate) throws ParseException;

	List<DeliveryUnitDTO> getTotalAllocationByDeliveryUnit(String time);

	List<DeliveryUnitDTO> getEffortEfficiencyByDeliveryUnit(String time);

	Page<ProjectInDeliveryUnitDto> getProjectsInDeliveryUnit(int page, int size, String duName);

	List<DeliveryUnitDTO> getTotalSpentTimeByDeliveryUnit(String time);

	List<DeliveryUnitDTO> getTotalUserByDeliveryUnit();

	List<DeliveryUnitDTO> getAllocationToOtherDeliveryUnit(String time);

	List<DeliveryUnitDTO> getManPowerByDeliveryUnit(String time);
	
	List<DeliveryUnitDTO> getPageDeliveryUnitInfo(String column,String sort,int month,int year);
	
	DuInternalDuDTO getTotalDuInfo(int id, int month, int year);
	
	DuInternalDuDTO GetDeliveryUnitByDuId(int id, int month, int year);
	
	ResourcesAvailableDTO getResourcesAvailable();
	List<RiskDTO> getAllRisk(int projectId,int month,int year,int groupId);
}
