package com.acetecsemi.godzilla.Godzilla.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:08
 */

@Entity
@Table(name = "godzilla_definestationprocess")
public class DefineStationProcess extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6947747083623078447L;

	private String defineStationProcessName;
	
	private Integer type;//1:晶圆 2:基板 3:直接材料4:间接材料

	private Set<Station> stations = new HashSet<Station>();

	public String getDefineStationProcessName() {
		return defineStationProcessName;
	}

	public void setDefineStationProcessName(String defineStationProcessName) {
		this.defineStationProcessName = defineStationProcessName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "godzilla_defpro_station", joinColumns = @JoinColumn(name = "def_def_station_pro_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "station_ID", referencedColumnName = "ID"))
	@OrderBy("sequence asc")
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}