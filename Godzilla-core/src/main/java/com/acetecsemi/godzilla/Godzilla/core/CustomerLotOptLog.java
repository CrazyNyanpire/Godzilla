package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "godzilla_customerlot_optlog")
public class CustomerLotOptLog extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String optName;
	private String custlotTableName;
	private Long custlotId;
	private Long optUser;
	private String description;
	
	public String getOptName() {
		return optName;
	}



	public void setOptName(String optName) {
		this.optName = optName;
	}



	public String getCustlotTableName() {
		return custlotTableName;
	}



	public void setCustlotTableName(String custlotTableName) {
		this.custlotTableName = custlotTableName;
	}



	public Long getCustlotId() {
		return custlotId;
	}



	public void setCustlotId(Long custlotId) {
		this.custlotId = custlotId;
	}



	public Long getOptUser() {
		return optUser;
	}



	public void setOptUser(Long optUser) {
		this.optUser = optUser;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}
