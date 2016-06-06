package com.acetecsemi.godzilla.Godzilla.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:14
 */
@Entity
@Table(name = "godzilla_substrateCompanyLot")
public class SubstrateCompanyLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 254909893126231907L;
	private SubstrateCustomerLot substrateCustomerLot;
	private String lotNo;
	
	private SubstrateCompanyLot mergeSubstrateCompanyLot;
	private SubstrateCompanyLot parentSubstrateCompanyLot;
	private Set<SubstrateCompanyLot> substrateCompanyLots = new HashSet<SubstrateCompanyLot>();
	private SubstrateProcess nowSubstrateProcess;
	private Set<SubstrateProcess> substrateProcesses = new HashSet<SubstrateProcess>();


	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="substrate_customer_lot_ID", referencedColumnName="ID")
	public SubstrateCustomerLot getSubstrateCustomerLot() {
		return substrateCustomerLot;
	}

	public void setSubstrateCustomerLot(SubstrateCustomerLot substrateCustomerLot) {
		this.substrateCustomerLot = substrateCustomerLot;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="merge_substr_com_lot_ID", referencedColumnName="ID")
	public SubstrateCompanyLot getMergeSubstrateCompanyLot() {
		return mergeSubstrateCompanyLot;
	}

	public void setMergeSubstrateCompanyLot(
			SubstrateCompanyLot mergeSubstrateCompanyLot) {
		this.mergeSubstrateCompanyLot = mergeSubstrateCompanyLot;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="parent_substr_com_lot_ID", referencedColumnName="ID")
	public SubstrateCompanyLot getParentSubstrateCompanyLot() {
		return parentSubstrateCompanyLot;
	}

	public void setParentSubstrateCompanyLot(
			SubstrateCompanyLot parentSubstrateCompanyLot) {
		this.parentSubstrateCompanyLot = parentSubstrateCompanyLot;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="substrateCompanyLots") 
	public Set<SubstrateCompanyLot> getSubstrateCompanyLots() {
		return substrateCompanyLots;
	}

	public void setSubstrateCompanyLots(
			Set<SubstrateCompanyLot> substrateCompanyLots) {
		this.substrateCompanyLots = substrateCompanyLots;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="now_substrate_process_ID", referencedColumnName="ID")
	public SubstrateProcess getNowSubstrateProcess() {
		return nowSubstrateProcess;
	}

	public void setNowSubstrateProcess(SubstrateProcess nowSubstrateProcess) {
		this.nowSubstrateProcess = nowSubstrateProcess;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="substrateCompanyLot") 
	public Set<SubstrateProcess> getSubstrateProcesses() {
		return substrateProcesses;
	}

	public void setSubstrateProcesses(Set<SubstrateProcess> substrateProcesses) {
		this.substrateProcesses = substrateProcesses;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getLotNo().toString()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}
}