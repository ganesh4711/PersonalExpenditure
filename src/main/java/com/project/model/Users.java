package com.project.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Users {
	@Id
    @Column(name = "uname")
	private String uname;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<PaymentMode> userpay = new ArrayList<>();

	public Users() {
		super();
	}

	public Users(String uname) {
		super();
		this.uname = uname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public List<PaymentMode> getUserpay() {
		return userpay;
	}

	public void setUserpay(List<PaymentMode> userpay) {
		this.userpay = userpay;
	}

}
