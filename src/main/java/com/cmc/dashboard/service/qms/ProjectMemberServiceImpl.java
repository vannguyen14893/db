package com.cmc.dashboard.service.qms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ProjectMemberDTO;
import com.cmc.dashboard.dto.ProjectMemberSolutionDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.ProjectMemberRepository;
import com.cmc.dashboard.repository.GroupRepository;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService{
	
	@Autowired
	ProjectMemberRepository projectMemberRepository;
	
	@Autowired
	MemberQmsRepository memberQmsRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Override
	public List<ProjectMemberDTO> getListProjectMember(int projectId) {
		// TODO Auto-generated method stub
		List<ProjectMemberDTO> listprojectMemberDto = new ArrayList<ProjectMemberDTO>();
		
			List<Object> listObj= projectMemberRepository.getListProjectMemberByProjectId(projectId);
			if(listObj == null) {
				return listprojectMemberDto;
			}
	
			for(Object obj: listObj) {
				Object[] oobj = (Object[]) obj;
				ProjectMemberDTO projectMemberDto = new ProjectMemberDTO();
				projectMemberDto.setStartDate(oobj[1] == null ? "" : oobj[1].toString());
				projectMemberDto.setName(oobj[0].toString());
				projectMemberDto.setEndDate(oobj[2] == null? "": oobj[2].toString());
				projectMemberDto.setEffortCalendar(oobj[4] == null ? 0 : Double.parseDouble(oobj[4].toString()));
				projectMemberDto.setEffortActual((projectMemberRepository.getEffortActual(projectId, oobj[3] == null ? 0 : Integer.parseInt(oobj[3].toString())))/3600);
				projectMemberDto.setRemoveDate(oobj[5] == null ? "" : oobj[5].toString());
				Group group = new Group();
				group = groupRepository.findById((int) oobj[6]);
				projectMemberDto.setGroup(group.getGroupName());
				projectMemberDto.setUser_id((int) oobj[3]);
				listprojectMemberDto.add(projectMemberDto);			
			}
		
		return listprojectMemberDto;
	}

	@Override
	public List<ProjectMemberSolutionDTO> getProjectMembers(int projectId) {
		List<ProjectMemberSolutionDTO> projectMemberDtos = new ArrayList<>();
		
		List<Object> listObj= memberQmsRepository.projectMemberSolutions(projectId);
		for(Object obj: listObj) {
			Object[] oobj = (Object[]) obj;
			ProjectMemberSolutionDTO projectMemberDto = new ProjectMemberSolutionDTO();
			projectMemberDto.setUserId( Integer.parseInt(oobj[0].toString()));
			projectMemberDto.setName(oobj[1].toString());
			projectMemberDtos.add(projectMemberDto);			
		}
		return projectMemberDtos;
	}
	
	

}
