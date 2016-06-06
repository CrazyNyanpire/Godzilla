package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class PurchaseOrderDTO implements Serializable {

	private Long id;

		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date poDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date poDateEnd;
		
	private String description;
	
		
	private String ppoType;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDateEnd;
		
	private Integer quantity;
	
		
	private String poNumber;
	
		
	private String paymentTerm;
	
		
	private String um;
	
		
	private String partNumber;
	
		
	private String currency;
	
			
		

	public void setPoDate(Date poDate) { 
		this.poDate = poDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPoDate() {
		return this.poDate;
	}
	
	public void setPoDateEnd(Date poDateEnd) { 
		this.poDateEnd = poDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPoDateEnd() {
		return this.poDateEnd;
	}
			
		

	public void setDescription(String description) { 
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
			
		

	public void setPpoType(String ppoType) { 
		this.ppoType = ppoType;
	}

	public String getPpoType() {
		return this.ppoType;
	}
	
			
		

	public void setDeliveryDate(Date deliveryDate) { 
		this.deliveryDate = deliveryDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}
	
	public void setDeliveryDateEnd(Date deliveryDateEnd) { 
		this.deliveryDateEnd = deliveryDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryDateEnd() {
		return this.deliveryDateEnd;
	}
			
		

	public void setQuantity(Integer quantity) { 
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return this.quantity;
	}
	
			
		

	public void setPoNumber(String poNumber) { 
		this.poNumber = poNumber;
	}

	public String getPoNumber() {
		return this.poNumber;
	}
	
			
		

	public void setPaymentTerm(String paymentTerm) { 
		this.paymentTerm = paymentTerm;
	}

	public String getPaymentTerm() {
		return this.paymentTerm;
	}
	
			
		

	public void setUm(String um) { 
		this.um = um;
	}

	public String getUm() {
		return this.um;
	}
	
			
		

	public void setPartNumber(String partNumber) { 
		this.partNumber = partNumber;
	}

	public String getPartNumber() {
		return this.partNumber;
	}
	
			
		

	public void setCurrency(String currency) { 
		this.currency = currency;
	}

	public String getCurrency() {
		return this.currency;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PurchaseOrderDTO other = (PurchaseOrderDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}