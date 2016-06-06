package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class WaferInfoDTO implements Serializable {

	private Long id;

	private Integer dieQty;

	private Integer waferNumber;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private String waferId;

	private String wafer;

	private Integer waferQty;
	
	private Integer scrapsQty;

	public void setDieQty(Integer dieQty) {
		this.dieQty = dieQty;
	}

	public Integer getDieQty() {
		return this.dieQty;
	}

	public void setWaferNumber(Integer waferNumber) {
		this.waferNumber = waferNumber;
	}

	public Integer getWaferNumber() {
		return this.waferNumber;
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

	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}

	public String getWaferId() {
		return this.waferId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getWafer() {
		return wafer;
	}

	public void setWafer(String wafer) {
		this.wafer = wafer;
	}

	public Integer getWaferQty() {
		return waferQty;
	}

	public void setWaferQty(Integer waferQty) {
		this.waferQty = waferQty;
	}

	public Integer getScrapsQty() {
		return scrapsQty;
	}

	public void setScrapsQty(Integer scrapsQty) {
		this.scrapsQty = scrapsQty;
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
		WaferInfoDTO other = (WaferInfoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}