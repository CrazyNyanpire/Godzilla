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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:05
 */
@Entity
@Table(name = "godzilla_wafercompanylot")
public class WaferCompanyLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 853892108528490930L;
	private WaferCustomerLot waferCustomerLot;
	private String lotNo;
	private WaferCompanyLot mergeWaferCompanyLot;
	private WaferCompanyLot parentWaferCompanyLot;
	private Set<WaferCompanyLot> waferCompanyLots = new HashSet<WaferCompanyLot>();
	private WaferProcess nowWaferProcess;
	private Set<WaferProcess> waferProcesses = new HashSet<WaferProcess>();
	private Station futureHoldStation ;
	private Set<WaferInfo> WaferInfos;
	private WaferProcess fromWaferProcess;
	
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "wafer_customer_lot_ID", referencedColumnName = "ID")
	public WaferCustomerLot getWaferCustomerLot() {
		return waferCustomerLot;
	}

	public void setWaferCustomerLot(WaferCustomerLot waferCustomerLot) {
		this.waferCustomerLot = waferCustomerLot;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "merge_wafer_company_lot_ID", referencedColumnName = "ID")
	public WaferCompanyLot getMergeWaferCompanyLot() {
		return mergeWaferCompanyLot;
	}

	public void setMergeWaferCompanyLot(WaferCompanyLot mergeWaferCompanyLot) {
		this.mergeWaferCompanyLot = mergeWaferCompanyLot;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_wafer_company_lot_ID", referencedColumnName = "ID")
	public WaferCompanyLot getParentWaferCompanyLot() {
		return parentWaferCompanyLot;
	}

	public void setParentWaferCompanyLot(WaferCompanyLot parentWaferCompanyLot) {
		this.parentWaferCompanyLot = parentWaferCompanyLot;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "parentWaferCompanyLot")
	public Set<WaferCompanyLot> getWaferCompanyLots() {
		return waferCompanyLots;
	}

	public void setWaferCompanyLots(Set<WaferCompanyLot> waferCompanyLots) {
		this.waferCompanyLots = waferCompanyLots;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="now_wafer_process_ID", referencedColumnName="ID")
	public WaferProcess getNowWaferProcess() {
		return nowWaferProcess;
	}

	public void setNowWaferProcess(WaferProcess nowWaferProcess) {
		this.nowWaferProcess = nowWaferProcess;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "waferCompanyLot")
	public Set<WaferProcess> getWaferProcesses() {
		return waferProcesses;
	}

	public void setWaferProcesses(Set<WaferProcess> waferProcesses) {
		this.waferProcesses = waferProcesses;
	}
	
	@OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "future_station_ID", referencedColumnName = "ID")
	public Station getFutureHoldStation() {
		return futureHoldStation;
	}

	public void setFutureHoldStation(Station futureHoldStation) {
		this.futureHoldStation = futureHoldStation;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "godzilla_lot_waferinfo", joinColumns = @JoinColumn(name = "lot_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "waferinfo_ID", referencedColumnName = "ID"))
	@OrderBy("waferNumber asc")
	public Set<WaferInfo> getWaferInfos() {
		return WaferInfos;
	}

	public void setWaferInfos(Set<WaferInfo> waferInfos) {
		WaferInfos = waferInfos;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="from_wafer_process_ID", referencedColumnName="ID")
	public WaferProcess getFromWaferProcess() {
		return fromWaferProcess;
	}

	public void setFromWaferProcess(WaferProcess fromWaferProcess) {
		this.fromWaferProcess = fromWaferProcess;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getLotNo()
				,this.getCreateDate().toString()};
	}

}