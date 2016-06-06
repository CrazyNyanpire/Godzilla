package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 09-10-2014 9:42:13
 */
@Entity
@Table(name = "godzilla_manu_statusoptlog")
public class ManufactureChangeLotTypeOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private OptLog optLog;
	private String lotType;
	private ManufactureProcess manufactureProcess;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "opt_log_ID", referencedColumnName = "ID")
	public OptLog getOptLog() {
		return optLog;
	}

	public void setOptLog(OptLog optLog) {
		this.optLog = optLog;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "manufacture_process_ID", referencedColumnName = "ID")
	public ManufactureProcess getManufactureProcess() {
		return manufactureProcess;
	}

	public void setManufactureProcess(ManufactureProcess manufactureProcess) {
		this.manufactureProcess = manufactureProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lotType == null) ? 0 : lotType.hashCode());
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime * result
				+ ((manufactureProcess == null) ? 0 : manufactureProcess.hashCode());
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
		ManufactureChangeLotTypeOptLog other = (ManufactureChangeLotTypeOptLog) obj;
		if (lotType == null) {
			if (other.lotType != null)
				return false;
		} else if (!lotType.equals(other.lotType))
			return false;
		if (optLog == null) {
			if (other.optLog != null)
				return false;
		} else if (!optLog.equals(other.optLog))
			return false;
		if (manufactureProcess == null) {
			if (other.manufactureProcess != null)
				return false;
		} else if (!manufactureProcess.equals(other.manufactureProcess))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}