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
 * @created 11-09-2014 9:42:13
 */
@Entity
@Table(name = "godzilla_material_optlog")
public class MaterialOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private Integer qty;
	private String type;
	private String comments;
	private OptLog optLog;
	private MaterialProcess materialProcess;

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
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
	@JoinColumn(name = "material_process_ID", referencedColumnName = "ID")
	public MaterialProcess getMaterialProcess() {
		return materialProcess;
	}

	public void setMaterialProcess(MaterialProcess materialProcess) {
		this.materialProcess = materialProcess;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
		result = prime * result + qty;
		result = prime
				* result
				+ ((materialProcess == null) ? 0 : materialProcess.hashCode());
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
		MaterialOptLog other = (MaterialOptLog) obj;
		if (optLog == null) {
			if (other.optLog != null)
				return false;
		} else if (!optLog.equals(other.optLog))
			return false;
		if (qty != other.qty)
			return false;
		if (materialProcess == null) {
			if (other.materialProcess != null)
				return false;
		} else if (!materialProcess.equals(other.materialProcess))
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