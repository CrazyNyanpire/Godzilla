package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:10
 */
@Entity
@Table(name = "godzilla_bomlistitem")
public class BomListItem extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628093280877973725L;
	/***/
	private String sort;
	/****/
	private String stage;
	private Integer sn;
	private Material material;
	private Double qty;
	private BomList bomList;
	/**是否需要扣减数量的*/
	private Boolean monitor;
	private Station station;
	/**每颗单位用量*/
	private Double bomUsage;
	/**损耗比*/
	private Double attritionRate;
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="material_id", referencedColumnName="ID")
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="bom_list_ID", referencedColumnName="ID")
	public BomList getBomList() {
		return bomList;
	}

	public void setBomList(BomList bomList) {
		this.bomList = bomList;
	}



	public Boolean getMonitor() {
		return monitor;
	}

	public void setMonitor(Boolean monitor) {
		this.monitor = monitor;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "station_ID", referencedColumnName = "ID")
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
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
		int result = super.hashCode();
		result = prime * result + ((bomList == null) ? 0 : bomList.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		long temp;
		temp = Double.doubleToLongBits(qty);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + sn;
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((stage == null) ? 0 : stage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BomListItem other = (BomListItem) obj;
		if (bomList == null) {
			if (other.bomList != null)
				return false;
		} else if (!bomList.equals(other.bomList))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (Double.doubleToLongBits(qty) != Double.doubleToLongBits(other.qty))
			return false;
		if (sn != other.sn)
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (stage == null) {
			if (other.stage != null)
				return false;
		} else if (!stage.equals(other.stage))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}



}