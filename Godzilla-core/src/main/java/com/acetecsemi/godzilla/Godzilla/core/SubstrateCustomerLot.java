package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:12
 */
@Entity
@Table(name = "godzilla_substrateCustomerLot")
public class SubstrateCustomerLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1560908708594367329L;
	private Vender vender;
	private String customerLotNo;

	private SubstratePart substratePart;
	private String batchNo;
	private int qty;
	private int strip;
	private Date shippingDate;
	private Date deliveryDate;
	private int shippingTime;
	private DefineStationProcess defineStationProcess;
	private RunCardTemplate runCardTemplate;
	private int delayTime;// h小时换算*d*h
	private String customerOrder;
	private String userName;
	private String remark;
	private String currStatus;
	private String pon;
	private Date manufactureDate;
	private Date expiryDate;
	private int isEngineering;
	private Resource resLotType;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "vender_ID", referencedColumnName = "ID")
	public Vender getVender() {
		return vender;
	}

	public void setVender(Vender vender) {
		this.vender = vender;
	}

	public String getCustomerLotNo() {
		return customerLotNo;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}



	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "part_ID", referencedColumnName = "ID")
	public SubstratePart getSubstratePart() {
		return substratePart;
	}

	public void setSubstratePart(SubstratePart substratePart) {
		this.substratePart = substratePart;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getStrip() {
		return strip;
	}

	public void setStrip(int strip) {
		this.strip = strip;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	
	
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(int shippingTime) {
		this.shippingTime = shippingTime;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "define_station_process_ID", referencedColumnName = "ID")
	public DefineStationProcess getDefineStationProcess() {
		return defineStationProcess;
	}

	public void setDefineStationProcess(
			DefineStationProcess defineStationProcess) {
		this.defineStationProcess = defineStationProcess;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "run_card_template_ID", referencedColumnName = "ID")
	public RunCardTemplate getRunCardTemplate() {
		return runCardTemplate;
	}

	public void setRunCardTemplate(RunCardTemplate runCardTemplate) {
		this.runCardTemplate = runCardTemplate;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getPon() {
		return pon;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}


	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getIsEngineering() {
		return isEngineering;
	}

	public void setIsEngineering(int isEngineering) {
		this.isEngineering = isEngineering;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="resource_lottype_ID", referencedColumnName="ID")
	public Resource getResLotType() {
		return resLotType;
	}

	public void setResLotType(Resource resLotType) {
		this.resLotType = resLotType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result
				+ ((currStatus == null) ? 0 : currStatus.hashCode());
		result = prime * result
				+ ((customerLotNo == null) ? 0 : customerLotNo.hashCode());
		result = prime * result
				+ ((customerOrder == null) ? 0 : customerOrder.hashCode());
		result = prime
				* result
				+ ((defineStationProcess == null) ? 0 : defineStationProcess
						.hashCode());
		result = prime * result + delayTime;
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + isEngineering;
		result = prime * result
				+ ((manufactureDate == null) ? 0 : manufactureDate.hashCode());
		result = prime * result + ((pon == null) ? 0 : pon.hashCode());
		result = prime * result + qty;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((runCardTemplate == null) ? 0 : runCardTemplate.hashCode());
		result = prime * result
				+ ((shippingDate == null) ? 0 : shippingDate.hashCode());
		
		result = prime * result
				+ ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		
		result = prime * result + shippingTime;
		result = prime * result + strip;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((vender == null) ? 0 : vender.hashCode());
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
		SubstrateCustomerLot other = (SubstrateCustomerLot) obj;
		if (batchNo == null) {
			if (other.batchNo != null)
				return false;
		} else if (!batchNo.equals(other.batchNo))
			return false;
		if (currStatus == null) {
			if (other.currStatus != null)
				return false;
		} else if (!currStatus.equals(other.currStatus))
			return false;
		if (customerLotNo == null) {
			if (other.customerLotNo != null)
				return false;
		} else if (!customerLotNo.equals(other.customerLotNo))
			return false;
		if (customerOrder == null) {
			if (other.customerOrder != null)
				return false;
		} else if (!customerOrder.equals(other.customerOrder))
			return false;
		if (defineStationProcess == null) {
			if (other.defineStationProcess != null)
				return false;
		} else if (!defineStationProcess.equals(other.defineStationProcess))
			return false;
		if (delayTime != other.delayTime)
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (isEngineering != other.isEngineering)
			return false;
		if (manufactureDate == null) {
			if (other.manufactureDate != null)
				return false;
		} else if (!manufactureDate.equals(other.manufactureDate))
			return false;
		if (pon == null) {
			if (other.pon != null)
				return false;
		} else if (!pon.equals(other.pon))
			return false;
		if (qty != other.qty)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (runCardTemplate == null) {
			if (other.runCardTemplate != null)
				return false;
		} else if (!runCardTemplate.equals(other.runCardTemplate))
			return false;
		if (shippingDate == null) {
			if (other.shippingDate != null)
				return false;
		} else if (!shippingDate.equals(other.shippingDate))
			return false;
		
		
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		
		
		
		if (shippingTime != other.shippingTime)
			return false;
		if (strip != other.strip)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (vender == null) {
			if (other.vender != null)
				return false;
		} else if (!vender.equals(other.vender))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getShippingDate().toString()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}