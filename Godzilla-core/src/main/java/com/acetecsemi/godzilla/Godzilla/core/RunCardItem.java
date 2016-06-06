package com.acetecsemi.godzilla.Godzilla.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:09
 */
@Entity
@Table(name = "godzilla_runcarditem")
public class RunCardItem extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712281548173153986L;
	private RunCardTemplate runCardTemplate;
	private Station station;
	private String haveMagIn;
	private String haveMagOut;
	//private Set<RunCardItemRemark> runCardItemRemarks = new HashSet<RunCardItemRemark>();
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="runcard_template_ID", referencedColumnName="ID")
	public RunCardTemplate getRunCardTemplate() {
		return runCardTemplate;
	}

	public void setRunCardTemplate(RunCardTemplate runCardTemplate) {
		this.runCardTemplate = runCardTemplate;
	}
	
	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="station_ID", referencedColumnName="ID")
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getHaveMagIn() {
		return haveMagIn;
	}

	public void setHaveMagIn(String haveMagIn) {
		this.haveMagIn = haveMagIn;
	}

	public String getHaveMagOut() {
		return haveMagOut;
	}

	public void setHaveMagOut(String haveMagOut) {
		this.haveMagOut = haveMagOut;
	}

//	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="runCardItem") 
//	@OrderBy("title asc")
//	public Set<RunCardItemRemark> getRunCardItemRemarks() {
//		return runCardItemRemarks;
//	}
//
//	public void setRunCardItemRemarks(Set<RunCardItemRemark> runCardItemRemarks) {
//		this.runCardItemRemarks = runCardItemRemarks;
//	}

	
	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}