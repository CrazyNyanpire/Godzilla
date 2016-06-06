package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author harlow
 * @version 1.0
 * @created 24-12-2014 9:42:10
 */
@Entity
@Table(name = "godzilla_waferInfoRejDes")
public class WaferInfoRejectDescription extends KoalaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628093280877973725L;
	/***/
	private WaferProcess waferProcess;
	/****/
	private WaferInfo waferInfo;

	private String rejcetDescription;
	
	private Date createDate;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "waferProcess_id", referencedColumnName = "ID")
	public WaferProcess getWaferProcess() {
		return waferProcess;
	}

	public void setWaferProcess(WaferProcess waferProcess) {
		this.waferProcess = waferProcess;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "waferInfo_id", referencedColumnName = "ID")
	public WaferInfo getWaferInfo() {
		return waferInfo;
	}

	public void setWaferInfo(WaferInfo waferInfo) {
		this.waferInfo = waferInfo;
	}

	public String getRejcetDescription() {
		return rejcetDescription;
	}

	public void setRejcetDescription(String rejcetDescription) {
		this.rejcetDescription = rejcetDescription;
	}
	
	/**
     * 获得实体的建立时间。
     *
     * @return 实体的建立时间
     */
    @Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
    
    /**
     * 设置实体建立时间。
     *
     * @param createDate 要设置的时间
     */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()), this.getWaferProcess().getId().toString()};
	}
}