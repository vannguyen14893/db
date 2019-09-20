package com.cmc.dashboard.service.qms;

import java.util.Date;
import java.util.List;

import com.cmc.dashboard.dto.TimesheetDTO;

public interface TimesheetService {
	
	public List<TimesheetDTO> getTimesheet(int resourceId, Date fromDate, Date toDate);
	public List<TimesheetDTO> getTimesheet(int resourceId);
	
}
