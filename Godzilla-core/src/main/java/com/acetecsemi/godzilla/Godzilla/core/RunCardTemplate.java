package com.acetecsemi.godzilla.Godzilla.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:09
 */
@Entity
@Table(name = "godzilla_runcardtemplate")
public class RunCardTemplate extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712281548173153986L;
	private String runCardType;
	private Product product;
	private DefineStationProcess defineStationProcess;
	
	private Set<RunCardItem> runCardItems = new HashSet<RunCardItem>();
	

	public String getRunCardType() {
		return runCardType;
	}

	public void setRunCardType(String runCardType) {
		this.runCardType = runCardType;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="product_ID", referencedColumnName="ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="defStationProcess_ID", referencedColumnName="ID")
	public DefineStationProcess getDefineStationProcess() {
		return defineStationProcess;
	}

	public void setDefineStationProcess(DefineStationProcess defineStationProcess) {
		this.defineStationProcess = defineStationProcess;
	}

	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY ,mappedBy ="runCardTemplate") 
	public Set<RunCardItem> getRunCardItems() {
		return runCardItems;
	}

	public void setRunCardItems(Set<RunCardItem> runCardItems) {
		this.runCardItems = runCardItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((runCardType == null) ? 0 : runCardType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunCardTemplate other = (RunCardTemplate) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (runCardType == null) {
			if (other.runCardType != null)
				return false;
		} else if (!runCardType.equals(other.runCardType))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}