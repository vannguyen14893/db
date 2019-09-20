package com.cmc.dashboard.service;

import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.repository.SkillRepository;
import com.cmc.dashboard.util.Constants;

@Service
public class SkillServiceImpl implements SkillService{

	@Autowired
	private SkillRepository skillRepository;
	@Override
	public Skill findById(int id) {
		// TODO Auto-generated method stub
		return skillRepository.findOne(id);
	}
	@Override
	public List<Skill> listSkill() {
		// TODO Auto-generated method stub
		List<Skill>result = skillRepository.findAll();
		for(Skill skill : result) {
			skill.setImage(Constants.BASE_URL+skill.getImage()+"?t="+new Date().getTime());
		}
		return result;
	}
	@Override
	public List<Skill> listSkillOfProject(int id) {
		// TODO Auto-generated method stub
		return skillRepository.listSkillOfProject(id);
	}
	
	@Override
	public void save(Skill find) {
		skillRepository.save(find);

	}
	@Override
	public Skill create(Skill s) {
	  return skillRepository.save(s);
	}
	@Override
	public boolean delete(int id) {
		boolean flag= false;
		try {
		skillRepository.delete(id);
		flag=true;
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return flag;
	}
	
}
