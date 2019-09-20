package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.model.ProjectType;

public interface ProjectTypeService {
	List <ProjectType> getAll();
    ProjectType insert(String pt);
    ProjectType update(ProjectType pt);
    boolean delete(int id);

    ProjectType findById(int id);

    ProjectType getProjecTypeById(int id);

}
