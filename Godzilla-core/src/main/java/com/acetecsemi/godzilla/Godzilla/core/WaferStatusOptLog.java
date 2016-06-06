package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "godzilla_wafer_statusoptlog")
public class WaferStatusOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private Station futureStation;
	private OptLog optLog;
	private String holdCode;
	private WaferProcess waferProcess;
	private String engineerName;
	private MHR mhr;

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "future_station_ID", referencedColumnName = "ID")
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
	@JoinColumn(name = "wafer_process_ID", referencedColumnName = "ID")
	public WaferProcess getWaferProcess() {
		return waferProcess;
	}

	public void setWaferProcess(WaferProcess waferProcess) {
		this.waferProcess = waferProcess;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "mhr_ID", referencedColumnName = "ID")
	public MHR getMhr() {
		return mhr;
	}

	public void setMhr(MHR mhr) {
		this.mhr = mhr;
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
		result = prime * result
				+ ((waferProcess == null) ? 0 : waferProcess.hashCode());
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
		WaferStatusOptLog other = (WaferStatusOptLog) obj;
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
		if (waferProcess == null) {
			if (other.waferProcess != null)
				return false;
		} else if (!waferProcess.equals(other.waferProcess))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}