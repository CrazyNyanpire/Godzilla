package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;

import com.acetecsemi.godzilla.Godzilla.application.utils.JsonDateTimeSerializer;

public class WaferProcessDTO implements Serializable {

	private Long id;

	private Integer shippingTime;

	private Integer delayTime;

	private Integer sort;

	private String status;

	private Integer waferQtyOut;

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

	private Long waferCustomerLotId;

	private String locationIds;

	private Long stationId;

	private String optUser;

	private String customerCode;

	private String lotNo;

	private String customerLotNo;

	private String partNumber;

	private String customerOrder;

	private Date entryDate;

	private String entryTime;

	private String currStatus;

	private String stockPos;

	private Integer qtyIn;
	private Integer waferQtyIn;
	private Integer qtyOut;
	private Integer stripQtyIn;

	private Long futureHoldStationId;

	private Long equipmentId;
	
	private String equipmentStatus;
	
	private String equipment;

	private Long startFlow;
	
	private String productName;
	
	private List<RejectCodeItemDTO> rejectCodeItemDTOs = new ArrayList<RejectCodeItemDTO>();
	
	private String step;
	
	private String rejectStation;
	
	private Long nextStationId;
	
	private List<WaferInfoDTO> waferInfoDTOs = new ArrayList<WaferInfoDTO>();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date guaranteePeriod;
	
	private String holdReason;
	
	private String useraccount;
	
	private String comments;
	
	private Integer waferStatusId;
	
	private String waferStatusName;

	public Long getWaferCustomerLotId() {
		return waferCustomerLotId;
	}

	public void setWaferCustomerLotId(Long waferCustomerLotId) {
		this.waferCustomerLotId = waferCustomerLotId;
	}

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

	public void setWaferQtyOut(Integer waferQtyOut) {
		this.waferQtyOut = waferQtyOut;
	}

	public Integer getWaferQtyOut() {
		return this.waferQtyOut;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
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

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getStockPos() {
		if(this.stockPos == null || "".equals(stockPos)){
			return "-";
		}
		return stockPos;
	}

	public void setStockPos(String stockPos) {
		this.stockPos = stockPos;
	}

	public Integer getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(Integer qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Integer getWaferQtyIn() {
		return waferQtyIn;
	}

	public void setWaferQtyIn(Integer waferQtyIn) {
		this.waferQtyIn = waferQtyIn;
	}

	public Integer getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Integer qtyOut) {
		this.qtyOut = qtyOut;
	}

	public Integer getStripQtyIn() {
		return stripQtyIn;
	}

	public void setStripQtyIn(Integer stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}

	public Long getFutureHoldStationId() {
		return futureHoldStationId;
	}

	public void setFutureHoldStationId(Long futureHoldStationId) {
		this.futureHoldStationId = futureHoldStationId;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public String getEquipment() {
		if(this.equipment == null || "".equals(this.equipment)){
			return "-";
		}
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Long getStartFlow() {
		return startFlow;
	}

	public void setStartFlow(Long startFlow) {
		this.startFlow = startFlow;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<RejectCodeItemDTO> getRejectCodeItemDTOs() {
		return rejectCodeItemDTOs;
	}

	public void setRejectCodeItemDTOs(List<RejectCodeItemDTO> rejectCodeItemDTOs) {
		this.rejectCodeItemDTOs = rejectCodeItemDTOs;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getRejectStation() {
		return rejectStation;
	}

	public void setRejectStation(String rejectStation) {
		this.rejectStation = rejectStation;
	}

	public Long getNextStationId() {
		return nextStationId;
	}

	public void setNextStationId(Long nextStationId) {
		this.nextStationId = nextStationId;
	}

	public List<WaferInfoDTO> getWaferInfoDTOs() {
		return waferInfoDTOs;
	}

	public void setWaferInfoDTOs(List<WaferInfoDTO> waferInfoDTOs) {
		this.waferInfoDTOs = waferInfoDTOs;
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

	public Integer getWaferStatusId() {
		return waferStatusId;
	}

	public void setWaferStatusId(Integer waferStatusId) {
		this.waferStatusId = waferStatusId;
	}

	public String getWaferStatusName() {
		return waferStatusName;
	}

	public void setWaferStatusName(String waferStatusName) {
		this.waferStatusName = waferStatusName;
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
		WaferProcessDTO other = (WaferProcessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}