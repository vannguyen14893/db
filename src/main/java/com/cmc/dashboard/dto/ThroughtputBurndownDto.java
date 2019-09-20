package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: May 28, 2018
 */
public class ThroughtputBurndownDto {
	private String selectedDate;
	private Float realRemainEstimatedHours;
	private float idealtRemainEstimatedHours;

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Float getRealRemainEstimatedHours() {
		return realRemainEstimatedHours;
	}

	public void setRealRemainEstimatedHours(Float realRemainEstimatedHours) {
		this.realRemainEstimatedHours = realRemainEstimatedHours;
	}

	public float getIdealtRemainEstimatedHours() {
		return idealtRemainEstimatedHours;
	}

	public void setIdealtRemainEstimatedHours(float idealtRemainEstimatedHours) {
		this.idealtRemainEstimatedHours = idealtRemainEstimatedHours;
	}

}
