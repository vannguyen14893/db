package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: GiangTM
 * @Date: May 23, 2018
 */
@Entity
@Table(name = "redmine_db.journals")
public class QmsJournals implements Serializable {
	private static final long serialVersionUID = -6041775236567507270L;

	@Id
	@Column(name = "id")
	private int id;
}
