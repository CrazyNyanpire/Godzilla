package com.acetecsemi.godzilla.Godzilla.application.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openkoala.koala.springmvc.JsonDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

public class CustomerLotOptLogDTO implements Serializable {

	private Long id;
	private String optName;
	private String custlotTableName;
	private Long custlotId;
	private String optUser;
	private String optUserPasswd;
	private String description;
	private UserDTO loginUser;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifyDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public String getCustlotTableName() {
		return custlotTableName;
	}
	public void setCustlotTableName(String custlotTableName) {
		this.custlotTableName = custlotTableName;
	}
	public Long getCustlotId() {
		return custlotId;
	}
	public void setCustlotId(Long custlotId) {
		this.custlotId = custlotId;
	}
	public String getOptUser() {
		return optUser;
	}
	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) { 
		this.createDate = createDate;
	}
	public void setLastModifyDate(Date lastModifyDate) { 
		this.lastModifyDate = lastModifyDate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastModifyDate() {
		return this.lastModifyDate;
	}
	
	public String getOptUserPasswd() {
		return optUserPasswd;
	}
	public void setOptUserPasswd(String optUserPasswd) {
		this.optUserPasswd = optUserPasswd;
	}
	public UserDTO getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(UserDTO loginUser) {
		this.loginUser = loginUser;
	}
	
	
}
