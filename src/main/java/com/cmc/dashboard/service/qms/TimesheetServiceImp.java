package com.cmc.dashboard.service.qms;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.TimesheetDTO;
import com.cmc.dashboard.qms.repository.TimesheetReponsitory;

@Service
public class TimesheetServiceImp implements TimesheetService{
	
	@Autowired
	TimesheetReponsitory timesheetReponsitory;
	
	@Override
	public List<TimesheetDTO> getTimesheet(int resourceId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		List<TimesheetDTO> listTimesheet = new ArrayList<TimesheetDTO>();
		List<Object> listObj = timesheetReponsitory.getListTimesheet(resourceId, fromDate, toDate);
		for(Object obj : listObj) {
			Object[] arrOjb = (Object[]) obj;
			TimesheetDTO timeDto = new TimesheetDTO();
			timeDto.setIssueNumeber(arrOjb[0]!=null ? arrOjb[0].toString() : "");
			timeDto.setIssueName(arrOjb[1]!=null ? arrOjb[1].toString() : "");
			timeDto.setLogTimeDate(arrOjb[2]!=null ? arrOjb[2].toString() : "");
			timeDto.setEstimateTime(arrOjb[3]!=null ? Float.parseFloat(arrOjb[3].toString()) : 0);
			timeDto.setSpentHours(arrOjb[4]!=null ? Float.parseFloat(arrOjb[4].toString()) : 0);
			timeDto.setStatus(arrOjb[5]!=null ? arrOjb[5].toString() : "");
			timeDto.setProjectName(arrOjb[6]!=null ? arrOjb[6].toString() : "");
			listTimesheet.add(timeDto);
		}	
		return listTimesheet;
	}

	@Override
	public List<TimesheetDTO> getTimesheet(int resourceId){
		// TODO Auto-generated method stub
		List<TimesheetDTO> listTimesheet = new ArrayList<TimesheetDTO>();
		List<Object> listObj = timesheetReponsitory.getListTimesheet(resourceId);
		for(Object obj : listObj) {
			Object[] arrOjb = (Object[]) obj;
			TimesheetDTO timeDto = new TimesheetDTO();
			timeDto.setIssueNumeber(arrOjb[0]!=null ? arrOjb[0].toString() : "");
			timeDto.setIssueName(arrOjb[1]!=null ? arrOjb[1].toString() : "");
			timeDto.setLogTimeDate(arrOjb[2]!=null ? arrOjb[2].toString() : "");
			timeDto.setEstimateTime(arrOjb[3]!=null ? Float.parseFloat(arrOjb[3].toString()) : 0);
			timeDto.setSpentHours(arrOjb[4]!=null ? Float.parseFloat(arrOjb[4].toString()) : 0);
			timeDto.setStatus(arrOjb[5]!=null ? arrOjb[5].toString() : "");
			listTimesheet.add(timeDto);
		}
		return listTimesheet;
	}

}
