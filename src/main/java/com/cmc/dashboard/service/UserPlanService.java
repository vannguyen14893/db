package com.cmc.dashboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cmc.dashboard.dto.ListResourceDetailDTO;
import com.cmc.dashboard.dto.RemoveResourcePlanDTO;
import com.cmc.dashboard.dto.ResourceAllocationDTO;
import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.dto.TimesheetDTO;
import com.cmc.dashboard.dto.UpdateResourceDTO;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.util.Constants;


public interface UserPlanService {

    /**
     * GET LIST RESOURCE ALLOCATION OF PROJECT.
     * @param projectId
     * @return List<UserPlanUtilizationDTO> 
     * @author: Hoai-Nam
     */
    public List<ResourceAllocationDTO> getListResourcesByProjectId(int projectId);
    
    /**
     * ALOCATE FOR MEMBER.
     * @param saveListUPlan
     * @return String 
     * @author: Hoai-Nam
     */
  
    public RestResponse saveNewPlan(String plan);
    
    public UpdateResourceDTO updatePlan(String plan);
    
    /**
     * delete user plan.
     * @param userId
     * @return String 
     * @author: Hoai-Nam
     */
    public RemoveResourcePlanDTO removePlan(int planId,int userId, int projectId);  
    
    /**
     * get resource detail.
     * @param userId
     * @param month
     * @param year
     * @return ResourceDetailDTO 
     * @author: Hoai-Nam
     */
    public ListResourceDetailDTO getResourceDetail(String userLoginId,String userId,String planMonth);
    
    
    /**
     * @param userId
     * @param projectId
     * @return
     */
    public List<ResourceDTO> getResourcePlanByUser(int userId, int projectId);

    /**
     * check exist plan id
     * 
     * @param planId
     * @return boolean 
     * @author: NNDuy
     */
    public boolean isExistPlanId(Integer planId);

    /**
     * get Project Id From User Plan
     * 
     * @param planId
     * @return int 
     * @author: NNDuy
     */
    public int getProjectIdFromUserPlan(Integer planId);
    
    /**
     * @param userId
     * @param month
     * @param year
     * @return
     */
    public Map<String, Object> getListTimeSheetByUserId(int userId,int month, int year,String projectName, int activePage, int size);
    
    public List<String> getListProjectTimeSheetByUserId(int userId,int month, int year);
    /**
     * @param date
     * @param userId
     * @return
     */
    public List<Integer> getSumEffortPerDayByDay(String date,int userId);
    
    /**
     * @param planId
     * @return
     */
    public UserPlan getPlanById(int planId);
    
    public ResponseEntity<Object> checkUserPlan(int userId,String fromDate , String toDate);
}


