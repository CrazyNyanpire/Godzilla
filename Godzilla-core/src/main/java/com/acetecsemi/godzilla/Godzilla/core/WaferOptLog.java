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
@Table(name = "godzilla_wafer_optlog")
public class WaferOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private int waferQty;
	private int dieQty;
	private String type;
	private OptLog optLog;
	private WaferProcess waferProcess;

	public int getWaferQty() {
		return waferQty;
	}

	public void setWaferQty(int waferQty) {
		this.waferQty = waferQty;
	}

	public int getDieQty() {
		return dieQty;
	}

	public void setDieQty(int dieQty) {
		this.dieQty = dieQty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "opt_log_ID", referencedColumnName = "ID")
	public OptLog getOptLog() {
		return optLog;
	}

	public void setOptLog(OptLog optLog) {
		this.optLog = optLog;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "wafer_process_ID", referencedColumnName = "ID")
	public WaferProcess getWaferProcess() {
		return waferProcess;
	}

	public void setWaferProcess(WaferProcess waferProcess) {
		this.waferProcess = waferProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dieQty;
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((waferProcess == null) ? 0 : waferProcess.hashCode());
		result = prime * result + waferQty;
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
		WaferOptLog other = (WaferOptLog) obj;
		if (dieQty != other.dieQty)
			return false;
		if (optLog == null) {
			if (other.optLog != null)
				return false;
		} else if (!optLog.equals(other.optLog))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (waferProcess == null) {
			if (other.waferProcess != null)
				return false;
		} else if (!waferProcess.equals(other.waferProcess))
			return false;
		if (waferQty != other.waferQty)
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}