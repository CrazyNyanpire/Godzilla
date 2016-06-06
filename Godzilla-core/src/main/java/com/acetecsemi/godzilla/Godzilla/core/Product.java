package com.acetecsemi.godzilla.Godzilla.core;

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
 * @created 28-07-2014 9:41:59
 */
@Entity
@Table(name = "godzilla_product")
public class Product extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1088222314548850010L;
	private String productName;
	private String partNo;//页面显示为partName
	private Customer customer;
	private Set<Pack> pack;
//	private String packSize;
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY ,mappedBy ="product") 
	public Set<Pack> getPack() {
		return pack;
	}


	public void setPack(Set<Pack> pack) {
		this.pack = pack;
	}

	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getPartNo() {
		return partNo;
	}


	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_ID", referencedColumnName = "ID")
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

//	public String getPackSize() {
//		return packSize;
//	}
//
//
//	public void setPackSize(String packSize) {
//		this.packSize = packSize;
//	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((partNo == null) ? 0 : partNo.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
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
		Product other = (Product) obj;
		if (partNo == null) {
			if (other.partNo != null)
				return false;
		} else if (!partNo.equals(other.partNo))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}


	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}

}