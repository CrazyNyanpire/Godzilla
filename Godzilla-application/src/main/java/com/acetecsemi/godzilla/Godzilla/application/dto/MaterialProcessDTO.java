package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

import com.acetecsemi.godzilla.Godzilla.application.utils.JsonDateTimeSerializer;

public class MaterialProcessDTO implements Serializable {

	private Long id;

	private Integer shippingTime;

	private Integer delayTime;

	private Integer sort;

	private String status;

	private Integer qtyIn;

	private Integer qtyOut;

	private String lotType;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;

	private Integer stripQtyOut;

	private RunCardTemplateDTO runCardTemplate;

	private Integer scrapsQtyOut;

	private Integer goodQtyOut;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private Integer stripQtyIn;

	private UserDTO userDTO;

	private String partId;

	private String materialName;

	private String partNameCN;

	private String batchNo;

	private Double balance;

	private String unit;

	private String venderName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entryDate;

	private String currStatus;

	private String userName;

	private String stockPos;

	private String pon;

	private String entryTime;

	private Long futureHoldStationId;

	private Long materialLotId;

	private String LocationIds;

	private Long stationId;

	private Integer materialType;

	private String lotNo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date guaranteePeriod;

	private String customerLotNo;
	
	private String engineerName;
	
	private Double inCapacity;
	
	private Long nextStationId;
	
	private String holdReason;
	
	private String useraccount;
	
	private String comments;
	
	public void setShippingTime(Integer shippingTime) {
		this.shippingTime = shippingTime;
	}

	public Integer getShippingTime() {
		return this.shippingTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setQtyIn(Integer qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Integer getQtyIn() {
		return this.qtyIn;
	}

	public void setQtyOut(Integer qtyOut) {
		this.qtyOut = qtyOut;
	}

	public Integer getQtyOut() {
		return this.qtyOut;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public String getLotType() {
		return this.lotType;
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

	public void setStripQtyOut(Integer stripQtyOut) {
		this.stripQtyOut = stripQtyOut;
	}

	public Integer getStripQtyOut() {
		return this.stripQtyOut;
	}

	public void setRunCardTemplate(RunCardTemplateDTO runCardTemplate) {
		this.runCardTemplate = runCardTemplate;
	}

	public RunCardTemplateDTO getRunCardTemplate() {
		return this.runCardTemplate;
	}

	public void setScrapsQtyOut(Integer scrapsQtyOut) {
		this.scrapsQtyOut = scrapsQtyOut;
	}

	public Integer getScrapsQtyOut() {
		return this.scrapsQtyOut;
	}

	public void setGoodQtyOut(Integer goodQtyOut) {
		this.goodQtyOut = goodQtyOut;
	}

	public Integer getGoodQtyOut() {
		return this.goodQtyOut;
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

	public void setStripQtyIn(Integer stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}

	public Integer getStripQtyIn() {
		return this.stripQtyIn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStockPos() {
		return stockPos;
	}

	public void setStockPos(String stockPos) {
		this.stockPos = stockPos;
	}

	public String getPon() {
		return pon;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public Long getFutureHoldStationId() {
		return futureHoldStationId;
	}

	public void setFutureHoldStationId(Long futureHoldStationId) {
		this.futureHoldStationId = futureHoldStationId;
	}

	public Long getMaterialLotId() {
		return materialLotId;
	}

	public void setMaterialLotId(Long materialLotId) {
		this.materialLotId = materialLotId;
	}

	public String getLocationIds() {
		return LocationIds;
	}

	public void setLocationIds(String locationIds) {
		LocationIds = locationIds;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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

	public String getCustomerLotNo() {
		return customerLotNo;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	public Double getInCapacity() {
		return inCapacity;
	}

	public void setInCapacity(Double inCapacity) {
		this.inCapacity = inCapacity;
	}

	public Long getNextStationId() {
		return nextStationId;
	}

	public void setNextStationId(Long nextStationId) {
		this.nextStationId = nextStationId;
	}

	public String getHoldReason() {
		return holdReason;
	}

	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}

	public String getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
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
		MaterialProcessDTO other = (MaterialProcessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}