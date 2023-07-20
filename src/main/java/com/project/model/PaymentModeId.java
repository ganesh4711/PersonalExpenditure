package com.project.model;

import java.io.Serializable;
import java.util.Objects;

public class PaymentModeId implements Serializable {
    private String code;
    private String user;
    PaymentModeId(){}
	public PaymentModeId(String code, String uname) {
		super();
		this.code = code;
		this.user = uname;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(code, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentModeId other = (PaymentModeId) obj;
		return Objects.equals(code, other.code) && Objects.equals(user, other.user);
	}
  
}