package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:00
 */
@Entity
@Table(name = "godzilla_vender")
public class Vender extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719789862816877658L;

	private String venderCode;
	private String venderName;


	public String getVenderCode() {
		return venderCode;
	}


	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}


	public String getVenderName() {
		return venderName;
	}


	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((venderCode == null) ? 0 : venderCode.hashCode());
		result = prime * result
				+ ((venderName == null) ? 0 : venderName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vender other = (Vender) obj;
		if (venderCode == null) {
			if (other.venderCode != null)
				return false;
		} else if (!venderCode.equals(other.venderCode))
			return false;
		if (venderName == null) {
			if (other.venderName != null)
				return false;
		} else if (!venderName.equals(other.venderName))
			return false;
		return true;
	}


	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getVenderCode().toString()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}