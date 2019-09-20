package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.model.Skill;

public interface SkillService {
	
	public Skill findById(int id);

	public List<Skill> listSkill();
	
	public List<Skill> listSkillOfProject(int id);

	public void save(Skill find);

	public Skill create(Skill s);

	boolean delete(int id);
}
