package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.Resource;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:04
 */
@Entity
@Table(name = "godzilla_spartpartProcess")
public class SpartPartProcess extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908985508528989308L;
	private SpartPart spartPart;
	private Set<Location> locations = new HashSet<Location>();
//	private Location location;
	private Station station;
	private Date shippingDate;
	private Integer shippingTime;// h小时换算*d*h
	private String status;
	private String lotType;
	private Integer delayTime;// h小时换算*d*h
	private Resource resLotType;	
	private SpartPartCompanyLot spartPartCompanyLot;
	
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "spartpartCompnayLot_ID", referencedColumnName = "ID")
	public SpartPartCompanyLot getSpartPartCompanyLot() {
		return spartPartCompanyLot;
	}

	public void setSpartPartCompanyLot(SpartPartCompanyLot spartPartCompanyLot) {
		this.spartPartCompanyLot = spartPartCompanyLot;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "spartpart_ID", referencedColumnName = "ID")
	public SpartPart getSpartPart() {
		return spartPart;
	}

	public void setSpartPart(SpartPart spartPart) {
		this.spartPart = spartPart;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY ,mappedBy ="spartPartProcess")
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "station_ID", referencedColumnName = "ID")
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

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "res_lottype_ID", referencedColumnName = "ID")
	public Resource getResLotType() {
		return resLotType;
	}

	public void setResLotType(Resource resLotType) {
		this.resLotType = resLotType;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString()};
	}
}