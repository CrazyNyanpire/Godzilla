package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.auth.core.domain.User;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author harlow
 * @version 1.0
 * @created 28-07-2014 9:42:13
 */
@Entity
@Table(name = "godzilla_optlog")
public class OptLog extends KoalaAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186144763432037028L;
	private String comments;
	private String optName;
	private User rightUser;
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	@ManyToOne
	@JoinColumn(name = "RIGHT_USER_ID")
	public User getRightUserUser() {
		return rightUser;
	}

	public void setRightUserUser(User rightUser) {
		this.rightUser = rightUser;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((optName == null) ? 0 : optName.hashCode());
		result = prime * result + ((rightUser == null) ? 0 : rightUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptLog other = (OptLog) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (optName == null) {
			if (other.optName != null)
				return false;
		} else if (!optName.equals(other.optName))
			return false;
		if (rightUser == null) {
			if (other.rightUser != null)
				return false;
		} else if (!rightUser.equals(other.rightUser))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getComments().toString()};
	}

}