package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "godzilla_delivernote")
public class DeliveryNote extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5555619153214762264L;

	private Customer customer;
	private Product product;
	private Integer delayTime;// h小时换算*d*h
	private String dnNo;
	private String soNo;
	private String customerPoN;
	private String ivoiceNo;
	private Date dDate;
	private Part part;
	private String partNumber;
	private String description;
	private Integer quantity;
	private String um;
	private Date deliveryDate;
	private String status; // (Open/Closed)

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_ID", referencedColumnName = "ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_ID", referencedColumnName = "ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public String getDnNo() {
		return dnNo;
	}

	public void setDnNo(String dnNo) {
		this.dnNo = dnNo;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getCustomerPoN() {
		return customerPoN;
	}

	public void setCustomerPoN(String customerPoN) {
		this.customerPoN = customerPoN;
	}

	public String getIvoiceNo() {
		return ivoiceNo;
	}

	public void setIvoiceNo(String ivoiceNo) {
		this.ivoiceNo = ivoiceNo;
	}

	public Date getdDate() {
		return dDate;
	}

	public void setdDate(Date dDate) {
		this.dDate = dDate;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "part_ID", referencedColumnName = "ID")
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getCustomerPoN() };
	}

}
