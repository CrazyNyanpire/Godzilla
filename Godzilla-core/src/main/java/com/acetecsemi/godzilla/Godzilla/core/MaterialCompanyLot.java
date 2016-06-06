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
 * @created 28-07-2014 9:42:14
 */
@Entity
@Table(name = "godzilla_materialCompanyLot")
public class MaterialCompanyLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 254909893126231907L;
	private MaterialLot materialLot;
	private String lotNo;
	
	private MaterialCompanyLot mergeMaterialCompanyLot;
	private MaterialCompanyLot parentMaterialCompanyLot;
	
	private MaterialProcess nowMaterialProcess;
	private Set<MaterialProcess> materialProcesses = new HashSet<MaterialProcess>();
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="material_lot_ID", referencedColumnName="ID")
	public MaterialLot getMaterialLot() {
		return materialLot;
	}

	public void setMaterialLot(MaterialLot materialLot) {
		this.materialLot = materialLot;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="material_company_lot_ID", referencedColumnName="ID")
	public MaterialCompanyLot getMergeMaterialCompanyLot() {
		return mergeMaterialCompanyLot;
	}

	public void setMergeMaterialCompanyLot(
			MaterialCompanyLot mergeMaterialCompanyLot) {
		this.mergeMaterialCompanyLot = mergeMaterialCompanyLot;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="parent_material_company_lot_ID", referencedColumnName="ID")
	public MaterialCompanyLot getParentMaterialCompanyLot() {
		return parentMaterialCompanyLot;
	}

	public void setParentMaterialCompanyLot(
			MaterialCompanyLot parentMaterialCompanyLot) {
		this.parentMaterialCompanyLot = parentMaterialCompanyLot;
	}

	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="now_material_process_ID", referencedColumnName="ID")
	public MaterialProcess getNowMaterialProcess() {
		return nowMaterialProcess;
	}

	public void setNowMaterialProcess(MaterialProcess nowMaterialProcess) {
		this.nowMaterialProcess = nowMaterialProcess;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE },fetch = FetchType.LAZY, mappedBy = "materialCompanyLot")
	public Set<MaterialProcess> getMaterialProcesses() {
		return materialProcesses;
	}

	public void setMaterialProcesses(Set<MaterialProcess> materialProcesses) {
		this.materialProcesses = materialProcesses;
	}
	
	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}
}