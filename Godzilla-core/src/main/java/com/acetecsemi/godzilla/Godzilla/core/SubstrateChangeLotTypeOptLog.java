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
 * @created 14-08-2014 9:42:13
 */
@Entity
@Table(name = "godzilla_sub_statusoptlog")
public class SubstrateChangeLotTypeOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private OptLog optLog;
	private String lotType;
	private SubstrateProcess substrateProcess;

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
	@JoinColumn(name = "substrate_process_ID", referencedColumnName = "ID")
	public SubstrateProcess getSubstrateProcess() {
		return substrateProcess;
	}

	public void setSubstrateProcess(SubstrateProcess substrateProcess) {
		this.substrateProcess = substrateProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lotType == null) ? 0 : lotType.hashCode());
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime * result
				+ ((substrateProcess == null) ? 0 : substrateProcess.hashCode());
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
		SubstrateChangeLotTypeOptLog other = (SubstrateChangeLotTypeOptLog) obj;
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
		if (substrateProcess == null) {
			if (other.substrateProcess != null)
				return false;
		} else if (!substrateProcess.equals(other.substrateProcess))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}