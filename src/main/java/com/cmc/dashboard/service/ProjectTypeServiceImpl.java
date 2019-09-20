package com.cmc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.ProjectType;
import com.cmc.dashboard.repository.ProjectTypeRepository;

@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {
	  @Autowired
	    private ProjectTypeRepository projectTypeRepo;
		@Override
		public List<ProjectType> getAll() {
			
			return projectTypeRepo.findAll();
		}

		@Override
		public ProjectType insert(String pt) {
			ProjectType success=new ProjectType();
			success.setName(pt);
			try {
				success= projectTypeRepo.save(success);
			}
			catch(Exception e) {
			 e.printStackTrace();	
			}
			return success;
		}

		@Override
		public ProjectType update(ProjectType pt) {
			ProjectType success=null;
			try {
				success= projectTypeRepo.save(pt);
			}
			catch(Exception e) {
			 e.printStackTrace();	
			}
			return success;
		}

		@Override
		public boolean delete(int id) {
			boolean flag= false;
			try {
			projectTypeRepo.delete(id);
			flag=true;
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			return flag;
		}

		@Override
		public ProjectType getProjecTypeById(int id) {
			// TODO Auto-generated method stub
			return projectTypeRepo.findOne(id);
		}


	@Override
	public ProjectType findById(int id) {
		
		return projectTypeRepo.findOne(id);
	}

}
