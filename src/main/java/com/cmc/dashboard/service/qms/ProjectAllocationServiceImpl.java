package com.cmc.dashboard.service.qms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.DuAllocation;
import com.cmc.dashboard.dto.ProjectAllocationDTO;
import com.cmc.dashboard.dto.ProjectTableDTO;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.ProjectAllocationRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.util.CustomValueUtil;

@Service
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	ProjectAllocationRepository projectAllocationRepository;

	@Autowired
	ProjectBillableRepository projectBillableRepository;
	
	@Autowired
	private UserPlanRepository userPlanRepository;
	
	@Autowired
	private GroupRepository groupRepository;

	public List<ProjectAllocationDTO> getAllProjectByDuWithoutPM(int userId, String dateMonth) {
		List<ProjectAllocationDTO> projects = new ArrayList<>();
//		QmsUser qmsUser = userQmsRepository.findGroup(userId);
//		if (qmsUser == null || qmsUser.getLastname() == null) {
//			return projects;
//		}
//		String groupName = qmsUser.getLastname();
//		if (groupName.contains(CustomValueUtil.PROJECT_MANAGER)) {
//			projects = this.getProjectAllocationfromObj(
//					projectAllocationRepository.getProjectListByUserWithoutPMPage(userId, dateMonth), dateMonth);
//		} else if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)) {
//			projects = this.getProjectAllocationfromObj(
//					projectAllocationRepository.getAllProjectWithoutPMPage(dateMonth), dateMonth);
//		} else if (groupName.contains(CustomValueUtil.DU_LEAD)) {
//			List<String> duName = userQmsRepository.getDeliveryUnitByUser(userId);
//			projects = this.getProjectAllocationfromObj(
//					projectAllocationRepository.getProjectByDeliveryUnitWithoutPMPage(duName, dateMonth), dateMonth);
//		}
		return projects;
	}

	@Override
	public List<ProjectAllocationDTO> getProjectAllocationfromObj(List<Object> listObj, String dateMonth) {
		List<ProjectAllocationDTO> listPro = new ArrayList<>();
		for (Object obj : listObj) {
			Object[] arrObj = (Object[]) obj;
			ProjectAllocationDTO projectAll = new ProjectAllocationDTO();
			projectAll.setProjectId(arrObj[0] != null ? Integer.parseInt(arrObj[0].toString()) : 0);
			projectAll.setProjectName(arrObj[1] != null ? arrObj[1].toString() : "");
			projectAll.setDuPic(arrObj[2] != null ? arrObj[2].toString() : "");
	//		projectAll.setDuAllocation(this.getDuAllocationfromObj(projectAll.getProjectId(), dateMonth));
			listPro.add(projectAll);
		}
		return listPro;
	}

	@Override
	public List<DuAllocation> getDuAllocationfromObj(int projectId, String dateMonth) {
		List<DuAllocation> listDuAll = new ArrayList<>();
		String monthDate = dateMonth.split("-")[1].concat("-").concat(dateMonth.split("-")[0]);
		List<Object> listObj = projectBillableRepository.getDuAllocationbyProjectIdAndMonth(projectId, monthDate);
		for (Object obj : listObj) {
			Object[] arrObj = (Object[]) obj;
			DuAllocation duAll = new DuAllocation();
			duAll.setName(arrObj[0] != null ? arrObj[0].toString() : "");
			duAll.setAllocation(arrObj[1] != null ? Float.parseFloat(arrObj[1].toString()) : 0);
			duAll.setBillAble(arrObj[2] != null ? Float.parseFloat(arrObj[2].toString()) : 0);
			listDuAll.add(duAll);
		}
		return listDuAll;
	}

	@Override
	public List<ProjectAllocationDTO> getListProject(int month, int year) {
		List<ProjectAllocationDTO> result = new ArrayList<ProjectAllocationDTO>();
		try {
		List<Object> listProject = userPlanRepository.getListProject(month, year);
		 for(Iterator<Object> it = listProject.iterator(); it.hasNext();) {
		    	Object[] object = (Object[]) it.next();
		    	Integer projectId=(Integer)object[0];
		    	List<Integer> listDuInvited=userPlanRepository.getListDUInvited(projectId, month, year);
		    	List<DuAllocation> listDu = new ArrayList<DuAllocation>();
		    	for(Integer num : listDuInvited) {
		    		DuAllocation duAllo= new DuAllocation();
		    		duAllo.setName(groupRepository.findOne(num).getGroupName());
		    		String allocation = userPlanRepository.allocation(projectId, month, year, num);
		    		if(allocation!=null) duAllo.setAllocation(Float.parseFloat(allocation));
		    		else duAllo.setAllocation(0.0f);
		    		String billable = userPlanRepository.sumBillableByProject(projectId, month, year, num);
		    		if(billable!=null) duAllo.setBillAble(Float.parseFloat(billable)/userPlanRepository.allocate(projectId, month, year, num));
		    		else duAllo.setBillAble(0.0f);
		    		listDu.add(duAllo);
		    	}
		    	ProjectAllocationDTO dto= new ProjectAllocationDTO();
		    	dto.setProjectId(projectId);
		    	dto.setProjectName((String)object[1]);
		    	dto.setDuPic(object[2]==null?null:(String)object[2]);
	            dto.setDuAllocation(listDu);
		    	result.add(dto);
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
