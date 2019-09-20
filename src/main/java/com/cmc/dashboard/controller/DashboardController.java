package com.cmc.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: ngocdv
 * @Date: Mar 12, 2018
 */
@Controller
public class DashboardController {
	
	private ModelAndView getPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getHomePage(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Model model) {
		return getPage();
	}
	
	//dasboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView getDashboard(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/dashboard/project-by-type", method = RequestMethod.GET)
	public ModelAndView getProjectByType(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/dashboard/effort-efficiency", method = RequestMethod.GET)
	public ModelAndView getEffortEfficiency(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/dashboard/customer-satisfic-survey", method = RequestMethod.GET)
	public ModelAndView indexDashboard() {
		return getPage();
	}
	
	// project management
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView viewProject(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/list", method = RequestMethod.GET)
	public ModelAndView indexProject(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/allocation", method = RequestMethod.GET)
	public ModelAndView allocation(Model model) {
		return getPage();
	}
	
	//project detail
		@RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
		public ModelAndView indexProject(Model model, @PathVariable("projectId") String projectId) {
			return getPage();
		}
	//project detail
	@RequestMapping(value = "/project/{projectId}/overview", method = RequestMethod.GET)
	public ModelAndView indexProjectOverview(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/kpi", method = RequestMethod.GET)
	public ModelAndView indexProjectKpi(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/member", method = RequestMethod.GET)
	public ModelAndView indexProjectMember(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/task", method = RequestMethod.GET)
	public ModelAndView indexProjectTask(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/resource-allocate", method = RequestMethod.GET)
	public ModelAndView projectAllocation(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/billable", method = RequestMethod.GET)
	public ModelAndView getProjectBillable(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/customer-statistic-survey", method = RequestMethod.GET)
	public ModelAndView getProjectCss(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	//issue of project
	@RequestMapping(value = "/project/{projectId}/issue", method = RequestMethod.GET)
	public ModelAndView getIssueByProject(Model model, @PathVariable("projectId") int projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/issue/create", method = RequestMethod.GET)
	public ModelAndView getCreateIssueView(Model model, @PathVariable("projectId") int projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/issue/{issueId}/update", method = RequestMethod.GET)
	public ModelAndView getUpdateIssueView(Model model, @PathVariable("projectId") int projectId, @PathVariable("projectId") int issueId) {
		return getPage();
	}
	
	//risk and solution
	@RequestMapping(value = "/project/{projectId}/risk", method = RequestMethod.GET)
	public ModelAndView getAllRiskOfProject(Model model, @PathVariable("projectId") int projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/risk/{riskId}", method = RequestMethod.GET)
	public ModelAndView getRiskDetailView(Model model, @PathVariable("projectId") int projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/risk/{riskId}/solution/{solutionId}", method = RequestMethod.GET)
	public ModelAndView getRiskSolutionView(Model model, @PathVariable("projectId") int projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/monitor-report", method = RequestMethod.GET)
	public ModelAndView getMonitoring(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	//url resource management
	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public ModelAndView indexResource(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources/{resourceId}", method = RequestMethod.GET)
	public ModelAndView indexResourcesByResourceId(Model model, @PathVariable("resourceId") String resourceId) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources/{resourceId}/plan", method = RequestMethod.GET)
	public ModelAndView indexDashboard(Model model, @PathVariable("resourceId") String projectId) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources/{userId}/assign-task", method = RequestMethod.GET)
	public ModelAndView indexAssignedTasks(Model model, @PathVariable("userId") String userId) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources/{userId}/man-power", method = RequestMethod.GET)
	public ModelAndView indexManPowers(Model model, @PathVariable("userId") String userId) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resource-allocation", method = RequestMethod.GET)
	public ModelAndView indexResourceAllocation(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources/{userId}/timesheet", method = RequestMethod.GET)
	public ModelAndView indexTimesheets(Model model, @PathVariable("userId") String userId) {
		return getPage();
	}

	@RequestMapping(value = "/resource/resources", method = RequestMethod.GET)
	public ModelAndView indexResourceByDay(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/resource/unallocation", method = RequestMethod.GET)
	public ModelAndView indexResourceByUnAllocation(Model model) {
		return getPage();
	}
	//delivery unit
	@RequestMapping(value = "/delivery-unit", method = RequestMethod.GET)
	public ModelAndView indexDeliveryUnit(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/delivery-unit/{duName}/projects", method = RequestMethod.GET)
	public ModelAndView getProjectsOfDeliveryUnit(Model model, @PathVariable("duName") String duName) {
		return getPage();
	}

	// error page
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public ModelAndView viewPage500(Model model) {
		return getPage();
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView viewPage403(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/time-line", method = RequestMethod.GET)
	public ModelAndView getDelivery(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}
	
	@RequestMapping(value = "/project/{projectId}/pcv-rate", method = RequestMethod.GET)
	public ModelAndView getPcvRate(Model model, @PathVariable("projectId") String projectId) {
		return getPage();
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView indexAdmin(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/user/detail", method = RequestMethod.GET)
	public ModelAndView indexUserDetail(Model model) {
		return getPage();
	}
	// nvvtiep 2
	
	//delivery
	@RequestMapping(value = "/user-profile", method = RequestMethod.GET)
	public ModelAndView indexUserProfile(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/delivery-unit/overview", method = RequestMethod.GET)
	public ModelAndView indexDeliveryOverview(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/delivery-unit/monitoring", method = RequestMethod.GET)
	public ModelAndView indexDeliveryMonitoring(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/delivery-unit/resources-available", method = RequestMethod.GET)
	public ModelAndView indexDeliveryResourceAvailable(Model model) {
		return getPage();
	}
	
	@RequestMapping(value = "/delivery-unit/risk", method = RequestMethod.GET)
	public ModelAndView indexDeliveryResourceRisk(Model model) {
		return getPage();
	}
	
	
	@RequestMapping(value = "/delivery-unit/issue", method = RequestMethod.GET)
	public ModelAndView indexDeliveryResourceIssue(Model model) {
		return getPage();
	}
	//admin
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public ModelAndView adminUser(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/admin/group", method = RequestMethod.GET)
	public ModelAndView adminGroup(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/admin/group-detail/{groupId}", method = RequestMethod.GET)
	public ModelAndView adminGroupDe(Model model, @PathVariable("groupId") String groupId) {
		return getPage();
	}
	@RequestMapping(value = "/admin/group/add-group", method = RequestMethod.GET)
	public ModelAndView adminGroupAdgroup(Model model) {
		return getPage();
	}	@RequestMapping(value = "/admin/project-type", method = RequestMethod.GET)
	public ModelAndView adminProjectType(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/admin/role", method = RequestMethod.GET)
	public ModelAndView adminRole(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/admin/permission", method = RequestMethod.GET)
	public ModelAndView adminPermission(Model model) {
		return getPage();
	}
	@RequestMapping(value = "/admin/permission/add-permission", method = RequestMethod.GET)
	public ModelAndView adminPermissionAdd(Model model) {
		return getPage();
	}	@RequestMapping(value = "admin/permission/permission-detail/{permissionId}", method = RequestMethod.GET)
	public ModelAndView adminPermissionDetail(Model model, @PathVariable("permissionId") String permissionId) {
		return getPage();
	}
	@RequestMapping(value = "admin/role-permission", method = RequestMethod.GET)
	public ModelAndView adminPermissionRole(Model model) {
		return getPage();
	}
	@RequestMapping(value = "admin/user-role", method = RequestMethod.GET)
	public ModelAndView adminUserRole(Model model) {
		return getPage();
	}
	@RequestMapping(value = "admin/role/role-detail/{roleId}", method = RequestMethod.GET)
	public ModelAndView adminRoleDetail(Model model,  @PathVariable("roleId") String roleId) {
		return getPage();
	}
	@RequestMapping(value = "admin/role/add-role", method = RequestMethod.GET)
	public ModelAndView adminRoleAdd(Model model) {
		return getPage();
	}
	@RequestMapping(value = "admin/project", method = RequestMethod.GET)
	public ModelAndView adminProject(Model model) {
		return getPage();
	}	@RequestMapping(value = "admin/project/edit/{:id}", method = RequestMethod.GET)
	public ModelAndView adminProjectEdit(Model model, @PathVariable("id") String id) {
		return getPage();
	}
	@RequestMapping(value = "admin/skill", method = RequestMethod.GET)
	public ModelAndView adminSkill(Model model) {
		return getPage();
	}
	
}
