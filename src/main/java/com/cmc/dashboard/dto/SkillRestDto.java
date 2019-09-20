package com.cmc.dashboard.dto;

public class SkillRestDto {
    private int skillId;
    private String skillName;
    
	public SkillRestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SkillRestDto(int skillId, String skillName) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
    
    
}
