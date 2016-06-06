package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

import com.acetecsemi.godzilla.Godzilla.application.utils.JsonDateTimeSerializer;

public class SpartPartProcessDTO implements Serializable {

	private Long id;

	private Integer delayTime;

	private String shippingTime;

	private String status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippingDateEnd;

	private String lotType;

	private SpartPartDTO spartPartDTO;
	
	private SpartPartCompanyLotDTO spartPartCompanyLotDTO;
	
	private String locationIds;
	
	private String useraccount;
	
	private String comments;
	
	private UserDTO userDTO;
	
	private Long startFlow;
	
	private Long nextStationId;
	
	private Long stationId;
	
	private String partName;
	private String partId;
	private String partNumber;
	private String description;
	private String vender;
	private String station;
	private String product;
	
	private Date entryDate;
	private String entryTime;
	private String stockPos;
	private String userName;
	private String pon;
	private Integer qty;
	
	
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

	public String getStockPos() {
		return stockPos;
	}

	public void setStockPos(String stockPos) {
		this.stockPos = stockPos;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPon() {
		return pon;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
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

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingTime() {
		return this.shippingTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public SpartPartDTO getSpartPartDTO() {
		return spartPartDTO;
	}

	public void setSpartPartDTO(SpartPartDTO spartPartDTO) {
		this.spartPartDTO = spartPartDTO;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public Long getStartFlow() {
		return startFlow;
	}

	public void setStartFlow(Long startFlow) {
		this.startFlow = startFlow;
	}

	public Long getNextStationId() {
		return nextStationId;
	}

	public void setNextStationId(Long nextStationId) {
		this.nextStationId = nextStationId;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public SpartPartCompanyLotDTO getSpartPartCompanyLotDTO() {
		return spartPartCompanyLotDTO;
	}

	public void setSpartPartCompanyLotDTO(
			SpartPartCompanyLotDTO spartPartCompanyLotDTO) {
		this.spartPartCompanyLotDTO = spartPartCompanyLotDTO;
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
		SpartPartProcessDTO other = (SpartPartProcessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}