package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "godzilla_spartPartCompanyLot")
public class SpartPartCompanyLot extends GodzillaAbstractEntity {

	private String lotNo;
	private SpartPart spartPart;
	private SpartPartProcess spartPartProcess;
	
	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public SpartPartProcess getSpartPartProcess() {
		return spartPartProcess;
	}

	public void setSpartPartProcess(SpartPartProcess spartPartProcess) {
		this.spartPartProcess = spartPartProcess;
	}

	@OneToOne
	@JoinColumn(name = "spart_id")
	public SpartPart getSpartPart() {
		return spartPart;
	}

	public void setSpartPart(SpartPart spartPart) {
		this.spartPart = spartPart;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
