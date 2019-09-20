package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@DynamicInsert
public class User implements Serializable {

	private static final long serialVersionUID = -956404796599843451L;
	@Id
	@TableGenerator(name = "Users_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "user_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Users_Gen")
	@Column(name = "user_id")
	private int userId;
	
    @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	private String email;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "status")
	private byte status;
	
    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "img")
	private String img;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "is_salary")
	private byte isSalary;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="users_Id")
	, inverseJoinColumns = @JoinColumn(name="roles_Id"))
	private Set<Role> role;

	
	@ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name="user_skill", joinColumns=@JoinColumn(name="user_Id")
	, inverseJoinColumns = @JoinColumn(name="skill_Id"))
	private Set<Skill> skill;

	// bi-directional many-to-one association to Group
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true, fetch = FetchType.LAZY,cascade= {CascadeType.ALL})
	@JoinColumn(name = "group_id")
	private Group group;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByClosedBy")
	@JsonIgnore
	private Collection<Issue> issuesForClosedBy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByApprovedBy")
	@JsonIgnore
	private Collection<Issue> issuesForApprovedBy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByCreatedBy")
	@JsonIgnore
	private Collection<Issue> issuesForCreatedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "registeredBy")
	@JsonIgnore
	private Collection<Risk> riskCollection;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreateBy")
	@JsonIgnore
	private Collection<Solution> solutionCreateBy;
	@Column(name = "token")
	private String token;
	
	@Column(name = "level", columnDefinition = "int default = 0")
	private int level;
	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Constructure
	 */
	public User(int userId) {
		this.userId = userId;
	}

	public User() {
		role = new HashSet<>();
	}

	
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Collection<Risk> getRiskCollection() {
		return riskCollection;
	}

	public void setRiskCollection(Collection<Risk> riskCollection) {
		this.riskCollection = riskCollection;
	}

	public Collection<Issue> getIssuesForClosedBy() {
		return issuesForClosedBy;
	}

	public void setIssuesForClosedBy(Collection<Issue> issuesForClosedBy) {
		this.issuesForClosedBy = issuesForClosedBy;
	}

	public Collection<Issue> getIssuesForApprovedBy() {
		return issuesForApprovedBy;
	}

	public void setIssuesForApprovedBy(Collection<Issue> issuesForApprovedBy) {
		this.issuesForApprovedBy = issuesForApprovedBy;
	}

	public Collection<Issue> getIssuesForCreatedBy() {
		return issuesForCreatedBy;
	}

	public void setIssuesForCreatedBy(Collection<Issue> issuesForCreatedBy) {
		this.issuesForCreatedBy = issuesForCreatedBy;
	}

	public Collection<Solution> getSolutionCreateBy() {
		return solutionCreateBy;
	}

	public void setSolutionCreateBy(Collection<Solution> solutionCreateBy) {
		this.solutionCreateBy = solutionCreateBy;
	}
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void addRole(Role role) {
		this.role.add(role);
		role.addUsers(this); 
}
	public void removeRole(Role role) {
        // TODO Auto-generated method stub
        this.role.remove(role);
        role.getUser().remove(this);
}
	
	public Set<Skill> getSkill() {
		return skill;
	}

	public void setSkill(Set<Skill> skill) {
		this.skill = skill;
	}
	public void addSkill(Skill skill) {
		this.skill.add(skill);
		skill.addUsers(this); 
}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public byte getIsSalary() {
		return isSalary;
	}

	public void setIsSalary(byte isSalary) {
		this.isSalary = isSalary;
	}
	
}