package com.acetecsemi.godzilla.Godzilla.core;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:04
 */
@Entity
@Table(name = "godzilla_process")
public class WaferProcess extends Process {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private WaferCompanyLot waferCompanyLot;
	/**进站Die数量*/
	private int qtyIn;
	/**进站wafer片数**/
	private int waferQtyIn;
	/**出站die数量*/
	private int qtyOut;
	/**出站wafer片数**/
	private int waferQtyOut;
	/**进站晶圆条数**/
	private Integer stripQtyIn;
	
	private Set<Location> locations = new HashSet<Location>();
	private Set<RejectCodeItem> rejectCodeItems = new HashSet<RejectCodeItem>();
	private Station futureStation;
	private Station rejectStation;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)  
	@JoinColumn(name="wafer_company_lot_ID", referencedColumnName="ID")
	public WaferCompanyLot getWaferCompanyLot() {
		return waferCompanyLot;
	}

	public void setWaferCompanyLot(WaferCompanyLot waferCompanyLot) {
		this.waferCompanyLot = waferCompanyLot;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY,mappedBy ="waferProcess")
	//@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	//@JoinTable(name = "godzilla_process_location", joinColumns = @JoinColumn(name = "process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "location_ID", referencedColumnName = "ID"))
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public Integer getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(Integer qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Integer getWaferQtyIn() {
		return waferQtyIn;
	}

	public void setWaferQtyIn(Integer waferQtyIn) {
		this.waferQtyIn = waferQtyIn;
	}

	public Integer getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Integer qtyOut) {
		this.qtyOut = qtyOut;
	}

	public Integer getWaferQtyOut() {
		return waferQtyOut;
	}

	public void setWaferQtyOut(Integer waferQtyOut) {
		this.waferQtyOut = waferQtyOut;
	}

	public Integer getStripQtyIn() {
		return stripQtyIn;
	}

	public void setStripQtyIn(Integer stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "godzilla_process_recode", joinColumns = @JoinColumn(name = "process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "reject_code_ID", referencedColumnName = "ID"))
	public Set<RejectCodeItem> getRejectCodeItems() {
		return rejectCodeItems;
	}

	public void setRejectCodeItems(Set<RejectCodeItem> rejectCodeItems) {
		this.rejectCodeItems = rejectCodeItems;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "future_hold_station_ID", referencedColumnName = "ID")
	public Station getFutureStation() {
		return futureStation;
	}

	public void setFutureStation(Station futureStation) {
		this.futureStation = futureStation;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "reject_station_ID", referencedColumnName = "ID")
	public Station getRejectStation() {
		return rejectStation;
	}

	public void setRejectStation(Station rejectStation) {
		this.rejectStation = rejectStation;
	}
	
	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = super.hashCode();
		result = prime * result
				+ ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + qtyIn;
		result = prime * result + qtyOut;
		result = prime * result + stripQtyIn;
		result = prime * result
				+ ((waferCompanyLot == null) ? 0 : waferCompanyLot.hashCode());
		result = prime * result + waferQtyIn;
		result = prime * result + waferQtyOut;
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
		WaferProcess other = (WaferProcess) obj;
		if (locations == null) {
			if (other.locations != null)
				return false;
		} else if (!locations.equals(other.locations))
			return false;
		if (qtyIn != other.qtyIn)
			return false;
		if (qtyOut != other.qtyOut)
			return false;
		if (stripQtyIn != other.stripQtyIn)
			return false;
		if (waferCompanyLot == null) {
			if (other.waferCompanyLot != null)
				return false;
		} else if (!waferCompanyLot.equals(other.waferCompanyLot))
			return false;
		if (waferQtyIn != other.waferQtyIn)
			return false;
		if (waferQtyOut != other.waferQtyOut)
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}
}