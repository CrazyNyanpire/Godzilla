package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author harlow
 * @version 1.0
 * @created 30-10-2014 9:42:11
 */
@Entity
@Table(name = "godzilla_materialname")
public class MaterialName extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 962725516666469524L;
	private String materialName;
	/**
	 * 1:直接材料;
	 * 2:间接材料；
	 */
	private Integer materialType;//1:直接材料;2:间接材料;
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public Integer getMaterialType() {
		return materialType;
	}
	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}
	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getMaterialName()};
	}

}