package com.acetecsemi.godzilla.Godzilla.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "godzilla_serialNumber")
public class SerialNumber extends GodzillaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3262773965182464547L;

	private int len;
	
	private int charLen;
	
	private String nowSerial;
	
	private String nextSerial;
	
	private String prevSerial;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getCharLen() {
		return charLen;
	}

	public void setCharLen(int charLen) {
		this.charLen = charLen;
	}

	public String getNowSerial() {
		return nowSerial;
	}

	public void setNowSerial(String nowSerial) {
		this.nowSerial = nowSerial;
	}

	public String getNextSerial() {
		return nextSerial;
	}

	public void setNextSerial(String nextSerial) {
		this.nextSerial = nextSerial;
	}

	public String getPrevSerial() {
		return prevSerial;
	}

	public void setPrevSerial(String prevSerial) {
		this.prevSerial = prevSerial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + charLen;
		result = prime * result + len;
		result = prime * result
				+ ((nextSerial == null) ? 0 : nextSerial.hashCode());
		result = prime * result
				+ ((nowSerial == null) ? 0 : nowSerial.hashCode());
		result = prime * result
				+ ((prevSerial == null) ? 0 : prevSerial.hashCode());
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
		SerialNumber other = (SerialNumber) obj;
		if (charLen != other.charLen)
			return false;
		if (len != other.len)
			return false;
		if (nextSerial == null) {
			if (other.nextSerial != null)
				return false;
		} else if (!nextSerial.equals(other.nextSerial))
			return false;
		if (nowSerial == null) {
			if (other.nowSerial != null)
				return false;
		} else if (!nowSerial.equals(other.nowSerial))
			return false;
		if (prevSerial == null) {
			if (other.prevSerial != null)
				return false;
		} else if (!prevSerial.equals(other.prevSerial))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId())
				,this.getCreateDate().toString(),this.getLastModifyDate().toString()};
	}
	
}
