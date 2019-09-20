package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the user_plan_detail database table.
 * 
 */
@Entity
@Table(name = "user_plan_detail")
@NamedQuery(name = "UserPlanDetail.findAll", query = "SELECT u FROM UserPlanDetail u")
public class UserPlanDetail implements Serializable {

	private static final long serialVersionUID = 7528331247000169649L;
	@TableGenerator(name = "UserPlanDetail_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "user_plan_detail_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "UserPlanDetail_Gen")
	@Column(name = "user_plan_detail_id")
	private int userPlanDetailId;

	@Column(name = "plan_month")
	private String planMonth;

	@Column(name = "man_day")
	private float manDay;

	@Column(name = "delivery_unit", updatable = false)
	private String deliveryUnit;
	
	@Column(name = "res_delivery_unit", updatable = false)
	private String resDeliveryUnit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	// bi-directional many-to-one association to UserPlan
	@ManyToOne
	@JoinColumn(name = "user_plan_id", updatable = false)
	private UserPlan userPlan;

	@Transient
	private int totalWokingDaysOfMonth;

	public UserPlanDetail() {
	}

	public UserPlanDetail(int id, String planMonth, float manDay, String deliveryUnit, UserPlan userPlan) {
		super();
		this.userPlanDetailId = id;
		this.planMonth = planMonth;
		this.manDay = manDay;
		this.deliveryUnit = deliveryUnit;
		this.userPlan = userPlan;
	}

	public UserPlanDetail(String planMonth, float manDay) {
		super();
		this.planMonth = planMonth;
		this.manDay = manDay;
	}

	public UserPlanDetail(int id, String planMonth, float manDay) {
		super();
		this.userPlanDetailId = id;
		this.planMonth = planMonth;
		this.manDay = manDay;
	}

	public UserPlanDetail(String planMonth, float manDay, String deliveryUnit, UserPlan userPlan) {
		super();
		this.planMonth = planMonth;
		this.manDay = manDay;
		this.deliveryUnit = deliveryUnit;
		this.userPlan = userPlan;
	}

	public UserPlanDetail(String planMonth, Float manDay, String deliveryUnit, String resDeliveryUnit,
			UserPlan userPlan) {
		super();
		this.planMonth = planMonth;
		this.manDay = null == manDay || Float.isNaN(manDay) ? 0 : manDay;
		this.deliveryUnit = deliveryUnit;
		this.resDeliveryUnit = resDeliveryUnit;
		this.userPlan = userPlan;
		this.updatedOn = new Date();
	}

	public UserPlanDetail(String planMonth, double manDay) {
		super();
		this.planMonth = planMonth;
		this.manDay = (float) manDay;
	}

	public UserPlanDetail(int id, String planMonth, float manDay, String deliveryUnit, String resDeliveryUnit,
			Date updatedOn, UserPlan userPlan) {
		super();
		this.userPlanDetailId = id;
		this.planMonth = planMonth;
		this.manDay = manDay;
		this.deliveryUnit = deliveryUnit;
		this.resDeliveryUnit = resDeliveryUnit;
		this.updatedOn = updatedOn;
		this.userPlan = userPlan;
	}

	public String getResDeliveryUnit() {
		return resDeliveryUnit;
	}

	public void setResDeliveryUnit(String resDeliveryUnit) {
		this.resDeliveryUnit = resDeliveryUnit;
	}

	public int getId() {
		return this.userPlanDetailId;
	}

	public void setId(int id) {
		this.userPlanDetailId = id;
	}

	public float getManDay() {
		return this.manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public String getPlanMonth() {
		return this.planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@JsonIgnore
	public UserPlan getUserPlan() {
		return this.userPlan;
	}

	public void setUserPlan(UserPlan userPlan) {
		this.userPlan = userPlan;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public int getTotalWokingDaysOfMonth() {
		return totalWokingDaysOfMonth;
	}

	public void setTotalWokingDaysOfMonth(int totalWokingDaysOfMonth) {
		this.totalWokingDaysOfMonth = totalWokingDaysOfMonth;
	}
	
}