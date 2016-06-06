package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class SpartPartDTO implements Serializable {

	private Long id;

	private Integer isEngineering;

	private String shippingTime;

	private Integer delayTime;

	private String remark;

	private String customerLotNo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDateEnd;

	private String customerOrder;

	private String partName;

	private Integer qty;

	private String partId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDateEnd;

	private String currStatus;

	private String pon;

	private String description;

	private String userName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufactureDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufactureDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private String partNumber;
	
	private String product;
	
	private Long productId;
	
	private String station;
	
	private Long stationId;
	
	private String vender;
	
	private Long venderId;

	private String lotType;
	
	private Long lotTypeId;
	
	public void setIsEngineering(Integer isEngineering) {
		this.isEngineering = isEngineering;
	}

	public Integer getIsEngineering() {
		return this.isEngineering;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingTime() {
		return this.shippingTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	public String getCustomerLotNo() {
		return this.customerLotNo;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getExpiryDateEnd() {
		return this.expiryDateEnd;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getCustomerOrder() {
		return this.customerOrder;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartId() {
		return this.partId;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDateEnd(Date shippingDateEnd) {
		this.shippingDateEnd = shippingDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getShippingDateEnd() {
		return this.shippingDateEnd;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDateEnd(Date deliveryDateEnd) {
		this.deliveryDateEnd = deliveryDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDateEnd() {
		return this.deliveryDateEnd;
	}
	
	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getCurrStatus() {
		return this.currStatus;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public String getPon() {
		return this.pon;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastModifyDate() {
		return this.lastModifyDate;
	}

	public void setLastModifyDateEnd(Date lastModifyDateEnd) {
		this.lastModifyDateEnd = lastModifyDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastModifyDateEnd() {
		return this.lastModifyDateEnd;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getManufactureDate() {
		return this.manufactureDate;
	}

	public void setManufactureDateEnd(Date manufactureDateEnd) {
		this.manufactureDateEnd = manufactureDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getManufactureDateEnd() {
		return this.manufactureDateEnd;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDateEnd() {
		return this.createDateEnd;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartNumber() {
		return this.partNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpartPartDTO other = (SpartPartDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public Long getLotTypeId() {
		return lotTypeId;
	}

	public void setLotTypeId(Long lotTypeId) {
		this.lotTypeId = lotTypeId;
	}
}