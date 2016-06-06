package com.acetecsemi.godzilla.Godzilla.application.dto;

public abstract class TableListAbstractDTO {

	private String sortname;
	private String sortorder;
	private Integer page;
	private Integer pagesize;
	private String actionError;
	
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public String getActionError() {
		return actionError;
	}
	public void setActionError(String actionError) {
		this.actionError = actionError;
	}
	
	
}
