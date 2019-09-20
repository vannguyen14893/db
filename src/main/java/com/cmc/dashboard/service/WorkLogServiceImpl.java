package com.cmc.dashboard.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.WorklogDTO;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.model.WorkLog;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.repository.WorkLogRepository;
import com.cmc.dashboard.util.CalculateClass;

@Service
public class WorkLogServiceImpl implements WorkLogService {

	
	
@Autowired
private UserRepository userRepository;
@Autowired
private WorkLogRepository workLogRepository;
	@Override
	  public boolean saveWorkLogJira(List<WorklogDTO> listWorklog,int issueId) {
		  CalculateClass cal = new CalculateClass();
		  SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
		  try {
		  List<Integer> listWorklogNew= new ArrayList<Integer>();
		  for(WorklogDTO workLog : listWorklog)listWorklogNew.add(workLog.getWorklogId().intValue());
		  List<Integer> listWorklogOld = userRepository.listWorkLogIdByIssueIdId(issueId);
		  List<Integer> listDelete = cal.differentNumber(listWorklogOld, listWorklogNew);
		  List<Integer> listInsert = cal.differentNumber(listWorklogNew, listWorklogOld);
		  
		  String deleteString = listDelete.toString();
		  String insertString = listInsert.toString();
		  for(WorklogDTO workLog : listWorklog) {
			  String userName=workLog.getUserName();
			  User user = userRepository.findByUserName(userName);
			   if(insertString.indexOf(workLog.getWorklogId().toString())!=-1) {
				  WorkLog log= new WorkLog();
				  log.setId(workLog.getWorklogId().intValue());
				  log.setLogWorkTime((int)workLog.getWorklogTime());
				  log.setProject_task_id(issueId);
				  if(workLog.getCreated()!=null)
				  log.setCreate_at(formatter.parse(workLog.getCreated()));
				  if(workLog.getUpdated()!=null)
				  log.setUpdate_at(formatter.parse(workLog.getUpdated()));
				  if(user!=null)
				  log.setUser_id(user.getUserId());
				  workLogRepository.save(log);
			  }
			  else {
				  int id=workLog.getWorklogId().intValue();
				  System.out.println(id);
				  WorkLog log = workLogRepository.findOne(id);
				  String oldTime="";
				  if(log.getUpdate_at()!=null)
			      oldTime = formatter.format(log.getUpdate_at());
				  if(log.getUpdate_at()== null || (oldTime.equals(workLog.getUpdated())==false)) {
				  log.setLogWorkTime((int)workLog.getWorklogTime());
				  if(workLog.getUpdated()!=null)
					  log.setUpdate_at(formatter.parse(workLog.getUpdated()));
				  workLogRepository.save(log);
			  } 
				  }
		  }
		  if(deleteString.equals("[]")==false) {
			  deleteString=deleteString.substring(1,deleteString.length()-1);
			  String[] array =deleteString.split(",");
			  System.out.println(deleteString);
			  for(String tem : array)
			  workLogRepository.delete(Integer.parseInt(tem));
		  }
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		return true;
		 
	  }
	@Override
	public int deleteWorkLogByProjectTaskId(int projectTaskId) {
		workLogRepository.deleteByProjectTaskId(projectTaskId);
		return 0;
	}
}
