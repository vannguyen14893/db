package com.cmc.dashboard.controller.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.DeliveryUnitDTO;
import com.cmc.dashboard.dto.DuInternalDuDTO;
import com.cmc.dashboard.dto.DuStatisticDTO;
import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.model.DUStatistic;
import com.cmc.dashboard.repository.DUStatisticRepository;
import com.cmc.dashboard.dto.ProjectInDeliveryUnitDto;
import com.cmc.dashboard.dto.ResourcesAvailableDTO;
import com.cmc.dashboard.service.DeliveryUnitService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class DeliveryUnitController {

	@Autowired
	DeliveryUnitService deliveryUnitService;
	
	@Autowired
	DUStatisticRepository duStatisticRepository;

	@ApiOperation(value = "View a list of delivery unit", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/list-delivery-unit", method = RequestMethod.GET)
	public ResponseEntity<?> getDeliveryUnitInfo(@RequestParam("column") String column,
			@RequestParam("sort") String sort, @RequestParam("month") int month, @RequestParam("year") int year) {
		return new ResponseEntity<List<DeliveryUnitDTO>>(
				deliveryUnitService.getPageDeliveryUnitInfo(column, sort, month, year), HttpStatus.OK);
	}

	@ApiOperation(value = "View info of a delivery unit", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	
	@RequestMapping(value = "/du-info", method = RequestMethod.GET)
	public ResponseEntity<?> getDeliveryUnitInfoById(@RequestParam("id") int id, @RequestParam("month") int month,
			@RequestParam("year") int year) {
		if (id == -1) {
			Object a = deliveryUnitService.getResourcesAvailable();
			return new ResponseEntity<DuInternalDuDTO>(deliveryUnitService.getTotalDuInfo(id, month, year),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<DuInternalDuDTO>(deliveryUnitService.GetDeliveryUnitByDuId(id,month, year),
					HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/du-statistic", method = RequestMethod.GET)
	public ResponseEntity<?> getDUStatistic(@RequestParam("month") int month,@RequestParam("year") int year, @RequestParam("groupId") int groupId) {	
		String monthYear = (month<10?"0"+month:month)+"-"+year;
		DUStatistic duStatistic = duStatisticRepository.findByGroupIdAndMonth(groupId == -1? 0 : groupId,monthYear);
			return new ResponseEntity<DUStatistic>(duStatistic,
					HttpStatus.OK);
	}

	@ApiOperation(value = "View projects of delivery unit", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved projects of delivery unit"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/getProjectDeliveyUnit", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectsOfDeliveryUnit(
			@Validated @NotNull(message = "notnull") @RequestParam(value = "duName") String duName,
			@RequestParam(value = "size") int size, @RequestParam("page") int page) {
		Page<ProjectInDeliveryUnitDto> projectInDeliveryUnitDtos = deliveryUnitService.getProjectsInDeliveryUnit(page,
				size, duName);
		return new ResponseEntity<Page<ProjectInDeliveryUnitDto>>(projectInDeliveryUnitDtos, HttpStatus.OK);
	}
	@GetMapping(value ="/getResourcesAvailable")
	public ResponseEntity<?> getResourcesAvailable(){
		return new ResponseEntity<ResourcesAvailableDTO>(deliveryUnitService.getResourcesAvailable(),HttpStatus.OK);
	}
	@RequestMapping(value="/risk/all", method = RequestMethod.GET)
	public ResponseEntity<List<RiskDTO>> listAllIssue(@RequestParam("groupId") int groupId,@RequestParam("month") int month,@RequestParam("year") int year,@RequestParam("projectId") int projectId) {
		List<RiskDTO> riskDTOs=deliveryUnitService.getAllRisk(projectId, month, year, groupId);
		return new ResponseEntity<>(riskDTOs, HttpStatus.OK);
	}


}
