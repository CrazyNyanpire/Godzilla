package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:11
 */
@Entity
@Table(name = "godzilla_mhr")
public class MHR extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 962725516666469524L;
	private String no;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()),
				this.getCreateDate().toString(),
				this.getLastModifyDate().toString() };
	}
	
	
}