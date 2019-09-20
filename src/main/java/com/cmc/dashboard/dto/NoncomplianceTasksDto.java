package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: May 14, 2018
 */
public class NoncomplianceTasksDto extends OverdueTasksDto {
	private float spentTime;
	private int doneRatio;

	public float getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(float spentTime) {
		this.spentTime = spentTime;
	}

	public int getDoneRatio() {
		return doneRatio;
	}

	public void setDoneRatio(int doneRatio) {
		this.doneRatio = doneRatio;
	}

}
