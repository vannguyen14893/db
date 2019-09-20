/**
 *
 */
package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.qms.model.QmsCustomValue;
import com.cmc.dashboard.qms.model.QmsMember;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author nvkhoa
 *
 */
public class ProjectDTO {
	private Integer projectId;
	private String projectName;
	private Set<String> projectManager;
	private String projectType;
	private String deliveryUnit;
	private String projectSize;
	private int status;
	private String startDate;
	private String endDate;;
	private String projectCode;
	private String platform;
	private float loggedTimeTotal;
	private int featureTotal;
	private int resolvedFeatureTotal;
	private int closedFeatureTotal;
	private int rejectedFeatureTotal;
	private ProjectProgressDto projectProgressDto;
	private float doneTask;
	private float totalTask;

	public ProjectDTO() {
		super();
	}
	
	
	public ProjectDTO(Project project) {
		super();
		this.projectId = project.getId();
		this.projectName = project.getName();
		this.projectType = project.getProjectType().getName();
		this.deliveryUnit = project.getGroup().getGroupName();
		this.status = project.getStatus();
		this.startDate = project.getStartDate().toString();
		this.endDate = project.getEndDate().toString();
		this.projectCode = project.getProjectCode();
	}


	public ProjectDTO(Integer projectId, String projectName, int status) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.status = status;
	}

	public ProjectDTO(Integer projectId, String projectName, Set<String> projectManager) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
	}
	
	public ProjectDTO(Integer projectId, String projectName, Set<String> projectManager, String startDate, String endDate) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ProjectDTO(Integer projectId, String startDate, String endDate, String projectType, String deliveryUnit) {
		super();
		this.projectId = projectId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
	}

	public ProjectDTO(Integer projectId, String projectName, Set<String> projectManager, String projectType,
					  String deliveryUnit, String projectSize, String startDate, String endDate, String projectCode) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.projectSize = projectSize;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectCode = projectCode;
	}

	/**
	 * Constructure for project progress
	 *
	 * @author GiangTM
	 */
	public ProjectDTO(Integer projectId, String projectSize, int status, String startDate, String endDate,
					  float loggedTimeTotal, int featureTotal, int resolvedFeatureTotal, int closedFeatureTotal,
					  int rejectedFeatureTotal) {
		this.projectId = projectId;
		this.projectSize = projectSize;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.loggedTimeTotal = loggedTimeTotal;
		this.featureTotal = featureTotal;
		this.resolvedFeatureTotal = resolvedFeatureTotal;
		this.closedFeatureTotal = closedFeatureTotal;
		this.rejectedFeatureTotal = rejectedFeatureTotal;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(String projectSize) {
		this.projectSize = projectSize;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Set<String> getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Set<String> projectManager) {
		this.projectManager = projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = new HashSet<>(Arrays.asList(projectManager.split("\\,")));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setProjectManager(List<QmsUser> users, List<QmsMember> members) {
		Set<String> set = new HashSet<>();
		for (QmsMember member : members) {
			for (QmsUser user : users) {
				if (member.getProjectId() == this.projectId && member.getUserId() == user.getId()) {
					set.add(user.getLastname() + " " + user.getFirstname());
				}
			}
		}
		this.projectManager = set;
	}

	public void setCustomValue(List<QmsCustomValue> customValues) {
		for (QmsCustomValue customValue : customValues) {

			if (projectId == customValue.getCustomizedId()) {

				switch (customValue.getCustomFieldId()) {
					case CustomValueUtil.DELIVERY_UNIT_ID: {
						this.deliveryUnit = customValue.getValue();
						break;
					}
					case CustomValueUtil.MAN_DAY_ID: {
						this.projectSize = customValue.getValue();
						break;
					}
					case CustomValueUtil.START_DATE_ID: {
						this.startDate = customValue.getValue();
						break;
					}
					case CustomValueUtil.END_DATE_ID: {
						this.endDate = customValue.getValue();
						break;
					}
					case CustomValueUtil.PROJECT_CODE: {
						this.projectCode = customValue.getValue();
						break;
					}
					case CustomValueUtil.PROJECT_TYPE_ID: {
						this.projectType = customValue.getValue();
						break;
					}

				}
			}
		}
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getFeatureTotal() {
		return featureTotal;
	}

	public void setFeatureTotal(int featureTotal) {
		this.featureTotal = featureTotal;
	}

	public float getLoggedTimeTotal() {
		return loggedTimeTotal;
	}

	public void setLoggedTimeTotal(float loggedTimeTotal) {
		this.loggedTimeTotal = loggedTimeTotal;
	}

	public int getResolvedFeatureTotal() {
		return resolvedFeatureTotal;
	}

	public void setResolvedFeatureTotal(int resolvedFeatureTotal) {
		this.resolvedFeatureTotal = resolvedFeatureTotal;
	}

	public int getClosedFeatureTotal() {
		return closedFeatureTotal;
	}

	public void setClosedFeatureTotal(int closedFeatureTotal) {
		this.closedFeatureTotal = closedFeatureTotal;
	}

	public int getRejectedFeatureTotal() {
		return rejectedFeatureTotal;
	}

	public void setRejectedFeatureTotal(int rejectedFeatureTotal) {
		this.rejectedFeatureTotal = rejectedFeatureTotal;
	}

	public ProjectProgressDto getProjectProgressDto() {
		return projectProgressDto;
	}

	public void setProjectProgressDto(ProjectProgressDto projectProgressDto) {
		this.projectProgressDto = projectProgressDto;
	}

	public String toStringManager() {
		String manager = "";
		List<String> list = new ArrayList<>();
		list.addAll(this.projectManager);

		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				manager = list.get(i);
			} else {
				manager = manager + ", " + list.get(i);
			}
		}
		return manager;
	}


	public float getDoneTask() {
		return doneTask;
	}


	public void setDoneTask(float doneTask) {
		this.doneTask = doneTask;
	}


	public float getTotalTask() {
		return totalTask;
	}


	public void setTotalTask(float totalTask) {
		this.totalTask = totalTask;
	}
}
