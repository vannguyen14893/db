/**
 * DashboardSystem - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cmc.dashboard.dto.DuDTO;
import com.cmc.dashboard.dto.ProjectResourceAllocationDTO;
import com.cmc.dashboard.dto.ReAllocationDTO;
import com.cmc.dashboard.dto.ReAllocationTableDTO;
//import com.cmc.dashboard.dto.ReAllocationTableDTO;
import com.cmc.dashboard.dto.ResourceFilterDto;
import com.cmc.dashboard.dto.ResourcePlansDTO;
import com.cmc.dashboard.dto.ResourceRestDTO;
import com.cmc.dashboard.dto.ResourcesAllocationRestDTO;
import com.cmc.dashboard.dto.ResourcesAllocationUpdateRestDTO;
import com.cmc.dashboard.dto.UnallocationListDTO;
import com.cmc.dashboard.model.ManPower;
import com.cmc.dashboard.model.ResourceAllocationDb;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.qms.model.ResourceAllocationQms;
import com.cmc.dashboard.util.filter.FilterResource;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
public interface ResourceService {

	/**
	 * Get all Delivery Unit in Office
	 * 
	 * @return
	 * @throws SQLException
	 *             List<String>
	 * @author: ngocdv
	 */
	List<String> getAllUserDeliveryUnit();


	/**nvtiep2
	 * @param userId
	 * @param month
	 * @param year
	 * @return
	 */
	public List<ResourcePlansDTO> getResourcePlans(int userId,int month, int year);

	/**
	 * Get all man power
	 * 
	 * @param userId
	 * @return List<ManPower>
	 * @author: DVNgoc
	 */
	public List<ManPower> getAllManPowers(int userId);

	/**
	 * Create new a ManPower
	 * 
	 * @param manPower
	 * @return ManPower
	 * @author: ngocdv
	 */
	public void createManPower(ManPower manPower);
	
	/**
	 * update a ManPower
	 * 
	 * @param manPower
	 * @return ManPower
	 * @author: ntquy
	 */
	public void updateManPowers(List<ManPower> listManPower);

	/**
	 * delete a ManPower
	 * 
	 * @param manPower
	 * @return ManPower
	 * @author: ntquy
	 */
	public void deleteManPower(int id);

	/**
	 * Update a ManPower
	 * 
	 * @param manPower
	 * @return ManPower
	 * @author: ngocdv
	 */
	public void updateManPower(ManPower manPower, ManPower entity);

	/**
	 * Check valid Man Power
	 * 
	 * @param ManPower
	 * @return boolean
	 * @author: ngocdv
	 */
	public boolean checkValidManPower(ManPower manPower);

	public boolean checkValidateManPower(ManPower manPower, ManPower manPowerOld);
	
	public boolean checkValidateSameMonthManPower(ManPower manPower);
	
	public boolean checkExistManPowerByMonth(ManPower manPower);
	
	public ManPower getManPowerById(int id);
	/**
	 * Method return list unallocation.
	 * 
	 * @param month
	 * @param year
	 * @return List<UnallocationDTO>
	 * @author: Hoai-Nam
	 */
	public UnallocationListDTO getUnallocations(int month, int year, int page, int size, String column, String sort,
			String resourceName, String status, String du);

	public List<ProjectResourceAllocationDTO> getResourceAllocation(String date, String monthAndYear, String duPic,
			int projectId);

	public List<ProjectResourceAllocationDTO> fiterResourceAllocation(String date, String monthAndYear, String duPic,
			String resourceName, String du, int projectId);

	public DuDTO getDuList(String du);

	public Page<ResourceAllocationQms> getRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId);

	/**
	 * get all project to filter
	 * 
	 * @return List<ResourceFilterDto>
	 * @author: NNDuy
	 */
	public List<ResourceFilterDto> getAllProjects(int userId);

	/**
	 * get all member to filter
	 * 
	 * @return List<ResourceFilterDto>
	 * @author: NNDuy
	 */
	public List<ResourceFilterDto> getAllMembers();

	/**
	 * get all DU to filter
	 * 
	 * @return List<String>
	 * @author: NNDuy
	 */
	public List<String> getAllDus(int userId);

	/**
	 * Get Resource Plan for Resource Monitor Feature
	 * 
	 * @param filter
	 * @param typeTime
	 * @param page
	 * @param numberPerPage
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	public Page<ResourceRestDTO> getResources(final FilterResource filter, final String typeTime, final int page,
			final int numberPerPage,int userId);

	public Page<ResourceAllocationQms> getAllRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId);
	
	public Page<ResourceAllocationDb> getAllallocation(int page, String column, String sort, String dateym,
			String datemy);

	public Page<ResourceAllocationQms> getListResourceQms(Page<ResourceAllocationDb> resDbs,
			Page<ResourceAllocationQms> pageQmss);

	public Page<ResourceAllocationQms> getListResourceDashboard(Page<ResourceAllocationQms> pageQmss,
			List<ResourceAllocationDb> listAllDbs, List<ResourceAllocationQms> listAllQmss, String sort, String column,
			int page);

	/**
	 * 
	 * @param month
	 * @param year
	 * @param page
	 * @param size
	 * @param column
	 * @param sort
	 * @param resourceName
	 * @param status
	 * @param DU
	 * @return UnallocationListDTO
	 * @author duyhieu
	 */
	public UnallocationListDTO exportUnallocations(int month, int year, int page, int size, String column, String sort,
			String resourceName, String status, String DU);

	/**
	 * @author duyhieu
	 * @param page
	 * @param column
	 * @param sort
	 * @param dateym
	 * @param datemy
	 * @return
	 */
	public Page<ResourceAllocationDb> exportAllallocation(int page, String column, String sort, String dateym,
			String datemy);

	/**
	 * export Ressource Allocation
	 * 
	 * @author duyhieu
	 * @param page
	 * @param column
	 * @param sort
	 * @param dateym
	 * @param resName
	 * @param du
	 * @param duPic
	 * @param projectId
	 * @return
	 */
	public Page<ResourceAllocationQms> exportRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId);

	/**
	 * @author duyhieu
	 * @param page
	 * @param column
	 * @param sort
	 * @param dateym
	 * @param resName
	 * @param du
	 * @param duPic
	 * @param projectId
	 * @return
	 */
	public Page<ResourceAllocationQms> exportAllRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId);

	/**
	 * save Allocation
	 * 
	 * @param allocation
	 * @return UserPlan
	 * @author: NNDuy
	 */
	public UserPlan saveAllocation(ResourcesAllocationRestDTO allocation) throws ParseException;
	
  /**
   * update Allocation
   * 
   * @param allocation
   * @return UserPlan 
   * @author: NNDuy
   */
  public UserPlan updateAllocation(ResourcesAllocationUpdateRestDTO allocation) throws ParseException;

  public void deleteResourcesAllocation(int id);
  
  public ReAllocationTableDTO getRessourceAllAllocation(int page, String column, String sort, int month,int ỷear,
			String resName, String du, String duPic, String projectName);
 
}
