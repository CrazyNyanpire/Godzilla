package com.acetecsemi.godzilla.Godzilla.application.dto;

public class SelectItemDTO {
	private Long id;

	private String value;
	
	private Double qty;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}
}
