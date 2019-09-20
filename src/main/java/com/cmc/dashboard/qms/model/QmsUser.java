package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author: nvkhoa
 * @Date: Dec 20, 2017
 */
@Entity
@Table(name = "redmine_db.users")
public class QmsUser implements Serializable {

	private static final long serialVersionUID = -505781541336430186L;

	@Id
	private int id;

	@Column(name = "login")
	private String login;

	@Column(name = "hashed_password")
	private String hashedPassword;
	
	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "admin")
	private boolean admin;

	@Column(name = "status")
	private int status;

	@Column(name = "salt")
	private String salt;

	@OneToOne(mappedBy = "qmsUser", cascade = CascadeType.ALL)
	private QmsEmailAddress qmsEmailAddress;


	public QmsUser() {
		super();
	}

	public QmsUser(int id, String firstname, String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public QmsUser(int id, String login, String firstname, String lastname, boolean admin, int status) {
		super();
		this.id = id;
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.admin = admin;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}
	@JsonIgnore
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public QmsEmailAddress getQmsEmailAddress() {
		return qmsEmailAddress;
	}

	public void setQmsEmailAddress(QmsEmailAddress qmsEmailAddress) {
		this.qmsEmailAddress = qmsEmailAddress;
	}

}
