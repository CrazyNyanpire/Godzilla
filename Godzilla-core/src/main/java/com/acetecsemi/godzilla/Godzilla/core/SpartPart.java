package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 26-12-2014 11:42:12
 */
@Entity
@Table(name = "godzilla_spartpart")
public class SpartPart extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1560908708594367329L;

	private String partName;
	private String partId;
	private String partNumber;
	private String description;
	private Set<Product> products;
	private Integer qty;
	private String station;
	private Resource resLotType;
	private Vender vender;
	private String pon;
	private String customerLotNo;

	private Date shippingDate;
	private Date deliveryDate;
//	private String shippingTime;
	private DefineStationProcess defineStationProcess;
	private Integer delayTime;// h小时换算*d*h
	private String customerOrder;
	private String userName;
	private String remark;
	private String currStatus;

//	private Date manufactureDate;
//	private Date expiryDate;
	private int isEngineering;

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

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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

//	public int getShippingTime() {
//		return shippingTime;
//	}
//
//	public void setShippingTime(int shippingTime) {
//		this.shippingTime = shippingTime;
//	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "define_station_process_ID", referencedColumnName = "ID")
	public DefineStationProcess getDefineStationProcess() {
		return defineStationProcess;
	}

	public void setDefineStationProcess(
			DefineStationProcess defineStationProcess) {
		this.defineStationProcess = defineStationProcess;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
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

//	public Date getManufactureDate() {
//		return manufactureDate;
//	}
//
//	public void setManufactureDate(Date manufactureDate) {
//		this.manufactureDate = manufactureDate;
//	}
//
//	public Date getExpiryDate() {
//		return expiryDate;
//	}
//
//	public void setExpiryDate(Date expiryDate) {
//		this.expiryDate = expiryDate;
//	}

	public int getIsEngineering() {
		return isEngineering;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public void setIsEngineering(int isEngineering) {
		this.isEngineering = isEngineering;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "resource_lottype_ID", referencedColumnName = "ID")
	public Resource getResLotType() {
		return resLotType;
	}

	public void setResLotType(Resource resLotType) {
		this.resLotType = resLotType;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "godzilla_spartpart_product", joinColumns = @JoinColumn(name = "spartpart_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "product_ID", referencedColumnName = "ID"))
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
//		result = prime * result
//				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + isEngineering;
//		result = prime * result
//				+ ((manufactureDate == null) ? 0 : manufactureDate.hashCode());
		result = prime * result + ((pon == null) ? 0 : pon.hashCode());
		result = prime * result + qty;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((shippingDate == null) ? 0 : shippingDate.hashCode());
		result = prime * result
				+ ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
//		result = prime * result + shippingTime;
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
		SpartPart other = (SpartPart) obj;
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
//		if (expiryDate == null) {
//			if (other.expiryDate != null)
//				return false;
//		} else if (!expiryDate.equals(other.expiryDate))
//			return false;
		if (isEngineering != other.isEngineering)
			return false;
//		if (manufactureDate == null) {
//			if (other.manufactureDate != null)
//				return false;
//		} else if (!manufactureDate.equals(other.manufactureDate))
//			return false;
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
//		if (shippingTime != other.shippingTime)
//			return false;
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
		return new String[] { String.valueOf(getId()),
				this.getShippingDate().toString(),
				this.getCreateDate().toString(),
				this.getLastModifyDate().toString() };
	}
}