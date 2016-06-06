package com.acetecsemi.godzilla.Godzilla.application.dto;


public class StationAlertDTO
{
	private int standardHour;
	private int alertHour;
	private int alertRate;
	private String description;
	private String actionDescription;
	private Long stationInId;
	private Long stationOutId;
	/**批次类型：生产批：assly,材料批：materal,晶圆批：preassly*/
	private String type;
	private String materialPartType;
	
	
	public int getStandardHour()
	{
		return standardHour;
	}
	public void setStandardHour(int standardHour)
	{
		this.standardHour = standardHour;
	}
	public int getAlertHour()
	{
		return alertHour;
	}
	public void setAlertHour(int alertHour)
	{
		this.alertHour = alertHour;
	}
	public int getAlertRate()
	{
		return alertRate;
	}
	public void setAlertRate(int alertRate)
	{
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
	public Long getStationInId()
	{
		return stationInId;
	}
	public void setStationInId(Long stationInId)
	{
		this.stationInId = stationInId;
	}
	public Long getStationOutId()
	{
		return stationOutId;
	}
	public void setStationOutId(Long stationOutId)
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

	
	
}
