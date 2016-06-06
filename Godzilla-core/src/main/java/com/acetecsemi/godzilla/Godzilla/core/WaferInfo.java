package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "godzilla_waferinfo")
public class WaferInfo extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -468645079570620153L; 
	private Integer waferNumber;
	private String waferId;
	private Integer dieQty;
	private Integer dieInitQty;
	private WaferCustomerLot waferCustomerLot;

	public Integer getWaferNumber() {
		return waferNumber;
	}

	public void setWaferNumber(Integer waferNumber) {
		this.waferNumber = waferNumber;
	}

	public String getWaferId() {
		return waferId;
	}

	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}

	public Integer getDieQty() {
		return dieQty;
	}

	public void setDieQty(Integer dieQty) {
		this.dieQty = dieQty;
	}
	
	public Integer getDieInitQty() {
		return dieInitQty;
	}

	public void setDieInitQty(Integer dieInitQty) {
		this.dieInitQty = dieInitQty;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="wafer_customer_lot_ID", referencedColumnName="ID")
	public WaferCustomerLot getWaferCustomerLot() {
		return waferCustomerLot;
	}

	public void setWaferCustomerLot(WaferCustomerLot waferCustomerLot) {
		this.waferCustomerLot = waferCustomerLot;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dieQty == null) ? 0 : dieQty.hashCode());
		result = prime * result + ((waferId == null) ? 0 : waferId.hashCode());
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
		WaferInfo other = (WaferInfo) obj;
		if (dieQty == null) {
			if (other.dieQty != null)
				return false;
		} else if (!dieQty.equals(other.dieQty))
			return false;
		if (waferId == null) {
			if (other.waferId != null)
				return false;
		} else if (!waferId.equals(other.waferId))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getWaferId().toString(),this.getLastModifyDate().toString()};
	}

}
