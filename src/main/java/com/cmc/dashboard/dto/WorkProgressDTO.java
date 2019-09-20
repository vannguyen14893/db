package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: Jun 4, 2018
 */
public class WorkProgressDTO {
	private int attentionTasksTotal;
	private float todayEstimatedTime;
	private int yesterdayClosedTasksTotal;
	private float yesterdayClosedTasksTime;
	private int openBugsTotal;
	private int yesterdayClosedBugsTotal;
	private int openRisksTotal;
	private float avgCss;
	private float avgBillable;

	public WorkProgressDTO(int attentionTasksTotal, float todayEstimatedTime, int yesterdayClosedTasksTotal,
			float yesterdayClosedTasksTime, int openBugsTotal, int yesterdayClosedBugsTotal, int openRisksTotal) {
		super();
		this.attentionTasksTotal = attentionTasksTotal;
		this.todayEstimatedTime = todayEstimatedTime;
		this.yesterdayClosedTasksTotal = yesterdayClosedTasksTotal;
		this.yesterdayClosedTasksTime = yesterdayClosedTasksTime;
		this.openBugsTotal = openBugsTotal;
		this.yesterdayClosedBugsTotal = yesterdayClosedBugsTotal;
		this.openRisksTotal = openRisksTotal;
	}
	
	public WorkProgressDTO(float todayEstimatedTime, int openBugsTotal, int openRisksTotal, float avgCss, float avgBillable) {
		super();
		this.todayEstimatedTime = todayEstimatedTime;
		this.openBugsTotal = openBugsTotal;
		this.openRisksTotal = openRisksTotal;
		this.avgCss = avgCss;
		this.avgBillable = avgBillable;
	}

	public int getAttentionTasksTotal() {
		return attentionTasksTotal;
	}

	public void setAttentionTasksTotal(int attentionTasksTotal) {
		this.attentionTasksTotal = attentionTasksTotal;
	}

	public float getTodayEstimatedTime() {
		return todayEstimatedTime;
	}

	public void setTodayEstimatedTime(float todayEstimatedTime) {
		this.todayEstimatedTime = todayEstimatedTime;
	}

	public int getYesterdayClosedTasksTotal() {
		return yesterdayClosedTasksTotal;
	}

	public void setYesterdayClosedTasksTotal(int yesterdayClosedTasksTotal) {
		this.yesterdayClosedTasksTotal = yesterdayClosedTasksTotal;
	}

	public float getYesterdayClosedTasksTime() {
		return yesterdayClosedTasksTime;
	}

	public void setYesterdayClosedTasksTime(float yesterdayClosedTasksTime) {
		this.yesterdayClosedTasksTime = yesterdayClosedTasksTime;
	}

	public int getOpenBugsTotal() {
		return openBugsTotal;
	}

	public void setOpenBugsTotal(int openBugsTotal) {
		this.openBugsTotal = openBugsTotal;
	}

	public int getYesterdayClosedBugsTotal() {
		return yesterdayClosedBugsTotal;
	}

	public void setYesterdayClosedBugsTotal(int yesterdayClosedBugsTotal) {
		this.yesterdayClosedBugsTotal = yesterdayClosedBugsTotal;
	}

	public int getOpenRisksTotal() {
		return openRisksTotal;
	}

	public void setOpenRisksTotal(int openRisksTotal) {
		this.openRisksTotal = openRisksTotal;
	}

	public float getAvgBillable() {
		return avgBillable;
	}

	public void setAvgBillable(float avgBillable) {
		this.avgBillable = avgBillable;
	}

	public float getAvgCss() {
		return avgCss;
	}

	public void setAvgCss(float avgCss) {
		this.avgCss = avgCss;
	}

}
