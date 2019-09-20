package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cmc.dashboard.util.Constants;

@Entity
@Table(name = "skill")
public class Skill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5281841100290797500L;
	@TableGenerator(name = "Skill_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "skill_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Skill_Gen")
	@Column(name = "skill_id", unique = true, nullable = false)
	private int skillId;
	
	@Column(name = "skill_name", nullable = false)
	private String name;
	@Column(name = "image", nullable = false)
	private String image;
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE },mappedBy="skill")
	private Set<Project> projects;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE },mappedBy="role")
	private Set<User> user;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Skill(int skillId, String name, String image) {
		super();
		this.skillId = skillId;
		this.name = name;
		this.image = Constants.BASE_URL+image;
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}
	public void addUsers(User user) {
		this.user.add(user);
	}
}
