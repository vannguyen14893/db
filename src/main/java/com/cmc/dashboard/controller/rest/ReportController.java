/**
 * 
 */
package com.cmc.dashboard.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.DashboardDTO;
import com.cmc.dashboard.dto.EfficiencyDTO;
import com.cmc.dashboard.dto.EffortEfficiencyDetails;
import com.cmc.dashboard.dto.ProjectCssDetailDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.ReportService;
import com.cmc.dashboard.util.MethodUtil;


/**
 * @author: NVKhoa
 * @Date: May 30, 2018
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api")
public class ReportController {

	@Autowired
	ReportService reportService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	/**
	 * 
	 * get api of chart css
	 * 
	 * @param year
	 * @param month
	 * @param userId
	 * @return ResponseEntity<?>
	 * @author: tvdung
	 */
	@RequestMapping(value = "/chartcss", method = RequestMethod.GET)
	public ResponseEntity<List<CssChartDTO>> getProjectChartCSS(@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("userId") int userId) {
		List<CssChartDTO> listCssChart = new ArrayList<>();
		if (MethodUtil.isValidDate(month, year)) {
			listCssChart = reportService.getChartCSSService(month, year, userId);
		}
		return new ResponseEntity<>(listCssChart, HttpStatus.OK);
	}

	/**
	 * Get DU_name and CSS_ score by month and year
	 * 
	 * @param year
	 * @param month
	 * @return ResponseEntity<List<CssChartDTO>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/css", method = RequestMethod.GET)
	public ResponseEntity<List<CssChartDTO>> getProjectCSS(@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("userId") int userId) {
		List<CssChartDTO> listCssChart = new ArrayList<>();
		if (MethodUtil.isValidDate(month, year)) {
			listCssChart = reportService.getProjectCSSService(month, year, userId);
		}
		return new ResponseEntity<>(listCssChart, HttpStatus.OK);
	}

	/**
	 * Call API get list of project details (css).
	 * 
	 * @param deliveryUnit
	 * @param year
	 * @param month
	 * @return ResponseEntity<List<ProjectCssDetailDto>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/css/detail", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectCssDetailDTO>> getCssDetails(@RequestParam("duName") String duName,
			@RequestParam("year") int year, @RequestParam("month") int month) {
		List<ProjectCssDetailDTO> listCssDetail = new ArrayList<>();
		if (MethodUtil.isValidDate(month, year)) {
			listCssDetail = reportService.getCssDetails(duName, month, year);
		}
		return new ResponseEntity<>(listCssDetail, HttpStatus.OK);
	}

	/**
	 * Get list of counted project by type
	 * 
	 * @param month
	 * @param year
	 * @param userId
	 * @return ResponseEntity<List<DashboardDTO<Integer>>>
	 * @author: NVKhoa
	 */
	@RequestMapping(value = "/projects/type", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardDTO<Integer>>> getProjectByType(@RequestParam("month") int month,
			@RequestParam("year") int year, @RequestParam("userId") int userId) {
		List<DashboardDTO<Integer>> list = reportService.getProjectByType(month, year, userId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * Get list project by type, duName, month, year
	 * 
	 * @param duName
	 * @param projectType
	 * @param month
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/projects/type/list", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> getListProjectByType(@RequestParam("unit") String unit,
			@RequestParam("projectType") String projectType, @RequestParam("month") int month,
			@RequestParam("year") int year) {
		List<ProjectDTO> lstProjectDto = reportService.getProjectListByType(unit, projectType, month, year);
		return new ResponseEntity<>(lstProjectDto, HttpStatus.OK);
	}

	/**
	 * Get effort efficiency for each delivery unit
	 * 
	 * @param month
	 * @param year
	 * @param userId
	 * @return ResponseEntity<List<EfficiencyDTO>>
	 * @author: NVKhoa
	 * @modifier:LXLinh
	 */
	@RequestMapping(value = "/effort/efficiency", method = RequestMethod.GET)
	public ResponseEntity<List<EfficiencyDTO>> getEffortEfficiency(@RequestParam("month") int month,
			@RequestParam("year") int year, @RequestParam("userId") int userId) {
		return new ResponseEntity<>(reportService.getEffortEfficiency(month, year, userId), HttpStatus.OK);
	}

	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public ResponseEntity<String> getDateTime() {
		return new ResponseEntity<>(MethodUtil.getCurrentDate(), HttpStatus.OK);
	}

	/**
	 * method return all project billable revert from database.
	 * 
	 * @param duName
	 * @return ResponseEntity<ProjectBillableDTO>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "/billable/detail", method = RequestMethod.GET)
	public ResponseEntity<List<EffortEfficiencyDetails>> getProjectBillableDetail(@RequestParam("duName") String duName,
			@RequestParam("month") String month, @RequestParam("year") String year) {
		if (month.length() == 1) {
			month = "0".concat(month);
		}
		String period = month.concat("-").concat(year);
		List<EffortEfficiencyDetails> billableEffortDTOs = new ArrayList<>();
		if (MethodUtil.isNull(duName)) {
			return new ResponseEntity<>(billableEffortDTOs, HttpStatus.BAD_REQUEST);
		} else {
			billableEffortDTOs = reportService.getListProjectBillabeByDU(duName, period);
			return new ResponseEntity<>(billableEffortDTOs, HttpStatus.OK);
		}
	}
}
