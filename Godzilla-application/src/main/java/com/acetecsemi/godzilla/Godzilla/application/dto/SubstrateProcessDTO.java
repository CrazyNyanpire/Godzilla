package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;

import com.acetecsemi.godzilla.Godzilla.application.utils.JsonDateTimeSerializer;

public class SubstrateProcessDTO implements Serializable {

	private Long id;

	private Integer delayTime;

	private Integer shippingTime;

	private Integer sort;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date optDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date optDateEnd;

	private String status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTimeEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;

	private String lotType;

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

	private UserDTO userDTO;

	private Long substrateCustomerId;

	private String locationIds;
	
	private Long stationId;
	
	private Long substrateCustomerLotId;
	
	private String venderName;
	
	private String lotNo;

	private String customerLotNo;
	
	private int qtyIn;
	
	private int stripQtyIn;
	
	private int qtyOut;
	
	private String partNumber;
	
	private String batchNo;
	
	private Date entryDate;
	
	private String currStatus;
	
	private String userName;
	
	private String stockPos;
	
	private String pon ;
	
	private String entryTime;
	
	private Long futureHoldStationId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date guaranteePeriod;
	
	private Long startFlow;
	
	private Long equipmentId;
	
	private Long substrateCompanyId;
	
	private String engineerName;
	
	private String step;
	
	private Long nextStationId;
	
	private String holdReason;
	
	private String useraccount;
	
	private String comments;
	
	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setShippingTime(Integer shippingTime) {
		this.shippingTime = shippingTime;
	}

	public Integer getShippingTime() {
		return this.shippingTime;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getOptDate() {
		return this.optDate;
	}

	public void setOptDateEnd(Date optDateEnd) {
		this.optDateEnd = optDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getOptDateEnd() {
		return this.optDateEnd;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTimeEnd(Date endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTimeEnd() {
		return this.endTimeEnd;
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

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public String getLotType() {
		return this.lotType;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public Long getSubstrateCustomerId() {
		return substrateCustomerId;
	}

	public void setSubstrateCustomerId(Long substrateCustomerId) {
		this.substrateCustomerId = substrateCustomerId;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getSubstrateCustomerLotId() {
		return substrateCustomerLotId;
	}

	public void setSubstrateCustomerLotId(Long substrateCustomerLotId) {
		this.substrateCustomerLotId = substrateCustomerLotId;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getCustomerLotNo() {
		return customerLotNo;
	}

	public void setCustomerLotNo(String customerLotNo) {
		this.customerLotNo = customerLotNo;
	}

	public int getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(int qtyIn) {
		this.qtyIn = qtyIn;
	}

	public int getStripQtyIn() {
		return stripQtyIn;
	}

	public void setStripQtyIn(int stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}

	public int getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(int qtyOut) {
		this.qtyOut = qtyOut;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
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

	public Long getStartFlow() {
		return startFlow;
	}

	public void setStartFlow(Long startFlow) {
		this.startFlow = startFlow;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Long getSubstrateCompanyId() {
		return substrateCompanyId;
	}

	public void setSubstrateCompanyId(Long substrateCompanyId) {
		this.substrateCompanyId = substrateCompanyId;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
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
		SubstrateProcessDTO other = (SubstrateProcessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}