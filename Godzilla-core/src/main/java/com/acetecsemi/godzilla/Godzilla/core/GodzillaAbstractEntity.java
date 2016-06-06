package com.acetecsemi.godzilla.Godzilla.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import org.openkoala.koala.auth.core.domain.User;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;


@MappedSuperclass
public abstract class GodzillaAbstractEntity extends KoalaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6980690950960762847L;
	private User createUser;
	private Date createDate;
	private User lastModifyUser;
	private Date lastModifyDate;
	
	/**
     * 获得实体的建立用户。
     *
     * @return 实体的建立用户
     */
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "CREATE_USER_ID")
    public User getCreateUser() {
		return createUser;
	}

    /**
    * 设置实体建立用户。
    *
    * @param createUser 要设置的用户
    */
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
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

	/**
     * 获得实体的修改用户。
     *
     * @return 实体的修改用户
     */
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "MODIFY_USER_ID")
	public User getLastModifyUser() {
		return lastModifyUser;
	}

    /**
     * 设置实体修改用户。
     *
     * @param lastModifyUser 要设置的时间
     */
	public void setLastModifyUser(User lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	/**
     * 获得实体的修改时间。
     *
     * @return 实体的修改时间
     */
    @Column(name = "LAST_MODIFY_DATE")
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

    /**
     * 设置实体修改时间。
     *
     * @param lastModifyDate 要设置的时间
     */
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

}
