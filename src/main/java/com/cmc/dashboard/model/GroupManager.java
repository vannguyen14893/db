package com.cmc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "group_manager")
public class GroupManager implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1425380604753139789L;

	public GroupManager(Group group, User user) {
		super();
		this.group = group;
		this.user = user;
	}

	@TableGenerator(name = "Group_Manager_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "group_manager_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Group_Manager_Gen")
	@Id
	@Column(name = "group_manager_id")
	private int groupUserId;
	
	@OneToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public int getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
