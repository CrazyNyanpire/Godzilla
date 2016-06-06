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
@Table(name = "godzilla_location")
public class Location extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5339320846591316259L;
	private String loctionCode;
	private WaferProcess waferProcess;
	private SubstrateProcess substrateProcess;
	private MaterialProcess materialProcess;
	private ManufactureProcess manufactureProcess;
	private SpartPartProcess spartPartProcess;
	private String description;

	public String getLoctionCode() {
		return loctionCode;
	}

	public void setLoctionCode(String loctionCode) {
		this.loctionCode = loctionCode;
	}
	
	@ManyToOne( cascade = { CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="wafer_process_ID", referencedColumnName="ID")
	public WaferProcess getWaferProcess() {
		return waferProcess;
	}

	public void setWaferProcess(WaferProcess waferProcess) {
		this.waferProcess = waferProcess;
	}


	@ManyToOne( cascade = { CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="substrate_process_ID", referencedColumnName="ID")
	public SubstrateProcess getSubstrateProcess() {
		return substrateProcess;
	}

	public void setSubstrateProcess(SubstrateProcess substrateProcess) {
		this.substrateProcess = substrateProcess;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="material_process_ID", referencedColumnName="ID")
	public MaterialProcess getMaterialProcess() {
		return materialProcess;
	}

	public void setMaterialProcess(MaterialProcess materialProcess) {
		this.materialProcess = materialProcess;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="manufacture_process_ID", referencedColumnName="ID")
	public ManufactureProcess getManufactureProcess() {
		return manufactureProcess;
	}

	public void setManufactureProcess(ManufactureProcess manufactureProcess) {
		this.manufactureProcess = manufactureProcess;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="spartpart_process_ID", referencedColumnName="ID")
	public SpartPartProcess getSpartPartProcess() {
		return spartPartProcess;
	}

	public void setSpartPartProcess(SpartPartProcess spartPartProcess) {
		this.spartPartProcess = spartPartProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((loctionCode == null) ? 0 : loctionCode.hashCode());
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
		Location other = (Location) obj;
		if (loctionCode == null) {
			if (other.loctionCode != null)
				return false;
		} else if (!loctionCode.equals(other.loctionCode))
			return false;
		if (waferProcess == null) {
			if (other.waferProcess != null)
				return false;
		} else if (!waferProcess.equals(other.waferProcess))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		
		return new String[] { String.valueOf(getId()), this.getLoctionCode()
				,this.getCreateDate().toString()};
	}

	
}