package com.cmc.dashboard.dto;

import java.util.Comparator;

public class ReAllocationDTO   {
    private String du;
    private String fullName;
    private String role;
    private String duPic;
    private String projectName;
    private String code;
    private String projectType;
    private int status;
    private double allocation;
	public String getDu() {
		return du;
	}
	public void setDu(String du) {
		this.du = du;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDuPic() {
		return duPic;
	}
	public void setDuPic(String duPic) {
		this.duPic = duPic;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return this.projectType;
	}
	public void setType(String type) {
		this.projectType = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public double getAllocation() {
		return allocation;
	}
	public void setAllocation(double allocation) {
		this.allocation = allocation;
	}
	public static Comparator<ReAllocationDTO> ComparatorAlloAsc = new Comparator<ReAllocationDTO>() {
        public int compare(ReAllocationDTO fruit1, ReAllocationDTO fruit2) {
        //ascending order
        return Double.compare(fruit1.allocation, fruit2.allocation);
         //descending order
       //return fruitName2.compareTo(fruitName1);
              }
            };
public static Comparator<ReAllocationDTO> ComparatorAlloDesc = new Comparator<ReAllocationDTO>() {
                public int compare(ReAllocationDTO fruit1, ReAllocationDTO fruit2) {
                return Double.compare(fruit2.allocation,fruit1.allocation);
                
                      }
                    };
	public ReAllocationDTO(String du, String fullName, String role, String duPic, String projectName, String code,
			String type, int status,double allocation) {
		super();
		this.du = du;
		this.fullName = fullName;
		this.role = role;
		this.duPic = duPic;
		this.projectName = projectName;
		this.code = code;
		this.projectType = type;
		this.allocation = allocation;
	}
}
