package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class SubstrateCustomerLotDTO extends TableListAbstractDTO implements Serializable {

	private Long id;

	private String shippingTime;
	
	
	
	private String shippingDelayTime;

	private Long delayTime;

	private String customerLotNo;

	private String remark;

	private String customerOrder;

	private Integer qty;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDateEnd;

	private Integer strip;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date guaranteePeriod;

	private String currStatus;

	private String batchNo;

	private String pon;

	private String userName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private Long productId;

	private Long venderId;

	private String venderName;

	private String partNumber;

	private UserDTO optUser;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufactureDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;
	
	private int isEngineering;
	
	private String password;
	
	private String lotType;
	
	private Long lotTypeId;

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingTime() {
		return this.shippingTime;
	}

	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	public Long getDelayTime() {
		return this.delayTime;
	}
	

	public String getShippingDelayTime()
	{
		return shippingDelayTime;
	}

	public void setShippingDelayTime(String shippingDelayTime)
	{
		this.shippingDelayTime = shippingDelayTime;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	public String getCustomerLotNo() {
		return this.customerLotNo;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getCustomerOrder() {
		return this.customerOrder;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getProductionDate() {
		return this.productionDate;
	}

	public void setProductionDateEnd(Date productionDateEnd) {
		this.productionDateEnd = productionDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getProductionDateEnd() {
		return this.productionDateEnd;
	}

	public void setStrip(Integer strip) {
		this.strip = strip;
	}

	public Integer getStrip() {
		return this.strip;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getShippingDate() {
		return this.shippingDate;
	}

	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setShippingDateEnd(Date shippingDateEnd) {
		this.shippingDateEnd = shippingDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getShippingDateEnd() {
		return this.shippingDateEnd;
	}

	public void setGuaranteePeriod(Date guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getGuaranteePeriod() {
		return this.guaranteePeriod;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getCurrStatus() {
		return this.currStatus;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public String getPon() {
		return this.pon;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public UserDTO getOptUser() {
		return optUser;
	}

	public void setOptUser(UserDTO optUser) {
		this.optUser = optUser;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		SubstrateCustomerLotDTO other = (SubstrateCustomerLotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}