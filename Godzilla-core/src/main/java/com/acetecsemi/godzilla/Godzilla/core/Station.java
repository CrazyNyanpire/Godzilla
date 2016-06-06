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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:02
 */
/**
 * @author abel
 *
 */
@Entity
@Table(name = "godzilla_station")
public class Station extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2945043892094745028L;
	private String stationName;//站别名称
	private String stationNameEn;//站别名称英文
	private Station parentStation;
	private int stationType;//1:parentStation 2:leafStation
	
	private Integer sequence;//站点顺序
	private Double defYield;//站点默认良率
	private Integer consume; //此站是否可能扣减材料   1：扣减，0:不扣减
	
	private Set<Equipment> equipments = new HashSet<Equipment>();
	private Set<RejectCodeItem> rejectCodeItems = new HashSet<RejectCodeItem>();
	private Set<StationAlert> stationAlert = new HashSet<StationAlert>();
	
	private RunCardItem runCardItem;
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationNameEn() {
		return stationNameEn;
	}
	public void setStationNameEn(String stationNameEn) {
		this.stationNameEn = stationNameEn;
	}
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="parent_station_ID", referencedColumnName="ID")
	public Station getParentStation() {
		return parentStation;
	}
	public void setParentStation(Station parentStation) {
		this.parentStation = parentStation;
	}
	public int getStationType() {
		return stationType;
	}
	public void setStationType(int stationType) {
		this.stationType = stationType;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "godzilla_station_equipment", joinColumns = @JoinColumn(name = "station_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "equipment_ID", referencedColumnName = "ID"))
	public Set<Equipment> getEquipments() {
		return equipments;
	}
	public void setEquipments(Set<Equipment> equipments) {
		this.equipments = equipments;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH }, mappedBy = "station")
	@OrderBy("rejectCode ASC")
	public Set<RejectCodeItem> getRejectCodeItems() {
		return rejectCodeItems;
	}
	public void setRejectCodeItems(Set<RejectCodeItem> rejectCodeItems) {
		this.rejectCodeItems = rejectCodeItems;
	}
	public Double getDefYield() {
		return defYield;
	}
	public void setDefYield(Double defYield) {
		this.defYield = defYield;
	}
	
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY ,mappedBy ="stationOutId")
	public Set<StationAlert> getStationAlert()
	{
		return stationAlert;
	}
	public void setStationAlert(Set<StationAlert> stationAlert)
	{
		this.stationAlert = stationAlert;
	}
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="runcarditem_ID", referencedColumnName="ID")
	public RunCardItem getRunCardItem() {
		return runCardItem;
	}
	public void setRunCardItem(RunCardItem runCardItem) {
		this.runCardItem = runCardItem;
	}
	
	public Integer getConsume() {
		return consume;
	}
	public void setConsume(Integer consume) {
		this.consume = consume;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
//		result = prime * result + alertHour;
//		result = prime * result + alertRate;
		result = prime * result
				+ ((parentStation == null) ? 0 : parentStation.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
//		result = prime * result + standardHour;
		result = prime * result
				+ ((stationName == null) ? 0 : stationName.hashCode());
		result = prime * result
				+ ((stationNameEn == null) ? 0 : stationNameEn.hashCode());
		result = prime * result + stationType;
		return result;
	}

	
	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getStationName(),
				this.getCreateDate().toString() };
	}

}