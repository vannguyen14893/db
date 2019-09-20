/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: Feb 26, 2018
 */
public class StoryPointDTO {
	private float storyPointTotal;

	/**
	 * Constructure
	 */
	public StoryPointDTO() {
		super();
	}

	/**
	 * Constructure
	 */
	public StoryPointDTO(float storyPointTotal) {
		super();
		this.storyPointTotal = storyPointTotal;
	}

	public float getStoryPointTotal() {
		return storyPointTotal;
	}

	public void setStoryPointTotal(float storyPointTotal) {
		this.storyPointTotal = storyPointTotal;
	}

}
