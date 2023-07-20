package com.project.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="paymentmode")
@IdClass(PaymentModeId.class)
public class PaymentMode {
    @Id
    private String code;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uname")
    @JsonIgnore
    private Users user;
    
    private String name;
    private String remarks;
    @OneToMany(mappedBy = "payment")
	@JsonIgnore
	private List<Expenditures> payExp = new ArrayList<>();
   
    // Getters and Setters
   public PaymentMode() {}
    
	public PaymentMode(String code) {
		super();
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "PaymentMode [code=" + code + ", user=" + user + ", name=" + name + ", remarks=" + remarks + ", payExp="
				+ payExp + "]";
	}
}
