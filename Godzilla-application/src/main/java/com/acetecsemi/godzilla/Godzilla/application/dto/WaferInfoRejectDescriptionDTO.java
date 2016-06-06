package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class WaferInfoRejectDescriptionDTO implements Serializable {

	private Long id;

		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;
		
	private String rejcetDescription;
	
			
	private Long waferProcessId;
	
	private Long waferInfoId;

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
			
		

	public void setRejcetDescription(String rejcetDescription) { 
		this.rejcetDescription = rejcetDescription;
	}

	public String getRejcetDescription() {
		return this.rejcetDescription;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
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
		WaferInfoRejectDescriptionDTO other = (WaferInfoRejectDescriptionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}