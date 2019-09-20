/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cmc.dashboard.util.Constants.StringPool;
import com.cmc.dashboard.util.Constants.TypeResourceConvert;
import com.cmc.dashboard.util.MethodUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author: NNDuy
 * @Date: Apr 17, 2018
 */
public class ResourceProjectRestDto {
	private int projectId;
	private String projectName;
	private int status;
	private String startDate;
	private String endDate;
	// time
	private List<ResourceProjectEstimateTimeRestDTO> estimatedTimes;
	private List<ResourceProjectPlanTimeRestDTO> planTimes;
	private List<ResourceProjectSpentTimeRestDTO> spentTimes;

	/**
	 * Constructure
	 */
	public ResourceProjectRestDto(int projectId, String projectName, int status, String startDate, String endDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		estimatedTimes = new ArrayList<>();
		planTimes = new ArrayList<>();
		spentTimes = new ArrayList<>();
	}

	public String getProjectName() {
		return projectName;
	}

	public int getProjectId() {
		return projectId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<ResourceProjectEstimateTimeRestDTO> getEstimatedTimes() {
		return estimatedTimes;
	}

	public List<ResourceProjectPlanTimeRestDTO> getPlanTimes() {
		return planTimes;
	}

	public List<ResourceProjectSpentTimeRestDTO> getSpentTimes() {
		return spentTimes;
	}

	/**
	 * set estimate, plan, spent time for project of user
	 * 
	 * @param objects
	 * @param indexProjectFrom
	 * @param indexProjectTo
	 * @param type
	 * @param typeTime
	 *            void
	 * @author: NNDuy
	 */
	public void setTimeProjects(List<Object> objects, int indexProjectFrom, int indexProjectTo,
			TypeResourceConvert type, String typeTime) {
		int indexTimeFrom = indexProjectFrom;
		int indexTimeTo;
		Integer indexInListProjectsByProjectId;
		// set for all project of user current
		while (indexTimeFrom <= indexProjectTo) {
			indexTimeTo = getIndexTimeTo(objects, indexTimeFrom, indexProjectTo, type, typeTime);
			switch (type) {
			case SPENT_TIMES:
				// set List SpentTime to project
				indexInListProjectsByProjectId = getIndexTimeInListSpentTime(objects, indexTimeFrom, typeTime);
				if (indexInListProjectsByProjectId == null) {
					spentTimes.add(new ResourceProjectSpentTimeRestDTO(objects, indexTimeFrom, indexTimeTo, typeTime));
				} else {
					spentTimes.get(indexInListProjectsByProjectId).setSpentTimes(objects, indexTimeFrom, indexTimeTo,
							typeTime);
				}
				break;
			case ESTIMATE_TIMES:
				// set List estimateTime to project
				indexInListProjectsByProjectId = getIndexTimeInListEstimateTime(objects, indexTimeFrom, typeTime);
				if (indexInListProjectsByProjectId == null) {
					estimatedTimes
							.add(new ResourceProjectEstimateTimeRestDTO(objects, indexTimeFrom, indexTimeTo, typeTime));
				} else {
					estimatedTimes.get(indexInListProjectsByProjectId).setEstimateTimes(objects, indexTimeFrom,
							indexTimeTo, typeTime);
				}
				break;
			case PLAN_TIMES:
				// set List plan Time to project
				indexInListProjectsByProjectId = getIndexTimeInListPlanTime(objects, indexTimeFrom, typeTime);
				if (indexInListProjectsByProjectId == null) {
					planTimes.add(new ResourceProjectPlanTimeRestDTO(objects, indexTimeFrom, indexTimeTo, typeTime));
				} else {
					planTimes.get(indexInListProjectsByProjectId).setPlanTimes(objects, indexTimeFrom, indexTimeTo,
							typeTime);
				}
				break;
			default:
				// not set time
				break;
			}
			// next time
			indexTimeFrom = indexTimeTo + 1;
		}
	}

	/**
	 * get Index Time In List EstimateTime
	 * 
	 * @param objects
	 * @param indexTimeFrom
	 * @param typeTime
	 * @return Integer
	 * @author: NNDuy
	 */
	private Integer getIndexTimeInListEstimateTime(List<Object> objects, int indexTimeFrom, String typeTime) {
		// get id of list estimate time
		String timeCurrent = getTime(objects, indexTimeFrom, TypeResourceConvert.ESTIMATE_TIMES, typeTime);
		if (null != timeCurrent) {
			for (int i = 0; i < estimatedTimes.size(); i++) {
				if (timeCurrent.equals(estimatedTimes.get(i).getTime())) {
					return i;
				}
			}
		}
		// return not in list estimate time
		return null;
	}

	/**
	 * get Index Time In List SpentTime
	 * 
	 * @param objects
	 * @param indexTimeFrom
	 * @param typeTime
	 * @return Integer
	 * @author: NNDuy
	 */
	private Integer getIndexTimeInListSpentTime(List<Object> objects, int indexTimeFrom, String typeTime) {
		// get id of list spent time
		String timeCurrent = getTime(objects, indexTimeFrom, TypeResourceConvert.SPENT_TIMES, typeTime);
		if (null != timeCurrent) {
			for (int i = 0; i < spentTimes.size(); i++) {
				if (timeCurrent.equals(spentTimes.get(i).getTime())) {
					return i;
				}
			}
		}
		// return not in list spent time
		return null;
	}

	/**
	 * get Index Time In List PlanTime
	 * 
	 * @param objects
	 * @param indexTimeFrom
	 * @param typeTime
	 * @return Integer
	 * @author: NNDuy
	 */
	private Integer getIndexTimeInListPlanTime(List<Object> objects, int indexTimeFrom, String typeTime) {
		// get id of list plan time
		String timeCurrent = getTime(objects, indexTimeFrom, TypeResourceConvert.PLAN_TIMES, typeTime);
		if (null != timeCurrent) {
			for (int i = 0; i < planTimes.size(); i++) {
				if (timeCurrent.equals(planTimes.get(i).getTime())) {
					return i;
				}
			}
		}
		// return not in list plan time
		return null;
	}

	/**
	 * get index record same project
	 * 
	 * @param objects
	 * @param indexProjectFrom
	 * @return int
	 * @author: NNDuy
	 */
	private int getIndexTimeTo(List<Object> objects, int indexTimeFrom, int indexProjectTo, TypeResourceConvert type,
			String typeTime) {
		// get time current
		String timeCurrent = getTime(objects, indexTimeFrom, type, typeTime);
		String timeNext;

		for (int i = indexTimeFrom + 1; i <= indexProjectTo; i++) {
			timeNext = getTime(objects, i, type, typeTime);
			if (timeCurrent != null && !timeCurrent.equals(timeNext)) {
				// return index of project id next
				return i - 1;
			}
		}
		return indexProjectTo;
	}

	/**
	 * get field time of object by date, month, week
	 * 
	 * @param objects
	 * @param index
	 * @return int
	 * @author: NNDuy
	 */
	private static String getTime(List<Object> objects, int index, TypeResourceConvert type, String typeTime) {
		return TypeResourceConvert.SPENT_TIMES.equals(type) ? getTimeForSpentTime(objects, index, typeTime)
				: getTimeForEstimateTimeAndPlanTime(objects, index, typeTime);
	}

	/**
	 * get field time of object by date, month, week for spent time
	 * 
	 * @param objects
	 * @param index
	 * @param type
	 * @param typeTime
	 *            void
	 * @author: NNDuy
	 */
	public static String getTimeForSpentTime(List<Object> objects, int index, String typeTime) {
		// get object by index
		Object[] object = ResourceRestDTO.getObject(objects, index);
		if (null == object[3]) {
			return null;
		}
		String date = object[3].toString();

		// return week
		if (StringPool.TYPE_RESOURCE_WEEK.equals(typeTime)) {
			return ResourceRestDTO.formatTimePeriod(MethodUtil.getFirstDayOfWeekFromDate(date),
					MethodUtil.getLastDayOfWeekFromDate(date));
		} else if (StringPool.TYPE_RESOURCE_MONTH.equals(typeTime)) {
			// return month
			return ResourceRestDTO.formatTimePeriod(MethodUtil.getFirstDayOfMonthFromDate(date),
					MethodUtil.getLastDayOfMonthFromDate(date));
		}
		// return date
		return date;
	}

	/**
	 * get field time of object by date, month, week for spent time
	 * 
	 * @param objects
	 * @param index
	 * @param type
	 * @param typeTime
	 *            void
	 * @author: NNDuy
	 */
	public static String getTimeForEstimateTimeAndPlanTime(List<Object> objects, int index, String typeTime) {
		// get object by index
		Object[] object = ResourceRestDTO.getObject(objects, index);

		String from = getFromDateOfEstimateTimeAndPlanTime(object);
		String to = getToDateOfEstimateTimeAndPlanTime(object);

		if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to)) {
			return null;
		}

		// return week
		if (StringPool.TYPE_RESOURCE_WEEK.equals(typeTime)) {
			return ResourceRestDTO.formatTimePeriod(MethodUtil.getFirstDayOfWeekFromDate(from),
					MethodUtil.getLastDayOfWeekFromDate(to));
		} else if (StringPool.TYPE_RESOURCE_MONTH.equals(typeTime)) {
			// return month
			return ResourceRestDTO.formatTimePeriod(MethodUtil.getFirstDayOfMonthFromDate(from),
					MethodUtil.getLastDayOfMonthFromDate(to));
		}
		// return date
		return ResourceRestDTO.formatTimePeriod(from, to);
	}

	/**
	 * get FromDate of EstimateTime And PlanTime
	 * 
	 * @param object
	 * @return String
	 * @author: NNDuy
	 */
	public static String getFromDateOfEstimateTimeAndPlanTime(Object[] object) {
		return null == object[3] ? null : object[3].toString();
	}

	/**
	 * get ToDate Of ManPower in record
	 * 
	 * @param object
	 * @return String
	 * @author: NNDuy
	 */
	public static String getToDateOfEstimateTimeAndPlanTime(Object[] object) {
		return null == object[4] ? null : object[4].toString();
	}

	/**
	 * caculate total Estimated Times
	 * 
	 * @return float
	 * @author: NNDuy
	 */
	@JsonIgnore
	public float getTotalEstimatedTimes() {
		return MethodUtil.formatFloatTimeResource(
				(float) estimatedTimes.stream().mapToDouble(ResourceProjectEstimateTimeRestDTO::getTotalHours).sum());
	}

	/**
	 * caculate total Plan Times
	 * 
	 * @return float
	 * @author: NNDuy
	 */
	@JsonIgnore
	public float getTotalPlanTimes() {
		return MethodUtil.formatFloatTimeResource(
				(float) planTimes.stream().mapToDouble(ResourceProjectPlanTimeRestDTO::getTotalHours).sum());
	}

	/**
	 * caculate total spent Times
	 * 
	 * @return float
	 * @author: NNDuy
	 */
	@JsonIgnore
	public float getTotalSpentTimes() {
		return MethodUtil.formatFloatTimeResource(
				(float) spentTimes.stream().mapToDouble(ResourceProjectSpentTimeRestDTO::getTotalHours).sum());
	}
}
