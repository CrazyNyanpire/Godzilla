package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class MaterialDTO implements Serializable {

	private Long id;

		
	private String partNameCN;
	
		
	private String unit;
	
		
	private String pon;
	
						
	private Integer materialType;
	
	private UserDTO userDTO;
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;
		
	private String vender;
	
		
	private String partId;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;
		
	private String materialName;
	
	private Long materialNameId;
	
	private String materialSpecification;
	private Double measure;
	private Double minCapacity;
	private String minUnit;
	private Long stationId;
	private String stationName;
		

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getMaterialSpecification() {
		return materialSpecification;
	}

	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public Double getMinCapacity() {
		return minCapacity;
	}

	public void setMinCapacity(Double minCapacity) {
		this.minCapacity = minCapacity;
	}

	public String getMinUnit() {
		return minUnit;
	}

	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}

	public Long getMaterialNameId() {
		return materialNameId;
	}

	public void setMaterialNameId(Long materialNameId) {
		this.materialNameId = materialNameId;
	}

	public void setPartNameCN(String partNameCN) { 
		this.partNameCN = partNameCN;
	}

	public String getPartNameCN() {
		return this.partNameCN;
	}
	
			
		

	public void setUnit(String unit) { 
		this.unit = unit;
	}

	public String getUnit() {
		return this.unit;
	}
	
			
		

	public void setPon(String pon) { 
		this.pon = pon;
	}

	public String getPon() {
		return this.pon;
	}
	
								
		

	public void setMaterialType(Integer materialType) { 
		this.materialType = materialType;
	}

	public Integer getMaterialType() {
		return this.materialType;
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
			
		

	public void setVender(String vender) { 
		this.vender = vender;
	}

	public String getVender() {
		return this.vender;
	}
	
			
		

	public void setPartId(String partId) { 
		this.partId = partId;
	}

	public String getPartId() {
		return this.partId;
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
			
		

	public void setMaterialName(String materialName) { 
		this.materialName = materialName;
	}

	public String getMaterialName() {
		return this.materialName;
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
		MaterialDTO other = (MaterialDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}