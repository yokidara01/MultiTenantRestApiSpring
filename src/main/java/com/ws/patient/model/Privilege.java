package com.ws.patient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "privilege", schema = "sc_mmt")
public class Privilege {

	@Id
	@GeneratedValue
	private long privilegeId;

	private String name;

	private String description;

	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(final long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
