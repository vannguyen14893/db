package com.cmc.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmc.dashboard.dto.DeliveryUnitXlsxStreaming;
import com.cmc.dashboard.dto.ProjectAllocationDTO;
import com.cmc.dashboard.dto.ProjectAllocationXlsxStreaming;
import com.cmc.dashboard.dto.ResourceAllocationXlsxStreaming;
import com.cmc.dashboard.dto.ResourceUnallocationXlsxStreaming;
import com.cmc.dashboard.dto.UnallocationListDTO;
import com.cmc.dashboard.model.ResourceAllocationDb;
import com.cmc.dashboard.qms.model.ResourceAllocationQms;
import com.cmc.dashboard.service.DeliveryUnitService;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.ResourceService;
import com.cmc.dashboard.service.UserPlanService;
import com.cmc.dashboard.service.qms.ProjectAllocationService;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NVKhoa
 * @Date: Apr 24, 2018
 */
@CrossOrigin("*")
@RequestMapping(value = "/api")
@Controller
public class ExportController {

	@Autowired
	ProjectService projectService;
	@Autowired
	UserPlanService userPlanService;
	/**
	 * Declare Project Allocation Service
	 */
	@Autowired
	private ProjectAllocationService allocService;
	
	@Autowired
	private DeliveryUnitService deliveryUnitService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * Export project allocation to excel
	 * @param req
	 * @param res
	 * @param userId
	 * @param dateMonth
	 * @return ModelAndView 
	 * @author: NVKhoa
	 */
	@RequestMapping(value = "/project/allocation/export", method = RequestMethod.GET)
	public ModelAndView exportProjectAllocation(HttpServletRequest req, HttpServletResponse res, 
			@NotNull @RequestParam("userId") final int userId,
			@NotNull @NotEmpty @RequestParam("dateMonth") final String dateMonth) {
		List<ProjectAllocationDTO> projectAllocationDTO = allocService.getAllProjectByDuWithoutPM(userId, dateMonth.trim());
		return new ModelAndView(new ProjectAllocationXlsxStreaming(dateMonth), "writter", projectAllocationDTO);
	}
	
	/**
	 * @author duyhieu
	 * @param month
	 * @param year
	 * @param column
	 * @param sort
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list-delivery-unit/export", method = RequestMethod.GET)
	public ModelAndView exportDeliveryUnit(@NotNull @NotEmpty @RequestParam("month") final int month,
			@NotNull @NotEmpty @RequestParam("year") final int year, @RequestParam("column") final String column,
			@RequestParam("sort") final String sort) {
		return new ModelAndView(
				new DeliveryUnitXlsxStreaming(MethodUtil.renMonthFromYm(month, year, CustomValueUtil.MONTH_YEAR)),
				Constants.StringPool.MODEL_NAME,
				deliveryUnitService.getPageDeliveryUnitInfo(column, sort, month, year));
	}

	/**
	 * @author duyhieu
	 * @param month
	 * @param year
	 * @param column
	 * @param sort
	 * @param page
	 * @param resourceName
	 * @param deliveryUnit
	 * @param duPic
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/resource-allocation-list/export", method = RequestMethod.GET)
	public ModelAndView exportResourceAllocation(@NotNull @NotEmpty @RequestParam("month") final int month,
			@NotNull @NotEmpty @RequestParam("year") final int year, @RequestParam("column") final String column,
			@RequestParam("sort") final String sort, @RequestParam("page") final int page,
			@RequestParam("resourceName") final String resourceName,
			@RequestParam("deliveryUnit") final String deliveryUnit, @RequestParam("duPic") final String duPic,
			@RequestParam("projectId") final String projectId) {
		List<ResourceAllocationQms> resourceAllocationQms;
		final String dateym = MethodUtil.renMonthFromYm(month, year, CustomValueUtil.YEAR_MONTH);
		final String datemy = MethodUtil.renMonthFromYm(month, year, CustomValueUtil.MONTH_YEAR);

		final Page<ResourceAllocationDb> allocation = resourceService.exportAllallocation(1,
				CustomValueUtil.RESOURCE_COLUMN_ALLOC, sort, dateym, datemy);

		if (!CustomValueUtil.RESOURCE_COLUMN_ALLOC.equals(column)
				&& !CustomValueUtil.RESOURCE_COLUMN_PLANALLOC.equals(column)) {
			final Page<ResourceAllocationQms> pageResource = resourceService.exportRessourceAllocation(page, column,
					sort, dateym, resourceName.trim(), deliveryUnit, duPic, projectId);
			resourceAllocationQms = resourceService.getListResourceQms(allocation, pageResource).getContent();
		} else {
			final Page<ResourceAllocationQms> listResource = resourceService.exportAllRessourceAllocation(1,
					CustomValueUtil.RESOURCE_COLUMN_DU, sort, dateym, resourceName, deliveryUnit, duPic, projectId);
			final Page<ResourceAllocationQms> pageResource = resourceService.exportRessourceAllocation(page,
					CustomValueUtil.RESOURCE_COLUMN_USERNAME, sort, dateym, resourceName, deliveryUnit, duPic,
					projectId);
			resourceAllocationQms = resourceService.getListResourceDashboard(pageResource, allocation.getContent(),
					listResource.getContent(), sort, column, page - 1).getContent();
		}
		return new ModelAndView(
				new ResourceAllocationXlsxStreaming(MethodUtil.renMonthFromYm(month, year, CustomValueUtil.MONTH_YEAR)),
				Constants.StringPool.MODEL_NAME, resourceAllocationQms);
	}


	/**
	 * @author duyhieu
	 * @param month
	 * @param year
	 * @param activePage
	 * @param size
	 * @param column
	 * @param sort
	 * @param resourceName
	 * @param status
	 * @param deliveryUnit
	 * @return
	 */
	@RequestMapping(value = "/resource/unallocation/export", method = RequestMethod.GET)
	public ModelAndView exportResourceUnallocation(@NotNull @NotEmpty @RequestParam("month") final int month,
			@NotNull @NotEmpty @RequestParam("year") final int year, @RequestParam("activePage") final int activePage,
			@RequestParam("size") final int size, @RequestParam("column") final String column,
			@RequestParam("sort") final String sort, @RequestParam("resourceName") final String resourceName,
			@RequestParam("status") final String status, @RequestParam("deliveryUnit") final String deliveryUnit) {
		final UnallocationListDTO unallocationListDTO = resourceService.exportUnallocations(month, year, activePage,
				size, column, sort, resourceName, status, deliveryUnit);
		return new ModelAndView(
				new ResourceUnallocationXlsxStreaming(
						MethodUtil.renMonthFromYm(month, year, CustomValueUtil.MONTH_YEAR)),
				Constants.StringPool.MODEL_NAME, unallocationListDTO);
	}
}
