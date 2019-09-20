/**
 * 
 */
package com.cmc.dashboard.dto;

/**
 * @author GiangTM
 *
 */
public class BugDTO {
	private String bugSeverity;
	private int bugTotal;

	/**
	 * Constructure
	 */
	public BugDTO() {
		super();
	}

	/**
	 * Constructure
	 */
	public BugDTO(String bugSeverity, int bugTotal) {
		super();
		this.bugSeverity = bugSeverity;
		this.bugTotal = bugTotal;
	}

	public String getBugSeverity() {
		return bugSeverity;
	}

	public void setBugSeverity(String bugSeverity) {
		this.bugSeverity = bugSeverity;
	}

	public int getBugTotal() {
		return bugTotal;
	}

	public void setBugTotal(int bugTotal) {
		this.bugTotal = bugTotal;
	}

}
