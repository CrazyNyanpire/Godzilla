package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class ResourceDTO implements Serializable {

	private Long id;

		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date abolishDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date abolishDateEnd;
		
	private String menuIcon;
	
		
	private String desc;
	
		
	private String level;
	
						
	private Integer sortOrder;
	
		
	private String name;
	
		
	private String serialNumber;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;
		
	private String identifier;
	
							private Boolean isValid;
    private String isValidAsString;
	
			
		

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
			
		

	public void setMenuIcon(String menuIcon) { 
		this.menuIcon = menuIcon;
	}

	public String getMenuIcon() {
		return this.menuIcon;
	}
	
			
		

	public void setDesc(String desc) { 
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
	
			
		

	public void setLevel(String level) { 
		this.level = level;
	}

	public String getLevel() {
		return this.level;
	}
	
								
		

	public void setSortOrder(Integer sortOrder) { 
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
			
		

	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
			
		

	public void setSerialNumber(String serialNumber) { 
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return this.serialNumber;
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
			
		

	public void setIdentifier(String identifier) { 
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return this.identifier;
	}
	
								
		

	public void setIsValid(Boolean isValid) { 
		this.isValid = isValid;
	}

	public Boolean getIsValid() {
		return this.isValid;
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
		ResourceDTO other = (ResourceDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}