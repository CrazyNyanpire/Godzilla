package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 28-08-2014 9:42:13
 */
@Entity
@Table(name = "godzilla_statusoptlog")
public class StatusOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private Resource resHold;
	private Station futureStation;
	private OptLog optLog;
	private String holdCode;
	private WaferCustomerLot waferCustomerLot;
	private WaferProcess waferProcess;
	private SubstrateCustomerLot substrateCustomerLot;
	private SubstrateProcess substrateProcess;
	private MaterialLot materialLot;
	private MaterialProcess materialProcess;
	private ManufactureProcess manufactureProcess;
	private SpartPart spartPart;
	private SpartPartProcess spartPartProcess;
	private String engineerName;
	private String type;

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
	@JoinColumn(name = "wafer_customer_ID", referencedColumnName = "ID")
	public WaferCustomerLot getWaferCustomerLot() {
		return waferCustomerLot;
	}

	public void setWaferCustomerLot(WaferCustomerLot waferCustomerLot) {
		this.waferCustomerLot = waferCustomerLot;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "wafer_process_ID", referencedColumnName = "ID")
	public WaferProcess getWaferProcess() {
		return waferProcess;
	}

	public void setWaferProcess(WaferProcess waferProcess) {
		this.waferProcess = waferProcess;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "substrate_customer_ID", referencedColumnName = "ID")
	public SubstrateCustomerLot getSubstrateCustomerLot() {
		return substrateCustomerLot;
	}

	public void setSubstrateCustomerLot(SubstrateCustomerLot substrateCustomerLot) {
		this.substrateCustomerLot = substrateCustomerLot;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "substrate_process_ID", referencedColumnName = "ID")
	public SubstrateProcess getSubstrateProcess() {
		return substrateProcess;
	}

	public void setSubstrateProcess(SubstrateProcess substrateProcess) {
		this.substrateProcess = substrateProcess;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "material_lot_ID", referencedColumnName = "ID")
	public MaterialLot getMaterialLot() {
		return materialLot;
	}

	public void setMaterialLot(MaterialLot materialLot) {
		this.materialLot = materialLot;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "manufacture_process_ID", referencedColumnName = "ID")
	public ManufactureProcess getManufactureProcess() {
		return manufactureProcess;
	}

	public void setManufactureProcess(ManufactureProcess manufactureProcess) {
		this.manufactureProcess = manufactureProcess;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "spartpart_ID", referencedColumnName = "ID")
	public SpartPart getSpartPart() {
		return spartPart;
	}

	public void setSpartPart(SpartPart spartPart) {
		this.spartPart = spartPart;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "spartpart_process_ID", referencedColumnName = "ID")
	public SpartPartProcess getSpartPartProcess() {
		return spartPartProcess;
	}

	public void setSpartPartProcess(SpartPartProcess spartPartProcess) {
		this.spartPartProcess = spartPartProcess;
	}
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "material_process_ID", referencedColumnName = "ID")
	public MaterialProcess getMaterialProcess() {
		return materialProcess;
	}

	public void setMaterialProcess(MaterialProcess materialProcess) {
		this.materialProcess = materialProcess;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "resource_ID", referencedColumnName = "ID")
	public Resource getResHold() {
		return resHold;
	}

	public void setResHold(Resource resHold) {
		this.resHold = resHold;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((futureStation == null) ? 0 : futureStation.hashCode());
		result = prime * result
				+ ((holdCode == null) ? 0 : holdCode.hashCode());
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
		StatusOptLog other = (StatusOptLog) obj;
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
		return new String[] { String.valueOf(getId()), this.getHoldCode(),
				this.getCreateDate().toString() };
	}

}