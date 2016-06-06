package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:04
 */
@MappedSuperclass
public abstract class Process extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private Station station;
	private Date shippingDate;
	private Integer shippingTime;// h小时换算*d*h
	private String status;
	private Integer sort;
	private String lotType;
	private Equipment equipment;
	private RunCardTemplate runCardTemplate;
	/**die良品数量出站*/
	private Integer goodQtyOut;
	/**die不良品数量*/
	private Integer scrapsQtyOut;
	private Integer stripQtyOut;
	private Integer delayTime;// h小时换算*d*h
	private Resource resLotType;
	private Resource resHold;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.EAGER, optional = true)  
	@JoinColumn(name="station_ID", referencedColumnName="ID")
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Integer getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Integer shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="equipment_ID", referencedColumnName="ID")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Integer getGoodQtyOut() {
		return goodQtyOut;
	}

	public void setGoodQtyOut(Integer goodQtyOut) {
		this.goodQtyOut = goodQtyOut;
	}

	public Integer getScrapsQtyOut() {
		return scrapsQtyOut;
	}

	public void setScrapsQtyOut(Integer scrapsQtyOut) {
		this.scrapsQtyOut = scrapsQtyOut;
	}

	public Integer getStripQtyOut() {
		return stripQtyOut;
	}

	public void setStripQtyOut(Integer stripQtyOut) {
		this.stripQtyOut = stripQtyOut;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="resource_lottype_ID", referencedColumnName="ID")
	public Resource getResLotType() {
		return resLotType;
	}

	public void setResLotType(Resource resLotType) {
		this.resLotType = resLotType;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="resource_hold_ID", referencedColumnName="ID")
	public Resource getResHold() {
		return resHold;
	}

	public void setResHold(Resource resHold) {
		this.resHold = resHold;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		int result = super.hashCode();
		result = prime * result + delayTime;
		result = prime * result
				+ ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + goodQtyOut;
		result = prime * result + ((lotType == null) ? 0 : lotType.hashCode());
		result = prime * result + ((resHold == null) ? 0 : resHold.hashCode());
		result = prime * result
				+ ((resLotType == null) ? 0 : resLotType.hashCode());
		result = prime * result
				+ ((runCardTemplate == null) ? 0 : runCardTemplate.hashCode());
		result = prime * result + scrapsQtyOut;
		result = prime * result
				+ ((shippingDate == null) ? 0 : shippingDate.hashCode());
		result = prime * result + shippingTime;
		result = prime * result + sort;
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + stripQtyOut;
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
		Process other = (Process) obj;
		if (delayTime != other.delayTime)
			return false;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (goodQtyOut != other.goodQtyOut)
			return false;
		if (lotType == null) {
			if (other.lotType != null)
				return false;
		} else if (!lotType.equals(other.lotType))
			return false;
		if (resHold == null) {
			if (other.resHold != null)
				return false;
		} else if (!resHold.equals(other.resHold))
			return false;
		if (resLotType == null) {
			if (other.resLotType != null)
				return false;
		} else if (!resLotType.equals(other.resLotType))
			return false;
		if (runCardTemplate == null) {
			if (other.runCardTemplate != null)
				return false;
		} else if (!runCardTemplate.equals(other.runCardTemplate))
			return false;
		if (scrapsQtyOut != other.scrapsQtyOut)
			return false;
		if (shippingDate == null) {
			if (other.shippingDate != null)
				return false;
		} else if (!shippingDate.equals(other.shippingDate))
			return false;
		if (shippingTime != other.shippingTime)
			return false;
		if (sort != other.sort)
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stripQtyOut != other.stripQtyOut)
			return false;
		return true;
	}


}