package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 10:02:21
 */
@Entity
@Table(name = "godzilla_wafercustomerlot")
public class WaferCustomerLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2970586113708918579L;
	private Customer customer;
	private String customerLotNo;
	private Product product;
	private Part part;
	private int qty;
	private int firstPQty;
	private int secondPQty;
	private int thirdPQty;
	private int wafer;
	private Date shippingDate;
	private int shippingTime;
	private Date deliveryDate;
	private DefineStationProcess defineStationProcess;
	private RunCardTemplate runCardTemplate;
	private Long delayTime;// h小时换算*d*h
	private String customerOrder;
	private String userName;
	private String remark;
	private String currStatus;
	private Date manufactureDate;
	private Date expiryDate;
	private int isEngineering;
	private Resource resLotType;
	/**1：已CP；2：未cp*/
	private Integer waferStatusId;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_ID", referencedColumnName = "ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerLotNo() {
		return customerLotNo;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_ID", referencedColumnName = "ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "part_ID", referencedColumnName = "ID")
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getFirstPQty() {
		return firstPQty;
	}

	public void setFirstPQty(int firstPQty) {
		this.firstPQty = firstPQty;
	}

	public int getSecondPQty() {
		return secondPQty;
	}

	public void setSecondPQty(int secondPQty) {
		this.secondPQty = secondPQty;
	}

	public int getThirdPQty() {
		return thirdPQty;
	}

	public void setThirdPQty(int thirdPQty) {
		this.thirdPQty = thirdPQty;
	}

	public int getWafer() {
		return wafer;
	}

	public void setWafer(int wafer) {
		this.wafer = wafer;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	

	public int getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(int shippingTime) {
		this.shippingTime = shippingTime;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "define_station_process_ID", referencedColumnName = "ID")
	public DefineStationProcess getDefineStationProcess() {
		return defineStationProcess;
	}

	public void setDefineStationProcess(
			DefineStationProcess defineStationProcess) {
		this.defineStationProcess = defineStationProcess;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "run_card_template_ID", referencedColumnName = "ID")
	public RunCardTemplate getRunCardTemplate() {
		return runCardTemplate;
	}

	public void setRunCardTemplate(RunCardTemplate runCardTemplate) {
		this.runCardTemplate = runCardTemplate;
	}

	public Long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Long delayTime) {
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
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="resource_lottype_ID", referencedColumnName="ID")
	public Resource getResLotType() {
		return resLotType;
	}

	public void setResLotType(Resource resLotType) {
		this.resLotType = resLotType;
	}

	public Integer getWaferStatusId() {
		return waferStatusId;
	}

	public void setWaferStatusId(Integer waferStatusId) {
		this.waferStatusId = waferStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((currStatus == null) ? 0 : currStatus.hashCode());
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result
				+ ((customerLotNo == null) ? 0 : customerLotNo.hashCode());
		result = prime * result
				+ ((customerOrder == null) ? 0 : customerOrder.hashCode());
		result = prime
				* result
				+ ((defineStationProcess == null) ? 0 : defineStationProcess
						.hashCode());
		result = prime * result + delayTime.intValue();
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + firstPQty;
		result = prime * result + isEngineering;
		result = prime * result
				+ ((manufactureDate == null) ? 0 : manufactureDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + qty;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((runCardTemplate == null) ? 0 : runCardTemplate.hashCode());
		result = prime * result + secondPQty;
		result = prime * result
				+ ((shippingDate == null) ? 0 : shippingDate.hashCode());
		
		result = prime * result
				+ ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		result = prime * result + shippingTime;
		result = prime * result + thirdPQty;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + wafer;
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
		WaferCustomerLot other = (WaferCustomerLot) obj;
		if (currStatus == null) {
			if (other.currStatus != null)
				return false;
		} else if (!currStatus.equals(other.currStatus))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
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
		if (firstPQty != other.firstPQty)
			return false;
		if (isEngineering != other.isEngineering)
			return false;
		if (manufactureDate == null) {
			if (other.manufactureDate != null)
				return false;
		} else if (!manufactureDate.equals(other.manufactureDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
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
		if (secondPQty != other.secondPQty)
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
		if (thirdPQty != other.thirdPQty)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (wafer != other.wafer)
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getShippingDate().toString()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}