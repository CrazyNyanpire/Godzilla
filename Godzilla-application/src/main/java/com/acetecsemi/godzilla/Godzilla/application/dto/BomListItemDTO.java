package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;

public class BomListItemDTO implements Serializable {

	private Long id;

	private Integer sn;

	private String sort;

	private Double qty;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDateEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDateEnd;

	private String stage;

	private String materialName;
	private String partId;
	private String partNameCN;
	private String unit;
	
	private String item;
	private String materialSpecification;

	private Double bomUsage;
	private Double attritionRate;
	private Long materialId;
	
	
	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSort() {
		return this.sort;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getQty() {
		return this.qty;
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

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStage() {
		return this.stage;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartNameCN() {
		return partNameCN;
	}

	public void setPartNameCN(String partNameCN) {
		this.partNameCN = partNameCN;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMaterialSpecification() {
		return materialSpecification;
	}

	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public Double getBomUsage() {
		return bomUsage;
	}

	public void setBomUsage(Double bomUsage) {
		this.bomUsage = bomUsage;
	}

	public Double getAttritionRate() {
		return attritionRate;
	}

	public void setAttritionRate(Double attritionRate) {
		this.attritionRate = attritionRate;
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
		BomListItemDTO other = (BomListItemDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}