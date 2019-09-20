package com.cmc.dashboard.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourcesAvailableDTO {
      private Set<String> skills;
      private Set<String> level;
      private Map<String,Integer> book;
      private Map<String,Integer> available;
      
	public ResourcesAvailableDTO(Set<String> skills, Set<String> level, Map<String, Integer> book,
			Map<String, Integer> available) {
		super();
		this.skills = skills;
		this.level = level;
		this.book = book;
		this.available = available;
	}

	public ResourcesAvailableDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public Set<String> getLevel() {
		return level;
	}

	public void setLevel(Set<String> level) {
		this.level = level;
	}

	public Map<String, Integer> getBook() {
		return book;
	}

	public void setBook(Map<String, Integer> book) {
		this.book = book;
	}

	public Map<String, Integer> getAvailable() {
		return available;
	}

	public void setAvailable(Map<String, Integer> available) {
		this.available = available;
	}
      
      
      
      
}
