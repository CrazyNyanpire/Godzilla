package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 27-10-2014 9:42:09
 */
@Entity
@Table(name = "godzilla_runcarditemremark")
public class RunCardItemRemark extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712281548173153986L;
	private String title;
	private String value;
	private RunCardItem runCardItem;
	private Product product;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="runcard_item_ID", referencedColumnName="ID")
	public RunCardItem getRunCardItem() {
		return runCardItem;
	}

	public void setRunCardItem(RunCardItem runCardItem) {
		this.runCardItem = runCardItem;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)  
	@JoinColumn(name="product_ID", referencedColumnName="ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((runCardItem == null) ? 0 : runCardItem.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunCardItemRemark other = (RunCardItemRemark) obj;
		if (runCardItem == null) {
			if (other.runCardItem != null)
				return false;
		} else if (!runCardItem.equals(other.runCardItem))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}