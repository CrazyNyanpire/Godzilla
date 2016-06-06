package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:06
 */
@Entity
@Table(name = "godzilla_substrate_location")
public class SubstrateLocation extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5339320846591316259L;
	private String loctionCode;
	private SubstrateCompanyLot substrateCompanyLot;

	public String getLoctionCode() {
		return loctionCode;
	}

	public void setLoctionCode(String loctionCode) {
		this.loctionCode = loctionCode;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="substrate_custlot_ID", referencedColumnName="ID")
	public SubstrateCompanyLot getSubstrateCompanyLot() {
		return substrateCompanyLot;
	}

	public void setSubstrateCompanyLot(SubstrateCompanyLot substrateCompanyLot) {
		this.substrateCompanyLot = substrateCompanyLot;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((loctionCode == null) ? 0 : loctionCode.hashCode());
		result = prime
				* result
				+ ((substrateCompanyLot == null) ? 0 : substrateCompanyLot
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
		SubstrateLocation other = (SubstrateLocation) obj;
		if (loctionCode == null) {
			if (other.loctionCode != null)
				return false;
		} else if (!loctionCode.equals(other.loctionCode))
			return false;
		if (substrateCompanyLot == null) {
			if (other.substrateCompanyLot != null)
				return false;
		} else if (!substrateCompanyLot.equals(other.substrateCompanyLot))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getLoctionCode()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

	
}