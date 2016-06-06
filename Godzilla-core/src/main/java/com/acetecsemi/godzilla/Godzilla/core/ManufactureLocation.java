package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:06
 */
@Entity
@Table(name = "godzilla_manufacture_location")
public class ManufactureLocation extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5339320846591316259L;
	private String loctionCode;
	private ManufactureLot manufactureLot;

	public String getLoctionCode() {
		return loctionCode;
	}

	public void setLoctionCode(String loctionCode) {
		this.loctionCode = loctionCode;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="maufacturelot_ID", referencedColumnName="ID")
	public ManufactureLot getManufactureLot() {
		return manufactureLot;
	}

	public void setManufactureLot(ManufactureLot manufactureLot) {
		this.manufactureLot = manufactureLot;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((loctionCode == null) ? 0 : loctionCode.hashCode());
		result = prime
				* result
				+ ((manufactureLot == null) ? 0 : manufactureLot
						.hashCode());
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
		ManufactureLocation other = (ManufactureLocation) obj;
		if (loctionCode == null) {
			if (other.loctionCode != null)
				return false;
		} else if (!loctionCode.equals(other.loctionCode))
			return false;
		if (manufactureLot == null) {
			if (other.manufactureLot != null)
				return false;
		} else if (!manufactureLot.equals(other.manufactureLot))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getLoctionCode()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

	
}