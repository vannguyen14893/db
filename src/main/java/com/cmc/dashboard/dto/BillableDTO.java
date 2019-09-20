package com.cmc.dashboard.dto;

import java.text.DecimalFormat;

import com.cmc.dashboard.model.ProjectBillable;

public class BillableDTO {
	private int projectBillableId;
	private String billableMonth;
	private float billableValue;
	private String issueCode;
	private double manMonth;
	private boolean inactive;

	public BillableDTO() {
		super();
	}

	public BillableDTO(ProjectBillable projectBillable, double manMonth, boolean inactive) {
		this.projectBillableId = projectBillable.getProjectBillableId();
		this.billableMonth = projectBillable.getBillableMonth();
		this.billableValue = projectBillable.getBillableValue();
		this.issueCode = projectBillable.getIssueCode();
		this.manMonth = manMonth;
		this.inactive = inactive;
	}

	public int getProjectBillableId() {
		return projectBillableId;
	}

	public void setProjectBillableId(int projectBillableId) {
		this.projectBillableId = projectBillableId;
	}

	public String getBillableMonth() {
		return billableMonth;
	}

	public void setBillableMonth(String billableMonth) {
		this.billableMonth = billableMonth;
	}

	public float getBillableValue() {
		return billableValue;
	}

	public void setBillableValue(float billableValue) {
		this.billableValue = billableValue;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public double getManMonth() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(manMonth));
	}

	public void setManMonth(double manMonth) {
		this.manMonth = manMonth;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
}
