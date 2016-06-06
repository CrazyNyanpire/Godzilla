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
@Table(name = "godzilla_bomlist")
public class BomList extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4347163133641071155L;
	private String bomListType;
	private Product product;
	private Set<BomListItem> bomListItems = new HashSet<BomListItem>();

	public String getBomListType() {
		return bomListType;
	}

	public void setBomListType(String bomListType) {
		this.bomListType = bomListType;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_ID", referencedColumnName = "ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="bomList") 
	public Set<BomListItem> getBomListItems() {
		return bomListItems;
	}

	public void setBomListItems(Set<BomListItem> bomListItems) {
		this.bomListItems = bomListItems;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((bomListItems == null) ? 0 : bomListItems.hashCode());
		result = prime * result
				+ ((bomListType == null) ? 0 : bomListType.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		BomList other = (BomList) obj;
		if (bomListItems == null) {
			if (other.bomListItems != null)
				return false;
		} else if (!bomListItems.equals(other.bomListItems))
			return false;
		if (bomListType == null) {
			if (other.bomListType != null)
				return false;
		} else if (!bomListType.equals(other.bomListType))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}