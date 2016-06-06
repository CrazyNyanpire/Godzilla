package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 14-08-2014 9:42:13
 */
@Entity
public class ManufactureStatusOptLog extends StatusOptLog {

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