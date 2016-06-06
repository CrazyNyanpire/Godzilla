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
@Table(name = "godzilla_mat_changelocoptlog")
public class MaterialChangeLocationOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private OptLog optLog;
	private String locationIds;
	private MaterialProcess materialProcess;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "opt_log_ID", referencedColumnName = "ID")
	public OptLog getOptLog() {
		return optLog;
	}

	public void setOptLog(OptLog optLog) {
		this.optLog = optLog;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "material_pro_ID", referencedColumnName = "ID")
	public MaterialProcess getMaterialProcess() {
		return materialProcess;
	}

	public void setMaterialProcess(MaterialProcess materialProcess) {
		this.materialProcess = materialProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((locationIds == null) ? 0 : locationIds.hashCode());
		result = prime * result
				+ ((materialProcess == null) ? 0 : materialProcess.hashCode());
		result = prime * result + ((optLog == null) ? 0 : optLog.hashCode());
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
		MaterialChangeLocationOptLog other = (MaterialChangeLocationOptLog) obj;
		if (locationIds == null) {
			if (other.locationIds != null)
				return false;
		} else if (!locationIds.equals(other.locationIds))
			return false;
		if (materialProcess == null) {
			if (other.materialProcess != null)
				return false;
		} else if (!materialProcess.equals(other.materialProcess))
			return false;
		if (optLog == null) {
			if (other.optLog != null)
				return false;
		} else if (!optLog.equals(other.optLog))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}
}