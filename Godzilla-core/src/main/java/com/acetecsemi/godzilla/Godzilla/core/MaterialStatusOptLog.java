package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;


/**
 * @author harlow
 * @version 1.0
 * @created 28-08-2014 9:42:13
 * @modify 26-12-2014 9:42:13
 */
@Entity
public class MaterialStatusOptLog extends StatusOptLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getHoldCode(),
				this.getCreateDate().toString() };
	}

}