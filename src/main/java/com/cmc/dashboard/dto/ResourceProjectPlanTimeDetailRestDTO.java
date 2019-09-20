package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.model.ProjectRole;
import com.cmc.dashboard.util.MethodUtil;

public class ResourceProjectPlanTimeDetailRestDTO {

  private int id;
  private String fromDate;
  private String toDate;
  private float totalHours;
  private float hoursPerDay;
  private ProjectRole role;
  private List<SkillRestDto> listSkills;

  /**
   * Constructure
   */
  public ResourceProjectPlanTimeDetailRestDTO(Object[] object) {
    this.id = getIdFromObjectResource(object);
    this.fromDate = ResourceProjectRestDto.getFromDateOfEstimateTimeAndPlanTime(object);
    this.toDate = ResourceProjectRestDto.getToDateOfEstimateTimeAndPlanTime(object);
    this.hoursPerDay = getEffortPerDayFromObjectResource(object);
    this.totalHours = this.hoursPerDay * MethodUtil.getTotalWorkingDaysBetweenDateWeekendF(
        MethodUtil.convertStringToDate(fromDate), MethodUtil.convertStringToDate(this.toDate));
    this.role = new ProjectRole(null == object[7] ? 0 :Integer.parseInt(object[7].toString()),null == object[6] ? "" : object[6].toString());
    String skilName = (null == object[8] ? "" : object[8].toString());
    String skillId = (null == object[9] ? "" : object[9].toString());
    String [] skillNames = skilName.split(",");
    String [] skillIds = skillId.split(",");
    this.listSkills = convertListSkill(skillNames,skillIds);
  }

  public int getId() {
    return id;
  }
  
  public ProjectRole getRole() {
	return role;
}

public void setRole(ProjectRole role) {
	this.role = role;
}

public List<SkillRestDto> getListSkills() {
	return listSkills;
}

public void setListSkills(List<SkillRestDto> listSkills) {
	this.listSkills = listSkills;
}

public String getFromDate() {
    return fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }

  public float getHoursPerDay() {
    return MethodUtil.formatFloatTimeResource(hoursPerDay);
  }

  /**
   * get field Effort per day
   * 
   * @param object
   * @return float
   * @author: NNDuy
   */
  private float getEffortPerDayFromObjectResource(Object[] object) {
    return null == object[5] ? 0 : Float.parseFloat(object[5].toString());
  }

  /**
   * get field id
   * 
   * @param object
   * @return int
   * @author: NNDuy
   */
  private int getIdFromObjectResource(Object[] object) {
    return null == object[1] ? 0 : Integer.parseInt(object[1].toString());
  }
  private List<SkillRestDto> convertListSkill (String[] skillNames,String[] skillIds){
	  List<SkillRestDto> result = new ArrayList<>();
	  SkillRestDto skill =null;
	  for(int i = 0 ; i< skillIds.length ; i++) {
		  skill = new SkillRestDto(Integer.parseInt(skillIds[i]), skillNames[i]);
		  result.add(skill);
	  }
	  return result;
  }

}
