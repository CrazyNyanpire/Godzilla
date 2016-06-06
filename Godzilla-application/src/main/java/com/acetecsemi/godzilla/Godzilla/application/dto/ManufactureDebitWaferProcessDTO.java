package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class ManufactureDebitWaferProcessDTO implements Serializable {

	private Long id;

	private Double percent;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	private Integer qty;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private Long manufactureProcessId;

	private Long waferProcessId;

	private Long waferInfoId;
	
	private String actionError;

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getPercent() {
		return this.percent;
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

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQty() {
		return this.qty;
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

	public Long getManufactureProcessId() {
		return manufactureProcessId;
	}

	public void setManufactureProcessId(Long manufactureProcessId) {
		this.manufactureProcessId = manufactureProcessId;
	}

	public Long getWaferProcessId() {
		return waferProcessId;
	}

	public void setWaferProcessId(Long waferProcessId) {
		this.waferProcessId = waferProcessId;
	}

	public Long getWaferInfoId() {
		return waferInfoId;
	}

	public void setWaferInfoId(Long waferInfoId) {
		this.waferInfoId = waferInfoId;
	}

	public String getActionError() {
		return actionError;
	}

	public void setActionError(String actionError) {
		this.actionError = actionError;
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
		ManufactureDebitWaferProcessDTO other = (ManufactureDebitWaferProcessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}