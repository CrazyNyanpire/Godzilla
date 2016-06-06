package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class WaferCompanyLotDTO implements Serializable {

	private Long id;

		
	private String lotNo;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;
			
	private UserDTO optUser;	
	
	private Long waferCustomerLotId;

	public void setLotNo(String lotNo) { 
		this.lotNo = lotNo;
	}

	public String getLotNo() {
		return this.lotNo;
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
	
	public UserDTO getOptUser() {
		return optUser;
	}

	public void setOptUser(UserDTO optUser) {
		this.optUser = optUser;
	}

    public Long getWaferCustomerLotId() {
		return waferCustomerLotId;
	}

	public void setWaferCustomerLotId(Long waferCustomerLotId) {
		this.waferCustomerLotId = waferCustomerLotId;
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
		WaferCompanyLotDTO other = (WaferCompanyLotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}