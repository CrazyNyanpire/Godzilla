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
 * @created 28-07-2014 9:42:14
 */
@Entity
@Table(name = "godzilla_manufactureLot")
public class ManufactureLot extends GodzillaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 254909893126231907L;
	private String lotNo;
	
	private ManufactureLot mergeManufactureLot;
	private ManufactureLot parentManufactureLot;
	private Set<ManufactureLot> manufactureLots = new HashSet<ManufactureLot>();
	private ManufactureProcess nowManufactureProcess;
	private Set<ManufactureProcess> manufactureProcesses = new HashSet<ManufactureProcess>();
	private SubstrateProcess substrateProcess;
	private Product product;
	private Customer customer;
	private DefineStationProcess defineStationProcess;
	private String packSize;
	private Long packSizeId;
	private ManufactureProcess fromManufactureProcess;
	
	public Long getPackSizeId() {
		return packSizeId;
	}

	public void setPackSizeId(Long packSizeId) {
		this.packSizeId = packSizeId;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="merge_manufacture_lot_ID", referencedColumnName="ID")
	public ManufactureLot getMergeManufactureLot() {
		return mergeManufactureLot;
	}

	public void setMergeManufactureLot(
			ManufactureLot mergeManufactureLot) {
		this.mergeManufactureLot = mergeManufactureLot;
	}

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="parent_manufacture_lot_ID", referencedColumnName="ID")
	public ManufactureLot getParentManufactureLot() {
		return parentManufactureLot;
	}

	public void setParentManufactureLot(
			ManufactureLot parentManufactureLot) {
		this.parentManufactureLot = parentManufactureLot;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="manufactureLots") 
	public Set<ManufactureLot> getManufactureLots() {
		return manufactureLots;
	}

	public void setManufactureLots(
			Set<ManufactureLot> manufactureLots) {
		this.manufactureLots = manufactureLots;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="now_manufacture_process_ID", referencedColumnName="ID")
	public ManufactureProcess getNowManufactureProcess() {
		return nowManufactureProcess;
	}

	public void setNowManufactureProcess(ManufactureProcess nowManufactureProcess) {
		this.nowManufactureProcess = nowManufactureProcess;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="manufactureLot") 
	public Set<ManufactureProcess> getManufactureProcesses() {
		return manufactureProcesses;
	}

	public void setManufactureProcesses(Set<ManufactureProcess> manufactureProcesses) {
		this.manufactureProcesses = manufactureProcesses;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="sub_process_ID", referencedColumnName="ID")
	public SubstrateProcess getSubstrateProcess() {
		return substrateProcess;
	}

	public void setSubstrateProcess(SubstrateProcess substrateProcess) {
		this.substrateProcess = substrateProcess;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="product_ID", referencedColumnName="ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="customer_ID", referencedColumnName="ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "define_station_process_ID", referencedColumnName = "ID")
	public DefineStationProcess getDefineStationProcess() {
		return defineStationProcess;
	}

	public void setDefineStationProcess(
			DefineStationProcess defineStationProcess) {
		this.defineStationProcess = defineStationProcess;
	}
	
	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)  
	@JoinColumn(name="from_manufacture_process_ID", referencedColumnName="ID")
	public ManufactureProcess getFromManufactureProcess() {
		return fromManufactureProcess;
	}

	public void setFromManufactureProcess(ManufactureProcess fromManufactureProcess) {
		this.fromManufactureProcess = fromManufactureProcess;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getLotNo().toString()
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}
}