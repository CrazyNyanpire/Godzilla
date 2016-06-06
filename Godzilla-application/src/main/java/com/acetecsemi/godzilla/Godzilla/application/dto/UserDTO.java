package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class UserDTO implements Serializable {

	private Long id;

		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date abolishDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date abolishDateEnd;
		
	private String userDesc;
	
						
	private Integer sortOrder;
	
		
	private String userPassword;
	
		
	private String serialNumber;
	
		
	private String createOwner;
	
		
	private String email;
	
							private Boolean isSuper;
    private String isSuperAsString;
	
		
	private String name;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;
		
	private String userAccount;
	
							private Boolean isValid;
    private String isValidAsString;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastLoginTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastLoginTimeEnd;
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyTimeEnd;
			
		

	public void setAbolishDate(Date abolishDate) { 
		this.abolishDate = abolishDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getAbolishDate() {
		return this.abolishDate;
	}
	
	public void setAbolishDateEnd(Date abolishDateEnd) { 
		this.abolishDateEnd = abolishDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getAbolishDateEnd() {
		return this.abolishDateEnd;
	}
			
		

	public void setUserDesc(String userDesc) { 
		this.userDesc = userDesc;
	}

	public String getUserDesc() {
		return this.userDesc;
	}
	
								
		

	public void setSortOrder(Integer sortOrder) { 
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
			
		

	public void setUserPassword(String userPassword) { 
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return this.userPassword;
	}
	
			
		

	public void setSerialNumber(String serialNumber) { 
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}
	
			
		

	public void setCreateOwner(String createOwner) { 
		this.createOwner = createOwner;
	}

	public String getCreateOwner() {
		return this.createOwner;
	}
	
			
		

	public void setEmail(String email) { 
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	
								
		

	public void setIsSuper(Boolean isSuper) { 
		this.isSuper = isSuper;
	}

	public Boolean getIsSuper() {
		return this.isSuper;
	}
	
			
		

	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
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
			
		

	public void setUserAccount(String userAccount) { 
		this.userAccount = userAccount;
	}

	public String getUserAccount() {
		return this.userAccount;
	}
	
								
		

	public void setIsValid(Boolean isValid) { 
		this.isValid = isValid;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}
	
			
		

	public void setLastLoginTime(Date lastLoginTime) { 
		this.lastLoginTime = lastLoginTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	public void setLastLoginTimeEnd(Date lastLoginTimeEnd) { 
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastLoginTimeEnd() {
		return this.lastLoginTimeEnd;
	}
			
		

	public void setLastModifyTime(Date lastModifyTime) { 
		this.lastModifyTime = lastModifyTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}
	
	public void setLastModifyTimeEnd(Date lastModifyTimeEnd) { 
		this.lastModifyTimeEnd = lastModifyTimeEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastModifyTimeEnd() {
		return this.lastModifyTimeEnd;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
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
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}