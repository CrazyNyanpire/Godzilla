package com.acetecsemi.godzilla.Godzilla.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:04
 */
@Entity
@Table(name = "godzilla_substrateProcess")
public class SubstrateProcess extends Process {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private SubstrateCompanyLot substrateCompanyLot;
	private Integer qtyIn;
	private Integer stripQtyIn;
	private Integer qtyOut;
	private Set<Location> substrateLocations = new HashSet<Location>();
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "substrate_company_lot_ID", referencedColumnName = "ID")
	public SubstrateCompanyLot getSubstrateCompanyLot() {
		return substrateCompanyLot;
	}

	public void setSubstrateCompanyLot(SubstrateCompanyLot substrateCompanyLot) {
		this.substrateCompanyLot = substrateCompanyLot;
	}
	
	public Integer getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(Integer qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Integer getStripQtyIn() {
		return stripQtyIn;
	}

	public void setStripQtyIn(Integer stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}

	public Integer getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Integer qtyOut) {
		this.qtyOut = qtyOut;
	}

	//@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	//@JoinTable(name = "godzilla_sub_process_location", joinColumns = @JoinColumn(name = "sub_process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "sub_location_ID", referencedColumnName = "ID"))
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY ,mappedBy ="substrateProcess")
	public Set<Location> getSubstrateLocations() {
		return substrateLocations;
	}

	public void setSubstrateLocations(Set<Location> substrateLocations) {
		this.substrateLocations = substrateLocations;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + qtyIn;
		result = prime * result + qtyOut;
		result = prime * result + stripQtyIn;
		result = prime
				* result
				+ ((substrateCompanyLot == null) ? 0 : substrateCompanyLot
						.hashCode());
		result = prime
				* result
				+ ((substrateLocations == null) ? 0 : substrateLocations
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
		SubstrateProcess other = (SubstrateProcess) obj;
		if (qtyIn != other.qtyIn)
			return false;
		if (qtyOut != other.qtyOut)
			return false;
		if (stripQtyIn != other.stripQtyIn)
			return false;
		if (substrateCompanyLot == null) {
			if (other.substrateCompanyLot != null)
				return false;
		} else if (!substrateCompanyLot.equals(other.substrateCompanyLot))
			return false;
		if (substrateLocations == null) {
			if (other.substrateLocations != null)
				return false;
		} else if (!substrateLocations.equals(other.substrateLocations))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}
}