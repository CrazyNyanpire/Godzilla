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
 * @modify 18-11-2014 9:42:04
 */
@Entity
@Table(name = "godzilla_materialProcess")
public class MaterialProcess extends Process {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private MaterialCompanyLot materialCompanyLot;
	private Double qtyIn;
	private Double qtyOut;
	private Double balance;//扣帐后余量
	private Double inCapacity;
	private int materialType;
	private Set<Location> materialLocations = new HashSet<Location>();

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "material_company_lot_ID", referencedColumnName = "ID")
	public MaterialCompanyLot getMaterialCompanyLot() {
		return materialCompanyLot;
	}

	public void setMaterialCompanyLot(MaterialCompanyLot materialCompanyLot) {
		this.materialCompanyLot = materialCompanyLot;
	}
	
	public Double getQtyIn() {
		return qtyIn;
	}
	
	public void setQtyIn(Double qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Double qtyOut) {
		this.qtyOut = qtyOut;
	}

	public int getMaterialType() {
		return materialType;
	}

	public void setMaterialType(int materialType) {
		this.materialType = materialType;
	}



	//@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	//@JoinTable(name = "godzilla_mat_process_location", joinColumns = @JoinColumn(name = "sub_process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "sub_location_ID", referencedColumnName = "ID"))
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY,mappedBy ="materialProcess")
	public Set<Location> getMaterialLocations() {
		return materialLocations;
	}

	public void setMaterialLocations(Set<Location> materialLocations) {
		this.materialLocations = materialLocations;
	}
	

	public Double getInCapacity() {
		return inCapacity;
	}

	public void setInCapacity(Double inCapacity) {
		this.inCapacity = inCapacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((materialCompanyLot == null) ? 0 : materialCompanyLot
						.hashCode());
		result = prime
				* result
				+ ((materialLocations == null) ? 0 : materialLocations
						.hashCode());
		result = prime * result + materialType;
		long temp;
		temp = Double.doubleToLongBits(qtyIn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(qtyOut);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		MaterialProcess other = (MaterialProcess) obj;
		if (materialCompanyLot == null) {
			if (other.materialCompanyLot != null)
				return false;
		} else if (!materialCompanyLot.equals(other.materialCompanyLot))
			return false;
		if (materialLocations == null) {
			if (other.materialLocations != null)
				return false;
		} else if (!materialLocations.equals(other.materialLocations))
			return false;
		if (materialType != other.materialType)
			return false;
		if (Double.doubleToLongBits(qtyIn) != Double
				.doubleToLongBits(other.qtyIn))
			return false;
		if (Double.doubleToLongBits(qtyOut) != Double
				.doubleToLongBits(other.qtyOut))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}
}