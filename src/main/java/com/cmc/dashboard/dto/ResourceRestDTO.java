/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cmc.dashboard.util.Constants.StringPool;
import com.cmc.dashboard.util.Constants.TypeResourceConvert;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: Apr 17, 2018
 */
public class ResourceRestDTO {
  private int userId;
  private String fullName;
  private int remove;
  private String removeAt;
  private List<ResourceProjectRestDto> projects;
  private List<ResourceManPowerRestDTO> manpowers;
  private List<SkillRestDto> listskills;
  private float totalEstimatedTimes;
  private float totalPlanTimes;
  private float totalSpentTimes;

  /**
   * Constructure init resource
   */
  public ResourceRestDTO(Object[] row) {
    setUserId(row);
    setFullName(row);
    projects = new ArrayList<>();
    manpowers = new ArrayList<>();
    listskills = new ArrayList<>();
    totalEstimatedTimes = 0;
    totalPlanTimes = 0;
    totalSpentTimes = 0;
  }

  public int getRemove() {
	return remove;
}

public void setRemove(int remove) {
	this.remove = remove;
}

public String getRemoceAt() {
	return removeAt;
}

public void setRemoceAt(String remoceAt) {
	this.removeAt = remoceAt;
}

public List<SkillRestDto> getSkills() {
	return listskills;
  }

/**
   * get object from index in list resource
   * 
   * @param objects
   * @param index
   * @return Object[]
   * @author: NNDuy
   */
  public static Object[] getObject(List<Object> objects, int index) {
	if(index < objects.size()) {
        return (Object[]) objects.get(index);
	}
	return null;
  }

  private void setUserId(Object[] object) {
    this.userId = null == object[0] ? 0 : Integer.parseInt(object[0].toString());

  }

  private void setFullName(Object[] object) {
    this.fullName = null == object[1] ? null : object[1].toString();
  }

  /**
   * set list project for user
   * 
   * @param objects
   * @param indexUserFrom
   * @param indexUserTo
   *          void
   * @author: NNDuy
   */
  public void setProject(List<Object> objects, int indexUserFrom, int indexUserTo) {
    for (int i = indexUserFrom; i <= indexUserTo; i++) {
      Object[] object = getObject(objects, i);
      if(object == null) return;
      Integer projectId = getProjectIdFromObjectResource(object);
      String projectName = getProjectNameFromObjectResource(object);
      Integer status = null == object[3] ? null : Integer.parseInt(object[3].toString());
      String startDate = null == object[4] ? null : object[4].toString();
      String endDate = null == object[5] ? null : object[5].toString();
      // if projectId and projectName != null then add to list projects
      if (null != projectId && !StringUtils.isEmpty(projectName)) {
        projects.add(new ResourceProjectRestDto(projectId, projectName, status, startDate, endDate));
      }
    }
  }
  public void setSkills(List<Object> skills,int indexUserFrom,int indexUserTo) {
	    for(int i=indexUserFrom;i<=indexUserTo;i++) {
	    	Object[] object = getObject(skills, i);
	    	Integer userId = null == object[0] ? null : Integer.parseInt(object[0].toString());
	    	Integer skillId = null == object[1] ? null : Integer.parseInt(object[1].toString());
	    	String skillName = null == object[2] ? null : object[2].toString();
	    	if(userId != null && skillId != null && !StringUtils.isEmpty(skillName)){
	    		listskills.add(new SkillRestDto(skillId, skillName));
	    	}
	    }
  }

  /**
   * set time for project of user
   * 
   * @param objects
   * @param indexUserFrom
   * @param indexUserTo
   * @param type
   *          void
   * @author: NNDuy
   */
  public void setTimeProjectForUsers(List<Object> objects, int indexUserFrom, int indexUserTo,
      TypeResourceConvert type, String typeTime) {
    int indexProjectFrom = indexUserFrom;
    int indexProjectTo;

    // set for all project of user current
    while (indexProjectFrom <= indexUserTo) {
      indexProjectTo = getIndexProjectTo(objects, indexProjectFrom, indexUserTo);
      // get index of project id current
      Integer indexInListProjectsByProjectId = getIndexInListProjectsByProjectId(
          getProjectIdFromObjectResource(getObject(objects, indexProjectFrom)));
      // set time
      if(null != indexInListProjectsByProjectId) {
        projects.get(indexInListProjectsByProjectId).setTimeProjects(objects, indexProjectFrom,
            indexProjectTo, type, typeTime);
      }
       
      // next project
      indexProjectFrom = indexProjectTo + 1;
    }

    // caculate total time project
    setTotalTime(type);
  }

  /**
   * caculate total time project
   * 
   * @param objects
   * @param indexUserFrom
   * @param indexUserTo
   * @param type
   *          void
   * @author: NNDuy
   */
  private void setTotalTime(TypeResourceConvert type) {
    switch (type) {
    case SPENT_TIMES:
      setTotalSpentTimes();
      break;
    case ESTIMATE_TIMES:
      setTotalEstimatedTimes();
      break;
    case PLAN_TIMES:
      setTotalPlanTimes();
      break;
    default:
      // not set time
      break;
    }
  }

  /**
   * caculate total Estimated Times
   * 
   * void
   * 
   * @author: NNDuy
   */
  private void setTotalEstimatedTimes() {
    // total estimated time of each project
    for (ResourceProjectRestDto project : projects) {
      totalEstimatedTimes += project.getTotalEstimatedTimes();
    }
  }

  /**
   * caculate total Estimated Times
   * 
   * void
   * 
   * @author: NNDuy
   */
  private void setTotalPlanTimes() {
    // total plan time of each project
    for (ResourceProjectRestDto project : projects) {
      totalPlanTimes += project.getTotalPlanTimes();
    }
  }

  /**
   * caculate total Spent Times
   * 
   * void
   * 
   * @author: NNDuy
   */
  private void setTotalSpentTimes() {
    // total spent time of each project
    for (ResourceProjectRestDto project : projects) {
      totalSpentTimes += project.getTotalSpentTimes();
    }
  }

  /**
   * set man power for user
   * 
   * @param objects
   * @param indexManPowerFrom
   * @param indexManPowerTo
   * @param type
   * @param typeTime
   *          void
   * @author: NNDuy
   */
  public void setManPowerForUsers(final List<Object> objects, final int indexUserFrom,
      final int indexUserTo, final String toDate, final String typeTime) {

    int indexTimeFrom = indexUserFrom;
    int indexTimeTo;
    // set for all man power of user current
    while (indexTimeFrom <= indexUserTo) {
      indexTimeTo = getIndexTimeToOfManPower(objects, indexTimeFrom, indexUserTo, typeTime, toDate);
      manpowers.add(new ResourceManPowerRestDTO(objects, indexTimeFrom, indexTimeTo, typeTime,
          indexUserTo, toDate));
      // next time
      indexTimeFrom = indexTimeTo + 1;
    }
  }

  /**
   * get index record same user
   * 
   * @param objects
   * @param indexTimeFrom
   * @param indexUserTo
   * @param typeTime
   * @param toDate
   * @return int
   * @author: NNDuy
   */
  private int getIndexTimeToOfManPower(List<Object> objects, int indexTimeFrom, int indexUserTo,
      String typeTime, String toDate) {
    // get time current
    String timeCurrent = getTimeOfManPower(objects, indexTimeFrom, typeTime, indexUserTo, toDate);
    String timeNext;

    for (int i = indexTimeFrom + 1; i <= indexUserTo; i++) {
      timeNext = getTimeOfManPower(objects, i, typeTime, indexUserTo, toDate);
      if (null != timeCurrent && !timeCurrent.equals(timeNext)) {
        // return index of max power
        return i - 1;
      }
    }
    return indexUserTo;
  }

  /**
   * get field time of object by date, month, week
   * 
   * @param objects
   * @param index
   * @return int
   * @author: NNDuy
   */
  public static String getTimeOfManPower(List<Object> objects, int index, String typeTime,
      int indexUserTo, String toDate) {
    // get object by index
    Object[] object = ResourceRestDTO.getObject(objects, index);

    String from = getFromDateOfManPower(object);
    String to = getToDateOfManPower(objects, index, indexUserTo, toDate);

    if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to)) {
      return null;
    }

    // return week
    if (StringPool.TYPE_RESOURCE_WEEK.equals(typeTime)) {
      return formatTimePeriod(MethodUtil.getFirstDayOfWeekFromDate(from),
          MethodUtil.getLastDayOfWeekFromDate(to));
    } else if (StringPool.TYPE_RESOURCE_MONTH.equals(typeTime)) {
      // return month
      return ResourceRestDTO.formatTimePeriod(MethodUtil.getFirstDayOfMonthFromDate(from),
          MethodUtil.getLastDayOfMonthFromDate(to));
    }
    // return date
    return formatTimePeriod(from, to);
  }

  /**
   * format time For Estimate And Plan Time
   * 
   * @param from
   * @param to
   * @return String
   * @author: NNDuy
   */
  public static String formatTimePeriod(String from, String to) {
    return from == null || to == null ? null : from + "," + to;
  }

  /**
   * format time for spent time
   * 
   * @param from
   * @param to
   * @return String
   * @author: NNDuy
   */
  public static String formatTime(String from, String to) {
    if (from == null || to == null) {
      return null;
    }

    return from.equals(to) ? from : from + "," + to;
  }

  /**
   * get ToDate Of ManPower
   * 
   * @param objects
   * @param indexManPowerCurrent
   * @param indexUserTo
   * @return String
   * @author: NNDuy
   */
  public static String getToDateOfManPower(List<Object> objects, int indexManPowerCurrent,
      int indexUserTo, String toDate) {
    String toDateOfManPower = getToDateOfManPower(
        (Object[]) getObject(objects, indexManPowerCurrent));
    // if toDate != null
    if (!StringUtils.isEmpty(toDateOfManPower)) {
      return toDateOfManPower;
    }
    // else if last record man power of user == null then toDateOfManPower = toDate
    if (indexManPowerCurrent == indexUserTo) {
      return toDate;
    }
    // else return fromDate of Next Manpower - 1
    LocalDate fromDateOfNextManPower = getFromDateOfManPowerToLocalDate(
        (Object[]) getObject(objects, indexManPowerCurrent + 1));

    return null == fromDateOfNextManPower ? null : fromDateOfNextManPower.minusDays(1).toString();
  }

  /**
   * get ToDate Of ManPower in record
   * 
   * @param object
   * @return String
   * @author: NNDuy
   */
  private static String getToDateOfManPower(Object[] object) {
    return null == object[4] ? null : object[4].toString();
  }

  /**
   * get FromDate Of ManPower and convert to String
   * 
   * @param object
   * @return String
   * @author: NNDuy
   */
  public static String getFromDateOfManPower(Object[] object) {
    return null == object[3] ? null : object[3].toString();
  }

  /**
   * get FromDate Of ManPower and convert to LocalDate
   * 
   * @param object
   * @return LocalDate
   * @author: NNDuy
   */
  private static LocalDate getFromDateOfManPowerToLocalDate(Object[] object) {
    return null == object[3] ? null : MethodUtil.convertDateTimeToLocalDate((Date) object[3]);
  }

  /**
   * get projectId
   * 
   * @param object
   * @return Integer
   * @author: NNDuy
   */
  private Integer getProjectIdFromObjectResource(Object[] object) {
	if(object == null || object[2]==null) return 0;
    return null == object[2] ? null : Integer.parseInt(object[2].toString());
  }

  /**
   * get projectName
   * 
   * @param object
   * @return String
   * @author: NNDuy
   */
  private String getProjectNameFromObjectResource(Object[] object) {
    return null == object[1] ? null : object[1].toString();
  }

  public int getUserId() {
    return userId;
  }

  public String getFullName() {
    return fullName;
  }

  public List<ResourceProjectRestDto> getProjects() {
    return projects;
  }

  /**
   * get Index In List Project By ProjectId
   * 
   * @param resources
   * @param userId
   * @return int
   * @author: NNDuy
   */
  private Integer getIndexInListProjectsByProjectId(Integer projectId) {
    if (null == projectId) {
      return null;
    }
    for (int i = 0; i < projects.size(); i++) {
      if (projectId.equals(projects.get(i).getProjectId())) {
        return i;
      }
    }
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
  private int getIndexProjectTo(List<Object> objects, int indexProjectFrom, int indexUserTo) {
    // get project id current
    int projectIdCurrent = getProjectId(objects, indexProjectFrom);

    int projectIdNext;
    for (int i = indexProjectFrom + 1; i <= indexUserTo; i++) {
      projectIdNext = getProjectId(objects, i);
      if (projectIdCurrent != projectIdNext) {
        // return index of project id next
        return i - 1;
      }
    }
    return indexUserTo;
  }

  /**
   * get field project id of object
   * 
   * @param objects
   * @param index
   * @return int
   * @author: NNDuy
   */
  public static int getProjectId(List<Object> objects, int index) {
    // get object by index
    Object[] object = getObject(objects, index);
    if(object == null) return 0;
    return (null == object[2] ? 0 :(Integer.parseInt(object[2].toString())));
  }

  public List<ResourceManPowerRestDTO> getManpowers() {
    return manpowers;
  }

  public float getTotalEstimatedTimes() {
    return totalEstimatedTimes;
  }

  public float getTotalPlanTimes() {
    return totalPlanTimes;
  }

  public float getTotalSpentTimes() {
    return totalSpentTimes;
  }

}
