package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MessageUtil;

/**
 * Created by Hoai Nam on 28/03/2018.
 */
@Entity
@Subselect(value = "")
public class UnallocationDTO implements Serializable,Comparable<UnallocationDTO> {
	
	private static final long serialVersionUID = 1L;
	@Id
	private int userId;
    private String duName;
    private String resourceName;
    private float allocation;
    private String status;
    private String comment;
    private String statusAllocation;

    public String getStatusAllocation() {
		return statusAllocation;
	}

	public void setStatusAllocation(String statusAllocation) {
		this.statusAllocation = statusAllocation;
	}

	public UnallocationDTO() {
    }

    public UnallocationDTO(int userId, String resourceName, String duName) {
		super();
		this.userId = userId;
		this.duName = duName;
		this.resourceName = resourceName;
	}

	public UnallocationDTO(int userId, String duName, String resourceName, float allocation) {
		super();
		this.userId = userId;
		this.duName = duName;
		this.resourceName = resourceName;
		this.allocation = allocation;
	}
	public UnallocationDTO(int userId, String duName, String resourceName, float allocation, String status,String statusAllocation) {
		super();
		this.userId = userId;
		this.duName = duName;
		this.resourceName = resourceName;
		this.allocation = allocation;
		this.status = status;
		this.statusAllocation=statusAllocation;
	}

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDuName() {
		return duName;
	}

	public void setDuName(String duName) {
		this.duName = duName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public float getAllocation() {
		return allocation;
	}

	public void setAllocation(float allocation) {
		this.allocation = allocation;
	}

	public String getStatus() {
    	status = (this.allocation<Constants.Numbers.LEVEL_STATUS_UNALLOCTION)?MessageUtil.NOT_FULL_ALLOCATION:MessageUtil.NOT_ALLOCATION;
        return status;
    }

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int compareTo(UnallocationDTO o) {
		 return this.allocation > o.allocation ? 1 : this.allocation < o.allocation ? -1 : 0;
	}
    
	public static Comparator<UnallocationDTO> UnAllocationComparatorAlloAsc = new Comparator<UnallocationDTO>() {
               public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
               //ascending order
               return Float.compare(fruit1.allocation,fruit2.allocation);
                //descending order
              //return fruitName2.compareTo(fruitName1);
                     }
                   };
    public static Comparator<UnallocationDTO> UnAllocationComparatorAlloDesc = new Comparator<UnallocationDTO>() {
                       public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
                       return Float.compare(fruit2.allocation,fruit1.allocation);
                       
                             }
                           };
    public static Comparator<UnallocationDTO> UnAllocationComparatorDuAsc = new Comparator<UnallocationDTO>() {
                            public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
                            	 String fruitName1 = fruit1.getDuName().toUpperCase();
                       	          String fruitName2 = fruit2.getDuName().toUpperCase();            
                            return fruitName1.compareTo(fruitName2);                        
                                  }
                                };
    public static Comparator<UnallocationDTO> UnAllocationComparatorDuDesc = new Comparator<UnallocationDTO>() {
                                    public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
                                    	 String fruitName1 = fruit1.getDuName().toUpperCase();
                               	          String fruitName2 = fruit2.getDuName().toUpperCase();            
                                    return fruitName2.compareTo(fruitName1);                        
                                          }
                                        };
    public static Comparator<UnallocationDTO> UnAllocationComparatorProjectNameAsc = new Comparator<UnallocationDTO>() {
                                            public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
                                            	 String fruitName1 = fruit1.getResourceName().toUpperCase();
                                       	          String fruitName2 = fruit2.getResourceName().toUpperCase();            
                                            return fruitName1.compareTo(fruitName2);                        
                                                  }
                                                };
     public static Comparator<UnallocationDTO> UnAllocationComparatorProjectNameDesc = new Comparator<UnallocationDTO>() {
                                                    public int compare(UnallocationDTO fruit1, UnallocationDTO fruit2) {
                                                    	 String fruitName1 = fruit1.getResourceName().toUpperCase();
                                               	          String fruitName2 = fruit2.getResourceName().toUpperCase();            
                                                    return fruitName2.compareTo(fruitName1);                        
                                                          }
                                                        };
}
