package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class MaterialLotDTO extends TableListAbstractDTO implements Serializable {

	private Long id;

	private String shippingTime;
	
	private String shippingDelayTime;
	
	private Long delayTime;

	private String batchNo;

	private String customerLotNo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	private Double qty;

	private Integer strip;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDateEnd;

	private MaterialDTO materialDTO;

	private Long venderId;

	private String venderName;

	private UserDTO optUser;

	private String currStatus;

	private String materialName;

	private String partNameCN;

	private String unit;

	private String pon;

	private Integer materialType;
	
	private Long materialId;
	
	private String partId;
	
	private String userName;
	
	private String remark;
	
	private Date productionDate;
	
	private Date guaranteePeriod;
	
	private Date manufactureDate;
	
	private Date expiryDate;
	
	private int isEngineering;
	
	private String password;
	
	private String lotType;
	
	private Long lotTypeId;
	
	private Long MaterialNameId;
	
	private Double inCapacity;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingTime() {
		return this.shippingTime;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	public String getCustomerLotNo() {
		return this.customerLotNo;
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

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setStrip(Integer strip) {
		this.strip = strip;
	}

	public Integer getStrip() {
		return this.strip;
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
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public MaterialDTO getMaterialDTO() {
		return materialDTO;
	}

	public void setMaterialDTO(MaterialDTO materialDTO) {
		this.materialDTO = materialDTO;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public UserDTO getOptUser() {
		return optUser;
	}

	public void setOptUser(UserDTO optUser) {
		this.optUser = optUser;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getPartNameCN() {
		return partNameCN;
	}

	public void setPartNameCN(String partNameCN) {
		this.partNameCN = partNameCN;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPon() {
		return pon;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Long getMaterialNameId() {
		return MaterialNameId;
	}

	public void setMaterialNameId(Long materialNameId) {
		MaterialNameId = materialNameId;
	}

	public Double getInCapacity() {
		return inCapacity;
	}

	public void setInCapacity(Double inCapacity) {
		this.inCapacity = inCapacity;
	}

	
	public String getShippingDelayTime()
	{
		return shippingDelayTime;
	}

	public void setShippingDelayTime(String shippingDelayTime)
	{
		this.shippingDelayTime = shippingDelayTime;
	}

	public Long getDelayTime()
	{
		return delayTime;
	}

	public void setDelayTime(Long delayTime)
	{
		this.delayTime = delayTime;
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
		MaterialLotDTO other = (MaterialLotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}