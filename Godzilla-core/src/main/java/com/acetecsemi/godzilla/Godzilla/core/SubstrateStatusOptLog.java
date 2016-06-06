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
public class SubstrateStatusOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private Station futureStation;
	private OptLog optLog;
	private String holdCode;
	private SubstrateProcess substrateProcess;
	private String engineerName;

	public Station getFutureStation() {
		return futureStation;
	}

	public void setFutureStation(Station futureStation) {
		this.futureStation = futureStation;
	}

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "opt_log_ID", referencedColumnName = "ID")
	public OptLog getOptLog() {
		return optLog;
	}

	public void setOptLog(OptLog optLog) {
		this.optLog = optLog;
	}

	public String getHoldCode() {
		return holdCode;
	}

	public void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "substrate_process_ID", referencedColumnName = "ID")
	public SubstrateProcess getSubstrateProcess() {
		return substrateProcess;
	}

	public void setSubstrateProcess(SubstrateProcess substrateProcess) {
		this.substrateProcess = substrateProcess;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((futureStation == null) ? 0 : futureStation.hashCode());
		result = prime * result
				+ ((holdCode == null) ? 0 : holdCode.hashCode());
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime
				* result
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
		SubstrateStatusOptLog other = (SubstrateStatusOptLog) obj;
		if (futureStation == null) {
			if (other.futureStation != null)
				return false;
		} else if (!futureStation.equals(other.futureStation))
			return false;
		if (holdCode == null) {
			if (other.holdCode != null)
				return false;
		} else if (!holdCode.equals(other.holdCode))
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
		return new String[] { String.valueOf(getId()), this.getHoldCode(),
				this.getCreateDate().toString() };
	}

}