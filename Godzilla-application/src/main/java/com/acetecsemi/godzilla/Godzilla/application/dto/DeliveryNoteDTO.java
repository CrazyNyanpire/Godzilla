package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonTimestampSerializer;
import org.openkoala.koala.springmvc.JsonDateSerializer;


public class DeliveryNoteDTO implements Serializable {

	private Long id;

		
	private String soNo;
	
		
	private String status;
	
		
	private String customerPoN;
	
		
	private String description;
	
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dDateEnd;
		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDateEnd;
		
	private Integer quantity;
	
		
	private String dnNo;
	
		
	private String um;
	
		
	private String ivoiceNo;
	
		
	private String partNumber;
	
			
		

	public void setSoNo(String soNo) { 
		this.soNo = soNo;
	}

	public String getSoNo() {
		return this.soNo;
	}
	
			
		

	public void setStatus(String status) { 
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	
			
		

	public void setCustomerPoN(String customerPoN) { 
		this.customerPoN = customerPoN;
	}

	public String getCustomerPoN() {
		return this.customerPoN;
	}
	
			
		

	public void setDescription(String description) { 
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
			
		

	public void setDDate(Date dDate) { 
		this.dDate = dDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDDate() {
		return this.dDate;
	}
	
	public void setDDateEnd(Date dDateEnd) { 
		this.dDateEnd = dDateEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDDateEnd() {
		return this.dDateEnd;
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
	
			
		

	public void setDnNo(String dnNo) { 
		this.dnNo = dnNo;
	}

	public String getDnNo() {
		return this.dnNo;
	}
	
			
		

	public void setUm(String um) { 
		this.um = um;
	}

	public String getUm() {
		return this.um;
	}
	
			
		

	public void setIvoiceNo(String ivoiceNo) { 
		this.ivoiceNo = ivoiceNo;
	}

	public String getIvoiceNo() {
		return this.ivoiceNo;
	}
	
			
		

	public void setPartNumber(String partNumber) { 
		this.partNumber = partNumber;
	}

	public String getPartNumber() {
		return this.partNumber;
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
		DeliveryNoteDTO other = (DeliveryNoteDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}