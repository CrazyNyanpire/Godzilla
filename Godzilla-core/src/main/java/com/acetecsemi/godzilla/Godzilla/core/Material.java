package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:11
 */
@Entity
@Table(name = "godzilla_material")
public class Material extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 962725516666469524L;
	private MaterialName materialName;
	private String partId;
	private String partNameCN;
	private String unit;
	private String item;
	private String materialSpecification;
	private Double measure;
	private Double minCapacity;
	private String minUnit;
	
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="material_name_id", referencedColumnName="ID")
	public MaterialName getMaterialName() {
		return materialName;
	}
	public void setMaterialName(MaterialName materialName) {
		this.materialName = materialName;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getPartNameCN() {
		return partNameCN;
	}
	public void setPartNameCN(String partNameCN) {
		this.partNameCN = partNameCN;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getMaterialSpecification() {
		return materialSpecification;
	}
	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public Double getMeasure() {
		return measure;
	}
	public void setMeasure(Double measure) {
		this.measure = measure;
	}
	public Double getMinCapacity() {
		return minCapacity;
	}
	public void setMinCapacity(Double minCapacity) {
		this.minCapacity = minCapacity;
	}
	public String getMinUnit() {
		return minUnit;
	}
	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((materialName == null) ? 0 : materialName.hashCode());
		result = prime * result + ((partId == null) ? 0 : partId.hashCode());
		result = prime * result
				+ ((partNameCN == null) ? 0 : partNameCN.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (materialName == null) {
			if (other.materialName != null)
				return false;
		} else if (!materialName.equals(other.materialName))
			return false;
		if (partId == null) {
			if (other.partId != null)
				return false;
		} else if (!partId.equals(other.partId))
			return false;
		if (partNameCN == null) {
			if (other.partNameCN != null)
				return false;
		} else if (!partNameCN.equals(other.partNameCN))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}
	
	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}


}