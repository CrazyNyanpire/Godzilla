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
 * @created 29-10-2014 9:42:09
 */
@Entity
@Table(name = "godzilla_manu_debitprocess")
public class ManufactureDebitProcess extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4347163133641071155L;
	private Double percent;
	private Integer qty;
	private ManufactureProcess manufactureProcess;
	private MaterialProcess materialProcess;

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "manu_process_ID", referencedColumnName = "ID")
	public ManufactureProcess getManufactureProcess() {
		return manufactureProcess;
	}

	public void setManufactureProcess(ManufactureProcess manufactureProcess) {
		this.manufactureProcess = manufactureProcess;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "material_process_ID", referencedColumnName = "ID")
	public MaterialProcess getMaterialProcess() {
		return materialProcess;
	}

	public void setMaterialProcess(MaterialProcess materialProcess) {
		this.materialProcess = materialProcess;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}

}
