/**
 * dashboard-phase2-backend- - com.cmc.dashboard.util
 */
package com.cmc.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import javax.el.TypeConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.cmc.dashboard.dto.ResourceFilterDto;
import com.cmc.dashboard.dto.ResourceRestDTO;
import com.cmc.dashboard.dto.TimeProjectDTO;
import com.cmc.dashboard.repository.ProjectRoleRepository;
import com.cmc.dashboard.repository.SkillRepository;
import com.cmc.dashboard.util.Constants.TypeResourceConvert;

/**
 * @author: NNDuy
 * @Date: Apr 18, 2018
 */
public final class ConvertDto {
  private ConvertDto() {

  }

  /**
   * convert list object to list ResourceFilterDto for project or user
   * 
   * @param objects
   * @return List<ResourceRestDTO>
   * @author: NNDuy
   */
  public static List<ResourceFilterDto> convertResourceFilterDtos(final List<Object> objects) {

    List<ResourceFilterDto> dtos = new ArrayList<>();

    // validate objects
    if (MethodUtil.checkList(objects)) {
      return dtos;
    }

    // convert
    for (Object obj : objects) {
      Object[] row = (Object[]) obj;
      dtos.add(new ResourceFilterDto(row));
    }

    return dtos;
  }

  /**
   * init resource
   *
   * @param resources
   * @param objects
   * @param type
   * @param typeTime
   * @param toDate
   * @return List<ResourceRestDTO>
   * @author: NNDuy
   */
  public static List<ResourceRestDTO> initResourceDtos(final List<Object> objects) {

    List<ResourceRestDTO> resources = new ArrayList<>();

    // validate objects
    if (MethodUtil.checkList(objects)) {
      return resources;
    }

    for (Object obj : objects) {
      resources.add(new ResourceRestDTO((Object[]) obj));
    }

    return resources;
  }

  /**
   * Convert resource by date
   *
   * @param resources
   * @param objects
   * @param type
   * @param typeTime
   * @param toDate
   * @return List<ResourceRestDTO>
   * @author: NNDuy
   */
  public static List<ResourceRestDTO> convertResourceDtos(final List<ResourceRestDTO> resources,
      final List<Object> objects, final TypeResourceConvert type, final String typeTime,
      final String toDate) {

    // validate objects
    if (MethodUtil.checkList(objects)) {
      return resources;
    }

    // list != null then convert
    int indexUserFrom = 0;
    int indexUserTo;
    Integer indexInListResourceByUserId;
    while (indexUserFrom < objects.size()) {
      if(type.equals(TypeResourceConvert.PLAN_TIMES)) {
    	List<Object> objectPlan =new ArrayList<>();
    	int indexPlanFrom = 0;
      	int indexPlanTo;
      	for(int i = indexPlanFrom; i < objects.size() - 1 ; i++) {
      		if(Integer.parseInt(((Object[])objects.get(i))[1].toString()) != Integer.parseInt(((Object[])objects.get(i+1))[1].toString())) {
      			indexPlanTo = i;
      			Object[] temp = (Object[]) objects.get(indexPlanFrom);
      			String skill = "";
      			String skillId = "";
      			for(int j=indexPlanFrom; j <= indexPlanTo; j++) {
      				skill += ((Object[]) objects.get(j))[8].toString() + ",";
      				skillId += ((Object[]) objects.get(j))[9].toString() + ",";
      			}
      			skill = skill.substring(0, skill.length()-1);
      			skillId = skillId.substring(0, skillId.length()-1);
      			temp[8] = skill;
      			temp[9] = skillId;
      			objectPlan.add(temp);
      			indexPlanFrom = indexPlanTo + 1;
      		}
      	}
      	Object[] temp = (Object[]) objects.get(indexPlanFrom);
      	String skill = "";
			String skillId = "";
      	for(int i = indexPlanFrom ; i < objects.size(); i ++) {
  		    skill += ((Object[]) objects.get(i))[8].toString() + ",";
  		    skillId += ((Object[]) objects.get(i))[9].toString() + ",";
      	}
      	skill = skill.substring(0, skill.length()-1);
			skillId = skillId.substring(0, skillId.length()-1);
      	temp[8] = skill;
			temp[9] = skillId;
			objectPlan.add(temp);
      	objects.clear();
      	objects.addAll(objectPlan);
      }
      indexUserTo = getIndexUserTo(objects, indexUserFrom);
      // convert 1 user
      switch (type) {
      case PROJECT:
        // get index of user id
        indexInListResourceByUserId = getIndexInListResourceByUserId(resources,
            getUserId(objects, indexUserFrom));
        // if not exist userId in Resource
        if (null != indexInListResourceByUserId) {
          // set project in 1 user
          resources.get(indexInListResourceByUserId).setProject(objects, indexUserFrom,
              indexUserTo);
        }
        break;
      case MAN_POWER:
        // get index of user id
        indexInListResourceByUserId = getIndexInListResourceByUserId(resources,
            getUserId(objects, indexUserFrom));
        // if not exist userId in Resource
        if (null != indexInListResourceByUserId) {
          // convert man power in 1 user
          resources.get(indexInListResourceByUserId).setManPowerForUsers(objects, indexUserFrom,
              indexUserTo, toDate, typeTime);
        }
        break;
      case SKILL:
    	  indexInListResourceByUserId = getIndexInListResourceByUserId(resources,
    	           getUserId(objects, indexUserFrom));
    	        // if not exist userId in Resource
    	        if (null != indexInListResourceByUserId) {
    	          // set project in 1 user
    	          resources.get(indexInListResourceByUserId).setSkills(objects, indexUserFrom,indexUserTo);
    	        }    
    	  break;
      default:
        // get index of user id
        indexInListResourceByUserId = getIndexInListResourceByUserId(resources,
                getUserId(objects, indexUserFrom));
        // if not exist userId in Resource
        if (null != indexInListResourceByUserId) {
          // convert all project in 1 user
          resources.get(indexInListResourceByUserId).setTimeProjectForUsers(objects, indexUserFrom,
              indexUserTo, type, typeTime);
        }
        break;
      }

      // next user
      indexUserFrom = indexUserTo + 1;
    }
    return resources;
  }

  /**
   * get Index In List Resource By UserId
   * 
   * @param resources
   * @param userId
   * @return int
   * @author: NNDuy
   */
  private static Integer getIndexInListResourceByUserId(List<ResourceRestDTO> resources,
      Integer userId) {
    if (null == userId) {
      return null;
    }
    for (int i = 0; i < resources.size(); i++) {
      if (userId.equals(resources.get(i).getUserId())) {
        return i;
      }
    }
    return null;
  }

  /**
   * get index record same user
   * 
   * @param objects
   * @param indexFromUser
   * @return int
   * @author: NNDuy
   */
  private static int getIndexUserTo(List<Object> objects, int indexFromUser) {
    // get user id current
    int userIdCurrent = getUserId(objects, indexFromUser);
    int userIdNext;
    for (int i = indexFromUser + 1; i < objects.size(); i++) {
      userIdNext = getUserId(objects, i);
      if (userIdCurrent != userIdNext) {
        // return index of user name
        return i - 1;
      }
    }
    return objects.size() - 1;
  }

  /**
   * get field user id of object
   * 
   * @param objects
   * @param index
   * @return int
   * @author: NNDuy
   */
  public static Integer getUserId(List<Object> objects, int index) {
    // get object by index
    Object[] object = getObject(objects, index);

    return Integer.parseInt((object[0].toString()));
  }

  /**
   * get object from index in list resource
   * 
   * @param objects
   * @param index
   * @return Object[]
   * @author: NNDuy
   */
  private static Object[] getObject(List<Object> objects, int index) {
    return (Object[]) objects.get(index);
  }

  /**
   * convert list object to list TimeProjectDto
   * 
   * @param timeOfListProject
   * @return List<TimeProjectDTO>
   * @author: NNDuy
   */
  public static List<TimeProjectDTO> convertTimeProjectDto(List<Object> objects) {
    List<TimeProjectDTO> dtos = new ArrayList<>();

    // validate objects
    if (MethodUtil.checkList(objects)) {
      return dtos;
    }

    // list != null then convert
    int indexProjectFrom = 0;
    int indexProjectTo;
    while (indexProjectFrom < objects.size()) {
      indexProjectTo = indexProjectFrom + 1;
      dtos.add(new TimeProjectDTO(objects, indexProjectFrom, indexProjectTo));
      // next project
      indexProjectFrom = indexProjectTo + 1;
    }
    return dtos;
  }

}