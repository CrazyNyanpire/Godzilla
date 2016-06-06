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
@Table(name = "godzilla_manufactureProcess")
public class ManufactureProcess extends Process {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private ManufactureLot manufactureLot;
	private Integer qtyIn;
	private Integer stripQtyIn;
	private Integer qtyOut;
	private Set<Location> manufactureLocations = new HashSet<Location>();
	private Station futureStation;
	private Station rejectStation;
	private Set<RejectCodeItem> rejectCodeItems = new HashSet<RejectCodeItem>();
	private Set<ManufactureDebitProcess> manufactureDebitProcesses = new HashSet<ManufactureDebitProcess>();
	private Integer sampleQtyOut;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "manufacture_lot_ID", referencedColumnName = "ID")
	public ManufactureLot getManufactureLot() {
		return manufactureLot;
	}

	public void setManufactureLot(ManufactureLot manufactureLot) {
		this.manufactureLot = manufactureLot;
	}
	
	public Integer getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(Integer qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Integer getStripQtyIn() {
		return stripQtyIn;
	}

	public void setStripQtyIn(Integer stripQtyIn) {
		this.stripQtyIn = stripQtyIn;
	}

	public Integer getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Integer qtyOut) {
		this.qtyOut = qtyOut;
	}

	//@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	//@JoinTable(name = "godzilla_man_process_location", joinColumns = @JoinColumn(name = "sub_process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "sub_location_ID", referencedColumnName = "ID"))
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY,mappedBy ="manufactureProcess")
	public Set<Location> getManufactureLocations() {
		return manufactureLocations;
	}

	public void setManufactureLocations(Set<Location> manufactureLocations) {
		this.manufactureLocations = manufactureLocations;
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
	
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "godzilla_manu_process_recode", joinColumns = @JoinColumn(name = "process_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "reject_code_ID", referencedColumnName = "ID"))
	public Set<RejectCodeItem> getRejectCodeItems() {
		return rejectCodeItems;
	}

	public void setRejectCodeItems(Set<RejectCodeItem> rejectCodeItems) {
		this.rejectCodeItems = rejectCodeItems;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY,mappedBy ="manufactureProcess") 
	public Set<ManufactureDebitProcess> getManufactureDebitProcesses() {
		return manufactureDebitProcesses;
	}

	public void setManufactureDebitProcesses(
			Set<ManufactureDebitProcess> manufactureDebitProcesses) {
		this.manufactureDebitProcesses = manufactureDebitProcesses;
	}

	public Integer getSampleQtyOut() {
		return sampleQtyOut;
	}

	public void setSampleQtyOut(Integer sampleQtyOut) {
		this.sampleQtyOut = sampleQtyOut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + qtyIn;
		result = prime * result + qtyOut;
		result = prime * result + stripQtyIn;
		result = prime
				* result
				+ ((manufactureLot == null) ? 0 : manufactureLot
						.hashCode());
		result = prime
				* result
				+ ((manufactureLocations == null) ? 0 : manufactureLocations
						.hashCode());
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
		ManufactureProcess other = (ManufactureProcess) obj;
		if (qtyIn != other.qtyIn)
			return false;
		if (qtyOut != other.qtyOut)
			return false;
		if (stripQtyIn != other.stripQtyIn)
			return false;
		if (manufactureLot == null) {
			if (other.manufactureLot != null)
				return false;
		} else if (!manufactureLot.equals(other.manufactureLot))
			return false;
		if (manufactureLocations == null) {
			if (other.manufactureLocations != null)
				return false;
		} else if (!manufactureLocations.equals(other.manufactureLocations))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}
}