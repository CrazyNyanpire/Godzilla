package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class WaferCustomerLotDTO extends TableListAbstractDTO implements Serializable {

	/**
	 * { "customerId": "1", "customerLotNo": "customerLotNo", "productId": "1",
	 * "qty": "1", "firstPQty": "1", "secondPQty": "1", "thirdPQty": "1",
	 * "wafer": "1", "customerOrder": "231", "shippingDate":
	 * "2014-07-31T12:23:00.000+0800", "remark": "123" }
	 */
	private Long id;

	private Long delayTime;
	
	private String shippingDelayTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;
	private String shippingTime;

	private Integer firstPQty;

	private Integer secondPQty;

	private String customerLotNo;

	private String remark;

	private String customerOrder;

	private Integer wafer;

	private Integer qty;

	private Integer thirdPQty;

	// use for query
	private String customerCode;

	private String partNumber;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shippingDateEnd;

	private String userName;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private Long productId;//productId为part id

	private Long customerId;
	
	private UserDTO optUser;
	
	private String currStatus;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date guaranteePeriod;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufactureDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;
	
	private int isEngineering;
	
	private String password;
	
	private String lotType;
	
	private Long lotTypeId;
	
	private String productName; 
	
	private Long partId;
	
	/**1：已CP；2：未cp*/
	private Integer waferStatusId;
	
	private String waferStatusName;
	
	
	
	public String getWaferStatusName() {
		return waferStatusName;
	}

	public void setWaferStatusName(String waferStatusName) {
		this.waferStatusName = waferStatusName;
	}

	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	public Long getDelayTime() {
		return this.delayTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingTime() {
		return this.shippingTime;
	}


	public void setFirstPQty(Integer firstPQty) {
		this.firstPQty = firstPQty;
	}

	public Integer getFirstPQty() {
		return this.firstPQty;
	}

	public void setSecondPQty(Integer secondPQty) {
		this.secondPQty = secondPQty;
	}

	public Integer getSecondPQty() {
		return this.secondPQty;
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

	public void setWafer(Integer wafer) {
		this.wafer = wafer;
	}

	public Integer getWafer() {
		return this.wafer;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setThirdPQty(Integer thirdPQty) {
		this.thirdPQty = thirdPQty;
	}

	public Integer getThirdPQty() {
		return this.thirdPQty;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public UserDTO getOptUser() {
		return optUser;
	}

	public void setOptUser(UserDTO optUser) {
		this.optUser = optUser;
	}

	//Hold/Waiting/Received
	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(Date guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	
	public String getShippingDelayTime()
	{
		return shippingDelayTime;
	}

	public void setShippingDelayTime(String shippingDelayTime)
	{
		this.shippingDelayTime = shippingDelayTime;
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
		WaferCustomerLotDTO other = (WaferCustomerLotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}