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
@Table(name = "godzilla_substrate_optlog")
public class SubstrateOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private int stripQty;
	private int qty;
	private String type;
	private OptLog optLog;
	private SubstrateProcess substrateProcess;



	public int getStripQty() {
		return stripQty;
	}

	public void setStripQty(int stripQty) {
		this.stripQty = stripQty;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime * result + qty;
		result = prime * result + stripQty;
		result = prime
				* result
				+ ((substrateProcess == null) ? 0 : substrateProcess.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SubstrateOptLog other = (SubstrateOptLog) obj;
		if (optLog == null) {
			if (other.optLog != null)
				return false;
		} else if (!optLog.equals(other.optLog))
			return false;
		if (qty != other.qty)
			return false;
		if (stripQty != other.stripQty)
			return false;
		if (substrateProcess == null) {
			if (other.substrateProcess != null)
				return false;
		} else if (!substrateProcess.equals(other.substrateProcess))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), String.valueOf(this.getQty())
				,this.getCreateDate().toString()};
	}

}