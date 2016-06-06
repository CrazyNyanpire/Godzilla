package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author abel
 *
 */
@Entity
@Table(name = "godzilla_stationalert")
public class StationAlert extends GodzillaAbstractEntity
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4402882683025432814L;
	private int standardHour;
	private int alertHour;
	private int alertRate;
	private String description;
	private String actionDescription;
	private Long stationInId;
	private Station stationOutId;
	/**报警类型：生产批：assly,材料批：materal,晶圆批：preassly*/
	private String type;
	private String materialPartType;
	
	
	
	
	public Long getStationInId()
	{
		return stationInId;
	}
	public void setStationInId(Long stationInId)
	{
		this.stationInId = stationInId;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="stationOutId", referencedColumnName="ID")
	public Station getStationOutId()
	{
		return stationOutId;
	}
	public void setStationOutId(Station stationOutId)
	{
		this.stationOutId = stationOutId;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getMaterialPartType()
	{
		return materialPartType;
	}
	public void setMaterialPartType(String materialPartType)
	{
		this.materialPartType = materialPartType;
	}
	public int getStandardHour() {
		return standardHour;
	}
	public void setStandardHour(int standardHour) {
		this.standardHour = standardHour;
	}
	public int getAlertHour() {
		return alertHour;
	}
	public void setAlertHour(int alertHour) {
		this.alertHour = alertHour;
	}
	public int getAlertRate() {
		return alertRate;
	}
	public void setAlertRate(int alertRate) {
		this.alertRate = alertRate;
	}
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getActionDescription()
	{
		return actionDescription;
	}
	public void setActionDescription(String actionDescription)
	{
		this.actionDescription = actionDescription;
	}
	
	@Override
	public String[] businessKeys()
	{
		return new String[] { String.valueOf(getId()), this.getDescription(),
				this.getCreateDate().toString() };
	}

}
